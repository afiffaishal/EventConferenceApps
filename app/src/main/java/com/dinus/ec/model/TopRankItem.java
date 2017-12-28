package com.dinus.ec.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by crocodic-mbp-9 on 9/5/17.
 */

public class TopRankItem extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1L;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("leader")
    @Expose
    private String leader;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("no")
    @Expose
    private Integer no;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }
}
