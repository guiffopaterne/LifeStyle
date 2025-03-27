package com.gwp.lifestyle.jour4.bottom_navigation.ui.list;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gwp.lifestyle.LifeStyleApplication;
import com.gwp.lifestyle.Prefs;
import com.gwp.lifestyle.databinding.FragmentListBinding;
import com.gwp.lifestyle.jour4.api.CatAsApi;
import com.gwp.lifestyle.jour4.api.CatImageResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment implements OnImageItemClickListener {
    public static ArrayList<CatImage> listImages =  new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterGallery adapter;
    SharedPreferences prefs = LifeStyleApplication.instance.getPrefs();
    public static String LABEL_POSITION = "POSITION";
    ImageDetailFragment imageDetailFragment;

    private FragmentListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView =binding.listGalleries;
        setupRecyclerView();
        prefs = LifeStyleApplication.instance.getPrefs();
    }
    private void setupRecyclerView(){
        GridLayoutManager lm = new GridLayoutManager(requireContext(),2);
        adapter = new AdapterGallery(this,requireContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(lm);
        loadImages(prefs.getInt(Prefs.LIMIT_CAT,10),prefs.getInt(Prefs.SKIP_CAT,0),false);
    }
    private void loadImages(int nbre, int skip,boolean reset){
        CatAsApi.catAsApiClient.getCatImages(nbre,skip).enqueue(
                new Callback<List<CatImageResponse>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<CatImageResponse>> call, @NonNull Response<List<CatImageResponse>> response) {
                        if(response.isSuccessful()){
                            if(response.body()==null){
                                Toast.makeText(requireContext(),"Pas d'images",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ArrayList<CatImage> c = new ArrayList<>();
                            for(CatImageResponse catRes: response.body()){
                                String url = String.format("%s/cat/%s?position=center",CatAsApi.BASE_URL,catRes.id);
                                c.add(new CatImage(listImages.size(), catRes.id,url));
                            }
                            reLoadImages(c,reset);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<CatImageResponse>> call, @NonNull Throwable t) {
                        Toast.makeText(requireContext(), t.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.e("TAG",t.getMessage().toString());
                    }
                }
        );
    }

    private void reLoadImages(ArrayList<CatImage> catImages,boolean isReset){
        if(isReset) listImages.clear();
        if(!catImages.isEmpty())listImages.addAll(catImages);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void updateDataImageFragment(int position) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        imageDetailFragment = (ImageDetailFragment) fragmentManager.findFragmentByTag("IMAGE_DETAIL");

        if (imageDetailFragment != null && imageDetailFragment.isAdded() && imageDetailFragment.isVisible()) {

            imageDetailFragment.setupImage(listImages.get(position));
        } else {
            Log.e("UPDATE_IMAGE", "Fragment absent, création"+ "imageDetailFragment "+(imageDetailFragment != null )+"\n imageDetailFragment.isAdded()=="+ imageDetailFragment.isAdded() + "\n imageDetailFragment.isVisible()"+imageDetailFragment.isVisible());
            setupFragmentImageDetail(position);
        }
    }

    private void setupFragmentImageDetail(int position) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            imageDetailFragment = ImageDetailFragment.newInstance(position, "");
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(binding.detailImageFragment.getId(), imageDetailFragment, "IMAGE_DETAIL");
            transaction.commit();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("ACTITVITY","fragment "+ imageDetailFragment.isVisible());
    }

    @Override
    public void onClickImageListener(int position) {
        Log.e("ONCLICK", "position "+ position);
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE){
            if(binding.detailImageFragment==null) return ;
            if(imageDetailFragment==null){
                setupFragmentImageDetail(position);
                return;
            }
            updateDataImageFragment(position);
            return;
        }
        Intent intent = new Intent(requireContext(),ImageDetailsActivity.class);
//        envoyez les donnees via intent
        intent.putExtra(LABEL_POSITION,position);
        startActivity(intent);
    }
}