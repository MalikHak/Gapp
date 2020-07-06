package com.auaf.gapp.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.auaf.gapp.R;
import com.auaf.gapp.activities.UploadUserProfile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainFragment extends Fragment {


    String names[] = {"Safiullah", "Ishaq", "Jaber", "Aqila", "Husna"};
    String userFeed[] = {"I can devlope an android app in 30 minutes", " I can develope an IOS app in 15 minutes", "I can create android games", "I am able to create app worldwide level", "I will create an amazing app for Afghans"};
    int photos[] = {R.drawable.profile, R.drawable.profile, R.drawable.profile, R.drawable.profile, R.drawable.profile};


    RecyclerView rvMain;

    MainAdpater objectAdapter;
    RecyclerView.LayoutManager manager;
    FloatingActionButton fbPostData;

    CircleImageView ivProfileUser;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewMain = inflater.inflate(R.layout.fragment_main, container, false);


        rvMain = viewMain.findViewById(R.id.rvMain);

        fbPostData = viewMain.findViewById(R.id.fbPostData);


        objectAdapter = new MainAdpater(getActivity(), names, userFeed, photos);

        manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        ivProfileUser = viewMain.findViewById(R.id.ivUserProfile);

        ivProfileUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), UploadUserProfile.class);
                intent.putExtra("type", "main");
                startActivity(intent);

            }
        });


        rvMain.setLayoutManager(manager);
        rvMain.setAdapter(objectAdapter);

        fbPostData.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                showPostDialog();

            }
        });


        return viewMain;

    }


    class MainAdpater extends RecyclerView.Adapter<MainAdpater.MainViewHolder> {

        Context context;
        String[] names;
        String[] feeds;
        int[] photos;


        public MainAdpater(Context context, String[] names, String[] feeds, int[] photos) {


            this.context = context;

            this.names = names;
            this.feeds = feeds;
            this.photos = photos;

        }


        @NonNull
        @Override
        public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            View itemView = getLayoutInflater().inflate(R.layout.layout_item, parent, false);

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

        class MainViewHolder extends RecyclerView.ViewHolder {

            CircleImageView ivPhoto;
            TextView tvName;
            TextView tvUserPost;

            public MainViewHolder(@NonNull View itemView) {
                super(itemView);

                ivPhoto = itemView.findViewById(R.id.ivProfile);
                tvName = itemView.findViewById(R.id.tvName);
                tvUserPost = itemView.findViewById(R.id.tvPostUser);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showPostDialog() {


        final View dialogView = View.inflate(getActivity(), R.layout.dialogpost, null);

        final Dialog dialog = new Dialog(getActivity(), R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        ImageView ivCloseDialog = (ImageView) dialog.findViewById(R.id.closeDialogImg);
        TextView tvPublishPost = dialog.findViewById(R.id.txtPublishPost);
        final EditText etTitlePost = dialog.findViewById(R.id.etTitlePost);
        final TextView etDescriptionPost = dialog.findViewById(R.id.etDescriptionPost);


        tvPublishPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title= etTitlePost.getText().toString();
                String description = etDescriptionPost.getText().toString();


            }
        });
        ivCloseDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                //     revealShow(dialogView, true, null);
            }
        });


        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {

                    //      revealShow(dialogView, false, dialog);
                    return true;
                }

                return false;
            }
        });


        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog.show();


    }


    //   @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//   private void revealShow(View dialogView, boolean b, final Dialog dialog) {
//
//        final View view = getActivity().findViewById(R.id.dialog);
//
//        int w = view.getWidth();
//        int h = view.getHeight();
//
//        int endRadius = (int) Math.hypot(w, h);
//
//        int cx = (int) (fab.getX() + (fab.getWidth() / 2));
//        int cy = (int) (fab.getY()) + fab.getHeight() + 56;
//
//
//        if (b) {
//            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);
//
//            view.setVisibility(View.VISIBLE);
//            revealAnimator.setDuration(700);
//            revealAnimator.start();
//        } else {
//            Animator anim =
//                    ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);
//
//            anim.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    super.onAnimationEnd(animation);
//                    dialog.dismiss();
//                    view.setVisibility(View.INVISIBLE);
//
//                }
//            });
//            anim.setDuration(700);
//            anim.start();
//        }
//
//    }


}