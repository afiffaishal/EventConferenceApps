package com.dinus.ec.service;

import android.content.Context;
import android.util.Log;

import com.dinus.ec.util.CustomToast;
import com.dinus.ec.util.Loading;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PHAP on 17/11/2016.
 */

public class FileDownloader {
//    private static final int MEGABYTE = 1024 * 1024;
//    private HttpURLConnection connection;
    private Call<ResponseBody> call;
    private File directory;
    private Loading loading;
    private CustomToast customToast = new CustomToast();
    private Context context;
    //private boolean status = false;

    public FileDownloader() {
    }

//    public FileDownloader(HttpURLConnection connection, String fileUrl, File directory, ProgressBar progressBar) {
//        this.connection = connection;
//        this.fileUrl = fileUrl;
//        this.directory = directory;
//        this.progressBar = progressBar;
//    }

    public FileDownloader(Call<ResponseBody> call, File directory, Loading loading, Context context) {
        this.call = call;
        this.directory = directory;
        this.loading = loading;
        this.context = context;
    }


//    public boolean isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }

    public void downloadFile(boolean status) {
        if (!status) {
            loading.showLoading("Downloading...");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Log.d("Danamon", "server contacted and has file");

                        boolean writtenToDisk = writeResponseBodyToDisk(response.body(), directory);

                        Log.d("Danamon", "file download was a success? " + writtenToDisk);
                    } else {
                        loading.dismissDialog();
                        Log.d("Danamon", "server contact failed");
                        customToast.showToast(context, "Download Failed");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    if (call.isCanceled()) {
                        loading.dismissDialog();
                        //status = false;
                        Log.e("Danamon", "request was cancelled");
                        customToast.showToast(context, "Download Failed");
                    } else {
                        loading.dismissDialog();
                        t.printStackTrace();
                        Log.e("Danamon", "other larger issue, i.e. no network connection?");
                        customToast.showToast(context, "Download Failed");
                    }

                }
            });
        } else {
            call.cancel();
            loading.dismissDialog();
        }
    }

//    public void stop(){
//        call.cancel();
//        new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                Log.d("MPM", "request success");
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        };
//    }

    private boolean writeResponseBodyToDisk(ResponseBody body, File file) {
        try {
            // todo change the file location/name according to your needs

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("Danamon", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                    loading.dismissDialog();
                    //this.status = false;
                }

                if (outputStream != null) {
                    outputStream.close();
                    loading.dismissDialog();
                    //this.status = false;
                }
                customToast.showToast(context, "Download Success");
            }
        } catch (IOException e) {
            return false;
        }
    }

//    public void downloadFile() throws Exception {
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        try {
//            URL url = new URL(fileUrl);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            //connection.connect();
//            InputStream inputStream = connection.getInputStream();
//            FileOutputStream fileOutputStream = new FileOutputStream(directory);
//            int totalSize = connection.getContentLength();
//
//            byte[] buffer = new byte[MEGABYTE];
//            int bufferLength = 0;
//            while ((bufferLength = inputStream.read(buffer)) > 0) {
//                fileOutputStream.write(buffer, 0, bufferLength);
//            }
//            fileOutputStream.close();
//            progressBar.setVisibility(View.GONE);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public void stop(ProgressBar progressBar){
//        connection.disconnect();
//        progressBar.setVisibility(View.GONE);
//    }
}

