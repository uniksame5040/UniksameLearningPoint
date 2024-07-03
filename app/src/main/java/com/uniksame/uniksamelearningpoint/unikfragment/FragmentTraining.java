package com.uniksame.uniksamelearningpoint.unikfragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.unikadapters.GkFragmentAdapter;

import java.util.ArrayList;

public class FragmentTraining extends Fragment {
    Activity context;
    View view;
    ArrayList<Integer> list;
    RecyclerView recyclerTrainingFrag;

    public FragmentTraining() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(i);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_training, container, false);
        recyclerTrainingFrag = view.findViewById(R.id.recycler_view_training);
        GkFragmentAdapter gkFragmentAdapter = new GkFragmentAdapter(getContext(), list);

        recyclerTrainingFrag.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerTrainingFrag.setAdapter(gkFragmentAdapter);
        // Inflate the layout for this fragment

        return view;
    }
}