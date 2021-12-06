package com.example.cm07project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button button;
    TextView registerText;

    EditText memail;
    EditText mpass;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.button = findViewById(R.id.button);
        this.registerText = findViewById(R.id.registerText);
        memail = findViewById(R.id.loginText);
        mpass = findViewById(R.id.passwordText);

        mAuth = FirebaseAuth.getInstance();
        
        button.setOnClickListener(view -> {
            loginUser();
        });

        registerText.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

    }

    private void loginUser() {
        String mail = memail.getText().toString();
        String pass = mpass.getText().toString();

        if (TextUtils.isEmpty(mail)){
            memail.setError("Email cannot be empty");
            memail.requestFocus();
        } else if (TextUtils.isEmpty(pass)){
            mpass.setError("Password cannot be empty");
            mpass.requestFocus();
        }else{
            mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "User logged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else {
                        Toast.makeText(LoginActivity.this, "Log in Failed:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}