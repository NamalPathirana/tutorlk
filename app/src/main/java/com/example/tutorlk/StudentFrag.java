package com.example.tutorlk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class StudentFrag extends Fragment {

    private RecyclerView sRecycleView;
    private Student_feed_adaper mAdapter;                                    //private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.frag_student_feed,container,false);

        final ArrayList <Student_tab> studentList =new ArrayList<>();

        studentList.add(new Student_tab(R.drawable.studentpic,"Jack nicolson","o/l student"));
        studentList.add(new Student_tab(R.drawable.stud2,"Ramdom Guy","postgraduate"));
        studentList.add(new Student_tab(R.drawable.studentpic,"Student 3","Education"));
        studentList.add(new Student_tab(R.drawable.studentpic,"Student 4","Education"));
        studentList.add(new Student_tab(R.drawable.studentpic,"Student 5","Education"));
        studentList.add(new Student_tab(R.drawable.studentpic,"Student 6","Education"));
        studentList.add(new Student_tab(R.drawable.studentpic,"Student 7","Education"));
        studentList.add(new Student_tab(R.drawable.studentpic,"Student 8","Education"));
        studentList.add(new Student_tab(R.drawable.studentpic,"Student 9","Education"));
        studentList.add(new Student_tab(R.drawable.studentpic,"Student 10","Education"));
        studentList.add(new Student_tab(R.drawable.studentpic,"Student 11","Education"));





        sRecycleView = (RecyclerView) view.findViewById(R.id.recyclerView_student_feed);
        sRecycleView.setHasFixedSize(true);
        mLayoutManager =new LinearLayoutManager(getActivity());
        mAdapter=new Student_feed_adaper(studentList);


        sRecycleView.setLayoutManager((mLayoutManager));
        sRecycleView.setAdapter(mAdapter);

        mAdapter.setOnIemClickListner(new Student_feed_adaper.onItemClickLIstner() {
            @Override
            public void onItemClick(int position) {

                String studentid=studentList.get(position).getmText1();
                studentList.get(position).OpentStudentportal(view.getContext(),studentid);



            }
        });

        return view;
    }

    public void openPortal(String id){ //id os the name




    };


}
