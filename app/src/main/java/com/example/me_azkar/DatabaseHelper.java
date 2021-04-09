package com.example.me_azkar;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class DatabaseHelper extends SQLiteOpenHelper {

    String data = MainActivity.getSupplication();
    Boolean language = MainActivity.getLanguage();
    String book_name_language = "";
    String hadith_no_language = "";

    // declare varible and store database basic information
    public static final String DATABASE_NAME =  "morning_evening_supplications_data.db";
    public static final String DB_PATH_SUFFIX = "/databases/";

    public static final String SUPPLICATION_TABLE_NAME = "supplication_detail";
    public static final String SUPPLICATION_MORNING_EVENING_TABLE_NAME = "supplication_morning_evening";
    public static final String SUPPLICATION_REFERENCE_TABLE_NAME = "supplication_reference";

    static Context context;
    private static DatabaseHelper instance;
    // constructor
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    public void CopyDataBaseFromAsset() throws IOException {
        InputStream open = context.getAssets().open(DATABASE_NAME);
        String databasePath = getDatabasePath();
        File file = new File(context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!file.exists()) {
            file.mkdir();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(databasePath);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = open.read(bArr);
            if (read > 0) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                fileOutputStream.flush();
                fileOutputStream.close();
                open.close();
                return;
            }
        }
    }

    public static String getDatabasePath() {
        return context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    public void openDataBase() throws SQLException {
        if (!context.getDatabasePath(DATABASE_NAME).exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String name = DatabaseHelper.class.getName();
        Log.w(name, "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        try {
            CopyDataBaseFromAsset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.w(DatabaseHelper.class.getName(), "Data base is upgraded  ");
    }

    public void refresh() {
        new DatabaseHelper(context).openDataBase();
    }
    public void recreate() {
        onCreate(null);
        instance = new DatabaseHelper(context);
        instance.openDataBase();
        instance.refresh();
    }

    public ArrayList<Model> getRowData() {
        ArrayList<Model> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        if (language) {
            book_name_language = "sr.supplication_book_name_en";
            hadith_no_language = "sr.supplication_hadith_no_en";

        } else {
            book_name_language = "sr.supplication_book_name_ur";
            hadith_no_language = "sr.supplication_hadith_no_ur";
        }

        Cursor cursor = db.rawQuery("SELECT sd.supplication_id, sd.supplication_repeat_no, sd.supplication_important_info_ur, sd.supplication_important_info_en , sd.supplication, sd.supplication_translation_ur, sd.supplication_translation_en , sd.supplication_detail_ur, sd.supplication_detail_en , "+book_name_language+" || \" , \" || "+hadith_no_language+" AS REFERENCES_ID FROM supplication_detail AS sd\n" +
                "JOIN supplication_morning_evening AS sme\n" +
                "ON sd.supplication_morning_evening_id = sme.supplication_morning_evening_id\n" +
                "JOIN supplication_reference as sr\n" +
                "ON sd.supplication_reference_id = sr.supplication_reference_id\n" +
                "WHERE sme." + data + " = 1", null);

        if (cursor.moveToFirst()) {
            int i = 1;
            do {
                Model model = new Model();
                model.setSupplication_id(String.valueOf(i));
                model.setSupplication_repeat(cursor.getString(1));
                model.setSupplication_important_info_ur(cursor.getString(2));
                model.setSupplication_important_info_en(cursor.getString(3));
                model.setSupplication(cursor.getString(4));
                model.setSupplication_translation_ur(cursor.getString(5));
                model.setSupplication_translation_en(cursor.getString(6));
                model.setSupplication_detail_ur(cursor.getString(7));
                model.setSupplication_detail_en(cursor.getString(8));
                model.setSupplication_reference_no(cursor.getString(9));
                arrayList.add(model);
                i = i + 1;
            } while (cursor.moveToNext());
        }
        return arrayList;
    }
}