package com.gwp.lifestyle.jour4.bottom_navigation.ui.camera;

import static android.Manifest.permission.CAMERA;
import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.gwp.lifestyle.databinding.FragmentCameraBinding;
import com.gwp.lifestyle.jour5.permissions.PermissionsDetails;


public class CameraFragment extends Fragment implements View.OnClickListener {

    private FragmentCameraBinding binding;
    private ImageView imageView;
    private MaterialButton btnTake;
    private MaterialButton btnShare;
    private ActivityResultLauncher<String> permissionCameraLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCameraBinding.inflate(inflater, container, false);
        imageView = binding.imageView;
        btnTake = binding.btnTake;
        btnShare = binding.btnShare;
        btnShare.setOnClickListener(this);
        btnTake.setOnClickListener(this);
        hideBtn();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        permissionCameraLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                isGranted ->{
            if(isGranted) openCamera();
            else Toast.makeText(requireActivity(),"Vous N'avez pas acces a la Camera",Toast.LENGTH_LONG).show();
                });
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result ->{
                    if(result.getResultCode()== RESULT_OK && result.getData()!=null){
                        Bundle extras = result.getData().getExtras();
                                if(extras!=null){
                                    Bitmap bitmap = (Bitmap) extras.get("data");
                                    showBtn();
                                    imageView.setImageBitmap(bitmap);
                                }
                    }
                }
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showBtn(){
        btnShare.setVisibility(View.VISIBLE);
        btnTake.setVisibility(View.VISIBLE);
    }

    private void hideBtn(){
        btnShare.setVisibility(View.GONE);
        btnTake.setVisibility(View.VISIBLE);
    }
    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);

    }

    @Override
    public void onClick(View v) {
        int id_view = v.getId();
        if(btnTake.getId()==id_view){
            if (!PermissionsDetails.isPermissionGranted(requireActivity(), CAMERA)) {
                PermissionsDetails.checkAndRequestCameraPermission(requireActivity(), permissionCameraLauncher);
                return;
            }
            openCamera();

        }
        if(btnShare.getId()==id_view){

        }
    }
}