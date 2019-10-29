package com.example.sensor_step;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubActivity extends AppCompatActivity {
    Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        //subactivity로 인텐트 값이 잘 넘어 오는 것을 확인
        Intent intent = new Intent();
        intent.putExtra("result","SubActivity로 intent 전달 성공");
        setResult(RESULT_OK,intent);
        finish();

        //button1 = (Button) findViewById(R.id.button1);
        //button1.setOnClickListener((View.OnClickListener) this);
    }

   /* public void onClick(View v){

        Intent resultIntent = new Intent(this,MainActivity.class);
        resultIntent.putExtra("result","서브액티비티입니다");
        setResult(RESULT_OK,resultIntent);
        finish();
    }
*/
}
