package com.example.testtask;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ann Vicheva on 14.07.2020.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>  {

    private List<Profile> mData;
    private LayoutInflater mInflater;


    // data is passed into the constructor
    RecyclerViewAdapter(Context context, List<Profile> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }


    // inflates the row layout from xml when needed
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row
    public void onBindViewHolder(ViewHolder holder, int position) {
        Profile profile = mData.get(position);
        holder.tvName_item.setText( profile.getName());
        holder.tvAge_item.setText("age : "+Integer.toString(profile.getAge()));
        holder.tvPhone_item.setText("number phone : "+profile.getPhone_number());
        holder.tvSex_item.setText("sex : "+profile.getSex());
    }


    // total number of rows
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder //implements View.OnClickListener
    {
        TextView tvName_item;
        TextView tvAge_item;
        TextView tvPhone_item;
        TextView tvSex_item;

        ViewHolder(View itemView) {
            super(itemView);
            tvName_item=(TextView)itemView.findViewById(R.id.tvName_item);
            tvAge_item=(TextView)itemView.findViewById(R.id.tvAge_item);
            tvPhone_item=(TextView)itemView.findViewById(R.id.tvPhone_item);
            tvSex_item=(TextView)itemView.findViewById(R.id.tvSex_item);
        }


    }


    // convenience method for getting data
    Profile getItem(int id) {
        return mData.get(id);
    }


}
