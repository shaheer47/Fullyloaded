package com.app.fullyloaded.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;

public class TicketsPurchasedSuccessfullyActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView Back;
    MyTextView txtDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticketspurchasedsuccessfully);

        getSupportActionBar().hide();

        mContext = this;

        Init();
    }

    public void Init() {
        Back = findViewById(R.id.Back);

        txtDone = findViewById(R.id.txtDone);

        Back.setOnClickListener(this);
        txtDone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                goToHome();
                break;
            case R.id.txtDone:
                goToHome();
                break;
        }
    }

    public void goToHome() {
        Intent intent = new Intent(mContext, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToHome();
        super.onBackPressed();
    }
}
