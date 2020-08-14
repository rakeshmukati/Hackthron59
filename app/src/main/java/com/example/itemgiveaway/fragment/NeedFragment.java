package com.example.itemgiveaway.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.adapter.NeedyPersonAdapter;
import com.example.itemgiveaway.controllers.NeedyController;
import com.example.itemgiveaway.controllers.DonationItemController;
import com.example.itemgiveaway.model.NeedyItem;
import com.example.itemgiveaway.model.Category;
import com.example.itemgiveaway.utils.ImageUtils;

import java.util.ArrayList;
import java.util.Objects;

public class NeedFragment extends Fragment implements NeedyController.OnNeedyPersonListPreparesListener {

    private NeedyController controller = NeedyController.getInstance();
    private NeedyPersonAdapter adapter;
    private AppCompatImageView itemImage = null;
    private boolean itemPicAdded = false;

    public NeedFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_need, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new NeedyPersonAdapter();
        RecyclerView needyList = view.findViewById(R.id.needyList);
        needyList.setLayoutManager(new LinearLayoutManager(requireContext()));
        needyList.setItemAnimator(new DefaultItemAnimator());
        needyList.setAdapter(adapter);
        controller.getNeedyPersonList(this);

        //set add button
        view.findViewById(R.id.btnAddNeedy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemDialog();
            }
        });
    }
    private void addItemDialog() {
        itemPicAdded = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_needy, null, false);
        builder.setView(view);
        final AlertDialog addDialog = builder.create();
        Objects.requireNonNull(addDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        addDialog.show();

        final NeedyItem item = new NeedyItem();
        final AppCompatSpinner categoriesSpinner = view.findViewById(R.id.categoriesSpinner);
        final EditText itemNameEdit = view.findViewById(R.id.name);
        itemImage = view.findViewById(R.id.image);
        final EditText itemNumberEdit=view.findViewById(R.id.number);
        final EditText itemAddressEdit=view.findViewById(R.id.Address);
        final EditText itemcityEdit=view.findViewById(R.id.city);
        final EditText itemdistrictEdit=view.findViewById(R.id.district);
        final EditText itemblockEdit=view.findViewById(R.id.block);
        final EditText itemstateEdit=view.findViewById(R.id.state);
        final EditText itempincodeEdit=view.findViewById(R.id.pincode);
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
        controller.getCategories(new NeedyController.OnCategoriesListListener() {
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
        view.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("", "==============================>" + ci[0]);
                String itemName = itemNameEdit.getText().toString();
                String number=itemNumberEdit.getText().toString();
               String address=itemAddressEdit.getText().toString();
                String city=itemcityEdit.getText().toString();
                String District=itemdistrictEdit.getText().toString();
                String block=itemblockEdit.getText().toString();
                String pincode=itempincodeEdit.getText().toString();
                String state=itemstateEdit.getText().toString();

                if (itemName.isEmpty()) {
                    itemNameEdit.setError("require!");
                    return;
                }
                else if (address.isEmpty()) {
                    itemAddressEdit.setError("require!");
                    return;
                }
                else if (city.isEmpty()) {
                    itemcityEdit.setError("require!");
                    return;
                }
                else if (District.isEmpty()) {
                    itemdistrictEdit.setError("require!");
                    return;
                }else if (block.isEmpty()) {
                    itemblockEdit.setError("require!");
                    return;
                }else if (pincode.isEmpty()) {
                    itempincodeEdit.setError("require!");
                    return;
                }else if (state.isEmpty()) {
                    itemstateEdit.setError("require!");
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
                item.setName(itemName);
                item.setPhone(number);
                item.setAddress(address);
                item.setCity(city);
                item.setDistrict(District);
                item.setBlock(block);
                item.setPostcode(pincode);
                item.setState(state);
                item.setPicture(new ImageUtils().viewToString(itemImage));
                controller.addNeedyPerson (item);
                Log.d("","=================pic "+item.getPicture());
                addDialog.cancel();
            }
        });
    }
    @Override
    public void onNeedyItemListPrepared(ArrayList<NeedyItem> items) {
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