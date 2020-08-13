package com.example.itemgiveaway.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.controllers.DonationItemController;
import com.example.itemgiveaway.model.Category;

import java.util.ArrayList;

public class DonateFragment extends Fragment {

    DonationItemController controller = DonationItemController.getInstance();

    public DonateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller.getCategories(new DonationItemController.OnCategoriesListListener() {
            @Override
            public void onCategoriesListFetched(ArrayList<Category> categories) {
                Log.d("List", "================>" + categories);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donate, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView donateList = view.findViewById(R.id.donateList);
        donateList.setLayoutManager(new LinearLayoutManager(requireContext()));


    }
}