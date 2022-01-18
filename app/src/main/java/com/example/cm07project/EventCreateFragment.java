package com.example.cm07project;

import android.app.usage.UsageEvents;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class EventCreateFragment extends Fragment {

    private EditText mname;
    private EditText mdesc;
    private EditText morg;
    private EditText mdata;
    private EditText mrua;
    private EditText mdist;
    private Button create;
    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_event_create, null);

        create = root.findViewById(R.id.createEventButton);

        mname = root.findViewById(R.id.eventnameedit);
        mdesc = root.findViewById(R.id.eventdescedit);
        morg = root.findViewById(R.id.org_edit);
        mdata = root.findViewById(R.id.date_edit);
        mrua = root.findViewById(R.id.rua_edit);
        mdist = root.findViewById(R.id.district_edit);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n1 = mname.getText().toString();
                String n2 = mdesc.getText().toString();
                String n3 = morg.getText().toString();
                String n4 = mdata.getText().toString();
                String n5 = mrua.getText().toString();
                String n6 = mdist.getText().toString();
                String n7 = "Portugal";
                String n8 = UUID.randomUUID().toString();


                Evento event = new Evento(n1,n2,n3,n4,n5,n6,n7,n8);


                FirebaseDatabase.getInstance().getReference("Events")
                        //.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .push()
                        .setValue(event).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getActivity(), "Evento registered", Toast.LENGTH_SHORT).show();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            EventsFragment llf = new EventsFragment();
                            ft.replace(R.id.container, llf);
                            ft.addToBackStack("tag");
                            ft.commit();
                        }else {
                            Toast.makeText(getActivity(), "Evento Failed:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });




        return  root;
    }
}
