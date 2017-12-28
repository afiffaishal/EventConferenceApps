package com.dinus.ec.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.adapter.SongAdapter;
import com.dinus.ec.model.AgendaItem;
import com.dinus.ec.model.SongItem;
import com.dinus.ec.util.BaseActivity;
import com.dinus.ec.util.ConvertDate;
import com.dinus.ec.util.LoadImage;
import com.dinus.ec.util.MenuClickRecyclerview;
import com.dinus.ec.util.OnOneOffClickListener;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class AgendaDetailActivity extends BaseActivity {

    private CollapsingToolbarLayout ctlAgenda;
    private ImageView ivAgenda;
    private ProgressBar pbAgenda;
    private TextView tvName;
    private EditText etTempat;
    private EditText etWaktu;
//    private Button btnDownload;
//    private TextView tvKeterangan;
    private WebView wvAgenda;
    private View vNama;
    private AgendaItem agendaItem;
    private LoadImage loadImage;
    private View vNoSong;
    private View vSong;
    private RecyclerView rvSong;
    private ConvertDate convertDate = new ConvertDate();
    private SongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_detail);
        initUI();

        loadImage = new LoadImage(getApplicationContext());

        Bundle extrass = getIntent().getExtras();
        if (extrass!=null);{
            agendaItem = (AgendaItem) extrass.getSerializable("item");
            initToolbar(agendaItem.getTitle());
            setValue(agendaItem);
        }

    }

    private void initToolbar(String strTitle) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View viewActionBar = getLayoutInflater().inflate(R.layout.toolbar_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);

        TextView tvTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        tvTitle.setText(strTitle);
        tvTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvTitle.setFocusable(true);
        tvTitle.setFocusableInTouchMode(true);
        tvTitle.requestFocus();
        tvTitle.setSingleLine(true);
        tvTitle.setSelected(true);
        tvTitle.setMarqueeRepeatLimit(-1);

        getSupportActionBar().setCustomView(viewActionBar, params);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initUI() {
        ctlAgenda = (CollapsingToolbarLayout) findViewById(R.id.ctl_agenda);
        ivAgenda = (ImageView)findViewById( R.id.iv_agenda );
        pbAgenda = (ProgressBar)findViewById( R.id.pb_agenda );
        tvName = (TextView)findViewById( R.id.tv_name );
        etTempat = (EditText)findViewById( R.id.et_tempat );
        etWaktu = (EditText)findViewById( R.id.et_waktu );
//        btnDownload = (Button)findViewById( R.id.btn_download );
//        tvKeterangan = (TextView)findViewById( R.id.tv_keterangan );
        wvAgenda = (WebView)findViewById( R.id.wv_agenda );
        vNama = findViewById( R.id.v_nama );
        vSong = findViewById( R.id.v_song );
        vNoSong = findViewById( R.id.v_no_song );
        rvSong = (RecyclerView)findViewById( R.id.rv_lyric );

        int imageHeight = getWindowManager().getDefaultDisplay().getWidth();

        ViewGroup.LayoutParams layoutParams = ctlAgenda.getLayoutParams();
        layoutParams.height = imageHeight;
        layoutParams.width = imageHeight;

        ctlAgenda.setLayoutParams(layoutParams);
        vNoSong.setVisibility(View.GONE);
        vSong.setVisibility(View.GONE);
    }

    private void setValue(AgendaItem agendaItem){
        String strImage = String.valueOf(agendaItem.getImage());
        String strName = String.valueOf(agendaItem.getSpeaker());
        String strPlace = String.valueOf(agendaItem.getLocation());
        String strTime = "";
        String strTime2Show = String.valueOf(agendaItem.getDoubleTime());
        String strShowSong = String.valueOf(agendaItem.getShowSong());
        if (strTime2Show.equalsIgnoreCase("Yes")) {
            strTime = convertDate.getTimeAgenda(agendaItem.getTimeStart())
                    + "-" + convertDate.getTimeAgenda(agendaItem.getTimeEnd())  + " WITA & "+
                    convertDate.getTimeAgenda(agendaItem.getTimeStart2())
                    + "-" + convertDate.getTimeAgenda(agendaItem.getTimeEnd2()) + " WITA";
        } else {
            strTime = agendaItem.getTimeStart()
                    + "-" + agendaItem.getTimeEnd() +  " WITA";
        }
        String strDescription = String.valueOf(agendaItem.getDescription());
        final String strMateri = String.valueOf(agendaItem.getMateri());
        final String strSpeaker = String.valueOf(agendaItem.getSpeaker());

//        if (agendaItem.getMateriShow()==1){
//            tvKeterangan.setVisibility(View.VISIBLE);
//            btnDownload.setVisibility(View.VISIBLE);
//        } else if (agendaItem.getMateriShow()==0){
//            tvKeterangan.setVisibility(View.GONE);
//            btnDownload.setVisibility(View.GONE);
//        }
//
//        tvKeterangan.setText("*Materi akan diupload tanggal 21 Oktober 2017");

        if (strSpeaker.equalsIgnoreCase("-") || strSpeaker.equalsIgnoreCase("")|| strSpeaker.equalsIgnoreCase("null")){
            vNama.setVisibility(View.GONE);
        } else {
            vNama.setVisibility(View.VISIBLE);
        }

        if (strName.equalsIgnoreCase("")||strName.equalsIgnoreCase("null")||strName.isEmpty()){
            tvName.setText("-");
        } else {
            tvName.setText(strName);
        }

        if (strPlace.equalsIgnoreCase("")||strPlace.equalsIgnoreCase("null")||strPlace.isEmpty()){
            etTempat.setText("-");
        } else {
            etTempat.setText(strPlace);
        }

        etWaktu.setText(strTime);

        if (!strImage.isEmpty() || !strImage.equalsIgnoreCase("")) {
//            loadImage.LoadImagePicasso(strImage, ivAgenda, pbAgenda);
            pbAgenda.setVisibility(View.GONE);
            byte[] decodedString = Base64.decode(strImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivAgenda.setImageBitmap(decodedByte);
        }


        if (strShowSong.equalsIgnoreCase("yes")){
            vNoSong.setVisibility(View.GONE);
            vSong.setVisibility(View.VISIBLE);
//            setAdapter(agendaItem.getSongs());
        } else {
            vNoSong.setVisibility(View.VISIBLE);
            vSong.setVisibility(View.GONE);
            wvAgenda.loadData(strDescription, "text/html", "utf-8");
        }

//        btnDownload.setOnClickListener(new OnOneOffClickListener() {
//            @Override
//            public void onSingleClick(View v) {
//                if (strMateri.equalsIgnoreCase("")||strMateri.equalsIgnoreCase("null")||
//                        strMateri.isEmpty()){
//                    customToast().showToast(AgendaDetailActivity.this, "Materi not avaiable yet");
//                } else {
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strMateri));
//                    if (browserIntent.resolveActivity(getPackageManager()) != null) {
//                        startActivity(browserIntent);
//                    }else{
//                        customToast().showToast(AgendaDetailActivity.this, "URL not valid");
//                    }
//                }
//            }
//        });
    }

    private void setAdapter(final List<SongItem> songItems){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AgendaDetailActivity.this);
        rvSong.setLayoutManager(layoutManager);

        adapter = new SongAdapter(AgendaDetailActivity.this, songItems);
        rvSong.setNestedScrollingEnabled(false);
        rvSong.setAdapter(adapter);

        MenuClickRecyclerview.addTo(rvSong).setOnItemClickListener(new MenuClickRecyclerview.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                SongItem songItem = songItems.get(position);
                Intent intentSong = new Intent(AgendaDetailActivity.this, AgendaSongActivity.class);
                intentSong.putExtra("item", songItem);
                startActivity(intentSong);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }

}
