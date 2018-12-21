package com.exemple.carpierschmidt.promob_jeu;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class solo_question extends AppCompatActivity {
    public String envoie = "";
    public int premier_ch;
    public int deuxieme_ch;
    public String calculer;
    public String result;
    public TextView calcul;
    public TextView rep;
    public int count = 0;
    boolean routine;
    long depart=0;
    Chronometer chrono;
    int nb = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_question);

        aleatoire();

        chrono = findViewById(R.id.chrono);
        Log.d("real time ", "temps: " + SystemClock.elapsedRealtime());
        depart = SystemClock.elapsedRealtime();
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.start();

        calcul = findViewById(R.id.calcul);
        calcul.setText(calculer);

        rep = (TextView) findViewById(R.id.rep);
        rep.setText(0 + "/" + (nb+1));

        Intent intent = getIntent();
        routine = intent.getBooleanExtra("routine", false);


    }

    public void sendMessage(View view) {
        if (count < nb){
            jeu();
        }else {
            ecran_suivant();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        chrono.stop();
    }

    public void jeu(){
        EditText reponse = findViewById(R.id.reponse);
        envoie = reponse.getText().toString();
        reponse.getText().clear();
        if (envoie.equals(result)){
            count += 1;
            rep.setText(count + "/" + (nb+1));
            aleatoire();
            calcul.setText(calculer);
        }else {
            Toast.makeText(this, "Nul Mitroglou", Toast.LENGTH_SHORT).show();
        }
    }

    public void ecran_suivant(){
        if(routine){
            chrono.stop();
            long temps = (SystemClock.elapsedRealtime()-depart)/1000;
            Intent intent = new Intent(this, solo_touch.class);
            intent.putExtra("routine",true );
            intent.putExtra("chrono",temps);
            intent.putExtra("depart",depart);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, solo_gagne.class);
            long temps = (SystemClock.elapsedRealtime()-depart)/1000;
            intent.putExtra("temps",temps);
            intent.putExtra("animal", "lapin_carrot");
            startActivity(intent);
        }
    }

    public void aleatoire(){
        premier_ch = new Random().nextInt(15) + 5;
        deuxieme_ch = new Random().nextInt(15) + 5;
        calculer  = premier_ch + " + " + deuxieme_ch + " = ?";
        result = String.valueOf(premier_ch + deuxieme_ch);
    }
}