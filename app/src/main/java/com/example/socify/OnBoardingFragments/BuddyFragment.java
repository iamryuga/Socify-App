package com.example.socify.OnBoardingFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.socify.R;

public class BuddyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root =  (ViewGroup) inflater.inflate(R.layout.fragment_buddy, container, false);
        return root;
    }
}
