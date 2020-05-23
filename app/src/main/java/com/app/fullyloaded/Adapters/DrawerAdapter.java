package com.app.fullyloaded.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Models.DrawerModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;

import java.util.ArrayList;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<DrawerModel> arrayList;
    ClickListener clickListener;
    int pos = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        MyTextView txtDrawerItem;
        LinearLayout MainLinearLayout;

        public MyViewHolder(View view) {
            super(view);

            txtDrawerItem = view.findViewById(R.id.txtDrawerItem);

            MainLinearLayout = view.findViewById(R.id.MainLinearLayout);
        }
    }

    public DrawerAdapter(Context mContext, ArrayList<DrawerModel> arrayList, ClickListener clickListener) {
        this.mContext = mContext;
        this.arrayList = arrayList;
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        DrawerModel drawerModel = arrayList.get(position);

        holder.txtDrawerItem.setText(drawerModel.getDrawerItem());

        if (pos == position) {
            holder.MainLinearLayout.setBackgroundResource(R.drawable.drawer_background);
            holder.txtDrawerItem.setTextColor(R.color.white);
        } else {

        }

        holder.MainLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = position;
                notifyDataSetChanged();
                clickListener.OnClickDrawerItem(arrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface ClickListener {
        void OnClickDrawerItem(DrawerModel drawerModel);
    }
}