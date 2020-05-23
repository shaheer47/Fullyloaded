package com.app.fullyloaded.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.fullyloaded.AppConstants.APIConstant;
import com.app.fullyloaded.AppConstants.ConstantFunction;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyEditText;
import com.app.fullyloaded.Utility.MyTextView;
import com.app.fullyloaded.VolleySupport.AppController;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView Back;
    MyTextView txtRegister, txtLogin;
    MyEditText edtUsername, edtEmailAddress, edtPassword;
    ProgressBar progressBar;
    CheckBox CheckBox;
    String NotificationStatus = "0";
    SharedPreferences preferences, sharedPreferences, getSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        getSupportActionBar().hide();

        mContext = this;

        Init();
    }

    public void Init() {
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        preferences = getSharedPreferences("Token", MODE_PRIVATE);
        getSharedPreferences = getSharedPreferences("Remember", MODE_PRIVATE);

        Back = findViewById(R.id.Back);

        edtUsername = findViewById(R.id.edtUsername);
        edtEmailAddress = findViewById(R.id.edtEmailAddress);
        edtPassword = findViewById(R.id.edtPassword);

        txtRegister = findViewById(R.id.txtRegister);
        txtLogin = findViewById(R.id.txtLogin);

        progressBar = findViewById(R.id.progressBar);

        CheckBox = findViewById(R.id.CheckBox);

        CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // perform logic
                    NotificationStatus = "1";
                } else {
                    NotificationStatus = "0";
                }
            }
        });

        Back.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                finish();
                break;
            case R.id.txtLogin:
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.txtRegister:
                if (edtUsername.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                } else if (edtEmailAddress.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please Enter Your Email Address", Toast.LENGTH_SHORT).show();
                } else if (!ConstantFunction.getInstance().isValidEmail(edtEmailAddress.getText().toString())) {
                    Toast.makeText(mContext, "Please Enter Valid Email address", Toast.LENGTH_SHORT).show();
                } else if (edtPassword.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (ConstantFunction.getInstance().isNetworkAvailable(mContext)) {
                        SignUpApi();
                    } else {
                        Toast.makeText(mContext, "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void SignUpApi() {
        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().SIGN_UP, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    Log.e("Response", "" + APIConstant.getInstance().SIGN_UP + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("message");
                    String Status = JsonMain.getString("status");
                    if (Status.equalsIgnoreCase("SUCCESS")) {
                        JSONObject jsonSignUp = JsonMain.getJSONObject("signup");
                        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                        sharedPreferencesEditor.putString("Name", jsonSignUp.getString("name"));
                        sharedPreferencesEditor.putString("Email", jsonSignUp.getString("email"));
                        sharedPreferencesEditor.putString("Password", jsonSignUp.getString("password"));
                        sharedPreferencesEditor.putString("DeviceToken", jsonSignUp.getString("deviceToken"));
                        sharedPreferencesEditor.putString("DeviceType", jsonSignUp.getString("deviceType"));
                        sharedPreferencesEditor.putString("NotificationStatus", jsonSignUp.getString("notification_status"));
                        sharedPreferencesEditor.putString("UserID", jsonSignUp.getString("user_id"));
                        sharedPreferencesEditor.putString("Token", jsonSignUp.getString("token"));
                        sharedPreferencesEditor.commit();
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", edtUsername.getText().toString());
                params.put("email", edtEmailAddress.getText().toString());
                params.put("password", edtPassword.getText().toString());
                params.put("deviceToken", preferences.getString("Token", ""));
                params.put("deviceType", "android");
                params.put("notification_status", NotificationStatus);
                Log.e("PARAMETER", "" + APIConstant.getInstance().SIGN_UP + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().SIGN_UP);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }
}
