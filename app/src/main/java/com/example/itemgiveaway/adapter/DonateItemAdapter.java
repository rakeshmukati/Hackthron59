package com.example.itemgiveaway.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itemgiveaway.model.Item;

import java.util.ArrayList;

public class DonateItemAdapter extends RecyclerView.Adapter<DonateItemAdapter.MyViewHolder> {

    private final ArrayList<Item> items = new ArrayList<>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.donate_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Item item = items.get(position);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void setItems(ArrayList<Item> items) {
        synchronized (this.items){
            this.items.clear();
            this.items.addAll(items);
            notifyDataSetChanged();
        }
    }
}
