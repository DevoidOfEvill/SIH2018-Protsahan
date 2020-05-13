package com.mota.tribal.protsahan.Profile.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mota.tribal.protsahan.R;

import java.util.ArrayList;
import java.util.HashMap;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ArrayList<String> urls;
    private Context context;
    private String recyclerViewType;

    public RecyclerViewAdapter(ArrayList<String> urls, Context context, String recyclerViewType) {
        this.urls = urls;
        this.context = context;
        this.recyclerViewType = recyclerViewType;
    }

    public static Bitmap retriveVideoFrameFromVideo(String videoPath) throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)" + e.getMessage());

        } finally {
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Log.d("abhi", recyclerViewType);

        if (recyclerViewType.equals("video_thumbnails")) {
            try {
                Bitmap bitmap = retriveVideoFrameFromVideo(urls.get(position));
                holder.thumbnail.setImageBitmap(bitmap);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            holder.name.setText("Video-" + (position + 1));
        } else if (recyclerViewType.equals("docs"))
            holder.name.setText("Document - " + (position + 1));
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView name;

        public MyViewHolder(View view) {
            super(view);
            thumbnail = view.findViewById(R.id.thumbnail);
            name = view.findViewById(R.id.video_name);
            if (recyclerViewType.equals("video_thumbnails"))
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        Intent intent = new Intent(context, VideoViewActivity.class);
                        intent.putExtra("url", urls.get(position));
                        context.startActivity(intent);
                    }
                });
            else if (recyclerViewType.equals("docs"))
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        Bundle bundle = new Bundle();
                        bundle.putString("url", urls.get(position));
                        DocViewFragment fragment = new DocViewFragment();
                        fragment.setArguments(bundle);
                        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frag_frame, fragment,
                                        fragment.getClass().getSimpleName()).
                                addToBackStack(null).commit();
                    }
                });
        }
    }
}
