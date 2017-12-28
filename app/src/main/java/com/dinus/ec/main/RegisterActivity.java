package com.dinus.ec.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.ResultReceiver;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dinus.ec.R;
import com.dinus.ec.db.SessionManager;
import com.dinus.ec.model.QR;
import com.dinus.ec.model.User;
import com.dinus.ec.service.API.APIInterface;
import com.dinus.ec.service.API.RestClient;
import com.dinus.ec.service.Config;
import com.dinus.ec.util.BaseActivity;
import com.dinus.ec.util.LoadImage;
import com.dinus.ec.util.Loading;
import com.dinus.ec.util.OnOneOffClickListener;
import com.dinus.ec.widget.DelayAutoCompleteTextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class RegisterActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener  {

    private TextInputLayout tilNama;
    private EditText etNama;
    private TextInputLayout tilNik;
    private EditText etNik;
    private TextInputLayout tilTglLahir;
    private EditText etTglLahir;
    private View vTglLahir;
    private TextInputLayout tilJabatan;
    private EditText etJabatan;
    private TextInputLayout tilSandi;
    private EditText etSandi;
    private TextInputLayout tilReSandi;
    private EditText etReSandi;
    private TextInputLayout tilLokasi;
    private DelayAutoCompleteTextView etLokasi;
    private View vLokasi;
    private Button btnRegister;
    private Loading loading = new Loading(RegisterActivity.this);
    private LoadImage loadImage;
    private SessionManager sessionManager;

    private LatLng mCenterLatLong;
    private String mAddressOutput;
    private String mAreaOutput;
    private String mKecamatanOutput;
    private String mStateOutput;
    private Location mLocation;
    private double mLatitude, mLongitude;
    private String mCityOutput, mProvinceOutput;
    private String alamat;
    private String mLocationServer = "not_found";

    private String txtquery, suggestLocation;

    //autoComplete
    private ArrayList<String> resultList = new ArrayList<>();
    private List<Address> addresses;
    private Address addresslocation;
    private LatLng resultLatLng;
    private ArrayAdapter adapter;
    private static final String LOG_TAG = "Places_api";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyDS_VLR4-g9jPMRtmbIOEISgiwGG4_OplE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initToolbar(getResources().getString(R.string.damamon_sales_leader));
        initUI();

        loadImage = new LoadImage(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
        }
    }

    private void initToolbar(String strTitle) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View viewActionBar = getLayoutInflater().inflate(R.layout.toolbar_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.LEFT);

        TextView tvTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        tvTitle.setText(strTitle);

        getSupportActionBar().setCustomView(viewActionBar, params);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initUI() {
        Typeface face = Typeface.createFromAsset(getAssets(), CalligraphyConfig.get().getFontPath());
        tilNama = (TextInputLayout)findViewById( R.id.til_nama );
        etNama = (EditText)findViewById( R.id.et_nama );
        tilNik = (TextInputLayout)findViewById( R.id.til_nik );
        etNik = (EditText)findViewById( R.id.et_nik );
        tilTglLahir = (TextInputLayout)findViewById( R.id.til_tgl_lahir );
        etTglLahir = (EditText)findViewById( R.id.et_tgl_lahir );
        vTglLahir = (View)findViewById( R.id.v_tgl_lahir );
        tilJabatan = (TextInputLayout)findViewById( R.id.til_jabatan );
        etJabatan = (EditText)findViewById( R.id.et_jabatan );
        tilLokasi = (TextInputLayout)findViewById( R.id.til_lokasi );
        etLokasi = (DelayAutoCompleteTextView)findViewById( R.id.et_lokasi );
        tilSandi = (TextInputLayout)findViewById( R.id.til_sandi );
        etSandi = (EditText)findViewById( R.id.et_sandi );
        tilReSandi = (TextInputLayout)findViewById( R.id.til_re_sandi );
        etReSandi = (EditText)findViewById( R.id.et_re_sandi );
        btnRegister = (Button)findViewById( R.id.btn_register );
        etSandi.setTypeface(face);
        tilSandi.setTypeface(face);
        tilReSandi.setTypeface(face);
        etReSandi.setTypeface(face);

        vTglLahir.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
                Calendar now = Calendar.getInstance();

                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        RegisterActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setMaxDate(now);
                dpd.dismissOnPause(true);
                dpd.showYearPickerFirst(true);
                dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
                dpd.setTitle("Tanggal Lahir");
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNama = etNama.getText().toString();
                String strNoID = etNik.getText().toString();
                String strTglLahir = etTglLahir.getText().toString();
                String strJabatan = etJabatan.getText().toString();
                String strLokasi = etLokasi.getText().toString();
                String strPassword = etSandi.getText().toString();
                String strRePassword = etReSandi.getText().toString();
                if (strNama.equalsIgnoreCase("")||strNama.equalsIgnoreCase("null")||strNama.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Silahkan isi nama anda", Toast.LENGTH_SHORT).show();
                } else if (strNoID.equalsIgnoreCase("")||strNoID.equalsIgnoreCase("null")||strNoID.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Silahkan isi no id anda", Toast.LENGTH_SHORT).show();
                } else if (strTglLahir.equalsIgnoreCase("")||strTglLahir.equalsIgnoreCase("null")||strTglLahir.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Silahkan isi Tanggal lahir anda", Toast.LENGTH_SHORT).show();
                } else if (strJabatan.equalsIgnoreCase("")||strJabatan.equalsIgnoreCase("null")||strJabatan.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Silahkan isi jabatan anda", Toast.LENGTH_SHORT).show();
                } else if (strLokasi.equalsIgnoreCase("")||strLokasi.equalsIgnoreCase("null")||strLokasi.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Silahkan isi Lokasi anda", Toast.LENGTH_SHORT).show();
                } else if (strPassword.equalsIgnoreCase("")||strPassword.equalsIgnoreCase("null")||strPassword.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Silahkan isi Password anda", Toast.LENGTH_SHORT).show();
                } else if (strRePassword.equalsIgnoreCase("")||strRePassword.equalsIgnoreCase("null")||strRePassword.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Silahkan isi lagi password anda", Toast.LENGTH_SHORT).show();
                } else if (!strRePassword.equalsIgnoreCase(strPassword)){
                    Toast.makeText(RegisterActivity.this, "Password anda tidak sesuai", Toast.LENGTH_SHORT).show();
                } else {
                    QR qr50 = new QR();
                    qr50.setCode("ec50");
                    getDb().saveObject(qr50);

                    QR qr100 = new QR();
                    qr100.setCode("ec100");
                    getDb().saveObject(qr100);

                    QR qr150 = new QR();
                    qr150.setCode("ec150");
                    getDb().saveObject(qr150);

                    QR qr200 = new QR();
                    qr200.setCode("ec200");
                    getDb().saveObject(qr200);

                    User user = new User();
                    user.setId("0");
                    user.setNama(strNama);
                    user.setNoid(strNoID);
                    user.setTgllahir(strTglLahir);
                    user.setJabatan(strJabatan);
                    user.setLokasi(strLokasi);
                    user.setAlat(String.valueOf(mLatitude));
                    user.setAlong(String.valueOf(mLongitude));
                    user.setPhoto(getStringImage(getResources().getDrawable(R.drawable.no_image_512dp)));
                    user.setPoin("0");
                    sessionManager.setLogin(true);
                    getDb().saveUser(user);
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.putExtra("from","login");
                    startActivity(intent);
                    finish();
                }
            }
        });

        adapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_dropdown_item_1line, resultList);
        etLokasi.setThreshold(2);
        etLokasi.setAdapter(adapter);

        etLokasi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alamat = etLokasi.getText().toString();
                suggestLocation = etLokasi.getText().toString().trim();
                getLatLng();
                hideKeyboard();
            }
        });

        etLokasi.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    alamat = etLokasi.getText().toString();
                    suggestLocation = etLokasi.getText().toString().trim();
                    getLatLng();
                    hideKeyboard();
                }
                return false;
            }
        });

        etLokasi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                txtquery = etLokasi.getText().toString().trim();

                if (txtquery.length() > 3) {
                    autocomplete(txtquery);
                } else {
                    resultList.clear();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void hideKeyboard() {
        InputMethodManager mImMan = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mImMan.hideSoftInputFromWindow(etLokasi.getWindowToken(), 0);
        etLokasi.clearFocus();
    }

    private void autocomplete(String input) {
        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?sensor=false&key=" + API_KEY);
            sb.append("&components=country:ID");
            sb.append("&language=id");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));


            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            Log.d("====", "Requesst send");
            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }


        try {
            // Create a JSON object hierarchy from the results
            Log.d("JSON", "Parsing resultant JSON :)");
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList.clear();
            Log.d("JSON", "predsJsonArray has length " + predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {

                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }
        adapter = new ArrayAdapter(RegisterActivity.this, android.R.layout.simple_dropdown_item_1line, resultList);
        etLokasi.setThreshold(2);
        etLokasi.setAdapter(adapter);
    }

    private void getLatLng() {
        Geocoder coder = new Geocoder(this);
        addresses = new ArrayList<Address>();
        addresses.clear();
        try {
            addresses.clear();
            addresses = coder.getFromLocationName(suggestLocation, 5);
            if (addresses.size() != 0) {
                addresslocation = addresses.get(0);
                addresslocation.getLatitude();
                addresslocation.getLongitude();
                resultLatLng = new LatLng(addresslocation.getLatitude(), addresslocation.getLongitude());

                mLatitude = addresslocation.getLatitude();
                mLongitude = addresslocation.getLongitude();
                mCityOutput = addresslocation.getSubAdminArea();
            } else {
                addressError("Lokasi tidak dapat digunakan, silahkan pilih lokasi yang lain");
             /*   mLatitude = 0;
                mLongitude = 0;
                mCityOutput = suggestLocation;*/
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void addressError(String notif) {
        Typeface face = Typeface.createFromAsset(getAssets(), CalligraphyConfig.get().getFontPath());
        new MaterialDialog.Builder(this)
                .title("Notification")
                .content(notif)
                .typeface(face, face)
                .positiveColor(getResources().getColor(R.color.text_purple))
                .positiveText("OK")
                .cancelable(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public String getStringImage(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    @Override
    public void onBackPressed() {
        setResult(0);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        if (dpd != null) dpd.setOnDateSetListener(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String dateSend = dayOfMonth + "-" + (monthOfYear+=1) + "-" + year;
        //String dateSend = year+"-"+(monthOfYear)+"-"+dayOfMonth;
        etTglLahir.setText(dateSend);
        Log.e("date Send", ""+dateSend);
    }


}
