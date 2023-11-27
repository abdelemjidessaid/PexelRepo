package com.sharpminded.pexelrepo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.sharpminded.pexelrepo.Activities.ImagesActivity;
import com.sharpminded.pexelrepo.Activities.VideosActivity;

public class MainActivity extends AppCompatActivity {
    private MaterialCardView images, videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // make the status bar transparent
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        images = findViewById(R.id.images_cardview);
        videos = findViewById(R.id.videos_cardview);
    }

    @Override
    protected void onResume() {
        super.onResume();


        images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ImagesActivity.class));
            }
        });

        videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VideosActivity.class));
            }
        });
    }
}