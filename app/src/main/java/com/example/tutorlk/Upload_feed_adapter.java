package com.example.tutorlk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Upload_feed_adapter extends RecyclerView.Adapter<Upload_feed_adapter.ExampleViewHolder>{
    private ArrayList<Uploaded_tab_con> contentList;




    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView=itemView.findViewById(R.id.Content_img);
            mTextView1=itemView.findViewById(R.id.content_name);
            mTextView2=itemView.findViewById(R.id.content_type);
            mTextView3=itemView.findViewById(R.id.con_author);
        }

    }

    public Upload_feed_adapter(ArrayList<Uploaded_tab_con> examplelist){

        contentList=examplelist;


    }


    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.uploded_tab, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        Uploaded_tab_con currentContent =contentList.get(position);

        holder.mImageView.setImageResource(currentContent.getmImageResource());
        holder.mTextView1.setText(currentContent.getmText1());
        holder.mTextView2.setText(currentContent.getmText2());
        holder.mTextView3.setText(currentContent.getmText3());



    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }
}
