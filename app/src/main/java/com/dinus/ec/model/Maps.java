package com.dinus.ec.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by crocodic-mbp-9 on 9/6/17.
 */

public class Maps extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("api_response")
    @Expose
    private String apiResponse;
    @SerializedName("api_message")
    @Expose
    private String apiMessage;
    @SerializedName("items")
    @Expose
    private RealmList<MapsItem> items = null;
    @SerializedName("center_lat")
    @Expose
    private String centerLat;
    @SerializedName("center_long")
    @Expose
    private String centerLong;

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

    public List<MapsItem> getItems() {
        return items;
    }

    public void setItems(RealmList<MapsItem> items) {
        this.items = items;
    }

    public String getCenterLat() {
        return centerLat;
    }

    public void setCenterLat(String centerLat) {
        this.centerLat = centerLat;
    }

    public String getCenterLong() {
        return centerLong;
    }

    public void setCenterLong(String centerLong) {
        this.centerLong = centerLong;
    }
}
