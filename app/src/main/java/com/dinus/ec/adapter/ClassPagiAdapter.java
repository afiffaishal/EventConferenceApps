package com.dinus.ec.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.model.Class;

import java.util.List;

/**
 * Created by PHAP on 01/09/2016.
 */
public class ClassPagiAdapter extends RecyclerView.Adapter<ClassPagiAdapter.ViewHolder> {
    public List<Class> list;
    Activity context;
    private boolean first = true;

    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;

    public ClassPagiAdapter(Activity context, List<Class> list) {
        super();
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_class, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        final Class item = list.get(i);

        String strTopic = "Topic : "+item.getName();
        String strSpeaker = "Speakers : "+item.getSpeaker();
        String strRoom = "Room : "+item.getRoom();
        String strAvaiable = "Room Capacity : "+String.valueOf(item.getAvailable())+" Kursi";

        viewHolder.tvTopic.setText(strTopic);
        viewHolder.tvSpeakers.setText(strSpeaker);
        viewHolder.tvRoom.setText(strRoom);
        viewHolder.tvCapacity.setText(strAvaiable);

        if (first){
            lastCheckedPos= -1;
            if (list.get(i).isChecked()){
                viewHolder.rbClass.setChecked(true);
                list.get(i).setChecked(true);
            } else {
                viewHolder.rbClass.setChecked(false);
                list.get(i).setChecked(false);
            }
        } else {
            if (i==lastCheckedPos){
                viewHolder.rbClass.setChecked(true);
                list.get(i).setChecked(true);
            } else {
                viewHolder.rbClass.setChecked(false);
                list.get(i).setChecked(false);
            }
        }

    }

    public void setSelected (int posisi){
        lastCheckedPos = posisi;
        first = false;
    }

    public Class getSelectedItem() {
        if (lastCheckedPos != -1) {
            return list.get(lastCheckedPos);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RadioButton rbClass;
        private TextView tvTopic;
        private TextView tvSpeakers;
        private TextView tvRoom;
        private TextView tvCapacity;

        public ViewHolder(View itemView) {
            super(itemView);
            rbClass = (RadioButton)itemView.findViewById( R.id.rb_class );
            rbClass = (RadioButton)itemView.findViewById( R.id.rb_class );
            tvTopic = (TextView)itemView.findViewById( R.id.tv_topic );
            tvSpeakers = (TextView)itemView.findViewById( R.id.tv_speakers );
            tvRoom = (TextView)itemView.findViewById( R.id.tv_room );
            tvCapacity = (TextView)itemView.findViewById( R.id.tv_capacity );
        }
    }

}
