package com.blockent.tabbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PhotoActivity extends AppCompatActivity {

    ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        imgPhoto = findViewById(R.id.imgPhoto);

        String imgUrl = getIntent().getStringExtra("imgUrl");

        Glide.with(PhotoActivity.this)
                .load(imgUrl)
                .placeholder(R.drawable.ic_outline_photo_24)
                .into(imgPhoto);
    }
}




