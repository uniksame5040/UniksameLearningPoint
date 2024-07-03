package com.uniksame.uniksamelearningpoint.unikfragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.unikadapters.RecyclerMotivationAdapter;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ModelMotivation;

import java.util.ArrayList;


public class FragmentMotivation extends Fragment {
    Activity context;
    View view;
    ArrayList<ModelMotivation> list;
    RecyclerView myrecyclerView;

    public FragmentMotivation() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_motivation, container, false);
        myrecyclerView = view.findViewById(R.id.recycler_view_motivation);

        FirebaseRecyclerOptions<ModelMotivation> currentAfInterModelOptions =
                new FirebaseRecyclerOptions.Builder<ModelMotivation>().setQuery(FirebaseDatabase
                        .getInstance().getReference().child("UniksameHost").child("YouTubeVideo"), ModelMotivation.class).build();

        RecyclerMotivationAdapter recyclerMotivationAdapter = new RecyclerMotivationAdapter(currentAfInterModelOptions);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(context));
        myrecyclerView.setAdapter(recyclerMotivationAdapter);
        recyclerMotivationAdapter.startListening();
        return view;
    }


}