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

import com.kukuh.mp.ProfilMemberActivity.MyCustomAdapter;

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
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DetailActivity extends Activity {
	private JSONObject jObject;
	private String jsonResult ="";
	private String urlwebfoto = "http://192.168.43.99/mp/uploads";
	private String urlwebfotomember = "http://192.168.43.99/mp/uploads/foto_member";
	private String url = "http://192.168.43.99/mp/json/dataiklan.php";
	String[] daftarnamamember;
	String[] daftaralamatmember;
	String[] daftartelpmember;
	String[] daftarfbmember;
	String[] daftartwmember;
	String[] daftarfotomember;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		//TextView tvprofilnamamemberatas = (TextView)findViewById(R.id.txtdetailuser);
		//tvprofilnamamemberatas.setText(TabLayoutActivity.nama);
		TextView tvdetailnama = (TextView)findViewById(R.id.tvdetailmerk);
		tvdetailnama.setText(getIntent().getStringExtra("merk"));
		TextView tvdetailtipe = (TextView)findViewById(R.id.tvdetailtipe);
		tvdetailtipe.setText(getIntent().getStringExtra("tipe"));
		TextView tvdetailtahun = (TextView)findViewById(R.id.tvdetailtahun);
		tvdetailtahun.setText(getIntent().getStringExtra("tahun"));
		TextView tvdetailharga = (TextView)findViewById(R.id.tvdetailharga);
		tvdetailharga.setText(getIntent().getStringExtra("harga"));
		TextView tvdetaildeskripsi = (TextView)findViewById(R.id.tvdetaildeskripsi);
		tvdetaildeskripsi.setText(getIntent().getStringExtra("deskripsi"));
		TextView tvdetailnopol = (TextView)findViewById(R.id.tvdetailnopol);
		tvdetailnopol.setText(getIntent().getStringExtra("nopol"));
		TextView tvdetailwarna = (TextView)findViewById(R.id.tvdetailwarna);
		tvdetailwarna.setText(getIntent().getStringExtra("warna"));
		TextView tvdetailtanganke = (TextView)findViewById(R.id.tvdetailtanganke);
		tvdetailtanganke.setText(getIntent().getStringExtra("tanganke"));
		TextView tvdetailtransmisi = (TextView)findViewById(R.id.tvdetailtransmisi);
		tvdetailtransmisi.setText(getIntent().getStringExtra("transmisi"));
		TextView tvdetailkilometer = (TextView)findViewById(R.id.tvdetailkilometer);
		tvdetailkilometer.setText(getIntent().getStringExtra("kilometer"));
		TextView tvdetailmesincc = (TextView)findViewById(R.id.tvdetailmesincc);
		tvdetailmesincc.setText(getIntent().getStringExtra("mesincc"));
		TextView tvdetailbahanbakar = (TextView)findViewById(R.id.tvdetailbahanbakar);
		tvdetailbahanbakar.setText(getIntent().getStringExtra("bahanbakar"));
		TextView tvdetailpjkthn = (TextView)findViewById(R.id.tvdetailpjkthn);
		tvdetailpjkthn.setText(getIntent().getStringExtra("pjkthn"));
		TextView tvdetailblnpjk = (TextView)findViewById(R.id.tvdetailblnpjk);
		tvdetailblnpjk.setText(getIntent().getStringExtra("blnpjk"));
		TextView tvdetailnamamember = (TextView)findViewById(R.id.tvdetailnamamember);
		tvdetailnamamember.setText(getIntent().getStringExtra("namamember"));
		TextView tvdetailalamatmember = (TextView)findViewById(R.id.tvdetailalamatmember);
		tvdetailalamatmember.setText(getIntent().getStringExtra("alamatmember"));
		TextView tvdetailtelpmember = (TextView)findViewById(R.id.tvdetailtelpmember);
		tvdetailtelpmember.setText(getIntent().getStringExtra("no_telpmember"));
		
		ImageView imgdetailfoto = (ImageView)findViewById(R.id.imgdetailfoto);
		
		BitmapFactory.Options bmOptions;
		bmOptions = new BitmapFactory.Options();
		bmOptions.inSampleSize = 2;
		
		Bitmap bm;
		
		try {
			bm = LoadImage(urlwebfoto + "/" + getIntent().getStringExtra("foto"), bmOptions);
			imgdetailfoto.setImageBitmap(bm);
		}
		catch (Exception e) {
			// TODO: handle exception
			imgdetailfoto.setImageBitmap(null);
		}
		
        ImageView imgdetailfotomember = (ImageView)findViewById(R.id.imgdetailfotomember);
		
		BitmapFactory.Options bm1Options;
		bm1Options = new BitmapFactory.Options();
		bm1Options.inSampleSize = 2;
		
		Bitmap bm1;
		
		try {
			bm1 = LoadImage(urlwebfotomember + "/" + getIntent().getStringExtra("idmember") + ".jpg", bm1Options);
			imgdetailfotomember.setImageBitmap(bm1);
		}
		catch (Exception e) {
			// TODO: handle exception
			imgdetailfotomember.setImageBitmap(null);
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

}
