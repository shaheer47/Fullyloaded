package com.app.fullyloaded.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Models.CartModel;
import com.app.fullyloaded.Models.OrderModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private ArrayList<OrderModel> arrayList;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ProductImage;
        MyTextView txtProductName, txtProductQuantity, txtProductPrice;

        public MyViewHolder(View view) {
            super(view);

            ProductImage = view.findViewById(R.id.ProductImage);

            txtProductName = view.findViewById(R.id.txtProductName);
            txtProductQuantity = view.findViewById(R.id.txtProductQuantity);
            txtProductPrice = view.findViewById(R.id.txtProductPrice);
        }
    }

    public OrderAdapter(Context mContext, ArrayList<OrderModel> arrayList) {
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

        String OrderImage = orderModel.getProductImage();
        String ProductName = orderModel.getProductName();
        String ProductPrice = orderModel.getProductPrice();
        String ProductQuantity = orderModel.getProductQuantity();

        if (OrderImage.equals("") || OrderImage.equals("null") || OrderImage.equals(null) || OrderImage == null) {

        } else {
            Glide.with(mContext).load(OrderImage).into(holder.ProductImage);
        }

        if (ProductName.equals("") || ProductName.equals("null") || ProductName.equals(null) || ProductName == null) {

        } else {
            holder.txtProductName.setText(ProductName);
        }

        if (ProductPrice.equals("") || ProductPrice.equals("null") || ProductPrice.equals(null) || ProductPrice == null) {

        } else {
            holder.txtProductPrice.setText(ProductName);
        }

        if (ProductQuantity.equals("") || ProductQuantity.equals("null") || ProductQuantity.equals(null) || ProductQuantity == null) {

        } else {
            holder.txtProductQuantity.setText("Qty : " + ProductQuantity);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
