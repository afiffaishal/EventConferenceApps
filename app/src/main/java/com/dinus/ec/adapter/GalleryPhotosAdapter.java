package com.dinus.ec.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.dinus.ec.R;
import com.dinus.ec.model.GalleryPhoto;
import com.dinus.ec.util.LoadImage;
import com.dinus.ec.util.ScreenSize;

import java.util.List;

/**
 * Created by PHAP on 01/09/2016.
 */
public class GalleryPhotosAdapter extends RecyclerView.Adapter<GalleryPhotosAdapter.ViewHolder> {
    List<GalleryPhoto> galleryPhotos;
    Activity context;
    LoadImage loadImage;

    public GalleryPhotosAdapter(Activity context, List<GalleryPhoto> galleryPhotos) {
        super();
        this.galleryPhotos = galleryPhotos;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_gallery_detail, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        loadImage = new LoadImage(context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final GalleryPhoto galleryPhoto = galleryPhotos.get(i);

        String strImages = String.valueOf(galleryPhoto.getPhoto());

        int width = (new ScreenSize(context).getScreenWidth()/3);
        int height = (new ScreenSize(context).getScreenHeight()/3);

        loadImage.LoadImagePicassoResize(strImages, viewHolder.ivItem, viewHolder.pbItem, width, height);

    }

    @Override
    public int getItemCount() {
        return galleryPhotos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivItem;
        private ProgressBar pbItem;

        public ViewHolder(View itemView) {
            super(itemView);
                ivItem = (ImageView)itemView.findViewById( R.id.iv_item );
                pbItem = (ProgressBar)itemView.findViewById( R.id.pb_item );
        }
    }

    private int dpToPixel(Context context, float dp) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();

        try {
            return (int) (dp * (metrics.densityDpi / 160f));
        } catch (NoSuchFieldError ignored) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
        }
    }

}
