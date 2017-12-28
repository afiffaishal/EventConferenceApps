package com.dinus.ec.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.model.SongItem;

import java.util.List;

/**
 * Created by PHAP on 01/09/2016.
 */
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {
    List<SongItem> songItemList;
    Activity context;

    public SongAdapter(Activity context, List<SongItem> songItemList) {
        super();
        this.songItemList = songItemList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lyric, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final SongItem songItem = songItemList.get(i);

        String strTitle = songItem.getTitle();

        viewHolder.tvTitle.setText(strTitle);

    }

    @Override
    public int getItemCount() {
        return songItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById( R.id.tv_title );
        }
    }


}
