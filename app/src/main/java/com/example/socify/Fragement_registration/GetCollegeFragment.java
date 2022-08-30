package com.example.socify.Fragement_registration;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.se.omapi.SEService;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.socify.Adapters.GetCollegeAdapter;
import com.example.socify.Classes.College;
import com.example.socify.R;
import com.example.socify.databinding.FragmentGetCollegeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class GetCollegeFragment extends Fragment {

    FragmentGetCollegeBinding binding;
    DatabaseReference ref;
    ArrayList<College> colleges;
    GetCollegeAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        colleges = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("CollegeNames");
         adapter = new GetCollegeAdapter(getContext(),colleges);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentGetCollegeBinding.inflate(getLayoutInflater());
        ProgressBar bar = requireActivity().findViewById(R.id.progressBar);
        bar.setProgress(60);
        RecyclerView rec = view.findViewById(R.id.CollegeListRV);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        rec.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        binding.searchcollege.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                adapter.notifyDataSetChanged();
                return true;
            }
        });
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for(DataSnapshot snap : snapshot.getChildren()){
                    College college = snap.getValue(College.class);
                    assert college != null;
                    Log.i("collegename",college.getCollege_name());
                    colleges.add(college);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_college, container, false);
    }
}