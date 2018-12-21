package com.exemple.carpierschmidt.promob_jeu;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class solo_geste extends AppCompatActivity implements SensorEventListener {

    private static String TAG = solo_geste.class.getSimpleName();

    SensorManager sensorManager;
    Sensor gyroscope;
    Sensor champs_magnetique;
    long base_chrono;
    TextView data;
    float gyro_y, magn_x;
    boolean routine;
    Chronometer chrono;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_geste);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        champs_magnetique = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        data = findViewById(R.id.data);


        chrono = findViewById(R.id.chrono);
        Intent intent = getIntent();
        routine  = intent.getBooleanExtra("routine", false);
        base_chrono  = intent.getLongExtra("chrono", 0);
        chrono.setBase(SystemClock.elapsedRealtime() - (0* 60000 + base_chrono * 1000));
        chrono.start();

    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this, gyroscope);
        chrono.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        data.setText(event.values[0] + "");
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            magn_x = Math.min(event.values[0], magn_x);
        }

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

            gyro_y = Math.min(event.values[1], gyro_y);
        }

        Log.d(TAG, "onSensorChanged: (" + gyro_y + ")");
        calcul();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void calcul(){
        if ((gyro_y < -4)){
            next_screen();
            Toast.makeText(this, "if_ok", Toast.LENGTH_SHORT).show();
        }
    }

    public void next_screen(){
        long temps;
        long depart = getIntent().getLongExtra("depart", 0);
        Intent intent = new Intent(this, solo_gagne.class);
        if(routine){
            temps = (SystemClock.elapsedRealtime()- depart)/1000;
        }else{
            temps = (SystemClock.elapsedRealtime()- chrono.getBase())/1000;
        }

        intent.putExtra("temps", temps);
        startActivity(intent);
    }
}

