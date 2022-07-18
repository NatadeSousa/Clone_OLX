package com.example.clone_olx.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clone_olx.Model.Categories;
import com.example.clone_olx.R;

import java.util.List;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.MyViewHolder> {

    private List<Categories> categoriesList;
    private OnClickListener onClickListener;

    public AdapterCategories(List<Categories> categoriesList, OnClickListener onClickListener) {
        this.categoriesList = categoriesList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Categories category = categoriesList.get(position);

        holder.textCategory.setText(category.getTitle());
        holder.imgCategory.setImageResource(category.getPath());

        holder.itemView.setOnClickListener(view -> onClickListener.OnClick(category));

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public interface OnClickListener{
         void OnClick(Categories category);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textCategory;
        ImageView imgCategory;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textCategory = itemView.findViewById(R.id.text_category);
            imgCategory = itemView.findViewById(R.id.img_category);

        }
    }
}
