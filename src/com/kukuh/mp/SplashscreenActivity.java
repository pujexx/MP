package com.kukuh.mp;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashscreenActivity  extends Activity {	
	protected boolean _active = true;
	protected int _splashTime = 5000; 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        
        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {
                    // do nothing
                } finally {
                    finish();
                    Intent i;
                    i = new Intent(SplashscreenActivity.this, MainActivity.class);
                    startActivity(i); 
                   // stop();
                   
                }
            }
        };
        splashTread.start();
    }
}

