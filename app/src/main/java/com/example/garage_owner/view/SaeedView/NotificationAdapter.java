package com.example.garage_owner.view.SaeedView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garage_owner.R;
import com.example.garage_owner.presenter.notifyItems;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private Context mCtx;
    private List<notifyItems> list;

    public NotificationAdapter(Context mCtx, List<notifyItems> list) {
        this.mCtx = mCtx;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutinflter = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_list_item, null);
        ViewHolder viewHolder = new ViewHolder(layoutinflter);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        notifyItems data = list.get(position);
        //loading image
        Picasso.with(mCtx)
                .load(data.getCircleImgURL())
                .placeholder(R.drawable.profile)
                .into(holder.circleImageView);

        Picasso.with(mCtx)
                .load(data.getStatusImgURL())
                .placeholder(R.drawable.camera_warning)
                .into(holder.imageViewstat);

        holder.message.setText(data.getNotiMssg());

        holder.period.setText(data.getNotiPeriod());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewstat;
        TextView message, period;
        CircleImageView circleImageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageViewstat = itemView.findViewById(R.id.imv_noti_color);
            circleImageView = itemView.findViewById(R.id.profile_image);
            message = itemView.findViewById(R.id.tv_noti_msg);
            period = itemView.findViewById(R.id.tv_noti_period);

        }
    }
}
