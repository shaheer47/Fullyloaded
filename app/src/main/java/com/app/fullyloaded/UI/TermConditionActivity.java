package com.app.fullyloaded.UI;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.app.fullyloaded.R;

public class TermConditionActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    WebView webView;
    ImageView Back;

    final Boolean[] failedLoading = {false};

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_condition);
        getSupportActionBar().hide();

        mContext = this;

        Init();


        progressBar.setVisibility(View.VISIBLE);


        webView.loadUrl("https://fullyloaded.ie/termsCondition");


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (!failedLoading[0]) {
                    progressBar.setVisibility(View.GONE);

                    webView.setVisibility(View.VISIBLE);
                    webView.setAlpha(0f);
                    ObjectAnimator anim  = ObjectAnimator.ofFloat(webView, "alpha",1f);
                    anim.setDuration(500);
                    anim.start();
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                failedLoading[0] = true;
                progressBar.setVisibility(View.GONE);

            }
        });

    }

    public void Init() {
        Back = findViewById(R.id.Back);

        Back.setOnClickListener(this);



        progressBar=findViewById(R.id.progressBar);
        webView = findViewById(R.id.webView);

//        webView.setWebViewClient(new WebViewClient());

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
