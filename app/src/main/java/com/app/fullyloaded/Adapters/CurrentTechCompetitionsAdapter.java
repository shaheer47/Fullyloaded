package com.app.fullyloaded.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Models.CurrentTechCompetitionsModel;
import com.app.fullyloaded.R;

import java.util.ArrayList;

public class CurrentTechCompetitionsAdapter extends RecyclerView.Adapter<CurrentTechCompetitionsAdapter.MyViewHolder> {

    ArrayList<CurrentTechCompetitionsModel> arrayList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView CurrentCompetitionsProductImage;

        public MyViewHolder(View view) {
            super(view);

            CurrentCompetitionsProductImage = view.findViewById(R.id.CurrentCompetitionsProductImage);
        }
    }

    public CurrentTechCompetitionsAdapter(Context mContext, ArrayList<CurrentTechCompetitionsModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_tech_competitions_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CurrentTechCompetitionsModel currentTechCompetitionsModel  = arrayList.get(position);

        holder.CurrentCompetitionsProductImage.setImageResource(currentTechCompetitionsModel.getDrawable());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
