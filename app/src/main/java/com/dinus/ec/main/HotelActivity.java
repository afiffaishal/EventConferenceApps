package com.dinus.ec.main;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dinus.ec.R;
import com.dinus.ec.adapter.HotelAdapter;
import com.dinus.ec.model.Hotel;
import com.dinus.ec.model.HotelItem;
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
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class HotelActivity extends BaseActivity {

    private TextView tvHotel;
    private RecyclerView rvHotel;
    private ProgressBar pbHotel;
    private View vError;
    private TextView tvErrorTitle;
    private TextView tvErrorContent;
    private HotelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        initToolbar(getResources().getString(R.string.ayana_resort));
        initUI();

        getHotel();
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
        tvHotel = (TextView)findViewById( R.id.tv_hotel );
        rvHotel = (RecyclerView)findViewById( R.id.rv_hotel );
        pbHotel = (ProgressBar)findViewById( R.id.pb_hotel );
        vError = findViewById( R.id.v_error );
        tvErrorTitle = (TextView)findViewById( R.id.tv_error_title );
        tvErrorContent = (TextView)findViewById( R.id.tv_error_content );
    }

    private void setAdapter(final List<HotelItem> hotelItems){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HotelActivity.this);
        rvHotel.setLayoutManager(layoutManager);

        adapter = new HotelAdapter(HotelActivity.this, hotelItems);
        rvHotel.setNestedScrollingEnabled(false);
        rvHotel.setAdapter(adapter);

        MenuClickRecyclerview.addTo(rvHotel).setOnItemClickListener(new MenuClickRecyclerview.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                HotelItem hotelItem = hotelItems.get(position);
                showDialogDetail(hotelItem);
            }
        });
    }

    private void getHotel(){
        vError.setVisibility(View.GONE);
        pbHotel.setVisibility(View.VISIBLE);
        tvHotel.setVisibility(View.GONE);

//        List<HotelItem> hotelItems = new ArrayList<>();
//
//        HotelItem hotelItem1 = new HotelItem(R.drawable.img_1, "Pool");
//        HotelItem hotelItem2 = new HotelItem(R.drawable.img_2, "Rock Bar");
//        HotelItem hotelItem3 = new HotelItem(R.drawable.img_3, "Rock Bar 2");
//        hotelItems.add(hotelItem1);
//        hotelItems.add(hotelItem2);
//        hotelItems.add(hotelItem3);
//
//        tvHotel.setText(getResources().getString(R.string.ayana_resort_desc));
//        setAdapter(hotelItems);

        APIInterface service = RestClient.getClient();
        final Gson gson = new Gson();

        Call<ResponseBody> call = service.hotelList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Danamon", "status code: " + response.code());
                if (response.isSuccessful()) {

                    pbHotel.setVisibility(View.GONE);

                    try {
                        String respon = response.body().string();
                        Log.d("Danamon", "response: " + respon);

                        JSONObject json = new JSONObject(respon);

                        int api_status = json.getInt("api_response");
                        String api_message = json.getString("api_message");
                        if (api_status == 1) {
                            Hotel hotel = gson.fromJson(json.toString(), Hotel.class);
                            tvHotel.setVisibility(View.VISIBLE);
                            tvHotel.setText(hotel.getNote());
                            setAdapter(hotel.getItems());
                            if (hotel.getItems().size()==0){
                                errorDataEmpty();
                            }
                        } else if (api_status == 0) {
                            errorData();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("Danamon", "error");
                    errorNetwork();
                    pbHotel.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Danamon", "failure: " + t.getLocalizedMessage());
                pbHotel.setVisibility(View.GONE);
                errorNetwork();
            }
        });
    }

    private void errorNetwork(){
        vError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText(getResources().getString(R.string.error_network_problem));
        tvErrorContent.setText(getResources().getString(R.string.btn_error));
        vError.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                vError.setVisibility(View.GONE);
                pbHotel.setVisibility(View.VISIBLE);
                getHotel();
            }
        });
    }

    private void errorData(){
        tvHotel.setVisibility(View.GONE);
        vError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText(getResources().getString(R.string.error_data_problem));
        tvErrorContent.setVisibility(View.GONE);
        vError.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {

            }
        });
    }

    private void errorDataEmpty(){
        tvHotel.setVisibility(View.GONE);
        vError.setVisibility(View.VISIBLE);
        tvErrorTitle.setText(getResources().getString(R.string.error_data_empty));
        tvErrorContent.setVisibility(View.GONE);
        vError.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {

            }
        });
    }

    private void showDialogDetail(HotelItem hotelItem) {
        Typeface face = Typeface.createFromAsset(getAssets(), CalligraphyConfig.get().getFontPath());
        new MaterialDialog.Builder(HotelActivity.this)
                .title(hotelItem.getTitle())
                .content(hotelItem.getDescription())
                .positiveText(R.string.ok)
                .typeface(face, face)
                .positiveColor(getResources().getColor(R.color.text_purple))
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
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
