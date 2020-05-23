package com.app.fullyloaded.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Models.CurrentCompetitionsModel;
import com.app.fullyloaded.Models.HomeCurrentTechCompetitionsModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.UI.CompetitionsDetailActivity;
import com.app.fullyloaded.Utility.MyTextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HomeCurrentTechCompetitionsAdapter extends RecyclerView.Adapter<HomeCurrentTechCompetitionsAdapter.MyViewHolder> {

    private ArrayList<HomeCurrentTechCompetitionsModel> arrayList;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView CurrentCompetitionsProductImage;
        MyTextView txtCurrentCompetitionsProductName, txtCurrentCompetitionsProductPrice, txtParticipate;

        public MyViewHolder(View view) {
            super(view);

            CurrentCompetitionsProductImage = view.findViewById(R.id.CurrentCompetitionsProductImage);

            txtCurrentCompetitionsProductName = view.findViewById(R.id.txtCurrentCompetitionsProductName);
            txtCurrentCompetitionsProductPrice = view.findViewById(R.id.txtCurrentCompetitionsProductPrice);
            txtParticipate = view.findViewById(R.id.txtParticipate);
        }
    }

    public HomeCurrentTechCompetitionsAdapter(Context mContext, ArrayList<HomeCurrentTechCompetitionsModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_current_tech_competitions_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HomeCurrentTechCompetitionsModel homeCurrentTechCompetitionsModel = arrayList.get(position);

        final String CurrentCompetitionID = homeCurrentTechCompetitionsModel.getCurrentTechCompetitionID();
        String CurrentCompetitionImage = homeCurrentTechCompetitionsModel.getCurrentTechCompetitionImage();
        String CurrentCompetitionName = homeCurrentTechCompetitionsModel.getCurrentTechCompetitionName();
        final String CurrentCompetitionPrice = homeCurrentTechCompetitionsModel.getCurrentTechCompetitionPrice();

        if (CurrentCompetitionImage.equals("") || CurrentCompetitionImage.equals("null") || CurrentCompetitionImage.equals(null) || CurrentCompetitionImage == null) {
        } else {
            Glide.with(mContext).load(CurrentCompetitionImage).into(holder.CurrentCompetitionsProductImage);
        }

        if (CurrentCompetitionName.equals("") || CurrentCompetitionName.equals("null") || CurrentCompetitionName.equals(null) || CurrentCompetitionName == null) {
        } else {
            holder.txtCurrentCompetitionsProductName.setText(CurrentCompetitionName);
        }

        if (CurrentCompetitionPrice.equals("") || CurrentCompetitionPrice.equals("null") || CurrentCompetitionPrice.equals(null) || CurrentCompetitionPrice == null) {
        } else {
            holder.txtCurrentCompetitionsProductPrice.setText("€" + CurrentCompetitionPrice);
        }

        holder.txtParticipate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CompetitionsDetailActivity.class);
                intent.putExtra("CurrentCompetitionID", CurrentCompetitionID);
                intent.putExtra("CurrentCompetitionPrice", CurrentCompetitionPrice);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
