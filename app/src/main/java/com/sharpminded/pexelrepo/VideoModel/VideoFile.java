package com.sharpminded.pexelrepo.VideoModel;

public class VideoFile {
    private long id;
    private String quality;
    private String file_type;
    private long width;
    private long height;
    private String link;

    public VideoFile(long id, String quality, String file_type, long width, long height, String link) {
        this.id = id;
        this.quality = quality;
        this.file_type = file_type;
        this.width = width;
        this.height = height;
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
