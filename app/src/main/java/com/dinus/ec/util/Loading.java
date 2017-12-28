package com.dinus.ec.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by yzzzd on 4/13/16.
 */
public class Loading {
    private ProgressDialog pDialog;
    private Context context;

    public Loading(Context context) {
        this.context = context;
    }

    public void showLoading(String pesan){
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(pesan);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public void dismissDialog(){
        pDialog.dismiss();
    }

    public boolean isShowing(){
        return pDialog.isShowing();
    }
}
