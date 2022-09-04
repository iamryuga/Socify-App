package com.example.socify.HelperClasses;

import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.View;

import com.example.socify.Activities.Registration;
import com.example.socify.Activities.SplashActivity;
import com.example.socify.Adapters.GetCollegeAdapter;
import com.example.socify.Classes.College;
import com.example.socify.Classes.Course;
import com.example.socify.FireBaseClasses.SendProfileData;
import com.example.socify.RegistrationFragments.CoursesFragment;
import com.example.socify.RegistrationFragments.GetCollegeFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OptimizedSearchCourses {
    public  ArrayList<Course> filterlist;
    Thread th;
    boolean stopthread;
    Map<String,ArrayList<Course>> hashMap;
    CoursesFragment getCollegeFragment;

    ArrayList<Course> newfilterlist;
    public OptimizedSearchCourses(CoursesFragment getCollegeFragment){
        filterlist = Registration.courses;
        hashMap = new HashMap<>();
        this.getCollegeFragment = getCollegeFragment;
    }

    String oldtext = "";
    public void startSearch(String newText){

        if(newText.isEmpty()){
            getCollegeFragment.adapter.filterlist(Registration.courses);
            getCollegeFragment.shimmerFrameLayout.setVisibility(View.GONE);
            getCollegeFragment.rec.setVisibility(View.VISIBLE);
            return;
        }

         if(hashMap.get(newText) != null) {
            filterlist = hashMap.get(newText);
            Log.i("this entering","yes");
        }else if(newText.contains(oldtext) && !oldtext.isEmpty())
            filterlist = newfilterlist;
        else
            filterlist = Registration.courses;

        stopthread  = false;
        newfilterlist = new ArrayList<>();
        th =new Thread(new Runnable() {
            @Override
            public void run() {
                for(Course course : filterlist ){
                    if(stopthread)
                        break;


                    String[] keywords = newText.split(" ");
                    String[] collegewords = course.getcoursename().split(" ");

                    if(OptimizedSearchCollege.check(keywords,collegewords) == keywords.length)
                        newfilterlist.add(course);

                }

                if(!stopthread) {
                    hashMap.put(newText, newfilterlist);

                    getCollegeFragment.hand.post(new Runnable() {
                        @Override
                        public void run() {
                            oldtext = newText;

                            getCollegeFragment.adapter.filterlist(newfilterlist);
                            getCollegeFragment.shimmerFrameLayout.setVisibility(View.GONE);
                            getCollegeFragment.rec.setVisibility(View.VISIBLE);
                        }
                    });
                }

            }
        });
        th.start();
    }


    public void stopRunningThread(){
        stopthread = true;
    }




}
