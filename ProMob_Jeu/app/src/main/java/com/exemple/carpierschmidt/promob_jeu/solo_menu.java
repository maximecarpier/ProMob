package com.exemple.carpierschmidt.promob_jeu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class solo_menu extends AppCompatActivity {

    TextView play, quick;
    long duration = 2000;
    Button question, eau, rocher, cupcake, prison, carrote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_menu);

        play = findViewById(R.id.play);
        quick = findViewById(R.id.quick);

        question = findViewById(R.id.question);
        prison = findViewById(R.id.prison);
        cupcake = findViewById(R.id.cupcake);
        carrote = findViewById(R.id.carrote);
        eau = findViewById(R.id.eau);
        rocher = findViewById(R.id.rocher);


    }


    public void animation(){
        ObjectAnimator playx = ObjectAnimator.ofFloat(play,"x", 1800f);
        ObjectAnimator quickx = ObjectAnimator.ofFloat(quick,"x", -2250f);
        playx.setDuration(duration);
        quickx.setDuration(duration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(playx, quickx);
        animatorSet.start();

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void quick_game(View view) {
        animation();
        question.setVisibility(View.VISIBLE);
        eau.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        rocher.setVisibility(View.VISIBLE);
                        cupcake.setVisibility(View.VISIBLE);
                    }
                }, 200);

                prison.setVisibility(View.VISIBLE);
                carrote.setVisibility(View.VISIBLE);

            }
        }, 400);

    }

    public void question(View view){
        Intent intent = new Intent(this, solo_question_2.class);
        startActivity(intent);
    }
    public void rocher(View view){
        Intent intent = new Intent(this, solo_touch_2.class);
        startActivity(intent);
    }
    public void cupcake(View view){
        Intent intent = new Intent(this, solo_touch.class);
        startActivity(intent);
    }
    public void prison(View view){
        Intent intent = new Intent(this, solo_geste.class);
        startActivity(intent);
    }
    public void carrote(View view){
        Intent intent = new Intent(this, solo_question.class);
        startActivity(intent);
    }
    public void eau(View view){
        Intent intent = new Intent(this, solo_geste_2.class);
        startActivity(intent);
    }


    public void routine(View view){
        final Intent intent = new Intent(this, solo_question.class);
        intent.putExtra("routine", true);
        animation();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                startActivity(intent);
            }
        }, 2000);
    }
}

class Utils {

    // Delay mechanism

    public interface DelayCallback{
        void afterDelay();
    }

    public static void delay(int secs, final DelayCallback delayCallback){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                delayCallback.afterDelay();
            }
        }, secs); // afterDelay will be executed after (secs*1000) milliseconds.
    }
}
