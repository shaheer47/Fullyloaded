package com.app.fullyloaded.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.app.fullyloaded.sharedPreference.SharedPreferencesEditor;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChooseCartItemActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView Back;
    RelativeLayout BottomRelativeLayout, PlusQuantityRelativeLayout, LessQuantityRelativeLayout;
    ProgressBar progressBar;
    MyTextView txtQuiz, txtQuantity;
    String QuizID, Quiz, OptionID, OptionA, OptionB, OptionC, OptionD;
    RadioGroup OptionRadioGroup;
    RadioButton OptionOneRadioButton, OptionTwoRadioButton, OptionThreeRadioButton, OptionFourRadioButton;
    int Quantity = 1;
    boolean isChecked = false;
    String CurrentCompetitionID, CurrentCompetitionPrice, UserAnswer;
    SharedPreferences sharedPreferences, preferences, getSharedPreferences;
    SharedPreferencesEditor localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_cartitem);

        getSupportActionBar().hide();

        mContext = this;
        localStorage = new SharedPreferencesEditor(this);

        Init();
    }

    public void Init() {
        CurrentCompetitionID = getIntent().getExtras().getString("CurrentCompetitionID");
        CurrentCompetitionPrice = getIntent().getExtras().getString("CurrentCompetitionPrice");

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        preferences = getSharedPreferences("Token", MODE_PRIVATE);
        getSharedPreferences = getSharedPreferences("Remember", MODE_PRIVATE);

        progressBar = findViewById(R.id.progressBar);

        Back = findViewById(R.id.Back);

        OptionRadioGroup = findViewById(R.id.OptionRadioGroup);

        OptionOneRadioButton = findViewById(R.id.OptionOneRadioButton);
        OptionTwoRadioButton = findViewById(R.id.OptionTwoRadioButton);
        OptionThreeRadioButton = findViewById(R.id.OptionThreeRadioButton);
        OptionFourRadioButton = findViewById(R.id.OptionFourRadioButton);

        txtQuiz = findViewById(R.id.txtQuiz);
        txtQuantity = findViewById(R.id.txtQuantity);

        txtQuantity.setText(String.valueOf(Quantity));

        BottomRelativeLayout = findViewById(R.id.BottomRelativeLayout);
        PlusQuantityRelativeLayout = findViewById(R.id.PlusQuantityRelativeLayout);
        LessQuantityRelativeLayout = findViewById(R.id.LessQuantityRelativeLayout);

        Back.setOnClickListener(this);
        BottomRelativeLayout.setOnClickListener(this);
        PlusQuantityRelativeLayout.setOnClickListener(this);
        LessQuantityRelativeLayout.setOnClickListener(this);

        // Log.e("IsChecked","" + isChecked);

        OptionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
                isChecked = checkedRadioButton.isChecked();
                // Log.e("IsChecked","" + isChecked);
                if (isChecked) {
                    UserAnswer = checkedRadioButton.getText().toString();
                    // Log.e("UserAnswer","" + UserAnswer);
                    Toast.makeText(mContext, checkedRadioButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ChooseTicketApi();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                finish();
                break;
            case R.id.PlusQuantityRelativeLayout:
                Quantity = Quantity + 1;
                txtQuantity.setText(String.valueOf(Quantity));
                break;
            case R.id.LessQuantityRelativeLayout:
                if (Quantity == 1) {

                } else {
                    Quantity = Quantity - 1;
                    txtQuantity.setText(String.valueOf(Quantity));
                }
                break;
            case R.id.BottomRelativeLayout:
                if (!localStorage.IsLoggedIn()) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (isChecked == false) {
                        Toast.makeText(mContext, "Please Select One Answer...", Toast.LENGTH_SHORT).show();
                    } else {
                        AddCartApi();
                    }
                }
                break;
        }
    }

    private void ChooseTicketApi() {
        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().CHOOSE_TICKET, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    Log.e("Response", "" + APIConstant.getInstance().CHOOSE_TICKET + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("message");
                    String Status = JsonMain.getString("status");
                    if (Status.equalsIgnoreCase("SUCCESS")) {
                        JSONObject jsonChooseTicket = JsonMain.getJSONObject("chooseTicket");
                        QuizID = jsonChooseTicket.getString("quiz_id");
                        Quiz = jsonChooseTicket.getString("quiz");
                        txtQuiz.setText(Quiz);
                        OptionID = jsonChooseTicket.getString("option_id");
                        OptionA = jsonChooseTicket.getString("option_a");
                        OptionOneRadioButton.setText(OptionA);
                        OptionB = jsonChooseTicket.getString("option_b");
                        OptionTwoRadioButton.setText(OptionB);
                        OptionC = jsonChooseTicket.getString("option_c");
                        OptionThreeRadioButton.setText(OptionC);
                        OptionD = jsonChooseTicket.getString("option_d");
                        OptionFourRadioButton.setText(OptionD);
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
                // params.put("", "");
                Log.e("PARAMETER", "" + APIConstant.getInstance().CHOOSE_TICKET + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().CHOOSE_TICKET);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }

    private void AddCartApi() {
        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().ADD_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    Log.e("Response", "" + APIConstant.getInstance().ADD_CART + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("message");
                    String Status = JsonMain.getString("status");
                    if (Status.equalsIgnoreCase("SUCCESS")) {
                        JSONObject jsonAddCart = JsonMain.getJSONObject("addCart");
                        Intent intent = new Intent(mContext, CartActivity.class);
                        intent.putExtra("Token", jsonAddCart.getString("token"));
                        intent.putExtra("CompetitionID", jsonAddCart.getString("competition_id"));
                        intent.putExtra("QuizID", jsonAddCart.getString("quiz_id"));
                        intent.putExtra("UserAnswer", jsonAddCart.getString("user_answer"));
                        intent.putExtra("Quantity", jsonAddCart.getString("quantity"));
                        intent.putExtra("Price", jsonAddCart.getString("price"));
                        intent.putExtra("UserID", jsonAddCart.getString("user_id"));
                        intent.putExtra("ProductID", jsonAddCart.getString("product_id"));
                        intent.putExtra("Inner", "1");
                        startActivity(intent);
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
                params.put("competition_id", CurrentCompetitionID);
                params.put("quiz_id", QuizID);
                params.put("user_answer", UserAnswer);
                params.put("quantity", txtQuantity.getText().toString());
                params.put("price", CurrentCompetitionPrice);
                Log.e("PARAMETER", "" + APIConstant.getInstance().ADD_CART + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().ADD_CART);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }
}
