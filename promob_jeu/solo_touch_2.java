package com.exemple.carpierschmidt.promob_jeu;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import static android.view.DragEvent.ACTION_DRAG_ENDED;
import static android.view.DragEvent.ACTION_DRAG_ENTERED;
import static android.view.DragEvent.ACTION_DRAG_LOCATION;
import static android.view.DragEvent.ACTION_DRAG_STARTED;
import static android.view.DragEvent.ACTION_DROP;

public class solo_touch_2 extends AppCompatActivity {

    public static ImageView truck, rock1, rock2, rock3, rock4, rock5, rock6, big_rock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solo_touch_2);
        initControls();
    }

    private void initControls() {
        truck = findViewById(R.id.truck);
        rock1 = findViewById(R.id.rock1);
        rock1.setOnTouchListener(new solo_touch_2.MyTouchListener());
        rock2 = findViewById(R.id.rock2);
        rock2.setOnTouchListener(new solo_touch_2.MyTouchListener());
        rock3 = findViewById(R.id.rock3);
        rock3.setOnTouchListener(new solo_touch_2.MyTouchListener());
        rock4 = findViewById(R.id.rock4);
        rock4.setOnTouchListener(new solo_touch_2.MyTouchListener());
        rock5 = findViewById(R.id.rock5);
        rock5.setOnTouchListener(new solo_touch_2.MyTouchListener());
        rock6 = findViewById(R.id.rock6);
        rock6.setOnTouchListener(new solo_touch_2.MyTouchListener());
        big_rock = findViewById(R.id.big_rock);
        big_rock.setOnTouchListener(new solo_touch_2.MyTouchListener());
        truck.setOnDragListener(new solo_touch_2.MyDragListener());
    }

    private final class MyTouchListener implements View.OnTouchListener {

        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (view == big_rock) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                        view.startDrag(data, shadowBuilder, view, 0);
                        view.setVisibility(View.INVISIBLE);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    view.setVisibility(View.INVISIBLE);
                    return true;
                }

            } else {
                return false;
            }
        }
    }


    private class MyDragListener implements View.OnDragListener {

        int resAct = R.drawable.truck_full;
        int resNormal = R.drawable.truck;

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    ((ImageView) v).setImageResource(resAct);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    ((ImageView) v).setImageResource(resNormal);
                    break;
                case DragEvent.ACTION_DROP:
                    ((ImageView) v).setImageResource(resAct);
                    // Display toast
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if (event.getResult()) { // drop succeeded
                        ((ImageView) v).setImageResource(resAct);
                        resNormal = resAct;
                    } else { // drop failed
                        final View draggedView = (View) event.getLocalState();
                        draggedView.post(new Runnable() {
                            @Override
                            public void run() {
                                draggedView.setVisibility(View.VISIBLE);
                            }
                        });
                        ((ImageView) v).setImageResource(resNormal);
                    }
                default:
                    break;
            }
            return true;
        }

    }
}



