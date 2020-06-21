package com.auaf.gapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.auaf.gapp.application.MyApp;
import com.auaf.gapp.R;
import com.auaf.gapp.fragments.FriendsFragment;
import com.auaf.gapp.fragments.MainFragment;
import com.auaf.gapp.fragments.ProfileFragment;
import com.auaf.gapp.fragments.SettingsFragment;
import com.auaf.gapp.utils.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ViewPager vpMainActivity;
    BottomNavigationView btnviewMainn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vpMainActivity=findViewById(R.id.vpMain);
        btnviewMainn=findViewById(R.id.bottom_navigation);

        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MainFragment());
        adapter.addFragment(new ProfileFragment());
        adapter.addFragment(new FriendsFragment());
        adapter.addFragment(new SettingsFragment());

        vpMainActivity.setAdapter(adapter);




        vpMainActivity.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){

                    case 0:
                        btnviewMainn.setSelectedItemId(R.id.navigationhome);
                        btnviewMainn.setSelected(true);
                        break;
                    case 1:
                        btnviewMainn.setSelectedItemId(R.id.navigation_profile);
                        break;

                    case 2:
                        btnviewMainn.setSelectedItemId(R.id.navigation_friends);
                        btnviewMainn.setSelected(true);
                        break;

                    case 3:
                        btnviewMainn.setSelectedItemId(R.id.navigation_settings);
                        btnviewMainn.setSelected(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        btnviewMainn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.navigationhome:
                        vpMainActivity.setCurrentItem(0);

                        break;
                    case R.id.navigation_profile:
                        vpMainActivity.setCurrentItem(1);

                        break;
                    case R.id.navigation_friends:
                        vpMainActivity.setCurrentItem(2);

                        break;

                    case R.id.navigation_settings:
                        vpMainActivity.setCurrentItem(3);

                        break;
                }

                return false;
            }
        });

    }







    class ViewPagerAdapter extends FragmentPagerAdapter{

        private final List<Fragment> fragmentList=new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment){

            fragmentList.add(fragment);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
