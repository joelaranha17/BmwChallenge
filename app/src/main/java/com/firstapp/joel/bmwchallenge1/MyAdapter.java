package com.firstapp.joel.bmwchallenge1;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by joel on 11/21/2017.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    Context context;
    List<Get_SetData> data= Collections.emptyList();
    Get_SetData get_setData;
    public MyAdapter(Context context, List<Get_SetData> mylist) {
        this.context=context;
        this.data=mylist;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myrecycler,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder holder, int position) {
        final Get_SetData get_setData = data.get(position);
        holder.location.setText(get_setData.getLatitude().toString());
        holder.address.setText(get_setData.getAddress());
        holder.my_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,MapDisplay.class);
                i.putExtra("latitude",get_setData.getLatitude());
                i.putExtra("longitude",get_setData.getLongitude());
                i.putExtra("address",get_setData.getAddress());
                i.putExtra("name",get_setData.getName());
                i.putExtra("arrival",get_setData.getArrival_time());
                Log.d("PRASAD ","NAME " +get_setData.getName() + " ARRIVAL " +get_setData.getArrival_time());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView location,address;
        public View my_view;
        public ViewHolder(View view) {
            super(view);

            my_view = view;
            location = (TextView)view.findViewById(R.id.location);
            address= (TextView) view.findViewById(R.id.Address);
         }
    }
}


