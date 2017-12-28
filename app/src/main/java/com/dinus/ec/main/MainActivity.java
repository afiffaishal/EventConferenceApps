package com.dinus.ec.main;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.adapter.HomeMenuAdapter;
import com.dinus.ec.model.HomeMenuModel;
import com.dinus.ec.model.User;
import com.dinus.ec.util.BaseActivity;
import com.dinus.ec.util.MenuClickRecyclerview;
import com.dinus.ec.util.OnOneOffClickListener;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends BaseActivity {

    private TextView tvName;
//    private TextView tvClass;
//    private TextView tvGrup;
    private TextView tvPoint;
    private RecyclerView rvHome;
    private View btnScan;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private AlphaInAnimationAdapter alphaAdapter;

    private ArrayList<HomeMenuModel> homeMenuModels = new ArrayList<HomeMenuModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extrass = getIntent().getExtras();
        if (extrass!=null);{
            String strFrom = extrass.getString("from");
            if (strFrom.equalsIgnoreCase("notif")){
                String strContent = extrass.getString("content");
                showDialogNotification(strContent);
            }
        }

        initUI();
        initToolbar(getResources().getString(R.string.damamon_sales_leader));
    }

    private void initToolbar(String strTitle) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View viewActionBar = getLayoutInflater().inflate(R.layout.toolbar_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.LEFT);

        TextView tvTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        tvTitle.setText(strTitle);

        getSupportActionBar().setCustomView(viewActionBar, params);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initUI() {
        tvName = (TextView)findViewById( R.id.tv_name );
        tvPoint = (TextView)findViewById( R.id.tv_point );
        rvHome = (RecyclerView)findViewById( R.id.rv_home );
        btnScan = findViewById( R.id.btn_scan );

        getMenu();

        btnScan.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(MainActivity.this, QRcodeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setAdapter(ArrayList<HomeMenuModel> menuModels){
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rvHome.setLayoutManager(layoutManager);
        rvHome.setHasFixedSize(true);
        rvHome.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        adapter = new HomeMenuAdapter(MainActivity.this, menuModels);
        alphaAdapter = new AlphaInAnimationAdapter(adapter);
        rvHome.setAdapter(alphaAdapter);

        MenuClickRecyclerview.addTo(rvHome).setOnItemClickListener(new MenuClickRecyclerview.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                switch (position){
                    case 0:
                        Intent intentProfile = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intentProfile);
                        break;
                    case 1:
                        Intent intentAgenda = new Intent(MainActivity.this, AgendaActivity.class);
                        startActivity(intentAgenda);
                        break;
                    case 2:
                        Intent intentMaps = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(intentMaps);
                        break;
//                    case 3:
//                        Intent intentTopRank = new Intent(MainActivity.this, TopRankActivity.class);
//                        startActivity(intentTopRank);
//                        break;
                    default:
                        break;
                }
            }
        });

    }

    public void getMenu(){
        HomeMenuModel itemProfile = new HomeMenuModel(0, R.drawable.ic_profile_white, "My Profile");
        homeMenuModels.add(itemProfile);
        HomeMenuModel itemAgenda = new HomeMenuModel(1, R.drawable.ic_agenda_white, "Agenda");
        homeMenuModels.add(itemAgenda);
        HomeMenuModel itemMaps = new HomeMenuModel(2, R.drawable.ic_map_white, "Maps");
        homeMenuModels.add(itemMaps);
//        HomeMenuModel itemTop = new HomeMenuModel(3, R.drawable.ic_rank_white, "Top Rank");
//        homeMenuModels.add(itemTop);
        setAdapter(homeMenuModels);
    }

    public void showDialogNotification(String strContent){
        final Dialog dialogNotif = new Dialog(MainActivity.this);
        dialogNotif.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogNotif.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogNotif.setContentView(R.layout.dialog_notification);
        dialogNotif.setCancelable(true);
        TextView tvContent = (TextView) dialogNotif.findViewById(R.id.tv_content);
        Button btnOk = (Button)  dialogNotif.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View view) {
                dialogNotif.dismiss();
            }
        });
        tvContent.setText(strContent);
        dialogNotif.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = getDb().getUser();

        tvName.setText(user.getNama());
        tvPoint.setText("Your Point "+user.getPoin());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                //customToast().showToast(MainActivity.this, "Help");
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

}
