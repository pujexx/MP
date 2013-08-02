package com.kukuh.mp;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UploadFotoActivity extends Activity {
	ImageView imgprofilbrowse;
	TextView tvprofilbrowse;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_foto);
		
		imgprofilbrowse = (ImageView)findViewById(R.id.imgprofilbrowse);
		tvprofilbrowse = (TextView)findViewById(R.id.tvprofilbrowse);
		
		Button btnprofilbrowse = (Button)findViewById(R.id.btnprofilbrowse);
		btnprofilbrowse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"),0);
			}
		});
		
		Button btnprofiluploadfoto = (Button)findViewById(R.id.btnprofiluploadfoto);
		btnprofiluploadfoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try
	        	{
	        		uploader upl = new uploader();
	        		upl.setUrlAndFile("http://192.168.43.99/mp/json/receiver-upload.php", tvprofilbrowse.getText().toString()); // "/sdcard/external_sd/nexus7.jpg"); //,(TextView)(findViewById(R.id.TextView01))); 
	        		//upl.setUrlAndFile("http://10.0.2.2/web-receiver-upload/receiver-upload.php", Environment.getExternalStorageDirectory().getAbsolutePath() + "external_sd/nexus7.jpg",(TextView)(findViewById(R.id.TextView01))); 
	        		//android.os.Environment.getExternalStorageDirectory() + "/nexus7.jpg";
	        		
	        		upl.execute();
	        	}
	        	catch(Exception e)
	        	{
	        		//((TextView)findViewById(R.id.TextView01)).setText(e.toString());
	        	}    
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	switch(requestCode) {
    	case 0:
    	    if(resultCode == RESULT_OK){  
    	    	imgprofilbrowse.setImageURI(data.getData());
    	    	//imgprofilbrowse.setText("Full Path = " + getRealPathFromURI(data.getData()));
    	    	//imgprofilbrowse.setText("URI = " + data.getDataString() + ", Filename = " + getRealPathFromURI(data.getData()).substring(getRealPathFromURI(data.getData()).lastIndexOf("/") + 1));
    	    	tvprofilbrowse.setText(getRealPathFromURI(data.getData()));
    	    }
    	break; 
    	}
        super.onActivityResult(requestCode, resultCode, data);
   }
    
    public String getRealPathFromURI(Uri contentUri) {
		String[] proj = { MediaStore.Images.Media.DATA };
		android.database.Cursor cursor = managedQuery(contentUri, proj, // Which columns to return
				null, // WHERE clause; which rows to return (all rows)
				null, // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_upload_foto, menu);
		return true;
	}

}
