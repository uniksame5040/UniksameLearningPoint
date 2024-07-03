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
import com.uniksame.uniksamelearningpoint.unikadapters.ComputerDailyShortsAdapter;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ComputerShortsModel;

public class FragmentInterview extends Fragment {

    public static final int INTERVIEW_CODE = 102;
    Activity context;
    View view;
    RecyclerView interviewFragRecycler;
    private ComputerDailyShortsAdapter adapter;

    public FragmentInterview() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_interview, container, false);
        interviewFragRecycler = view.findViewById(R.id.recycler_view_interview);

        FirebaseRecyclerOptions<ComputerShortsModel> currentAfInterModelOptions =
                new FirebaseRecyclerOptions.Builder<ComputerShortsModel>().setQuery(FirebaseDatabase
                        .getInstance().getReference().child("UniksameHost").child("ComputerDailyShorts"), ComputerShortsModel.class).build();
        adapter = new ComputerDailyShortsAdapter(currentAfInterModelOptions,INTERVIEW_CODE);
        adapter.startListening();
        interviewFragRecycler.setLayoutManager(new LinearLayoutManager(context));
        interviewFragRecycler.setAdapter(adapter);
        return view;
    }
}