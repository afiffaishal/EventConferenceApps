package com.dinus.ec.util;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.dinus.ec.db.DB;
import com.dinus.ec.db.SessionManager;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Jaeger on 16/2/14.
 *
 * Email: chjie.jaeger@gmail.com
 * GitHub: https://github.com/laobie
 */
public class BaseActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private boolean isLogin;
    private CustomToast customToast = new CustomToast();
    private DB db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getApplicationContext());
        db = new DB(getApplication());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public boolean isLogin() {
        return sessionManager.isLoggedIn();
    }

    public CustomToast customToast() {
        return customToast;
    }

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }
}
