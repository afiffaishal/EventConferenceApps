package com.dinus.ec.util;

/**
 * Created by PHAP on 01/09/2016.
 */
import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by yzzzd on 4/17/16.
 */
public class ScreenSize {
    private Activity activity;
    DisplayMetrics dm = new DisplayMetrics();

    public ScreenSize(Activity activity) {
        this.activity = activity;
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    public int getScreenWidth(){
        return dm.widthPixels;
    }

    public int getScreenHeightHome(){
        return dm.heightPixels-220;
    }

    public int getScreenHeight(){
        return dm.heightPixels;
    }

    public double getScreenInc(){
        int dens = dm.densityDpi;

        double w = (double)getScreenWidth()/(double)dens;
        double h = (double)getScreenHeight()/(double)dens;

        double x = Math.pow(w, 2);
        double y = Math.pow(h, 2);

        return Math.sqrt(x + y);
    }
}
