package com.kukuh.mp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class BerandaActivity extends TabActivity {
/*	private JSONObject jObject;
	private String jsonResult ="";
	private String url = "http://192.168.43.99/mp/json/dataiklan.php";
	private String url2 = "http://192.168.43.99/json/delkota.php";
	private String urlprofil = "http://192.168.43.99/mp/json/datamemberbyemail.php";
	private String urlwebfoto = "http://192.168.43.99/mp/uploads";
	String[] daftarid;
	String[] daftarmerk;
	String[] daftartahun;
	String[] daftarnopol;
	String[] daftarfoto;
	String[] daftarmesincc;
	String[] daftartransmisi;
	String[] daftarkilometer;
	String[] daftarwarna;
	String[] daftartanganke;
	String[] daftarbahanbakar;
	//public static String username;
	/*public static String email;
	public static String id;
	public static String nama;
	public static String alamat;
	public static String gender;
	public static String no_telp;
	public static String fb;
	public static String tw;
	AlarmManager am;
	PendingIntent pendingIntentRepeat;
	final static int RQS_1 = 1;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        
        //email = getIntent().getStringExtra("email");
        
        //Toast.makeText(getApplicationContext(), "OKKK", Toast.LENGTH_LONG).show();
    //	RefreshProfil();
        
    //	Toast.makeText(getApplicationContext(), BerandaActivity.id, Toast.LENGTH_LONG).show();
    	
      //  setRepeatingAlarm();
        
        
        TabHost tabHost = getTabHost();
        
        // Tab for Photos
        TabSpec homespec = tabHost.newTabSpec("");
        homespec.setIndicator("", getResources().getDrawable(R.drawable.icon_home));
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homespec.setContent(homeIntent);
        
        // Tab for Songs
        TabSpec baruspec = tabHost.newTabSpec("");
        // setting Title and Icon for the Tab
        baruspec.setIndicator("", getResources().getDrawable(R.drawable.icon_mobilbaru));
        Intent baruIntent = new Intent(this, MobilBaruActivity.class);
        baruspec.setContent(baruIntent);
        
        // Tab for Videos
        TabSpec bekasspec = tabHost.newTabSpec("");
        bekasspec.setIndicator("", getResources().getDrawable(R.drawable.icon_mobilbekas));
        Intent bekasIntent = new Intent(this, MobilBekasActivity.class);
        bekasspec.setContent(bekasIntent);
        
        // Adding all TabSpec to TabHost
        tabHost.addTab(homespec); // Adding photos tab
        tabHost.addTab(baruspec); // Adding songs tab
        tabHost.addTab(bekasspec); // Adding videos tab
    }
        /*TextView txthomeuser = (TextView)findViewById(R.id.txthomeuser);
    	txthomeuser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(BerandaActivity.this, ProfilMemberActivity.class);
				startActivity(i);
			}
		});
    	
    	ImageButton imgupload = (ImageButton)findViewById(R.id.actionupload);
    	imgupload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(BerandaActivity.this, UploadIklanActivity.class);
				startActivity(i);
			}
		});
    }
    
   /* public void setRepeatingAlarm() {
    	Intent intent = new Intent(getBaseContext(), TimeAlarm.class);
    	intent.putExtra("id", BerandaActivity.id);
    	pendingIntentRepeat = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
		AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (10 * 1000), pendingIntentRepeat);
		//TimeAlarm.idm = BerandaActivity.id;
		
    	/*
    	  Intent intent = new Intent(this, TimeAlarm.class);
    	  //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
    	  pendingIntentRepeat = PendingIntent.getBroadcast(this, 0,
    	    intent, PendingIntent.FLAG_CANCEL_CURRENT);
    	  am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
    	    (5 * 1000), pendingIntentRepeat);
    	    */
    
	/* public void RefreshProfil() {
    	try {
        	jsonResult = getRequest(urlprofil + "?email=" + email.replace("@", "_at_")); 
        	//Toast.makeText(getApplicationContext(), urlprofil + "?email=" + email, Toast.LENGTH_LONG).show();
        	
        	jObject = new JSONObject(jsonResult);
			JSONArray menuitemArray = jObject.getJSONArray("member");

			if (menuitemArray.length() > 0) 
			{
				//username = menuitemArray.getJSONObject(0).getString("username").toString();
				nama = menuitemArray.getJSONObject(0).getString("nama").toString();
				//Toast.makeText(getApplicationContext(), nama, Toast.LENGTH_LONG).show();
				id = menuitemArray.getJSONObject(0).getString("id_member").toString();
				alamat = menuitemArray.getJSONObject(0).getString("alamat").toString();
				gender = menuitemArray.getJSONObject(0).getString("gender").toString();
				no_telp = menuitemArray.getJSONObject(0).getString("no_telp").toString();
				fb = menuitemArray.getJSONObject(0).getString("fb").toString();
				tw = menuitemArray.getJSONObject(0).getString("tw").toString();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	TextView txthomeuser = (TextView)findViewById(R.id.txthomeuser);
    	txthomeuser.setText(nama);
    }*/
        
	/* public String getRequest(String Url){

			String sret="";
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(Url);
			try{
				HttpResponse response = client.execute(request);
				sret =request(response);

			}catch(Exception ex){
				Toast.makeText(this,"Gagal "+sret, Toast.LENGTH_SHORT).show();
			}
			return sret;

		}
	 
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
	 private Bitmap LoadImage(String URL, BitmapFactory.Options options)
		{       
			Bitmap bitmap = null;
			InputStream in = null;       
			try {
				in = OpenHttpConnection(URL);
				bitmap = BitmapFactory.decodeStream(in, null, options);
				in.close();
			} catch (IOException e1) {
			}
			return bitmap;               
		}

		private InputStream OpenHttpConnection(String strURL) throws IOException{
			InputStream inputStream = null;
			URL url = new URL(strURL);
			URLConnection conn = url.openConnection();

			try{
				HttpURLConnection httpConn = (HttpURLConnection)conn;
				httpConn.setRequestMethod("GET");
				httpConn.connect();

				if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
					inputStream = httpConn.getInputStream();
				}
			}
			catch (Exception ex)
			{
			}
			return inputStream;
		}     
		*/
	}