package com.kukuh.mp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import android.content.Intent;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class HasilcariActivity extends Activity {
	String[] arrIDMerk; 
	String[] arrMerk;
	String[] arrIDKategori;
	String[] arrKategori;
	String[] arrIDNopol;
	String[] arrNopol;
	private JSONObject jObject;
	private String jsonResult ="";
	private String url = "http://192.168.43.99/mp/json/dataiklan.php";
	private String urlmerk = "http://192.168.43.99/mp/json/datamerk.php";
	private String urlnopol = "http://192.168.43.99/mp/json/datanopol.php";
	private String urlkategori = "http://192.168.43.99/mp/json/datakategori.php";
	Spinner spncarimerk;
	Spinner spncarinopol;
	Spinner spncarikategori;
	String[] daftarid;
	String[] daftarnama;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hasilcari);
		
		
		
		RefreshList(arrIDMerk[spncarimerk.getSelectedItemPosition()],arrIDKategori[spncarikategori.getSelectedItemPosition()],arrIDNopol[spncarinopol.getSelectedItemPosition()]);
	}
		/*	Button btncarimobil = (Button)findViewById(R.id.btncarimobil);
		btncarimobil.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//EditText edcarimobil = (EditText)findViewById(R.id.btncari);
				//RefreshList(edcarimobil.getText().toString());
				RefreshList(arrIDMerk[spncarimerk.getSelectedItemPosition()],arrIDKategori[spncarikategori.getSelectedItemPosition()],arrIDNopol[spncarinopol.getSelectedItemPosition()]); //.toString()); // edcarimobil.getText().toString());
				
			}
		});
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
		
	}*/

    public void RefreshList(String id_merk,String id_kategori,String id_nopol) { //String katakunci) {
    	
    	try {
        	//jsonResult = getRequest(url+"?katakunci="+katakunci); 
    		jsonResult = getRequest(url+"?id_merk="+id_merk); 
    		Toast.makeText(this,jsonResult, Toast.LENGTH_LONG).show();
    		Toast.makeText(this,url+"?id_merk="+id_merk, Toast.LENGTH_LONG).show();
    		
        	jObject = new JSONObject(jsonResult);
			JSONArray menuitemArray = jObject.getJSONArray("iklan");

			daftarid = new String[menuitemArray.length()];
			daftarnama = new String[menuitemArray.length()];
			
			for (int i = 0; i < menuitemArray.length(); i++)
			{
				Toast.makeText(this,menuitemArray.getJSONObject(i).getString("id_iklan").toString(), Toast.LENGTH_LONG).show();
				daftarid[i] = menuitemArray.getJSONObject(i).getString("id_iklan").toString();
				daftarnama[i] = menuitemArray.getJSONObject(i).getString("nama_merk").toString();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListView listcari = (ListView)findViewById(R.id.listcari);
		listcari.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftarnama));
        
		listcari.setSelected(true);
		listcari.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				/*final String selectionid = daftarid[arg2]; 
				final String selectionnama = daftarnama[arg2]; 
				final String selectionlatitude = daftarlatitude[arg2]; 
				final String selectionlongitude = daftarlongitude[arg2]; 
		    	final CharSequence[] dialogitem = {"Edit", "Delete"};
		    	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		        builder.setTitle("Pilih ?");
		        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						switch(item){
						case 0 :
							Intent i = new Intent(getApplicationContext(), EditActivity.class);  
							i.putExtra("id", selectionid);
							i.putExtra("nama", selectionnama);
							i.putExtra("latitude", selectionlatitude);
							i.putExtra("longitude", selectionlongitude);
					    	startActivity(i);
							
							break;
						case 1 :
							getRequest(url2 + "?id=" + selectionid);
							RefreshList();
							
							break;
						}
					}
				});
		        builder.create().show();*/
			}
		});

        ((ArrayAdapter)listcari.getAdapter()).notifyDataSetInvalidated();
        
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


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

}
