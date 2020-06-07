package com.app.fullyloaded.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.app.fullyloaded.AppConstants.APIConstant;
import com.app.fullyloaded.R;

public class SplashActivity extends AppCompatActivity {

    Context mContext;
    int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mContext = this;

        getSupportActionBar().hide();

        Init();
    }

    public void Init() {
        if (APIConstant.FirstTimeLaunchApplication.equals("1")) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("UserID", "0");
            editor.commit();
            Log.e("UserID", "" + sharedPreferences.getString("UserID",""));
            APIConstant.FirstTimeLaunchApplication = "0";
        } else {

        }
    }

    @Override
    protected void onResume() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
        super.onResume();
    }
}
