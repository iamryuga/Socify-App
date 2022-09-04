package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.socify.QueryFragments.Ask_QueryFragment;
import com.example.socify.QueryFragments.QueryListFragment;
import com.example.socify.R;
import com.example.socify.databinding.ActivityQnBinding;

public class QnA extends AppCompatActivity {

    ActivityQnBinding binding;
    Ask_QueryFragment ask_queryFragment;
    QueryListFragment questionListFragment;
    public static int fragwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQnBinding.inflate(getLayoutInflater());
        ask_queryFragment = new Ask_QueryFragment();
        questionListFragment = new QueryListFragment();

        if(fragwitch==0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.queryFragmentLoader, ask_queryFragment).commit();
        }
        else if(fragwitch==1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.queryFragmentLoader, questionListFragment).commit();
        }
        setContentView(binding.getRoot());
    }
}