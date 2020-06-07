package com.auaf.gapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class SensorExampleActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    TextView textView;
    private boolean color=false;
    private long lastUpdat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_example);

        textView=findViewById(R.id.tvTextChange);
        textView.setBackgroundColor(Color.BLUE);



        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);

        lastUpdat=System.currentTimeMillis();
        Log.d("Data", "onCreate: "+ System.currentTimeMillis());



    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){

            getAcceleoremeter(event);
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void getAcceleoremeter(SensorEvent event) {

        float [] values=event.values;

        float x=values[0];
        float y=values[1];
        float z=values[2];



        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = event.timestamp;
        if (accelationSquareRoot >= 2) //
        {
            if (actualTime - lastUpdat < 200) {
                return;
            }
            lastUpdat = actualTime;

            Toast.makeText(this, "Device was shaked", Toast.LENGTH_SHORT).show();


            if (color){

                textView.setBackgroundColor(Color.RED);
            }else {

                textView.setBackgroundColor(Color.BLACK);
            }

            color=!color;
        }
    }



    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(SensorExampleActivity.this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(SensorExampleActivity.this);
    }


}
