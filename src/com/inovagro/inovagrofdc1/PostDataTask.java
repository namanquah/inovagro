package com.inovagro.inovagrofdc1;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

public class PostDataTask extends AsyncTask<String, String, String> {//implements InovagroConstants {
	GeneralCallbackIntefaces callBack=null;
	
	private ProgressDialog pd;
	Activity activity;
	int actionType;
	HashMap<String, String>values=null;
	String thePath;
	//boolean mTwoPane;
	
	public PostDataTask(int action, String path, Activity a, HashMap<String, String>values){
		thePath=path;
		activity=a;
		this.actionType =action;
		this.values=values;
	//	mTwoPane=noOfPanes;
		
	}
	
	
	@Override
	protected String doInBackground(String... urls) {
//		if (actionType==actionUPLOAD_SAVED_VISIT_DATA){
//			values=readTheSavedData();
//		}
//		
		//returns result
		String result=null;
		//String uploadFile="/sdcard/test.png";
		//String actionUrl = "http://your_domain/your_post_url";
		final String end = "\r\n";
		final String twoHyphens = "--";
		final String boundary = "*****++++++************++++++++++++";
	
		try{
			URL url = new URL(thePath);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
		
			/* setRequestProperty */
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+ boundary);
		
			DataOutputStream ds = new DataOutputStream(conn.getOutputStream());

			for (Map.Entry<String, String> entry: values.entrySet()){
				String key=entry.getKey();
				String value =entry.getValue();
				ds.writeBytes(twoHyphens + boundary + end);
				ds.writeBytes("Content-Disposition: form-data; name=\""+key+"\""+end+end+value+end);
				
			}
/*			
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; name=\"from\""+end+end+"auto"+end);
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; name=\"to\""+end+end+"ja"+end);
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; name=\"uploadedFile\";filename=\"" + uploadFile +"\"" + end);
			ds.writeBytes(end);
		
			FileInputStream fStream = new FileInputStream(uploadFile);
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
		
			while((length = fStream.read(buffer)) != -1) {
			  ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			
*/			
			
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			//fStream.close();
			
			
			
			ds.flush();
			ds.close();
		
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
				final String msg=conn.getResponseMessage();
			  //Toast.makeText(activity, conn.getResponseMessage(), Toast.LENGTH_LONG).show();  //this line will generate an error which is caught anyway.
				activity.runOnUiThread(new Runnable() {
					  public void run() {
					    //Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
					    Toast.makeText(activity, "Connection Error", Toast.LENGTH_SHORT).show();
					  }
					});
			}
		
			StringBuffer b = new StringBuffer();
			InputStream is = conn.getInputStream();
			int bufferSize = 1024; //defined earlier in commented code
			byte[] data = new byte[bufferSize];
			int leng = -1;
			while((leng = is.read(data)) != -1) {
			  b.append(new String(data, 0, leng));
			}
			result = b.toString();
			
		}catch(Exception e){
			Log.v("PostError", e.toString());
		
			
		}
		
		pd.dismiss();
		return result;
	}

	//
	
	@Override
	protected void onPreExecute() {
		String msg="connecting";
		if (actionType==1){ //update teh actionType value
			msg="Fetching Support Data..";
		}
	
	    pd = ProgressDialog.show(activity, "Connecting...",
	            msg);
	}
	
	@Override
	protected void onProgressUpdate(String... item) {
	 
	}
	
	@Override
	protected void onPostExecute(String result) {
		//if (actionType==1){//fix the actionType to a particular visit type
			//simply call an interface method
			callBack = (GeneralCallbackIntefaces) activity;
			callBack.doAfterPostData(result);
		//}//if action
	
	}

//	HashMap<String, String> readTheSavedData(){  //using the thread to also read the long lived process of reading
//		return null;
//	}
}
