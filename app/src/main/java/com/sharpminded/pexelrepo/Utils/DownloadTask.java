package com.sharpminded.pexelrepo.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.sharpminded.pexelrepo.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadTask extends AsyncTask<String, Integer, String> {
    private Context context;
    private String fileName, mimetype;
    private DownloadCallback callback;

    public DownloadTask(Context context, String fileName, String mimetype, DownloadCallback callback) {
        this.context = context;
        this.fileName = fileName;
        this.mimetype = mimetype;
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];

        requestStoragePermission(context);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                saveFileToDownloadDirectory(context, fileName, url, mimetype);
            else downloadFile(context, url, fileName);
            return "Download successful";
        } catch (Exception e) {
            e.printStackTrace();
            return "Download failed";
        }
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if (callback != null) {
            callback.onProgressUpdate(progress[0]);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (callback != null) {
            callback.onDownloadCompleted(result);
        }
    }

    public void requestStoragePermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (
                    context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                            context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        }, 15);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            context.startActivity(intent);
        }
    }

    private void downloadFile(Context context, String url, String fileName) throws IOException {
        File directory = new File(Environment.getExternalStorageDirectory(), "Download/Pexel_Repo");
        System.out.println(directory.getAbsolutePath());
        if (!directory.exists()) directory.mkdirs();
        File file = new File(directory, fileName);

        // Create the file if it doesn't exist
        if (!file.exists()) {
            boolean created = file.createNewFile();
            System.out.println(created ? "Your file is create." : "File not created !!!!");
        }

        // Create an OutputStream to the file
        OutputStream outputStream = new FileOutputStream(file);

        // Download the file from the URL
        URL urlObject = new URL(url);
        URLConnection connection = urlObject.openConnection();
        connection.connect();

        // Get the file size
        int fileSize = connection.getContentLength();

        // Download the file in chunks
        InputStream inputStream = connection.getInputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        int progress = 0;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
            progress += bytesRead;

            publishProgress((progress * 100) / fileSize);
        }

        outputStream.close();
        inputStream.close();
    }

    public void saveFileToDownloadDirectory(Context context, String fileName, String url, String mimetype) throws IOException {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        values.put(MediaStore.Downloads.MIME_TYPE, mimetype);
        values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        ContentResolver resolver = context.getContentResolver();
        Uri downloadUri = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            downloadUri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
        }

        OutputStream outputStream = null;
        if (downloadUri != null) outputStream = resolver.openOutputStream(downloadUri);

        URL urlObject = new URL(url);
        URLConnection connection = urlObject.openConnection();
        connection.connect();

        // Get the file size
        int fileSize = connection.getContentLength();

        // Download the file in chunks
        InputStream inputStream = connection.getInputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        int progress = 0;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            if (outputStream != null) outputStream.write(buffer, 0, bytesRead);
            progress += bytesRead;

            publishProgress((progress * 100) / fileSize);
        }

        if (outputStream != null) outputStream.close();
    }
}
