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
import com.app.fullyloaded.R;
import com.app.fullyloaded.UI.CompetitionsDetailActivity;
import com.app.fullyloaded.Utility.MyTextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SimilarCurrentCompetitionsAdapter extends RecyclerView.Adapter<SimilarCurrentCompetitionsAdapter.MyViewHolder> {

    private ArrayList<CurrentCompetitionsModel> arrayList;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout MainRelativeLayout;
        ImageView CurrentCompetitionImage;
        MyTextView txtCurrentCompetitionName, txtCurrentCompetitionPrice, txtParticipate;

        public MyViewHolder(View view) {
            super(view);

            MainRelativeLayout = view.findViewById(R.id.MainRelativeLayout);

            CurrentCompetitionImage = view.findViewById(R.id.CurrentCompetitionImage);

            txtCurrentCompetitionName = view.findViewById(R.id.txtCurrentCompetitionName);
            txtCurrentCompetitionPrice = view.findViewById(R.id.txtCurrentCompetitionPrice);
            txtParticipate = view.findViewById(R.id.txtParticipate);
        }
    }

    public SimilarCurrentCompetitionsAdapter(Context mContext, ArrayList<CurrentCompetitionsModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_current_competitions_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CurrentCompetitionsModel currentCompetitionsModel = arrayList.get(position);

        final String CurrentCompetitionID = currentCompetitionsModel.getCurrentCompetitionID();
        String CurrentCompetitionImage = currentCompetitionsModel.getCurrentCompetitionImage();
        String CurrentCompetitionName = currentCompetitionsModel.getCurrentCompetitionName();
        String CurrentCompetitionPrice = currentCompetitionsModel.getCurrentCompetitionPrice();

        if (CurrentCompetitionImage.equals("") || CurrentCompetitionImage.equals("null") || CurrentCompetitionImage.equals(null) || CurrentCompetitionImage == null) {
        } else {
            Glide.with(mContext).load(CurrentCompetitionImage).into(holder.CurrentCompetitionImage);
        }

        if (CurrentCompetitionName.equals("") || CurrentCompetitionName.equals("null") || CurrentCompetitionName.equals(null) || CurrentCompetitionName == null) {
        } else {
            holder.txtCurrentCompetitionName.setText(CurrentCompetitionName);
        }

        if (CurrentCompetitionPrice.equals("") || CurrentCompetitionPrice.equals("null") || CurrentCompetitionPrice.equals(null) || CurrentCompetitionPrice == null) {
        } else {
            holder.txtCurrentCompetitionPrice.setText("€" + CurrentCompetitionPrice);
        }

        holder.txtParticipate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CompetitionsDetailActivity.class);
                intent.putExtra("CurrentCompetitionID", CurrentCompetitionID);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
