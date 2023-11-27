package com.sharpminded.pexelrepo.ImageModel;

public class Image {
    private float id;
    private float width;
    private float height;
    private String url;
    private String photographer;
    private String photographer_url;
    private float photographer_id;
    private String avg_color;
    private Src SrcObject;
    private boolean liked;
    private String alt;

    public Image(float id, float width, float height, String url, String photographer, String photographer_url,
                 float photographer_id, String avg_color, Src srcObject, boolean liked, String alt) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.url = url;
        this.photographer = photographer;
        this.photographer_url = photographer_url;
        this.photographer_id = photographer_id;
        this.avg_color = avg_color;
        this.SrcObject = srcObject;
        this.liked = liked;
        this.alt = alt;
    }

    // Getter Methods

    public float getId() {
        return id;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public String getUrl() {
        return url;
    }

    public String getPhotographer() {
        return photographer;
    }

    public String getPhotographer_url() {
        return photographer_url;
    }

    public float getPhotographer_id() {
        return photographer_id;
    }

    public String getAvg_color() {
        return avg_color;
    }

    public Src getSrc() {
        return SrcObject;
    }

    public boolean getLiked() {
        return liked;
    }

    public String getAlt() {
        return alt;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public void setPhotographer_url(String photographer_url) {
        this.photographer_url = photographer_url;
    }

    public void setPhotographer_id(float photographer_id) {
        this.photographer_id = photographer_id;
    }

    public void setAvg_color(String avg_color) {
        this.avg_color = avg_color;
    }

    public void setSrc(Src srcObject) {
        this.SrcObject = srcObject;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }
}
