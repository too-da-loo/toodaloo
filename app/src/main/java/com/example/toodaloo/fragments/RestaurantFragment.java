package com.example.toodaloo.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.toodaloo.MarkerDetails;
import com.example.toodaloo.Post;
import com.example.toodaloo.PostsAdapter;
import com.example.toodaloo.R;
import com.example.toodaloo.User;
import com.example.toodaloo.UserAdapter;

import java.util.ArrayList;

public class RestaurantFragment extends Fragment {

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        placeName = view.findViewById(R.id.profileEstablishmentName);
        placeAddress = view.findViewById(R.id.restroomAddressInfo);
        placeRating = view.findViewById(R.id.ratingBar);
    }

}