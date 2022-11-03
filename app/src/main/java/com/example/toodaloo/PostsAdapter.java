package com.example.toodaloo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        //Added extra position parameter for bind method in order to delete item post from the list(position)
        holder.bind(post,position);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void clear() {
        posts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvUsername;
        private TextView tvDescription;
        private TextView tvPlaceName;
        private ImageView ivImage;
        private ImageView ivProfilePicture;
        private ToggleButton btnSeeMore;
        private ImageButton btnDelete;
        private TextView tvDate;
        private RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.username);
            tvPlaceName = itemView.findViewById(R.id.placeName);
            tvDescription = itemView.findViewById(R.id.reviewDescription);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivProfilePicture = itemView.findViewById(R.id.ivProfilePicture);
            btnSeeMore = itemView.findViewById(R.id.btnSeeMore);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tvDate = itemView.findViewById(R.id.tvDate);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

        public void bind(Post post, int position) {
            //Create a User class to get relational data from Post object for the profile picture
            //ParseObject user = post.getParseObject(Post.KEY_USER);
            ParseObject user = post.getParseUser(Post.KEY_USER);
            ParseFile profileImage = user.getParseFile("profilePicture");
            if (profileImage != null) {
                Glide.with(context).load(profileImage.getUrl()).into(ivProfilePicture);
            }

            //Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText("@" + post.getUser().getUsername());
            tvPlaceName.setText(post.getPlaceName());
            ratingBar.setRating(post.getRating());

            //getCreatedAt() is a Parse function to get the creation date of the post
            //We use the Java SimpleDateFormat class to format the date to our preference
            Date date = post.getCreatedAt();
            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
            tvDate.setText(formatter.format(date));


            /*
            If the current user doesn't match the user of the Post, set the delete button to GONE
            We get a reference to an owner of a post by calling post.getParseObject(Post.KEY_USER) above,
            and then calling the getObjectId method to its user reference variable
            We then get the object Id of the current user logined by calling arseUser.getCurrentUser().getObjectId() below
            */
            if (!user.getObjectId().equals(ParseUser.getCurrentUser().getObjectId() )){
                btnDelete.setVisibility(View.GONE);
                Log.e("PostAdapter", "Post " + user.getObjectId() + " = " + ParseUser.getCurrentUser().getObjectId());
            }


            //Get image of the post
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }

            //OnClickListener to show/hide the photo associated with a post
            btnSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(btnSeeMore.isChecked()){
                        ivImage.setVisibility(View.VISIBLE);
                    }
                    else{
                        ivImage.setVisibility(View.GONE);
                    }
                }
            });

            //onClickListener for deleting a post
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(btnDelete.getContext(), "Deleted", Toast.LENGTH_SHORT).show();

                    //posts is the list of Post objects. We use the position from the onBindViewholder parameter to delete
                    //the post at specified position from the recyclerView
                    posts.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, posts.size());

                    //Query to delete the Post from the database
                    ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

                    //Constraint to only display posts of the selected objectID
                    query.whereEqualTo(Post.KEY_OBJECT_ID, post.getObjectId());
                    query.getFirstInBackground(new GetCallback<Post>() {
                        @Override
                        public void done(Post object, ParseException e) {
                            try {
                                object.delete();
                                object.saveInBackground();
                            } catch (ParseException parseException) {
                                parseException.printStackTrace();
                            }
                        }
                    });
                }
            });
        }
    }
}