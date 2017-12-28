package com.dinus.ec.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dinus.ec.R;
import com.dinus.ec.model.Profile;
import com.dinus.ec.service.API.APIInterface;
import com.dinus.ec.service.API.RestClient;
import com.dinus.ec.util.BaseActivity;
import com.dinus.ec.util.LoadImage;
import com.dinus.ec.util.Loading;
import com.dinus.ec.util.OnOneOffClickListener;
import com.google.gson.Gson;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ProfileEditActivity extends BaseActivity {

    private CollapsingToolbarLayout ctlProfile;
    private ImageView ivProfile;
    private ImageView ivCamera;
    private ProgressBar pbImageProfile;
    private TextInputLayout tilName;
    private EditText etName;
    private View vProfilEditSandi;
    private View vProfilEditSandiContent;
    private TextInputLayout tilOldPassword;
    private EditText etOldPassword;
    private TextInputLayout tilNewPassword;
    private EditText etNewPassword;
    private TextInputLayout tilRePassword;
    private EditText etRePassword;
    private View vProfileEditBottom;
    private NestedScrollView nsProfile;
    private Button btnSave;

    private LoadImage loadImage;
    private Loading loading = new Loading(ProfileEditActivity.this);
    private Profile profile = null;

    private static final int REQUEST_CAMERA = 101;
    private static final int SELECT_FILE = 102;

    public File filePhoto;
    private Uri uriCamera = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        initToolbar(getResources().getString(R.string.edit_profile));
        initUI();

        loadImage = new LoadImage(getApplicationContext());

        Bundle extrass = getIntent().getExtras();
        if (extrass!=null){
            profile = (Profile) extrass.getSerializable("profile");
            setValue(profile);
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
        Typeface face = Typeface.createFromAsset(getAssets(), CalligraphyConfig.get().getFontPath());

        ctlProfile = (CollapsingToolbarLayout) findViewById(R.id.ctl_profile);
        ivProfile = (ImageView) findViewById(R.id.iv_profile);
        ivCamera = (ImageView) findViewById(R.id.iv_camera);
        pbImageProfile = (ProgressBar) findViewById(R.id.pb_image_profile);
        tilName = (TextInputLayout) findViewById(R.id.til_name);
        etName = (EditText) findViewById(R.id.et_name);
        vProfilEditSandi = findViewById(R.id.v_profile_edit_sandi);
        vProfilEditSandiContent = findViewById(R.id.v_profile_edit_sandi_content);
        tilOldPassword = (TextInputLayout) findViewById(R.id.til_old_password);
        etOldPassword = (EditText) findViewById(R.id.et_old_password);
        tilNewPassword = (TextInputLayout) findViewById(R.id.til_new_password);
        etNewPassword = (EditText) findViewById(R.id.et_new_password);
        tilRePassword = (TextInputLayout) findViewById(R.id.til_re_password);
        etRePassword = (EditText) findViewById(R.id.et_re_password);
        vProfileEditBottom = findViewById(R.id.v_profil_edit_bottom);
        nsProfile = (NestedScrollView) findViewById(R.id.ns_profile);
        btnSave = (Button) findViewById(R.id.btn_save);

        etRePassword.setTypeface(face);
        etNewPassword.setTypeface(face);
        etOldPassword.setTypeface(face);
        tilNewPassword.setTypeface(face);
        tilOldPassword.setTypeface(face);
        tilRePassword.setTypeface(face);

        int imageHeight = getWindowManager().getDefaultDisplay().getWidth();

        ViewGroup.LayoutParams layoutParams = ctlProfile.getLayoutParams();
        layoutParams.height = imageHeight;
        layoutParams.width = imageHeight;

        vProfilEditSandiContent.setVisibility(View.GONE);
        vProfilEditSandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNewPassword.setText("");
                etOldPassword.setText("");
                etRePassword.setText("");
                if (vProfilEditSandiContent.getVisibility() == View.VISIBLE) {
                    vProfilEditSandiContent.setVisibility(View.GONE);
                    nsProfile.requestChildFocus(vProfileEditBottom, vProfileEditBottom);
                } else if (vProfilEditSandiContent.getVisibility() == View.GONE) {
                    vProfileEditBottom.clearFocus();
                    vProfilEditSandiContent.setVisibility(View.VISIBLE);
                }
            }
        });

        ivCamera.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    PermissionListener permissionlistener = new PermissionListener() {
                        @Override
                        public void onPermissionGranted() {
                            showChooseImage();
                        }

                        @Override
                        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                            Toast.makeText(ProfileEditActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    };

                    new TedPermission(getApplicationContext())
                            .setPermissionListener(permissionlistener)
                            .setRationaleMessage("We need permission to write, read your external storage & take picture")
                            .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                            .check();

                } else{
                    showChooseImage();
                }
            }
        });

        btnSave.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                String vSandiLama = etOldPassword.getText().toString().trim();
                String vSandiBaru = etNewPassword.getText().toString().trim();
                String vSandiKonfirmasi = etRePassword.getText().toString().trim();

                if (vSandiLama.isEmpty() || vSandiLama.equalsIgnoreCase("")){
                    Toast.makeText(ProfileEditActivity.this, "Sandi tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
//                else if (!vSandiLama.equals(getSqLiteHandler().getPassword())){
//                    Toast.makeText(ProfileEditActivity.this, "Sandi lama yang Anda masukkan salah", Toast.LENGTH_SHORT).show();
//                }
                else if (vSandiBaru.isEmpty() || vSandiBaru.equalsIgnoreCase("")){
                    Toast.makeText(ProfileEditActivity.this, "Sandi Baru kosong", Toast.LENGTH_SHORT).show();
                } else if (vSandiKonfirmasi.isEmpty() || vSandiKonfirmasi.equalsIgnoreCase("")){
                    Toast.makeText(ProfileEditActivity.this, "Sandi Konfirmasi kosong", Toast.LENGTH_SHORT).show();
                } else if (!vSandiBaru.equals(vSandiKonfirmasi)){
                    Toast.makeText(ProfileEditActivity.this, "Sandi Konfirmasi yang Anda masukkan salah", Toast.LENGTH_SHORT).show();
                } else if (vSandiBaru.equalsIgnoreCase(vSandiLama)){
                    Toast.makeText(ProfileEditActivity.this, "Tidak ada perubahan", Toast.LENGTH_SHORT).show();
                } else {
                    postChangePassword(vSandiLama, vSandiBaru);
                    //updatePassword(String.valueOf(sqLiteHandler.getIDUser()), sqLiteHandler.getIDUser(), vSandiBaru);
                }
            }
        });


    }

    private void setValue(Profile profile){
        etName.setText(profile.getFirstName());
        etName.setEnabled(false);

        String strImage = String.valueOf(profile.getPhoto());

        if (!strImage.isEmpty() || !strImage.equalsIgnoreCase("")) {
            loadImage.LoadImagePicasso(strImage, ivProfile, pbImageProfile);
        }

    }

    private void postChangePassword(String strPasslama, final String strPasBaru){
        loading.showLoading("Send Request...");

        APIInterface service = RestClient.getClient();
        final Gson gson = new Gson();

        final String strIDUser = String.valueOf(getDb().getIDUser());

        Call<ResponseBody> call = service.changepassword(strIDUser,
                strPasslama, strPasBaru);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Danamon", "status code: " + response.code());
                if (response.isSuccessful()) {

                    loading.dismissDialog();

                    try {
                        String respon = response.body().string();
                        Log.d("Danamon", "response: " + respon);

                        JSONObject json = new JSONObject(respon);

                        int api_status = json.getInt("api_response");
                        String api_message = json.getString("api_message");

                        if (api_status == 1) {
//                            getSqLiteHandler().updatePassword(strPasBaru, strIDUser);
                            showDialogSuccess(api_message);
                        } else if (api_status == 0) {
                            customToast().showToast(ProfileEditActivity.this, api_message);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("Danamon", "error");
                    customToast().showToast(ProfileEditActivity.this,getResources().getString(R.string.error_data_problem));
//                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.error_data_problem), Toast.LENGTH_SHORT).show();
                    loading.dismissDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Danamon", "failure: " + t.getLocalizedMessage());
                customToast().showToast(ProfileEditActivity.this,getResources().getString(R.string.error_network_problem ));
//                Toast.makeText(RegisterActivity.this, getResources().getString(R.string.error_network_problem), Toast.LENGTH_SHORT).show();
                loading.dismissDialog();
            }
        });

    }

    private void postChangePhoto(String strPhoto){
        loading.showLoading("Send Request...");

        APIInterface service = RestClient.getClient();
        final Gson gson = new Gson();

        final String strIDUser = String.valueOf(getDb().getIDUser());

        Call<ResponseBody> call = service.changephotoprofile(strIDUser, strPhoto);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("Danamon", "status code: " + response.code());
                if (response.isSuccessful()) {

                    loading.dismissDialog();

                    try {
                        String respon = response.body().string();
                        Log.d("Danamon", "response: " + respon);

                        JSONObject json = new JSONObject(respon);

                        int api_status = json.getInt("api_response");
                        String api_message = json.getString("api_message");

                        if (api_status == 1) {
                            String photo = json.getString("photo");
//                            getSqLiteHandler().updateProfile(photo, strIDUser);
//                            loadImage.LoadImagePicasso(getSqLiteHandler().getUser().getPhoto(), ivProfile, pbImageProfile);
                            //getSqLiteHandler().updatePassword(strPasBaru, strIDUser);
                            //showDialogSuccess(api_message);
                        } else if (api_status == 0) {
                            customToast().showToast(ProfileEditActivity.this, api_message);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("Danamon", "error");
                    customToast().showToast(ProfileEditActivity.this,getResources().getString(R.string.error_data_problem));
//                    Toast.makeText(RegisterActivity.this, getResources().getString(R.string.error_data_problem), Toast.LENGTH_SHORT).show();
                    loading.dismissDialog();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Danamon", "failure: " + t.getLocalizedMessage());
                customToast().showToast(ProfileEditActivity.this,getResources().getString(R.string.error_network_problem ));
//                Toast.makeText(RegisterActivity.this, getResources().getString(R.string.error_network_problem), Toast.LENGTH_SHORT).show();
                loading.dismissDialog();
            }
        });

    }

    private void showDialogSuccess(String strMessage) {
        Typeface face = Typeface.createFromAsset(getAssets(), CalligraphyConfig.get().getFontPath());
        new MaterialDialog.Builder(ProfileEditActivity.this)
                .content(strMessage)
                .positiveText(R.string.ok)
                .cancelable(false)
                .typeface(face,face)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                }).show();

    }

    // TODO Update Photo

    public void showChooseImage(){
        Typeface face = Typeface.createFromAsset(getAssets(), CalligraphyConfig.get().getFontPath());
        new MaterialDialog.Builder(ProfileEditActivity.this)
                .items(R.array.itemsSelectImage)
                .typeface(face, face)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        switch (which) {
                            case 0:
                                pickPhoto();
                                break;
                            case 1:
                                shoot();
                                break;

                            default:
                                break;
                        }
                    }
                })
                .show();
    }

    public void pickPhoto() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_FILE);
    }

    public void shoot() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Danamon");
        boolean success = true;
        // Create the storage directory if it does not exist
        if (!f.exists()) {
            success = f.mkdirs();
        }
        File newMediaFile = new File(f.getPath() + File.separator
                + "profile_danamon_" + timeStamp + ".jpg");
        uriCamera = Uri.fromFile(newMediaFile);
        if (Build.VERSION.SDK_INT >= 24) {
            Uri uri = FileProvider.getUriForFile(ProfileEditActivity.this, getApplicationContext().getPackageName()
                    + ".BaseActivity", newMediaFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, REQUEST_CAMERA);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newMediaFile));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, REQUEST_CAMERA);
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter, float rotasi) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Matrix matrix = new Matrix();
        matrix.postRotate(rotasi);

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        Bitmap fixBitmap = Bitmap.createBitmap(newBitmap, 0, 0, width, height, matrix, true);

        return fixBitmap;
    }

    public int getCameraPhotoOrientation(Context context, Uri imageUri, String imagePath) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CAMERA:
