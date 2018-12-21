package com.exemple.carpierschmidt.promob_jeu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static android.graphics.Rect.intersects;

public class solo_geste_2 extends AppCompatActivity implements SensorEventListener {

    private static String TAG = solo_geste_2.class.getSimpleName();

    SensorManager sensorManager;


    ImageView marteau, terre1, terre2, terre3;
    Sensor accel, gyro;
    long base_chrono;
    ImageView carrot, table;
    boolean routine;
    int alea = 1, nb = 4;
    Chronometer chrono;
    float val_x, val_g;
    TextView text;
    int place = 0;
    float base_x;
    float y1,y2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_geste_2);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        terre1 = findViewById(R.id.terre1);
        terre2 = findViewById(R.id.terre2);
        terre3 = findViewById(R.id.terre3);
        marteau = findViewById(R.id.marteau);
        text = findViewById(R.id.text);

        chrono = findViewById(R.id.chrono);
        Intent intent = getIntent();
        routine = intent.getBooleanExtra("routine", false);
        base_chrono = intent.getLongExtra("chrono", 0);

        base_x = marteau.getX();

        chrono.setBase(SystemClock.elapsedRealtime() - (0 * 60000 + base_chrono * 1000));
        chrono.start();
        aleatoire();

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
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                y1 = event.getY();
                break;

            case MotionEvent.ACTION_UP:

                y2 = event.getY();
                tape();
                break;

        }
        return super.onTouchEvent(event);
    }

    @Override

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            int prev;

            val_x = event.values[0];


            if ((val_x > 5) && (place > -1)) {
                place -= 1;
                marteau.setX(marteau.getX() - 320);
            }else if ((val_x < -5) && (place < 1)) {
                place += 1;
                marteau.setX(marteau.getX() + 320);

            } else if ((place != 0) && (val_x >  -5) && (val_x < 5)){
                if (place == -1) {
                    marteau.setX(marteau.getX() + 320);
                    place += 1;
                } else if (place == 1) {
                    marteau.setX(marteau.getX() - 320);
                    place -= 1;
                }
            }
        }
    }

    public void tape() {
        float min_distance = 150;
        float deltaY = Math.abs(y1 - y2);

        if (deltaY >= min_distance) {
            if (y2 > y1) {
                if (place == alea) {
                    nb -= 1;
                    if (nb == 0) {
                        next_screen();
                    } else {
                        Toast.makeText(this, "Continue à taper dessus!", Toast.LENGTH_SHORT).show();
                        aleatoire();
                    }
                }
            }
        }
    }


    public void aleatoire(){
        int nnum = new Random().nextInt(2)-1;
        while(alea == nnum){
            nnum = new Random().nextInt(2)-1;
        }
        alea = nnum;

        terre1.setImageResource(R.drawable.terre);
        terre2.setImageResource(R.drawable.terre);
        terre3.setImageResource(R.drawable.terre);

        switch (alea){
            case -1:
                terre1.setImageResource(R.drawable.taupe);
                break;
            case 0:
                terre2.setImageResource(R.drawable.taupe);
                break;
            case 1:
                terre3.setImageResource(R.drawable.taupe);
        }
    }

    public void next_screen() {
        long temps;
        long depart = getIntent().getLongExtra("depart", 0);
        Intent intent = new Intent(this, solo_gagne.class);
        if (routine) {
            temps = (SystemClock.elapsedRealtime() - depart) / 1000;
            intent.putExtra("animal", "routine");
        } else {
            temps = (SystemClock.elapsedRealtime() - chrono.getBase()) / 1000;
            intent.putExtra("animal", "taupe");
        }

        intent.putExtra("temps", temps);
        startActivity(intent);
    }
}

