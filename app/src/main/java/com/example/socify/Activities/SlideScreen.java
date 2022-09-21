package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;

import com.example.socify.Adapters.SlideViewPagerAdapter;
import com.example.socify.R;
import com.example.socify.databinding.ActivitySlideScreenBinding;

public class SlideScreen extends AppCompatActivity {

    ActivitySlideScreenBinding binding;
    SlideViewPagerAdapter slideViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySlideScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        slideViewPagerAdapter = new SlideViewPagerAdapter(this);
        binding.ViewPager.setAdapter(slideViewPagerAdapter);
        binding.dotsIndicator.setViewPager(binding.ViewPager);

        binding.Loginbtn.setOnClickListener(v ->
        {
            startActivity(new Intent(getApplicationContext(),Login.class));
        } );

        binding.Signupbtn.setOnClickListener(v -> {
            startActivity(new Intent(SlideScreen.this,VerificationActivity.class));
            finishAffinity();
        });


    }


}