package com.example.cm07project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;
    TextView imageView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        /** Profile **/
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userid = user.getUid();


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, null);
        final TextView but = (TextView) root.findViewById(R.id.firstnamevalue);
        final TextView but2 = (TextView) root.findViewById(R.id.lastnamevalue);
        final TextView but3 = (TextView) root.findViewById(R.id.emailvalue);


        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);

                if (userprofile != null) {
                    String firstname = userprofile.firstname;
                    String lastname = userprofile.lastname;
                    String email = userprofile.email;
                    but.setText(firstname);
                    but2.setText(lastname);
                    but3.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               // Toast.makeText(ProfileFragment.this, "Profile Failed:" , Toast.LENGTH_SHORT).show();
            }
        });

        //return inflater.inflate(R.layout.fragment_profile, container, false);
        return root;

    }
}