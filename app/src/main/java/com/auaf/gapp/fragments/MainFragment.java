package com.auaf.gapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.auaf.gapp.R;
import com.auaf.gapp.activities.UploadUserProfile;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainFragment extends Fragment {


    String names[]= {"Safiullah","Ishaq","Jaber","Aqila","Husna"};
    String userFeed[]= {"I can devlope an android app in 30 minutes"," I can develope an IOS app in 15 minutes","I can create android games","I am able to create app worldwide level","I will create an amazing app for Afghans"};
    int photos []={R.drawable.profile,R.drawable.profile,R.drawable.profile,R.drawable.profile,R.drawable.profile};


    RecyclerView rvMain;

    MainAdpater objectAdapter;
    RecyclerView.LayoutManager manager;

    CircleImageView ivProfileUser;


    public MainFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewMain = inflater.inflate(R.layout.fragment_main, container, false);


        rvMain=viewMain.findViewById(R.id.rvMain);

        objectAdapter=new MainAdpater(getActivity(),names,userFeed,photos);

        manager=new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);

        ivProfileUser=viewMain.findViewById(R.id.ivUserProfile);

        ivProfileUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(), UploadUserProfile.class);
                intent.putExtra("type","main");
                startActivity(intent);

            }
        });


        rvMain.setLayoutManager(manager);
        rvMain.setAdapter(objectAdapter);





        return viewMain;

    }


    class MainAdpater extends RecyclerView.Adapter<MainAdpater.MainViewHolder> {

        Context context;
        String [] names;
        String [] feeds;
        int [] photos;


        public MainAdpater(Context context,String[] names,String [] feeds,int [] photos){


           this.context=context;

           this.names=names;
           this.feeds=feeds;
           this.photos=photos;

            }


        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View itemView= getLayoutInflater().inflate(R.layout.layout_item,parent,false);

            return new MainViewHolder(itemView);
        }



        @Override
        public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {

            holder.tvName.setText(names[position]);
            holder.tvUserPost.setText(feeds[position]);
            holder.ivPhoto.setImageResource(photos[position]);

        }

        @Override
        public int getItemCount() {
            return names.length;
        }

        class MainViewHolder extends RecyclerView.ViewHolder{

            CircleImageView ivPhoto;
            TextView tvName;
            TextView tvUserPost;

            public MainViewHolder(@NonNull View itemView) {
                super(itemView);

                ivPhoto= itemView.findViewById(R.id.ivProfile);
                tvName= itemView.findViewById(R.id.tvName);
                tvUserPost=itemView.findViewById(R.id.tvPostUser);
            }
        }
    }


}