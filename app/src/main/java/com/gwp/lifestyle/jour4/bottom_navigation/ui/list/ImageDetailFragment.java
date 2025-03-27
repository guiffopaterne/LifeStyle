package com.gwp.lifestyle.jour4.bottom_navigation.ui.list;

import static com.gwp.lifestyle.jour4.bottom_navigation.ui.list.ListFragment.LABEL_POSITION;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gwp.lifestyle.R;
import com.gwp.lifestyle.databinding.FragmentImageDetailBinding;
import com.squareup.picasso.Picasso;


public class ImageDetailFragment extends Fragment {
    private FragmentImageDetailBinding ui;
    private ImageView imageView;

    private static final String ARG_PARAM1 = "position";
    private static final String ARG_PARAM2 = "url";

    // TODO: Rename and change types of parameters
    private int position = -1;
    private String url;

    public ImageDetailFragment() {
        // Required empty public constructor
    }

    public static ImageDetailFragment newInstance(int position, String url) {
        ImageDetailFragment fragment = new ImageDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        args.putString(ARG_PARAM2, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM1);
            url = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ui = FragmentImageDetailBinding.inflate(inflater,container,false);
        return ui.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        imageView = ui.imgDetail;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LABEL_POSITION,position);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState!=null){
            position = savedInstanceState.getInt(LABEL_POSITION,0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("GLIDE_DEBUG", "Fragment détruit !");
        setupImage(null);
    }

    public void setupImage(CatImage catImage){
        if(catImage==null){
            catImage = ListFragment.listImages.get(position!=-1?position:0);
        }
        Log.e("tag","contect "+ ui.imgDetail);
        Glide
                .with(requireContext())
                .load(catImage.getSrc())
                .placeholder(R.drawable.ic_menu_camera)
                .error(R.drawable.baseline_map_24)
                .centerCrop()
                .into(imageView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ui = null;
    }
}