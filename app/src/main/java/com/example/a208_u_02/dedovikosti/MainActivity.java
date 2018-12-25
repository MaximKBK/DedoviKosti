package com.example.a208_u_02.dedovikosti;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView img1;
    private ImageView img2;
    private SensorManager sensorManager;
    private Sensor motionSensor;
    private List<Integer> ImageResources = new ArrayList<>();
    private Random random = new Random();
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;
    private static final int ACCELERATION_THRESHOLD = 100000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img1=findViewById(R.id.firstKost);
        img2=findViewById(R.id.secondKost);
        acceleration = 0.00f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;
        ImageResources.add(R.drawable.kost1);
        ImageResources.add(R.drawable.kost2);
        ImageResources.add(R.drawable.kost3);
        ImageResources.add(R.drawable.kost4);
        ImageResources.add(R.drawable.kost5);
        ImageResources.add(R.drawable.kost6);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        motionSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }
    SensorEventListener motionListener=new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // save previous acceleration value
            lastAcceleration = currentAcceleration;

            // calculate the current acceleration
            currentAcceleration = x * x + y * y + z * z;

            // calculate the change in acceleration
            acceleration = currentAcceleration *
                    (currentAcceleration - lastAcceleration);

            // if the acceleration is above a certain threshold
            if (acceleration > ACCELERATION_THRESHOLD) {
                int randomResource = random.nextInt(ImageResources.size());
                img1.setImageResource(ImageResources.get(randomResource));
                int randomResource2 = random.nextInt(ImageResources.size());
                img2.setImageResource(ImageResources.get(randomResource2));
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(motionListener,motionSensor,sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(motionListener);
    }
}
