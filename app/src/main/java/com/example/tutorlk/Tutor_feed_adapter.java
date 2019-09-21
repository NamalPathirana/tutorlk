package com.example.tutorlk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tutorlk.R;
import com.example.tutorlk.model.tutorDetails;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Tutor_feed_adapter extends RecyclerView.Adapter<com.example.tutorlk.Tutor_feed_adapter.ExampleViewHolder>{

    private ArrayList<tutorDetails> mTutorList;
    private com.example.tutorlk.Tutor_feed_adapter.onItemClickLIstner mListener;

    public  interface onItemClickLIstner{

        void onItemClick(int position);



    }

    public void setOnIemClickListner(com.example.tutorlk.Tutor_feed_adapter.onItemClickLIstner listner){

        mListener = listner;




    }






    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mimageView;
        public TextView mTextView1;
        public TextView  mTextView2;


        public ExampleViewHolder(View itemView, final com.example.tutorlk.Tutor_feed_adapter.onItemClickLIstner listener) {
            super(itemView);

            mimageView=itemView.findViewById(R.id.tutor_image);
            mTextView1=itemView.findViewById(R.id.tutor_edu_type);
            mTextView2=itemView.findViewById(R.id.tutor_expt);

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

    public Tutor_feed_adapter(ArrayList<tutorDetails> examplelist){

        mTutorList=examplelist;


    }


    @NonNull
    @Override
    public com.example.tutorlk.Tutor_feed_adapter.ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.tutortab,parent,false);
        com.example.tutorlk.Tutor_feed_adapter.ExampleViewHolder evh=new com.example.tutorlk.Tutor_feed_adapter.ExampleViewHolder(v,mListener);
        return evh;

    }

    @Override
    public void onBindViewHolder(@NonNull com.example.tutorlk.Tutor_feed_adapter.ExampleViewHolder holder, int position) {

        tutorDetails currentTutor =mTutorList.get(position);

        Picasso.get().load(currentTutor.getImageUrl()).into(holder.mimageView);
        holder.mTextView1.setText(currentTutor.getName());
        holder.mTextView2.setText(currentTutor.getEducationQualification());

    }

    @Override
    public int getItemCount() {
        return mTutorList.size();

    }
}

