package com.dinus.ec.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.model.HomeMenuModel;
import com.dinus.ec.util.ScreenSize;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by PHAP on 01/09/2016.
 */
public class HomeMenuAdapter extends RecyclerView.Adapter<HomeMenuAdapter.ViewHolder> {
    List<HomeMenuModel> homeMenuModels;
    Activity context;

    public HomeMenuAdapter(Activity context, List<HomeMenuModel> programs) {
        super();
        this.homeMenuModels = programs;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_home, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final HomeMenuModel homeMenuModel = homeMenuModels.get(i);

        viewHolder.tvItem.setText(homeMenuModel.getTitle());

        int m220 = dpToPixel(context, 220);
        int square = ((new ScreenSize(context).getScreenHeight()-m220)/3);

        int gambar = 0;
        if(homeMenuModel.getIcon()!=0) {
            gambar = homeMenuModel.getIcon();
        }

        Picasso.with(context)
                .load(gambar)
                .into(viewHolder.ivItem);


        if (i%2==0){
            if (i==0){
                FrameLayout.LayoutParams itemParam = new FrameLayout.LayoutParams(square, square);
//                itemParam.setMargins(mSixteenDP, mSixteenDP, 0, 0);
                viewHolder.cvKarangan.setLayoutParams(itemParam);
            } else if ((homeMenuModels.size()%2!=0 && i==homeMenuModels.size()-1)||
                    (homeMenuModels.size()%2==0 && i==homeMenuModels.size()-2)){
                FrameLayout.LayoutParams itemParam = new FrameLayout.LayoutParams(square, square);
//                itemParam.setMargins(mSixteenDP, 0, 0, mSixteenDP);
                viewHolder.cvKarangan.setLayoutParams(itemParam);
            } else {
                FrameLayout.LayoutParams itemParam = new FrameLayout.LayoutParams(square, square);
//                itemParam.setMargins(mSixteenDP, 0, 0, 0);
                viewHolder.cvKarangan.setLayoutParams(itemParam);
            }
        } else {
            if (i==1){
                FrameLayout.LayoutParams itemParam = new FrameLayout.LayoutParams(square, square);
//                itemParam.setMargins(0, mSixteenDP, mSixteenDP, 0);
                viewHolder.cvKarangan.setLayoutParams(itemParam);
            } else if ((homeMenuModels.size()%2==0 && i==homeMenuModels.size()-2)){
                FrameLayout.LayoutParams itemParam = new FrameLayout.LayoutParams(square, square);
                //itemParam.setMargins(0, 0, mSixteenDP, mSixteenDP);
                viewHolder.cvKarangan.setLayoutParams(itemParam);
            } else {
                FrameLayout.LayoutParams itemParam = new FrameLayout.LayoutParams(square, square);
                //itemParam.setMargins(0, 0, mSixteenDP, 0);
                viewHolder.cvKarangan.setLayoutParams(itemParam);
            }
        }
    }

    @Override
    public int getItemCount() {
        return homeMenuModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cvKarangan;
        private View vItem;
        private ImageView ivItem;
        private TextView tvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            cvKarangan = (CardView)itemView.findViewById( R.id.cv_karangan );
            vItem = itemView.findViewById( R.id.v_item );
            ivItem = (ImageView)itemView.findViewById( R.id.iv_item );
            tvItem = (TextView)itemView.findViewById( R.id.tv_item );
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
