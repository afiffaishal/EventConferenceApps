package com.dinus.ec.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.model.AgendaDateItem;

import java.util.List;

/**
 * Created by PHAP on 01/09/2016.
 */
public class AgendaDateAdapter extends RecyclerView.Adapter<AgendaDateAdapter.ViewHolder> {
    List<AgendaDateItem> agendaListItems;
    Activity context;

    public AgendaDateAdapter(Activity context, List<AgendaDateItem> programs) {
        super();
        this.agendaListItems = programs;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_agenda, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final AgendaDateItem agendaListItem = agendaListItems.get(i);

        String strAgenda = String.valueOf(agendaListItem.getTglView());

        viewHolder.tvItem.setText(strAgenda);
    }

    @Override
    public int getItemCount() {
        return agendaListItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }


}
