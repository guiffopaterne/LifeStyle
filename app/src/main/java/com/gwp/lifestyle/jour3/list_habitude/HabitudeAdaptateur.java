package com.gwp.lifestyle.jour3.list_habitude;

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
    private OnHabitudeItemListener listener;

    public HabitudeAdaptateur(Context context, ArrayList<Habitude> habitudes,OnHabitudeItemListener listener) {
        this.context = context;
        this.habitudes = habitudes;
        this.listener =listener;
    }

    @NonNull
    @Override
    public HabitudeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHabitudeBinding ui = ItemHabitudeBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new HabitudeViewHolder(ui,listener);
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
