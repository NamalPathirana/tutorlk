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
import com.google.firebase.database.Query;
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

        Query query=mDatabaseRef.orderByChild("likes");

        final ArrayList<tutorDetails> tutorList =new ArrayList<>();


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                tutorList.clear(); // important clear the list on the binning because add valueEvent repeats every time database changes

                for (DataSnapshot postSpan : dataSnapshot.getChildren()){

                    tutorDetails stud=postSpan.getValue(tutorDetails.class);
                    tutorList.add(stud);


                }

                decendingLikes(tutorList);


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


    public void decendingLikes(ArrayList<tutorDetails> list){  // by likes make sort the tutors to the decending order

        int n = list.size();
        tutorDetails temp ;

        for(int i=0; i < n; i++){
            for(int j=1; j < (n-i); j++){

                if(list.get(j-1).getViews()< list.get(j).getViews()){
                    //swap the elements!
                    temp = list.get(j-1);
                    list.set(j-1, list.get(j));                 //Do not use add(),it does not replace the value it simply pushes the values to higher
                    list.set(j,temp);                           // indexes,use set() to replace
                }

            }
        }


    }


}