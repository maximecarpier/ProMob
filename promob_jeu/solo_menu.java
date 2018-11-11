package com.exemple.carpierschmidt.promob_jeu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class solo_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_menu);
    }

    public void question(View view) {
        Intent intent = new Intent(this, solo_question.class);
        startActivity(intent);
    }

    public void touch(View view){
        Intent intent = new Intent(this, solo_touch_2.class);
        startActivity(intent);
    }

    public void geste(View view){
        Intent intent = new Intent(this, solo_geste_2.class);
        startActivity(intent);
    }

    public void routine(View view){
        Intent intent = new Intent(this, solo_question.class);
        intent.putExtra("routine", true);
        startActivity(intent);
    }
}
