package com.example.cm07project.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cm07project.R;


public class SignUpTabFragment extends Fragment {
    EditText email, firstname, lastname, password;//confirm;
    Button sign_up;
    Switch org;
    float v = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);


        email = root.findViewById(R.id.emailreg);
        password = root.findViewById(R.id.passwordSignUp);
        firstname = root.findViewById(R.id.firstname);
        lastname = root.findViewById(R.id.lastname);
        org = root.findViewById(R.id.organization);
//        confirm = root.findViewById(R.id.confirm_password);
        sign_up = root.findViewById(R.id.btn_signup);

        setAnimation();
        return root;
    }

    public void setAnimation() {
        email.setTranslationX(800);
        password.setTranslationX(800);
        firstname.setTranslationX(800);
        lastname.setTranslationX(800);
        org.setTranslationX(800);
//        confirm.setTranslationX(800);
        sign_up.setTranslationX(800);

        email.setAlpha(v);
        password.setAlpha(v);
        firstname.setAlpha(v);
        lastname.setAlpha(v);
        org.setAlpha(v);
//        confirm.setAlpha(v);
        sign_up.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        firstname.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        lastname.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        org.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
//        confirm.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(700).start();
        sign_up.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();


    }
}
