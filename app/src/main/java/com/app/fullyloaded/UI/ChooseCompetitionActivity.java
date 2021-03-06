package com.app.fullyloaded.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
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
import com.app.fullyloaded.Models.CategoryModel;
import com.app.fullyloaded.Models.CurrentCompetitionsModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;
import com.app.fullyloaded.VolleySupport.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChooseCompetitionActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout dropdown_layout;

    Context mContext;
    ImageView Back, dropDown;
    MyTextView txtErrorTitle, txtSelectTitle, txtParticipate;
    ArrayList<CategoryModel> CategoryList = new ArrayList<>();
    ProgressBar progressBar;
    String Category = "";
    MyTextView txtCompetitionCategoryTitle;
    RecyclerView CurrentCompetitionsRecyclerView;
    ArrayList<CurrentCompetitionsModel> currentCompetitionsList = new ArrayList<>();
    String CategoryName;
    SharedPreferences sharedPreferences, preferences, getSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_competitions);

        getSupportActionBar().hide();

        mContext = this;

        Toast.makeText(mContext, "Please Select a Competition", Toast.LENGTH_SHORT).show();

        Init();
    }

    public void Init() {
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        preferences = getSharedPreferences("Token", MODE_PRIVATE);
        getSharedPreferences = getSharedPreferences("Remember", MODE_PRIVATE);

//        txtCompetitionCategoryTitle = findViewById(R.id.txtCompetitionCategoryTitle);
//        txtCompetitionCategoryTitle.setText(CategoryName);
        CurrentCompetitionsRecyclerView = findViewById(R.id.CurrentCompetitionsRecyclerView);

        dropdown_layout = findViewById(R.id.select);

        progressBar = findViewById(R.id.progressBar);

        dropDown = findViewById(R.id.drop_down);

        txtErrorTitle = findViewById(R.id.txtErrorTitle);
        txtSelectTitle = findViewById(R.id.txtSelectTitle);
        txtParticipate = findViewById(R.id.txtParticipate);

        Back = findViewById(R.id.Back);

        Back.setOnClickListener(this);
        txtSelectTitle.setOnClickListener(this);
        dropDown.setOnClickListener(this);

        txtParticipate.setVisibility(View.GONE);
        txtParticipate.setOnClickListener(this);

        CategoryListApi();
//        CurrentCompetitionApi();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                finish();
                break;
            case R.id.txtSelectTitle:
                showPopupMenu(view);
                break;
            case R.id.drop_down:
                showPopupMenu(txtSelectTitle);
                break;

//            case R.id.select:
//                showPopupMenu(txtSelectTitle);
//                break;


//       case R.id.txtParticipate:
//                if (Category.equals("")) {
//                    Toast.makeText(mContext, "Please Select Your Category", Toast.LENGTH_SHORT).show();
//                } else {
//                    CategoryName = Category;
//                    CurrentCompetitionApi();
//                break;

                }
        }


    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(mContext, view);
        popup.getMenuInflater().inflate(R.menu.choose_category_popup, popup.getMenu());
        popup.getMenu().removeGroup(0);
        for (int i = 0; i < CategoryList.size(); i++) {
            CategoryModel categoryModel = CategoryList.get(i);
            popup.getMenu().add(categoryModel.getCategoryName());
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                currentCompetitionsList.clear();
                CurrentCompetitionsAdapter currentCompetitionsAdapter = new CurrentCompetitionsAdapter(mContext, currentCompetitionsList);
                CurrentCompetitionsRecyclerView.setAdapter(currentCompetitionsAdapter);
                Category = String.valueOf(menuItem.getTitle());
                txtSelectTitle.setText(Category);

                CategoryName = Category;
                CurrentCompetitionApi();
                return true;
            }
        });
        popup.show();
    }

    private void CategoryListApi() {
        String req = "req";
        progressBar.setVisibility(View.VISIBLE);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, APIConstant.getInstance().CATEGORY_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                try {
                    progressBar.setVisibility(View.GONE);
                    Log.e("Response", "" + APIConstant.getInstance().CATEGORY_LIST + response);
                    JSONObject JsonMain = new JSONObject(response);
                    String message = JsonMain.getString("message");
                    String Status = JsonMain.getString("status");
                    if (Status.equalsIgnoreCase("SUCCESS")) {
                        JSONArray jsonCategoryList = JsonMain.getJSONArray("categoryList");
                        for (int i = 0; i < jsonCategoryList.length(); i++) {
                            CategoryModel categoryModel = new CategoryModel();
                            categoryModel.setCategoryID(jsonCategoryList.getJSONObject(i).getString("id"));
                            categoryModel.setCategoryName(jsonCategoryList.getJSONObject(i).getString("name"));
                            categoryModel.setCategoryCreatedAt(jsonCategoryList.getJSONObject(i).getString("created_at"));
                            categoryModel.setCategoryUpdatedAt(jsonCategoryList.getJSONObject(i).getString("updated_at"));
                            CategoryList.add(categoryModel);
                        }
                        /*if (CategoryList.size() > 0) {
                            txtErrorTitle.setVisibility(View.GONE);
                            CategoryAdapter  categoryAdapter = new CategoryAdapter(mContext, CategoryList);
                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2, ChooseCompetitionRecyclerView.VERTICAL, false);
                            ChooseCompetitionRecyclerView.setLayoutManager(mLayoutManager);
                            ChooseCompetitionRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            ChooseCompetitionRecyclerView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            txtErrorTitle.setVisibility(View.VISIBLE);
                        }*/
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
                Log.e("PARAMETER", "" + APIConstant.getInstance().CATEGORY_LIST + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().getCache().remove(APIConstant.getInstance().CATEGORY_LIST);
        AppController.getInstance().addToRequestQueue(stringRequest, req);
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

                            if(jsonCurrentCompetition.getJSONObject(i).getJSONObject("sale").getString("status").equals("SUCCESS")){

                                currentCompetitionsModel.setCurrentCompetitionPrice(jsonCurrentCompetition.getJSONObject(i).getJSONObject("sale").getString("price_after_sale"));
                                currentCompetitionsModel.setCurrentCompetitionSalePrice(jsonCurrentCompetition.getJSONObject(i).getString("curr_competition_price"));
                            }
                            else{
                                currentCompetitionsModel.setCurrentCompetitionSalePrice("");
                            }


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
}
