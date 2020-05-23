package com.app.fullyloaded.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Adapters.DrawerAdapter;
import com.app.fullyloaded.Models.CartModel;
import com.app.fullyloaded.Models.DrawerModel;
import com.app.fullyloaded.R;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity implements DrawerAdapter.ClickListener {

    DrawerLayout DrawerLayout;
    Context mContext;
    FrameLayout FrameLayout;
    Toolbar toolbar;
    RecyclerView DrawerRecyclerView;
    ArrayList<DrawerModel> drawerModelArrayList = new ArrayList<>();
    ArrayList<CartModel> cartList = new ArrayList<>();
    SharedPreferences preferences, sharedPreferences, getSharedPreferences;

    @Override
    public void setContentView(int layoutResID) {
        DrawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        mContext = this;
        FrameLayout = DrawerLayout.findViewById(R.id.FrameLayout);
        getLayoutInflater().inflate(layoutResID, FrameLayout, true);
        super.setContentView(DrawerLayout);

        Init();

        setDrawerRecyclerView();

        preferences = getSharedPreferences("Token", MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        getSharedPreferences = getSharedPreferences("Remember", MODE_PRIVATE);
    }

    public void Init() {
        toolbar = findViewById(R.id.toolbar);

        if (useToolbar()) {
            setSupportActionBar(toolbar);
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }

    public void setDrawerRecyclerView() {
        DrawerRecyclerView = findViewById(R.id.DrawerRecyclerView);

        DrawerModel drawerModel;

        drawerModelArrayList.clear();
        drawerModel = new DrawerModel("0", getResources().getString(R.string.home));
        drawerModelArrayList.add(drawerModel);

        drawerModel = new DrawerModel("1", getResources().getString(R.string.current_competitions));
        drawerModelArrayList.add(drawerModel);

        drawerModel = new DrawerModel("2", getResources().getString(R.string.previous_winners));
        drawerModelArrayList.add(drawerModel);

        drawerModel = new DrawerModel("3", getResources().getString(R.string.my_account));
        drawerModelArrayList.add(drawerModel);

        drawerModel = new DrawerModel("4", getResources().getString(R.string.terms_condition));
        drawerModelArrayList.add(drawerModel);

        drawerModel = new DrawerModel("5", getResources().getString(R.string.privacy_policy));
        drawerModelArrayList.add(drawerModel);

        DrawerAdapter drawerAdapter = new DrawerAdapter(mContext, drawerModelArrayList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, DrawerRecyclerView.VERTICAL, false);
        DrawerRecyclerView.setLayoutManager(mLayoutManager);
        DrawerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DrawerRecyclerView.setAdapter(drawerAdapter);
        drawerAdapter.notifyDataSetChanged();
    }

    protected boolean useToolbar() {
        return true;
    }

    public void OpenMenu(View view) {
        DrawerLayout = findViewById(R.id.DrawerLayout);
        if (DrawerLayout.isDrawerOpen(GravityCompat.START)) {
            DrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            DrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void OpenCart(View view) {
        Intent intent = new Intent(mContext, CartActivity.class);
        intent.putExtra("Inner", "0");
        startActivity(intent);
    }

    @Override
    public void OnClickDrawerItem(DrawerModel drawerModel) {
        String UserID = sharedPreferences.getString("UserID", "");
        // Log.e("UserID", "" + UserID);
        if (drawerModel.getID().equals("0")) {
            /*if (UserID.equals("0")) {
                if (!(mContext instanceof LoginActivity)) {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
                DrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                if (!(mContext instanceof HomeActivity)) {
                    mContext.startActivity(new Intent(mContext, HomeActivity.class));
                }
                DrawerLayout.closeDrawer(GravityCompat.START);
            }*/
            if (!(mContext instanceof HomeActivity)) {
                mContext.startActivity(new Intent(mContext, HomeActivity.class));
            }
            DrawerLayout.closeDrawer(GravityCompat.START);
        } else if (drawerModel.getID().equals("1")) {
            if (UserID.equals("0")) {
                if (!(mContext instanceof LoginActivity)) {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
                DrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                if (!(mContext instanceof ChooseCompetitionActivity)) {
                    mContext.startActivity(new Intent(mContext, ChooseCompetitionActivity.class));
                }
                DrawerLayout.closeDrawer(GravityCompat.START);
            }
        } else if (drawerModel.getID().equals("2")) {
            /*if (UserID.equals("0")) {
                if (!(mContext instanceof LoginActivity)) {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
                DrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                if (!(mContext instanceof PreviousWinnersActivity)) {
                    mContext.startActivity(new Intent(mContext, PreviousWinnersActivity.class));
                }
                DrawerLayout.closeDrawer(GravityCompat.START);
            }*/
            if (!(mContext instanceof PreviousWinnersActivity)) {
                mContext.startActivity(new Intent(mContext, PreviousWinnersActivity.class));
            }
            DrawerLayout.closeDrawer(GravityCompat.START);
        } else if (drawerModel.getID().equals("3")) {
            if (UserID.equals("0")) {
                if (!(mContext instanceof LoginActivity)) {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
                DrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                if (!(mContext instanceof MyAccountActivity)) {
                    mContext.startActivity(new Intent(mContext, MyAccountActivity.class));
                }
                DrawerLayout.closeDrawer(GravityCompat.START);
            }
        } else if (drawerModel.getID().equals("4")) {
            /*if (UserID.equals("0")) {
                if (!(mContext instanceof LoginActivity)) {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
                DrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                if (!(mContext instanceof TermConditionActivity)) {
                    mContext.startActivity(new Intent(mContext, TermConditionActivity.class));
                }
                DrawerLayout.closeDrawer(GravityCompat.START);
            }*/
            if (!(mContext instanceof TermConditionActivity)) {
                mContext.startActivity(new Intent(mContext, TermConditionActivity.class));
            }
            DrawerLayout.closeDrawer(GravityCompat.START);
        } else if (drawerModel.getID().equals("5")) {
            /*if (UserID.equals("0")) {
                if (!(mContext instanceof LoginActivity)) {
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
                DrawerLayout.closeDrawer(GravityCompat.START);
            } else {
                if (!(mContext instanceof PrivacyPolicyActivity)) {
                    mContext.startActivity(new Intent(mContext, PrivacyPolicyActivity.class));
                }
                DrawerLayout.closeDrawer(GravityCompat.START);
            }*/
            if (!(mContext instanceof PrivacyPolicyActivity)) {
                mContext.startActivity(new Intent(mContext, PrivacyPolicyActivity.class));
            }
            DrawerLayout.closeDrawer(GravityCompat.START);
        } else {

        }
    }

    @Override
    protected void onResume() {
        setDrawerRecyclerView();
        super.onResume();
    }
}
