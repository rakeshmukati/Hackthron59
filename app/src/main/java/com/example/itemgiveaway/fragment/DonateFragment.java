package com.example.itemgiveaway.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.adapter.DonateItemAdapter;
import com.example.itemgiveaway.controllers.DonationItemController;
import com.example.itemgiveaway.model.Category;
import com.example.itemgiveaway.model.Item;
import com.example.itemgiveaway.utils.AuthenticationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DonateFragment extends Fragment implements DonationItemController.OnDonatedItemListPreparesListener {

    private DonationItemController controller = DonationItemController.getInstance();
    private DonateItemAdapter adapter;


    public DonateFragment() {
        // Required empty public constructor
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

        adapter = new DonateItemAdapter();
        RecyclerView donateList = view.findViewById(R.id.donateList);
        donateList.setLayoutManager(new LinearLayoutManager(requireContext()));
        donateList.setItemAnimator(new DefaultItemAnimator());
        donateList.setAdapter(adapter);
        controller.getDonatedItemList(this);

        //set add button
        view.findViewById(R.id.btnDonateItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemDialog();
            }
        });
    }

    private void addItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_donate_item,null,false);
        builder.setView(view);
        AlertDialog addDialog = builder.create();
        Objects.requireNonNull(addDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addDialog.show();

        final Item item = new Item();
        final AppCompatSpinner categoriesSpinner = view.findViewById(R.id.categoriesSpinner);
        final AppCompatEditText itemNameEdit = view.findViewById(R.id.name);
        AppCompatImageView itemImageView = view.findViewById(R.id.image);
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        final ArrayList<Category> categories = new ArrayList<>();
        controller.getCategories(new DonationItemController.OnCategoriesListListener() {
            @Override
            public void onCategoriesListFetched(ArrayList<Category> cat) {
                categories.clear();
                categories.addAll(cat);
                ArrayAdapter<Category> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories);
                categoriesSpinner.setAdapter(adapter);
            }
        });
        view.findViewById(R.id.btnDonateItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemName = itemNameEdit.getText().toString();
                if (itemName.isEmpty()){
                    itemNameEdit.setError("name require!");
                    return;
                }

                item.setItemName(itemName);
                controller.addItemForDonation(item);
            }
        });
    }

    @Override
    public void onItemListPrepared(ArrayList<Item> items) {
        adapter.setItems(items);
    }
}