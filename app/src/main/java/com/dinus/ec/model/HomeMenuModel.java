package com.dinus.ec.model;

/**
 * Created by PHAP on 01/09/2016.
 */
public class HomeMenuModel {
    private int id, icon;
    private String title;

    public HomeMenuModel() {
    }

    public HomeMenuModel(int id, int icon, String title) {
        this.id = id;
        this.icon = icon;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
