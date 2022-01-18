package com.example.cm07project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class ChatFragment extends Fragment {

    private final String otherName;
    private User user;
    private FirebaseUser fuser;

    public ChatFragment(String otherName) {
        this.otherName = otherName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fuser = FirebaseAuth.getInstance().getCurrentUser();
        this.user = getUserFromDB();

    }

    private User getUserFromDB() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        Query query = databaseReference.orderByChild("guardianEmail").equalTo("guardian@example.com");
        final User[] user1 = new User[1];
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final User userz = snapshot.getValue(User.class);
                assert userz != null;

                user1[0] = userz;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                user1[0] = null;
            }
        });
        return user1[0];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);;
        TextView tv = (TextView) v.findViewById(R.id.username);
        tv.setText(this.otherName);

        return v;
    }
}