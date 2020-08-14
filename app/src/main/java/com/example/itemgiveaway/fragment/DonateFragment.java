package com.example.itemgiveaway.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.adapter.DonateItemAdapter;
import com.example.itemgiveaway.controllers.DonationItemController;
import com.example.itemgiveaway.interfaces.OnFailedListener;
import com.example.itemgiveaway.interfaces.OnSuccessListener;
import com.example.itemgiveaway.model.Category;
import com.example.itemgiveaway.model.Item;
import com.example.itemgiveaway.utils.ImageUtils;

import java.util.ArrayList;
import java.util.Objects;

public class DonateFragment extends Fragment implements DonationItemController.OnDonatedItemListPreparesListener {

    private DonationItemController controller = DonationItemController.getInstance();
    private DonateItemAdapter adapter;
    private AppCompatImageView itemImage = null;
    private boolean itemPicAdded = false;

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
        itemPicAdded = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_donate_item, null, false);
        builder.setView(view);
        final AlertDialog addDialog = builder.create();
        Objects.requireNonNull(addDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addDialog.show();

        final Item item = new Item();
        final AppCompatSpinner categoriesSpinner = view.findViewById(R.id.categoriesSpinner);
        final AppCompatEditText itemNameEdit = view.findViewById(R.id.name);
        itemImage = view.findViewById(R.id.image);
        itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
        final ArrayList<Category> categories = new ArrayList<>();
        final int[] ci = {0};
        controller.getCategories(new DonationItemController.OnCategoriesListListener() {
            @Override
            public void onCategoriesListFetched(ArrayList<Category> cat) {
                categories.clear();
                categories.add(new Category("Select category", -1));
                categories.addAll(cat);
                ArrayAdapter<Category> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories);
                categoriesSpinner.setAdapter(adapter);
            }
        });
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                item.setCategoryId(categories.get(i).getId());
                ci[0] = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        view.findViewById(R.id.btnDonateItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "==============================>" + ci[0]);
                String itemName = itemNameEdit.getText().toString();
                if (itemName.isEmpty()) {
                    itemNameEdit.setError("name require!");
                    return;
                }
                if (ci[0] == 0) {
                    Toast.makeText(requireContext(), "Please choose category", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!itemPicAdded){
                    Toast.makeText(requireContext(), "Please choose item pic", Toast.LENGTH_SHORT).show();
                    return;
                }
                item.setItemName(itemName);
                item.setPicture(new ImageUtils().viewToString(itemImage));
                controller.addItemForDonation(item, new OnSuccessListener<Item>() {
                    @Override
                    public void onSuccess(Item item) {
                        adapter.notifyDataSetChanged();
                        addDialog.cancel();
                        Toast.makeText(requireContext(),"Item added",Toast.LENGTH_SHORT).show();
                    }
                }, new OnFailedListener<String>() {
                    @Override
                    public void onFailed(String s) {
                        Toast.makeText(requireContext(),"failed to add item for donation",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onItemListPrepared(ArrayList<Item> items) {
        adapter.setItems(items);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri && itemImage != null) {
                    itemImage.setImageURI(selectedImageUri);
                    itemImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    itemPicAdded = true;
                }
            }
        }
    }
}