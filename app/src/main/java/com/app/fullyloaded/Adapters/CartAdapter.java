package com.app.fullyloaded.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Models.CartModel;
import com.app.fullyloaded.Models.PreviousWinnersModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    ArrayList<CartModel> arrayList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView CompetitionImageView;
        MyTextView txtProductName, txtQuantity, txtProductPrice;

        public MyViewHolder(View view) {
            super(view);

            CompetitionImageView = view.findViewById(R.id.CompetitionImageView);

            txtProductName = view.findViewById(R.id.txtProductName);
            txtQuantity = view.findViewById(R.id.txtQuantity);
            txtProductPrice = view.findViewById(R.id.txtProductPrice);
        }
    }

    public CartAdapter(Context mContext, ArrayList<CartModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CartModel cartModel = arrayList.get(position);

        String ID = cartModel.getID();
        String ProductID = cartModel.getProductID();
        String ProductName = cartModel.getProductName();
        String ProductImage = cartModel.getProductImage();
        String ProductPrice = cartModel.getProductPrice();
        String ProductQuantity = cartModel.getProductQuantity();

        if (ProductImage.equals("") || ProductImage.equals("null") || ProductImage.equals(null) || ProductImage == null) {
        } else {
            Glide.with(mContext).load(ProductImage).into(holder.CompetitionImageView);
        }

        if (ProductName.equals("") || ProductName.equals("null") || ProductName.equals(null) || ProductName == null) {
        } else {
            holder.txtProductName.setText(ProductName);
        }

        if (ProductQuantity.equals("") || ProductQuantity.equals("null") || ProductQuantity.equals(null) || ProductQuantity == null) {
        } else {
            holder.txtQuantity.setText(ProductQuantity);
        }

        if (ProductPrice.equals("") || ProductPrice.equals("null") || ProductPrice.equals(null) || ProductPrice == null) {
        } else {
            holder.txtProductPrice.setText("€" + ProductPrice);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
