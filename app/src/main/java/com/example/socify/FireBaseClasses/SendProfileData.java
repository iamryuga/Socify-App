package com.example.socify.FireBaseClasses;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.socify.Activities.Home;
import com.example.socify.Activities.Registration;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SendProfileData {

    UploadTask uploadTask;
    String currentUID;
    StorageReference storageReference;
    DocumentReference documentReference;
    CollectionReference collectionReference;
    public static HashMap<String, String> profile = new HashMap<>();
    public static HashMap<String, ArrayList<String>> interests = new HashMap<>();

    public void initialization() {
        currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference("Profile Images");
        documentReference = FirebaseFirestore.getInstance().collection("Profiles").document(currentUID);
    }

    public void uploadtoCloud() {
        documentReference.set(profile);
    }

    public void sendImg() {
        initialization();
        if(Registration.details.getImgUri()!="No Image") {
            final StorageReference reference = storageReference.child(Registration.details.getImgUri());
            uploadTask = reference.putFile(Uri.parse(Registration.details.getImgUri()));
            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
                        profile.put("ImgUrl", downloadUrl.toString());
                        documentReference.set(profile)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.i("Img Uploaded", "True");
                                    }
                                });
                    }
                }
            });
        }
        else{
            Log.e("zzz", "No image selected");
            profile.put("ImgUrl", "No Image");
        }

    }

    public void sendName() {
        initialization();
        profile.put("Name", Registration.details.getName());
        uploadtoCloud();
    }

    public void sendpassyear() {
        initialization();
        profile.put("Passing Year", Registration.details.getPassyear());
        uploadtoCloud();
    }

    public void sendUsername() {
        initialization();
        profile.put("Username", Registration.details.getUsername());
        uploadtoCloud();
    }

    public void sendPassword() {
        initialization();
        profile.put("Password", Registration.details.getPassword());
        uploadtoCloud();
    }

    public void sendCollegeName() {
        initialization();
        profile.put("CollegeName", Registration.details.getCollege_name());
        uploadtoCloud();
    }
    public void sendCurrentUID() {
        initialization();
        profile.put("UID", currentUID);
        uploadtoCloud();
    }
    public void sendCourse() {
        initialization();
        profile.put("Course", Registration.details.getCourse());
        uploadtoCloud();
    }

    public void sendDOB() {
        initialization();
        profile.put("Age", Registration.details.getAge());
        uploadtoCloud();
    }

    public void sendBio() {
        initialization();
        profile.put("Bio", Registration.details.getBio());
        uploadtoCloud();
    }

    public void sendTags() {
        currentUID = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        storageReference = FirebaseStorage.getInstance().getReference("Profile Images");
        documentReference = FirebaseFirestore.getInstance().collection("Profiles").document(currentUID);
        interests.put("Tags", Registration.details.getTags());
        documentReference.collection("Interests").document("UserTags").set(interests);
    }
}
