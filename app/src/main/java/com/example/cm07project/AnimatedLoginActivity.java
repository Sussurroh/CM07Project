package com.example.cm07project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import com.example.cm07project.Adapter.LoginAdapter;
import com.example.cm07project.Fragments.LoginTabFragment;
import com.example.cm07project.Fragments.SignUpTabFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class AnimatedLoginActivity extends AppCompatActivity {

    TabLayout mTabLayout;
    ViewPager mViewPager;
    FloatingActionButton fb,google,twitter;
    float v = 0;

    LoginTabFragment loginTabFragment;
    SignUpTabFragment signUpTabFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTabLayout = findViewById(R.id.tab_layout);

        mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF0000"));
        mTabLayout.setBackgroundColor(Color.parseColor("#008577"));
        mTabLayout.setTabTextColors(Color.parseColor("#727272"),Color.parseColor("#D81B60"));

        mViewPager = findViewById(R.id.view_pager);
        fb = findViewById(R.id.fab_fb);
        google = findViewById(R.id.fab_google);
        twitter = findViewById(R.id.fab_twitter);

        mTabLayout.addTab(mTabLayout.newTab().setText("Login"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Sign Up"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        loginTabFragment = new LoginTabFragment();
        signUpTabFragment = new SignUpTabFragment();

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(),this,mTabLayout.getTabCount(), signUpTabFragment, loginTabFragment);
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0)
                    loginTabFragment.setAnimation();
                if(position==1)
                    signUpTabFragment.setAnimation();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        fb.setTranslationY(300);
        google.setTranslationY(300);
        twitter.setTranslationY(300);
        mTabLayout.setTranslationY(300);

        fb.setAlpha(v);
        google.setAlpha(v);
        twitter.setAlpha(v);
        mTabLayout.setAlpha(v);

        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(600).start();
        twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(800).start();
        twitter.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
    }
}