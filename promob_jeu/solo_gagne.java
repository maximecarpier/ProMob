package com.exemple.carpierschmidt.promob_jeu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class solo_gagne extends AppCompatActivity {

    MediaPlayer music;
    TextView resultat;
    long temps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_gagne);

        Intent intent = getIntent();
        temps = intent.getLongExtra("temps",0);


        resultat = findViewById(R.id.resultat);
        music = MediaPlayer.create(this, R.raw.champions);
        music.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resultat.setText("" + temps);
    }


    @Override
    protected void onPause() {
        super.onPause();
        music.stop();
        music.release();

    }


    public void retour_menu(View view) {
        Intent intent = new Intent(this, solo_menu.class);
        startActivity(intent);
    }

}
