package com.blockent.postingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.blockent.postingapp.adapter.PostingAdapter;
import com.blockent.postingapp.api.NetworkClient;
import com.blockent.postingapp.api.PostingApi;
import com.blockent.postingapp.config.Config;
import com.blockent.postingapp.model.Posting;
import com.blockent.postingapp.model.PostingList;
import com.blockent.postingapp.model.Res;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;

    RecyclerView recyclerView;
    PostingAdapter adapter;
    ArrayList<Posting> postingList = new ArrayList<>();

    ProgressBar progressBar;

    // 페이징 처리를 위한 변수
    int count = 0;
    int offset = 0;
    int limit = 7;
    private Posting selectedPosting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 억세스 토큰이 있는지 확인
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String accessToken = sp.getString(Config.ACCESS_TOKEN, "");
        if(accessToken.isEmpty()){
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        btnAdd = findViewById(R.id.btnAdd);
        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getNetworkData();
    }

    void getNetworkData(){
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);

        PostingApi api = retrofit.create(PostingApi.class);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
        String accessToken = "Bearer " + sp.getString(Config.ACCESS_TOKEN, "");

        offset = 0;

        Call<PostingList> call = api.getPosting(accessToken, offset, limit);

        call.enqueue(new Callback<PostingList>() {
            @Override
            public void onResponse(Call<PostingList> call, Response<PostingList> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccessful()){

                    postingList.clear();

                    count = response.body().getCount();
                    postingList.addAll( response.body().getItems() );

                    offset = offset + count;

                    adapter = new PostingAdapter(MainActivity.this, postingList);
                    recyclerView.setAdapter(adapter);

                }



            }

            @Override
            public void onFailure(Call<PostingList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    public void likeProcess(int index){

        selectedPosting = postingList.get(index);

        // 2. 해당행의 좋아요가 이미 좋아요인지 아닌지 파악
        if (selectedPosting.getIsLike()  == 0){
            // 3. 좋아요 API를 호출
            Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);

            PostingApi api = retrofit.create(PostingApi.class);

            SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, Context.MODE_PRIVATE);
            String accessToken = "Bearer " +sp.getString(Config.ACCESS_TOKEN, "");

            Call<Res> call = api.setLike(accessToken, selectedPosting.getPostingId());

            call.enqueue(new Callback<Res>() {
                @Override
                public void onResponse(Call<Res> call, Response<Res> response) {
                    if(response.isSuccessful()){

                        // 4. 화면에 결과를 표시
                        selectedPosting.setIsLike(1);

                        adapter.notifyDataSetChanged();

                    }else{

                    }
                }

                @Override
                public void onFailure(Call<Res> call, Throwable t) {

                }
            });


        }else{
            // 3. 좋아요 해제 API를 호출

            Retrofit retrofit = NetworkClient.getRetrofitClient(MainActivity.this);

            PostingApi api = retrofit.create(PostingApi.class);

            SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME, Context.MODE_PRIVATE);
            String accessToken = "Bearer " +sp.getString(Config.ACCESS_TOKEN, "");

            Call<Res> call = api.deleteLike(accessToken, selectedPosting.getPostingId());

            call.enqueue(new Callback<Res>() {
                @Override
                public void onResponse(Call<Res> call, Response<Res> response) {
                    if(response.isSuccessful()){

                        // 4. 화면에 결과를 표시
                        selectedPosting.setIsLike(0);

                        adapter.notifyDataSetChanged();

                    }else{

                    }
                }

                @Override
                public void onFailure(Call<Res> call, Throwable t) {

                }
            });



        }


    }

}








