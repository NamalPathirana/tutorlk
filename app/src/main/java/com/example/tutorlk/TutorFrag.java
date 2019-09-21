package com.example.tutorlk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.tutorlk.model.tutorDetails;

import java.util.ArrayList;
import java.util.List;


public class TutorFrag extends Fragment {

    private RecyclerView sRecycleView;
    private Tutor_feed_adapter mAdapter;                                    //private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<tutorDetails> studs;

    private DatabaseReference mDatabaseRef;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tutor_std,container,false);




        sRecycleView = (RecyclerView) view.findViewById(R.id.recyclerView_tutor_feed);
        sRecycleView.setHasFixedSize(true);
        mLayoutManager =new LinearLayoutManager(getActivity());

        sRecycleView.setLayoutManager((mLayoutManager));
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Tutor");

        final ArrayList<tutorDetails> tutorList =new ArrayList<>();


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSpan : dataSnapshot.getChildren()){

                    tutorDetails stud=postSpan.getValue(tutorDetails.class);
                    tutorList.add(stud);


                }

                mAdapter= new Tutor_feed_adapter(tutorList);
                sRecycleView.setAdapter(mAdapter);

                mAdapter.setOnIemClickListner(new Tutor_feed_adapter.onItemClickLIstner() {
                    @Override
                    public void onItemClick(int position) {

                        String studentid=tutorList.get(position).getUid();
                        tutorList.get(position).OpentTuotrPortal(view.getContext(),studentid);


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

    public void openPortal(String id){ //id os the name




    };


}