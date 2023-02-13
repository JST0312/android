package com.blockent.papagoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaMetadataEditor;
import android.os.Bundle;

import com.blockent.papagoapp.adapter.HistoryAdapter;
import com.blockent.papagoapp.model.History;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HistoryAdapter adapter;
    ArrayList<History> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));

        historyList = (ArrayList<History>) getIntent().getSerializableExtra("historyList");

        adapter = new HistoryAdapter(HistoryActivity.this, historyList);
        recyclerView.setAdapter(adapter);

    }
}