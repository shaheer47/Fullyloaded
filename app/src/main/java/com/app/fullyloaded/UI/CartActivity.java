package com.app.fullyloaded.UI;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.app.fullyloaded.Adapters.CartAdapter;
import com.app.fullyloaded.Adapters.OnDeleteCartListener;
import com.app.fullyloaded.AppConstants.APIConstant;
import com.app.fullyloaded.Models.CartModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;
import com.app.fullyloaded.VolleySupport.AppController;
import com.app.fullyloaded.config.Config;
import com.app.fullyloaded.sharedPreference.SharedPreferencesEditor;
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings;
import com.oppwa.mobile.connect.exception.PaymentError;
import com.oppwa.mobile.connect.provider.Connect;
import com.oppwa.mobile.connect.provider.Transaction;
import com.oppwa.mobile.connect.provider.TransactionType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class CartActivity extends AppCompatActivity implements View.OnClickListener, OnDeleteCartListener {

    Context mContext;
    EditText et_coupon;
    ImageView Back;
    RecyclerView CartItemRecyclerView;
    TextView totalAmount, btn_applyCoupon, tokenName;
    ArrayList<CartModel> cartList = new ArrayList<>();
    RelativeLayout BottomRelativeLayout, layoutBottom;
    String QuizID, UserAnswer, Quantity, Price, CompetitionID, ProductID, UserID, Token, Inner, TotalAmount;
    ProgressBar progressBar;
    MyTextView txtErrorTitle;
    ImageView btn_removeCoupon;
    SharedPreferences sharedPreferences, preferences, getSharedPreferences;
    SharedPreferencesEditor localStorage;
    CartAdapter cartAdapter;
    String transactionId;
    String coupan;
    private String checkoutId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().hide();

        mContext = this;
        localStorage = new SharedPreferencesEditor(this);

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

        et_coupon = findViewById(R.id.et_coupon);

        tokenName = findViewById(R.id.tokenName);

        btn_applyCoupon = findViewById(R.id.btn_applyCoupon);
        btn_applyCoupon.setOnClickListener(this);

        totalAmount = findViewById(R.id.totalAmount);

        btn_removeCoupon = findViewById(R.id.btn_removeCoupon);
        btn_removeCoupon.setOnClickListener(this);

        Back = findViewById(R.id.Back);

        BottomRelativeLayout = findViewById(R.id.BottomRelativeLayout);
        layoutBottom = findViewById(R.id.layoutBottom);

        Back.setOnClickListener(this);

        BottomRelativeLayout.setOnClickListener(this);

        CartItemRecyclerView = findViewById(R.id.CartItemRecyclerView);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_applyCoupon:
                if (et_coupon.length() == 0) {
                    Toast.makeText(mContext, "Please enter coupon number", Toast.LENGTH_SHORT).show();
                } else {


                    tokenName.setText(et_coupon.getText().toString());
                    et_coupon.setText("");
                    btn_removeCoupon.setVisibility(View.VISIBLE);
                    coupan = tokenName.getText().toString();
                    CouponApi();

                }
                break;
            case R.id.btn_removeCoupon:
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.remove_alert);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                dialog.findViewById(R.id.txtRemove).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        tokenName.setText("");
                        RemoveCouponApi();
                        btn_applyCoupon.setClickable(true);
                        btn_removeCoupon.setVisibility(View.GONE);
                    }
                });
                dialog.findViewById(R.id.txtCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
                break;
            case R.id.Back:
                finish();
                break;
            case R.id.BottomRelativeLayout:
                final Dialog dialog1 = new Dialog(mContext);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.pay_alert);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                MyTextView txtAmount = (MyTextView) dialog1.findViewById(R.id.txtAmount);
                txtAmount.setText("€" + totalAmount.getText().toString());
                dialog1.findViewById(R.id.txtPay).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.cancel();
                        requestCheckoutId();

//                        Intent intent = new Intent(mContext, PaymentActivity.class);
//                        intent.putExtra("TotalAmount", TotalAmount);
//                        startActivity(intent);


                        //TODO

