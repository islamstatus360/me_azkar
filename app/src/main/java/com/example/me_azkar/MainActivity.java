package com.example.me_azkar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // object's database helper class
    DatabaseHelper databaseHelper;
    Switch aSwitch;

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
        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        aSwitch.setChecked(sharedPreferences.getBoolean("value", false));
        setLanguage(true);
        try {
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
                        editor.putBoolean("value", true);
                        editor.apply();
                        aSwitch.setChecked(false);
                        setLanguage(false);
                    }
                }
            });
        }
        catch (Exception e) { }


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
        } catch (Exception e) {
        }
        ;

        try {
            btnEvening.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ShowSupplications.class);
                    setSupplication("supplication_evening");
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
        }

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