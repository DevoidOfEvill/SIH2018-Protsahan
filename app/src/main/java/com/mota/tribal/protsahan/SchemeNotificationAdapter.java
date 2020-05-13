package com.mota.tribal.protsahan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mota.tribal.protsahan.Helper.NewsData;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SchemeNotificationAdapter extends RecyclerView.Adapter<SchemeNotificationAdapter.NotificationViewHolder> {


    private Context mContext;
    private ArrayList<NewsData> data;

    public SchemeNotificationAdapter(Context mContext, ArrayList<NewsData> data) {
        this.mContext = mContext;
        this.data = data;
    }

    public static void showProgressBar(ProgressBar progressBar) {
        if (progressBar.getVisibility() != View.VISIBLE)
            progressBar.setVisibility(View.VISIBLE);
    }

    public static void hideProgressBar(ProgressBar progressBar) {
        if (progressBar.getVisibility() != View.GONE
                || progressBar.getVisibility() != View.INVISIBLE)
            progressBar.setVisibility(View.GONE);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false);
        return new SchemeNotificationAdapter.NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationViewHolder holder, int position) {
        holder.title.setText(data.get(position).getTitle());
        holder.description.setText(data.get(position).getDescription());
        if (data.get(position).getImgurl().equals(""))
            showProgressBar(holder.progressBar);
        Picasso.with(mContext).load(data.get(position).getImgurl()).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                hideProgressBar(holder.progressBar);
            }

            @Override
            public void onError() {
                hideProgressBar(holder.progressBar);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView imageView;
        ProgressBar progressBar;

        public NotificationViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.imageView);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }
}
