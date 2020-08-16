package com.example.itemgiveaway.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.model.NeedyItem;
import com.example.itemgiveaway.utils.ImageUtils;

import java.util.ArrayList;

public class NeedyPersonAdapter extends RecyclerView.Adapter<NeedyPersonAdapter.MyViewHolder>  {
    private final ArrayList<NeedyItem> items = new ArrayList<>();


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.needy_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final NeedyItem item = items.get(position);
        holder.itemName.setText(item.getName());
        holder.itemImage.setImageBitmap(new ImageUtils().stringToBitmap(item.getPicture()));
        holder.itemcategory.setText(item.getCategoryId()+"");
        holder.itemNumber.setText(item.getPhone());
        String totalAddress=item.getAddress()+"\n"+item.getCity()+"\n"+item.getBlock()+" , "+item.getDistrict()+"\n"+item.getPostcode()+" , "+item.getState();

        holder.address.setText(totalAddress);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView itemImage;
        AppCompatTextView itemName,itemNumber,itemcategory,address;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.needyProfilePicture);
            itemName = itemView.findViewById(R.id.needy_name);
            itemNumber =itemView.findViewById(R.id.needy_number);
            itemcategory=itemView.findViewById(R.id.needCategory);
            address=itemView.findViewById(R.id.address);
        }
    }

    public void setItems(ArrayList<NeedyItem> items) {
        synchronized (this.items){
            this.items.clear();
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }
}
