package com.example.tutorlk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class StudentSearch extends Fragment {

    private RecyclerView sRecycleView;
    private Student_feed_adaper mAdapter;                                    //private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Student_tab> studs;

    private DatabaseReference mDatabaseRef;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_student_search, container, false);


        sRecycleView = (RecyclerView) view.findViewById(R.id.recyclerView_student_feed);
        sRecycleView.setHasFixedSize(true);
        mLayoutManager =new LinearLayoutManager(getActivity());

        sRecycleView.setLayoutManager((mLayoutManager));
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Student");

        final ArrayList<Student_tab> studentList =new ArrayList<>();


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSpan : dataSnapshot.getChildren()){

                    Student_tab stud=postSpan.getValue(Student_tab.class);
                    studentList.add(stud);


                }

                mAdapter= new Student_feed_adaper(studentList);
                sRecycleView.setAdapter(mAdapter);

                mAdapter.setOnIemClickListner(new Student_feed_adaper.onItemClickLIstner() {
                    @Override
                    public void onItemClick(int position) {

                        String studentid=studentList.get(position).getUid();
                        studentList.get(position).OpentStudentportal(view.getContext(),studentid);


                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                //mProgressCircle.setVisibility(View.INVISIBLE);

            }
        });






//
//        mAdapter=new Student_feed_adaper(studentList);
//
//        sRecycleView.setAdapter(mAdapter);
//


        return view;








    }


}
