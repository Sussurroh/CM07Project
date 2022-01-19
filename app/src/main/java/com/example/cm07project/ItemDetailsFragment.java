package com.example.cm07project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemDetailsFragment extends Fragment {

    private DatabaseReference reference;
    public String userID;
    public TextView username;
    public TextView item;
    public TextView desc;
    public TextView category;
    public TextView quantity;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_item_details, null);

        //Vai buscar o id do evento passado pelo EventsFragment
        String idItem = getArguments().getString("message");

        Log.i("id",idItem);
        username = (TextView) root.findViewById(R.id.usernamevalue);
        item = (TextView) root.findViewById(R.id.titlevalue);
        desc = (TextView) root.findViewById(R.id.descvalue);
        category = (TextView) root.findViewById(R.id.categoryvalue);
        quantity = (TextView) root.findViewById(R.id.quantvalue);


        reference = FirebaseDatabase.getInstance().getReference("Products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    final String a = snapshot.child("id").getValue().toString();
                    if (idItem.toString().equals(a)){
                        //list.add(a);
                        userID = snapshot.child("userID").getValue().toString();
                        item.setText(snapshot.child("item").getValue().toString());
                        desc.setText(snapshot.child("desc").getValue().toString());
                        category.setText("Categoria: " + snapshot.child("category").getValue().toString());
                        quantity.setText("Disponíveis: " + snapshot.child("quantity").getValue().toString());

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }});

            DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Users");
            reference2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                        final String a = snapshot.getKey();
                        if (userID.equals(a)){
                            username .setText(snapshot.child("firstname").getValue().toString() + " " + snapshot.child("lastname").getValue().toString());

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }});



        return root;
    }
}