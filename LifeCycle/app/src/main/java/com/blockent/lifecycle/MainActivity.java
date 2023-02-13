package com.blockent.lifecycle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText editName;
    EditText editAge;
    TextView txtAge;

    // 내가 실행한 액티비티로부터, 데이터를 다시 받아오는 경우에 작성하는 코드
    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if(result.getResultCode() == 0){
                               int age10 = result.getData().getIntExtra("age10", 0);
                                    txtAge.setText(""+age10);
                            }
                        }
                    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("LifeCycleApp", "onCreate 함수 실행");

        button = findViewById(R.id.button);
        editName = findViewById(R.id.editName);
        editAge = findViewById(R.id.editAge);
        txtAge = findViewById(R.id.txtAge);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editName.getText().toString().trim();
                String strAge = editAge.getText().toString().trim();

                int age = Integer.valueOf(strAge).intValue();

                // 두번째 액티비티를 실행시키는 코드 작성.

                // 인텐트를 만든다!
                // 인텐트란, 어떤 액티비티가, 어떤 액티비티를 띄우겠다 라는것.
                Intent intent = new Intent(MainActivity.this, SecondActivity.class );
                // 새로운 액티비티에 전달할 데이터를 셋팅한다.
                // key, value 로 데이터 셋팅!
                intent.putExtra("name", name);
                intent.putExtra("age", age);

                // 아래 startActivity 함수는 단방향 데이터 전달할때만 사용
//                startActivity(intent);

                // 양방향 데이터 전달할때는
                launcher.launch(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i("LifeCycleApp", "onStart 함수 실행");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("LifeCycleApp", "onResume 함수 실행");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("LifeCycleApp", "onPause 함수 실행");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("LifeCycleApp", "onStop 함수 실행");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("LifeCycleApp", "onDestroy 함수 실행");
    }
}










