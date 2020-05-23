package com.app.fullyloaded.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Adapters.TicketSetAdapter;
import com.app.fullyloaded.Models.TicketSetModel;
import com.app.fullyloaded.R;

import java.util.ArrayList;

public class ChooseTicketActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView Back;
    RecyclerView TicketSetRecyclerView, TicketRecyclerView;
    ArrayList<TicketSetModel> ticketSetList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ticket);

        getSupportActionBar().hide();

        mContext = this;

        Init();
    }

    public void Init() {
        Back = findViewById(R.id.Back);

        Back.setOnClickListener(this);

        TicketSetRecyclerView = findViewById(R.id.TicketSetRecyclerView);

        AddTicketSetList();

        TicketRecyclerView = findViewById(R.id.TicketRecyclerView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Back:
                finish();
                break;
        }
    }

    private void AddTicketSetList() {
        TicketSetModel ticketSetModel;

        ticketSetList.clear();
        ticketSetModel = new TicketSetModel("1","100");
        ticketSetList.add(ticketSetModel);

        ticketSetModel = new TicketSetModel("101","200");
        ticketSetList.add(ticketSetModel);

        ticketSetModel = new TicketSetModel("201","300");
        ticketSetList.add(ticketSetModel);

        ticketSetModel = new TicketSetModel("301","400");
        ticketSetList.add(ticketSetModel);

        TicketSetAdapter ticketSetAdapter = new TicketSetAdapter(mContext, ticketSetList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, TicketSetRecyclerView.HORIZONTAL, false);
        TicketSetRecyclerView.setLayoutManager(mLayoutManager);
        TicketSetRecyclerView.setItemAnimator(new DefaultItemAnimator());
        TicketSetRecyclerView.setAdapter(ticketSetAdapter);
        ticketSetAdapter.notifyDataSetChanged();
    }

    public void OpenCart(View view) {
        Intent intent = new Intent(mContext, CartActivity.class);
        startActivity(intent);
    }
}
