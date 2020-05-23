package com.app.fullyloaded.UI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.fullyloaded.Adapters.CartAdapter;
import com.app.fullyloaded.Adapters.CurrentCompetitionsAdapter;
import com.app.fullyloaded.AppConstants.APIConstant;
import com.app.fullyloaded.Models.CartModel;
import com.app.fullyloaded.Models.CurrentCompetitionsModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;
import com.app.fullyloaded.VolleySupport.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView Back;
    RecyclerView CartItemRecyclerView;
    ArrayList<CartModel> cartList = new ArrayList<>();
    RelativeLayout BottomRelativeLayout;
    String QuizID, UserAnswer, Quantity, Price, CompetitionID, ProductID, UserID, Token, Inner, TotalAmount;
    ProgressBar progressBar;
    MyTextView txtErrorTitle;
    SharedPreferences sharedPreferences, preferences, getSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().hide();

        mContext = this;

        Init();
    }

    public void Init() {
        QuizID = getIntent().getExtras().getString("QuizID");
        UserAnswer = getIntent().getExtras().getString("UserAnswer");
        Quantity = getIntent().getExtras().getString("Quantity");
        Price = getIntent().getExtras().getString("Price");
        CompetitionID = getIntent().getExtras().getString("CompetitionID");
        ProductID = getIntent().getExtras().getString("ProductID");
        UserID = getIntent().getExtras().getString("UserID");
        Token = getIntent().getExtras().getString("Token");
        Inner = getIntent().getExtras().getString("Inner");

        /*Log.e("QuizID","" + QuizID);
        Log.e("UserAnswer","" + UserAnswer);
        Log.e("Quantity","" + Quantity);
        Log.e("Price","" + Price);
        Log.e("CompetitionID","" + CompetitionID);
        Log.e("ProductID","" + ProductID);
        Log.e("UserID","" + UserID);
        Log.e("Token","" + Token);*/

        preferences = getSharedPreferences("Token", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        getSharedPreferences = getSharedPreferences("Remember", MODE_PRIVATE);

        progressBar = findViewById(R.id.progressBar);

        txtErrorTitle = findViewById(R.id.txtErrorTitle);

        Back = findViewById(R.id.Back);

        BottomRelativeLayout = findViewById(R.id.BottomRelativeLayout);

        Back.setOnClickListener(this);

        BottomRelativeLayout.setOnClickListener(this);

        CartItemRecyclerView = findViewById(R.id.CartItemRecyclerView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                finish();
                break;
            case R.id.BottomRelativeLayout:
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.pay_alert);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                MyTextView txtAmount = (MyTextView) dialog.findViewById(R.id.txtAmount);
                txtAmount.setText("€" + TotalAmount);
                dialog.findViewById(R.id.txtPay).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        Intent intent = new Intent(mContext, PaymentActivity.class);
                        intent.putExtra("TotalAmount", TotalAmount);
                        startActivity(intent);
                    }
                });
                dialog.show();
                break;
        }
    }

    private void CartDetailsApi() {
        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        cartList.clear();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().CART_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    Log.e("Response", "" + APIConstant.getInstance().CART_DETAILS + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("message");
                    String Status = JsonMain.getString("status");
                    TotalAmount = JsonMain.getString("total_amount");
                    if (Status.equalsIgnoreCase("SUCCESS")) {
                        JSONArray jsonCartDetails = JsonMain.getJSONArray("cartDetails");
                        for (int i = 0; i < jsonCartDetails.length(); i++) {
                            CartModel cartModel = new CartModel();
                            cartModel.setID(jsonCartDetails.getJSONObject(i).getString("id"));
                            cartModel.setProductID(jsonCartDetails.getJSONObject(i).getString("product_id"));
                            cartModel.setProductName(jsonCartDetails.getJSONObject(i).getString("product_name"));
                            cartModel.setProductImage(jsonCartDetails.getJSONObject(i).getString("product_image"));
                            cartModel.setProductPrice(jsonCartDetails.getJSONObject(i).getString("product_price"));
                            cartModel.setProductQuantity(jsonCartDetails.getJSONObject(i).getString("product_quantity"));
                            cartList.add(cartModel);
                        }
                        if (cartList.size() > 0) {
                            txtErrorTitle.setVisibility(View.GONE);
                            CartAdapter cartAdapter = new CartAdapter(mContext, cartList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, CartItemRecyclerView.VERTICAL, false);
                            CartItemRecyclerView.setLayoutManager(mLayoutManager);
                            CartItemRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            CartItemRecyclerView.setAdapter(cartAdapter);
                            cartAdapter.notifyDataSetChanged();
                        } else {
                            txtErrorTitle.setVisibility(View.VISIBLE);
                            BottomRelativeLayout.setVisibility(View.GONE);
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
                if (Inner.equals("1")) {
                    params.put("token", Token);
                } else {
                    params.put("token", sharedPreferences.getString("Token", ""));
                }
                Log.e("PARAMETER", "" + APIConstant.getInstance().CART_DETAILS + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().CART_DETAILS);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }

    @Override
    protected void onResume() {
        String UserID = sharedPreferences.getString("UserID", "");
        // Log.e("UserID", "" + UserID);
        if (UserID.equals("0")) {
            txtErrorTitle.setVisibility(View.VISIBLE);
            BottomRelativeLayout.setVisibility(View.GONE);
        } else {
            CartDetailsApi();
        }
        super.onResume();
    }
}
