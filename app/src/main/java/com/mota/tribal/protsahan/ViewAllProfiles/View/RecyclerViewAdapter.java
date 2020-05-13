package com.mota.tribal.protsahan.ViewAllProfiles.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mota.tribal.protsahan.Helper.Urls;
import com.mota.tribal.protsahan.Profile.Model.Data.VidImgDocData;
import com.mota.tribal.protsahan.Profile.View.GalleryActivity;
import com.mota.tribal.protsahan.R;
import com.mota.tribal.protsahan.ViewAllProfiles.Model.Data.ViewProfilesData;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ViewProfilesData.Docs> docs;
    private ArrayList<VidImgDocData.Obj> objects;
    private ArrayList<String> usernames, profile_pic_urls, names;
    private String type;
    private int length = 0;

    public RecyclerViewAdapter(Context context, ArrayList<ViewProfilesData.Docs> docs, String type) {
        this.context = context;
        this.docs = docs;
        this.type = type;

        objects = new ArrayList<>();
        names = new ArrayList<>();
        profile_pic_urls = new ArrayList<>();
        usernames = new ArrayList<>();

        if (type.equals("image"))
            for (int i = 0; i < docs.size(); i++) {
                for (int j = 0; j < docs.get(i).getImages().size(); j++) {
                    try {
                        if (docs.get(i).getImages().get(j).isStatus()) {
                            length += 1;
                            objects.add(docs.get(i).getImages().get(j));
                            usernames.add(docs.get(i).getUsername());
                            profile_pic_urls.add(docs.get(i).getProfilePicUrl());
                            names.add(docs.get(i).getName());
                        }
                    } catch (NullPointerException e) {
                        Log.d("abhi", e.getMessage() + "");
                    }
                }
            }
        if (type.equals("video"))
            for (int i = 0; i < docs.size(); i++) {
                for (int j = 0; j < docs.get(i).getVideos().size(); j++) {
                    try {
                        if (docs.get(i).getVideos().get(j).isStatus()) {
                            length += 1;
                            objects.add(docs.get(i).getVideos().get(j));
                            profile_pic_urls.add(docs.get(i).getProfilePicUrl());
                            names.add(docs.get(i).getName());
                            usernames.add(docs.get(i).getUsername());
                        }
                    } catch (NullPointerException e) {
                        Log.d("abhi", e.getMessage());
                    }
                }
            }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_image_item, parent, false);
        return new RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        if (type.equals("image"))
            Picasso.with(context).load(Urls.BASE_URL2 + objects.get(position).
                    getUrl().substring(6)).into(holder.image, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                }
            });
        else if (type.equals("video"))
            Picasso.with(context).load(Urls.BASE_URL2 + objects.get(position).getUrl().substring(6) + ".png").
                    into(holder.image, new Callback() {
                        @Override
                        public void onSuccess() {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {

                        }
                    });
        holder.title.setText(objects.get(position).getTitle());
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, Urls.BASE_URL2 + objects.get(position).getUrl().substring(6));
                sendIntent.setType("text/plain");
                Intent.createChooser(sendIntent, "Share via");
                context.startActivity(sendIntent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image, play_button, share;
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
            play_button = itemView.findViewById(R.id.play_button);
            progressBar = itemView.findViewById(R.id.image_progress_bar);
            share = itemView.findViewById(R.id.share);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = (int) (displayMetrics.widthPixels / 2.5);
            image.getLayoutParams().width = width;
            image.getLayoutParams().height = width;
            image.requestLayout();


            if (type.equals("video"))
                play_button.setVisibility(View.VISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    ArrayList<VidImgDocData.Obj> img = new ArrayList<VidImgDocData.Obj>();
                    img.add(objects.get(position));
                    Intent intent = new Intent(context, GalleryActivity.class);
                    intent.putExtra("tribal_name", names.get(position));
                    intent.putExtra("profile_pic_url", profile_pic_urls.get(position));
                    intent.putExtra("type", type);
                    intent.putExtra("objects", img);
                    intent.putExtra("count_type", "single" + usernames.get(position));
                    context.startActivity(intent);
                }
            });
        }

    }
}