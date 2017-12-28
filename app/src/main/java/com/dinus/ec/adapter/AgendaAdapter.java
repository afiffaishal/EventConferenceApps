package com.dinus.ec.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.model.AgendaItem;
import com.dinus.ec.util.ConvertDate;
import com.dinus.ec.util.LoadImage;

import java.util.List;

/**
 * Created by PHAP on 01/09/2016.
 */
public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ViewHolder> {
    List<AgendaItem> agendaListItems;
    Activity context;
    LoadImage loadImage;
    ConvertDate convertDate = new ConvertDate();

    public AgendaAdapter(Activity context, List<AgendaItem> programs) {
        super();
        this.agendaListItems = programs;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_sub_agenda, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        loadImage = new LoadImage(context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final AgendaItem agendaListItem = agendaListItems.get(i);

        String strAgenda = agendaListItem.getTitle();//"\n"+"by "+agendaListItem.getSpeaker();
        String strVenue = "Venue : "+agendaListItem.getLocation();
        String strTime = "";
        String strTime2Show = String.valueOf(agendaListItem.getDoubleTime());
        if (strTime2Show.equalsIgnoreCase("Yes")) {
            strTime = //convertDate.getTimeAgenda(
                    agendaListItem.getTimeStart()
//            )
                    + "-" + convertDate.getTimeAgenda(agendaListItem.getTimeEnd()) + "\n" + "WITA"+"\n\n"+
            convertDate.getTimeAgenda(agendaListItem.getTimeStart2())
                    + "-" + convertDate.getTimeAgenda(agendaListItem.getTimeEnd2()) + "\n" + "WITA";
        } else {
            strTime = agendaListItem.getTimeStart() + "-" + agendaListItem.getTimeEnd() + "\n" + "WITA";
        }
        String strPhoto = agendaListItem.getImage();

        if (strAgenda.equalsIgnoreCase("")||strAgenda.equalsIgnoreCase("null")||strAgenda.isEmpty()){
            viewHolder.tvTopic.setText("-");
        } else {
            viewHolder.tvTopic.setText(strAgenda);
        }

        if (strVenue.equalsIgnoreCase("")||strVenue.equalsIgnoreCase("null")||strVenue.isEmpty()){
            viewHolder.tvVenue.setText("-");
        } else {
            viewHolder.tvVenue.setText(strVenue);
        }

        viewHolder.tvWaktu.setText(strTime);

//        loadImage.LoadImagePicasso(strPhoto, viewHolder.ivItem, viewHolder.pbItem);
        viewHolder.pbItem.setVisibility(View.GONE);
        byte[] decodedString = Base64.decode(strPhoto, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        viewHolder.ivItem.setImageBitmap(decodedByte);
    }

    @Override
    public int getItemCount() {
        return agendaListItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivItem;
        private ProgressBar pbItem;
        private TextView tvTopic;
        private TextView tvVenue;
        private TextView tvWaktu;

        public ViewHolder(View itemView) {
            super(itemView);
            ivItem = (ImageView)itemView.findViewById( R.id.iv_item );
            pbItem = (ProgressBar)itemView.findViewById( R.id.pb_item );
            tvTopic = (TextView)itemView.findViewById( R.id.tv_topic );
            tvVenue = (TextView)itemView.findViewById( R.id.tv_venue );
            tvWaktu = (TextView)itemView.findViewById( R.id.tv_waktu );
        }
    }


}
