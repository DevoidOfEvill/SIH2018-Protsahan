package com.mota.tribal.protsahan.Profile.View;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mota.tribal.protsahan.Helper.Urls;
import com.mota.tribal.protsahan.Login.Model.Data.UserInfo;
import com.mota.tribal.protsahan.Login.SQLiteHandler;
import com.mota.tribal.protsahan.Profile.Model.Data.VidImgDocData;
import com.mota.tribal.protsahan.Profile.Presenter.ProfilePresenter;
import com.mota.tribal.protsahan.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.MyViewHolder> {
    private ArrayList<VidImgDocData.Obj> urls;
    private String profile_pic_url, tribal_name, type, count_type;
    private Context context;
    private ProfilePresenter presenter;
    private SQLiteHandler db;
    private UserInfo user;

    public GalleryAdapter(ArrayList<VidImgDocData.Obj> urls,
                          String tribal_name, String profile_pic_url, String type, String count_type, Context context, ProfilePresenter presenter) {
        this.urls = urls;
        this.type = type;
        this.tribal_name = tribal_name;
        this.profile_pic_url = profile_pic_url;
        this.context = context;
        this.count_type = count_type;
        this.presenter = presenter;
        db = new SQLiteHandler(context);
        user = db.getUser();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
        return new GalleryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Log.d("abhi", "" + Urls.BASE_URL2 + profile_pic_url.substring(6));
        Picasso.with(context).load(Urls.BASE_URL2 + profile_pic_url.substring(6))
                .placeholder(R.drawable.mario_black).into(holder.profilePic, new Callback() {
            @Override
            public void onSuccess() {
                holder.profileProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });
        holder.imageName.setText(urls.get(position).getTitle());
        holder.tribalName.setText(tribal_name);
        Log.d("abhi", Urls.BASE_URL2 + urls.get(position).getUrl().substring(6));
        if (type.equals("image"))
            Picasso.with(context).load(Urls.BASE_URL2 + urls.get(position).getUrl().substring(6))
                    .placeholder(R.drawable.mario_black).into(holder.image, new Callback() {
                @Override
                public void onSuccess() {
                    holder.imageProgress.setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                }
            });

        if (type.equals("video")) {
            Picasso.with(context).load(Urls.BASE_URL2 + urls.get(position).getUrl().substring(6) + ".png").placeholder(R.drawable.mario_black).into(holder.image);
            holder.playButton.setVisibility(View.VISIBLE);
            holder.playButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, VideoViewActivity.class);
                    intent.putExtra("url", urls.get(position).getUrl());
                    context.startActivity(intent);
                }
            });
        }

        if (count_type.substring(0, 6).equals("single")) {
            holder.moreInfoButton.setVisibility(View.VISIBLE);
            holder.moreInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProfileActivity.class);
                    intent.putExtra("call_from", "more_info");
                    intent.putExtra("username", count_type.substring(6));
                    context.startActivity(intent);
                }
            });
            holder.deleteimage.setVisibility(View.GONE);
        } else
            holder.deleteimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("abhi", user.getUsername() + " " + urls.get(position).get_id());
                    if (type.equals("image"))
                        presenter.deleteImage(user.getToken(), user.getUsername(), urls.get(position).get_id(), urls.get(position).getTitle());
                    else if (type.equals("video"))
                        presenter.deleteVideo(user.getToken(), user.getUsername(), urls.get(position).get_id(), urls.get(position).getTitle());

                }
            });
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private final ProgressBar profileProgress, imageProgress;
        ImageView profilePic, image, deleteimage, playButton;
        Button moreInfoButton;
        TextView tribalName, imageName;

        public MyViewHolder(View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.profile_img);
            image = itemView.findViewById(R.id.image);
            playButton = itemView.findViewById(R.id.play_button);
            moreInfoButton = itemView.findViewById(R.id.more_info_button);
            profileProgress = itemView.findViewById(R.id.profile_pic_progress_bar);
            imageProgress = itemView.findViewById(R.id.image_progress_bar);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;
            image.getLayoutParams().height = width;
            image.requestLayout();

            if (type.equals("video"))
                playButton.setVisibility(View.VISIBLE);

            tribalName = itemView.findViewById(R.id.name_of_tribal);
            imageName = itemView.findViewById(R.id.image_name);
            deleteimage = itemView.findViewById(R.id.delete_image);

            if (count_type.equals("normal_many"))
                deleteimage.setVisibility(View.GONE);

        }
    }
}