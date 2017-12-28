package com.dinus.ec.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by crocodic-mbp-9 on 9/11/17.
 */

public class Hotel extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("api_response")
    @Expose
    private String apiResponse;
    @SerializedName("api_message")
    @Expose
    private String apiMessage;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("items")
    @Expose
    private RealmList<HotelItem> items = null;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<HotelItem> getItems() {
        return items;
    }

    public void setItems(RealmList<HotelItem> items) {
        this.items = items;
    }

}
