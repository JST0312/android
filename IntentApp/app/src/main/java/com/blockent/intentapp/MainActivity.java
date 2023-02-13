package com.blockent.intentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                composeEmail(
                        new String[]{"abc@naver.com", "abc@gmail.com"},
                        "테스트 메일입니다."
                        );

//                composeSMS("010-1111-3434");
            }
        });

    }


    // 연락처 선택하는 액티비티 띄우기
    void selectContact(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        startActivity(intent);
    }

    // 웹브라우저 실행시키는 인텐트
    void openWebPage(String url){
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    // SMS 보내기위한 액티비티 띄우기
    void composeSMS(String phone){
        Uri uri = Uri.parse("smsto:"+phone);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    // 이메일 작성하는 액티비티 띄우기
    void composeEmail(String[] address, String subject){
        Uri uri = Uri.parse("mailto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(uri);
        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        startActivity(intent);
    }
    
    // 공유버튼 눌러서, 문자열을 공유할수 있게.

}







