package com.sharpminded.pexelrepo.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sharpminded.pexelrepo.R;
import com.sharpminded.pexelrepo.Utils.DownloadCallback;
import com.sharpminded.pexelrepo.Utils.DownloadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class PreviewImageActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_STORAGE_PERMISSIONS = 0;

    private ImageView imageView;
    private TextView imageTitle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);

        // make the status bar transparent
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        // get the data
        final String[] title = {getIntent().getStringExtra("title")};
        String url = getIntent().getStringExtra("url");

        imageView = findViewById(R.id.image_preview);
        imageTitle = findViewById(R.id.image_preview_title);

        Picasso.get().load(url).into(imageView);
        imageTitle.setText(title[0]);

        findViewById(R.id.download_image).setOnClickListener(view -> {
            // download the image
            requestStoragePermissions(PreviewImageActivity.this);
            String extension = url.substring(url.lastIndexOf("."));
            if (extension.isEmpty()) extension = ".jpeg";
            if (title[0].isEmpty()) title[0] = "Image_" + System.currentTimeMillis();
            String mimetype = getMimeTypeFromExtension(extension);

            // download file
            DownloadTask download = new DownloadTask(this, title[0] + extension, mimetype, new DownloadCallback() {
                @Override
                public void onProgressUpdate(int progress) {
                    System.out.println("Download started...");
                }

                @Override
                public void onDownloadCompleted(String result) {
                    runOnUiThread(() -> {
                        Toast.makeText(PreviewImageActivity.this, "Download completed.", Toast.LENGTH_SHORT).show();
                    });
                }
            });

            download.execute(url);
        });

        findViewById(R.id.set_as_wallpaper).setOnClickListener(view -> {
            // set the image as wallpaper
            setWallpaperFromImageView(this, imageView);
        });
    }

    public void setWallpaperFromImageView(Context context, ImageView imageView) {
        try {
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageData = byteArrayOutputStream.toByteArray();

            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
            wallpaperManager.setStream(new ByteArrayInputStream(imageData));

            Toast.makeText(context, "Wallpaper set successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void requestStoragePermissions(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Request MANAGE_EXTERNAL_STORAGE permission on Android 13 and above
            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            context.startActivity(intent);
        } else {
            // Request READ_EXTERNAL_STORAGE and WRITE_EXTERNAL_STORAGE permissions on older Android versions
            ActivityCompat.requestPermissions(
                    (Activity) context,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    REQUEST_CODE_STORAGE_PERMISSIONS
            );
        }
    }

    public String getMimeTypeFromExtension(String extension) {
        if (extension == null || extension.isEmpty()) {
            return null;
        }

        extension = extension.toLowerCase();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getMimeTypeFromExtension(extension);
    }
}