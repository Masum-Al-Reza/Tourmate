package com.example.tourmate_final.viewmodel;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tourmate_final.MomentGallary;
import com.example.tourmate_final.pojos.Moments;
import com.example.tourmate_final.repository.MomentRepository;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MomentViewModel extends ViewModel {
    private MomentRepository momentRepository;
  public MutableLiveData<List<Moments>> momentsLD = new MutableLiveData<>();
    private static final String TAG = MomentViewModel.class.getSimpleName();

    public MomentViewModel() {

        momentRepository = new MomentRepository();
    }

    /*public void uploadImageToFirebaseStorage(final Context context,File file, final String eventId) {
*//*
        StorageReference rootRef = FirebaseStorage.getInstance().getReference();
        Uri fileUri = Uri.fromFile(file);
        final StorageReference imageRef = rootRef.child("EventImages/" + fileUri.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(fileUri);
*//*
       // MomentGallary.uploadProgressBar.setVisibility(View.VISIBLE);
        Toast.makeText(context, "Wait Uploading", Toast.LENGTH_SHORT).show();

        StorageReference rootRef = FirebaseStorage.getInstance().getReference();
        Uri fileUri = Uri.fromFile(file);
        final StorageReference imageRef = rootRef.child("EventImages/" + fileUri.getLastPathSegment());

        ///For image Compress
        Bitmap bmp = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(context.getContentResolver(),fileUri);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);


        //For get URI Link of Image

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL

                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
                 //   MomentGallary.uploadProgressBar.setVisibility(View.GONE);
                    Uri downloadUri = task.getResult();
                    Moments moments = new Moments(null, eventId, downloadUri.toString());
                    momentRepository.addNewMoment(moments);
                } else {
                    // Handle failures
                    // ...
                }
            }
        });
*/
    //}
    public void uploadImageToFirebaseStorage(final Context context,File file, final String eventId) {
/*
        StorageReference rootRef = FirebaseStorage.getInstance().getReference();
        Uri fileUri = Uri.fromFile(file);
        final StorageReference imageRef = rootRef.child("EventImages/" + fileUri.getLastPathSegment());
        UploadTask uploadTask = imageRef.putFile(fileUri);
*/
        //MomentGallary.uploadProgressBar.setVisibility(View.VISIBLE);
        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Wait Image Uploading...");
        pd.show();

        StorageReference rootRef = FirebaseStorage.getInstance().getReference();
        Uri fileUri = Uri.fromFile(file);
        final StorageReference imageRef = rootRef.child("EventImages/" + fileUri.getLastPathSegment());

        ///For image Compress
        Bitmap bmp = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(context.getContentResolver(),fileUri);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);


        //For get URI Link of Image

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL

                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();

                    pd.dismiss();
                    Uri downloadUri = task.getResult();
                   // Log.e(TAG, "uploadImageToFirebaseStorage: "+eventId);
                    Moments moments = new Moments(null, eventId, downloadUri.toString());
                    Log.e("moment", "upload completed");
                    Log.e(TAG, "uploadI: "+eventId);
                    momentRepository.addNewMoment(moments);
                } else {
                    // Handle failures
                    // ...
                }
            }
        });

    }
/*    public  void uploadimagetodtb(File file, final String eventid){
        StorageReference rootref= FirebaseStorage.getInstance().getReference();
        Uri uri=Uri.fromFile(file);
        final StorageReference images=rootref.child("images/"+uri.getPathSegments());
        UploadTask uploadTask=images.putFile(uri);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return images.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Log.e("moment", "upload completed");
                    Uri downloadUri = task.getResult();

                    Moments moments=new Moments(null,eventid,downloadUri.toString());
                    momentRepository.addNewMoment(moments);
                } else {
                    // Handle failures
                    // ...
                }
            }
        });

    }*/

    public void getMoments(String eventId){

        momentsLD = momentRepository.getAllMoments(eventId);
    }

    public void deleteImage(Moments momentPojo) {

        momentRepository.deleteImagefromDB(momentPojo);
    }


}
