package com.kukuh.mp;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	private String urlregister = "http://192.168.43.99/mp/register";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		Button btnregdaftar = (Button)findViewById(R.id.btnregdaftar);
		btnregdaftar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				EditText edregemail = (EditText)findViewById(R.id.edregemail);
				EditText edregpassword = (EditText)findViewById(R.id.edregpassword);
				EditText edregnama = (EditText)findViewById(R.id.edregnama);
				//EditText edreggender = (EditText)findViewById(R.id.edreggender);
				//EditText edregalamat = (EditText)findViewById(R.id.edregalamat);
				
				int TIMEOUT_MILLISEC = 10000; 
				HttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpClient client = new DefaultHttpClient(httpParams);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3); 
				nameValuePairs.add(new BasicNameValuePair("nama", edregnama.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("email", edregemail.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("password", edregpassword.getText().toString()));
				
			    try {
			    	HttpPost request = new HttpPost(urlregister);
			    	request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					
					HttpResponse response = client.execute(request);
				}
				catch(Exception e){
					Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			    
			    Toast.makeText(getApplicationContext(), "Registrasi berhasil, silahkan periksa email anda " + edregemail.getText().toString() + " \n Silahkan ikuti langkah-langkahnya untuk melakukan aktivasi", Toast.LENGTH_LONG).show();
				//MainActivity.ma.RefreshList();
				
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_register, menu);
		return true;
	}

}
