package com.kukuh.mp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button btncari = (Button)findViewById(R.id.btncari);
        btncari.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this,CariActivity.class);
				startActivity(i);
			}
		});
        
        Button btnhome = (Button)findViewById(R.id.btnhome);
        btnhome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, BerandaActivity.class);
				startActivity(i);
			}
		});
        
        Button btnlogin = (Button)findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(i);
			}
		});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
         
        switch (item.getItemId())
        {
        case R.id.menusetting:
            // Single menu item is selected do something
            // Ex: launching new activity/screen or show alert message
            Toast.makeText(MainActivity.this, "Setting is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menulogout:
            Toast.makeText(MainActivity.this, "Logout is Selected", Toast.LENGTH_SHORT).show();
            return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }       
}
