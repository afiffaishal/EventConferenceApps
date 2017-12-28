package com.dinus.ec.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.model.GalleryDateItem;

import java.util.List;

/**
 * Created by PHAP on 01/09/2016.
 */
public class GalleryDateAdapter extends RecyclerView.Adapter<GalleryDateAdapter.ViewHolder> {
    List<GalleryDateItem> galleryDateItems;
    Activity context;

    public GalleryDateAdapter(Activity context, List<GalleryDateItem> galleryDateItems) {
        super();
        this.galleryDateItems = galleryDateItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_gallery, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final GalleryDateItem galleryDateItem = galleryDateItems.get(i);

        String strTanggal = String.valueOf(galleryDateItem.getTglView());

        viewHolder.tvItem.setText(strTanggal);
    }

    @Override
    public int getItemCount() {
        return galleryDateItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }


}
