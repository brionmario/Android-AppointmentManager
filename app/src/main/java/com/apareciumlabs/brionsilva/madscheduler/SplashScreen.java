package com.apareciumlabs.brionsilva.madscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {


    private final int DURATION =3000;
    private Thread mSplashThread;
    private ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        mSplashThread = new Thread() {

            @Override
            public void run() {
                synchronized (this) {
                    try {
                        // wait(1000);
                        progressBar.setProgress(25);
                        // wait(2000);
                        progressBar.setProgress(50);
                        //  wait(1500);
                        progressBar.setProgress(80);
                        wait(DURATION);
                    } catch (InterruptedException e) {
                    } finally {
                        progressBar.setProgress(100);
                        finish();
                        Intent intent = new Intent(getBaseContext(),
                                HomeScreen.class);
                        startActivity(intent);
                    }
                }
            }

        };
        mSplashThread.start();
    }


}

