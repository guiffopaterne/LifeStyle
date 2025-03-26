package com.gwp.lifestyle.jour3.list_habitude;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import com.gwp.lifestyle.databinding.ItemHabitudeBinding;

public class HabitudeViewHolder extends RecyclerView.ViewHolder {
    private final ItemHabitudeBinding ui;
    private final OnHabitudeItemListener l;
    public HabitudeViewHolder(ItemHabitudeBinding ui,OnHabitudeItemListener l) {
        super(ui.getRoot());
        this.ui = ui;
        this.l = l;
    }

    public void setHabitude(Context context, Habitude habitude, int position){
        String titre = position +habitude.getTitle();
        ui.titleHabitude.setText(titre);
        ui.descriptionHabitude.setText(habitude.getDescription());
        ui.getRoot().setOnClickListener(v-> l.onHabitudeClick(position));
    }
}
