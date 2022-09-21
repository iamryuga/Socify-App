package com.example.socify.PostFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.socify.Adapters.PostsViewPagerAdapter;
import com.example.socify.databinding.FragmentPostLoaderBinding;

public class PostLoaderFragment extends Fragment {

    FragmentPostLoaderBinding binding;

    ImagePostFragment imagePostFragment = new ImagePostFragment();
    VideoPostFragment videoPostFragment = new VideoPostFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tablayoutposts.setupWithViewPager(binding.ViewPagerposts);
        PostsViewPagerAdapter postsViewPagerAdapter = new PostsViewPagerAdapter(getChildFragmentManager(), 0);
        postsViewPagerAdapter.addfragment(imagePostFragment, "Images");
        postsViewPagerAdapter.addfragment(videoPostFragment, "Videos");
        binding.ViewPagerposts.setAdapter(postsViewPagerAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPostLoaderBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}