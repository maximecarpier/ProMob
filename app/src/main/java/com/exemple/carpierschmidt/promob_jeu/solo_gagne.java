package com.exemple.carpierschmidt.promob_jeu;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class solo_gagne extends AppCompatActivity {

    MediaPlayer music;
    TextView resultat;
    long temps;
    String animal;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_gagne);

        Intent intent = getIntent();
        temps = intent.getLongExtra("temps",0);
        animal = intent.getStringExtra("animal");

        image = findViewById(R.id.image);
        resultat = findViewById(R.id.resultat);
        music = MediaPlayer.create(this, R.raw.champions);
        music.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resultat.setText(temps + "s");
        switch(animal){
            case "lapin_carrot":
                image.setImageResource(R.drawable.rabbit);
                break;

            case "licorne_vomi":
                image.setImageResource(R.drawable.licorne_vomi);
                break;

            case "lapin":
                image.setImageResource(R.drawable.cute_rabbit);
                break;

            case "licorne_vener":
                image.setImageResource(R.drawable.licorne_vener);
                break;

            case "happy_unicorn":
                image.setImageResource(R.drawable.happy_unicorn);
                break;

            case "taupe":
                image.setImageResource(R.drawable.taupe_morte);


            default:
                image.setImageResource(R.drawable.unicorn_fart);
        }
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
