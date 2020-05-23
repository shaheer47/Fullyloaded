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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.app.fullyloaded.Adapters.CurrentCompetitionsAdapter;
import com.app.fullyloaded.AppConstants.APIConstant;
import com.app.fullyloaded.Models.CurrentCompetitionsModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;
import com.app.fullyloaded.VolleySupport.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CurrentCompetitionsActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView Back;
    MyTextView txtErrorTitle, txtCompetitionCategoryTitle;
    RecyclerView CurrentCompetitionsRecyclerView;
    ArrayList<CurrentCompetitionsModel> currentCompetitionsList = new ArrayList<>();
    ProgressBar progressBar;
    String CategoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_competitions);


        getSupportActionBar().hide();

        mContext = this;

        Init();
    }

    public void Init() {
        CategoryName = getIntent().getExtras().getString("CategoryName");

        progressBar = findViewById(R.id.progressBar);

        txtCompetitionCategoryTitle = findViewById(R.id.txtCompetitionCategoryTitle);
        txtErrorTitle = findViewById(R.id.txtErrorTitle);

        txtCompetitionCategoryTitle.setText(CategoryName);

        Back = findViewById(R.id.Back);

        Back.setOnClickListener(this);

        CurrentCompetitionsRecyclerView = findViewById(R.id.CurrentCompetitionsRecyclerView);

        CurrentCompetitionApi();
    }

    private void CurrentCompetitionApi() {
        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().CURRENT_COMPETITION, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    Log.e("Response", "" + APIConstant.getInstance().CURRENT_COMPETITION + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("message");
                    String Status = JsonMain.getString("status");
                    if (Status.equalsIgnoreCase("SUCCESS")) {
                        JSONArray jsonCurrentCompetition = JsonMain.getJSONArray("currentCompetition");
                        for (int i = 0; i < jsonCurrentCompetition.length(); i++) {
                            CurrentCompetitionsModel currentCompetitionsModel = new CurrentCompetitionsModel();
                            currentCompetitionsModel.setCurrentCompetitionID(jsonCurrentCompetition.getJSONObject(i).getString("curr_competition_id"));
                            currentCompetitionsModel.setCurrentCompetitionImage(jsonCurrentCompetition.getJSONObject(i).getString("curr_competition_image"));
                            currentCompetitionsModel.setCurrentCompetitionName(jsonCurrentCompetition.getJSONObject(i).getString("curr_competition_name"));
                            currentCompetitionsModel.setCurrentCompetitionPrice(jsonCurrentCompetition.getJSONObject(i).getString("curr_competition_price"));
                            currentCompetitionsModel.setCurrentCompetitionType(jsonCurrentCompetition.getJSONObject(i).getString("curr_competition_type"));
                            currentCompetitionsList.add(currentCompetitionsModel);
                        }
                        if (currentCompetitionsList.size() > 0) {
                            txtErrorTitle.setVisibility(View.GONE);
                            CurrentCompetitionsAdapter currentCompetitionsAdapter = new CurrentCompetitionsAdapter(mContext, currentCompetitionsList);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2, CurrentCompetitionsRecyclerView.VERTICAL, false);
                            CurrentCompetitionsRecyclerView.setLayoutManager(mLayoutManager);
                            CurrentCompetitionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            CurrentCompetitionsRecyclerView.setAdapter(currentCompetitionsAdapter);
                            currentCompetitionsAdapter.notifyDataSetChanged();
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
                params.put("competitionType", CategoryName);
                Log.e("PARAMETER", "" + APIConstant.getInstance().CURRENT_COMPETITION + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().CURRENT_COMPETITION);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                finish();
                break;
        }
    }
}
