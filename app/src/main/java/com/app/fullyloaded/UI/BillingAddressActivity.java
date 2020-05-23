package com.app.fullyloaded.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class BillingAddressActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView Back;
    MyTextView txtCancel, txtSave;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences, preferences, getSharedPreferences;
    MyEditText edtName, edtCountry, edtStreetAddress, edtTownCity, edtStateCounty, edtPostalCodeZipCode,
            edtMobileNumber, edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_address);

        getSupportActionBar().hide();

        mContext = this;

        Init();
    }

    public void Init() {
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        preferences = getSharedPreferences("Token", MODE_PRIVATE);
        getSharedPreferences = getSharedPreferences("Remember", MODE_PRIVATE);

        progressBar = findViewById(R.id.progressBar);

        Back = findViewById(R.id.Back);

        edtName = findViewById(R.id.edtName);
        edtCountry = findViewById(R.id.edtCountry);
        edtStreetAddress = findViewById(R.id.edtStreetAddress);
        edtTownCity = findViewById(R.id.edtTownCity);
        edtStateCounty = findViewById(R.id.edtStateCounty);
        edtPostalCodeZipCode = findViewById(R.id.edtPostalCodeZipCode);
        edtMobileNumber = findViewById(R.id.edtMobileNumber);
        edtEmail = findViewById(R.id.edtEmail);

        txtCancel = findViewById(R.id.txtCancel);
        txtSave = findViewById(R.id.txtSave);

        Back.setOnClickListener(this);

        txtCancel.setOnClickListener(this);
        txtSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                finish();
                break;
            case R.id.txtSave:
                if (edtName.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                } else if (edtCountry.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please Enter Your Country", Toast.LENGTH_SHORT).show();
                } else if (edtStreetAddress.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please Enter Your Street Address", Toast.LENGTH_SHORT).show();
                } else if (edtTownCity.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please Enter Your Town/City", Toast.LENGTH_SHORT).show();
                } else if (edtStateCounty.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please Enter Your State", Toast.LENGTH_SHORT).show();
                } else if (edtPostalCodeZipCode.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please Enter Your PostalCode/ZipCode", Toast.LENGTH_SHORT).show();
                } else if (edtMobileNumber.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please Enter Your MobileNumber", Toast.LENGTH_SHORT).show();
                } else if (!ConstantFunction.getInstance().isValidMobile(edtMobileNumber.getText().toString())) {
                    Toast.makeText(mContext, "Please Enter Your Valid MobileNumber", Toast.LENGTH_SHORT).show();
                } else if (edtEmail.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please Enter Your Email address", Toast.LENGTH_SHORT).show();
                } else if (!ConstantFunction.getInstance().isValidEmail(edtEmail.getText().toString())) {
                    Toast.makeText(mContext, "Please Enter Valid Email address", Toast.LENGTH_SHORT).show();
                } else {
                    if (ConstantFunction.getInstance().isNetworkAvailable(mContext)) {
                        SaveBillingAddressApi();
                    } else {
                        Toast.makeText(mContext, "Please Check Your Network Connection", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.txtCancel:
                finish();
                break;
        }
    }

    private void SaveBillingAddressApi() {
        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().SAVE_BILLING_ADDRESS, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    Log.e("Response", "" + APIConstant.getInstance().SAVE_BILLING_ADDRESS + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("message");
                    String Status = JsonMain.getString("status");
                    if (Status.equalsIgnoreCase("SUCCESS")) {
                        /*JSONObject jsonLogin = JsonMain.getJSONObject("login");*/
                        Intent intent = new Intent(mContext, ShippingAddressActivity.class);
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
                params.put("token", sharedPreferences.getString("Token", ""));
                params.put("first_name", edtName.getText().toString());
                params.put("last_name", "");
                params.put("country_name", edtCountry.getText().toString());
                params.put("street_address", edtStreetAddress.getText().toString());
                params.put("town_city_name", edtTownCity.getText().toString());
                params.put("state_name", edtStateCounty.getText().toString());
                params.put("postal_code", edtPostalCodeZipCode.getText().toString());
                params.put("mobile_number", edtMobileNumber.getText().toString());
                params.put("email", edtEmail.getText().toString());
                Log.e("PARAMETER", "" + APIConstant.getInstance().SAVE_BILLING_ADDRESS + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().SAVE_BILLING_ADDRESS);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }
}
