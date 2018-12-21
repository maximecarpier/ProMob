package com.exemple.carpierschmidt.promob_jeu;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static android.graphics.Color.LTGRAY;

public class solo_question_2 extends AppCompatActivity {

    Button rep1,rep2,rep3,rep4;
    TextView question;
    Chronometer chrono;
    String[] question_select;
    int nb = 5;

    String[] question1 = {"Quelle est la couleur des licornes ?", "Blanche et Arc-en-ciel", "Blanc et rose", "On s'en fout", "Noir et Blanc (Wesh comme les zèbres)", "1" };
    String[] question2 = {"Pourquoi les licornes ont disparues ?", "Sperme de mauvaise qualité", "Population féminine", "Deux femelles dans l'arche de Noé", "Elles n'ont pas disparues de nos coeurs","4"};
    String[] question3 = {"Que mangent les lapins ? ", "Leurs oreilles", "Des carrotes", "Ils ne mangent pas", "Des fraises (comme les femmes enceintes)", "2"};
    String[] question4 = {"Comment sont apparues les licorne ? ", "Darwin disait l'évolution","Accouplement entre âne et rhinocéros", "personne ne sait", "tombées du ciel", "2"};
    String[] question5 = {"A quoi sert la corne des licornes ?", "Se défendre", "Chasser", "Plaisirs sexuelles étranges", "faire les malines", "3"};


    int rep;

    boolean routine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_question_2);

        rep1 = findViewById(R.id.rep1);
        rep2 = findViewById(R.id.rep2);
        rep3 = findViewById(R.id.rep3);
        rep4 = findViewById(R.id.rep4);

        chrono = findViewById(R.id.chrono);
        long base_chrono = getIntent().getLongExtra("chrono",0);
        chrono.setBase(SystemClock.elapsedRealtime() - (0* 60000 + base_chrono * 1000));

        question = findViewById(R.id.question);

        Intent intent = getIntent();
        routine = intent.getBooleanExtra("routine", false);
        base_chrono = intent.getLongExtra("chrono",0);
        chrono.setBase(SystemClock.elapsedRealtime() - (0* 60000 + base_chrono * 1000));
        chrono.start();

        next_question();
    }

    public void reponse1(View view){
        if(rep == 1) {
            rep1.setBackgroundColor(0xff00ff00);
            nb -= 1;
            next();
        }
            else {
            rep1.setBackgroundColor(0xffff0000);
        }

    }
    public void reponse2(View view){
        if(rep == 2){
            rep2.setBackgroundColor(0xff00ff00);
            nb -= 1;
            next();
        }
            else {
            rep2.setBackgroundColor(0xffff0000);
        }

    }
    public void reponse3(View view){
        if(rep == 3) {
            nb -= 1;
            next();
            rep3.setBackgroundColor(0xff00ff00);
        }
          else {
            rep3.setBackgroundColor(0xffff0000);
        }


    }
    public void reponse4(View view){
        if(rep == 4){
            rep4.setBackgroundColor(0xff00ff00);
            nb -= 1;
            next();
        }

          else rep4.setBackgroundColor(0xffff0000);

    }

    public void next(){
        Toast.makeText(solo_question_2.this, "bonne réponse", Toast.LENGTH_SHORT).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(nb == 1){
                    ecran_suivant();
                }else{
                    next_question();
                }
            }
        }, 2000);
    }


    @Override
    protected void onResume() {
        super.onResume();
        chrono.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        chrono.stop();
    }

    protected void next_question(){

        rep1.setBackgroundColor(LTGRAY);
        rep2.setBackgroundColor(LTGRAY);
        rep3.setBackgroundColor(LTGRAY);
        rep4.setBackgroundColor(LTGRAY);

        switch(nb){
            case 1:
                question_select = question1;
                break;
            case 2:
                question_select = question2;
                break;
            case 3:
                question_select = question3;
                break;
            case 4:
                question_select = question4;
                break;
            case 5:
                question_select = question5;
                break;

        }

        question.setText(question_select[0]);
        rep1.setText(question_select[1]);
        rep2.setText(question_select[2]);
        rep3.setText(question_select[3]);
        rep4.setText(question_select[4]);
        rep = Integer.parseInt(question_select[5]);

    }



    public void ecran_suivant(){
            if (routine) {
                long depart = getIntent().getLongExtra("depart", 0);
                Intent intent = new Intent(getBaseContext(), solo_touch_2.class);
                long temps = (SystemClock.elapsedRealtime()- depart)/1000;
                intent.putExtra("chrono", temps);
                intent.putExtra("routine", true);
                intent.putExtra("depart", depart);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getBaseContext(), solo_gagne.class);
                long temps = (SystemClock.elapsedRealtime()- chrono.getBase())/1000;
                intent.putExtra("temps", temps);
                intent.putExtra("animal", "licorne_vener");
                startActivity(intent);
            }
    }
}
