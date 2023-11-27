package com.sharpminded.pexelrepo.ImageModel;

import java.util.ArrayList;

public class ImageResult {
    private long total_results;
    private long page;
    private long per_page;
    private ArrayList <Image> photos = new ArrayList<>();
    private String next_page;

    public ImageResult(long total_results, long page, long per_page, ArrayList<Image> photos, String next_page) {
        this.total_results = total_results;
        this.page = page;
        this.per_page = per_page;
        this.photos = photos;
        this.next_page = next_page;
    }

    // Getter Methods

    public long getTotal_results() {
        return total_results;
    }

    public long getPage() {
        return page;
    }

    public long getPer_page() {
        return per_page;
    }

    public String getNext_page() {
        return next_page;
    }

    public ArrayList<Image> getPhotos() {
        return photos;
    }

    // Setter Methods

    public void setTotal_results(long total_results) {
        this.total_results = total_results;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public void setPer_page(long per_page) {
        this.per_page = per_page;
    }

    public void setNext_page(String next_page) {
        this.next_page = next_page;
    }

    public void setPhotos(ArrayList<Image> photos) {
        this.photos = photos;
    }
}
