package com.example.clone_olx.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clone_olx.Model.City;
import com.example.clone_olx.R;

import java.util.List;

public class AdapterRegions extends RecyclerView.Adapter<AdapterRegions.MyViewHolder>{

    private List<String> regionsList;
    private OnClickListener onClickListener;

    public AdapterRegions(List<String> regionsList, OnClickListener onClickListener) {
        this.regionsList = regionsList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String region = regionsList.get(position);

        holder.textRegion.setText(region);
        holder.itemView.setOnClickListener(v -> onClickListener.OnClick(region));

    }

    @Override
    public int getItemCount() {
        return regionsList.size();
    }

    public interface OnClickListener{
        void OnClick(String region);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textRegion;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textRegion = itemView.findViewById(R.id.text_city);

        }
    }
}
