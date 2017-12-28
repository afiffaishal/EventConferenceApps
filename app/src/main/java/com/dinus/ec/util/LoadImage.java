package com.dinus.ec.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.dinus.ec.R;
import com.squareup.picasso.Picasso;

import java.io.File;


/**
 * Created by PHAP on 4/13/16.
 */
public class LoadImage {
    private Context context;

    public LoadImage(Context context) {
        this.context = context;
    }

    public void LoadImagePicasso(String uLoad, ImageView target, final ProgressBar mDialog) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("")||load.isEmpty() || load.equalsIgnoreCase("null")){
            mDialog.setVisibility(View.GONE);
            Picasso
                    .with(context)
                    .load(R.drawable.no_image_512dp)
                    .into(target);
        } else {
            mDialog.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(load)
                    .into(target, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    public void LoadImagePicassoResize(String uLoad, final ImageView target, final ProgressBar mDialog, final int width, final int height) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("")||load.isEmpty() || load.equalsIgnoreCase("null")){
            mDialog.setVisibility(View.GONE);
            Picasso
                    .with(context)
                    .load(R.drawable.no_image_512dp)
                    .placeholder(R.drawable.no_image_512dp)
                    .resize(width, height)
                    .centerCrop()
                    .into(target);
        } else {
            mDialog.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(load)
                    .resize(width, height)
                    .centerCrop()
                    .placeholder(R.drawable.no_image_512dp)
                    .into(target, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                                Picasso
                                        .with(context)
                                        .load(R.drawable.no_image_512dp)
                                        .transform(new RoundedTransform(24, 0))
                                        .placeholder(R.drawable.no_image_512dp)
                                        .resize(width, height)
                                        .centerCrop()
                                        .into(target);
                            }
                        }
                    });
        }
    }

    public void LoadImageRoundedNoProgressDialogPicasso(String uLoad, ImageView target) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("")||load.isEmpty()||load.equals("null")){
            Picasso
                    .with(context)
                    .load(R.drawable.no_image_512dp)
                    .transform(new RoundedTransform(24, 0))
                    .placeholder(R.drawable.no_image_512dp)
                    .resize(300, 300)
                    .centerCrop()
                    .into(target);
        } else {
            Picasso
                    .with(context)
                    .load(load)
                    .transform(new RoundedTransform(24, 0))
                    .placeholder(R.drawable.no_image_512dp)
                    .resize(300, 300)
                    .centerCrop()
                    .into(target);
        }
    }

    public void LoadImageCirclePicasso(String uLoad, ImageView target, final ProgressBar mDialog) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("")||load.isEmpty() || load.equalsIgnoreCase("null")){
            mDialog.setVisibility(View.GONE);
            Picasso
                    .with(context)
                    .load(R.drawable.no_image_512dp)
                    .transform(new CircleTransform())
                    .placeholder(R.drawable.no_image_512dp)
                    .resize(300, 300)
                    .centerCrop()
                    .into(target);
        } else {
            mDialog.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(load)
                    .transform(new CircleTransform())
                    .placeholder(R.drawable.no_image_512dp)
                    .resize(300, 300)
                    .centerCrop()
                    .into(target, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    public void LoadImageCircleNoProgressbarPicasso(String uLoad, ImageView target) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("")||load.isEmpty() || load.equalsIgnoreCase("null")){
            Picasso
                    .with(context)
                    .load(R.drawable.no_image_512dp)
                    .transform(new CircleTransform())
                    .placeholder(R.drawable.no_image_512dp)
                    .resize(300, 300)
                    .centerCrop()
                    .into(target);
        } else {
            Picasso
                    .with(context)
                    .load(load)
                    .transform(new CircleTransform())
                    .placeholder(R.drawable.no_image_512dp)
                    .resize(300, 300)
                    .centerCrop()
                    .into(target);
        }
    }

    public void LoadImageRoundedPicasso(String uLoad, ImageView target, final ProgressBar mDialog) {
        if (uLoad.equalsIgnoreCase("")||uLoad.isEmpty()||uLoad.equalsIgnoreCase("null")){
            if (mDialog != null) {
                mDialog.setVisibility(View.GONE);
            }
            Picasso
                    .with(context)
                    .load(R.drawable.no_image_512dp)
                    .transform(new RoundedTransform(24, 0))
                    .placeholder(R.drawable.no_image_512dp)
                    .resize(300, 300)
                    .centerCrop()
                    .into(target);
        } else {
            String load = uLoad.replace(" ", "%20");
            mDialog.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(load)
                    .transform(new RoundedTransform(24, 0))
                    .placeholder(R.drawable.no_image_512dp)
                    .resize(300, 300)
                    .centerCrop()
                    .into(target, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    public void LoadImageRoundedFilePicasso(File load, ImageView target, final ProgressBar mDialog) {

        if (load == null){
            Picasso
                    .with(context)
                    .load(R.drawable.no_image_512dp)
                    .transform(new RoundedTransform(24, 0))
                    .placeholder(R.drawable.no_image_512dp)
                    .resize(300, 300)
                    .centerCrop()
                    .into(target);
        } else {
            mDialog.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(load)
                    .transform(new RoundedTransform(24, 0))
                    .placeholder(R.drawable.no_image_512dp)
                    .resize(300, 300)
                    .centerCrop()
                    .into(target, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    public void LoadImageRoundedDrawablePicasso(ImageView target) {
        Picasso
                .with(context)
                .load(R.drawable.no_image_512dp)
                .transform(new RoundedTransform(24, 0))
                .placeholder(R.drawable.no_image_512dp)
                .resize(300, 300)
                .centerCrop()
                .into(target);
    }

    public void LoadImageNoProgressPicasso(String uLoad, ImageView target) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("")||load.isEmpty()){
            Picasso
                    .with(context)
                    .load(R.drawable.no_image_512dp)
                    .centerCrop()
                    .resize(300, 300)
                    .placeholder(R.drawable.no_image_512dp)
                    .into(target);
        } else {
            Picasso
                    .with(context)
                    .load(load)
                    .placeholder(R.drawable.no_image_512dp)
                    .into(target);
        }
    }

}
