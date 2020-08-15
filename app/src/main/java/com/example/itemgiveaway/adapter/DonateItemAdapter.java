package com.example.itemgiveaway.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itemgiveaway.R;
import com.example.itemgiveaway.model.Item;
import com.example.itemgiveaway.utils.ImageUtils;

import java.util.ArrayList;

public class DonateItemAdapter extends RecyclerView.Adapter<DonateItemAdapter.MyViewHolder> {
    private final onDonatedItemSelectListener onDonatedItemSelectListener;
    private final ArrayList<Item> items = new ArrayList<>();

    public DonateItemAdapter(DonateItemAdapter.onDonatedItemSelectListener onDonatedItemSelectListener) {
        this.onDonatedItemSelectListener = onDonatedItemSelectListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.donate_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Item item = items.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemImage.setImageBitmap(new ImageUtils().stringToBitmap(item.getPicture()));
        holder.donateListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDonatedItemSelectListener.onItemSelected(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        AppCompatImageView itemImage;
        CardView donateListItem;
        AppCompatTextView itemName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.image);
            itemName = itemView.findViewById(R.id.name);
            donateListItem = itemView.findViewById(R.id.donateListItem);
        }
    }

    public void setItems(ArrayList<Item> items) {
        synchronized (this.items){
            this.items.clear();
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }

    public interface onDonatedItemSelectListener{
        void onItemSelected(Item item);
    }
}
