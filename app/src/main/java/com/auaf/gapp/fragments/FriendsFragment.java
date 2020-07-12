package com.auaf.gapp.fragments;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.auaf.gapp.R;
import com.auaf.gapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import static androidx.recyclerview.widget.RecyclerView.*;


public class FriendsFragment extends Fragment {
    RecyclerView rv_friends;
    Toolbar toolbar;
    FriendsAdapter objAdapter;
    List<User> userList;
//    DividerItemDecoration dividerItemDecoration

    public FriendsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View vFriends = inflater.inflate(R.layout.fragment_friends, container, false);
        rv_friends = vFriends.findViewById(R.id.rv_friends);
        toolbar = vFriends.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        rv_friends.setHasFixedSize(true);
        rv_friends.setLayoutManager(new LinearLayoutManager(getActivity()));


        userList = new ArrayList<>();
        getAllUsers();
        return vFriends;

    }

    class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {
        Context context;
        List<User> userList;

        public FriendsAdapter(Context context, List<User> userList) {
            this.context = context;
            this.userList = userList;
        }

        @NonNull
        @Override
        public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.row_friends, parent, false);
            return new FriendsViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
            String userImage = userList.get(position).getPhoto();
            final String userName = userList.get(position).getName();

            holder.txt_friend.setText(userName);
            try {
                Picasso.get().load(userImage).placeholder(R.drawable.friend).into(holder.img_friend);

            } catch (Exception e) {

            }
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "" + userName, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }

        class FriendsViewHolder extends RecyclerView.ViewHolder {

            CircleImageView img_friend;
            TextView txt_friend;

            public FriendsViewHolder(@NonNull View itemView) {
                super(itemView);
                img_friend = itemView.findViewById(R.id.img_friend);
                txt_friend = itemView.findViewById(R.id.txt_friend);
            }
        }
    }

    private void getAllUsers() {
        final FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (!user.getUid().equals(fUser.getUid())) {
                        userList.add(user);
                    }
                    objAdapter = new FriendsAdapter(getActivity(), userList);
                    rv_friends.setAdapter(objAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}