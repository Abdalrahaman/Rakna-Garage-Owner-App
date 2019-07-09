package com.example.garage_owner.view.SaeedView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garage_owner.R;
import com.example.garage_owner.presenter.item2data;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    private List<item2data> list;

    public HistoryAdapter(Context context, List<item2data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutinflter = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_view_saeed,null);
        ViewHolder viewHolder = new ViewHolder(layoutinflter);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        item2data data = list.get(position);

        //loading image
        Picasso.with(context)
                .load(data.getImgurl())
                .placeholder(R.drawable.profile)
                .into(holder.imageView);

        holder.name.setText(data.getName());
        holder.number.setText(data.getNumber());
        holder.date.setText(data.getDate());
        holder.time.setText(data.getFrom_time()+" : "+data.getTo_time());
        holder.cost.setText(String.valueOf( data.getCost()));
        holder.rate.setText(String.valueOf(data.getRate()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
         ImageView imageView;
         TextView name,number,date,time,cost,rate;
         ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.customerpic);
            name = itemView.findViewById(R.id.customername);
            number = itemView.findViewById(R.id.customernumber);
            date = itemView.findViewById(R.id.customerdate);
            time = itemView.findViewById(R.id.customertime);
            cost = itemView.findViewById(R.id.customercost);
            rate = itemView.findViewById(R.id.ratingBar);
        }
    }
}
