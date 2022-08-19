package com.example.clone_olx.Adapter;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clone_olx.Model.City;
import com.example.clone_olx.R;

import java.util.List;

public class AdapterCities extends RecyclerView.Adapter<AdapterCities.MyViewHolder> {

    private List<City> citiesList;
    private OnClickListener onClickListener;

    public AdapterCities(List<City> citiesList, OnClickListener onClickListener) {
        this.citiesList = citiesList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        City city = citiesList.get(position);

        holder.textCity.setText(city.getRegion());
        holder.itemView.setOnClickListener(view -> onClickListener.OnClick(city));
    }

    @Override
    public int getItemCount() {
        return citiesList.size();
    }

    public interface OnClickListener{
        void OnClick(City city);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView textCity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textCity = itemView.findViewById(R.id.text_city);
        }
    }
}
