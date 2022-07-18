package com.example.toodaloo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toodaloo.MarkerDetails;
import com.example.toodaloo.Post;
import com.example.toodaloo.PostsAdapter;
import com.example.toodaloo.R;
import com.example.toodaloo.User;
import com.example.toodaloo.UserAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class RestaurantFragment extends Fragment {
    private RecyclerView rvFeed;
    public static final String TAG = "RestaurantFragment";

    SwipeRefreshLayout swipeContainer;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;

    MarkerDetails markerDetails = new MarkerDetails();
    private TextView placeName;
    private TextView placeAddress;
    private RatingBar placeRating;

    public RestaurantFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                markerDetails = bundle.getParcelable("bundleKey");
                placeName.setText(markerDetails.getPlaceName());
                placeAddress.setText(markerDetails.getPlaceAddress());
                placeRating.setRating(Float.parseFloat(markerDetails.getPlaceRating()));

                // Go to RestaurantFragment upon Info Window click
                Fragment newFragment = new ComposeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.compose_button_feed, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.compose_action) {
            Bundle result = new Bundle();
            result.putParcelable("bundleKey2", (Parcelable) markerDetails);
            getParentFragmentManager().setFragmentResult("requestKey2", result);

            // Go to RestaurantFragment upon Info Window click
            Fragment newFragment = new ComposeFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.flContainer, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        placeName = view.findViewById(R.id.profileEstablishmentName);
        placeAddress = view.findViewById(R.id.restroomAddressInfo);
        placeRating = view.findViewById(R.id.ratingBar);
        rvFeed = view.findViewById(R.id.rvRestaurant);

        swipeContainer = view.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() { queryPosts(); }
        });

        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(getContext(), allPosts);
        rvFeed.setAdapter(adapter);
        rvFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPosts();
        setHasOptionsMenu(true);
    }

    protected void queryPosts(){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.include(Post.KEY_PLACE_NAME);

        //Constraint to only display posts of the selected location
        query.whereEqualTo(Post.KEY_PLACE_NAME, placeName.getText().toString());
        query.setLimit(20);

        //Filters the order of the posts based on the time created key (newest on top)
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for(Post post : posts){
                    Log.i(TAG, "Post: " + post.getPlaceName());
                }
                adapter.clear();
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }
        });
    }
}