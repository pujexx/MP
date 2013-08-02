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

import com.kukuh.mp.HomeActivity.MyCustomAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CariActivity extends Activity {
	String[] arrIDMerk; 
	String[] arrMerk;
	String[] arrIDKategori;
	String[] arrKategori;
	String[] arrIDNopol;
	String[] arrNopol;
	String[] tahun;
	private JSONObject jObject;
	private String jsonResult ="";
	private String url = "http://192.168.43.99/mp/json/cariiklan.php";
	private String urlmerk = "http://192.168.43.99/mp/json/datamerk.php";
	private String urlnopol = "http://192.168.43.99/mp/json/datanopol.php";
	private String urlkategori = "http://192.168.43.99/mp/json/datakategori.php";
	private String urlwebfoto = "http://192.168.43.99/mp/uploads";
	Spinner spncarimerk;
	Spinner spncarinopol;
	Spinner spncarikategori;
	String[] daftarid;
	//String[] daftarnama;
	String[] daftarmerk;
	String[] daftartipe;
	String[] daftarharga;
	String[] daftartahun;
	String[] daftarnopol;
	String[] daftarfoto;
	String[] daftarmesincc;
	String[] daftartransmisi;
	String[] daftarkilometer;
	String[] daftarwarna;
	String[] daftartanganke;
	String[] daftarbahanbakar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cari);
		
		RefreshListMerk();
		
		spncarimerk = (Spinner) findViewById(R.id.spncarimerk);
		ArrayAdapter<String> adapter;
		adapter = adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrMerk);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spncarimerk.setAdapter(adapter);
		
       RefreshListNopol();
		
       spncarinopol = (Spinner) findViewById(R.id.spncarinopol);
		ArrayAdapter<String> adapternopol;
		adapternopol = adapternopol = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrNopol);
		adapternopol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spncarinopol.setAdapter(adapternopol);

		RefreshListKategori();
		
		spncarikategori = (Spinner) findViewById(R.id.spncarikategori);
		ArrayAdapter<String> adapterkategori;
		adapterkategori = adapterkategori = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, arrKategori);
		adapterkategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spncarikategori.setAdapter(adapterkategori);
		
		//RefreshList("");
		
		Button btncarimobil = (Button)findViewById(R.id.btncarimobil);
		btncarimobil.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EditText edcaritahun = (EditText)findViewById(R.id.edcaritahun);
				
				RefreshList(arrIDMerk[spncarimerk.getSelectedItemPosition()],arrIDKategori[spncarikategori.getSelectedItemPosition()],arrIDNopol[spncarinopol.getSelectedItemPosition()],edcaritahun.getText().toString()); //.toString()); // edcarimobil.getText().toString());
				
			}
		});
	}
	
	public void RefreshListMerk() {
    	try {
        	jsonResult = getRequest(urlmerk); 
        	
        	jObject = new JSONObject(jsonResult);
			JSONArray menuitemArray = jObject.getJSONArray("merk");

			arrMerk = new String[menuitemArray.length() + 1];
			arrIDMerk = new String[menuitemArray.length() + 1];
			
			arrMerk[0] = "Semua Merk";
			arrIDMerk[0] = "";
					
			for (int i = 0; i < menuitemArray.length(); i++)
			{
				arrMerk[i+1] = menuitemArray.getJSONObject(i).getString("nama_merk").toString();
				arrIDMerk[i+1] = menuitemArray.getJSONObject(i).getString("id_merk").toString();
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

			arrNopol = new String[menuitemArray.length() + 1];
			arrIDNopol = new String[menuitemArray.length() + 1];
			
			arrNopol[0] = "Semua Nopol";
			arrIDNopol[0] = "";
			
			for (int i = 0; i < menuitemArray.length(); i++)
			{
				arrNopol[i+1] = menuitemArray.getJSONObject(i).getString("nopolisi").toString();
				arrIDNopol[i+1] = menuitemArray.getJSONObject(i).getString("id_nopol").toString();
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

			arrKategori = new String[menuitemArray.length() + 1];
			arrIDKategori = new String[menuitemArray.length() + 1];
			
			arrKategori[0] = "Semua Kategori";
			arrIDKategori[0] = "";
			
			for (int i = 0; i < menuitemArray.length(); i++)
			{
				arrKategori[i+1] = menuitemArray.getJSONObject(i).getString("nama_kategori").toString();
				arrIDKategori[i+1] = menuitemArray.getJSONObject(i).getString("id_kategori").toString();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

    public void RefreshList(String id_merk,String id_kategori,String id_nopol,String tahun) {
    	
    	try {
    		jsonResult = getRequest(url+"?id_kategori="+id_kategori+"&id_merk="+id_merk+"&tahun="+tahun+"&id_nopol="+id_nopol);
    		//Toast.makeText(this,jsonResult, Toast.LENGTH_LONG).show();
    		
        	jObject = new JSONObject(jsonResult);
			JSONArray menuitemArray = jObject.getJSONArray("iklan");

			daftarid = new String[menuitemArray.length()];
			daftarmerk = new String[menuitemArray.length()];
			daftartipe = new String[menuitemArray.length()];
			daftarharga = new String[menuitemArray.length()];
			daftartahun = new String[menuitemArray.length()];
			daftarnopol = new String[menuitemArray.length()];
			daftarfoto = new String[menuitemArray.length()];
			daftarwarna = new String[menuitemArray.length()];
			daftartransmisi = new String[menuitemArray.length()];
			daftarkilometer = new String[menuitemArray.length()];
			daftarmesincc = new String[menuitemArray.length()];
			daftartanganke = new String[menuitemArray.length()];
			daftarbahanbakar = new String[menuitemArray.length()];
			
			for (int i = 0; i < menuitemArray.length(); i++)
			{
				Toast.makeText(this,menuitemArray.getJSONObject(i).getString("id_iklan").toString(), Toast.LENGTH_LONG).show();
				daftarid[i] = menuitemArray.getJSONObject(i).getString("id_iklan").toString();
				daftarmerk[i] = menuitemArray.getJSONObject(i).getString("nama_merk").toString();
				daftartipe[i] = menuitemArray.getJSONObject(i).getString("tipe").toString();
				daftarharga[i] = menuitemArray.getJSONObject(i).getString("harga").toString();
				daftartahun[i] = menuitemArray.getJSONObject(i).getString("tahun").toString();
				daftarnopol[i] = menuitemArray.getJSONObject(i).getString("nopolisi").toString();
				daftarfoto[i] = menuitemArray.getJSONObject(i).getString("nama_foto").toString();
				daftarwarna[i] = menuitemArray.getJSONObject(i).getString("warna").toString();
				daftarmesincc[i] = menuitemArray.getJSONObject(i).getString("mesin_cc").toString();
				daftartransmisi[i] = menuitemArray.getJSONObject(i).getString("transmisi").toString();
				daftartanganke[i] = menuitemArray.getJSONObject(i).getString("tgn_ke").toString();
				daftarkilometer[i] = menuitemArray.getJSONObject(i).getString("kilometer").toString();
				daftarbahanbakar[i] = menuitemArray.getJSONObject(i).getString("bhn_bkr").toString();
			}
			
			if (menuitemArray.length() < 1)
			{
				Toast.makeText(CariActivity.this, "Data Tidak Dtemukan", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
				Toast.makeText(CariActivity.this, "Data Tidak Dtemukan", Toast.LENGTH_LONG).show();
				e.printStackTrace();
		}
		
		ListView listcari = (ListView)findViewById(R.id.listcari);
		listcari.setAdapter(new MyCustomAdapter(CariActivity.this, R.layout.row, daftarmerk));
        
		listcari.setSelected(true);
		listcari.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				final String selectionid = daftarid[arg2]; 
				final String selectionmerk = daftarmerk[arg2];
				final String selectiontipe = daftartipe[arg2];
				final String selectionharga = daftarharga[arg2];
				final String selectiontahun = daftartahun[arg2];
				final String selectionnopol = daftarnopol[arg2];
				final String selectionwarna = daftarwarna[arg2];
				final String selectiontanganke = daftartanganke[arg2];
				final String selectiontransmisi = daftartransmisi[arg2];
				final String selectionmesincc = daftarmesincc[arg2];
				final String selectionkilometer = daftarkilometer[arg2];
				final String selectionbahanbakar = daftarbahanbakar[arg2];
				final String selectionfoto = daftarfoto[arg2];
		    	/*AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		        builder.setTitle("Pilih ?");
		        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						switch(item){
						case 0 :*/
							Intent i = new Intent(getApplicationContext(), DetailActivity.class);  
							i.putExtra("id_iklan", selectionid);
							i.putExtra("merk", selectionmerk);
							i.putExtra("tipe", selectiontipe);
							i.putExtra("harga", selectionharga);
							i.putExtra("tahun", selectiontahun);
							i.putExtra("nopol", selectionnopol);
							i.putExtra("warna", selectionwarna);
							i.putExtra("tanganke", selectiontanganke);
							i.putExtra("mesincc", selectionmesincc);
							i.putExtra("transmisi", selectiontransmisi);
							i.putExtra("kilometer", selectionkilometer);
							i.putExtra("bahanbakar", selectionbahanbakar);
							i.putExtra("foto", selectionfoto);
					    	startActivity(i);
							/*
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
    
    public class MyCustomAdapter extends ArrayAdapter<String> {

		public MyCustomAdapter(Context context, int textViewResourceId,
				String[] objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			//return super.getView(position, convertView, parent);
			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.row, parent, false);
			TextView lbobjectrownama=(TextView)row.findViewById(R.id.objectrownama);
			lbobjectrownama.setText(daftarmerk[position]);
			TextView lbobjectrowtipe=(TextView)row.findViewById(R.id.objectrowtipe);
			lbobjectrowtipe.setText(daftartipe[position]);
			TextView lbobjectrownopol=(TextView)row.findViewById(R.id.objectrownopol);
			lbobjectrownopol.setText(daftarnopol[position]);
			TextView lbobjectrowtahun=(TextView)row.findViewById(R.id.objectrowtahun);
			lbobjectrowtahun.setText(daftartahun[position]);
			TextView lbobjectrowharga=(TextView)row.findViewById(R.id.objectrowharga);
			lbobjectrowharga.setText(daftarharga[position]);
			
			ImageView icon=(ImageView)row.findViewById(R.id.objectrowicon);
			//icon.setImageResource(R.drawable.ic_launcher);
			//icon.setImageBitmap(bm);
			
			BitmapFactory.Options bmOptions;
			bmOptions = new BitmapFactory.Options();
			bmOptions.inSampleSize = 1;
			
			Bitmap bm;
			//if (daftartipe_pesan.get(position).equals("makanan"))
			//{
			try {
				//bm = LoadImage(urlwebfoto + "/" + daftarid[position] + ".jpg", bmOptions);
				bm = LoadImage(urlwebfoto + "/" + daftarfoto[position], bmOptions);
				icon.setImageBitmap(bm);
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			//	//Toast.makeText(getApplicationContext(), daftarid_menu_pesan.get(position), Toast.LENGTH_LONG).show();
			//}
			//else
			//{
			//	bm = LoadImage(urlwebfotominuman + "/" + daftarid_menu_pesan.get(position) + ".jpg", bmOptions);
			//	
			//}
			

			/*
			String myJpgPath = Environment.getExternalStorageDirectory().toString() + "/mapjogja/" + arr_object_id[position] + ".jpg";  
			BitmapFactory.Options options = new BitmapFactory.Options();
			Bitmap bm = BitmapFactory.decodeFile(myJpgPath, options);
			icon.setImageBitmap(bm);
*/
			
			/*if (menuArray[position]=="Kos"){
				icon.setImageResource(R.drawable.building);
			}
			else if (menuArray[position]=="Kontrakan"){
				icon.setImageResource(R.drawable.shop);
			}
			else if (menuArray[position]=="Tanah"){
				icon.setImageResource(R.drawable.store);
			}
			else if (menuArray[position]=="Rumah"){
				icon.setImageResource(R.drawable.home);
			}*/
					
			return row;
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


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_home, menu);
		return true;
	}

}
