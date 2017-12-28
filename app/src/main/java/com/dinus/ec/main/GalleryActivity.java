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
import com.dinus.ec.adapter.GalleryDateAdapter;
import com.dinus.ec.model.GalleryDate;
import com.dinus.ec.model.GalleryDateItem;
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

public class GalleryActivity extends BaseActivity {

    private RecyclerView rvGallery;
    private ProgressBar pbGallerry;
    private View vError;
    private TextView tvErrorTitle;
    private TextView tvErrorContent;
    private GalleryDateAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_list);
        initToolbar(getResources().getString(R.string.gallery));
        initUI();

        getGallery();
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
        rvGallery = (RecyclerView) findViewById(R.id.rv_gallery);
        pbGallerry = (ProgressBar) findViewById(R.id.pb_gallerry);
        vError = findViewById(R.id.v_error);
        tvErrorTitle = (TextView) findViewById(R.id.tv_error_title);
        tvErrorContent = (TextView) findViewById(R.id.tv_error_content);
    }

    private void setAdapter(final List<GalleryDateItem> galleryDateItems) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GalleryActivity.this);
        rvGallery.setLayoutManager(layoutManager);

        adapter = new GalleryDateAdapter(GalleryActivity.this, galleryDateItems);
        rvGallery.setNestedScrollingEnabled(false);
        rvGallery.setAdapter(adapter);

        MenuClickRecyclerview.addTo(rvGallery).setOnItemClickListener(new MenuClickRecyclerview.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                GalleryDateItem galleryDateItem = galleryDateItems.get(position);
                Intent intent = new Intent(GalleryActivity.this, GallerySubActivity.class);
                intent.putExtra("item", galleryDateItem);
                startActivity(intent);
            }
        });
    }

    private void getGallery() {
        vError.setVisibility(View.GONE);
        pbGallerry.setVisibility(View.VISIBLE);
        try {
            setAdapter(getDb().galleryDateItems());
            if (getDb().galleryDateItems().size() == 0) {
                errorDataEmpty();
            }
        } catch (NullPointerException e){
            errorDataEmpty();
            e.printStackTrace();
        }
//        APIInterface service = RestClient.getClient();
//        final Gson gson = new Gson();
//
//        Call<ResponseBody> call = service.gallerydate();
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.d("Danamon", "status code: " + response.code());
//                if (response.isSuccessful()) {
//
//                    pbGallerry.setVisibility(View.GONE);
//
//                    try {
//                        String respon = response.body().string();
//                        Log.d("Danamon", "response: " + respon);
//
//                        JSONObject json = new JSONObject(respon);
//
//                        int api_status = json.getInt("api_response");
//                        String api_message = json.getString("api_message");
//                        if (api_status == 1) {
//                            GalleryDate agendaDate = gson.fromJson(json.toString(), GalleryDate.class);
//                            setAdapter(agendaDate.getItems());
//                            if (agendaDate.getItems().size()==0){
//                                errorDataEmpty();
//                            }
//                        } else if (api_status == 0) {
//                            errorData();
//                        }
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Log.d("Danamon", "error");
//                    errorNetwork();
//                    pbGallerry.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d("Danamon", "failure: " + t.getLocalizedMessage());
//                pbGallerry.setVisibility(View.GONE);
//                errorNetwork();
//            }
//        });
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

    private void errorNetwork() {
        vError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText(getResources().getString(R.string.error_network_problem));
        tvErrorContent.setText(getResources().getString(R.string.btn_error));
        vError.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                vError.setVisibility(View.GONE);
                pbGallerry.setVisibility(View.VISIBLE);
                getGallery();
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
