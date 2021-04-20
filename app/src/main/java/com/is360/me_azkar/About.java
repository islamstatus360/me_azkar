package com.is360.me_azkar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class About extends AppCompatActivity {

    ImageView blog, facebook, youtube, instagram, twitter;
    CardView whatsappGroup;
    TextView mail,waNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            this.getSupportActionBar().hide();
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (NullPointerException e) {
        }
        setContentView(R.layout.activity_about);

        // find views
        mail = findViewById(R.id.app_email);
        waNumber = findViewById(R.id.app_number);
        whatsappGroup = findViewById(R.id.joinWhatsapp);

        blog = findViewById(R.id.blogger);
        facebook = findViewById(R.id.facebook);
        youtube = findViewById(R.id.youtube);
        instagram = findViewById(R.id.instagram);
        twitter = findViewById(R.id.twitter);


        blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSocialMedia("https://islamstatus360.blogspot.com", "com.android.chrome");
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSocialMedia("fb://page/2203658966582320", "");
            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSocialMedia("https://youtube.com/islamstatus360", "");
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSocialMedia("https://instagram.com/islamstatus360", "");
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSocialMedia("https://twitter.com/islamstatus360", "");
            }
        });

        waNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSocialMedia("https://api.whatsapp.com/send?phone=923106162624", "");
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        whatsappGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSocialMedia("https://islamstatus360.github.io/wagroup", "com.android.chrome");
            }
        });
    }

    private void openSocialMedia (String url, String packageName) {
        Intent intent;
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        if (packageName == "") {
            try {
                startActivity(intent);
            }
            catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else {
            intent.setPackage(packageName);
            try {
                startActivity(intent);
            }
            catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }
}