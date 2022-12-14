package com.example.socify.QueryFragments;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.socify.Activities.Home;
import com.example.socify.Classes.QuestionsMember;
import com.example.socify.R;
import com.example.socify.databinding.FragmentAskQueryBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Ask_QueryFragment extends Fragment {

    FragmentAskQueryBinding binding;
    private static final int PICK_FILE=1;
    Uri questionimgURI = Uri.parse("No Image");
    String type;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference Questions;
    StorageReference storageReference;
    NavController controller;
    public static Boolean flag=false;

    //Fragment after successful question
    QuestionsMember member;

    private String getFileExt(Uri uri) {
        ContentResolver contentResolver = requireActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void setonclicklisteners() {

        binding.addimgtv.setOnClickListener(v -> chooseFile());


        binding.askbtn.setOnClickListener(v -> {
            String question = binding.questiontext.getText().toString().trim();
            String tag = binding.categories.getText().toString();

            Calendar cdate = Calendar.getInstance();
            SimpleDateFormat currenDate = new SimpleDateFormat("dd-MMMM-yy");
            final String saveDate = currenDate.format(cdate.getTime());

            Calendar ctime = Calendar.getInstance();
            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
            final String saveTime = currentTime.format(ctime.getTime());

            String time = saveDate + ":" + saveTime;

            if(!question.isEmpty() && !tag.isEmpty()) {
                if(questionimgURI.toString().equals("No Image")) {
                    member.setQuestionURI("No Image");
                }
                member.setUsername(Home.getUserData.username);
                member.setQuestion(question);
                member.setTime(time);
                member.setUserid(Home.getUserData.uid);
                member.setUrl(Home.getUserData.imgurl);
                member.setTag(tag);
                String postid = Questions.push().getKey();
                member.setKey(postid);
//                Log.e("imgURI",member.getQuestionURI());

                if(!questionimgURI.toString().equals("No Image")) {

                    final StorageReference reference = storageReference.child(System.currentTimeMillis() + ":" + getFileExt(questionimgURI));
                    UploadTask uploadTask = reference.putFile(questionimgURI);

                    uploadTask.continueWithTask(task -> {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return reference.getDownloadUrl();
                    }).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Uri downloaduri = task.getResult();
                            member.setQuestionURI(downloaduri.toString());
                            Questions.child(member.getTag().replaceAll("[^A-Za-z]+", "").toLowerCase()).child(Home.getUserData.uid).child(postid).setValue(member);
                            Log.e("Done", "Donee");
                        } else {
                            Toast.makeText(getContext(), "Error Uploading", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Questions.child(member.getTag().replaceAll("[^A-Za-z]+", "").toLowerCase()).child(Home.getUserData.uid).child(postid).setValue(member);
                    Log.e("Done", "Without Image");
                }
                Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show();
                //Navigation Code
                flag = true;
                NavDirections action = Ask_QueryFragmentDirections.actionAskQueryFragmentToQueryTagFragment();
                controller.navigate(action);
            }
            else if(question.isEmpty()){
                Toast.makeText(requireActivity(), "Please enter the question", Toast.LENGTH_SHORT).show();
            }
            else if(tag.isEmpty()) {
                Toast.makeText(requireActivity(), "Please select a tag", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        setonclicklisteners();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAskQueryBinding.inflate(inflater, container, false);
        Questions = database.getReference("College").child(Home.getUserData.college_name).child("Questions");
        storageReference = FirebaseStorage.getInstance().getReference().child("QueryImages").child(Home.getUserData.uid);
        member = new QuestionsMember();
        return binding.getRoot();
    }

    @SuppressLint("IntentReset")
    private void chooseFile() {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == PICK_FILE && data != null) {
            questionimgURI = data.getData();
            if(questionimgURI.toString().contains("image")) {
                Glide.with(this).load(questionimgURI).into(binding.queryimg);
                binding.queryimg.setVisibility(View.VISIBLE);
                binding.addimgtv.setVisibility(View.GONE);
                type = "image";
                Log.e("Selected URI", String.valueOf(questionimgURI));
            }else {
                questionimgURI = Uri.parse("No Image");
                Toast.makeText(requireActivity(), "No File Selected", Toast.LENGTH_SHORT).show();
            }

        }

    }


}