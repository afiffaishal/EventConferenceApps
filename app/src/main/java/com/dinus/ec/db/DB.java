package com.dinus.ec.db;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.dinus.ec.model.AgendaDate;
import com.dinus.ec.model.AgendaDateItem;
import com.dinus.ec.model.AgendaItem;
import com.dinus.ec.model.GalleryDateItem;
import com.dinus.ec.model.GalleryItem;
import com.dinus.ec.model.MapsItem;
import com.dinus.ec.model.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by crocodic-mbp-9 on 11/20/17.
 */

public class DB {

    private static DB instance;
    private final Realm realm;

    public DB(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static DB with(Fragment fragment) {
        if (instance == null) {
            instance = new DB(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static DB with(Activity activity) {
        if (instance == null) {
            instance = new DB(activity.getApplication());
        }
        return instance;
    }

    public static DB with(Application application) {
        if (instance == null) {
            instance = new DB(application);
        }
        return instance;
    }

    public static DB getInstance() {
        return instance;
    }

    public Realm getRealm() {
        return realm;
    }

    public void refresh() {
        realm.refresh();
    }

    public void saveObject(RealmObject object){
        realm.beginTransaction();
        realm.copyToRealm(object);
        realm.commitTransaction();
    }

    /**
     * Fungsi Simpan Data user
     * @param object data user
     */
    public void saveUser(User object){
        realm.beginTransaction();
        realm.copyToRealm(object);
        realm.commitTransaction();
    }

    public String getIDUser(){
        User realmObject = realm.copyFromRealm(realm.where(User.class).findFirst());
        return String.valueOf(realmObject.getNoid());
    }

    public User getUser(){
        return realm.copyFromRealm(realm.where(User.class).findFirst());
    }

    public List<GalleryDateItem> galleryDateItems(){
        return realm.copyFromRealm(realm.where(GalleryDateItem.class).findAll());
    }

    public List<GalleryItem> galleryItems(){
        return realm.copyFromRealm(realm.where(GalleryItem.class).findAll());
    }

    public List<AgendaDateItem> agendaDateItems(){
        return realm.copyFromRealm(realm.where(AgendaDateItem.class).findAll());
    }

    public List<AgendaItem> agendaItems(String strTgl){
        return realm.copyFromRealm(realm.where(AgendaItem.class).equalTo("tgl", strTgl).findAll());
    }

    public List<MapsItem> mapsItemList(){
        return realm.copyFromRealm(realm.where(MapsItem.class).findAll());
    }

    public void updateUser(User object){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();
    }

    public void deleteAll(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public void deleteAgendaDate(){
        realm.beginTransaction();
        realm.delete(AgendaDateItem.class);
        realm.commitTransaction();
    }

    public void deleteAgenda(){
        realm.beginTransaction();
        realm.delete(AgendaItem.class);
        realm.commitTransaction();
    }

    //// TODO: 10/11/17 CEK JUMLAH DATA
    public long dataSize(Class c){
        return realm.where(c).count();
    }

}
