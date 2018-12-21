package com.exemple.carpierschmidt.promob_jeu;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Vibrator;

import java.util.Random;

public class solo_geste extends AppCompatActivity implements SensorEventListener {

    private static String TAG = solo_geste.class.getSimpleName();

    SensorManager sensorManager;
    Sensor gyroscope;
    Sensor champs_magnetique;
    long base_chrono;
    TextView vibre;
    float gyro_y;
    boolean routine, deverrouille;
    Chronometer chrono;
    int alea;
    ImageView clef;
    int nb = 2;


    Vibrator v;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_geste);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        champs_magnetique = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        vibre = findViewById(R.id.vibre);
        clef = findViewById(R.id.clef);
        clef.setImageResource(android.R.color.transparent);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        chrono = findViewById(R.id.chrono);
        Intent intent = getIntent();
        routine = intent.getBooleanExtra("routine", false);
        base_chrono = intent.getLongExtra("chrono", 0);
        chrono.setBase(SystemClock.elapsedRealtime() - (0 * 60000 + base_chrono * 1000));
        chrono.start();

        clef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deverrouille == true) {
                    if(nb > 0){
                        nb -= 1;
                        Toast.makeText(solo_geste.this, "Continue comme ça", Toast.LENGTH_SHORT).show();
                        alea();

                    }else{
                        next_screen();
                    }
                }
            }
        });
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this, gyroscope);
        sensorManager.unregisterListener(this, champs_magnetique);
        chrono.stop();
        super.onPause();
    }


    public void alea(){
        alea = new Random().nextInt(42) - 20;
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, champs_magnetique, SensorManager.SENSOR_DELAY_UI);
        alea();
        super.onResume();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            if ((event.values[2] > (alea - 5)) && (event.values[2] < (alea + 5))){
                v.vibrate(200);
                vibre.setText("ça vibre mon grand !");
                deverrouille = true;
                Log.d(TAG, "vibre");

                }else {
                vibre.setText("");
                deverrouille = false;
            }

            }

/*
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

            gyro_y = Math.min(event.values[1], gyro_y);
        }
*/
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

    public void next_screen() {
        if (routine) {
            long depart = getIntent().getLongExtra("depart", 0);
            Intent intent = new Intent(getBaseContext(), solo_question_2.class);
            long temps = (SystemClock.elapsedRealtime() - depart) / 1000;
            intent.putExtra("chrono", temps);
            intent.putExtra("routine", true);
            intent.putExtra("depart", depart);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getBaseContext(), solo_gagne.class);
            long temps = (SystemClock.elapsedRealtime() - chrono.getBase()) / 1000;
            intent.putExtra("temps", temps);
            intent.putExtra("animal", "lapin_prison");
            startActivity(intent);
        }
    }}
