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
    private ArrayList<Post> posts = new ArrayList<>();
    private ImageUtils imageUtils = new ImageUtils();
    private final OnPostAdapterListener onPostAdapterListener;

    public PostAdapter(OnPostAdapterListener onPostAdapterListener) {
        this.onPostAdapterListener = onPostAdapterListener;
    }

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
        if (description == null || description.length() == 0) {
            holder.layoutDescription.setVisibility(View.GONE);
        } else {
            holder.layoutDescription.setVisibility(View.VISIBLE);
            holder.postDescription.setText(description);
        }

        holder.postTitle.setText(post.getTitle());
        holder.postListItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onPostAdapterListener.onLongPostSelect(post);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public interface OnPostAdapterListener{
        void onLongPostSelect(Post post);
    }

    public void setItems(ArrayList<Post> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView postPicture;
        AppCompatTextView postTitle, postText, postDescription;
        View layoutDescription;
        View postListItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            postListItem = itemView.findViewById(R.id.postListItem);
            postPicture = itemView.findViewById(R.id.postPicture);
            postText = itemView.findViewById(R.id.postText);
            postTitle = itemView.findViewById(R.id.postTitle);
            postDescription = itemView.findViewById(R.id.postDescription);
            layoutDescription = itemView.findViewById(R.id.layoutDescription);
        }
    }
}
