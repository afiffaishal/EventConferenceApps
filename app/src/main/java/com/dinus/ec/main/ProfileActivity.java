package com.dinus.ec.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.adapter.TeamAdapter;
import com.dinus.ec.model.Profile;
import com.dinus.ec.model.Team;
import com.dinus.ec.model.User;
import com.dinus.ec.service.API.APIInterface;
import com.dinus.ec.service.API.RestClient;
import com.dinus.ec.util.BaseActivity;
import com.dinus.ec.util.LoadImage;
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

public class ProfileActivity extends BaseActivity {

    private CoordinatorLayout vProfile;
    private CollapsingToolbarLayout ctlProfile;
    private ImageView ivProfile;
    private ProgressBar pbImageProfile;
    private TextView tvName;
    private TextView tvNoId;
    private TextView tvTglLahir;
    private TextView tvJabatan;
    private TextView tvPoin;
    private ProgressBar pbProfile;

    private View vError;
    private TextView tvErrorTitle;
    private TextView tvErrorContent;

    private TeamAdapter adapter;

    private LoadImage loadImage;
    private Profile profile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initToolbar(getResources().getString(R.string.my_profile));
        initUI();

        loadImage = new LoadImage(getApplicationContext());
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
        vProfile = (CoordinatorLayout)findViewById( R.id.v_profile );
        ctlProfile = (CollapsingToolbarLayout)findViewById( R.id.ctl_profile );
        ivProfile = (ImageView)findViewById( R.id.iv_profile );
        pbImageProfile = (ProgressBar)findViewById( R.id.pb_image_profile );
        tvName = (TextView)findViewById( R.id.tv_name );
        tvNoId = (TextView)findViewById( R.id.tv_no_id );
        tvTglLahir = (TextView)findViewById( R.id.tv_tgl_lahir );
        tvJabatan = (TextView)findViewById( R.id.tv_jabatan );
        tvPoin = (TextView)findViewById( R.id.tv_poin );
        pbProfile = (ProgressBar)findViewById( R.id.pb_profile );
        vError = findViewById( R.id.v_error );
        tvErrorTitle = (TextView)findViewById( R.id.tv_error_title );
        tvErrorContent = (TextView)findViewById( R.id.tv_error_content );

        int imageHeight = getWindowManager().getDefaultDisplay().getWidth();

        ViewGroup.LayoutParams layoutParams = ctlProfile.getLayoutParams();
        layoutParams.height = imageHeight;
        layoutParams.width = imageHeight;

        ctlProfile.setLayoutParams(layoutParams);
        setValue(getDb().getUser());
    }

    private void setValue(User value) {
        pbImageProfile.setVisibility(View.GONE);
        pbProfile.setVisibility(View.GONE);
        String strImage = String.valueOf(value.getPhoto());
        String strName = String.valueOf(value.getNama());
        String strJabatan = String.valueOf(value.getJabatan());
        String strID = String.valueOf(value.getNoid());
        String strPoin = String.valueOf(value.getPoin());
        String strTglLahir = String.valueOf(value.getTgllahir());

        if (!strImage.isEmpty() || !strImage.equalsIgnoreCase("")) {
            pbProfile.setVisibility(View.GONE);
            byte[] decodedString = Base64.decode(strImage, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivProfile.setImageBitmap(decodedByte);
        }
        tvName.setText(strName);
        tvNoId.setText(": "+strID);
        tvPoin.setText(": "+strPoin);
        tvJabatan.setText(": "+strJabatan);
        tvTglLahir.setText(": "+strTglLahir);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.profile, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:

                Intent intent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                if (this.profile!=null) {
                    intent.putExtra("profile", profile);
                    startActivity(intent);
                } else {
                    customToast().showToast(ProfileActivity.this, getResources().getString(R.string.error_data_problem));
                }

                break;
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }
}
