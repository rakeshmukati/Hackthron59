package com.example.itemgiveaway.adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.controllers.CategoryController;
import com.example.itemgiveaway.model.Category;
import com.example.itemgiveaway.model.NeedyItem;
import com.example.itemgiveaway.utils.ImageUtils;

import java.util.ArrayList;

public class NeedyPersonAdapter extends RecyclerView.Adapter<NeedyPersonAdapter.MyViewHolder> {
    private  ArrayList<NeedyItem> items = new ArrayList<>();


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.needy_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final NeedyItem item = items.get(position);
        holder.itemName.setText(item.getName());
        holder.itemImage.setImageBitmap(new ImageUtils().stringToBitmap(item.getPicture()));
        CategoryController.getInstance().getCategories(new CategoryController.OnCategoriesListListener() {
            @Override
            public void onCategoriesListFetched(ArrayList<Category> categories) {
                for (Category category : categories) {
                    if (category.getId() == item.getCategoryId()) {
                        holder.itemcategory.setText(category.getName());
                    }
                }
            }
        });
        final String phone = item.getPhone();
        if (phone == null || phone.length() == 0) {
            holder.itemNumber.setVisibility(View.GONE);
            holder.btnCallNeedy.setVisibility(View.GONE);
        } else {
            holder.itemNumber.setVisibility(View.VISIBLE);
            holder.btnCallNeedy.setVisibility(View.VISIBLE);
            holder.itemNumber.setText(item.getPhone());
            holder.btnCallNeedy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                        view.getContext().startActivity(intent);
                    } else {
                        ActivityCompat.requestPermissions(((AppCompatActivity) view.getContext()), new String[]{
                                Manifest.permission.CALL_PHONE
                        }, 101);
                    }
                }
            });
        }
        String totalAddress = item.getAddress() + "," + item.getCity() + "," + item.getBlock() + " , " + item.getDistrict() + "," + item.getPostcode() + " , " + item.getState();
        holder.address.setText(totalAddress);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView itemImage, btnCallNeedy;
        AppCompatTextView itemName, itemNumber, itemcategory, address;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.needyProfilePicture);
            itemName = itemView.findViewById(R.id.needy_name);
            itemNumber = itemView.findViewById(R.id.needy_number);
            itemcategory = itemView.findViewById(R.id.needCategory);
            address = itemView.findViewById(R.id.address);
            btnCallNeedy = itemView.findViewById(R.id.btnCallNeedy);
        }
    }

    public void setItems(ArrayList<NeedyItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
