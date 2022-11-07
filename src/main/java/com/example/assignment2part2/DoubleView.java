package com.example.assignment2part2;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DoubleView extends Fragment {
    private class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView image;
        ImageView image2;
        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView2);
            image2 = itemView.findViewById(R.id.imageView3);

        }
    }
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.doublecolumn,parent,false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.image.setImageBitmap(DisplayUI.images.get(position+position));
            holder.image2.setImageBitmap(DisplayUI.images.get(position+position+1));
            Bitmap tempImage = DisplayUI.images.get(position+position);
            Bitmap tempImage2 = DisplayUI.images.get(position+position+1);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    DisplayUI.selectedImage = tempImage;
                }
            });
            holder.image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    DisplayUI.selectedImage = tempImage2;
                }
            });
        }

        @Override
        public int getItemCount() {
            return DisplayUI.images.size()/2;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.doublecolumn, container, false);
        RecyclerView rv = view.findViewById(R.id.DoubleViewRecycler);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        MyAdapter myAdapter = new MyAdapter();
        rv.setAdapter(myAdapter);
        return view;
    }
}