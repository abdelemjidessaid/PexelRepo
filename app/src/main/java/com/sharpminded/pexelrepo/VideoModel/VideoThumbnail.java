package com.sharpminded.pexelrepo.VideoModel;

public class VideoThumbnail {
    private long id;
    private String picture;
    private long nr;

    public VideoThumbnail(long id, String picture, long nr) {
        this.id = id;
        this.picture = picture;
        this.nr = nr;
    }

    // Getter Methods

    public long getId() {
        return id;
    }

    public String getPicture() {
        return picture;
    }

    public long getNr() {
        return nr;
    }

    // Setter Methods

    public void setId(long id) {
        this.id = id;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setNr(long nr) {
        this.nr = nr;
    }
}
