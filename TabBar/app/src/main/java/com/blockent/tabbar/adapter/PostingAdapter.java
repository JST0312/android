package com.blockent.tabbar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.blockent.tabbar.FirstFragment;
import com.blockent.tabbar.MainActivity;
import com.blockent.tabbar.R;
import com.blockent.tabbar.model.Posting;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class PostingAdapter extends RecyclerView.Adapter<PostingAdapter.ViewHolder>  {

    Context context;
    ArrayList<Posting> postingList;

    SimpleDateFormat sf;
    SimpleDateFormat df;

    public interface OnItemClickListener {
        // 프레그먼트에서 사용가능토록,
        // 어댑터의 특정 행이나 버튼 누르면 처리할 함수를 만든다.
        void likeProcess(int index);
        void onImageClick(int index);
    }
    public OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public PostingAdapter(Context context, ArrayList<Posting> postingList) {
        this.context = context;
        this.postingList = postingList;

        // UTC => Local Time
        sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sf.setTimeZone(TimeZone.getTimeZone("UTC"));
        df.setTimeZone(TimeZone.getDefault());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posting_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Posting posting = postingList.get(position);

        holder.txtContent.setText(posting.getContent());
        holder.txtEmail.setText(posting.getEmail());

        try {
            Date date = sf.parse(posting.getCreatedAt());
            holder.txtCreatedAt.setText(  df.format(date)  );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(posting.getIsLike() == 1){
            holder.imgLike.setImageResource(R.drawable.ic_thumb_up_2);
        }else {
            holder.imgLike.setImageResource(R.drawable.ic_thumb_up_1);
        }

        Glide.with(context)
                .load(posting.getImgUrl())
                .placeholder(R.drawable.ic_outline_photo_24)
                .into(holder.imgPhoto);

    }

    @Override
    public int getItemCount() {
        return postingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView txtContent;
        TextView txtEmail;
        TextView txtCreatedAt;
        ImageView imgLike;
        ImageView imgPhoto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
            imgLike = itemView.findViewById(R.id.imgLike);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);

            imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 1. 어느번째의 데이터의 좋아요를 누른것인지 확인
                    int index = getAdapterPosition();

//                    ((MainActivity)context).likeProcess(index);
//                    ((FirstFragment) ((MainActivity)context).firstFragment).likeProcess(index);
                    listener.likeProcess(index);

                }
            });

            imgPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    listener.onImageClick(index);
                }
            });

        }
    }

}










