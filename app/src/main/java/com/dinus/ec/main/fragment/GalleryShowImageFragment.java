package com.dinus.ec.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.dinus.ec.R;
import com.dinus.ec.model.GalleryPhoto;
import com.dinus.ec.service.API.APIInterface;
import com.dinus.ec.service.API.RestClient;
import com.dinus.ec.service.FileDownloader;
import com.dinus.ec.util.LoadImage;
import com.dinus.ec.util.Loading;
import com.dinus.ec.util.OnOneOffClickListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by Ridwan_Akbar on 01/05/2016.
 */
public class GalleryShowImageFragment extends DialogFragment {

    private String TAG = GalleryShowImageFragment.class.getSimpleName();
    private ArrayList<GalleryPhoto> images;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private int selectedPosition = 0;
    private LoadImage loadImage;
    private Loading loading;
    private boolean downloading = false;

    public static GalleryShowImageFragment newInstance() {
        GalleryShowImageFragment f = new GalleryShowImageFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image_slider, container, false);

        loadImage = new LoadImage(getActivity());
        loading = new Loading(getActivity());

        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        images = (ArrayList<GalleryPhoto>) getArguments().getSerializable("detailImage");
        selectedPosition = getArguments().getInt("position");
        Button btnDownload = (Button) v.findViewById(R.id.btn_download);

        Log.e(TAG, "position: " + selectedPosition);
        Log.e(TAG, "images size: " + images.size());

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnDownload.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                saveBitmap(images.get(selectedPosition).getPhoto());
            }
        });

        setCurrentItem(selectedPosition);

        return v;
    }

    private void saveBitmap(String url){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Danamon");
        boolean success = true;
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            success = mediaStorageDir.mkdirs();
        }
        File newMediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "danamon_IMG_" + timeStamp + ".jpg");
        APIInterface downloadService = RestClient.getClient();
        Call<ResponseBody> call = downloadService.downloadFileWithDynamicUrlSync(url);
        FileDownloader downloader = new FileDownloader(call, newMediaFile, loading, getActivity());
//        if (downloading){
//            downloader.downloadFile(true);
//            downloading = false;
//        } else {
            downloader.downloadFile(false);
//            downloading = true;
//        }
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    //  page change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void displayMetaInfo(int position) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    //  adapter
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.image_fullscreen_item, container, false);

            ImageView imageViewPreview = (ImageView) view.findViewById(R.id.image_preview);
            GalleryPhoto image = images.get(position);

            loadImage.LoadImageNoProgressPicasso(image.getPhoto(), imageViewPreview);

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
