package com.xparticle.drecipeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewAdapter>{

    private Context mContext;
    private ArrayList<Category> mExampleList;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public ExampleAdapter(Context context, ArrayList<Category> arrayList){
        mContext = context;
        mExampleList = arrayList;
    }

    @NonNull
    @Override
    public ExampleViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recipe_category,parent,false);
        return new ExampleViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewAdapter holder, int position) {
        Category currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getmImageUrl();
        String categoryName = currentItem.getmCategoryName();

        holder.mTextView.setText(categoryName);
        Picasso.get().load(imageUrl).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    class ExampleViewAdapter extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView;
        public ExampleViewAdapter(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.cImageView);
            mTextView = itemView.findViewById(R.id.cTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
