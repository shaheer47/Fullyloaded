package com.app.fullyloaded.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.fullyloaded.Adapters.HomeCurrentCompetitionsAdapter;
import com.app.fullyloaded.Adapters.HomeCurrentTechCompetitionsAdapter;
import com.app.fullyloaded.Adapters.HomePreviousWinnersAdapter;
import com.app.fullyloaded.Adapters.HomeSliderAdapter;
import com.app.fullyloaded.AppConstants.APIConstant;
import com.app.fullyloaded.AppConstants.LinePagerIndicatorDecoration;
import com.app.fullyloaded.Models.CartModel;
import com.app.fullyloaded.Models.CurrentCompetitionsModel;
import com.app.fullyloaded.Models.HomeCurrentTechCompetitionsModel;
import com.app.fullyloaded.Models.PreviousWinnersModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;
import com.app.fullyloaded.VolleySupport.AppController;
import com.app.fullyloaded.sharedPreference.SharedPreferencesEditor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends BaseActivity {

    boolean doubleBackToExitPressedOnce = false;
    SharedPreferences preferences, sharedPreferences, getSharedPreferences;
    RecyclerView SliderRecyclerView, CurrentCompetitionRecyclerView, CurrentTechCompetitionRecyclerView, PreviousWinnersRecyclerView;
    ProgressBar progressBar;
    ArrayList<String> BannerList = new ArrayList<>();
    ArrayList<CurrentCompetitionsModel> currentCompetitionList = new ArrayList<>();
    ArrayList<HomeCurrentTechCompetitionsModel> currentTechCompetitionList = new ArrayList<>();
    ArrayList<PreviousWinnersModel> previousWinnersList = new ArrayList<>();
    MyTextView txtBannerErrorTitle, txtCurrentCompetitionsTitle, txtCurrentTechCompetitionsTitle, txtPreviousWinnersTitle;
    MyTextView txtCartCount, txtTitle, txtDescription;
    ImageView LeftArrow, RightArrow;
    int position, movePosition = 0;
    SharedPreferencesEditor localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movePosition > 0) {
                    movePosition = movePosition - 2;
                    PreviousWinnersRecyclerView.smoothScrollToPosition(movePosition);
                }
            }
        });

        RightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movePosition <= BannerList.size()) {
                    movePosition = movePosition + 2;
                    PreviousWinnersRecyclerView.smoothScrollToPosition(movePosition);
                }
            }
        });
        localStorage = new SharedPreferencesEditor(this);
        Init();

    }

    private void Timer() {

        Timer timer;
        position = -1;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    public void run() {
                        if (position == (BannerList.size())) {
                            position = -1;
                            position++;
                        } else {
                            position++;
                        }
                        SliderRecyclerView.smoothScrollToPosition(position);

                        Log.d("TAG", "run: " + position);

//                        recyclerview.smoothScrollToPosition(position)
                    }
                });

            }
        }, 1000, 2000); // delay*/

    }

    public void Init() {
        preferences = getSharedPreferences("Token", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        getSharedPreferences = getSharedPreferences("Remember", MODE_PRIVATE);

        LeftArrow = findViewById(R.id.LeftArrow);
        RightArrow = findViewById(R.id.RightArrow);

        txtCartCount = findViewById(R.id.txtCartCount);
        txtTitle = findViewById(R.id.txtTitle);
        txtDescription = findViewById(R.id.txtDescription);

        txtBannerErrorTitle = findViewById(R.id.txtBannerErrorTitle);
        txtCurrentCompetitionsTitle = findViewById(R.id.txtCurrentCompetitionsTitle);
        txtCurrentTechCompetitionsTitle = findViewById(R.id.txtCurrentTechCompetitionsTitle);
        txtPreviousWinnersTitle = findViewById(R.id.txtPreviousWinnersTitle);

        progressBar = findViewById(R.id.progressBar);

        SliderRecyclerView = findViewById(R.id.SliderRecyclerView);

        CurrentCompetitionRecyclerView = findViewById(R.id.CurrentCompetitionRecyclerView);

        CurrentTechCompetitionRecyclerView = findViewById(R.id.CurrentTechCompetitionRecyclerView);

        PreviousWinnersRecyclerView = findViewById(R.id.PreviousWinnersRecyclerView);

        HomePageApi();
    }

    private void HomePageApi() {
        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        BannerList.clear();
        currentCompetitionList.clear();
        currentTechCompetitionList.clear();
        previousWinnersList.clear();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().HOME_PAGE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    txtTitle.setVisibility(View.VISIBLE);
                    txtDescription.setVisibility(View.VISIBLE);
                    Log.e("Response", "" + APIConstant.getInstance().HOME_PAGE + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("message");
                    String Status = JsonMain.getString("status");
                    if (Status.equalsIgnoreCase("SUCCESS")) {
                        JSONObject jsonHomePage = JsonMain.getJSONObject("homePage");
                        JSONArray jsonBannerImage = jsonHomePage.getJSONArray("banner_image");
                        for (int i = 0; i < jsonBannerImage.length(); i++) {
                            String BannerImage = jsonBannerImage.optString(i);
                            // Log.e("image", "" + BannerImage);
                            BannerList.add(BannerImage);
                        }
                        if (BannerList.size() > 0) {
                            txtBannerErrorTitle.setVisibility(View.GONE);
                            HomeSliderAdapter homeSliderAdapter = new HomeSliderAdapter(mContext, BannerList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
                            SliderRecyclerView.setLayoutManager(mLayoutManager);
                            SliderRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            SliderRecyclerView.addItemDecoration(new LinePagerIndicatorDecoration());
                            PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
                            pagerSnapHelper.attachToRecyclerView(SliderRecyclerView);
                            SliderRecyclerView.setAdapter(homeSliderAdapter);
                            homeSliderAdapter.notifyDataSetChanged();
                        } else {
                            txtBannerErrorTitle.setVisibility(View.VISIBLE);
                        }

                        JSONArray jsonCurrentCompetition = jsonHomePage.getJSONArray("current_competition");
                        for (int i = 0; i < jsonCurrentCompetition.length(); i++) {
                            CurrentCompetitionsModel currentCompetitionsModel = new CurrentCompetitionsModel();
                            currentCompetitionsModel.setCurrentCompetitionID(jsonCurrentCompetition.getJSONObject(i).getString("curr_competition_id"));
                            currentCompetitionsModel.setCurrentCompetitionImage(jsonCurrentCompetition.getJSONObject(i).getString("curr_competition_image"));
                            currentCompetitionsModel.setCurrentCompetitionName(jsonCurrentCompetition.getJSONObject(i).getString("curr_competition_name"));
                            currentCompetitionsModel.setCurrentCompetitionPrice(jsonCurrentCompetition.getJSONObject(i).getString("curr_competition_price"));

                            JSONObject sale = jsonCurrentCompetition.getJSONObject(i).getJSONObject("sale");
                            if (sale.getString("status").equals("SUCCESS")) {
                                double salePrice = sale.getDouble("price_after_sale");
                                currentCompetitionsModel.setCurrentCompetitionSalePrice(salePrice + "");
                            }

                            currentCompetitionList.add(currentCompetitionsModel);
                        }
                        if (currentCompetitionList.size() > 0) {
                            txtCurrentCompetitionsTitle.setVisibility(View.VISIBLE);
                            HomeCurrentCompetitionsAdapter homeCurrentCompetitionsAdapter = new HomeCurrentCompetitionsAdapter(mContext, currentCompetitionList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, CurrentCompetitionRecyclerView.HORIZONTAL, false);
                            CurrentCompetitionRecyclerView.setLayoutManager(mLayoutManager);
                            CurrentCompetitionRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            CurrentCompetitionRecyclerView.setAdapter(homeCurrentCompetitionsAdapter);
                            homeCurrentCompetitionsAdapter.notifyDataSetChanged();
                        } else {
                            txtCurrentCompetitionsTitle.setVisibility(View.GONE);
                        }

                        JSONArray jsonCurrentTechCompetition = jsonHomePage.getJSONArray("currentTech_competition");
                        for (int j = 0; j < jsonCurrentTechCompetition.length(); j++) {
                            HomeCurrentTechCompetitionsModel homeCurrentTechCompetitionsModel = new HomeCurrentTechCompetitionsModel();
                            homeCurrentTechCompetitionsModel.setCurrentTechCompetitionID(jsonCurrentTechCompetition.getJSONObject(j).getString("currTech_competition_id"));
                            homeCurrentTechCompetitionsModel.setCurrentTechCompetitionImage(jsonCurrentTechCompetition.getJSONObject(j).getString("currTech_competition_image"));
                            homeCurrentTechCompetitionsModel.setCurrentTechCompetitionName(jsonCurrentTechCompetition.getJSONObject(j).getString("currTech_competition_name"));
                            homeCurrentTechCompetitionsModel.setCurrentTechCompetitionPrice(jsonCurrentTechCompetition.getJSONObject(j).getString("currTech_competition_price"));

                            JSONObject sale = jsonCurrentTechCompetition.getJSONObject(j).getJSONObject("sale");
                            if (sale.getString("status").equals("SUCCESS")) {
                                double salePrice = sale.getDouble("price_after_sale");
                                homeCurrentTechCompetitionsModel.setCurrentTechCompetitionSalePrice(salePrice + "");
                            }

                            currentTechCompetitionList.add(homeCurrentTechCompetitionsModel);
                        }
                        if (currentTechCompetitionList.size() > 0) {
                            txtCurrentTechCompetitionsTitle.setVisibility(View.VISIBLE);
                            HomeCurrentTechCompetitionsAdapter homeCurrentTechCompetitionsAdapter = new HomeCurrentTechCompetitionsAdapter(mContext, currentTechCompetitionList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
                            CurrentTechCompetitionRecyclerView.setLayoutManager(mLayoutManager);
                            CurrentTechCompetitionRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            CurrentTechCompetitionRecyclerView.setAdapter(homeCurrentTechCompetitionsAdapter);
                            homeCurrentTechCompetitionsAdapter.notifyDataSetChanged();
                        } else {
                            txtCurrentTechCompetitionsTitle.setVisibility(View.GONE);
                        }




                        getPreviousWinners();

                        ///////////////////////////////////////////Wrong Get strings
//                        JSONArray jsonPreviousWinner = jsonHomePage.getJSONArray("previous_winner");
////                        if(jsonPreviousWinner.length() > 0){
////
////                            LeftArrow.setVisibility(View.VISIBLE);
////                            RightArrow.setVisibility(View.VISIBLE);
////
////                        }
//                        for (int k = 0; k < jsonPreviousWinner.length(); k++) {
//                            PreviousWinnersModel previousWinnersModel = new PreviousWinnersModel();
//                            previousWinnersModel.setUserName(jsonPreviousWinner.getJSONObject(k).getString("user_name"));
//                            previousWinnersModel.setProfileImage(jsonPreviousWinner.getJSONObject(k).getString("profile_image"));
//                            previousWinnersModel.setWinningDate(jsonPreviousWinner.getJSONObject(k).getString("winning_date"));
//                            previousWinnersModel.setProductID(jsonPreviousWinner.getJSONObject(k).getString("product_id"));
//                            previousWinnersList.add(previousWinnersModel);
//                        }
//                        if (previousWinnersList.size() > 0) {
//                            txtPreviousWinnersTitle.setVisibility(View.VISIBLE);
//                            HomePreviousWinnersAdapter homePreviousWinnersAdapter = new HomePreviousWinnersAdapter(mContext, previousWinnersList);
//                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
//                            PreviousWinnersRecyclerView.setLayoutManager(mLayoutManager);
//                            PreviousWinnersRecyclerView.setItemAnimator(new DefaultItemAnimator());
//                            PreviousWinnersRecyclerView.setAdapter(homePreviousWinnersAdapter);
//                            homePreviousWinnersAdapter.notifyDataSetChanged();
//                        } else {
//                            txtPreviousWinnersTitle.setVisibility(View.GONE);
//                        }
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
                /*params.put("email", edtEmail.getText().toString());*/
                Log.e("PARAMETER", "" + APIConstant.getInstance().HOME_PAGE + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().HOME_PAGE);
        AppController.getInstance().addToRequestQueue(stringRequest, req);

        Timer();

    }

    private void getPreviousWinners() {
        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        previousWinnersList.clear();
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, APIConstant.getInstance().PREVIOUS_WINNERS, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    Log.e("Response", "" + APIConstant.getInstance().PREVIOUS_WINNERS + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("message");
                    String Status = JsonMain.getString("status");
                    if (Status.equalsIgnoreCase("SUCCESS")) {
                        JSONArray jsonPreviousWinners = JsonMain.getJSONArray("data");
                        for (int i = 0; i < jsonPreviousWinners.length(); i++) {
                            PreviousWinnersModel previousWinnersModel = new PreviousWinnersModel();

                            previousWinnersModel.setProfileImage(jsonPreviousWinners.getJSONObject(i).getString("image"));
                            previousWinnersModel.setDescription(jsonPreviousWinners.getJSONObject(i).getString("description"));
                            previousWinnersModel.setUserName(jsonPreviousWinners.getJSONObject(i).getString("name"));

//                            previousWinnersModel.setProfileImage(jsonPreviousWinners.getJSONObject(i).getString("profile_image"));
//                            previousWinnersModel.setWinningDate(jsonPreviousWinners.getJSONObject(i).getString("winning_date"));
//                            previousWinnersModel.setUserName(jsonPreviousWinners.getJSONObject(i).getString("user_name"));
//                            previousWinnersModel.setDescription(jsonPreviousWinners.getJSONObject(i).getString("description"));
//                            previousWinnersModel.setCompetitionID(jsonPreviousWinners.getJSONObject(i).getString("competiotion_id"));
//                            previousWinnersModel.setCompetitionName(jsonPreviousWinners.getJSONObject(i).getString("competition_name"));
//                            previousWinnersModel.setCompetitionImage(jsonPreviousWinners.getJSONObject(i).getString("competition_image"));
//                            previousWinnersModel.setCompetitionSpecification(jsonPreviousWinners.getJSONObject(i).getString("competition_specification"));
                            previousWinnersList.add(previousWinnersModel);
                        }
                        if (previousWinnersList.size() > 0) {
                            RightArrow.setVisibility(View.VISIBLE);
                            LeftArrow.setVisibility(View.VISIBLE);

                            txtPreviousWinnersTitle.setVisibility(View.VISIBLE);
                            HomePreviousWinnersAdapter previousWinnersAdapter = new HomePreviousWinnersAdapter(mContext, previousWinnersList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
                            PreviousWinnersRecyclerView.setLayoutManager(mLayoutManager);
                            PreviousWinnersRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            PreviousWinnersRecyclerView.setAdapter(previousWinnersAdapter);
                            previousWinnersAdapter.notifyDataSetChanged();
                        } else {
                            txtPreviousWinnersTitle.setVisibility(View.GONE);
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
                // params.put("email", edtEmail.getText().toString());
                Log.e("PARAMETER", "" + APIConstant.getInstance().PREVIOUS_WINNERS + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().PREVIOUS_WINNERS);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }

    private void CartDetailsApi() {
        String req = "req";
        cartList.clear();
        // progressBar.setVisibility(View.VISIBLE);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().CART_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    // progressBar.setVisibility(View.GONE);
                    Log.e("Response", "" + APIConstant.getInstance().CART_DETAILS + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("message");
                    String Status = JsonMain.getString("status");
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
                            String CartCount = String.valueOf(cartList.size());
                            txtCartCount.setText(CartCount);
                        } else {
                            txtCartCount.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    // progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        // progressBar.setVisibility(View.GONE);
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

    @Override
    protected void onResume() {

        if (!localStorage.IsLoggedIn()) {
            txtCartCount.setVisibility(View.GONE);
        } else {
            CartDetailsApi();
        }
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please Click  BACK  Again To Exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
