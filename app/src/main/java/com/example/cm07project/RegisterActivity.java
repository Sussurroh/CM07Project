package com.example.cm07project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    Button button;

    FirebaseAuth mAuth;

    EditText memail;
    EditText mpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.button = findViewById(R.id.signUp);

        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);



        mAuth = FirebaseAuth.getInstance();
        button.setOnClickListener(view -> {
            createUser();
        });


    }

    private void createUser() {
        String mail = memail.getText().toString();
        String pass = mpassword.getText().toString();

        if (TextUtils.isEmpty(mail)){
            memail.setError("Email cannot be empty");
            memail.requestFocus();
        } else if (TextUtils.isEmpty(pass)){
            mpassword.setError("Password cannot be empty");
            mpassword.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "User registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}