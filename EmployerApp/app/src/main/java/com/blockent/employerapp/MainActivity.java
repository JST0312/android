package com.blockent.employerapp;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blockent.employerapp.adapter.EmployeeAdapter;
import com.blockent.employerapp.model.Employee;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    ProgressBar progressBar;
    FloatingActionButton fab;

    RecyclerView recyclerView;
    EmployeeAdapter adapter;
    ArrayList<Employee> employeeList = new ArrayList<>();

    final String URL = "https://block1-image-test.s3.ap-northeast-2.amazonaws.com";


    // 내가 실행한 액티비티로부터, 데이터를 다시 받아오는 경우에 작성하는 코드
    public ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            // 액티비티를 실행한후, 이 액티비티로
                            // 돌아왔을때 할 일을 여기에 작성.

                            // AddActivity가 넘겨준
                            // Employee  객체를 받아서
                            // 리스트에 넣어주고
                            // 화면 갱신 해준다.
                            if(result.getResultCode() == AddActivity.SAVE){
                                Employee employee = (Employee) result.getData().getSerializableExtra("employee");
                                employeeList.add(employee);
                                adapter.notifyDataSetChanged();
                            } else if(result.getResultCode() == EditActivity.EDIT){
                                Employee employee = (Employee) result.getData().getSerializableExtra("employee");
                                int index = result.getData().getIntExtra("index", -1);
                                employeeList.set(index, employee);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 액션바의 타이틀 변경법
        getSupportActionBar().setTitle("직원리스트");

        btnAdd = findViewById(R.id.btnAdd);
        progressBar = findViewById(R.id.progressBar);
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 새로운 액티비티 띄운다.

                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                launcher.launch(intent);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                launcher.launch(intent);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL + "/employees.json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("EMPLOYER_APP", response.toString());

                        progressBar.setVisibility(View.GONE);

                        try {

                            Log.i("EMPLOYER_APP", response.getJSONArray("data").toString());

                            JSONArray data = response.getJSONArray("data");

                            for(int i = 0; i < data.length(); i++ ){
                                // JSONArray 에 들어있는 직원 정보를 가져와서,
                                // Employee  클래스로 만든다.
                                JSONObject employeeJson = data.getJSONObject(i);

                                Employee employee = new Employee(employeeJson.getInt("id") ,
                                                           employeeJson.getString("employee_name"),
                                                           employeeJson.getInt("employee_age") ,
                                                           employeeJson.getInt("employee_salary"));

                                employeeList.add(employee);

                            }


                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "데이터 파싱 에러", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        adapter = new EmployeeAdapter(MainActivity.this, employeeList);
                        recyclerView.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "서버 에러 발생", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        progressBar.setVisibility(View.VISIBLE);
        queue.add(request);

    }


    // 액션바의 메뉴는, 전용 함수가 있다.
    // 이 함수를 오버라이딩 해야 한다.
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        // 액션바에 메뉴가 나오도록 설정한다.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 액션바의 메뉴를 탭했을때, 실행하는 함수가 있다.
    // 이 함수를 오버라이딩 해야 한다.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if(itemId == R.id.menuAdd){
            // AddActivity 실행하는 코드

            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            launcher.launch(intent);
            
        }else if (itemId == R.id.menuAbout){
            // AboutActiviy 를 실행하는 코드

        }
        return super.onOptionsItemSelected(item);
    }
}







