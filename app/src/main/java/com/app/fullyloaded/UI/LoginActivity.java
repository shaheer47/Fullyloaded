package com.app.fullyloaded.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView Back;
    MyTextView txtLogin, txtRegister, txtForgetPassword;
    MyEditText edtEmail, edtPassword;
    ProgressBar progressBar;
    CheckBox checkBox;
    boolean check = false;
    SharedPreferences sharedPreferences, preferences, getSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        mContext = this;

        Init();

        getToken();
    }

    public void Init() {
        preferences = getSharedPreferences("Token", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        getSharedPreferences = getSharedPreferences("Remember", MODE_PRIVATE);

        progressBar = findViewById(R.id.progressBar);

        checkBox = findViewById(R.id.checkBox);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        Back = findViewById(R.id.Back);

        txtLogin = findViewById(R.id.txtLogin);
        txtRegister = findViewById(R.id.txtRegister);
        txtForgetPassword = findViewById(R.id.txtForgetPassword);

        Back.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
        txtRegister.setOnClickListener(this);
        txtForgetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                finish();
                break;
            case R.id.txtLogin:
                if (edtEmail.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please Enter Your Email", Toast.LENGTH_SHORT).show();
                } else if (edtPassword.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                } else {
                    if (ConstantFunction.getInstance().isNetworkAvailable(mContext)) {
                        LoginApi();
                    } else {
                        Toast.makeText(mContext, "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.txtRegister:
                Intent intent = new Intent(mContext, SignInActivity.class);
                startActivity(intent);
                break;
            case R.id.txtForgetPassword:
                Intent i = new Intent(mContext, ForgotPasswordActivity.class);
                startActivity(i);
                break;
        }
    }

    private void LoginApi() {
        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().LOG_IN, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    Log.e("Response", "" + APIConstant.getInstance().LOG_IN + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("message");
                    String Status = JsonMain.getString("status");
                    if (Status.equalsIgnoreCase("SUCCESS")) {
                        JSONObject jsonLogin = JsonMain.getJSONObject("login");
                        getSharedPreferences = getSharedPreferences("Remember", MODE_PRIVATE);
                        SharedPreferences.Editor getEditor = getSharedPreferences.edit();
                        if (checkBox.isChecked()) {
                            getEditor.putString("UserName", edtEmail.getText().toString());
                            getEditor.putString("Password", edtPassword.getText().toString());
                            getEditor.putString("Remember", "1");
                            getEditor.commit();
                        } else {
                            check = false;
                            getEditor.putString("UserName", "");
                            getEditor.putString("Password", "");
                            getEditor.putString("Remember", "0");
                            getEditor.commit();
                        }
                        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
                        sharedPreferencesEditor.putString("UserID", jsonLogin.getString("id"));
                        sharedPreferencesEditor.putString("Name", jsonLogin.getString("name"));
                        sharedPreferencesEditor.putString("Email", jsonLogin.getString("email"));
                        sharedPreferencesEditor.putString("ProfileImage", jsonLogin.getString("profile_image"));
                        sharedPreferencesEditor.putString("Status", jsonLogin.getString("status"));
                        sharedPreferencesEditor.putString("NotificationStatus", jsonLogin.getString("notification_status"));
                        sharedPreferencesEditor.putString("IsAdmin", jsonLogin.getString("is_admin"));
                        sharedPreferencesEditor.putString("RememberToken", jsonLogin.getString("remember_token"));
                        sharedPreferencesEditor.putString("CreatedAt", jsonLogin.getString("created_at"));
                        sharedPreferencesEditor.putString("UpdatedAt", jsonLogin.getString("updated_at"));
                        sharedPreferencesEditor.putString("Token", jsonLogin.getString("token"));
                        sharedPreferencesEditor.commit();
                        Intent intent = new Intent(mContext, HomeActivity.class);
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
                params.put("email", edtEmail.getText().toString());
                params.put("password", edtPassword.getText().toString());
                params.put("deviceToken", preferences.getString("Token", ""));
                params.put("deviceType", "android");
                Log.e("PARAMETER", "" + APIConstant.getInstance().LOG_IN + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().LOG_IN);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }

    public void getToken() {
        String Token = FirebaseInstanceId.getInstance().getToken();
        Log.e("Token", "" + Token);
        SharedPreferences preferences = getSharedPreferences("Token", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString("Token", Token);
        preferencesEditor.commit();
    }

    @Override
    protected void onResume() {
        String Remember = getSharedPreferences.getString("Remember", "");
        if (Remember.equals("1")) {
            checkBox.setChecked(true);
            edtEmail.setText(getSharedPreferences.getString("UserName", ""));
            edtPassword.setText(getSharedPreferences.getString("Password", ""));
        } else {
            checkBox.setChecked(false);
        }
        super.onResume();
    }
}
