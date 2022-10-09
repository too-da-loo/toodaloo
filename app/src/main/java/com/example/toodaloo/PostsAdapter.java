package com.example.toodaloo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseObject;

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
        holder.bind(post);
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
        private Button btnSeeMore;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.username);
            tvPlaceName = itemView.findViewById(R.id.placeName);
            tvDescription = itemView.findViewById(R.id.reviewDescription);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivProfilePicture = itemView.findViewById(R.id.ivProfilePicture);
            btnSeeMore = itemView.findViewById(R.id.btnSeeMore);

            btnSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ivImage.setVisibility(View.VISIBLE);
                }
            });
        }

        public void bind(Post post) {
            //Create a User class to get relational data from Post object
            ParseObject user = post.getParseObject(Post.KEY_USER);
            ParseFile profileImage = user.getParseFile("profilePicture");
            if (profileImage != null) {
                Glide.with(context).load(profileImage.getUrl()).into(ivProfilePicture);
            }

            //Bind the post data to the view elements
            tvDescription.setText(post.getDescription());
            tvUsername.setText("@" + post.getUser().getUsername());
            tvPlaceName.setText(post.getPlaceName());
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
            }
        }
    }
}