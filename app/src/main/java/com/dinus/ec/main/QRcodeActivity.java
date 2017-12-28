package com.dinus.ec.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.dinus.ec.model.User;
import com.dinus.ec.service.API.APIInterface;
import com.dinus.ec.service.API.RestClient;
import com.dinus.ec.util.BaseActivity;
import com.dinus.ec.util.Loading;
import com.google.gson.Gson;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRcodeActivity extends BaseActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private Loading loading = new Loading(QRcodeActivity.this);
    private boolean scanned = false;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);// Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    private void sendQR(String strCode) {
        scanned = true;
        if (strCode.equalsIgnoreCase("ec50")) {
            Intent intent = new Intent(QRcodeActivity.this, PointCodeActivity.class);
            intent.putExtra("message", "Selamat anda mendapat 50 poin");
            User user = getDb().getUser();
            int currentPoint = Integer.parseInt(user.getPoin());
            int updatePoint = currentPoint+50;
            user.setPoin(String.valueOf(updatePoint));
            getDb().updateUser(user);
            startActivity(intent);
            finish();
        } else  if (strCode.equalsIgnoreCase("ec100")) {
            Intent intent = new Intent(QRcodeActivity.this, PointCodeActivity.class);
            intent.putExtra("message", "Selamat anda mendapat 100 poin");
            User user = getDb().getUser();
            int currentPoint = Integer.parseInt(user.getPoin());
            int updatePoint = currentPoint+100;
            user.setPoin(String.valueOf(updatePoint));
            getDb().updateUser(user);
            startActivity(intent);
            finish();
        } else  if (strCode.equalsIgnoreCase("ec150")) {
            Intent intent = new Intent(QRcodeActivity.this, PointCodeActivity.class);
            intent.putExtra("message", "Selamat anda mendapat 150 poin");
            User user = getDb().getUser();
            int currentPoint = Integer.parseInt(user.getPoin());
            int updatePoint = currentPoint+150;
            user.setPoin(String.valueOf(updatePoint));
            getDb().updateUser(user);
            startActivity(intent);
            finish();
        } else if (strCode.equalsIgnoreCase("ec200")) {
            Intent intent = new Intent(QRcodeActivity.this, PointCodeActivity.class);
            intent.putExtra("message", "Selamat anda mendapat 200 poin");
            User user = getDb().getUser();
            int currentPoint = Integer.parseInt(user.getPoin());
            int updatePoint = currentPoint+200;
            user.setPoin(String.valueOf(updatePoint));
            getDb().updateUser(user);
            startActivity(intent);
            finish();
        } else {
            scanned = false;
            customToast().showToast(QRcodeActivity.this, "QR Code tidak diketahui");
        }
//        loading.showLoading("Please Wait...");
//        APIInterface service = RestClient.getClient();
//        final Gson gson = new Gson();
//
//        Call<ResponseBody> call = service.scan(String.valueOf(""), strCode);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.d("Danamon", "status code: " + response.code());
//                if (response.isSuccessful()) {
//
//                    loading.dismissDialog();
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
//                            Intent intent = new Intent(QRcodeActivity.this, PointCodeActivity.class);
//                            intent.putExtra("message", api_message);
//                            startActivity(intent);
//                            finish();
//                        } else if (api_status == 0) {
//                            scanned = false;
//                            loading.dismissDialog();
//                            customToast().showToast(QRcodeActivity.this, api_message);
//                        }
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Log.d("Danamon", "error");
//                    loading.dismissDialog();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d("Danamon", "failure: " + t.getLocalizedMessage());
//                loading.dismissDialog();
//            }
//        });
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v("Danamaon", rawResult.getText()); // Prints scan results
        Log.v("Danamaon", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        if (!scanned) {
            sendQR(rawResult.getText());
        }

        // If you would like to resume scanning, call this method below:
        mScannerView.resumeCameraPreview(this);
    }
}
