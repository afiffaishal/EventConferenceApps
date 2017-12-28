package com.dinus.ec.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.adapter.AgendaAdapter;
import com.dinus.ec.model.Agenda;
import com.dinus.ec.model.AgendaDateItem;
import com.dinus.ec.model.AgendaItem;
import com.dinus.ec.service.API.APIInterface;
import com.dinus.ec.service.API.RestClient;
import com.dinus.ec.util.BaseActivity;
import com.dinus.ec.util.MenuClickRecyclerview;
import com.dinus.ec.util.OnOneOffClickListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendaSubActivity extends BaseActivity {

    private RecyclerView rvSubAgenda;
    private ProgressBar pbSubAgenda;
    private View vError;
    private TextView tvErrorTitle;
    private TextView tvErrorContent;
    private AgendaAdapter adapter;

    private AgendaDateItem agendaDateItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_sub);
        initUI();

        Bundle extrass = getIntent().getExtras();
        if (extrass != null) ;
        {
            agendaDateItem = (AgendaDateItem) extrass.getSerializable("item");
            initToolbar(agendaDateItem.getTglView());
            getAgenda(agendaDateItem.getTgl());
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

        getSupportActionBar().setCustomView(viewActionBar, params);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initUI() {
        rvSubAgenda = (RecyclerView) findViewById(R.id.rv_sub_agenda);
        pbSubAgenda = (ProgressBar) findViewById(R.id.pb_sub_agenda);
        vError = findViewById(R.id.v_error);
        tvErrorTitle = (TextView) findViewById(R.id.tv_error_title);
        tvErrorContent = (TextView) findViewById(R.id.tv_error_content);
    }

    private void setAdapter(final List<AgendaItem> agendaItems) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AgendaSubActivity.this);
        rvSubAgenda.setLayoutManager(layoutManager);

        adapter = new AgendaAdapter(AgendaSubActivity.this, agendaItems);
        rvSubAgenda.setNestedScrollingEnabled(false);
        rvSubAgenda.setAdapter(adapter);

        MenuClickRecyclerview.addTo(rvSubAgenda).setOnItemClickListener(new MenuClickRecyclerview.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                AgendaItem agendaItem = agendaItems.get(position);
                Intent intent = new Intent(AgendaSubActivity.this, AgendaDetailActivity.class);
                intent.putExtra("item", agendaItem);
                startActivity(intent);
            }
        });
    }

    private void getAgenda(String strTGL) {
        vError.setVisibility(View.GONE);
        pbSubAgenda.setVisibility(View.GONE);

        getDb().deleteAgenda();

        AgendaItem agendaItem1 = new AgendaItem();
        agendaItem1.setDescription("Saat para peserta datang, cek in terlebih dahulu untuk melakukan registrasi ulang lalu dilanjutkan ke kamar yang sudah disediakan untukmenaruh barang bawaan. Setelah itu ke ruang makan untuk sarapan yag telah disediakan.");
        agendaItem1.setLocation("Hall Food and Restoran");
        agendaItem1.setTimeStart("07.00");
        agendaItem1.setTimeEnd("09.00");
        agendaItem1.setDate("28 Desember 2017");
        agendaItem1.setDoubleTime("");
        agendaItem1.setShowSong("0");
        agendaItem1.setImage(getStringImage(getResources().getDrawable(R.drawable.check_in_and_breakfast)));
        agendaItem1.setTitle("Agenda registrasi ulang dan sarapan");
        agendaItem1.setTgl("281217");

        AgendaItem agendaItem2 = new AgendaItem();
        agendaItem2.setSpeaker("Afif Faishal (CEO. Make Event.com)");
        agendaItem2.setDescription("Acara berupa kenferensi dan seminar yang telah disajikan dengan materi – materi khusus yang sangat bermanfaat oleh Bapak Afif Faishal Founder dari MakeEvent.com.");
        agendaItem2.setLocation("BallRoom");
        agendaItem2.setTimeStart("09.00");
        agendaItem2.setTimeEnd("12.00");
        agendaItem2.setDate("28 Desember 2017");
        agendaItem2.setDoubleTime("");
        agendaItem2.setShowSong("0");
        agendaItem2.setImage(getStringImage(getResources().getDrawable(R.drawable.conference_and_seminar)));
        agendaItem2.setTgl("281217");
        agendaItem2.setTitle("Aktifitas berupa conferensi dan seminar.");

        AgendaItem agendaItem3 = new AgendaItem();
        agendaItem3.setSpeaker("");
        agendaItem3.setDescription("Istirahat peserta dapat melakukan aktifitas seperti Sholat, Makan Siang dan lainnya.");
        agendaItem3.setLocation("Hall Food and Restoran");
        agendaItem3.setTimeStart("12.00");
        agendaItem3.setTimeEnd("13.00");
        agendaItem3.setDate("28 Desember 2017");
        agendaItem3.setDoubleTime("");
        agendaItem3.setShowSong("0");
        agendaItem3.setImage(getStringImage(getResources().getDrawable(R.drawable.lunch_break)));
        agendaItem3.setTgl("281217");
        agendaItem3.setTitle("Acara berupa istirahat, Sholat dan Makan Siang.");

        AgendaItem agendaItem4 = new AgendaItem();
        agendaItem4.setDate("");
        agendaItem4.setSpeaker("");
        agendaItem4.setDescription("Para peserta bebas melakukan kegiatan yang diinginka seperti Games, " +
                "Challenge ataupun kompetisi – kompetisi yang sangat menarik. Siapkan Handphone anda untuk " +
                "menscan Code sebagai syarat untuk mendapatkan poin. Peserta dengan poin tertinggi akan mendapatkan hadiah menarik.");
        agendaItem4.setLocation("Halaman Belakang Hotel & Hall B");
        agendaItem4.setTimeStart("13.00");
        agendaItem4.setTimeEnd("16.00");
        agendaItem4.setDate("28 Desember 2017");
        agendaItem4.setDoubleTime("");
        agendaItem4.setShowSong("0");
        agendaItem4.setImage(getStringImage(getResources().getDrawable(R.drawable.games_challenge_and_competition)));
        agendaItem4.setTgl("281217");
        agendaItem4.setTitle("Agenda inti berupa Games, Challenge, dan Competition.");

        AgendaItem agendaItem5 = new AgendaItem();
        agendaItem5.setDate("");
//        agendafile://localhost/Users/crocodic-mbp-9/Downloads/Telegram%20Desktop/tugas%20kuliah.txtItem5.setDescription("Saat para peserta selesai dengan acara inti, dapat melakukan aktifitas bebas untuk menunggu sampai acara Gala Dinner");
        agendaItem5.setLocation("Pantai Indah Kapuk");
        agendaItem5.setTimeStart("16.00");
        agendaItem5.setTimeEnd("18.00");
        agendaItem5.setDate("28 Desember 2017");
        agendaItem5.setDoubleTime("");
        agendaItem5.setShowSong("0");
        agendaItem5.setImage(getStringImage(getResources().getDrawable(R.drawable.free_activity)));
        agendaItem5.setTgl("281217");
        agendaItem5.setTitle("Acara bebas ingin melakukan apa saja");

//Gala Dinner
        AgendaItem agendaItem6 = new AgendaItem();
        agendaItem6.setDate("");
        agendaItem6.setDescription("Peserta dapat ke bagian hall Food untuk makan malam bersama dengan aneka ragam masakan yang tentunya menggoda selera");
        agendaItem6.setLocation("Hall Food and Restoran");
        agendaItem6.setTimeStart("18.00");
        agendaItem6.setTimeEnd("20.00");
        agendaItem6.setDate("28 Desember 2017");
        agendaItem6.setDoubleTime("");
        agendaItem6.setShowSong("0");
        agendaItem6.setImage(getStringImage(getResources().getDrawable(R.drawable.gala_dinner)));
        agendaItem6.setTgl("281217");
        agendaItem6.setTitle("Agenda berupa makan malam bersama");

//Concert Music, Musisinya: Payung Teduh, Tulus, Raisa, and Glen Fredly
        AgendaItem agendaItem7 = new AgendaItem();
        agendaItem7.setDate("");
        agendaItem7.setDescription("Saat para peserta datang, cek in terlebih dahulu untuk melakukan registrasi ulang lalu dilanjutkan ke kamar yang sudah disediakan untukmenaruh barang bawaan. Setelah itu ke ruang makan untuk sarapan yag telah disediakan");
        agendaItem7.setLocation("Hall C");
        agendaItem7.setTimeStart("20.00");
        agendaItem7.setTimeEnd("23.00");
        agendaItem7.setDate("28 Desember 2017");
        agendaItem7.setDoubleTime("");
        agendaItem7.setShowSong("0");
        agendaItem7.setImage(getStringImage(getResources().getDrawable(R.drawable.concert_music)));
        agendaItem7.setTgl("281217");
        agendaItem7.setTitle("Acara penutup yakni konser event conference");

//End Event
        AgendaItem agendaItem8 = new AgendaItem();
        agendaItem8.setDate("");
        agendaItem8.setDescription("Setelah semua acara selesai para peserta dapat kembali ke kamar masing - masing");
        agendaItem8.setLocation("Back to Room Hotel");
        agendaItem8.setTimeStart("23.00");
        agendaItem8.setTimeEnd("00.00");
        agendaItem8.setDate("28 Desember 2017");
        agendaItem8.setDoubleTime("");
        agendaItem8.setShowSong("0");
        agendaItem8.setImage(getStringImage(getResources().getDrawable(R.drawable.end_event)));
        agendaItem8.setTgl("281217");
        agendaItem8.setTitle("Acara selesai");

//Check Out
        AgendaItem agendaItem9 = new AgendaItem();
        agendaItem9.setDate("");
        agendaItem9.setDescription("Peserta dapat melakukan Check out hotel untuk pulang ke rumah masing - masing");
        agendaItem9.setLocation("Lobby Utama");
        agendaItem9.setTimeStart("07.00");
        agendaItem9.setTimeEnd("09.00");
        agendaItem9.setDate("29 Desember 2017");
        agendaItem9.setDoubleTime("");
        agendaItem9.setShowSong("0");
        agendaItem9.setImage(getStringImage(getResources().getDrawable(R.drawable.check_out)));
        agendaItem9.setTgl("291217");
        agendaItem9.setTitle("Peserta melakukan Check Out Hotel");

        getDb().saveObject(agendaItem1);
        getDb().saveObject(agendaItem2);
        getDb().saveObject(agendaItem3);
        getDb().saveObject(agendaItem4);
        getDb().saveObject(agendaItem5);
        getDb().saveObject(agendaItem6);
        getDb().saveObject(agendaItem7);
        getDb().saveObject(agendaItem8);
        getDb().saveObject(agendaItem9);

        try {
            setAdapter(getDb().agendaItems(strTGL));
            if (getDb().agendaItems(strTGL).size() == 0) {
                errorDataEmpty();
            }
        } catch (NullPointerException e){
            errorDataEmpty();
            e.printStackTrace();
        }
    }

    public String getStringImage(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void errorNetwork() {
        vError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText(getResources().getString(R.string.error_network_problem));
        tvErrorContent.setText(getResources().getString(R.string.btn_error));
        vError.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                vError.setVisibility(View.GONE);
                pbSubAgenda.setVisibility(View.VISIBLE);
                getAgenda(agendaDateItem.getTgl());
            }
        });
    }

    private void errorData() {
        vError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText(getResources().getString(R.string.error_data_problem));
        tvErrorContent.setVisibility(View.GONE);
        vError.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {

            }
        });
    }

    private void errorDataEmpty() {
        vError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText(getResources().getString(R.string.error_data_empty));
        tvErrorContent.setVisibility(View.GONE);
        vError.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {

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
