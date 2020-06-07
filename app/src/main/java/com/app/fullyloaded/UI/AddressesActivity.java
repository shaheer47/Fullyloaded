package com.app.fullyloaded.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.fullyloaded.AppConstants.APIConstant;
import com.app.fullyloaded.R;
import com.app.fullyloaded.VolleySupport.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddressesActivity extends AppCompatActivity {

    SharedPreferences preferences, sharedPreferences, getSharedPreferences;

    ProgressBar progressBar;


//    LinearLayout ship,bill;


    ImageView back;
    RelativeLayout shippingLayout, billingLayout;

    TextView b_name, b_country, b_address, b_city, b_state, b_zip, b_mobile, b_email, b_edit, add_b_address;
    TextView s_name, s_country, s_address, s_city, s_state, s_zip, s_mobile, s_email, s_edit, add_s_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses);

        getSupportActionBar().hide();


        init();

        getBillingData();
        getShippingData();


        add_b_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressesActivity.this, BillingAddressActivity.class));
            }
        });


        add_s_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressesActivity.this, ShippingAddressActivity.class));

            }
        });


        b_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressesActivity.this, BillingAddressActivity.class));

            }
        });
        s_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(AddressesActivity.this, ShippingAddressActivity.class));

            }
        });

    }

    private void getShippingData() {
        String req = "req";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().GET_SHIPPING_ADDRESS, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray address = jsonObject.getJSONArray("address");
                    if (address.length() >= 1) {
                        setShippingAddress(address);
                        shippingLayout.setVisibility(View.VISIBLE);
//                        ship.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    } else {
//                        ship.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);

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
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().HOME_PAGE);
        AppController.getInstance().addToRequestQueue(stringRequest, req);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getBillingData() {
        String req = "req";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().GET_Billing_ADDRESS, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray address = jsonObject.getJSONArray("address");
                    if (address.length() >= 1) {
                        setBillingAddress(address);
//                        bill.setVisibility(View.VISIBLE);
                        billingLayout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);


                    } else {
//                        bill.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);

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
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().HOME_PAGE);
        AppController.getInstance().addToRequestQueue(stringRequest, req);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setBillingAddress(JSONArray maddress) {

        try {

            for (int i = 0; i < maddress.length(); i++) {

                JSONObject address = maddress.getJSONObject(i);

                if (address.getString("first_name").equals("") || address.getString("first_name").equals("null") || address.getString("first_name").equals(null) || address.getString("first_name") == null) {
                } else {
                    b_name.setText(address.getString("first_name"));
                }
                if (address.getString("last_name").equals("") || address.getString("last_name").equals("null") || address.getString("last_name").equals(null) || address.getString("last_name") == null) {
                } else {
                    b_name.setText(b_name.getText().toString() + " " + address.getString("last_name"));
                }
                if (address.getString("country_name").equals("") || address.getString("country_name").equals("null") || address.getString("country_name").equals(null) || address.getString("country_name") == null) {
                } else {
                    b_country.setText(address.getString("country_name"));
                }
                if (address.getString("street_address").equals("") || address.getString("street_address").equals("null") || address.getString("street_address").equals(null) || address.getString("street_address") == null) {
                } else {
                    b_address.setText(address.getString("street_address"));
                }
                if (address.getString("town_city_name").equals("") || address.getString("town_city_name").equals("null") || address.getString("town_city_name").equals(null) || address.getString("town_city_name") == null) {
                } else {
                    b_city.setText(address.getString("town_city_name"));
                }

                if (address.getString("state_name").equals("") || address.getString("state_name").equals("null") || address.getString("state_name").equals(null) || address.getString("state_name") == null) {
                } else {

                    b_state.setText(address.getString("state_name"));
                }

                if (address.getString("postal_code").equals("") || address.getString("postal_code").equals("null") || address.getString("postal_code").equals(null) || address.getString("postal_code") == null) {
                } else {

                    b_zip.setText(address.getString("postal_code"));
                }

                if (address.getString("mobile_number").equals("") || address.getString("mobile_number").equals("null") || address.getString("mobile_number").equals(null) || address.getString("mobile_number") == null) {
                } else {
                    b_mobile.setText(address.getString("mobile_number"));
                }

                if (address.getString("email").equals("") || address.getString("email").equals("null") || address.getString("email").equals(null) || address.getString("email") == null) {
                } else {
                    b_email.setText(address.getString("email"));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }


    }

    private void setShippingAddress(JSONArray maddress) {

        try {
            for (int i = 0; i < maddress.length(); i++) {

                JSONObject address = maddress.getJSONObject(i);
                if (address.getString("first_name").equals("") || address.getString("first_name").equals("null") || address.getString("first_name").equals(null) || address.getString("first_name") == null) {
                } else {
                    s_name.setText(address.getString("first_name"));
                }
                if (address.getString("last_name").equals("") || address.getString("last_name").equals("null") || address.getString("last_name").equals(null) || address.getString("last_name") == null) {
                } else {
                    s_name.setText(s_name.getText().toString() + " " + address.getString("last_name"));
                }
                if (address.getString("country_name").equals("") || address.getString("country_name").equals("null") || address.getString("country_name").equals(null) || address.getString("country_name") == null) {
                } else {
                    s_country.setText(address.getString("country_name"));
                }
                if (address.getString("street_address").equals("") || address.getString("street_address").equals("null") || address.getString("street_address").equals(null) || address.getString("street_address") == null) {
                } else {
                    s_address.setText(address.getString("street_address"));
                }
                if (address.getString("town_city_name").equals("") || address.getString("town_city_name").equals("null") || address.getString("town_city_name").equals(null) || address.getString("town_city_name") == null) {
                } else {
                    s_city.setText(address.getString("town_city_name"));
                }

                if (address.getString("state_name").equals("") || address.getString("state_name").equals("null") || address.getString("state_name").equals(null) || address.getString("state_name") == null) {
                } else {

                    s_state.setText(address.getString("state_name"));
                }

                if (address.getString("postal_code").equals("") || address.getString("postal_code").equals("null") || address.getString("postal_code").equals(null) || address.getString("postal_code") == null) {
                } else {

                    s_zip.setText(address.getString("postal_code"));
                }

                if (address.getString("mobile_number").equals("") || address.getString("mobile_number").equals("null") || address.getString("mobile_number").equals(null) || address.getString("mobile_number") == null) {
                } else {
                    s_mobile.setText(address.getString("mobile_number"));
                }

                if (address.getString("email").equals("") || address.getString("email").equals("null") || address.getString("email").equals(null) || address.getString("email") == null) {
                } else {
                    s_email.setText(address.getString("email"));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();

        }


    }


    private void init() {

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);


        billingLayout = findViewById(R.id.billing_view);
        shippingLayout = findViewById(R.id.shipping_view);

        back = findViewById(R.id.Back);

        progressBar = findViewById(R.id.progressBar1);

//        ship=findViewById(R.id.add_shipping_main);
//        bill=findViewById(R.id.add_billing_main);

        b_name = findViewById(R.id.billing_name);
        b_country = findViewById(R.id.billing_country);
        b_address = findViewById(R.id.billing_address);
        b_city = findViewById(R.id.billing_city);
        b_state = findViewById(R.id.billing_state);
        b_zip = findViewById(R.id.billing_zip);
        b_mobile = findViewById(R.id.billing_mobile);
        b_email = findViewById(R.id.billing_email);
        b_edit = findViewById(R.id.edit_billing);
        add_b_address = findViewById(R.id.add_billing);


        s_name = findViewById(R.id.shipping_name);
        s_country = findViewById(R.id.shipping_country);
        s_address = findViewById(R.id.shipping_address);
        s_city = findViewById(R.id.shipping_city);
        s_state = findViewById(R.id.shipping_state);
        s_zip = findViewById(R.id.shipping_zip);
        s_mobile = findViewById(R.id.shipping_mobile);
        s_email = findViewById(R.id.shipping_email);
        s_edit = findViewById(R.id.edit_shipping);
        add_s_address = findViewById(R.id.add_shipping);

    }

}
