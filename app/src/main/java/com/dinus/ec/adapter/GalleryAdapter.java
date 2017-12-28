package com.dinus.ec.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.model.GalleryItem;

import java.util.List;

/**
 * Created by PHAP on 01/09/2016.
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    List<GalleryItem> galleryItems;
    Activity context;

    public GalleryAdapter(Activity context, List<GalleryItem> galleryItems) {
        super();
        this.galleryItems = galleryItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_sub_gallery, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final GalleryItem galleryItem = galleryItems.get(i);

        String strTittle = String.valueOf(galleryItem.getTitle());

        viewHolder.tvItem.setText(strTittle);
    }

    @Override
    public int getItemCount() {
        return galleryItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }


}
