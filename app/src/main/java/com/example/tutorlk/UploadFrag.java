package com.example.tutorlk;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UploadFrag extends Fragment {

    private RecyclerView sRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.frag_upload_feed,container,false);

        ArrayList<Uploaded_tab_con> contentList =new ArrayList<>();

        contentList.add(new Uploaded_tab_con(R.drawable.ic_layers_black_24dp,"O/l pastpapers","Exam preparation ","Author c.clark"));
        contentList.add(new Uploaded_tab_con(R.drawable.ic_layers_black_24dp,"O/l pastpapers","Exam preparation ","Author c.clark"));
        contentList.add(new Uploaded_tab_con(R.drawable.ic_layers_black_24dp,"Short notes","Educational material","M.K.R ravinath"));
        contentList.add(new Uploaded_tab_con(R.drawable.ic_layers_black_24dp,"Short notes","Educational material","M.K.R ravinath"));
        contentList.add(new Uploaded_tab_con(R.drawable.ic_layers_black_24dp,"Short notes","Educational material","M.K.R ravinath"));
        contentList.add(new Uploaded_tab_con(R.drawable.ic_layers_black_24dp,"Short notes","Educational material","M.K.R ravinath"));
        contentList.add(new Uploaded_tab_con(R.drawable.ic_layers_black_24dp,"Short notes","Educational material","M.K.R ravinath"));

        sRecycleView = (RecyclerView) view.findViewById(R.id.recyclerView_content_feed);
        sRecycleView.setHasFixedSize(true);
        mLayoutManager =new LinearLayoutManager(getActivity());
        mAdapter=new Upload_feed_adapter(contentList);


        sRecycleView.setLayoutManager((mLayoutManager));
        sRecycleView.setAdapter(mAdapter);


        Button upload=(Button)view.findViewById(R.id.content_upload_btn);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getActivity(),contentUploadForm.class);
                startActivity(intent);

            }
        });





        return view;

    }


    public void onclick(){

        Intent intent=new Intent(getActivity(),contentUploadForm.class);
        startActivity(intent);




    }



}
