package com.exemple.carpierschmidt.promob_jeu;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class solo_touch extends AppCompatActivity {

    long base_chrono;
    boolean routine;
    Chronometer chrono;
    ImageView c_h, c_b, c_d, c_g;
    String  objectif;
    int count=5;
    private float x1,x2,y1,y2;
    static final int min_distance = 150;

    //shortcut control + o
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_touch);

        chrono = findViewById(R.id.chrono);
        Intent intent = getIntent();
        routine = intent.getBooleanExtra("routine", false);
        base_chrono = intent.getLongExtra("chrono",0);
        chrono.setBase(SystemClock.elapsedRealtime() - (0* 60000 + base_chrono * 1000));
        chrono.start();

        c_h = findViewById(R.id.cupcake_haut);
        c_g = findViewById(R.id.cupcake_gauche);
        c_b = findViewById(R.id.cupcake_bas);
        c_d = findViewById(R.id.cupcake_droit);

        aleatoire();

    }

    @Override
    protected void onPause() {
        super.onPause();
        chrono.stop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;

            case MotionEvent.ACTION_UP:

                x2 = event.getX();
                y2 = event.getY();
                calcul();
                break;

        }
        return super.onTouchEvent(event);
    }

    public void calcul() {
        String direction="";
        float deltaX = Math.abs(x2 - x1);
        float deltaY = Math.abs(y1 - y2);

        if ((deltaY >= min_distance) || (deltaX >= min_distance)) {
            if (deltaX >= deltaY) {
                if (x1 <= x2) {
                   // Toast.makeText(this, "swipe right", Toast.LENGTH_SHORT).show();
                    direction = "droite";
                } else {
                    direction = "gauche";
                }
            }else{
                if (y1 >= y2) {
                    direction = "haut";
                } else {
                    direction = "bas";
                }
            }
        }
        if(direction == objectif){
            if(count > 1){
                aleatoire();
                count -= 1;
            }else{
                next_screen();
            }

        }

    }

    public void aleatoire(){
        int num = new Random().nextInt(3);
        switch(num){
            case 0:
                c_g.setImageResource(android.R.color.transparent);
                c_d.setImageResource(android.R.color.transparent);
                c_b.setImageResource(android.R.color.transparent);
                c_h.setImageResource(R.drawable.rainbow_cupcake);
                objectif = "haut";
                break;

            case 1:
                c_h.setImageResource(android.R.color.transparent);
                c_d.setImageResource(android.R.color.transparent);
                c_b.setImageResource(android.R.color.transparent);
                c_g.setImageResource(R.drawable.rainbow_cupcake);
                objectif = "gauche";
                break;

            case 2:
                c_g.setImageResource(android.R.color.transparent);
                c_d.setImageResource(android.R.color.transparent);
                c_h.setImageResource(android.R.color.transparent);
                c_b.setImageResource(R.drawable.rainbow_cupcake);
                objectif = "bas";
                break;

            case 3:
                c_g.setImageResource(android.R.color.transparent);
                c_h.setImageResource(android.R.color.transparent);
                c_b.setImageResource(android.R.color.transparent);
                c_d.setImageResource(R.drawable.rainbow_cupcake);
                objectif = "droite";
                break;

        }
    }

    public void next_screen() {
        if (routine) {
            long depart = getIntent().getLongExtra("depart", 0);
            Intent intent = new Intent(getBaseContext(), solo_geste.class);
            long temps = (SystemClock.elapsedRealtime()- depart)/1000;
            intent.putExtra("chrono", temps);
            intent.putExtra("routine", true);
            intent.putExtra("depart", depart);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getBaseContext(), solo_gagne.class);
            long temps = (SystemClock.elapsedRealtime()- chrono.getBase())/1000;
            intent.putExtra("temps", temps);
            startActivity(intent);
        }
    }

}

