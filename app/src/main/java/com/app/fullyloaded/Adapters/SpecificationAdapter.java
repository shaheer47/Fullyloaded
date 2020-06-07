package com.app.fullyloaded.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Models.SpecificationModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.Utility.MyTextView;

import java.util.ArrayList;

public class SpecificationAdapter extends RecyclerView.Adapter<SpecificationAdapter.MyViewHolder> {

    ArrayList<SpecificationModel> arrayList;
    Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        MyTextView txtSpecification;

        public MyViewHolder(View view) {
            super(view);

            txtSpecification = view.findViewById(R.id.txtSpecification);
        }
    }

    public SpecificationAdapter(Context mContext, ArrayList<SpecificationModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.specification_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SpecificationModel specificationModel = arrayList.get(position);

        holder.txtSpecification.setText(specificationModel.getSpecification());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