//                        checkoutId = requestCheckoutId();
//
//                        Set<String> paymentBrands = new LinkedHashSet<String>();
//
//                        paymentBrands.add("VISA");
//                        paymentBrands.add("MASTER");
//                        paymentBrands.add("AMEX");
//                        paymentBrands.add("DIRECTDEBIT_SEPA");
//
//
//                        CheckoutSettings checkoutSettings = new CheckoutSettings(checkoutId, paymentBrands, Connect.ProviderMode.TEST);
//                        checkoutSettings.setShopperResultUrl("com.wedefineapps.fullyloaded.payments://result");
//
//                        Intent intent = checkoutSettings.createCheckoutActivityIntent(CartActivity.this);
//
//                        startActivityForResult(intent, CheckoutActivity.REQUEST_CODE_CHECKOUT);
//

//                        ComponentName componentName = new ComponentName(BuildConfig.APPLICATION_ID,
//                                CheckoutBroadcastReceiver.class.getCanonicalName());

//                        Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
//                        intent.putExtra(CheckoutActivity.CHECKOUT_SETTINGS, checkoutSettings);
//
//                        startActivityForResult(intent, CheckoutActivity.REQUEST_CODE_CHECKOUT);

                    }
                });
                dialog1.show();
                break;
        }
    }

    private void RemoveCouponApi() {

        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        cartList.clear();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().REMOVE_COUPAN, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    CartDetailsApi();
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

                Log.e("PARAMETER", "" + APIConstant.getInstance().CART_DETAILS + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().CART_DETAILS);
        AppController.getInstance().addToRequestQueue(stringRequest, req);


    }


    ///////////////////Test by volly////////////////////
    public void requestCheckoutId() {

        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        cartList.clear();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().CHECKOUT, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {

                try {
                    JSONObject JsonMain = new JSONObject(response);

                    checkoutId = JsonMain.getString("checkout_id");
                    checkOut(checkoutId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("token", sharedPreferences.getString("Token", ""));
                params.put("total_price", totalAmount.getText().toString());

                Log.e("PARAMETER", "" + APIConstant.getInstance().CART_DETAILS + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().CART_DETAILS);
        AppController.getInstance().addToRequestQueue(stringRequest, req);


    }
    ///////////////////Test by volly  end////////////////////
//    public void requestCheckoutId() {
//        URL url;
//        String urlString;
//        HttpURLConnection connection = null;
//        String checkoutId = null;
//
//        urlString = "https://fullyloaded.ie/api/checkout?token=" + sharedPreferences.getString("Token", "") + "&total_price=" + TotalAmount;
//
//        Log.e("URL_for_check",urlString);
//        try {
//            url = new URL(urlString);
//            connection = (HttpURLConnection) url.openConnection();
//
//            JsonReader reader = new JsonReader(
//                    new InputStreamReader(connection.getInputStream(), "UTF-8"));
//
//            reader.beginObject();
//
//            while (reader.hasNext()) {
//                if (reader.nextName().equals("checkoutId")) {
//
//                    checkOut(reader.nextString());
////                    checkOut(checkoutId);
////                    checkoutId = reader.nextString();
//                    break;
//                }
//            }
//
//            reader.endObject();
//            reader.close();
//        } catch (Exception e) {
//            Log.e("Error12",e.getMessage());
//
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//
//
//    }

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
                    totalAmount.setText(TotalAmount);
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

                        JSONObject coupan = JsonMain.getJSONObject("coupon");
                        if (coupan.getString("status").equals("SUCCESS")) {

                            tokenName.setText(coupan.getString("coupan"));
                            totalAmount.setText(JsonMain.getString("price_after_discount"));
                            btn_applyCoupon.setClickable(false);
                            btn_removeCoupon.setVisibility(View.VISIBLE);

                        }


                        if (cartList.size() > 0) {

                            txtErrorTitle.setVisibility(View.GONE);
                            cartAdapter = new CartAdapter(mContext, cartList, CartActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, CartItemRecyclerView.VERTICAL, false);
                            CartItemRecyclerView.setLayoutManager(mLayoutManager);
                            CartItemRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            CartItemRecyclerView.setAdapter(cartAdapter);
                            cartAdapter.notifyDataSetChanged();

                        } else {
                            txtErrorTitle.setVisibility(View.VISIBLE);
                            layoutBottom.setVisibility(View.GONE);
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

    private void CouponApi() {
        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        cartList.clear();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().CHECK_COUPAN, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    CartDetailsApi();
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
                params.put("coupan_name", coupan);

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

        if (!localStorage.IsLoggedIn()) {
            txtErrorTitle.setVisibility(View.VISIBLE);
            layoutBottom.setVisibility(View.GONE);
        } else {
            CartDetailsApi();
        }
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case CheckoutActivity.RESULT_OK:
                /* transaction completed */
                Transaction transaction = data.getParcelableExtra(CheckoutActivity.CHECKOUT_RESULT_TRANSACTION);

                /* resource path if needed */
                String resourcePath = data.getStringExtra(CheckoutActivity.CHECKOUT_RESULT_RESOURCE_PATH);

                if (transaction.getTransactionType() == TransactionType.SYNC) {

                    checkPayemntStatus(checkoutId);


                    /* check the result of synchronous transaction */
                    fireBroadcast(Config.SUCCESS, "checkoutId=" + checkoutId);
                } else {
                    /* wait for the asynchronous transaction callback in the onNewIntent() */
                }

                break;
            case CheckoutActivity.RESULT_CANCELED:
                fireBroadcast(Config.FAILED, "Shoper cancelled transaction");
                break;
            case CheckoutActivity.RESULT_ERROR:
                PaymentError error = data.getParcelableExtra(CheckoutActivity.CHECKOUT_RESULT_ERROR);
                fireBroadcast(Config.FAILED, error.getErrorMessage());
                break;
            default:
                break;
        }
    }

    private void checkPayemntStatus(final String checkoutId) {


        String req = "req";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().PAYMENT_STATUS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject status = new JSONObject(response);

                    JSONObject a = status.getJSONObject("data");
                    transactionId = a.getString("id");

                    savePayment();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", sharedPreferences.getString("Token", ""));
                params.put("checkout_id", checkoutId);
                return params;
            }
        };


//                stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().REMOVE_CART_ITEM);
        AppController.getInstance().addToRequestQueue(stringRequest, req);

    }

    private void savePayment() {

        String req = "req";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().LUMI_SAVE_PAYMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                startActivity(new Intent(CartActivity.this, TicketsPurchasedSuccessfullyActivity.class));


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", sharedPreferences.getString("Token", ""));
                params.put("transaction_id", transactionId);
                params.put("total_amount", totalAmount.getText().toString());
                return params;
            }
        };


