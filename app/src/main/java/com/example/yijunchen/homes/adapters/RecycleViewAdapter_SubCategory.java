package com.example.yijunchen.homes.adapters;


import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yijunchen.homes.R;
import com.example.yijunchen.homes.models.Property;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by yijunchen on 7/3/17.
 */

public class RecycleViewAdapter_SubCategory extends RecyclerView.Adapter<RecycleViewAdapter_SubCategory.ViewHolder> implements View.OnClickListener {
    Context context;
    List<Property> propertyList;

    public RecycleViewAdapter_SubCategory(List<Property> propertyList, Context context) {
        super();
        this.propertyList = propertyList;
        this.context = context;
    }

    //define interface
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecycleViewAdapter_SubCategory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_detail, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        v.setOnClickListener(this);//add this line to enable onclick
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleViewAdapter_SubCategory.ViewHolder holder, int position) {
        Property property = propertyList.get(position);
        holder.propertyPrice.setText("$" + property.getCost());
        holder.propertyName.setText(property.getName());
        Log.d("property img", property.getImgThumb2());
        Picasso.with(context).load(property.getImgThumb2()).into(holder.propertyImg);
    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, String.valueOf(v.getTag()));
        } else {
            Log.e("CLICK", "ERROR");
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView propertyPrice;
        public TextView propertyName;
        public ImageView propertyImg;

        public ViewHolder(View itemView) {
            super(itemView);
            propertyImg = (ImageView) itemView.findViewById(R.id.property_list_img);
            propertyPrice = (TextView) itemView.findViewById(R.id.property_list_price);
            propertyName = (TextView) itemView.findViewById(R.id.property_list_name);
        }
    }
}
