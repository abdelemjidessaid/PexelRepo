package com.sharpminded.pexelrepo.Utils;

public interface DownloadCallback {
    void onProgressUpdate(int progress);
    void onDownloadCompleted(String result);
}
