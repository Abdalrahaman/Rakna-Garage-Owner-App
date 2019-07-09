package com.example.garage_owner.view.Islam;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.garage_owner.R;
import com.example.garage_owner.presenter.Islam.Data;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Profile_Adapter extends RecyclerView.Adapter<Profile_Adapter.ViewHolder> {
    private Context context;
    private List<Data> list;
    Dialog dialog;

    public Profile_Adapter(Context context, List<Data> list) {
        this.context = context;
        this.list = list;
        initDialog();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutinflter = LayoutInflater.from(parent.getContext()).inflate(R.layout.rakna_garage_list_item,null);
        ViewHolder viewHolder = new ViewHolder(layoutinflter);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Data data = list.get(position);

        if (data.getImgurl().equals("null")){
            Picasso.with(context)
                        .load(R.drawable.image_example)
                        .into(holder.imageView);
        }else {
            Picasso.with(context)
                    .load(data.getImgurl())
                    .into(holder.imageView);
        }

        holder.name.setText(" " + data.getName() );

        if (!data.getRate().equals("null")) {
            holder.ratingBar.setText(data.getRate());
        }else {
            holder.ratingBar.setText("5.0");
        }

        holder.phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, ""+data.getNumber(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + data.getNumber()));
                context.startActivity(intent);
            }
        });

        holder.chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StartActivityChat.class);
                context.startActivity(intent);
            }
        });

        holder.profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        ImageView backInageView = dialog.findViewById(R.id.back);
        backInageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    public void initDialog(){
        dialog = new Dialog(context, R.style.FullHeightDialog);
        dialog.setContentView(R.layout.car_owner_dialog_info);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

         ImageView imageView;
         TextView name,ratingBar;
         Button phoneButton, chatButton, profileButton;

         ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview_account_profile);
            name = itemView.findViewById(R.id.user_name);
            phoneButton = itemView.findViewById(R.id.phone);
            chatButton = itemView.findViewById(R.id.chat);
            profileButton = itemView.findViewById(R.id.profile);
//            number = itemView.findViewById(R.id.phone);
            ratingBar = itemView.findViewById(R.id.rate);
        }
    }
}
