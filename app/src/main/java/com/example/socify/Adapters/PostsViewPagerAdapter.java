package com.example.socify.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PostsViewPagerAdapter extends FragmentPagerAdapter {

    public List<Fragment> fragments = new ArrayList<>();
    public List<String> fragment_title = new ArrayList<>();

    public PostsViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    public void addfragment(Fragment fragment, String title) {
        fragments.add(fragment);
        fragment_title.add(title);
        notifyDataSetChanged();
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragment_title.get(position);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
