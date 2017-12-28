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

public class GalleryDate extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1L;
    @SerializedName("api_response")
    @Expose
    private String apiResponse;
    @SerializedName("api_message")
    @Expose
    private String apiMessage;
    @SerializedName("items")
    @Expose
    private RealmList<GalleryDateItem> items = null;

    public String getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(String apiResponse) {
        this.apiResponse = apiResponse;
    }

    public String getApiMessage() {
        return apiMessage;
    }

    public void setApiMessage(String apiMessage) {
        this.apiMessage = apiMessage;
    }

    public List<GalleryDateItem> getItems() {
        return items;
    }

    public void setItems(RealmList<GalleryDateItem> items) {
        this.items = items;
    }

}
