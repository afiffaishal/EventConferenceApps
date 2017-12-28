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
import com.dinus.ec.model.Team;
import com.dinus.ec.util.ConvertDate;
import com.dinus.ec.util.LoadImage;

import java.util.List;

/**
 * Created by PHAP on 01/09/2016.
 */
public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {
    List<Team> teams;
    Activity context;
    LoadImage loadImage;
    ConvertDate convertDate = new ConvertDate();

    public TeamAdapter(Activity context, List<Team> teams) {
        super();
        this.teams = teams;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_grup, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        loadImage = new LoadImage(context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Team team = teams.get(i);

        String strName = String.valueOf(team.getName());
        String strPoint = String.valueOf(team.getPoint());
        String strPhone = String.valueOf(team.getPhone());
        String strPhoto = String.valueOf(team.getPhoto());

        if (strName.equalsIgnoreCase("")||strName.equalsIgnoreCase("null")||strName.isEmpty()){
            viewHolder.tvNama.setText("-");
        } else {
            viewHolder.tvNama.setText(strName);
        }

        if (strPoint.equalsIgnoreCase("")||strPoint.equalsIgnoreCase("null")||strPoint.isEmpty()){
            viewHolder.tvPoint.setText("-");
        } else {
            viewHolder.tvPoint.setText(strPoint+"\nPoint");
        }

        if (strPhone.equalsIgnoreCase("")||strPhone.equalsIgnoreCase("null")||strPhone.isEmpty()){
            viewHolder.tvPhone.setText("-");
        } else {
            viewHolder.tvPhone.setText(strPhone);
        }

        loadImage.LoadImageCirclePicasso(strPhoto, viewHolder.ivItem, viewHolder.pbItem);

    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivItem;
        private ProgressBar pbItem;
        private TextView tvNama;
        private TextView tvPhone;
        private TextView tvPoint;

        public ViewHolder(View itemView) {
            super(itemView);
            ivItem = (ImageView)itemView.findViewById( R.id.iv_item );
            pbItem = (ProgressBar)itemView.findViewById( R.id.pb_item );
            tvNama = (TextView)itemView.findViewById( R.id.tv_nama );
            tvPhone = (TextView)itemView.findViewById( R.id.tv_phone );
            tvPoint = (TextView)itemView.findViewById( R.id.tv_point );
        }
    }


}
