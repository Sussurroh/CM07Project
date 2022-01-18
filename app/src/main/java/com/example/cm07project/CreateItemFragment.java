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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;


public class CreateItemFragment extends Fragment {
    private EditText mtitle;
    private EditText mdesc;
    private EditText mquant;
    private String category;
    private Spinner spinner;
    private Button create;
    private DatabaseReference reference;
    private FirebaseUser user;
    private String userid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_create_item, null);

        spinner = (Spinner) root.findViewById(R.id.category_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),R.array.category_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        create = root.findViewById(R.id.createItemButton);

        mtitle = root.findViewById(R.id.itemtitleedit);
        mdesc = root.findViewById(R.id.itemdescedit);
        mquant = root.findViewById(R.id.itemquant);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userid = user.getUid();

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createItem();

            }
        });

        return  root;


    }
    private void createItem() {
        String title = mtitle.getText().toString();
        String desc = mdesc.getText().toString();
        category = spinner.getSelectedItem().toString();

        if(title.isEmpty()) {
            Toast.makeText(getActivity(), "Título obrigatório", Toast.LENGTH_SHORT).show();

        } else if(desc.isEmpty()) {
            Toast.makeText(getActivity(), "Descrição obrigatória", Toast.LENGTH_SHORT).show();

        } else if(category.isEmpty()) {
            Toast.makeText(getActivity(), "Categoria obrigatória", Toast.LENGTH_SHORT).show();

        } else if(mquant.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Descrição obrigatória", Toast.LENGTH_SHORT).show();

        } else {
            int quant = Integer.parseInt(mquant.getText().toString());

            Products itemP = new Products(userid, title,desc, category,quant);

            FirebaseDatabase.getInstance().getReference("Products")
                    .push()
                    .setValue(itemP).addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(getActivity(), "Item registered", Toast.LENGTH_SHORT).show();

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();

                        ProfileFragment llf = new ProfileFragment();

                        ft.replace(R.id.container, llf);
                        ft.addToBackStack("tag");
                        ft.commit();

                    }else {
                        Toast.makeText(getActivity(), "Item Failed:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}