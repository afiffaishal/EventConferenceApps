package com.dinus.ec.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.model.TopRankItem;

import java.util.List;

/**
 * Created by PHAP on 01/09/2016.
 */
public class TopRankAdapter extends RecyclerView.Adapter<TopRankAdapter.ViewHolder> {
    List<TopRankItem> topRankItems;
    Activity context;

    public TopRankAdapter(Activity context, List<TopRankItem> topRankItems) {
        super();
        this.topRankItems = topRankItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_top_rank, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final TopRankItem topRankItem = topRankItems.get(i);

        String strGrup = String.valueOf(topRankItem.getNo())+". "+String.valueOf(topRankItem.getName());
        String strLeader = "Leader : "+String.valueOf(topRankItem.getLeader());
        String strPoint = String.valueOf(topRankItem.getPoint())+"\n Point";

        viewHolder.tvLeader.setText(strLeader);
        viewHolder.tvNama.setText(strGrup);
        viewHolder.tvPoint.setText(strPoint);

    }

    @Override
    public int getItemCount() {
        return topRankItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNama;
        private TextView tvLeader;
        private TextView tvPoint;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNama = (TextView)itemView.findViewById( R.id.tv_nama );
            tvLeader = (TextView)itemView.findViewById( R.id.tv_leader );
            tvPoint = (TextView)itemView.findViewById( R.id.tv_point );
        }
    }


}
