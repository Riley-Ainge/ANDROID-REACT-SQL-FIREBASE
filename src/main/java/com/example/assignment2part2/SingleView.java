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

public class SingleView extends Fragment {
    private class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView1);

        }
    }
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.singlecolumn,parent,false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.image.setImageBitmap(DisplayUI.images.get(position));
            Bitmap tempImage = DisplayUI.images.get(position);
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    DisplayUI.selectedImage = tempImage;
                }
            });
        }

        @Override
        public int getItemCount() {
            return DisplayUI.images.size();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.singlecolumn, container, false);
        RecyclerView rv = view.findViewById(R.id.SingleViewRecycler);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        MyAdapter myAdapter = new MyAdapter();
        rv.setAdapter(myAdapter);
        return view;
    }
}