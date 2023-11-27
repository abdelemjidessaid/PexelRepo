package com.sharpminded.pexelrepo.VideoModel;

import java.util.ArrayList;

public class Video {
    private long id;
    private long width;
    private long height;
    private String url;
    private String image;
    private long duration;
    VideoUser user;
    ArrayList<VideoFile> video_files = new ArrayList <> ();
    ArrayList <VideoThumbnail> video_pictures = new ArrayList <> ();

    public Video(
            long id, long width, long height, String url,
            String image, long duration, VideoUser user,
            ArrayList video_files, ArrayList video_pictures
    ) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.url = url;
        this.image = image;
        this.duration = duration;
        this.user = user;
        this.video_files = video_files;
        this.video_pictures = video_pictures;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWidth() {
        return width;
    }

    public void setWidth(long width) {
        this.width = width;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public VideoUser getUser() {
        return user;
    }

    public void setUser(VideoUser user) {
        this.user = user;
    }

    public ArrayList<VideoFile> getVideo_files() {
        return video_files;
    }

    public void setVideo_files(ArrayList<VideoFile> video_files) {
        this.video_files = video_files;
    }

    public ArrayList<VideoThumbnail> getVideo_pictures() {
        return video_pictures;
    }

    public void setVideo_pictures(ArrayList<VideoThumbnail> video_pictures) {
        this.video_pictures = video_pictures;
    }
}
