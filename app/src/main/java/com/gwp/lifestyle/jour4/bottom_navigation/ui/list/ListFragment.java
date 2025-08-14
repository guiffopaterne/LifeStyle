package com.gwp.lifestyle.jour4.bottom_navigation.ui.list;


import static com.gwp.lifestyle.LifeStyleApplication.CHANNEL_ID;
import static com.gwp.lifestyle.Prefs.LIMIT_CAT;
import static com.gwp.lifestyle.Prefs.NUMBER_CAT;
import static com.gwp.lifestyle.Prefs.NUMBER_CAT_DEFAULT;
import static com.gwp.lifestyle.Prefs.SKIP_CAT;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gwp.lifestyle.LifeStyleApplication;
import com.gwp.lifestyle.Prefs;
import com.gwp.lifestyle.R;
import com.gwp.lifestyle.databinding.FragmentListBinding;
import com.gwp.lifestyle.jour4.api.CatAsApi;
import com.gwp.lifestyle.jour4.api.CatImageResponse;
import com.gwp.lifestyle.jour4.bottom_navigation.BottomNavigationActivity;
import com.gwp.lifestyle.jour5.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListFragment extends Fragment implements OnImageItemClickListener {
    private static final int NOTIFICATION_ID = 10;
    public static ArrayList<CatImage> listImages =  new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterGallery adapter;
    SharedPreferences prefs = LifeStyleApplication.instance.getPrefs();
    public static String LABEL_POSITION = "POSITION";
    ImageDetailFragment imageDetailFragment;
    private String title_notification = "";
    private String description_notification = "";

    private FragmentListBinding binding;
    private ActivityResultLauncher<String> permissionLauncher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        showNotification(title_notification,description_notification);
                    } else {
                        Toast.makeText(requireContext(), "Permission refusee", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView =binding.listGalleries;
        setupRecyclerView();
        prefs = LifeStyleApplication.instance.getPrefs();

//        Objects.requireNonNull(requireActivity().getActionBar()).show();

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.gallery_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.gallery_refresh){
            loadImages(10,0,false);
        }
        if(item.getItemId()==R.id.gallery_save_data){
            PermissionUtils.checkAndRequestNotificationPermission(requireActivity(),permissionLauncher);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupRecyclerView(){
        GridLayoutManager lm = new GridLayoutManager(requireContext(),2);
        adapter = new AdapterGallery(this,requireContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(lm);
        loadImages(prefs.getInt(LIMIT_CAT,10),prefs.getInt(Prefs.SKIP_CAT,0),false);
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
                            prefs.edit().putInt(LIMIT_CAT,nbre+prefs.getInt(NUMBER_CAT,NUMBER_CAT_DEFAULT))
                                    .putInt(SKIP_CAT,nbre)
                                    .apply();
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

    private void logAllFragments() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();

        Log.e("FRAGMENT_MANAGER", "Fragments actifs : " + fragments.size());
        for (Fragment fragment : fragments) {
            Log.e("FRAGMENT", "Nom: " + fragment.getClass().getSimpleName() +
                    ", isAdded: " + fragment.isAdded() +
                    ", isVisible: " + fragment.isVisible() +
                    ", isHidden: " + fragment.isHidden());
        }
    }

    private void logBackStack() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();

        Log.e("FRAGMENT_MANAGER", "Nombre de transactions dans la back stack : " + count);
        for (int i = 0; i < count; i++) {
            FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(i);
            Log.e("BACK_STACK", "Transaction " + i + " → Nom: " + entry.getName());
        }
    }

    private void updateDataImageFragment(int position) {
        logAllFragments();
        logBackStack();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        imageDetailFragment = (ImageDetailFragment) fragmentManager.findFragmentByTag("IMAGE_DETAIL");

        if (imageDetailFragment != null && imageDetailFragment.isAdded() && imageDetailFragment.isVisible()) {
            imageDetailFragment.setupImage(listImages.get(position));
        } else {
           setupFragmentImageDetail(position);
        }
    }

    private void setupFragmentImageDetail(int position) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            imageDetailFragment = ImageDetailFragment.newInstance(position, "");
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(binding.detailImageFragment.getId(), imageDetailFragment, "IMAGE_DETAIL");
            transaction.addToBackStack("IMAGE_DETAIL");
            transaction.commit();

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

    private void showNotification(String title, String description) {
        // Intent pour ouvrir l'application quand on clique sur la notification
        Intent intent = new Intent(requireContext(), BottomNavigationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Construire la notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp) // Icône de la notification
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Priorité élevée
                .setContentIntent(pendingIntent) // Ouvre l'app au clic
                .setAutoCancel(true); // Efface la notification au clic

        // Afficher la notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }

    }

}