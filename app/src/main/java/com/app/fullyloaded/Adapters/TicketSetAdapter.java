package com.app.fullyloaded.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Models.TicketSetModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TicketSetAdapter extends RecyclerView.Adapter<TicketSetAdapter.MyViewHolder> {

    private ArrayList<TicketSetModel> arrayList;
    private Context mContext;
    private int pos = 0;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        MyTextView txtTicketSet;
        View BottomView;
        RelativeLayout MainRelativeLayout;

        public MyViewHolder(View view) {
            super(view);

            txtTicketSet = view.findViewById(R.id.txtTicketSet);

            BottomView = view.findViewById(R.id.BottomView);

            MainRelativeLayout = view.findViewById(R.id.MainRelativeLayout);
        }
    }

    public TicketSetAdapter(Context mContext, ArrayList<TicketSetModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticketset_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, final int position) {
        TicketSetModel ticketSetModel = arrayList.get(position);

        if (pos == position) {
            holder.BottomView.setVisibility(View.VISIBLE);
        } else {
            holder.BottomView.setVisibility(View.GONE);
        }

        holder.MainRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                notifyDataSetChanged();
            }
        });

        holder.txtTicketSet.setText(ticketSetModel.getTicketFrom() + "-" + ticketSetModel.getTicketTo());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
