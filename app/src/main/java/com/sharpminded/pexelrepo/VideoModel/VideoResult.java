package com.sharpminded.pexelrepo.VideoModel;


import java.util.ArrayList;

public class VideoResult {
    private long page;
    private long per_page;
    private long total_results;
    private String url;
    ArrayList<Video> videos = new ArrayList <> ();

    public VideoResult(long page, long per_page, long total_results, String url, ArrayList<Video> videos) {
        this.page = page;
        this.per_page = per_page;
        this.total_results = total_results;
        this.url = url;
        this.videos = videos;
    }

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getPer_page() {
        return per_page;
    }

    public void setPer_page(long per_page) {
        this.per_page = per_page;
    }

    public long getTotal_results() {
        return total_results;
    }

    public void setTotal_results(long total_results) {
        this.total_results = total_results;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }
}
