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

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MobilBaruActivity extends Activity {
	private JSONObject jObject;
	private String jsonResult ="";
	private String url = "http://192.168.43.99/mp/json/datamobilbaru.php";
	private String urljumlah = "http://192.168.43.99/mp/json/jumlahiklan.php";
	private String urlprofil = "http://192.168.43.99/mp/json/datamemberbyemail.php";
	private String urlwebfoto = "http://192.168.43.99/mp/uploads";
	String[] daftarid;
	String[] daftarmerk;
	String[] daftartipe;
	String[] daftarharga;
	String[] daftartahun;
	String[] daftardeskripsi;
	String[] daftarpjkthn;
	String[] daftarblnpjk;
	String[] daftarnopol;
	String[] daftarfoto;
	String[] daftarmesincc;
	String[] daftartransmisi;
	String[] daftarkilometer;
	String[] daftarwarna;
	String[] daftartanganke;
	String[] daftarbahanbakar;
	String[] daftaridmember;
	String[] daftarnamamember;
	String[] daftaralamatmember;
	String[] daftartelpmember;
	String[] daftarfotomember;
	
	double jumlahiklan = 0;
	int jumlahhal = 0;
	int perhal = 5;
	int hal = 1;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobil_baru);
		
		getJumlah();
		
		jumlahhal = (int) Math.ceil(jumlahiklan / perhal);
		
		RefreshList();
		
		Button btnbaruback = (Button)findViewById(R.id.btnbaruback);
		btnbaruback.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				hal = hal - 1;
				if (hal < 1) { hal = 1; }
				RefreshList();
			}
		});
		
		Button btnbarunext = (Button)findViewById(R.id.btnbarunext);
		btnbarunext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				hal = hal + 1;
				if (hal > jumlahhal) { hal = jumlahhal; }
				RefreshList();
			}
		});
		
	}


	public void getJumlah() {
    	try {
        	jsonResult = getRequest(urljumlah); 
        	
        	jObject = new JSONObject(jsonResult);
			JSONArray menuitemArray = jObject.getJSONArray("jumlahiklan");

			if (menuitemArray.length()>0)
			{
				jumlahiklan = Double.parseDouble(menuitemArray.getJSONObject(0).getString("jumlah").toString());
				Toast.makeText(getApplicationContext(), String.valueOf(jumlahiklan), Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

  
    public void RefreshList() {
    	try {
    		jsonResult = getRequest(url + "?hal="+String.valueOf(hal)+"&perhal="+String.valueOf(perhal));
        	
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
			daftardeskripsi = new String[menuitemArray.length()];
			daftarpjkthn = new String[menuitemArray.length()];
			daftarblnpjk = new String[menuitemArray.length()];
			daftaridmember = new String[menuitemArray.length()];
			daftarnamamember = new String[menuitemArray.length()];
			daftaralamatmember = new String[menuitemArray.length()];
			daftartelpmember = new String[menuitemArray.length()];
			daftarfotomember = new String[menuitemArray.length()];
			
			for (int i = 0; i < menuitemArray.length(); i++)
			{
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
				daftardeskripsi[i] = menuitemArray.getJSONObject(i).getString("deskripsi").toString();
				daftarpjkthn[i] = menuitemArray.getJSONObject(i).getString("pjk_th").toString();
				daftarblnpjk[i] = menuitemArray.getJSONObject(i).getString("bln_pjk").toString();
				daftaridmember[i] = menuitemArray.getJSONObject(i).getString("id_member").toString();
				daftarnamamember[i] = menuitemArray.getJSONObject(i).getString("nama").toString();
				daftaralamatmember[i] = menuitemArray.getJSONObject(i).getString("alamat").toString();
				daftartelpmember[i] = menuitemArray.getJSONObject(i).getString("no_telp").toString();
				daftarfotomember[i] = menuitemArray.getJSONObject(i).getString("foto").toString();
				}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ListView listmobilbaru = (ListView)findViewById(R.id.listmobilbaru);
		//listmobilbaru.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftarmerk));
		listmobilbaru.setAdapter(new MyCustomAdapter(MobilBaruActivity.this, R.layout.row, daftarmerk));
		
		listmobilbaru.setSelected(true);
		listmobilbaru.setOnItemClickListener(new OnItemClickListener() {
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
				final String selectiondeskripsi = daftardeskripsi[arg2];
				final String selectionpjkthn = daftarpjkthn[arg2];
				final String selectionblnpjk = daftarblnpjk[arg2];
				final String selectionidmember = daftaridmember[arg2];
				final String selectionnamamember = daftarnamamember[arg2];
				final String selectionalamatmember = daftaralamatmember[arg2];
				final String selectiontelpmember = daftartelpmember[arg2];
				final String selectionfotomember = daftarfotomember[arg2];
				final String selectionfoto = daftarfoto[arg2];
				
				//final String selectionlatitude = daftarlatitude[arg2]; 
				//final String selectionlongitude = daftarlongitude[arg2]; 
		    	//final CharSequence[] dialogitem = {"Edit", "Delete"};
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
							i.putExtra("deskripsi", selectiondeskripsi);
							i.putExtra("pjkthn", selectionpjkthn);
							i.putExtra("blnpjk", selectionblnpjk);
							i.putExtra("foto", selectionfoto);
							i.putExtra("idmember", selectionidmember);
							i.putExtra("namamember", selectionnamamember);
							i.putExtra("alamatmember", selectionalamatmember);
							i.putExtra("no_telpmember", selectiontelpmember);
							i.putExtra("fotomember", selectionfotomember);
							//i.putExtra("latitude", selectionlatitude);
							//i.putExtra("longitude", selectionlongitude);
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

        ((ArrayAdapter)listmobilbaru.getAdapter()).notifyDataSetInvalidated();    
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

	public boolean onKeyDown(int keyCode, KeyEvent event) 
    {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) 
            {
                //Ask the user if they want to quit
                new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Apakah Anda Yakin Ingin Keluar ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() 
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
                         Intent intent = new Intent(MobilBaruActivity.this, MainActivity.class);
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
