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

public class AgendaItem extends RealmObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("time_start")
    @Expose
    private String timeStart;
    @SerializedName("time_end")
    @Expose
    private String timeEnd;
    @SerializedName("double_time")
    @Expose
    private String doubleTime;
    @SerializedName("time_start_2")
    @Expose
    private String timeStart2;
    @SerializedName("time_end_2")
    @Expose
    private String timeEnd2;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("speaker")
    @Expose
    private String speaker;
    @SerializedName("materi_show")
    @Expose
    private Integer materiShow;
    @SerializedName("materi")
    @Expose
    private String materi;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("show_song")
    @Expose
    private String showSong;
    @SerializedName("songs")
    @Expose
//    private RealmList<SongItem> songs = null;
    private String tgl;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getDoubleTime() {
        return doubleTime;
    }

    public void setDoubleTime(String doubleTime) {
        this.doubleTime = doubleTime;
    }

    public String getTimeStart2() {
        return timeStart2;
    }

    public void setTimeStart2(String timeStart2) {
        this.timeStart2 = timeStart2;
    }

    public String getTimeEnd2() {
        return timeEnd2;
    }

    public void setTimeEnd2(String timeEnd2) {
        this.timeEnd2 = timeEnd2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public Integer getMateriShow() {
        return materiShow;
    }

    public void setMateriShow(Integer materiShow) {
        this.materiShow = materiShow;
    }

    public String getMateri() {
        return materi;
    }

    public void setMateri(String materi) {
        this.materi = materi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShowSong() {
        return showSong;
    }

    public void setShowSong(String showSong) {
        this.showSong = showSong;
    }

//    public List<SongItem> getSongs() {
//        return songs;
//    }
//
//    public void setSongs(RealmList<SongItem> songs) {
//        this.songs = songs;
//    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }
}
