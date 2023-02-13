package com.blockent.simpleimgapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blockent.simpleimgapp.PhotoActivity;
import com.blockent.simpleimgapp.R;
import com.blockent.simpleimgapp.model.Post;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Context context;
    ArrayList<Post> postList;

    public PostAdapter(Context context, ArrayList<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_row, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post post = postList.get(position);

        holder.txtTitle.setText(post.title);
        holder.txtId.setText(post.id+"");
        holder.txtAlbumId.setText(post.albumId+"");
        // 이미지뷰에 사진 셋팅.
        // URL을 가지고, 네트워크를 통해서 사진데이터를 받아온다.

        Glide.with(context).load(post.thumbnailUrl)
                .placeholder(R.drawable.ic_outline_insert_photo_24)
                .into(holder.imgPhoto);

    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        TextView txtId;
        TextView txtAlbumId;
        ImageView imgPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtId = itemView.findViewById(R.id.txtId);
            txtAlbumId = itemView.findViewById(R.id.txtAlbumId);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);

            imgPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();

                    Post post = postList.get(index);

                    Intent intent = new Intent(context, PhotoActivity.class);
                    intent.putExtra("url", post.url);

                    context.startActivity(intent);

                }
            });

        }
    }
}
