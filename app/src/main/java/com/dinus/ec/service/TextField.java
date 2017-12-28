package com.dinus.ec.service;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dinus.ec.R;


/**
 * Created by crocodic-mbp-9 on 1/10/17.
 */

public class TextField extends TextView {

    public TextField(final Context context, final AttributeSet attrs) {
        super(context, attrs, R.attr.textFieldStyle);
    }
}
