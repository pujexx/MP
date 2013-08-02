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
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class TabLayoutActivity extends TabActivity {
	private JSONObject jObject;
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
	public static String email;
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
	private ProgressDialog loading;
	private TextView txthomeuser;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        txthomeuser = (TextView)findViewById(R.id.txthomeuser);
        
        //inisialisasi loading
        loading = new ProgressDialog(this);
        loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loading.setMessage("Loading....");
        
        email = getIntent().getStringExtra("email");
        
        //Toast.makeText(getApplicationContext(), "OKKK", Toast.LENGTH_LONG).show();
    	//RefreshProfil();
        
        new AsyncRefreshProfile().execute();
        
    	Toast.makeText(getApplicationContext(), TabLayoutActivity.id, Toast.LENGTH_LONG).show();
    	
        setRepeatingAlarm();
        
        
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
    
        TextView txthomeuser = (TextView)findViewById(R.id.txthomeuser);
    	txthomeuser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(TabLayoutActivity.this, ProfilMemberActivity.class);
				startActivity(i);
			}
		});
    	
    	ImageButton imgupload = (ImageButton)findViewById(R.id.actionupload);
    	imgupload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(TabLayoutActivity.this, UploadIklanActivity.class);
				startActivity(i);
			}
		});
    	
    	ImageButton actioncari = (ImageButton)findViewById(R.id.actioncari);
    	actioncari.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i = new Intent(TabLayoutActivity.this, CariActivity.class);
				startActivity(i);
			}
		});
    	
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) 
            {
                //Ask the user if they want to quit
                new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Really Exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() 
                    {
                        public void onClick(DialogInterface dialog, int which) 
                            {
                             Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
  
                            finish();
                            }
                    })
                .setNegativeButton("No", null)
                .show();
                return true;
            }
        else 
            {
            return super.onKeyDown(keyCode, event);
            }
    }
    public void setRepeatingAlarm() {
    	Intent intent = new Intent(getBaseContext(), TimeAlarm.class);
    	intent.putExtra("id", TabLayoutActivity.id);
    	pendingIntentRepeat = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
		AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), (10 * 1000), pendingIntentRepeat);
		//TimeAlarm.idm = TabLayoutActivity.id;
    	/*
    	  Intent intent = new Intent(this, TimeAlarm.class);
    	  
    	  //PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
    	  pendingIntentRepeat = PendingIntent.getBroadcast(this, 0,
    	    intent, PendingIntent.FLAG_CANCEL_CURRENT);
    	  am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
    	    (5 * 1000), pendingIntentRepeat);
    	    */
    }
    
	 public void RefreshProfil() {
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
    	
    	
    	
    }
	 public String getRequest(String Url){

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
		
		
		class AsyncRefreshProfile extends AsyncTask<String, String, String>{
			
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
				loading.show();
			}

			@Override
			protected String doInBackground(String... params) {
				
				// TODO Auto-generated method stub
				RefreshProfil();
				return null;
			}
			

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				txthomeuser.setText(nama);
				loading.dismiss();
			}
			
		}
	}