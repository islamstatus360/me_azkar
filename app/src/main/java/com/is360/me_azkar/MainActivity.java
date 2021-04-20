package com.is360.me_azkar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    Context context;
    private static String supplication;
    private static Boolean language;
    Switch aSwitch;
    CardView btnMorning, btnEvening, cardLanguageButton, cardShareButton, cardMoreButton, cardAboutButton;

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
        cardLanguageButton = findViewById(R.id.switchLangCard);
        cardShareButton = findViewById(R.id.cardShareButton);
        cardMoreButton = findViewById(R.id.cardMoreButton);
        cardAboutButton = findViewById(R.id.cardAboutButton);


        // Save State in Shared Preferences
        context = getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("save", context.MODE_PRIVATE);
        aSwitch.setChecked(sharedPreferences.getBoolean("value", false));

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aSwitch.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    aSwitch.setText("English");
                    aSwitch.setChecked(true);
                    setLanguage(true);
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    aSwitch.setText("Urdu");
                    editor.apply();
                    aSwitch.setChecked(false);
                    setLanguage(false);
                }
            }
        });

        // for first time
        if(aSwitch.isChecked()) {
            setLanguage(true);
            aSwitch.setText("English");
        }
        else {
            setLanguage(false);
        }


        // language card button
        cardLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(aSwitch.isChecked())) {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", true);
                    editor.apply();
                    aSwitch.setText("English");
                    aSwitch.setChecked(true);
                    setLanguage(true);
                    aSwitch.setChecked(true);
                }
                else {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("value", false);
                    editor.apply();
                    aSwitch.setText("Urdu");
                    aSwitch.setChecked(false);
                    setLanguage(false);
                    aSwitch.setChecked(false);
                }
            }
        });

        // share card app
        cardShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "ME AZKAR , Daily Morning Evening Supplications");
                    intent.putExtra(Intent.EXTRA_TEXT, "ME AZKAR.\n\nPlease install this app and share with your friends or in groups & give us your valueable feedback.\n\nhttps://play.google.com/store/apps/details?id=com.is360.me_azkar");
                    intent.setType("text/plain");
                    startActivity(Intent.createChooser(intent, "Share ME_Azkar App ..."));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        // more card app
        cardMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:Islam Status 360")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=8907093897824220429")));
                }
            }
        });

        // Card Info Button
        cardAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, About.class);
                startActivity(intent);
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