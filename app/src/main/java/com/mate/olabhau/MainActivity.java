package com.mate.olabhau;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mate.olabhau.fragments.HomeFragment;
import com.mate.olabhau.fragments.MapFragment;
import com.mate.olabhau.fragments.StatusFragment;
import com.mate.olabhau.utils.Constants;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity{

    private static final String LOGTAG = MainActivity.class.getSimpleName();

    private Context context;

    private static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;

    private static final int NUM_PAGES = 3;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

//        fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        //transaction.add(HomeFragment.newInstance("Home Fragment"), "HomeFragment");
//        transaction.replace(R.id.fragmentMain, new StatusFragment());
//        transaction.commit();

        //openFragment(Constants.HOME_FRAGMENT_COUNT);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(1);
//
//        StatusFragment sts = new StatusFragment();

    }

//    public void openFragment(int fragmentCount){
//        switch(fragmentCount){
//            case Constants.HOME_FRAGMENT_COUNT:
//                openFragment(new HomeFragment());
//                break;
//            case Constants.MAP_FRAGMENT_COUNT:
//                openFragment(new MapFragment());
//                break;
//            case Constants.STATUS_FRAGMENT_COUNT:
//                openFragment(new StatusFragment());
//                break;
//        }
//    }
//
//    private void openFragment(Fragment fragment){
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(fragment, "");
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        fragmentTransaction.commit();
//    }

//    @Override
//    public void onBackPressed() {
//        if (mPager.getCurrentItem() == 0) {
//            // If the user is currently looking at the first step, allow the system to handle the
//            // Back button. This calls finish() on this activity and pops the back stack.
//            super.onBackPressed();
//        } else {
//            // Otherwise, select the previous step.
//            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
//        }
//    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new StatusFragment();
            }else if(position == 1){
                return new HomeFragment();
            }else if(position == 2){
                return new MapFragment();
            }else{
                return new HomeFragment();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
