package com.example.socify.HomeFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socify.R;
import com.example.socify.databinding.FragmentCommentsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CommentsFragment extends Fragment {

    FragmentCommentsBinding binding;
    DatabaseReference ref1, ref2;
    String currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCommentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void docomment() {
        ref1 = FirebaseDatabase.getInstance().getReference().child("Posts").child("All Posts").child(currentUID);
        ref2 = FirebaseDatabase.getInstance().getReference().child("Posts").child("User's Posts").child(currentUID);

    }

}