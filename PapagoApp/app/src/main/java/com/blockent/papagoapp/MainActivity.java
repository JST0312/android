package com.blockent.papagoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blockent.papagoapp.config.Config;
import com.blockent.papagoapp.model.History;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    EditText editText;
    Button button;
    TextView txtResult;

    final String URL = "https://openapi.naver.com/v1/papago/n2mt";

    String text;
    String target;
    ArrayList<History> historyList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        txtResult = findViewById(R.id.txtResult);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1. 에디티텍스트에서 유저가 작성한 글을 가져온다.
                text = editText.getText().toString().trim();

                if(text.isEmpty()){
                    return;
                }
                // 2. 어떤 언어로 번역할지를 라디오버튼 정보 가져온다.
                int radioBtnId = radioGroup.getCheckedRadioButtonId();

                if(radioBtnId == R.id.radioBtn1){
                    target = "en";
                }else if(radioBtnId == R.id.radioBtn2){
                    target = "zh-CN";
                }else if(radioBtnId == R.id.radioBtn3){
                    target = "zh-TW";
                }else if(radioBtnId == R.id.radioBtn4){
                    target = "th";
                }else {
                    Toast.makeText(MainActivity.this, "언어를 선택하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 3. 파파고 API 호출
                String source = "ko";

                JSONObject body = new JSONObject();
                try {
                    body.put("source", source);
                    body.put("target", target);
                    body.put("text", text);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST,
                        URL,
                        body,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("PAPAGO_APP", response.toString());

                                // 4. 호출결과를 화면에 보여준다.
                                try {

                                    String result = response.getJSONObject("message")
                                            .getJSONObject("result")
                                            .getString("translatedText");

                                    txtResult.setText(result);

                                    // 히스토리 객체를 생성한다.
                                    History history = new History(text, result, target);

                                    // 어레이리스트에 넣어준다.
                                    historyList.add(0, history);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    return;
                                }


                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("PAPAGO_APP", error.toString());
                            }
                        }
                ){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("X-Naver-Client-Id", Config.NAVER_CLIENT_ID);
                        headers.put("X-Naver-Client-Secret", Config.NAVER_CLIENT_SECRET);
                        return headers;
                    }
                };

                queue.add(request);



            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if(itemId == R.id.menuHistory){
            // 새로운 액티비티를 띄운다.

            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            intent.putExtra("historyList", historyList);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}



