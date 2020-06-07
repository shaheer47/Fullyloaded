package com.app.fullyloaded.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.fullyloaded.AppConstants.APIConstant;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;
import com.app.fullyloaded.VolleySupport.AppController;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView Back;
    RelativeLayout BottomRelativeLayout;
    MyTextView txtPayAmount;
    String QuizID, UserAnswer, Quantity, Price, CompetitionID;
    String TotalAmount, ClientToken;
    SharedPreferences sharedPreferences, preferences, getSharedPreferences;
    int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        getSupportActionBar().hide();

        mContext = this;

        Init();

        BrainTreePaymentApi();
    }

    public void Init() {
        preferences = getSharedPreferences("Token", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        getSharedPreferences = getSharedPreferences("Remember", MODE_PRIVATE);

        TotalAmount = getIntent().getExtras().getString("TotalAmount");
        QuizID = getIntent().getExtras().getString("QuizID");
        UserAnswer = getIntent().getExtras().getString("UserAnswer");
        Quantity = getIntent().getExtras().getString("Quantity");
        Price = getIntent().getExtras().getString("Price");
        CompetitionID = getIntent().getExtras().getString("CompetitionID");

        /*Log.e("QuizID", "" + QuizID);
        Log.e("UserAnswer", "" + UserAnswer);
        Log.e("Quantity", "" + Quantity);
        Log.e("Price", "" + Price);
        Log.e("CompetitionID", "" + CompetitionID);
        Log.e("TotalAmount", "" + TotalAmount);*/

        Back = findViewById(R.id.Back);

        BottomRelativeLayout = findViewById(R.id.BottomRelativeLayout);

        txtPayAmount = findViewById(R.id.txtPayAmount);
        txtPayAmount.setText("Pay €" + TotalAmount);

        Back.setOnClickListener(this);
        BottomRelativeLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                finish();
                break;
            case R.id.BottomRelativeLayout:
                onBrainTreeSubmit(ClientToken);
                break;
        }
    }

    private void BrainTreePaymentApi() {
        String req = "req";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().BRAIN_TREE_PAYMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    Log.e("Response", "" + APIConstant.getInstance().BRAIN_TREE_PAYMENT + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String Success = JsonMain.getString("success");
                    if (Success.equalsIgnoreCase("true")) {
                        ClientToken = JsonMain.getString("token");
                        Log.e("ClientToken", "" + ClientToken);
                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "generate");
                Log.e("PARAMETER", "" + APIConstant.getInstance().BRAIN_TREE_PAYMENT + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().BRAIN_TREE_PAYMENT);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }

    public void onBrainTreeSubmit(String ClientToken) {
        DropInRequest dropInRequest = new DropInRequest().clientToken(ClientToken);
        startActivityForResult(dropInRequest.getIntent(mContext), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                // use the result to update your UI and send the payment method nonce to your server
                String Nonce = result.getPaymentMethodNonce().getNonce();
                Log.e("Nonce", "" + Nonce);
                postNonceToServer(Nonce);
            } else if (resultCode == RESULT_CANCELED) {
                // the user canceled
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
            }
        }
    }

    public void postNonceToServer(final String nonce) {
        String req = "req";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().BRAIN_TREE_PAYMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    Log.e("Response", "" + APIConstant.getInstance().BRAIN_TREE_PAYMENT + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("massege");
                    String Success = JsonMain.getString("success");
                    if (Success.equalsIgnoreCase("true")) {
                        String TransactionID = JsonMain.getString("transaction_id");
                        Log.e("TransactionID", "" + TransactionID);
                        SavePaymentApi(TransactionID);
                    } else {
                        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("action", "payment");
                params.put("amount", TotalAmount);
                params.put("payment_method_nonce", nonce);
                params.put("firstName", sharedPreferences.getString("Name", ""));
                params.put("email", sharedPreferences.getString("Email", ""));
                Log.e("PARAMETER", "" + APIConstant.getInstance().BRAIN_TREE_PAYMENT + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().BRAIN_TREE_PAYMENT);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }

    public void SavePaymentApi(final String TransactionID) {
        String req = "req";
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().SAVE_PAYMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    Log.e("Response", "" + APIConstant.getInstance().SAVE_PAYMENT + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("message");
                    String Status = JsonMain.getString("status");
                    if (Status.equalsIgnoreCase("SUCCESS")) {
                        Intent intent = new Intent(mContext, TicketsPurchasedSuccessfullyActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", sharedPreferences.getString("Token", ""));
                params.put("transaction_id", TransactionID);
                params.put("total_paid", TotalAmount);
                Log.e("PARAMETER", "" + APIConstant.getInstance().SAVE_PAYMENT + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().SAVE_PAYMENT);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }
}
