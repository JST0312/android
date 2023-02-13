package com.blockent.memoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blockent.memoapp.api.NetworkClient;
import com.blockent.memoapp.api.UserApi;
import com.blockent.memoapp.config.Config;
import com.blockent.memoapp.model.User;
import com.blockent.memoapp.model.UserRes;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    EditText editEmail;
    EditText editPassword;
    EditText editNickname;
    Button btnRegister;
    TextView txtLogin;

    // 네트워크를 통해서 로직처리를 할때 보여주는
    // 프로그레스 다이얼로그
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editNickname = findViewById(R.id.editNickname);
        btnRegister = findViewById(R.id.btnLogin);
        txtLogin = findViewById(R.id.txtRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 이메일 가져와서 형식 체크
                String email = editEmail.getText().toString().trim();

                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if(pattern.matcher(email).matches() == false){
                    Toast.makeText(RegisterActivity.this, "이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호 체크
                String password = editPassword.getText().toString().trim();
                // 우리 기획에는 비번길이가 4~12 만 허용
                if(password.length() < 4 || password.length() > 12){
                    Toast.makeText(RegisterActivity.this, "비번 길이를 확인하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 닉네임 가져온다.
                String nickname = editNickname.getText().toString().trim();
                if(nickname.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "닉네임은 필수입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 회원가입 API 를 호출!

                // 1. 다이얼로그를 화면에 보여준다.
                showProgress("회원가입 중입니다...");

                // 2. 서버로 데이터를 보낸다.
                // 2-1. 레트로핏 변수 생성
                Retrofit retrofit =
                        NetworkClient.getRetrofitClient(RegisterActivity.this);
                // 2-2. api 패키지에 있는 인터페이스 생성
                UserApi api = retrofit.create(UserApi.class);

                // 2-3. 보낼 데이터 만들기 => 클래스의 객체 생성
                User user = new User(email, password, nickname);

                // 2-4. api 호출
                Call<UserRes> call = api.register(user);
                // 2.5. 서버로부터 받아온 응답을 처리하는 코드 작성
                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        // 프로그레스 다이얼로그가 있으면, 나타나지 않게해준다.
                        dismissProgress();

                        // 서버에서 보낸 응답이 200 OK 일때 처리하는 코드
                        if(response.isSuccessful()){
                            Log.i("MEMO_APP", response.toString());

                            // 서버가 보낸 데이터를 받는 방법!!!!!!!
                            UserRes res = response.body();

                            Log.i("MEMO_APP", res.getResult());
                            Log.i("MEMO_APP", res.getAccess_token());

                            // 억세스토큰은, api 할때마다 헤더에서 사용하므로
                            // 회원가입이나 로그인이 끝나면, 파일로 꼭 저장해 놔야 한다.
                            SharedPreferences sp =
                                    getApplication().getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString(Config.ACCESS_TOKEN, res.getAccess_token() );
                            editor.apply();

                            // 3. 데이터를 이상없이 처리하면,
                            //    메인액티비티를 화면에 나오게 한다.
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Log.i("MEMO_APP", response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {
                        // 프로그레스 다이얼로그가 있으면, 나타나지 않게해준다.
                        dismissProgress();
                    }
                });



            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

                finish();
            }
        });
    }

    // 네트워크 로직 처리시에 화면에 보여주는 함수
    void showProgress(String message){
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.show();
    }

    // 로직처리가 끝나면 화면에서 사라지는 함수
    void dismissProgress(){
        dialog.dismiss();
    }

}





