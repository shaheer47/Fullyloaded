package com.app.fullyloaded.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.app.fullyloaded.Adapters.SimilarCurrentCompetitionsAdapter;
import com.app.fullyloaded.Adapters.SpecificationAdapter;
import com.app.fullyloaded.AppConstants.APIConstant;
import com.app.fullyloaded.Models.CurrentCompetitionsModel;
import com.app.fullyloaded.Models.SpecificationModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;
import com.app.fullyloaded.VolleySupport.AppController;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompetitionsDetailActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView Back, CompetitionImageView;
    MyTextView txtQuiz, txtCompetitionName, txtCompetitionType, txtCompetitionPrice, txtDescription, txtSpecifications, txtErrorTitle, txtCurrentCompetitionTitle;
    RecyclerView CurrentCompetitionsRecyclerView;
    ArrayList<CurrentCompetitionsModel> currentCompetitionsList = new ArrayList<>();
    ProgressBar progressBar;
    String CurrentCompetitionID, CurrentCompetitionPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition_detail);

        getSupportActionBar().hide();

        mContext = this;

        Init();
    }

    public void Init() {
        CurrentCompetitionID = getIntent().getExtras().getString("CurrentCompetitionID");
        CurrentCompetitionPrice = getIntent().getExtras().getString("CurrentCompetitionPrice");

        progressBar = findViewById(R.id.progressBar);

        CompetitionImageView = findViewById(R.id.CompetitionImageView);
        Back = findViewById(R.id.Back);

        txtCompetitionName = findViewById(R.id.txtCompetitionName);
        txtCompetitionType = findViewById(R.id.txtCompetitionType);
        txtCompetitionPrice = findViewById(R.id.txtCompetitionPrice);
        txtSpecifications = findViewById(R.id.txtSpecifications);
        txtDescription = findViewById(R.id.txtDescription);
        txtQuiz = findViewById(R.id.txtQuiz);
        txtErrorTitle = findViewById(R.id.txtErrorTitle);
        txtCurrentCompetitionTitle = findViewById(R.id.txtCurrentCompetitionTitle);

        Back.setOnClickListener(this);

        txtQuiz.setOnClickListener(this);

        CurrentCompetitionsRecyclerView = findViewById(R.id.CurrentCompetitionsRecyclerView);

        CompetitionDetailApi();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                finish();
                break;
            case R.id.txtQuiz:
                Intent intent = new Intent(mContext, ChooseCartItemActivity.class);
                intent.putExtra("CurrentCompetitionID", CurrentCompetitionID);
                intent.putExtra("CurrentCompetitionPrice", CurrentCompetitionPrice);
                startActivity(intent);
                break;
        }
    }

    private void CompetitionDetailApi() {
        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().COMPETITION_DETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    Log.e("Response", "" + APIConstant.getInstance().COMPETITION_DETAIL + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("message");
                    String Status = JsonMain.getString("status");
                    if (Status.equalsIgnoreCase("SUCCESS")) {
                        JSONObject jsonCompetitionDetail = JsonMain.getJSONObject("competitionDetail");
                        String CompetitionImage = jsonCompetitionDetail.getString("competition_image");
                        if (CompetitionImage.equals("") || CompetitionImage.equals("null") || CompetitionImage.equals(null) || CompetitionImage == null) {
                        } else {
                            Glide.with(mContext).load(CompetitionImage).into(CompetitionImageView);
                        }

                        String CompetitionName = jsonCompetitionDetail.getString("competition_name");
                        if (CompetitionName.equals("") || CompetitionName.equals("null") || CompetitionName.equals(null) || CompetitionName == null) {
                        } else {
                            txtCompetitionName.setText(CompetitionName);
                        }

                        String CompetitionType = jsonCompetitionDetail.getString("competition_type");
                        if (CompetitionType.equals("") || CompetitionType.equals("null") || CompetitionType.equals(null) || CompetitionType == null) {
                        } else {
                            txtCompetitionType.setText(CompetitionType);
                        }

                        String CompetitionPrice = jsonCompetitionDetail.getString("competition_price");
                        if (CompetitionPrice.equals("") || CompetitionPrice.equals("null") || CompetitionPrice.equals(null) || CompetitionPrice == null) {
                        } else {
                            txtCompetitionPrice.setText("£" + CompetitionPrice);
                        }

                        String CompetitionDescription = jsonCompetitionDetail.getString("competition_description");
                        String string = "<font color='#767877'>" + CompetitionDescription + "</font>"+"<b><font color='#2969cb'> " + "</b></font>";
                        // String string = CompetitionDescription;

                        if (CompetitionDescription.equals("") || CompetitionDescription.equals("null") || CompetitionDescription.equals(null) || CompetitionDescription == null) {
                        } else {
                            // txtDescription.setText(CompetitionDescription);
                            txtDescription.setText(Html.fromHtml(string), TextView.BufferType.SPANNABLE);
                        }

                        String CompetitionSpecification = jsonCompetitionDetail.getString("competition_specification");
                        String stringSpecification = "<font color='#767877'>" + CompetitionSpecification + "</font>"+"<b><font color='#2969cb'> " + "</b></font>";
                        // String stringSpecification = CompetitionDescription;

                        if (CompetitionSpecification.equals("") || CompetitionSpecification.equals("null") || CompetitionSpecification.equals(null) || CompetitionSpecification == null) {
                        } else {
                            // txtDescription.setText(CompetitionSpecification);
                            txtSpecifications.setText(Html.fromHtml(stringSpecification), TextView.BufferType.SPANNABLE);
                        }

                        JSONArray jsonRelatedCompetition = JsonMain.getJSONArray("related_competiton");
                        for (int i = 0; i < jsonRelatedCompetition.length(); i++) {
                            CurrentCompetitionsModel currentCompetitionsModel = new CurrentCompetitionsModel();
                            currentCompetitionsModel.setCurrentCompetitionID(jsonRelatedCompetition.getJSONObject(i).getString("currTech_competition_id"));
                            currentCompetitionsModel.setCurrentCompetitionImage(jsonRelatedCompetition.getJSONObject(i).getString("currTech_competition_image"));
                            currentCompetitionsModel.setCurrentCompetitionName(jsonRelatedCompetition.getJSONObject(i).getString("currTech_competition_name"));
                            currentCompetitionsModel.setCurrentCompetitionPrice(jsonRelatedCompetition.getJSONObject(i).getString("currTech_competition_price"));
                            currentCompetitionsList.add(currentCompetitionsModel);
                        }
                        if (currentCompetitionsList.size() > 0) {
                            // txtErrorTitle.setVisibility(View.GONE);
                            txtCurrentCompetitionTitle.setVisibility(View.VISIBLE);
                            SimilarCurrentCompetitionsAdapter similarCurrentCompetitionsAdapter = new SimilarCurrentCompetitionsAdapter(mContext, currentCompetitionsList);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, CurrentCompetitionsRecyclerView.HORIZONTAL, false);
                            CurrentCompetitionsRecyclerView.setLayoutManager(mLayoutManager);
                            CurrentCompetitionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            CurrentCompetitionsRecyclerView.setAdapter(similarCurrentCompetitionsAdapter);
                            similarCurrentCompetitionsAdapter.notifyDataSetChanged();
                        } else {
                            txtCurrentCompetitionTitle.setVisibility(View.GONE);
                            // txtErrorTitle.setVisibility(View.VISIBLE);
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
                params.put("competition_id", CurrentCompetitionID);
                Log.e("PARAMETER", "" + APIConstant.getInstance().COMPETITION_DETAIL + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().COMPETITION_DETAIL);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
    }
}
