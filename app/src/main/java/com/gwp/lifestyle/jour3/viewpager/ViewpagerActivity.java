package com.gwp.lifestyle.jour3.viewpager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.gwp.lifestyle.R;
import com.gwp.lifestyle.databinding.ActivityViewpagerBinding;

import java.util.ArrayList;

public class ViewpagerActivity extends AppCompatActivity {
    private ActivityViewpagerBinding ui;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ui = ActivityViewpagerBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        viewPager = ui.viewpager;
        viewPager.setAdapter(new DemoAdapterPager(getSupportFragmentManager()));
//        ajout le tablayout
        tabLayout = ui.tablayout;
        tabLayout.setupWithViewPager(viewPager);
//        ArrayList<Integer> listicon = new ArrayList<>();
//        for (int i = 0; i < 4; i++) {
//            tabLayout.getTabAt(i).setIcon(listicon.get(i));
//            tabLayout.getTabAt(i).setText()
//
//        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.viewpager_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}