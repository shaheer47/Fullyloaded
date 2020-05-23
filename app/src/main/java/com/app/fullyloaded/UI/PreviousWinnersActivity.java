package com.app.fullyloaded.UI;

import android.content.Context;
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
import com.app.fullyloaded.Adapters.PreviousWinnersAdapter;
import com.app.fullyloaded.AppConstants.APIConstant;
import com.app.fullyloaded.Models.PreviousWinnersModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;
import com.app.fullyloaded.VolleySupport.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PreviousWinnersActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    RecyclerView PreviousWinnersRecyclerView;
    ArrayList<PreviousWinnersModel> previousWinnersList = new ArrayList<>();
    ImageView Back;
    ProgressBar progressBar;
    MyTextView txtErrorTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_winners);

        getSupportActionBar().hide();

        mContext = this;

        Init();
    }

    public void Init() {
        progressBar = findViewById(R.id.progressBar);

        txtErrorTitle = findViewById(R.id.txtErrorTitle);

        Back = findViewById(R.id.Back);

        Back.setOnClickListener(this);

        PreviousWinnersRecyclerView = findViewById(R.id.PreviousWinnersRecyclerView);

        PreviousWinnersApi();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                finish();
                break;
        }
    }

    private void PreviousWinnersApi() {
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
                        JSONArray jsonPreviousWinners = JsonMain.getJSONArray("previousWinners");
                        for (int i = 0; i < jsonPreviousWinners.length(); i++) {
                            PreviousWinnersModel previousWinnersModel = new PreviousWinnersModel();
                            previousWinnersModel.setProfileImage(jsonPreviousWinners.getJSONObject(i).getString("profile_image"));
                            previousWinnersModel.setWinningDate(jsonPreviousWinners.getJSONObject(i).getString("winning_date"));
                            previousWinnersModel.setUserName(jsonPreviousWinners.getJSONObject(i).getString("user_name"));
                            previousWinnersModel.setDescription(jsonPreviousWinners.getJSONObject(i).getString("description"));
                            previousWinnersModel.setCompetitionID(jsonPreviousWinners.getJSONObject(i).getString("competiotion_id"));
                            previousWinnersModel.setCompetitionName(jsonPreviousWinners.getJSONObject(i).getString("competition_name"));
                            previousWinnersModel.setCompetitionImage(jsonPreviousWinners.getJSONObject(i).getString("competition_image"));
                            previousWinnersModel.setCompetitionSpecification(jsonPreviousWinners.getJSONObject(i).getString("competition_specification"));
                            previousWinnersList.add(previousWinnersModel);
                        }
                        if (previousWinnersList.size() > 0) {
                            txtErrorTitle.setVisibility(View.GONE);
                            PreviousWinnersAdapter previousWinnersAdapter = new PreviousWinnersAdapter(mContext, previousWinnersList);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2, PreviousWinnersRecyclerView.VERTICAL, false);
                            PreviousWinnersRecyclerView.setLayoutManager(mLayoutManager);
                            PreviousWinnersRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            PreviousWinnersRecyclerView.setAdapter(previousWinnersAdapter);
                            previousWinnersAdapter.notifyDataSetChanged();
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
                // params.put("email", edtEmail.getText().toString());
                Log.e("PARAMETER", "" + APIConstant.getInstance().PREVIOUS_WINNERS + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().PREVIOUS_WINNERS);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }
}
