package com.dinus.ec.main;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.service.Config;
import com.dinus.ec.util.BaseActivity;
import com.dinus.ec.util.OnOneOffClickListener;

public class HelpActivity extends BaseActivity {

//    private WebView wvLine;
//    private ProgressBar pbHelp;
//    private View vError;
//    private TextView tvErrorTitle;
//    private TextView tvErrorContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        initToolbar(getResources().getString(R.string.help));
        initUI();

//        getHelp();
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
//        vError = findViewById( R.id.v_error );
//        tvErrorTitle = (TextView)findViewById( R.id.tv_error_title );
//        tvErrorContent = (TextView)findViewById( R.id.tv_error_content );
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
