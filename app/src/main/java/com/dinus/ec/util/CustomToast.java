package com.dinus.ec.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by crocodic-mbp-9 on 8/24/17.
 */

public class CustomToast {
    private Toast mToast = null; // <-- keep this in your Activity or even in a custom Application class

    public void showToast(Context context, String kata){
        try {

            if (mToast != null) mToast.cancel();
            mToast = Toast.makeText(context, kata, Toast.LENGTH_SHORT);
            mToast.show();

        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }


}
