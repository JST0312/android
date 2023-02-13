package com.blockent.youtubeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blockent.youtubeapp.R;
import com.blockent.youtubeapp.model.Video;
import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    Context context;
    ArrayList<Video> videoList;

    public VideoAdapter(Context context, ArrayList<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_row, parent, false);
        return new VideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder, int position) {
        Video video = videoList.get(position);

        holder.txtTitle.setText(video.title);
        holder.txtDescription.setText(video.description);
        Glide.with(context).load( video.mediumUrl )
                .placeholder(R.drawable.ic_baseline_ondemand_video_24)
                .into( holder.imgThumb );

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView txtTitle;
        TextView txtDescription;
        ImageView imgThumb;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            imgThumb = itemView.findViewById(R.id.imgThumb);
        }
    }
}
