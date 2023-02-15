package com.blockent.tabbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.blockent.tabbar.adapter.PostingAdapter;
import com.blockent.tabbar.api.NetworkClient;
import com.blockent.tabbar.api.PostingApi;
import com.blockent.tabbar.config.Config;
import com.blockent.tabbar.model.Posting;
import com.blockent.tabbar.model.PostingList;
import com.blockent.tabbar.model.Res;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_first, container, false);
        btnAdd = rootView.findViewById(R.id.btnAdd);

        progressBar = rootView.findViewById(R.id.progressBar);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class );
                startActivity(intent);
            }
        });

        getNetworkData();

        return rootView;
    }


    void getNetworkData(){
        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = NetworkClient.getRetrofitClient(getActivity());

        PostingApi api = retrofit.create(PostingApi.class);

        SharedPreferences sp = getActivity().getSharedPreferences(Config.PREFERENCE_NAME, getActivity().MODE_PRIVATE);
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

                    adapter = new PostingAdapter(getActivity(), postingList);
                    adapter.setOnItemClickListener(new PostingAdapter.OnItemClickListener() {
                        @Override
                        public void likeProcess(int index) {
                            FirstFragment.this.likeProcess(index);
                        }

                        @Override
                        public void onImageClick(int index) {
                            Posting posting = postingList.get(index);
                            String imgUrl = posting.getImgUrl();
                            Intent intent = new Intent(getActivity(), PhotoActivity.class);
                            intent.putExtra("imgUrl", imgUrl);
                            startActivity(intent);
                        }
                    });

                    recyclerView.setAdapter(adapter);

                    Log.i("TABBAR_APP", "onResponse :  "+postingList.size());

                }



            }

            @Override
            public void onFailure(Call<PostingList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }


    public void likeProcess(int index){

        Log.i("TABBAR_APP", "likeProcess :  "+postingList.size());

        selectedPosting = postingList.get(index);

        // 2. 해당행의 좋아요가 이미 좋아요인지 아닌지 파악
        if (selectedPosting.getIsLike()  == 0){
            // 3. 좋아요 API를 호출
            Retrofit retrofit = NetworkClient.getRetrofitClient(getActivity());

            PostingApi api = retrofit.create(PostingApi.class);

            SharedPreferences sp = getActivity().getSharedPreferences(Config.PREFERENCE_NAME, Context.MODE_PRIVATE);
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

            Retrofit retrofit = NetworkClient.getRetrofitClient(getActivity());

            PostingApi api = retrofit.create(PostingApi.class);

            SharedPreferences sp = getActivity().getSharedPreferences(Config.PREFERENCE_NAME, Context.MODE_PRIVATE);
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








