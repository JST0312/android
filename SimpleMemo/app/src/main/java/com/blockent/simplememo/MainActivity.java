package com.blockent.simplememo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blockent.simplememo.adapter.MemoAdapter;
import com.blockent.simplememo.model.Memo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editMemo;
    Button btnSave;

    RecyclerView recyclerView;
    MemoAdapter adapter;
    ArrayList<Memo> memoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editMemo = findViewById(R.id.editMemo);
        btnSave = findViewById(R.id.btnSave);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 유저가 입력한 메모를 가져온다.
                String content = editMemo.getText().toString().trim();
                if(content.isEmpty()){
                    Toast.makeText(MainActivity.this, "메모 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 2. 메모를 리스트에 저장한다.
                Memo memo = new Memo();
                memo.setContent(content);

                memoList.add(memo);

                // 3. 리사이클러뷰를 갱신!!!!
                adapter.notifyDataSetChanged();

                // 4. 에디트텍스트를 초기화한다.
                editMemo.setText("");

            }
        });


        // 리사이클러뷰에 보이게 한다.
        adapter = new MemoAdapter(MainActivity.this, memoList);
        recyclerView.setAdapter(adapter);
    }
}






