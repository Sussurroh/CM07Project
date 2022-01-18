package com.example.cm07project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class ChatFragment extends Fragment {

    private final String otherUid;
    private final String otherName;
    private FirebaseUser fuser;

    ImageButton btn_send;
    EditText text_send;

    public ChatFragment(String otherUid, String name) {
        this.otherUid = otherUid;
        this.otherName = name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fuser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);;
        TextView tv = (TextView) v.findViewById(R.id.username);
        tv.setText(this.otherName);
        this.btn_send = v.findViewById(R.id.btn_send);
        this.text_send = v.findViewById(R.id.text_send);

        btn_send.setOnClickListener(view -> {
            String msg = text_send.getText().toString();
            if(!msg.isEmpty()){
                sendMessage(fuser.getUid(),this.otherUid, msg);
            } else {
                Toast.makeText(getContext(),
                        "You cant send an empty message",Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        reference.child("Chats").push().setValue(hashMap);
    }

}