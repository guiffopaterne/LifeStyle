package com.gwp.lifestyle.jour4.bottom_navigation.ui.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gwp.lifestyle.databinding.ItemGalleryBinding;

public class AdapterGallery extends RecyclerView.Adapter<ItemImageViewHolder> {
    private OnImageItemClickListener listener;
    private Context ctx;

    public AdapterGallery(OnImageItemClickListener listener,Context ctx) {
        this.listener = listener;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public ItemImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGalleryBinding binding = ItemGalleryBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ItemImageViewHolder(binding,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemImageViewHolder holder, int position) {
        holder.setImage(ListFragment.listImages.get(position),ctx);
    }

    @Override
    public int getItemCount() {
        return ListFragment.listImages.size();
    }
}
