package com.app.fullyloaded.UI;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Adapters.CurrentTechCompetitionsAdapter;
import com.app.fullyloaded.Models.CurrentTechCompetitionsModel;
import com.app.fullyloaded.R;

import java.util.ArrayList;

public class CurrentTechCompetitionsActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView Back;
    RecyclerView CurrentTechCompetitionsRecyclerView;
    ArrayList<CurrentTechCompetitionsModel> currentTechCompetitionsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_tech_competitions);

        getSupportActionBar().hide();

        mContext = this;

        Init();
    }

    public void Init() {
        Back = findViewById(R.id.Back);

        Back.setOnClickListener(this);

        CurrentTechCompetitionsRecyclerView = findViewById(R.id.CurrentTechCompetitionsRecyclerView);

        AddCurrentTechCompetitionsItems();
    }

    public void AddCurrentTechCompetitionsItems() {
        CurrentTechCompetitionsModel currentTechCompetitionsModel;

        currentTechCompetitionsList.clear();
        currentTechCompetitionsModel = new CurrentTechCompetitionsModel(R.drawable.laptop);
        currentTechCompetitionsList.add(currentTechCompetitionsModel);

        currentTechCompetitionsModel = new CurrentTechCompetitionsModel(R.drawable.mobile);
        currentTechCompetitionsList.add(currentTechCompetitionsModel);

        currentTechCompetitionsModel = new CurrentTechCompetitionsModel(R.drawable.laptop);
        currentTechCompetitionsList.add(currentTechCompetitionsModel);

        currentTechCompetitionsModel = new CurrentTechCompetitionsModel(R.drawable.mobile);
        currentTechCompetitionsList.add(currentTechCompetitionsModel);

        currentTechCompetitionsModel = new CurrentTechCompetitionsModel(R.drawable.laptop);
        currentTechCompetitionsList.add(currentTechCompetitionsModel);

        currentTechCompetitionsModel = new CurrentTechCompetitionsModel(R.drawable.mobile);
        currentTechCompetitionsList.add(currentTechCompetitionsModel);

        currentTechCompetitionsModel = new CurrentTechCompetitionsModel(R.drawable.laptop);
        currentTechCompetitionsList.add(currentTechCompetitionsModel);

        currentTechCompetitionsModel = new CurrentTechCompetitionsModel(R.drawable.mobile);
        currentTechCompetitionsList.add(currentTechCompetitionsModel);

        CurrentTechCompetitionsAdapter currentCompetitionsAdapter = new CurrentTechCompetitionsAdapter(mContext, currentTechCompetitionsList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 2, CurrentTechCompetitionsRecyclerView.VERTICAL, false);
        CurrentTechCompetitionsRecyclerView.setLayoutManager(mLayoutManager);
        CurrentTechCompetitionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        CurrentTechCompetitionsRecyclerView.setAdapter(currentCompetitionsAdapter);
        currentCompetitionsAdapter.notifyDataSetChanged();
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
