package com.blockent.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtAge;

    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.i("LifeCycleApp",
                "두번째 액티비티의 onCreate 함수 실행");

        txtName = findViewById(R.id.txtName);
        txtAge = findViewById(R.id.txtAge);

        // 이 액티비티를 실행한 액티비티로부터 데이터를 받아오는 코드
        // 인텐트를 가져오고, 그 인텐트에 들어있는 키값으로 데이터를
        // 받아온다.
        String name = getIntent().getStringExtra("name");
        age = getIntent().getIntExtra("age", 0);

//        Intent intent = getIntent();
//        String name2 = intent.getStringExtra("name");

        txtName.setText(name);

        age = age + 10;
        txtAge.setText(age+"");

    }



    // Back 버튼 눌렀을때의 이벤트 처리 방법
    @Override
    public void onBackPressed() {

        Log.i("LifeCycleApp",
                "두번째 액티비티의 onBackPressed 함수 실행");

        // 10년후의 나이를 메인액티비티로 전달!!!
        Intent intent = new Intent();
        intent.putExtra("age10", age);
        // 돌려줄때는 startActivity 가 아니라, setResult 함수 이용
        setResult(0, intent);

        // 프레임워크가 할일 하라는 코드.
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("LifeCycleApp",
                "두번째 액티비티의 onStart 함수 실행");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("LifeCycleApp",
                "두번째 액티비티의 onResume 함수 실행");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LifeCycleApp",
                "두번째 액티비티의 onPause 함수 실행");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LifeCycleApp",
                "두번째 액티비티의 onStop 함수 실행");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LifeCycleApp",
                "두번째 액티비티의 onDestroy 함수 실행");
    }
}





