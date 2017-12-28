package com.dinus.ec.main;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.model.SongItem;
import com.dinus.ec.util.BaseActivity;

public class AgendaSongActivity extends BaseActivity {

    private SongItem songItem;
    private WebView wvSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_song);

        initUI();

        Bundle extrass = getIntent().getExtras();
        if (extrass!=null);{
            songItem = (SongItem) extrass.getSerializable("item");
            initToolbar(songItem.getTitle());
            setValue(songItem);
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
        wvSong = (WebView) findViewById(R.id.wv_lyric);
    }

    private void setValue(SongItem songItem){
        wvSong.loadData(songItem.getDescription(), "text/html", "utf-8");
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
