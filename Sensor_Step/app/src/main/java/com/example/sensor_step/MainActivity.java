package com.example.sensor_step;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private Sensor stepDetectorSensor;
    TextView tvStepDetector;
    private int mStepDetector;

    private Sensor stepCountSensor;
    TextView tvStepCount;

    private int Request_test = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        //Detector
        tvStepDetector = (TextView) findViewById(R.id.tvStepDetector);
        stepDetectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(stepDetectorSensor == null) {
            Toast.makeText(this, "No Step Detected", Toast.LENGTH_SHORT).show();
        }
        //Counter
        tvStepCount = (TextView) findViewById(R.id.tvStepCount);
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(stepCountSensor == null){
            Toast.makeText(this, "No Step Counted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this,stepDetectorSensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,stepCountSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    //센서에 변화가 생길 때 값을 리턴하는 부분

        Intent intent = new Intent(MainActivity.this, SubActivity.class);

        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            if(event.values[0] == 1.0f){
                mStepDetector+= event.values[0];
                tvStepDetector.setText("Step Detected : " + String.valueOf(mStepDetector));

                if(mStepDetector>20){
                    Toast.makeText(this, "Steps 20++", Toast.LENGTH_SHORT).show();
                    //(테스트용)startActivity 메서드를 사용해서 인수를 호출할 대상을 지정하는 Intent 객체를 전달
                    startActivityForResult(intent, Request_test);
                }
            }
        }
        else if(event.sensor.getType()==Sensor.TYPE_STEP_COUNTER){
            tvStepCount.setText("Step Counted : " + String.valueOf(event.values[0]));

            if(Float.valueOf(event.values[0])>50){
                Toast.makeText(this, "Steps 50++", Toast.LENGTH_SHORT).show();

                //실제에선 여기에서 startActivity 메서드를 사용해서 인수를 호출할 대상을 지정하는 Intent 객체를 전달

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //SubActivity에서 intent를 전달 받았음을 확인 하기 위해 결과 값 ok를 받는 것
        if(requestCode == Request_test){
            if(resultCode==RESULT_OK){
                Toast.makeText(MainActivity.this,"Result:"
                        +data.getStringExtra("result"),Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
