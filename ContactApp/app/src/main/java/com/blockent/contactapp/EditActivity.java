package com.blockent.contactapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blockent.contactapp.data.DatabaseHandler;
import com.blockent.contactapp.model.Contact;

public class EditActivity extends AppCompatActivity {

    EditText editName;
    EditText editPhone;
    Button btnSave;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        btnSave = findViewById(R.id.btnSave);

//        int id = getIntent().getIntExtra("id", 0);
//        String name = getIntent().getStringExtra("name");
//        String phone = getIntent().getStringExtra("phone");
//        editName.setText(name);
//        editPhone.setText(phone);

        Contact contact = (Contact) getIntent().getSerializableExtra("contact");
        id = contact.id;
        editName.setText( contact.name  );
        editPhone.setText( contact.phone );

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(EditActivity.this, "필수항목입니다. 데이터를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // db 에 저장한다.
                DatabaseHandler db = new DatabaseHandler(EditActivity.this);

                // 아이디, 이름, 전화번호
                Contact contact = new Contact(id, name, phone);

                db.updateContact(contact);

                // 이 액티비티를 종료하고, 원래있던 메인액티비티가 나오도록!
                finish();
            }
        });

    }
}






