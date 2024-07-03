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
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ComputerShortsModel;
import com.uniksame.uniksamelearningpoint.unikhelpermodels.ModelEducation;
import com.uniksame.uniksamelearningpoint.R;
import com.uniksame.uniksamelearningpoint.unikadapters.RecyclerEducationTopicAdapter;

import java.util.ArrayList;

public class FragmentEducation extends Fragment {
    Activity context;
    View view;
    ArrayList<ModelEducation> list;
    RecyclerView recycler_education;

    public FragmentEducation() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list= new ArrayList<>();
        list.add(new ModelEducation("Personality Development", R.drawable.teamwork_img));
        list.add(new ModelEducation("Functional English vol.1",R.drawable.communication_img));
        list.add(new ModelEducation("Functional English vol.2 ",R.drawable.teamwork_img));
        list.add(new ModelEducation("Basic Computer",R.drawable.communication_img));
        list.add(new ModelEducation("Fundamental Math & logic",R.drawable.teamwork_img));
        list.add(new ModelEducation("Official Skills",R.drawable.communication_img));
        list.add(new ModelEducation("Motivation",R.drawable.teamwork_img));
        list.add(new ModelEducation("General knowledge",R.drawable.communication_img));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_education, container, false);
        recycler_education = view.findViewById(R.id.recycler_view_education);

        FirebaseRecyclerOptions<ComputerShortsModel> crrentEducationAdapter =
                new FirebaseRecyclerOptions.Builder<ComputerShortsModel>().setQuery(FirebaseDatabase
                        .getInstance().getReference().child("UniksameHost").child("FreeCourse").child("AllCourses"),
                        ComputerShortsModel.class).build();

        RecyclerEducationTopicAdapter recyclerEducationTopicAdapter = new RecyclerEducationTopicAdapter(crrentEducationAdapter,"All Courses");
        recycler_education.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_education.setAdapter(recyclerEducationTopicAdapter);
        recyclerEducationTopicAdapter.startListening();

         return view;
    }

//    private void topOfDay(Activity context, View view) {
//        recyclerTopOfDay = view.findViewById(R.id.recycler_top_of_day);
//        TopOfDayAdapter topOfDayAdapter = new TopOfDayAdapter(getContext(),list);
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL,false);
//        recyclerTopOfDay.setLayoutManager(linearLayoutManager);
//        recyclerTopOfDay.setAdapter(topOfDayAdapter);
//    }

}