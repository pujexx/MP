package com.kukuh.mp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public static String userlogin = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		Button btnloginlogin = (Button)findViewById(R.id.btnloginlogin);
		btnloginlogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				EditText edloginemail = (EditText)findViewById(R.id.edloginemail);
				EditText edloginpassword = (EditText)findViewById(R.id.edloginpassword);
				
				int TIMEOUT_MILLISEC = 10000; 
				HttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpClient client = new DefaultHttpClient(httpParams);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2); 
				nameValuePairs.add(new BasicNameValuePair("email", edloginemail.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("password", edloginpassword.getText().toString()));
				
			    try {
			    	HttpPost request = new HttpPost("http://192.168.43.99/mp/json/validasilogin.php");
			    	request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					
					HttpResponse response = client.execute(request);
					
					String hasil = request(response).trim().replace("\n", "");
					String cek = "Login Sukses";
					if (hasil.equals(cek))
					{
						LoginActivity.userlogin = edloginemail.getText().toString();
						Intent i = new Intent(LoginActivity.this, TabLayoutActivity.class);
						i.putExtra("email", edloginemail.getText().toString());
						startActivity(i);
					}

					Toast.makeText(getApplicationContext(), hasil, Toast.LENGTH_LONG).show();
				}
				catch(Exception e){
					Toast.makeText(getApplicationContext(), "Tidak ada koneksi Internet", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
				
			}
		});
		
		Button btnloginregister = (Button)findViewById(R.id.btnloginregister);
		btnloginregister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(i);
			}
		});
	}
	/**
	 * Method untuk Menenrima data dari server
	 * @param response
	 * @return
	 */
	public static String request(HttpResponse response){
        String result = "";
        try{
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                str.append(line + "\n");
            }
            in.close();
            result = str.toString();
        }catch(Exception ex){
            result = "Error";
        }
        return result;
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

}
