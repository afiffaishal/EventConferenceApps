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
import com.dinus.ec.adapter.GalleryAdapter;
import com.dinus.ec.model.Gallery;
import com.dinus.ec.model.GalleryDateItem;
import com.dinus.ec.model.GalleryItem;
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

public class GallerySubActivity extends BaseActivity {

    private RecyclerView rvSubGallery;
    private ProgressBar pbSubGallerry;
    private View vError;
    private TextView tvErrorTitle;
    private TextView tvErrorContent;
    private GalleryAdapter adapter;

    private GalleryDateItem galleryDateItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_sub);

        initUI();

        Bundle extrass = getIntent().getExtras();
        if (extrass != null) ;
        {
            galleryDateItem = (GalleryDateItem) extrass.getSerializable("item");
            initToolbar(galleryDateItem.getTglView());
            getGallery(String.valueOf(galleryDateItem.getId()));
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
        rvSubGallery = (RecyclerView) findViewById(R.id.rv_sub_gallery);
        pbSubGallerry = (ProgressBar) findViewById(R.id.pb_sub_gallerry);
        vError = findViewById(R.id.v_error);
        tvErrorTitle = (TextView) findViewById(R.id.tv_error_title);
        tvErrorContent = (TextView) findViewById(R.id.tv_error_content);
    }

    private void setAdapter(final List<GalleryItem> galleryItems) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(GallerySubActivity.this);
        rvSubGallery.setLayoutManager(layoutManager);

        adapter = new GalleryAdapter(GallerySubActivity.this, galleryItems);
        rvSubGallery.setNestedScrollingEnabled(false);
        rvSubGallery.setAdapter(adapter);

        MenuClickRecyclerview.addTo(rvSubGallery).setOnItemClickListener(new MenuClickRecyclerview.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                GalleryItem galleryItem = galleryItems.get(position);
                Intent intent = new Intent(GallerySubActivity.this, GalleryDetailActivity.class);
                intent.putExtra("item", galleryItem);
                startActivity(intent);
            }
        });
    }

    private void getGallery(String strID) {
        vError.setVisibility(View.GONE);
        pbSubGallerry.setVisibility(View.VISIBLE);

//        Gallery gallery = gson.fromJson(json.toString(), Gallery.class);
        try {
            setAdapter(getDb().galleryItems());
            if (getDb().galleryItems().size() == 0) {
                errorDataEmpty();
            }
        } catch (NullPointerException e){
            errorDataEmpty();
            e.printStackTrace();
        }


//        APIInterface service = RestClient.getClient();
//        final Gson gson = new Gson();
//
//        Call<ResponseBody> call = service.gallerylist(strID);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.d("Danamon", "status code: " + response.code());
//                if (response.isSuccessful()) {
//
//                    pbSubGallerry.setVisibility(View.GONE);
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
//                            Gallery gallery = gson.fromJson(json.toString(), Gallery.class);
//                            setAdapter(gallery.getItems());
//                            if (gallery.getItems().size()==0){
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
//                    pbSubGallerry.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d("Danamon", "failure: " + t.getLocalizedMessage());
//                pbSubGallerry.setVisibility(View.GONE);
//                errorNetwork();
//            }
//        });
    }

    private void errorNetwork() {
        vError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText(getResources().getString(R.string.error_network_problem));
        tvErrorContent.setText(getResources().getString(R.string.btn_error));
        vError.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                vError.setVisibility(View.GONE);
                pbSubGallerry.setVisibility(View.VISIBLE);
                getGallery(String.valueOf(galleryDateItem.getId()));
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
