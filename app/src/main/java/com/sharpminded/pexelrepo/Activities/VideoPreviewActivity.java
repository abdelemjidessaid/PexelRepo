package com.sharpminded.pexelrepo.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.sharpminded.pexelrepo.R;
import com.sharpminded.pexelrepo.Utils.DownloadCallback;
import com.sharpminded.pexelrepo.Utils.DownloadTask;

public class VideoPreviewActivity extends AppCompatActivity {
    private VideoView videoView;
    private String url, title, extension;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video_preview);

        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        extension = getIntent().getStringExtra("extension");
        String mimetype = getMimeTypeFromExtension(extension);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        videoView = findViewById(R.id.video_preview);
        videoView.setVideoURI(Uri.parse(url));

        findViewById(R.id.downloadVideo).setOnClickListener(view -> {
            // download the video
            DownloadTask task = new DownloadTask(this, title + extension, mimetype, new DownloadCallback() {
                @Override
                public void onProgressUpdate(int progress) {
                    System.out.println("Download started...");
                }

                @Override
                public void onDownloadCompleted(String result) {
                    runOnUiThread(() -> {
                        Toast.makeText(VideoPreviewActivity.this, "Download finished.", Toast.LENGTH_SHORT).show();
                    });
                }
            });
            task.execute(url);
        });

        MediaController controller = new MediaController(VideoPreviewActivity.this);
        controller.setAnchorView(videoView);

        videoView.setMediaController(controller);
        videoView.setOnPreparedListener(mediaPlayer -> {
            mediaPlayer.start();
            mediaPlayer.setVolume(1f, 1f);
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN
            );
        }
    }

    private String getMimeTypeFromExtension(String extension) {
        if (extension == null || extension.isEmpty()) {
            return null;
        }

        extension = extension.toLowerCase();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getMimeTypeFromExtension(extension);
    }
}