package com.blockent.actionbarapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blockent.actionbarapp.adapter.PostAdapter;
import com.blockent.actionbarapp.model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    FloatingActionButton fab;

    RecyclerView recyclerView;
    PostAdapter adapter;
    ArrayList<Post> postList = new ArrayList<>();

    final String URL = "https://block1-image-test.s3.ap-northeast-2.amazonaws.com";

    // 내가 실행한 액티비티로부터, 데이터를 다시 받아오는 경우에 작성하는 코드
    public ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            // 액티비티를 실행한후, 이 액티비티로
                            // 돌아왔을때 할 일을 여기에 작성.

                            // 객체를 받아서
                            // 리스트에 넣어주고
                            // 화면 갱신 해준다.
                            if(result.getResultCode() == AddActivity.SAVE){
                               Post post = (Post) result.getData().getSerializableExtra("post");
                               postList.add(0, post);
                               adapter.notifyDataSetChanged();
                            }

                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("포스팅 리스트");

        progressBar = findViewById(R.id.progressBar);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // fab 버튼에 대한 이벤트 처리
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AddActivity 를 실행한다.
                startAddActivity();
            }
        });
        
        // 네트워크 통해서 데이터를 받아오고, 화면에 표시
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL + "/posting.json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            JSONArray data = response.getJSONArray("data");

                            for(int i = 0; i < data.length(); i++){

                                Post post = new Post(data.getJSONObject(i).getInt("userId"),
                                        data.getJSONObject(i).getInt("id"),
                                        data.getJSONObject(i).getString("title"),
                                        data.getJSONObject(i).getString("body"));

                                postList.add(post);

                            }

                        } catch (JSONException e) {

                            return;
                        }

                        adapter = new PostAdapter(MainActivity.this, postList);
                        recyclerView.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );
        // 네트워크 호출할때, 프로그레스바를 화면에 보이게 한다.
        progressBar.setVisibility(View.VISIBLE);
        // 네트워크 호출!
        queue.add(request);

    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        // 액션바에 메뉴가 나오도록 설정한다.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if(itemId == R.id.menuAdd){
            // AddActivity 를 실행한다.
            startAddActivity();
        }

        return super.onOptionsItemSelected(item);
    }


    // AddActivity 를 실행하는 함수 만든다.
    void startAddActivity(){
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        launcher.launch(intent);
    }

}




