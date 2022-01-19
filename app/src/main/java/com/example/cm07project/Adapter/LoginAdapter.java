package com.example.cm07project.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.cm07project.Fragments.LoginTabFragment;
import com.example.cm07project.Fragments.SignUpTabFragment;


public class LoginAdapter extends FragmentPagerAdapter {
    int totaltabs;

    SignUpTabFragment signUpTabFragment;
    LoginTabFragment loginTabFragment;

    private Context context;

    public LoginAdapter(@NonNull FragmentManager fm, Context context, int totaltabs,  SignUpTabFragment signUpTabFragment, LoginTabFragment loginTabFragment) {
        super(fm);
        this.totaltabs = totaltabs;
        this.signUpTabFragment=signUpTabFragment;
        this.loginTabFragment = loginTabFragment;
    }

    @Override
    public int getCount() {
        return totaltabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                LoginTabFragment loginTabFragment = new LoginTabFragment();
                return loginTabFragment;
            case 1:
                SignUpTabFragment signUpTabFragment = new SignUpTabFragment();
                return signUpTabFragment;
            default:
                return null;
        }
    }
}
