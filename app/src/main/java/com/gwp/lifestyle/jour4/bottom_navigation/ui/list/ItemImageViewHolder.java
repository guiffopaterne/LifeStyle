package com.gwp.lifestyle.jour4.bottom_navigation.ui.list;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gwp.lifestyle.R;
import com.gwp.lifestyle.databinding.ItemGalleryBinding;

public class ItemImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private OnImageItemClickListener l;
    private ItemGalleryBinding ui;
    private ImageView imageView;


    public ItemImageViewHolder(@NonNull ItemGalleryBinding ui, OnImageItemClickListener l) {
        super(ui.getRoot());
        this.ui = ui;
        this.l = l;
        imageView = ui.imageView;
        imageView.setOnClickListener(this);
    }

    public void setImage(CatImage image, Context ctx){
        Glide.with(ctx)
                .load(image.getSrc())
                .centerCrop()
                .placeholder(R.drawable.baseline_camera_24)
                .into(ui.imageView);

    }

    @Override
    public void onClick(View v) {
        if(getAdapterPosition()!=RecyclerView.NO_POSITION) l.onClickImageListener(getAdapterPosition());
    }
}
