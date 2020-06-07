package com.app.fullyloaded.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Models.PreviousWinnersModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PreviousWinnersAdapter extends RecyclerView.Adapter<PreviousWinnersAdapter.MyViewHolder> {

    private ArrayList<PreviousWinnersModel> arrayList;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView PreviousWinnerImage;
        MyTextView txtPreviousWinnerName, txtPreviousWinningDate, txtPreviousWinningDescription;

        public MyViewHolder(View view) {
            super(view);

            PreviousWinnerImage = view.findViewById(R.id.PreviousWinnerImage);
            txtPreviousWinnerName = view.findViewById(R.id.txtPreviousWinnerName);
            txtPreviousWinningDate = view.findViewById(R.id.txtPreviousWinningDate);
            txtPreviousWinningDescription = view.findViewById(R.id.txtPreviousWinningDescription);
        }
    }

    public PreviousWinnersAdapter(Context mContext, ArrayList<PreviousWinnersModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_winners_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PreviousWinnersModel previousWinners = arrayList.get(position);

        String ProfileImage = previousWinners.getProfileImage();
        String UserName = previousWinners.getUserName();
//        String WinningDate = previousWinners.getWinningDate();
        String Description = previousWinners.getDescription();

        if (ProfileImage.equals("") || ProfileImage.equals("null") || ProfileImage.equals(null) || ProfileImage == null) {

        } else {
            Glide.with(mContext).load(ProfileImage).into(holder.PreviousWinnerImage);
        }

        if (UserName.equals("") || UserName.equals("null") || UserName.equals(null) || UserName == null) {

        } else {
            holder.txtPreviousWinnerName.setText(Html.fromHtml(UserName), TextView.BufferType.SPANNABLE);
        }

//        if (WinningDate.equals("") || WinningDate.equals("null") || WinningDate.equals(null) || WinningDate == null) {
//
//        } else {
//            holder.txtPreviousWinningDate.setText(WinningDate);
//        }

        if (Description.equals("") || Description.equals("null") || Description.equals(null) || Description == null) {

        } else {
            holder.txtPreviousWinningDescription.setText(Html.fromHtml(Description), TextView.BufferType.SPANNABLE);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
