package com.blockent.employerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blockent.employerapp.model.Employee;

public class AddActivity extends AppCompatActivity {

    EditText editName;
    EditText editSalary;
    EditText editAge;
    Button btnSave;

    public static final int SAVE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getSupportActionBar().setTitle("직원 추가");
        // 아래 코드는 돌아갈수 있는 화살표만 화면에 보여준다.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editName = findViewById(R.id.editName);
        editSalary = findViewById(R.id.editSalary);
        editAge = findViewById(R.id.editAge);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String strSalary = editSalary.getText().toString().trim();
                String strAge = editAge.getText().toString().trim();

                if (name.isEmpty() || strSalary.isEmpty() || strAge.isEmpty()){
                    Toast.makeText(AddActivity.this, "필수 항목을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int salary = Integer.valueOf(strSalary).intValue();
                int age = Integer.valueOf(strAge).intValue();

                Employee employee = new Employee(name, age, salary);

                // 메인액티비티로 돌아가야 한다.
                // employee 객체를 메인액티비티에 보내준다.

                Intent intent = new Intent();
                intent.putExtra("employee", employee);
                setResult(SAVE, intent);

                // 이 액티비티는 할일 다 했으니, 종료하면 된다.
                finish();

            }
        });
    }

    // 액션바의 돌아가는 화살표를 눌렀을때의 이벤트를 처리하는
    // 함수를 오버라이딩 해야 한다.
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}






