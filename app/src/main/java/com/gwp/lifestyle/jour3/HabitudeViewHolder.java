package com.gwp.lifestyle.jour3;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import com.gwp.lifestyle.databinding.ItemHabitudeBinding;

public class HabitudeViewHolder extends RecyclerView.ViewHolder {
    private ItemHabitudeBinding ui;
    public HabitudeViewHolder(ItemHabitudeBinding ui) {
        super(ui.getRoot());
        this.ui = ui;
    }

    public void setHabitude(Context context,Habitude habitude,int position){
        String titre = position +habitude.getTitle();
        ui.titleHabitude.setText(titre);
        ui.descriptionHabitude.setText(habitude.getDescription());
    }
}
