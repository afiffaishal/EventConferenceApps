package com.dinus.ec.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.model.HotelItem;
import com.dinus.ec.util.LoadImage;
import com.dinus.ec.util.ScreenSize;

import java.util.List;

/**
 * Created by PHAP on 01/09/2016.
 */
public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
    List<HotelItem> hotelItems;
    Activity context;
    LoadImage loadImage;

    public HotelAdapter(Activity context, List<HotelItem> hotelItems) {
        super();
        this.hotelItems = hotelItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_hotel, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        loadImage = new LoadImage(context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final HotelItem hotelItem = hotelItems.get(i);

        String strImage = String.valueOf(hotelItem.getImage());

        viewHolder.tvHotel.setText(hotelItem.getTitle());

        int width = new ScreenSize(context).getScreenWidth();

        loadImage.LoadImagePicassoResize(strImage, viewHolder.ivHotel, viewHolder.pbAgenda, width, width);

    }

    @Override
    public int getItemCount() {
        return hotelItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHotel;
        private ProgressBar pbAgenda;
        private TextView tvHotel;

        public ViewHolder(View itemView) {
            super(itemView);
            ivHotel = (ImageView)itemView.findViewById( R.id.iv_hotel );
            pbAgenda = (ProgressBar)itemView.findViewById( R.id.pb_agenda );
            tvHotel = (TextView)itemView.findViewById( R.id.tv_hotel );
        }
    }

}
