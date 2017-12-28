package com.dinus.ec.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dinus.ec.R;
import com.dinus.ec.model.Maps;
import com.dinus.ec.model.MapsItem;
import com.dinus.ec.model.User;
import com.dinus.ec.service.API.APIInterface;
import com.dinus.ec.service.API.RestClient;
import com.dinus.ec.util.BaseActivity;
import com.dinus.ec.util.Loading;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Loading loading = new Loading(MapsActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initUI();
        initToolbar(getResources().getString(R.string.maps));
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
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try {
            ImageView locationButton = (ImageView) mapFragment.getView().findViewById(Integer.parseInt("2"));
            locationButton.setImageResource(R.drawable.ic_marker_orange);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    private void getMaps() {

        try {
//            Maps maps = new Maps();
//            RealmList<MapsItem> mapsItems = new RealmList<>();
//            mapsItems.addAll(getDb().mapsItemList());
//            maps.setItems(mapsItems);
//            maps.setCenterLat("0");
//            maps.setCenterLong("0");
//            setMarker(maps);
            User user = getDb().getUser();
            LatLng latLng = new LatLng(Double.parseDouble(user.getAlat()), Double.parseDouble(user.getAlong()));
            Marker userMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_lokasi_purple))
                    .title("Lokasi Anda")
            );
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    private void setMarker(Maps marker){
        List<MapsItem> mapsItems = marker.getItems();
        for (int i=0; i<mapsItems.size(); i++){
            MapsItem mapsItem = mapsItems.get(i);
            String strLat = String.valueOf(mapsItem.getLatitude());
            String strLong = String.valueOf(mapsItem.getLongitude());
            if (strLat.isEmpty()||strLat.equalsIgnoreCase("")||strLat.equalsIgnoreCase("null")){
                strLat = "0";
            }

            if (strLong.isEmpty()||strLong.equalsIgnoreCase("")||strLong.equalsIgnoreCase("null")){
                strLong = "0";
            }
            LatLng latLng = new LatLng(Double.parseDouble(strLat), Double.parseDouble(strLong));
            Marker userMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_lokasi_purple))
                    .title(mapsItem.getName())
            );
        }

        String centerStrLat = String.valueOf(marker.getCenterLat());
        String centerStrLong = String.valueOf(marker.getCenterLong());
        if (centerStrLat.isEmpty()||centerStrLat.equalsIgnoreCase("")||centerStrLat.equalsIgnoreCase("null")){
            centerStrLat = "0";
        }

        if (centerStrLong.isEmpty()||centerStrLong.equalsIgnoreCase("")||centerStrLong.equalsIgnoreCase("null")){
            centerStrLong = "0";
        }
        LatLng latLngCenter = new LatLng(Double.parseDouble(centerStrLat), Double.parseDouble(centerStrLong));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngCenter, 16));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (Build.VERSION.SDK_INT >= 23) {
            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mMap.setMyLocationEnabled(true);

                }
                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    Toast.makeText(MapsActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                }

            };

            new TedPermission(this)
                    .setPermissionListener(permissionlistener)
                    .setRationaleMessage("We need permission for find Your location, write, read your external storage & take picture")
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA)
                    .check();

        } else {
           mMap.setMyLocationEnabled(true);
        }

        User user = getDb().getUser();
        LatLng latLng = new LatLng(Double.parseDouble(user.getAlat()), Double.parseDouble(user.getAlong()));
        Marker userMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_lokasi_purple))
                .title("Lokasi Anda")
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

//        getMaps();
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
