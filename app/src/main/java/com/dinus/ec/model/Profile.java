package com.dinus.ec.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by crocodic-mbp-9 on 9/8/17.
 */

public class Profile extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1L;
    @SerializedName("api_response")
    @Expose
    private String apiResponse;
    @SerializedName("api_message")
    @Expose
    private String apiMessage;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("group")
    @Expose
    private String group;
    @SerializedName("my_point")
    @Expose
    private Integer myPoint;
    @SerializedName("group_point")
    @Expose
    private Integer groupPoint;
    @SerializedName("leader")
    @Expose
    private String leader;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("pagi_time")
    @Expose
    private String pagiTime;
    @SerializedName("pagi")
    @Expose
    private String pagi;
    @SerializedName("siang_time")
    @Expose
    private String siangTime;
    @SerializedName("siang")
    @Expose
    private String siang;
    @SerializedName("team")
    @Expose
    private RealmList<Team> team = null;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getMyPoint() {
        return myPoint;
    }

    public void setMyPoint(Integer myPoint) {
        this.myPoint = myPoint;
    }

    public Integer getGroupPoint() {
        return groupPoint;
    }

    public void setGroupPoint(Integer groupPoint) {
        this.groupPoint = groupPoint;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPagiTime() {
        return pagiTime;
    }

    public void setPagiTime(String pagiTime) {
        this.pagiTime = pagiTime;
    }

    public String getPagi() {
        return pagi;
    }

    public void setPagi(String pagi) {
        this.pagi = pagi;
    }

    public String getSiangTime() {
        return siangTime;
    }

    public void setSiangTime(String siangTime) {
        this.siangTime = siangTime;
    }

    public String getSiang() {
        return siang;
    }

    public void setSiang(String siang) {
        this.siang = siang;
    }

    public List<Team> getTeam() {
        return team;
    }

    public void setTeam(RealmList<Team> team) {
        this.team = team;
    }

}
