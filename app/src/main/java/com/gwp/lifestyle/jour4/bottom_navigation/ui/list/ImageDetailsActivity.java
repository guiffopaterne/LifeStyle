package com.gwp.lifestyle.jour4.bottom_navigation.ui.list;

import static com.gwp.lifestyle.jour4.bottom_navigation.ui.list.ListFragment.LABEL_POSITION;
import static com.gwp.lifestyle.jour4.bottom_navigation.ui.list.ListFragment.listImages;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.gwp.lifestyle.R;
import com.gwp.lifestyle.databinding.ActivityImageDetailsBinding;

public class ImageDetailsActivity extends AppCompatActivity {
private ActivityImageDetailsBinding ui;
private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ui = ActivityImageDetailsBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.image_details), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView =  ui.imgDetail;
        setupImage();

    }

    private void setupImage(){
        int position = getIntent().getIntExtra(LABEL_POSITION,-1);
        if(position==-1 || position >= listImages.size()){
            Toast.makeText(this,"Pas d'image a cet index",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Glide
                .with(this)
                .load(listImages.get(position).getSrc())
                .centerCrop()
                .into(imageView);
    }
}