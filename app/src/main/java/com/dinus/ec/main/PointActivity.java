package com.dinus.ec.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dinus.ec.R;
import com.dinus.ec.util.BaseActivity;
import com.dinus.ec.util.OnOneOffClickListener;

public class PointActivity extends BaseActivity {

    private TextView tvMessage;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        initUI();

        Bundle extras = getIntent().getExtras();

        if (extras!=null){
            String message = extras.getString("message");
            setValue(message);
        }
    }

    private void initUI() {
        tvMessage = (TextView)findViewById( R.id.tv_message );
        btnNext = (Button)findViewById( R.id.btn_next );

        btnNext.setOnClickListener(new OnOneOffClickListener() {
            @Override
            public void onSingleClick(View v) {
                Intent intent = new Intent(PointActivity.this, MainActivity.class);
                intent.putExtra("from","");
                startActivity(intent);
                finish();
            }
        });
    }

    private void setValue (String message){
        tvMessage.setText(message);
    }
}
