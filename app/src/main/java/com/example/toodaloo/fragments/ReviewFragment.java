package com.example.toodaloo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.toodaloo.Post;
import com.example.toodaloo.PostsAdapter;
import com.example.toodaloo.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {
    private RecyclerView rvFeed;
    public static final String TAG = "ReviewFragment";

    SwipeRefreshLayout swipeContainer;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;

    public ReviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }
/*
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu,@NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.compose_button_feed, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.compose_action) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            ComposeFragment composeFragment = new ComposeFragment();
            transaction.replace(R.id.flContainer, composeFragment);
            transaction.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFeed = view.findViewById(R.id.rvFeed);
        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts();
            }
        });


        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);
        rvFeed.setAdapter(adapter);
        rvFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();

        //Actionbar:
        //setHasOptionsMenu(true);
    }

    protected void queryPosts(){

        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        //ADD KEY:
        query.addDescendingOrder(Post.KEY_CREATED_KEY);

        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for(Post post : posts){
                    Log.i(TAG, "Post: " + post.getDescription() + ", Username: " + post.getUser().getUsername());
                }
                adapter.clear();
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        });
/*
        ParseQuery<Post> query2 = ParseQuery.getQuery(Post.class);
        query2.include(Post.KEY_RESTNAME);
        query2.setLimit(20);
        query2.addDescendingOrder(Post.KEY_CREATED_KEY);

        query2.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> restaurant, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting restaurants", e);
                    return;
                }

                TextView t = (TextView) getView().findViewById(R.id.restaurantName);

                for(Post post : restaurant){
                    ParseObject restName = post.getParseObject("restaurantName");
                    String name;

                    if(restName != null) {
                        name = restName.getString("name");
                        t.setText(name);
                        Log.i(TAG, "Restaurant!!!!!!!" + name);
                    }

                }
            }
        });
        */
    }
}