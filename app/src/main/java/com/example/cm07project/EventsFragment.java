
package com.example.cm07project;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class EventsFragment extends Fragment {

    private ListView listView;
    private FirebaseUser user;
    private DatabaseReference reference;
    private Button v1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_events, null);

        ListView listView = (ListView) root.findViewById(R.id.listView);
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(root.getContext(), R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        final TextView but = (TextView) root.findViewById(R.id.eventvalue);
        final Button v1 = root.findViewById(R.id.eventdetails1);



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Events");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //EventsInformation info = snapshot.getValue(EventsInformation.class);
                    //String txttt = info.getEvent_name();

                    final String a = snapshot.child("name").getValue().toString();
                    //list.add(snapshot.getValue().toString());
                    //but.setText(a);
                    list.add(a);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Bundle bundle = new Bundle();

        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                EventsDetailsFragment llf = new EventsDetailsFragment();
                bundle.putString("message", "1");
                llf.setArguments(bundle);
                ft.replace(R.id.container, llf);
                ft.commit();
            }
        });

        return root;
    }
}