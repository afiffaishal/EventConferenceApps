package com.dinus.ec.main;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.adapter.AgendaDateAdapter;
import com.dinus.ec.model.AgendaDate;
import com.dinus.ec.model.AgendaDateItem;
import com.dinus.ec.service.API.APIInterface;
import com.dinus.ec.service.API.RestClient;
import com.dinus.ec.util.BaseActivity;
import com.dinus.ec.util.MenuClickRecyclerview;
import com.dinus.ec.util.OnOneOffClickListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendaActivity extends BaseActivity {

    private RecyclerView rvAgenda;
    private ProgressBar pbAgenda;
    private View vError;
    private TextView tvErrorTitle;
    private TextView tvErrorContent;
    private AgendaDateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        initToolbar(getResources().getString(R.string.agenda));
        initUI();

        getAgenda();
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
        rvAgenda = (RecyclerView) findViewById(R.id.rv_agenda);
        pbAgenda = (ProgressBar) findViewById(R.id.pb_agenda);
        vError = findViewById(R.id.v_error);
        tvErrorTitle = (TextView) findViewById(R.id.tv_error_title);
        tvErrorContent = (TextView) findViewById(R.id.tv_error_content);
    }

    private void setAdapter(final List<AgendaDateItem> agendaDateItems) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AgendaActivity.this);
        rvAgenda.setLayoutManager(layoutManager);

        adapter = new AgendaDateAdapter(AgendaActivity.this, agendaDateItems);
        rvAgenda.setNestedScrollingEnabled(false);
        rvAgenda.setAdapter(adapter);

        MenuClickRecyclerview.addTo(rvAgenda).setOnItemClickListener(new MenuClickRecyclerview.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                AgendaDateItem agendaDateItem = agendaDateItems.get(position);
                Intent intent = new Intent(AgendaActivity.this, AgendaSubActivity.class);
                intent.putExtra("item", agendaDateItem);
                startActivity(intent);
            }
        });
    }

    private void getAgenda() {
        vError.setVisibility(View.GONE);
        pbAgenda.setVisibility(View.GONE);

        getDb().deleteAgendaDate();

        AgendaDateItem agendaDateItem1 = new AgendaDateItem();
        agendaDateItem1.setId(0);
        agendaDateItem1.setTgl("281217");
        agendaDateItem1.setTglView("28 Desember 2017");
        getDb().saveObject(agendaDateItem1);

        AgendaDateItem agendaDateItem2 = new AgendaDateItem();
        agendaDateItem2.setId(0);
        agendaDateItem2.setTgl("291217");
        agendaDateItem2.setTglView("29 Desember 2017");
        getDb().saveObject(agendaDateItem2);

        try {
            setAdapter(getDb().agendaDateItems());
            if (getDb().agendaDateItems().size() == 0) {
                errorDataEmpty();
            }
        } catch (NullPointerException e){
            errorDataEmpty();
            e.printStackTrace();
        }

    }

    private void errorNetwork() {
        vError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText(getResources().getString(R.string.error_network_problem));
        tvErrorContent.setText(getResources().getString(R.string.btn_error));
        vError.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                vError.setVisibility(View.GONE);
                pbAgenda.setVisibility(View.VISIBLE);
                getAgenda();
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
