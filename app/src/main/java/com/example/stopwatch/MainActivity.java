package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;



import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private int milliseconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            milliseconds = savedInstanceState.getInt("milliseconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("milliseconds", milliseconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    //Start the stopwatch running when the Start button is clicked.
    public void onClickStart(View view) {
        running = true;
    }

    //Stop the stopwatch running when the Stop button is clicked.
    public void onClickStop(View view) {
        running = false;
    }

    //Reset the stopwatch when the Reset button is clicked.
    public void onClickReset(View view) {
        final TextView lapView = (TextView)findViewById(R.id.lapText);
        lapView.setText("");
        running = false;
        milliseconds= 0;

    }

    public void onClickLap(View view)
    {
        final TextView lapView = (TextView)findViewById(R.id.lapText);
        final TextView timeView = (TextView)findViewById(R.id.time_view);

        lapView.setText(timeView.getText());

    }

    //Sets the number of seconds on the timer.
    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = milliseconds/360000;
                int minutes = (milliseconds%216000)/3600;
                int secs = (milliseconds%3600)/60;
                int milisecs = (milliseconds%60);
                String time = String.format(Locale.getDefault(),
                        "%02d:%02d:%02d:%02d", hours, minutes, secs,milisecs);
                timeView.setText(time);
                if (running) {
                    milliseconds+=1;
                }
                handler.postDelayed(this,0);
            }
        });
    }
}