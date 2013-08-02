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

public class EditProfilActivity extends Activity {
	private String urleditprofil = "http://192.168.43.99/mp/json/updatemember.php";
	EditText editprofilnamamember;
	EditText editprofilgendermember;
	EditText editprofilalamatmember;
	EditText editprofiltelponmember;
	EditText editprofilfbmember;
	EditText editprofiltwmember;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profil);
		
		editprofilnamamember = (EditText)findViewById(R.id.editprofilnamamember);
		editprofilgendermember = (EditText)findViewById(R.id.editprofilgendermember);
		editprofilalamatmember = (EditText)findViewById(R.id.editprofilalamatmember);
		editprofiltelponmember = (EditText)findViewById(R.id.editprofiltelponmember);
		editprofilfbmember = (EditText)findViewById(R.id.editprofilfbmember);
		editprofiltwmember = (EditText)findViewById(R.id.editprofiltwmember);
		
		editprofilnamamember.setText(TabLayoutActivity.nama);
		editprofilgendermember.setText(TabLayoutActivity.gender);
		editprofilalamatmember.setText(TabLayoutActivity.alamat);
		editprofiltelponmember.setText(TabLayoutActivity.no_telp);
		editprofilfbmember.setText(TabLayoutActivity.fb);
		editprofiltwmember.setText(TabLayoutActivity.tw);
		
		Button btnprofileditsimpan = (Button)findViewById(R.id.btnprofileditsimpan);
		btnprofileditsimpan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				int TIMEOUT_MILLISEC = 10000; 
				HttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpClient client = new DefaultHttpClient(httpParams);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6); 
				nameValuePairs.add(new BasicNameValuePair("nama", editprofilnamamember.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("gender", editprofilgendermember.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("alamat", editprofilalamatmember.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("no_telp", editprofiltelponmember.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("fb", editprofilfbmember.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("tw", editprofiltwmember.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("id_member", TabLayoutActivity.id));
				
				
			    try {
			    	HttpPost request = new HttpPost(urleditprofil);
			    	request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					
					HttpResponse response = client.execute(request);
				}
				catch(Exception e){
					Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			    
			    Toast.makeText(getApplicationContext(), "Profil berhasil diedit", Toast.LENGTH_LONG).show();
				//MainActivity.ma.RefreshList();
				
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_edit_profil, menu);
		return true;
	}

}
