package com.exemple.carpierschmidt.promob_jeu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.graphics.Rect.intersects;

public class solo_geste_2 extends AppCompatActivity implements SensorEventListener {

    private static String TAG = solo_geste_2.class.getSimpleName();

    SensorManager sensorManager;


    Sensor accel;
    long base_chrono;
    ImageView carrot,lapin,table,verre;
    boolean routine;
    Chronometer chrono;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_geste_2);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lapin = findViewById(R.id.lapin);
        table = findViewById(R.id.table);
        verre = findViewById(R.id.verre);

        chrono = findViewById(R.id.chrono);
        Intent intent = getIntent();
        routine  = intent.getBooleanExtra("routine", false);
        base_chrono  = intent.getLongExtra("chrono", 0);

        chrono.setBase(SystemClock.elapsedRealtime() - (0* 60000 + base_chrono * 1000));
        chrono.start();

    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this, accel);
        chrono.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_UI);
        super.onResume();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

   @Override

    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

            float val_x = event.values[0];

            if (event.values[0] > 0){
                float x = verre.getX();
                verre.setX(x+val_x);
            }else {
                float x = verre.getX();
                verre.setX(x+val_x);
            }
            if(verre.getX() < table.getX()){
                next_screen();
            }
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

