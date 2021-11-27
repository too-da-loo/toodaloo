package com.example.toodaloo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private final Context context;
    private final List<Review> reviews;

    public ReviewsAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView username;
        //private TextView establishmentName;
        private final TextView reviewDescription;
        //private RatingBar reviewRating;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            //establishmentName = itemView.findViewById(R.id.establishmentName);
            reviewDescription = itemView.findViewById(R.id.reviewDescription);
            //reviewRating = itemView.findViewById(R.id.reviewRating);
        }

        public void bind(Review review) {
            reviewDescription.setText(review.getReview());
            username.setText(review.getUser().getUsername());
        }
    }
    public void clear() {
        reviews.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Review> list) {
        reviews.addAll(list);
        notifyDataSetChanged();
    }
}
