package com.example.shlokpatel.sensorslist;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    TextView tx,ty,tz;
    LinearLayout root;
    SensorManager sensorManager;
    public static final String TAG="Mainactivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        root=findViewById(R.id.linear);
        tx=findViewById(R.id.textView);
        ty=findViewById(R.id.textView2);
        tz=findViewById(R.id.textView3);
        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> alluser=sensorManager.getSensorList(Sensor.TYPE_GRAVITY);
        Log.e(TAG, "total : "+alluser.size());
        for (Sensor s:alluser){
            Log.e(TAG, "name : "+s.getName());
            Log.e(TAG, "vendor : "+s.getVendor());
        }
        Sensor gravitySensor=sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener((SensorEventListener) this,gravitySensor,SensorManager.SENSOR_DELAY_UI);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] a=sensorEvent.values;
        tx.setText(""+a[0]);
        ty.setText(""+a[1]);
        tz.setText(""+a[2]);
        if(a[0]>0 && a[1]<=0 && a[2]<=0)
            root.setBackgroundColor(Color.BLACK);
        else if(a[0]<=0 && a[1]>0 && a[2]<=0)
            root.setBackgroundColor(Color.BLUE);
        else if(a[0]<=0 && a[1]<=0 && a[2]>0)
            root.setBackgroundColor(Color.GREEN);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        Sensor gravitySensor=sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener((SensorEventListener) this,gravitySensor,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        sensorManager.unregisterListener();
        super.onStop();
    }
}
