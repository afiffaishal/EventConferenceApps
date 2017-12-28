package com.dinus.ec.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by crocodic-mbp-9 on 9/5/17.
 */

public class AgendaDateItem extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("tgl")
    @Expose
    private String tgl;
    @SerializedName("tgl_view")
    @Expose
    private String tglView;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getTglView() {
        return tglView;
    }

    public void setTglView(String tglView) {
        this.tglView = tglView;
    }
}
