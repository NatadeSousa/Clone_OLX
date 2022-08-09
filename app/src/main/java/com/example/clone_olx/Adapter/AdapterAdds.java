package com.example.clone_olx.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clone_olx.Helper.SetMask;
import com.example.clone_olx.Model.Adds;
import com.example.clone_olx.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterAdds extends RecyclerView.Adapter<AdapterAdds.MyViewHolder>{

    private List<Adds> addsList;
    private OnClickListener onClickListener;

    public AdapterAdds(List<Adds> addsList, OnClickListener onClickListener) {
        this.addsList = addsList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Adds add = addsList.get(position);

        Picasso.get().load(add.getImagesUrl().get(0)).into(holder.imgAdd);

        holder.textTitle.setText(add.getTitle());
        holder.textPrice.setText("R$ " + SetMask.getValue(add.getPrice()));
        holder.textPlace.setText(SetMask.getDate(add.getAddDate(), 3) + ", " + add.getPlace().getLocalidade());
        holder.itemView.setOnClickListener(v -> {
            onClickListener.OnClick(add);
        });

    }

    @Override
    public int getItemCount() {
        return addsList.size();
    }

    public interface OnClickListener{
        void OnClick(Adds add);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgAdd;
        private TextView textTitle,textPrice,textPlace;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAdd = itemView.findViewById(R.id.img_add);
            textTitle = itemView.findViewById(R.id.text_title_add);
            textPrice = itemView.findViewById(R.id.text_price_add);
            textPlace = itemView.findViewById(R.id.text_place_add);

        }
    }
}
