package com.dinus.ec.main;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.adapter.GalleryPhotosAdapter;
import com.dinus.ec.main.fragment.GalleryShowImageFragment;
import com.dinus.ec.model.GalleryItem;
import com.dinus.ec.model.GalleryPhoto;
import com.dinus.ec.util.BaseActivity;
import com.dinus.ec.util.MenuClickRecyclerview;

import java.io.Serializable;
import java.util.List;

public class GalleryDetailActivity extends BaseActivity {

    private RecyclerView rvGallery;
    private ProgressBar pbGallerry;
    private View vError;
    private TextView tvErrorTitle;
    private TextView tvErrorContent;
    private GalleryPhotosAdapter adapter;

    private GalleryItem galleryItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        initUI();
        initToolbar(getResources().getString(R.string.gallery));

        Bundle extrass = getIntent().getExtras();
        if (extrass!=null);{
            galleryItem = (GalleryItem) extrass.getSerializable("item");
            setAdapter(galleryItem.getPhotos());
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
        rvGallery = (RecyclerView)findViewById( R.id.rv_gallery );
        pbGallerry = (ProgressBar)findViewById( R.id.pb_gallerry );
        vError = findViewById( R.id.v_error );
        tvErrorTitle = (TextView)findViewById( R.id.tv_error_title );
        tvErrorContent = (TextView)findViewById( R.id.tv_error_content );
    }

    private void setAdapter(final List<GalleryPhoto> galleryPhotos){
        pbGallerry.setVisibility(View.GONE);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(GalleryDetailActivity.this, 3);
        rvGallery.setLayoutManager(layoutManager);
//        final List<GalleryPhoto> galleryPhotos1 = new ArrayList<>();
//        galleryPhotos1.addAll(galleryPhotos);
//        galleryPhotos1.addAll(galleryPhotos);
//        galleryPhotos1.addAll(galleryPhotos);
//        galleryPhotos1.addAll(galleryPhotos);
//        galleryPhotos1.addAll(galleryPhotos);
//        galleryPhotos1.addAll(galleryPhotos);
//        galleryPhotos1.addAll(galleryPhotos);
//        galleryPhotos1.addAll(galleryPhotos);
//        galleryPhotos1.addAll(galleryPhotos);
        adapter = new GalleryPhotosAdapter(GalleryDetailActivity.this, galleryPhotos);
        rvGallery.setNestedScrollingEnabled(false);
        rvGallery.setAdapter(adapter);

        MenuClickRecyclerview.addTo(rvGallery).setOnItemClickListener(new MenuClickRecyclerview.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("detailImage", (Serializable) galleryPhotos);
                bundle.putInt("position", position);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                GalleryShowImageFragment newFragment = GalleryShowImageFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
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
