package com.dinus.ec.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by crocodic-mbp-9 on 9/5/17.
 */

public class AgendaDate implements Serializable {

    private static final long serialVersionUID = 1L;
    @SerializedName("api_response")
    @Expose
    private String apiResponse;
    @SerializedName("api_message")
    @Expose
    private String apiMessage;
    @SerializedName("items")
    @Expose
    private List<AgendaDateItem> items = null;

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

    public List<AgendaDateItem> getItems() {
        return items;
    }

    public void setItems(List<AgendaDateItem> items) {
        this.items = items;
    }

}