//                stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().REMOVE_CART_ITEM);
        AppController.getInstance().addToRequestQueue(stringRequest, req);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent.getScheme().equals("devsupport")) {
            String checkoutId = intent.getData().getQueryParameter("id");
            fireBroadcast(Config.SUCCESS, "checkoutId=" + checkoutId);
        }
    }

    private void fireBroadcast(int code, String message) {
        Intent intent = new Intent();
        intent.setAction("ai.devsupport.peachpay");
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.putExtra("code", code);
        intent.putExtra("response", message);
        sendBroadcast(intent);
    }

    @Override
    public void removeCart(final int position) {


        String req = "req";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().REMOVE_CART_ITEM, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                cartList.remove(position);
                cartAdapter.notifyDataSetChanged();
                CartDetailsApi();
                progressBar.setVisibility(View.GONE);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();


            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", sharedPreferences.getString("Token", ""));
                params.put("cart_id", cartList.get(position).getID());
                return params;
            }
        };


//                stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().REMOVE_CART_ITEM);
        progressBar.setVisibility(View.VISIBLE);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }


    public void checkOut(String mcheckoutId) {


        Set<String> paymentBrands = new LinkedHashSet<String>();

        paymentBrands.add("VISA");
        paymentBrands.add("MASTER");
        paymentBrands.add("AMEX");
        paymentBrands.add("DIRECTDEBIT_SEPA");

        CheckoutSettings checkoutSettings = new CheckoutSettings(mcheckoutId, paymentBrands, Connect.ProviderMode.LIVE);
        checkoutSettings.setShopperResultUrl("com.wedefineapps.fullyloaded.payments://result");

        Intent intent = checkoutSettings.createCheckoutActivityIntent(CartActivity.this);

        startActivityForResult(intent, CheckoutActivity.REQUEST_CODE_CHECKOUT);


//                        ComponentName componentName = new ComponentName(BuildConfig.APPLICATION_ID,
//                                CheckoutBroadcastReceiver.class.getCanonicalName());

//                        Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
//                        intent.putExtra(CheckoutActivity.CHECKOUT_SETTINGS, checkoutSettings);

    }
}
