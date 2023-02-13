package com.blockent.simpleimgapp;

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

        // 이미지를 표시하려면, url 있어야 한다.
        // 따라서, 메인액티비티로부터, url을 받아온다!!

        String url = getIntent().getStringExtra("url");

        Glide.with(PhotoActivity.this)
                .load(url)
                .placeholder(R.drawable.ic_outline_insert_photo_24)
                .into(imgPhoto);

    }
}