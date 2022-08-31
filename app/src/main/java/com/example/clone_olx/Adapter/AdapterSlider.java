package com.example.clone_olx.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.clone_olx.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterSlider extends SliderViewAdapter<AdapterSlider.AdapterSliderVH> {

    private final List<String> imagesUrls;

    public AdapterSlider(List<String> imagesUrls) {
        this.imagesUrls = imagesUrls;
    }

    @Override
    public AdapterSliderVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider, null);
        return new AdapterSliderVH(inflate);
    }

    @Override
    public void onBindViewHolder(AdapterSliderVH viewHolder, int position) {
        String imageUrl = imagesUrls.get(position);
        Picasso.get().load(imageUrl).placeholder(R.drawable.loading_smaller_2).into(viewHolder.imageViewBackground);
    }

    @Override
    public int getCount() {
        return imagesUrls.size();
    }

    static class AdapterSliderVH extends SliderViewAdapter.ViewHolder {

        ImageView imageViewBackground;

        public AdapterSliderVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.image_add);
        }
    }

}
