package com.app.fullyloaded.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;
import com.app.fullyloaded.sharedPreference.SharedPreferencesEditor;

public class MyAccountActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView Back;
    CardView OrderCardView, AddressCardView, LogoutCardView, DashBoardCardView;
    MyTextView txtLoginLogout, ProfileName, ProfileEmail;
    String UserID;
    SharedPreferences sharedPreferences, preferences, getSharedPreferences;
    SharedPreferencesEditor localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        getSupportActionBar().hide();
        localStorage = new SharedPreferencesEditor(this);

        mContext = this;

        Init();
    }

    public void Init() {
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        preferences = getSharedPreferences("Token", MODE_PRIVATE);
        getSharedPreferences = getSharedPreferences("Remember", MODE_PRIVATE);

        UserID = sharedPreferences.getString("UserID", "");
        Log.e("UserID", "" + UserID);

        Back = findViewById(R.id.Back);

        ProfileName = findViewById(R.id.ProfileName);
        ProfileEmail = findViewById(R.id.ProfileEmail);
        txtLoginLogout = findViewById(R.id.txtLoginLogout);

        ProfileName.setText(sharedPreferences.getString("Name",""));
        ProfileEmail.setText(sharedPreferences.getString("Email",""));

        DashBoardCardView = findViewById(R.id.DashBoardCardView);
        OrderCardView = findViewById(R.id.OrderCardView);
        AddressCardView = findViewById(R.id.AddressCardView);
        LogoutCardView = findViewById(R.id.LogoutCardView);

        Back.setOnClickListener(this);

        DashBoardCardView.setOnClickListener(this);
        OrderCardView.setOnClickListener(this);
        AddressCardView.setOnClickListener(this);
        LogoutCardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                finish();
                break;
            case R.id.OrderCardView:
                Intent intent = new Intent(mContext, RecentOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.AddressCardView:
                Intent i = new Intent(mContext, AddressesActivity.class);
                startActivity(i);
                break;
            case R.id.LogoutCardView:
                if (!localStorage.IsLoggedIn()) {
                    Intent intent2 = new Intent(mContext, LoginActivity.class);
                    startActivity(intent2);
                    finish();
                } else {
                    Logout();
                }
                break;
            case R.id.DashBoardCardView:
                Intent intent1 = new Intent(mContext, HomeActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

    public void Logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Fully Loaded");
        builder.setMessage("Are you sure want to Logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("UserID", "0");
                editor.clear().commit();
                localStorage.setLoggedIn(false);
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        // alert.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(false);
        // alert.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);
    }

    @Override
    protected void onResume() {
        if (!localStorage.IsLoggedIn()) {
            txtLoginLogout.setText("Login");
        } else {
            txtLoginLogout.setText("Logout");
        }
        super.onResume();
    }
}
