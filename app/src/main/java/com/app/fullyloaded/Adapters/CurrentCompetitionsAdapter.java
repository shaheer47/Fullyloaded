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
import com.app.fullyloaded.Models.PreviousWinnersModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.UI.CompetitionsDetailActivity;
import com.app.fullyloaded.Utility.MyTextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CurrentCompetitionsAdapter extends RecyclerView.Adapter<CurrentCompetitionsAdapter.MyViewHolder> {

    private ArrayList<CurrentCompetitionsModel> arrayList;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout MainRelativeLayout;
        ImageView CurrentCompetitionsProductImage;
        MyTextView txtCurrentCompetitionsProductName, txtCurrentCompetitionsProductPrice, txtCategoryName;

        public MyViewHolder(View view) {
            super(view);

            CurrentCompetitionsProductImage = view.findViewById(R.id.CurrentCompetitionsProductImage);

            txtCurrentCompetitionsProductName = view.findViewById(R.id.txtCurrentCompetitionsProductName);
            txtCurrentCompetitionsProductPrice = view.findViewById(R.id.txtCurrentCompetitionsProductPrice);
            txtCategoryName = view.findViewById(R.id.txtCategoryName);

            MainRelativeLayout = view.findViewById(R.id.MainRelativeLayout);
        }
    }

    public CurrentCompetitionsAdapter(Context mContext, ArrayList<CurrentCompetitionsModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_competitions_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CurrentCompetitionsModel currentCompetitionsModel = arrayList.get(position);

        final String CurrentCompetitionID = currentCompetitionsModel.getCurrentCompetitionID();
        String CurrentCompetitionImage = currentCompetitionsModel.getCurrentCompetitionImage();
        String CurrentCompetitionName = currentCompetitionsModel.getCurrentCompetitionName();
        final String CurrentCompetitionPrice = currentCompetitionsModel.getCurrentCompetitionPrice();
        String CurrentCompetitionType = currentCompetitionsModel.getCurrentCompetitionType();

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

        if (CurrentCompetitionType.equals("") || CurrentCompetitionType.equals("null") || CurrentCompetitionType.equals(null) || CurrentCompetitionType == null) {
        } else {
            holder.txtCategoryName.setText(CurrentCompetitionType);
        }

        holder.MainRelativeLayout.setOnClickListener(new View.OnClickListener() {
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
