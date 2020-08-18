package com.example.itemgiveaway.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.itemgiveaway.App;
import com.example.itemgiveaway.R;
import com.example.itemgiveaway.adapter.PostAdapter;
import com.example.itemgiveaway.controllers.PostController;
import com.example.itemgiveaway.interfaces.OnFailedListener;
import com.example.itemgiveaway.interfaces.OnSuccessListener;
import com.example.itemgiveaway.model.Post;
import com.example.itemgiveaway.model.User;
import com.example.itemgiveaway.utils.AuthenticationManager;
import com.example.itemgiveaway.utils.ImageUtils;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Objects;

public class ChatFragment extends Fragment implements OnSuccessListener<ArrayList<Post>>, PostAdapter.OnPostAdapterListener {
    private AppCompatImageView dialogImage = null;
    private PostAdapter adapter;
    private PostController controller = PostController.getInstance();
    String value = null;
    private View progressBar;
    SwipeRefreshLayout swipeRefreshLayout;

    public ChatFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);
        RecyclerView postRecyclerView = view.findViewById(R.id.postRecyclerView);
        adapter = new PostAdapter(this);
        postRecyclerView.setItemAnimator(new DefaultItemAnimator());
        postRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        postRecyclerView.setAdapter(adapter);

        swipeRefreshLayout = view.findViewById(R.id.refresh);

        controller.getPost(this, true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                controller.getPost(ChatFragment.this, false);
                // swipeRefreshLayout.setRefreshing(false);
            }

        });
        view.findViewById(R.id.btnPostDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPostDialog();
            }
        });
    }

    public void addPostDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_post, null, false);
        builder.setView(view);
        final AlertDialog addDialog = builder.create();
        Objects.requireNonNull(addDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addDialog.show();

        final Post post = new Post();
        final RadioGroup radioGroup;
        final AppCompatEditText dialogText = view.findViewById(R.id.dialog_text);
        final AppCompatEditText dialogTitle = view.findViewById(R.id.name);
        final AppCompatEditText dialogDescrip = view.findViewById(R.id.description);
        final View progressBar = view.findViewById(R.id.progressBar);

        radioGroup = view.findViewById(R.id.radio_group);
        dialogImage = view.findViewById(R.id.dialog_image);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                value = (i == R.id.radio_text ? "TEXT" : "IMAGE");
                if (value.equals("TEXT")) {
                    dialogText.setVisibility(View.VISIBLE);
                    dialogImage.setVisibility(View.GONE);
                } else {
                    dialogText.setVisibility(View.GONE);
                    dialogImage.setVisibility(View.VISIBLE);
                }
            }
        });
        dialogImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });


        view.findViewById(R.id.btnPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = dialogTitle.getText().toString();
                String description = dialogDescrip.getText().toString();
                String text = dialogText.getText().toString();

                if (title.isEmpty()) {
                    dialogTitle.setError("name require!");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                if (value.equals("IMAGE")) {
                    post.setPicture(new ImageUtils().viewToString(dialogImage));
                } else {
                    post.setText(text);
                }
                Location location = App.locationService.getLocation();
                if (location!=null){
                    post.setLatLng(new LatLng(location.getLatitude(),location.getLongitude()));
                }else{
                    post.setLatLng(new LatLng(0,0));
                }
                post.setTitle(title);
                post.setDescription(description);
                controller.addItemPost(post, new OnSuccessListener<Post>() {
                    @Override
                    public void onSuccess(Post post) {
                        addDialog.cancel();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(requireContext(), "Post Success", Toast.LENGTH_SHORT).show();
                    }
                }, new OnFailedListener<String>() {
                    @Override
                    public void onFailed(String s) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(requireContext(), "failed to post", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        AuthenticationManager.getInstance().getCurrentUser(new AuthenticationManager.OnUserCallbackListener() {
            @Override
            public void onUserDetailReceived(User currentUser) {
                post.setEmail(currentUser.getEmail());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri && dialogImage != null) {
                    dialogImage.setImageURI(selectedImageUri);
                    dialogImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
        }
    }

    @Override
    public void onSuccess(final ArrayList<Post> posts) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setItems(posts);
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onLongPostSelect(final Post post) {
        AuthenticationManager.getInstance().getCurrentUser(new AuthenticationManager.OnUserCallbackListener() {
            @Override
            public void onUserDetailReceived(User currentUser) {
                if (currentUser.getEmail().equals(post.getEmail())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setMessage("Delete item");
                    builder.setMessage("Are you sure to remove this item from donation.");
                    builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            controller.deletePost(post, new OnSuccessListener<Post>() {
                                @Override
                                public void onSuccess(Post post) {
                                    Toast.makeText(requireContext(), "Post deleted successfully", Toast.LENGTH_SHORT).show();
                                    adapter.notifyDataSetChanged();
                                }
                            }, new OnFailedListener<String>() {
                                @Override
                                public void onFailed(String s) {
                                    Toast.makeText(requireContext(), "Failed to delete post.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                    builder.setNegativeButton("Cancel", null);
                    builder.create().show();
                } else {
                    Toast.makeText(requireContext(), "you can't delete this post", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}