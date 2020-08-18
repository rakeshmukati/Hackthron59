package com.example.itemgiveaway.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.model.Post;
import com.example.itemgiveaway.utils.ImageUtils;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    private final ArrayList<Post> posts = new ArrayList<>();
    private ImageUtils imageUtils = new ImageUtils();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Post post = posts.get(position);

        final String picture = post.getPicture();
        if (picture == null || picture.length() == 0) {
            holder.postPicture.setVisibility(View.GONE);
        } else {
            holder.postPicture.setImageBitmap(imageUtils.stringToBitmap(picture));
            holder.postPicture.setVisibility(View.VISIBLE);
        }

        final String text = post.getText();
        if (text == null || text.length() == 0) {
            holder.postText.setVisibility(View.GONE);
        } else {
            holder.postText.setText(text);
            holder.postText.setVisibility(View.VISIBLE);
        }

        String description = post.getDescription();
        if (picture == null || picture.length() == 0) {
            holder.layoutDescription.setVisibility(View.GONE);
        } else {
            holder.layoutDescription.setVisibility(View.VISIBLE);
            holder.postDescription.setText(description);
        }

        holder.postTitle.setText(post.getTitle());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView postPicture;
        AppCompatTextView postTitle, postText, postDescription;
        View layoutDescription;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            postPicture = itemView.findViewById(R.id.postPicture);
            postText = itemView.findViewById(R.id.postText);
            postTitle = itemView.findViewById(R.id.postTitle);
            postDescription = itemView.findViewById(R.id.postDescription);
            layoutDescription = itemView.findViewById(R.id.layoutDescription);
        }
    }

    public void setItems(ArrayList<Post> posts) {
        synchronized (this.posts) {
            this.posts.clear();
            this.posts.addAll(posts);
            notifyDataSetChanged();
        }
    }
}
