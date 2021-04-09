package com.is360.me_azkar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    Switch aSwitch;
    Context context;
    private static String supplication;
    private static Boolean language;

    Button btnMorning, btnEvening;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (NullPointerException e) {
        }

        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.openDataBase();
        databaseHelper.refresh();
        databaseHelper.recreate();

        btnMorning = findViewById(R.id.btnMorning);
        btnEvening = findViewById(R.id.btnEvening);
        aSwitch = findViewById(R.id.switchLanguage);

        // Save State in Shared Preferences
        context = getApplication();
        SharedPreferences sharedPreferences = context.getSharedPreferences("save", context.MODE_PRIVATE);
        aSwitch.setChecked(sharedPreferences.getBoolean("value", false));
        setLanguage(false);
        aSwitch.setChecked(false);

            aSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (aSwitch.isChecked()) {
                        SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                        editor.putBoolean("value", true);
                        editor.apply();
                        aSwitch.setChecked(true);
                        setLanguage(true);
                    } else {
                        SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                        editor.putBoolean("value", false);
                        editor.apply();
                        aSwitch.setChecked(false);
                        setLanguage(false);
                    }
                }
            });

        // morning azkar button
        try {
            btnMorning.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ShowSupplications.class);
                    setSupplication("supplication_morning");
                    startActivity(intent);
                }
            });
        } catch (Exception e) { }

        try {
            btnEvening.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ShowSupplications.class);
                    setSupplication("supplication_evening");
                    startActivity(intent);
                }
            });
        } catch (Exception e) { }

    }

    public static String getSupplication() {
        return supplication;
    }

    public static void setSupplication(String supplication) {
        MainActivity.supplication = supplication;
    }

    public static Boolean getLanguage() {
        return language;
    }

    public static void setLanguage(Boolean language) {
        MainActivity.language = language;
    }

}