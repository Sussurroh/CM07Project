package com.example.cm07project;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.ArrayList;

public class ItemDetailsFragment extends Fragment {

    private DatabaseReference reference;
    private StorageReference storageReference;
    public String userID;
    public TextView username;
    public TextView item;
    public TextView desc;
    public TextView category;
    public TextView quantity;
    public ImageView imageView;
    private String idPhoto;

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
        imageView = (ImageView) root.findViewById(R.id.imageView);



        reference = FirebaseDatabase.getInstance().getReference("Products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    final String a = snapshot.child("id").getValue().toString();
                    if (idItem.toString().equals(a)){
                        Log.i("snap", snapshot.child("photoid").getValue().toString());
                        userID = snapshot.child("userID").getValue().toString();
                        idPhoto = snapshot.child("photoid").getValue().toString();
                        item.setText(snapshot.child("item").getValue().toString());
                        desc.setText(snapshot.child("desc").getValue().toString());
                        category.setText("Categoria: " + snapshot.child("category").getValue().toString());
                        quantity.setText("Disponíveis: " + snapshot.child("quantity").getValue().toString());

                    }

                }

                storageReference = FirebaseStorage.getInstance().getReference().child("images/"+ idPhoto);

                GlideApp.with(getContext()).load(storageReference)
                        .into(imageView);

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

            //storage buscar foto


        return root;
    }
}
