package com.gwp.lifestyle.jour3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gwp.lifestyle.databinding.ItemHabitudeBinding;

import java.util.ArrayList;

public class HabitudeAdaptateur extends RecyclerView.Adapter<HabitudeViewHolder> {
    private Context context;
    private ArrayList<Habitude> habitudes;
    private int positionAdapter;

    public HabitudeAdaptateur(Context context, ArrayList<Habitude> habitudes) {
        this.context = context;
        this.habitudes = habitudes;
    }

    @NonNull
    @Override
    public HabitudeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHabitudeBinding ui = ItemHabitudeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HabitudeViewHolder(ui);
    }

    @Override
    public void onBindViewHolder(@NonNull HabitudeViewHolder holder, int position) {
        holder.setHabitude(context,habitudes.get(position),position);
    }

    @Override
    public int getItemCount() {
        return habitudes.size();
    }
//    public int getPositionAdapter(){
////        return ;
//    }
}
