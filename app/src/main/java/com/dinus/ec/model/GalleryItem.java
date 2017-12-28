package com.dinus.ec.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by crocodic-mbp-9 on 9/5/17.
 */

public class GalleryItem extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("photos")
    @Expose
    private RealmList<GalleryPhoto> photos = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<GalleryPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(RealmList<GalleryPhoto> photos) {
        this.photos = photos;
    }
}
