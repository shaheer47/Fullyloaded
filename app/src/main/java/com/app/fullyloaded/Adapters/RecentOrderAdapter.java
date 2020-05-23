package com.app.fullyloaded.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Models.OrderModel;
import com.app.fullyloaded.R;

import java.util.ArrayList;

public class RecentOrderAdapter extends RecyclerView.Adapter<RecentOrderAdapter.MyViewHolder> {

    ArrayList<OrderModel> arrayList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //

        public MyViewHolder(View view) {
            super(view);

            //
        }
    }

    public RecentOrderAdapter(Context mContext, ArrayList<OrderModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderModel orderModel = arrayList.get(position);

        //
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
