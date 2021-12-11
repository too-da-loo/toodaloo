package com.example.toodaloo.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toodaloo.LoginActivity;
import com.example.toodaloo.Post;
import com.example.toodaloo.PostsAdapter;
import com.example.toodaloo.R;
import com.example.toodaloo.User;
import com.example.toodaloo.UserAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class ProfileFragment extends Fragment{
    public static final String TAG = "ProfileFragment";
    //FROM REVIEW FRAGMENT:
    private RecyclerView rvFeed;
    protected UserAdapter adapter;
    protected List<User> allUsers;
    protected PostsAdapter adapter1;
    protected List<Post> allPosts;

    //Profile Image:
    public ImageView profilePicture;
    private TextView profileUsername;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Profile Image:
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
      //  profilePicture = view.findViewById(R.id.profileImage);
        //profileUsername = view.findViewById(R.id.profileUsername);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //FROM REVIEW FRAGMENT
        rvFeed = view.findViewById(R.id.rvProfile);
        allPosts = new ArrayList<Post>();
        adapter1 = new PostsAdapter(getContext(), allPosts);

        allUsers = new ArrayList<User>();
        adapter = new UserAdapter(getContext(), allUsers);

        //TEST SETTING SEPARATE ADAPTER ***********************************************
        rvFeed.setAdapter(adapter1);
        rvFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        queryProfile();

//        //Profile Image:
        profilePicture = view.findViewById(R.id.profileImage);
        profileUsername = view.findViewById(R.id.profileUsername);

        //Actionbar:
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.logout_button_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout_action){
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            goLoginActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goLoginActivity() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
    }


//    @Override
    protected void queryProfile() {

        ParseQuery<User> query1 = ParseQuery.getQuery(User.class);
        query1.include(User.KEY_USERNAME);
        query1.whereEqualTo(User.KEY_USERNAME, ParseUser.getCurrentUser());
        query1.addDescendingOrder(User.KEY_CREATED_KEY);

        query1.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> objects, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Pictures: Parse exception null", e);
                    return;
                }
                //Log.i(TAG, "INDEX TEST: " + objects.get(0).getUser().getUsername());

                ImageView imgUser = (ImageView) getView().findViewById(R.id.profileImage);
                ParseUser currentUser = ParseUser.getCurrentUser();

                try {
                    ParseFile img = currentUser.getParseFile("profilePicture");
                    Bitmap bmp = BitmapFactory.decodeStream(img.getDataStream());
                    Log.i(TAG, "PICTURE BETTER WORK ");
                    imgUser.setImageBitmap(bmp);
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }

                /*
                for(User name : objects) {
                    Log.i(TAG, "Issue with getting PICTURES", e);
                    ParseFile image = name.getProfileImage();
                    Glide.with(getContext()).load(name.getProfileImage().getUrl()).into(profilePicture);
                }
                */

                allUsers.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        //ADD KEY:
        query.addDescendingOrder(User.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> users, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for(Post user : users){
                    Log.i(TAG, "Profile: " + user.getDescription() + ", Username: " + user.getUser().getUsername());
                    //Log.i(TAG, "INDEX TEST: " + users.get(0).getDescription());
                    profileUsername.setText(user.getUser().getUsername());
                }
                allPosts.addAll(users);
                adapter1.notifyDataSetChanged();
            }
        });
    }
}