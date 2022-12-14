package com.example.socify.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.socify.HelperClasses.GetUserData;
import com.example.socify.InterfaceClass;
import com.example.socify.R;
import com.google.firebase.auth.FirebaseAuth;


public class SplashActivity extends AppCompatActivity {
    FirebaseAuth auth;
    public static GetUserData getCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();

         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(auth.getCurrentUser()!=null)
                {
                    startActivity(new Intent(getApplicationContext(),Home.class));

                    finish();
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), SlideScreen.class));
                    finish();
                }
            }
        },1600);


    }

}