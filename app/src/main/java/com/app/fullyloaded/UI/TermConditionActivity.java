package com.app.fullyloaded.UI;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.fullyloaded.R;

public class TermConditionActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    WebView webView;
    ImageView Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_condition);

        getSupportActionBar().hide();

        mContext = this;

        Init();
    }

    public void Init() {
        Back = findViewById(R.id.Back);

        Back.setOnClickListener(this);

        webView = findViewById(R.id.webView);

        webView.loadUrl("http://fullyloaded.ie/termsCondition");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                finish();
                break;
        }
    }
}
