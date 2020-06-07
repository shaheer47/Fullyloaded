package com.app.fullyloaded.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Models.CartModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    SharedPreferences sharedPreferences, preferences, getSharedPreferences;


    ArrayList<CartModel> arrayList;
    Context mContext;
    OnDeleteCartListener listener;

    public CartAdapter(Context mContext, ArrayList<CartModel> arrayList, OnDeleteCartListener listener) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_adapter, parent, false);
        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CartModel cartModel = arrayList.get(position);

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


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.remove_alert);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                TextView textView = dialog.findViewById(R.id.txtTitle);
                textView.setText(R.string.remove_product_text);
                dialog.findViewById(R.id.txtRemove).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                        listener.removeCart(position);
                    }

                });

                dialog.findViewById(R.id.txtCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();

            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, CompetitionImageView;
        MyTextView txtProductName, txtQuantity, txtProductPrice;

        public MyViewHolder(View view) {
            super(view);

            CompetitionImageView = view.findViewById(R.id.CompetitionImageView);

            txtProductName = view.findViewById(R.id.txtProductName);
            txtQuantity = view.findViewById(R.id.txtQuantity);
            txtProductPrice = view.findViewById(R.id.txtProductPrice);

            imageView = view.findViewById(R.id.btn_removeCartItem);


        }


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