//                    Log.d("uri camera",""+uriCamera);
                    beginCrop(uriCamera);
                    break;

                case SELECT_FILE:
                    beginCrop(data.getData());
                    break;

                case UCrop.REQUEST_CROP:
                    handleCrop(data);
                    break;
                default:
                    break;

            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "_avatar.jpg"));
        UCrop uCrop = UCrop.of(source, destination);
        uCrop = advancedConfig(uCrop);
        uCrop.start(this);
    }

    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setCompressionQuality(50);

        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.colorAccent));
        options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.white));
        options.setMaxBitmapSize(640);


        return uCrop.withOptions(options);
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e("handleCropError: ", "" + cropError);
            Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Unexpected error", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCrop(@NonNull Intent result) {

        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            try {
                copyFileToFolder(resultUri);
                File fileDelete = new File(uriCamera.getPath());
                fileDelete.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(this, "Can't retrieve image", Toast.LENGTH_SHORT).show();
        }
    }

    private void copyFileToFolder(Uri croppedFileUri) throws Exception {

        File newMediaFile = new File(android.os.Environment
                .getExternalStorageDirectory(), "temp.jpg");

        FileInputStream inStream = new FileInputStream(new File(croppedFileUri.getPath()));
        FileOutputStream outStream = new FileOutputStream(newMediaFile);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
        Bitmap bitmap;
        Bitmap bitmapScaled;
        try {
            filePhoto = new File(newMediaFile.getPath());
            Log.e("file path", ""+filePhoto.getPath());

            bitmap = BitmapFactory.decodeFile(newMediaFile.getPath());
            int rotasi = getCameraPhotoOrientation(getApplicationContext(), Uri.fromFile(filePhoto), filePhoto.getAbsolutePath());

            bitmapScaled = scaleDown(bitmap, 720, false, rotasi);

            filePhoto.delete();
            postChangePhoto(getStringImage(bitmapScaled));

        } catch (Exception e) {
            e.printStackTrace();
        }


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
