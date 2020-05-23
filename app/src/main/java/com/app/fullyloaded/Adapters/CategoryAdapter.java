package com.app.fullyloaded.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.app.fullyloaded.Models.CategoryModel;
import com.app.fullyloaded.Models.CurrentCompetitionsModel;
import com.app.fullyloaded.R;
import com.app.fullyloaded.UI.CompetitionsDetailActivity;
import com.app.fullyloaded.UI.CurrentCompetitionsActivity;
import com.app.fullyloaded.Utility.MyTextView;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private ArrayList<CategoryModel> arrayList;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        MyTextView txtCategoryName, txtParticipate;

        public MyViewHolder(View view) {
            super(view);

            txtCategoryName = view.findViewById(R.id.txtCategoryName);
            txtParticipate = view.findViewById(R.id.txtParticipate);
        }
    }

    public CategoryAdapter(Context mContext, ArrayList<CategoryModel> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        CategoryModel categoryModel = arrayList.get(position);

        final String CategoryName = categoryModel.getCategoryName();

        if (CategoryName.equals("") || CategoryName.equals("null") || CategoryName.equals(null) || CategoryName == null) {
        } else {
            holder.txtCategoryName.setText(CategoryName);
        }

        holder.txtParticipate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CurrentCompetitionsActivity.class);
                intent.putExtra("CategoryName", CategoryName);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
