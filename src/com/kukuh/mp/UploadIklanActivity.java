package com.kukuh.mp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UploadIklanActivity extends Activity {
	String[] arrIDMerk; 
	String[] arrMerk;
	String[] arrIDModel;
	String[] arrModel;
	String[] arrIDNopol;
	String[] arrNopol;
	String[] arrIDKategori;
	String[] arrKategori;
	private JSONObject jObject;
	private String jsonResult ="";
	private String urlmerk = "http://192.168.43.99/mp/json/datamerk.php";
	private String urlmodel = "http://192.168.43.99/mp/json/datamodel.php";
	private String urlnopol = "http://192.168.43.99/mp/json/datanopol.php";
	private String urlkategori = "http://192.168.43.99/mp/json/datakategori.php";
	private String urlwebaddiklan = "http://192.168.43.99/mp/json/addiklan.php";
	Spinner spnuploadmerk;
	Spinner spnuploadmodel;
	Spinner spnuploadnopol;
	Spinner spnuploadkategori;
	
	ImageView imgiklanbrowse;
	TextView tviklanbrowse;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_iklan);
		
		imgiklanbrowse = (ImageView)findViewById(R.id.imgiklanbrowse);
		tviklanbrowse = (TextView)findViewById(R.id.tviklanbrowse);
		
		RefreshListMerk();
		
		spnuploadmerk = (Spinner) findViewById(R.id.spnuploadmerk);
		ArrayAdapter<String> adapter;
		adapter = adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrMerk);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnuploadmerk.setAdapter(adapter);

		RefreshListModel();
		
		spnuploadmodel = (Spinner) findViewById(R.id.spnuploadmodel);
		ArrayAdapter<String> adaptermodel;
		adaptermodel = adaptermodel = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrModel);
		adaptermodel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnuploadmodel.setAdapter(adaptermodel);

		RefreshListNopol();
		
		spnuploadnopol = (Spinner) findViewById(R.id.spnuploadnopol);
		ArrayAdapter<String> adapternopol;
		adapternopol = adapternopol = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrNopol);
		adapternopol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnuploadnopol.setAdapter(adapternopol);

		RefreshListKategori();
		
		spnuploadkategori = (Spinner) findViewById(R.id.spnuploadkategori);
		ArrayAdapter<String> adapterkategori;
		adapterkategori = adapterkategori = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrKategori);
		adapterkategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnuploadkategori.setAdapter(adapterkategori);
		
		Button btnuploadiklansimpan = (Button)findViewById(R.id.btnuploadiklansimpan);
		btnuploadiklansimpan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				EditText adduploadtipe = (EditText)findViewById(R.id.adduploadtipe);
				EditText adduploadtahun = (EditText)findViewById(R.id.adduploadtahun);
				EditText adduploadtanganke = (EditText)findViewById(R.id.adduploadtanganke);
				EditText adduploadharga = (EditText)findViewById(R.id.adduploadharga);
				EditText adduploadbahanbakar = (EditText)findViewById(R.id.adduploadbahanbakar);
				EditText adduploadwarna = (EditText)findViewById(R.id.adduploadwarna);
				EditText adduploadmesincc = (EditText)findViewById(R.id.adduploadmesincc);
				EditText adduploadtransmisi = (EditText)findViewById(R.id.adduploadtransmisi);
				EditText adduploadkilometer = (EditText)findViewById(R.id.adduploadkilometer);
				EditText adduploadpajaktahun = (EditText)findViewById(R.id.adduploadpajaktahun);
				EditText adduploadpajakbulan = (EditText)findViewById(R.id.adduploadpajakbulan);
				EditText adduploaddeskripsi = (EditText)findViewById(R.id.adduploaddeskripsi);
				
				int TIMEOUT_MILLISEC = 10000; 
				HttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
				HttpClient client = new DefaultHttpClient(httpParams);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(18); 
				nameValuePairs.add(new BasicNameValuePair("id_member", TabLayoutActivity.id)); 
				nameValuePairs.add(new BasicNameValuePair("id_merk", arrIDMerk[spnuploadmerk.getSelectedItemPosition()])); 
				nameValuePairs.add(new BasicNameValuePair("id_model", arrIDModel[spnuploadmodel.getSelectedItemPosition()]));
				nameValuePairs.add(new BasicNameValuePair("id_nopol", arrIDNopol[spnuploadnopol.getSelectedItemPosition()])); 
				nameValuePairs.add(new BasicNameValuePair("id_kategori", arrIDKategori[spnuploadkategori.getSelectedItemPosition()]));
				nameValuePairs.add(new BasicNameValuePair("id_foto", ""));
				nameValuePairs.add(new BasicNameValuePair("status", "P"));//edtahunmobil.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("tipe", adduploadtipe.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("tahun", adduploadtahun.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("tgn_ke", adduploadtanganke.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("harga", adduploadharga.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("bhn_bkr", adduploadbahanbakar.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("transmisi", adduploadtransmisi.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("warna", adduploadwarna.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("mesin_cc", adduploadmesincc.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("kilometer", adduploadkilometer.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("pjk_th", adduploadpajaktahun.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("bln_pjk", adduploadpajakbulan.getText().toString()));
				nameValuePairs.add(new BasicNameValuePair("deskripsi", adduploaddeskripsi.getText().toString()));
				
				
			    try {
			    	HttpPost request = new HttpPost(urlwebaddiklan);
			    	request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
					
					HttpResponse response = client.execute(request);
				}
				catch(Exception e){
					Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			    
			  try
	        	{
	        		uploaderiklan upl = new uploaderiklan();
	        		upl.setUrlAndFile("http://192.168.43.99/mp/json/receiver-upload-iklan.php", tviklanbrowse.getText().toString()); // "/sdcard/external_sd/nexus7.jpg"); //,(TextView)(findViewById(R.id.TextView01))); 
	        		//upl.setUrlAndFile("http://10.0.2.2/web-receiver-upload/receiver-upload.php", Environment.getExternalStorageDirectory().getAbsolutePath() + "external_sd/nexus7.jpg",(TextView)(findViewById(R.id.TextView01))); 
	        		//android.os.Environment.getExternalStorageDirectory() + "/nexus7.jpg";
	        		
	        		upl.execute();
	        	}
	        	catch(Exception e)
	        	{
	        	}    
			    
			    Toast.makeText(getApplicationContext(), "Iklan Berhasil Terpasang", Toast.LENGTH_LONG).show();
				//MainActivity.ma.RefreshList();
				
				finish();
			}
		});
		
		Button btnuploadiklanbrowse = (Button)findViewById(R.id.btnuploadiklanbrowse);
		btnuploadiklanbrowse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"),0);
			}
		});

	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch(requestCode) {
    	case 0:
    	    if(resultCode == RESULT_OK){  
    	    	imgiklanbrowse.setImageURI(data.getData());
    	    	tviklanbrowse.setText(getRealPathFromURI(data.getData()));
    	    }
    	break; 
    	}
        super.onActivityResult(requestCode, resultCode, data);
   }
    
    public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		android.database.Cursor cursor = managedQuery(contentUri, proj, // Which columns to return
				null,
				null,
				null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}
	public void RefreshListMerk() {
    	try {
        	jsonResult = getRequest(urlmerk); 
        	
        	jObject = new JSONObject(jsonResult);
			JSONArray menuitemArray = jObject.getJSONArray("merk");

			arrMerk = new String[menuitemArray.length()];
			arrIDMerk = new String[menuitemArray.length()];
			
			for (int i = 0; i < menuitemArray.length(); i++)
			{
				arrMerk[i] = menuitemArray.getJSONObject(i).getString("nama_merk").toString();
				arrIDMerk[i] = menuitemArray.getJSONObject(i).getString("id_merk").toString();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void RefreshListModel() {
    	try {
        	jsonResult = getRequest(urlmodel); 
        	
        	jObject = new JSONObject(jsonResult);
			JSONArray menuitemArray = jObject.getJSONArray("model");

			arrModel = new String[menuitemArray.length()];
			arrIDModel = new String[menuitemArray.length()];
			
			for (int i = 0; i < menuitemArray.length(); i++)
			{
				arrModel[i] = menuitemArray.getJSONObject(i).getString("nama_model").toString();
				arrIDModel[i] = menuitemArray.getJSONObject(i).getString("id_model").toString();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void RefreshListNopol() {
    	try {
        	jsonResult = getRequest(urlnopol); 
        	
        	jObject = new JSONObject(jsonResult);
			JSONArray menuitemArray = jObject.getJSONArray("nopol");

			arrNopol = new String[menuitemArray.length()];
			arrIDNopol = new String[menuitemArray.length()];
			
			for (int i = 0; i < menuitemArray.length(); i++)
			{
				arrNopol[i] = menuitemArray.getJSONObject(i).getString("nopolisi").toString();
				arrIDNopol[i] = menuitemArray.getJSONObject(i).getString("id_nopol").toString();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void RefreshListKategori() {
    	try {
        	jsonResult = getRequest(urlkategori); 
        	
        	jObject = new JSONObject(jsonResult);
			JSONArray menuitemArray = jObject.getJSONArray("kategori");

			arrKategori = new String[menuitemArray.length()];
			arrIDKategori = new String[menuitemArray.length()];
			
			for (int i = 0; i < menuitemArray.length(); i++)
			{
				arrKategori[i] = menuitemArray.getJSONObject(i).getString("nama_kategori").toString();
				arrIDKategori[i] = menuitemArray.getJSONObject(i).getString("id_kategori").toString();
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
			Toast.makeText(this,"Gagal "+sret, Toast.LENGTH_SHORT).show();
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

	public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK) 
            {
                //Ask the user if they want to quit
                new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Apakah Anda Yakin Ingin Membatalkan Pasang Iklan ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() 
                    {
                        public void onClick(DialogInterface dialog, int which) 
                            {
                             finish();
                            }
                    })
                .setNegativeButton("Tidak", null)
                .show();
                return true;
            }
        else 
            {
            return super.onKeyDown(keyCode, event);
            }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.layout.menu, menu);
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
	            Toast.makeText(UploadIklanActivity.this, "Setting is Selected", Toast.LENGTH_SHORT).show();
	            return true;
	 
	        case R.id.menulogout:
	            //Toast.makeText(HomeActivity.this, "Logout is Selected", Toast.LENGTH_SHORT).show();
	        	new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Apakah Anda Yakin Ingin Logout ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() 
                    {
                        public void onClick(DialogInterface dialog, int which) 
                            {
                             Intent intent = new Intent(UploadIklanActivity.this, MainActivity.class);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
  
                            finish();
                            }
                    })
                .setNegativeButton("Tidak", null)
                .show();
                return true;
	        	
	            
	            
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }  

}
