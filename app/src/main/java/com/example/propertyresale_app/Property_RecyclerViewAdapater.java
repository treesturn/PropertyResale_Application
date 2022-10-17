package com.example.propertyresale_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Property_RecyclerViewAdapater extends RecyclerView.Adapter<Property_RecyclerViewAdapater.MyViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private ArrayList SN, town, price;

    public Property_RecyclerViewAdapater(Context context, ArrayList SN, ArrayList town, ArrayList price, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.SN = SN;
        this.town = town;
        this.price = price;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public Property_RecyclerViewAdapater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is where you inflate the layout(Giving a look to our rows)

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);

        return new Property_RecyclerViewAdapater.MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull Property_RecyclerViewAdapater.MyViewHolder holder, int position) {
        //assigning values to the views we created in the recycler_view_row layout file
        //based on the position of the recycler view

        holder.SN.setText(String.valueOf(SN.get(position)));
        holder.town.setText(String.valueOf(town.get(position)));
        holder.price.setText(String.valueOf(price.get(position)));
    }

    @Override
    public int getItemCount() {
        //the recycler view wants to know the number of items to display
        return SN.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

//        ImageView imageView;
        TextView SN, town, price;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            SN = itemView.findViewById(R.id.SN);
            town = itemView.findViewById(R.id.town);
            price = itemView.findViewById(R.id.price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

}
