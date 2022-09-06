package com.example.socify.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.socify.Classes.College;
import com.example.socify.Classes.Course;
import com.example.socify.FireBaseClasses.UserDetails;
import com.example.socify.R;
import com.example.socify.RegistrationFragments.CoursesFragment;
import com.example.socify.RegistrationFragments.GetCollegeFragment;
import com.example.socify.RegistrationFragments.InterestsFragment;
import com.example.socify.RegistrationFragments.ProfilePic;
import com.example.socify.RegistrationFragments.UserNameFragment;
import com.example.socify.databinding.ActivityRegistrationBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

import io.grpc.InternalServer;

public class Registration extends AppCompatActivity {
    ArrayList<College> colleges;
    ArrayList<Fragment> gotoFragment;
    ActivityRegistrationBinding binding;
    DatabaseReference ref;
    public static UserDetails details = new UserDetails();
    public static ArrayList<Course> courses;
    public static ProfilePic profilePic ;
    public static UserNameFragment userNameFragment;
    public static GetCollegeFragment getCollegeFragment;
    public static CoursesFragment coursesFragment;
    public static InterestsFragment interestsFragment;
    public static int fragment_curr_pos =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ref = FirebaseDatabase.getInstance().getReference("Courses");
        courses = new ArrayList<>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (DataSnapshot snap : snapshot.getChildren()) {

                            Course course = new Course(Objects.requireNonNull(snap.child("course").getValue()).toString());
                            courses.add(course);
                            Log.i("course name ", course.getcoursename());
                        }

                    }
                }).start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        profilePic = new ProfilePic();
        userNameFragment = new UserNameFragment();
        getCollegeFragment = new GetCollegeFragment();
        coursesFragment = new CoursesFragment();
        interestsFragment = new InterestsFragment();
        gotoFragment = new ArrayList<>();
        gotoFragment.add(profilePic);
        gotoFragment.add(userNameFragment);
        gotoFragment.add(getCollegeFragment);
        gotoFragment.add(coursesFragment);
        gotoFragment.add(interestsFragment);


        getSupportFragmentManager().beginTransaction().add(R.id.frame_registration, profilePic).commit();
        binding.backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment_curr_pos--;
                if(fragment_curr_pos == 0)
                    binding.backIcon.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_registration, gotoFragment.get(fragment_curr_pos)).commit();
            }
        });
    }


}