package com.sharpminded.pexelrepo.VideoModel;

public class VideoUser {
    private long id;
    private String name;
    private String url;

    public VideoUser(long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    // Getter Methods

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    // Setter Methods

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
