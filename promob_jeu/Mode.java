package com.exemple.carpierschmidt.promob_jeu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Mode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
    }

    public void solo(View view) {
        Intent intent = new Intent(this, solo_menu.class);
        startActivity(intent);
    }
    public void multi(View view) {
        Intent intent = new Intent(this, multi.class);
        startActivity(intent);
    }
}
