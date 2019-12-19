package com.example.tourmate_final;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tourmate_final.adapter.MomentsAdapter;
import com.example.tourmate_final.pojos.Moments;
import com.example.tourmate_final.viewmodel.MomentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Moment_fragment extends Fragment {
    private static final int REQUEST_CAMERA_CODE = 123;
    private static final int REQUEST_STORAGE_CODE = 321;
    private MomentViewModel momentViewModel;
    private RecyclerView recyclerView;
    private String eventid=null;
    private MomentsAdapter adapter;
    private FloatingActionButton camerafloat;
    private String currentPhotoPath;


    public Moment_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        momentViewModel= ViewModelProviders.of(this).get(MomentViewModel.class);

        Toast.makeText(getActivity(), "sdfafd", Toast.LENGTH_SHORT).show();
        Bundle bundle=getArguments();
        if (bundle!=null){
            eventid=bundle.getString("id");
            momentViewModel.getMoments(eventid);

            Toast.makeText(getActivity(), "data"+eventid, Toast.LENGTH_SHORT).show();

        }


        return inflater.inflate(R.layout.fragment_moment_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.momentsRV);
        camerafloat=view.findViewById(R.id.CameraFav);
        momentViewModel.momentsLD.observe(this, new Observer<List<Moments>>() {
            @Override
            public void onChanged(List<Moments> moments) {
                adapter = new MomentsAdapter(getActivity(), moments);
                GridLayoutManager llm=new GridLayoutManager(getActivity(),2);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(adapter);
                Toast.makeText(getActivity(), "this"+moments.size(), Toast.LENGTH_SHORT).show();

            }
        });
        camerafloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choice Option !");
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                View view1 = inflater.inflate(R.layout.pick_image_dilog,null);
                builder.setView(view1);
                CardView CAmera=view1.findViewById(R.id.cameraid);
                CardView gallery=view1.findViewById(R.id.galleryid);
                final   AlertDialog dialog = builder.create();
                dialog.show();
                CAmera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkStoragePermission()){
                            dispatchCameraIntent();
                            dialog.dismiss();
                        }
                    }

                });
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "gallery", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

            }
        });
    }

    private void dispatchCameraIntent() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.tourmate_final.provider",
                        photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private boolean checkStoragePermission() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            requestPermissions(permissions, REQUEST_STORAGE_CODE);
            return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_STORAGE_CODE && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getActivity(), "Permission accepted", Toast.LENGTH_SHORT).show();
            dispatchCameraIntent();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA_CODE &&
                resultCode == Activity.RESULT_OK){
           // Log.e(TAG, "onActivityResult: "+currentPhotoPath);
            File file = new File(currentPhotoPath);
            momentViewModel.uploadimagetodtb(file, eventid);
        }
    }

}
