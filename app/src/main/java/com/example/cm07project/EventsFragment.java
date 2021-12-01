
package com.example.cm07project;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class EventsFragment extends Fragment {

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_events, container, false);

        Context ct = getActivity();
        this.recyclerView = v.findViewById(R.id.recyclerView);

        String[] s1 = {"Evento 1", "Evento 2" , "Evento 3" , "Evento 4"};
        String[] s2 = {"Descricao do Evento 1", "Descricao do Evento 2",
                "Descricao do Evento 3" , "Descricao do Evento 4"};

        EAdapter eAdapter = new EAdapter(ct, s1, s2);

        recyclerView.setAdapter(eAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ct));

        return v;
    }
}