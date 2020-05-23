package com.app.fullyloaded.UI;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Adapters.OrderAdapter;
import com.app.fullyloaded.Models.OrderModel;
import com.app.fullyloaded.R;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    Context mContext;
    ImageView Back;
    RecyclerView OrderRecyclerView;
    ArrayList<OrderModel> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getSupportActionBar().hide();

        mContext = this;

        Init();
    }

    public void Init() {
        Back = findViewById(R.id.Back);

        Back.setOnClickListener(this);

        OrderRecyclerView = findViewById(R.id.OrderRecyclerView);

        /*for (int i = 0; i < 7; i++) {
            OrderModel orderModel = new OrderModel(String.valueOf(i));
            orderList.add(orderModel);
        }*/

        OrderAdapter orderAdapter = new OrderAdapter(mContext, orderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, OrderRecyclerView.VERTICAL, false);
        OrderRecyclerView.setLayoutManager(mLayoutManager);
        OrderRecyclerView.setItemAnimator(new DefaultItemAnimator());
        OrderRecyclerView.setAdapter(orderAdapter);
        orderAdapter.notifyDataSetChanged();
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
