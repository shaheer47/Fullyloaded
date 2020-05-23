package com.app.fullyloaded.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HomeSliderAdapter extends RecyclerView.Adapter<HomeSliderAdapter.MyViewHolder> {

    private ArrayList<String> arrayList;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView SliderImageView;

        public MyViewHolder(View view) {
            super(view);

            SliderImageView = view.findViewById(R.id.SliderImageView);
        }
    }

    public HomeSliderAdapter(Context mContext, ArrayList<String> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String BannerImage = arrayList.get(position);

        if (BannerImage.equals("") || BannerImage.equals("null") || BannerImage.equals(null) || BannerImage == null) {

        } else {
            Glide.with(mContext).load(BannerImage).into(holder.SliderImageView);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
