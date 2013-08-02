package com.kukuh.mp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class TimeAlarm extends BroadcastReceiver {
	private JSONObject jObject;
	private String jsonResult ="";
	//private String url = "http://192.168.43.99/json/getpesan.php";
	private String url = "http://192.168.43.99/mp/json/getpesan.php";
	String[] daftardeskripsi;
	String[] daftartipe;
	NotificationManager nm;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		
		nm = (NotificationManager) arg0
	    .getSystemService(Context.NOTIFICATION_SERVICE);
		
		try {
        	jsonResult = getRequest(url + "?id_member=" +arg1.getStringExtra("id")); 
        	
        	jObject = new JSONObject(jsonResult);
			JSONArray menuitemArray = jObject.getJSONArray("iklan");

			daftardeskripsi = new String[menuitemArray.length()];
			daftartipe = new String[menuitemArray.length()];
			
			if (menuitemArray.length() > 0)
			{
				daftardeskripsi[0] = menuitemArray.getJSONObject(0).getString("deskripsi").toString();
				daftartipe[0] = menuitemArray.getJSONObject(0).getString("tipe").toString();
				
				CharSequence from = "Mobil Pekalongan";
				  CharSequence message = "Iklan Terbaru : Mobil " + daftartipe[0] + " : " + daftardeskripsi[0]; 
				  Intent notificationIntent = new Intent(arg0, MainActivity.class);
				  PendingIntent contentIntent = PendingIntent.getActivity(arg0, 0,
				    notificationIntent, 0);
				  Notification notif = new Notification(R.drawable.logo,
				    "Update Iklan MobilPekalongan ", System.currentTimeMillis());
				  notif.setLatestEventInfo(arg0, from, message, contentIntent);
				  nm.notify(1, notif);
				  //Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				  //builder.setSound(alarmSound);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	}
	
	/**
	 * Method untuk Mengirimkan data ke server
	 */
	public String getRequest(String Url){

		String sret="";
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(Url);
		try{
			HttpResponse response = client.execute(request);
			sret =request(response);

		}catch(Exception ex){
			//Toast.makeText(this,"Gagal "+sret, Toast.LENGTH_SHORT).show();
		}
		return sret;

	}
	/**
	 * Method untuk Menerima data dari server
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

}