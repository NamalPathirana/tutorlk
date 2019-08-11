package com.example.tutorlk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Student_feed_adaper extends RecyclerView.Adapter<Student_feed_adaper.ExampleViewHolder>{

     private ArrayList<Student_tab> mStudentList;
     private onItemClickLIstner mListener;

     public  interface onItemClickLIstner{

         void onItemClick(int position);



     }

     public void setOnIemClickListner(onItemClickLIstner listner){

         mListener = listner;




     }






    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mimageView;
        public TextView  mTextView1;
        public TextView  mTextView2;


        public ExampleViewHolder(View itemView, final onItemClickLIstner listener) {
            super(itemView);

            mimageView=itemView.findViewById(R.id.std_image);
            mTextView1=itemView.findViewById(R.id.std_edu_type);
            mTextView2=itemView.findViewById(R.id.ed_expt);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(listener!= null){

                        int position =getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){

                        listener.onItemClick(position);



                        }


                    }


                }
            });


        }




    }

    public Student_feed_adaper(ArrayList<Student_tab> examplelist){

        mStudentList=examplelist;


    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_exa1,parent,false);
        ExampleViewHolder evh=new ExampleViewHolder(v,mListener);
        return evh;

    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

      Student_tab currentStudent =mStudentList.get(position);

      holder.mimageView.setImageResource(currentStudent.getmImageResource());
      holder.mTextView1.setText(currentStudent.getmText1());
      holder.mTextView2.setText(currentStudent.getmText2());
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();

    }
}
