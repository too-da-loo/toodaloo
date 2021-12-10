package com.example.toodaloo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private Context context;
    private List<User> users;

    public UserAdapter(Context context, List<User> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void clear() {
        users.clear();
        notifyDataSetChanged();

    }

    // Add a list of items -- change to type used
    public void addAll(List<User> list) {
        users.addAll(list);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView profileUsername;
        private ImageView ivImage;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileUsername = itemView.findViewById(R.id.profileUsername);
            ivImage = itemView.findViewById(R.id.profileImage);

        }


        public void bind(User users) {
            //Bind the post data to the view elements
            profileUsername.setText(users.getUser().getUsername());
            ParseFile image = users.getProfileImage();
            if (image != null) {
                Glide.with(context).load(users.getProfileImage().getUrl()).into(ivImage);
            }
        }
    }

}
