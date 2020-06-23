package com.auaf.gapp.fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.auaf.gapp.R;
import com.auaf.gapp.activities.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.recyclerview.widget.RecyclerView.*;


public class FriendsFragment extends Fragment {
    int photos[] = {R.drawable.friend,R.drawable.friend,R.drawable.friend,R.drawable.friend,R.drawable.friend,R.drawable.friend,R.drawable.friend,R.drawable.friend,R.drawable.friend,R.drawable.friend};
    String names[] = {"Amina" , "Husnia" ,"Marwa" , "Hamida" , "Laila","Amina" , "Husnia" ,"Marwa" , "Hamida" , "Laila"};

    RecyclerView rv_friends;
    FriendsAdapter objectAdapter;
    LayoutManager manager;
    DatabaseReference ref;




    public FriendsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View vFriends =  inflater.inflate(R.layout.fragment_friends, container, false);
        rv_friends = vFriends.findViewById(R.id.rv_friends);
        objectAdapter = new FriendsAdapter(getActivity(),photos,names);
        manager = new LinearLayoutManager(getActivity(),  RecyclerView.VERTICAL,false);
        rv_friends.setLayoutManager(manager);
        rv_friends.setAdapter(objectAdapter);

        return vFriends;



    }
    class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder>{

        Context context;
        int [] photos;
        String [] names;


        public FriendsAdapter(Context context,int [] photos,String [] names){
            this. context = context;
            this.photos = photos;
            this.names = names;

        }
        @NonNull
        @Override
        public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.layout_friends,parent,false);
            return new FriendsViewHolder(itemView);        }

        @Override
        public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
            holder.txt_friend.setText(names[position]);
            holder.img_friend.setImageResource(photos[position]);


        }

        @Override
        public int getItemCount() {
            return names.length;
        }

        class FriendsViewHolder extends RecyclerView.ViewHolder{

            CircleImageView img_friend;
            TextView txt_friend;

            public FriendsViewHolder(@NonNull View itemView) {
                super(itemView);
                img_friend = itemView.findViewById(R.id.img_friend);
                txt_friend = itemView.findViewById(R.id.txt_friend);
            }
        }
    }

        @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {

                                          }
                                      }
        );
    }
}