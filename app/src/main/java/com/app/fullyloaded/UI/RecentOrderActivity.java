package com.app.fullyloaded.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.fullyloaded.Adapters.OrderAdapter;
import com.app.fullyloaded.AppConstants.APIConstant;
import com.app.fullyloaded.Models.OrderModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;
import com.app.fullyloaded.VolleySupport.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecentOrderActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView Back;
    RecyclerView OrderRecyclerView;
    ArrayList<OrderModel> orderList = new ArrayList<>();
    ProgressBar progressBar;
    MyTextView txtErrorTitle;
    SharedPreferences sharedPreferences, preferences, getSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_order);

        getSupportActionBar().hide();

        mContext = this;

        Init();
    }

    public void Init() {
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        preferences = getSharedPreferences("Token", MODE_PRIVATE);
        getSharedPreferences = getSharedPreferences("Remember", MODE_PRIVATE);

        progressBar = findViewById(R.id.progressBar);

        txtErrorTitle = findViewById(R.id.txtErrorTitle);

        Back = findViewById(R.id.Back);

        Back.setOnClickListener(this);

        OrderRecyclerView = findViewById(R.id.OrderRecyclerView);

        RecentOrderApi();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                finish();
                break;
        }
    }

    private void RecentOrderApi() {
        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().RECENT_ORDER, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    Log.e("Response", "" + APIConstant.getInstance().RECENT_ORDER + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("message");
                    String Status = JsonMain.getString("status");
                    if (Status.equalsIgnoreCase("SUCCESS")) {
                        JSONArray jsonRecentOrder = JsonMain.getJSONArray("recentOrder");
                        for (int i = 0; i < jsonRecentOrder.length(); i++) {
                            OrderModel orderModel = new OrderModel();
                            orderModel.setProductName(jsonRecentOrder.getJSONObject(i).getString("product_name"));
                            orderModel.setProductImage(jsonRecentOrder.getJSONObject(i).getString("product_image"));
                            orderModel.setProductQuantity(jsonRecentOrder.getJSONObject(i).getString("product_quantity"));
                            orderModel.setProductPrice(jsonRecentOrder.getJSONObject(i).getString("product_price"));
                            orderList.add(orderModel);
                        }
                        if (orderList.size() > 0) {
                            txtErrorTitle.setVisibility(View.GONE);
                            OrderAdapter orderAdapter = new OrderAdapter(mContext, orderList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, OrderRecyclerView.VERTICAL, false);
                            OrderRecyclerView.setLayoutManager(mLayoutManager);
                            OrderRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            OrderRecyclerView.setAdapter(orderAdapter);
                            orderAdapter.notifyDataSetChanged();
                        } else {
                            txtErrorTitle.setVisibility(View.VISIBLE);
                        }
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
                Log.e("PARAMETER", "" + APIConstant.getInstance().RECENT_ORDER + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().RECENT_ORDER);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }
}
