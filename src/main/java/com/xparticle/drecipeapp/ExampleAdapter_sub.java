package com.xparticle.drecipeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ExampleAdapter_sub extends RecyclerView.Adapter<ExampleAdapter_sub.ExampleViewAdapter>{

    private Context context;
    private ArrayList<Category> mExampleList;
    private onItemClickListener mListener;


    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener = listener;
    }

    public ExampleAdapter_sub(Context context, ArrayList<Category> mExampleList) {
        this.context = context;
        this.mExampleList = mExampleList;
    }

    @NonNull
    @Override
    public ExampleViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recipe_sub_category,parent,false);
        return new ExampleViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewAdapter holder, int position) {
        Category currentItem = mExampleList.get(position);

        String imageUrl = currentItem.getmImageUrl();
        String categoryName = currentItem.getmCategoryName();

        holder.textView.setText(categoryName);
        Picasso.get().load(imageUrl).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    class ExampleViewAdapter extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;
        public ExampleViewAdapter(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.subCatImageView);
            textView = itemView.findViewById(R.id.subCatTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( mListener != null){
                        int position = getAdapterPosition();
                        if(position!= RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
