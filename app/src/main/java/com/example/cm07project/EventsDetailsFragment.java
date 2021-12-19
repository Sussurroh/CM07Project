package com.example.cm07project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventsDetailsFragment extends Fragment {

    private DatabaseReference reference;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_events_details, null);


        String strtext = getArguments().getString("message");
        if (strtext == "1"){
            //EVENTS 1

        }

        ListView listView = (ListView) root.findViewById(R.id.listView);
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(root.getContext(), R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Events");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    final String a = snapshot.child("id").getValue().toString();
                    if (strtext.toString().equals(a)){
                        //list.add(a);
                        final String n1 = "nome do evento: " + snapshot.child("name").getValue().toString();
                        final String n2 = "Organiador: " + snapshot.child("org").getValue().toString();
                        final String n3 = "Data: " + snapshot.child("date").getValue().toString();
                        list.add(n1);
                        list.add(n2);
                        list.add(n3);
                    }

                    //list.add(a);
                    //list.add(snapshot.getValue().toString());

                }

                adapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return root;
       // return inflater.inflate(R.layout.fragment_messages, container, false);
    }
}
