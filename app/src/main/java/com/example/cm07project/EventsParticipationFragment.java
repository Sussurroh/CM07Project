package com.example.cm07project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventsParticipationFragment extends Fragment {
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userid;
    private EditText edit;
    private Button addd;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_events__participation, null);


        addd = root.findViewById(R.id.participar);

        String strtext = getArguments().getString("message");

        ListView listView = (ListView) root.findViewById(R.id.listView);
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(root.getContext(), R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("People");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    final String a = snapshot.child("eventid").getValue().toString();
                    if (strtext.toString().equals(a)){
                        //list.add(a);
                        final String n1 =  snapshot.child("name").getValue().toString();

                        list.add(n1);

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

        /** Profile **/
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Users");
        userid = user.getUid();


        addd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference2.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User userprofile = snapshot.getValue(User.class);
                        if (userprofile != null) {
                            String firstname = userprofile.firstname;
                            String lastname = userprofile.lastname;
                            String fullname = firstname +" "+lastname;
                            People event = new People(strtext.toString(),fullname);
                            
                            FirebaseDatabase.getInstance().getReference("People")
                                    //.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .push()
                                    .setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getActivity(), "Participação registada", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getActivity(), "Participação Failed:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });








            }
        });


        return root;
    }
}
