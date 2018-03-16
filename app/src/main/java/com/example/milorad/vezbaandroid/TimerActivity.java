package com.example.milorad.vezbaandroid;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {

    protected int counter;
    protected TextView timerText;
    protected boolean timerRunning;
    protected Button startButton;
    protected Button stopButton;

    protected long startedAt;
    protected long lastStopped;


    private static long UPDATE_EVERY = 200;

    protected Handler handler;
    protected UpdateTimer updateTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        counter = 0;
        timerText = findViewById(R.id.timer_display);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        timerRunning = false;


        startedAt = 0;
        lastStopped = 0;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void TimerStart(View view){
        Log.e("TIMER", "Timer start");
        timerRunning = true;

        startedAt = System.currentTimeMillis();

        setTimeDisplay();
        enableButtons();

        handler = new Handler();
        updateTimer = new UpdateTimer();
        handler.postDelayed(updateTimer, UPDATE_EVERY);
    }

    public void TimerStop(View view){
        Log.e("TIMER", "Timer stop");
        timerRunning = false;

        lastStopped = System.currentTimeMillis();

        setTimeDisplay();
        enableButtons();

        handler.removeCallbacks(updateTimer);
        handler = null;
    }

    private void enableButtons(){
        Log.e("ENB_BTN", "pozvan enable buttons");
        startButton.setEnabled(!timerRunning);
        stopButton.setEnabled(timerRunning);
    }

    protected void setTimeDisplay(){

        String display;
        long currTime;
        long diff;
        long seconds;
        long minutes;
        long hours;

        if (timerRunning){
            currTime = System.currentTimeMillis();
        }
        else{
            currTime = lastStopped;
        }

        diff = currTime - startedAt;

        if (diff < 0){
            diff = 0;
        }

        seconds = diff / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        minutes = minutes % 60;
        seconds = seconds % 60;

        display = String.format("%d", hours) + ":"
                + String.format("%02d", minutes) + ":"
                + String.format("%02d", seconds);

        timerText.setText(display);
    }

    class UpdateTimer implements Runnable{

        @Override
        public void run() {
            setTimeDisplay();
            if (handler != null) {
                handler.postDelayed(this, UPDATE_EVERY);
            }
        }
    }

}
