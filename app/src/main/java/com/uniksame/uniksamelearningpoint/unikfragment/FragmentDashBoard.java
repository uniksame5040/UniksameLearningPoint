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
import com.uniksame.uniksamelearningpoint.unikadapters.RecyclerHomeAdapter;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ModelEducation;

import java.util.ArrayList;

public class FragmentDashBoard extends Fragment {

    Activity context;
    View view;
    ArrayList<Integer> list;

    public FragmentDashBoard() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<>();
        for (int i=0;i<6;i++){
            list.add(i);
        }
//        list.add(new ModelEducation("Communication", R.drawable.teamwork_img));
//    ist.add(new ModelEducation("Dreams", R.drawable.communication_img));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        RecyclerView recyclerViewHome = view.findViewById(R.id.recycler_home);
        RecyclerHomeAdapter recyclerHomeAdapter = new RecyclerHomeAdapter(getContext(), list);
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewHome.setAdapter(recyclerHomeAdapter);

        return view;
    }

}
