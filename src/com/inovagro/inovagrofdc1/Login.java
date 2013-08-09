package com.inovagro.inovagrofdc1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements InovagroConstants {

	Button btnLogin,btnCancel;
	EditText edtUserName, edtPassword;
	static int UserID;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getBooleanExtra("EXIT", false)) {
       	 finish();
       	}//use above in conjuction with code in MainActivity to kill the stack and "exit" the program
        //see handler for exit() in the List event handling section in the main activity
        
        setContentView(R.layout.login);
    	btnLogin=(Button)findViewById(R.id.btnLogin);
    	btnCancel=(Button)findViewById(R.id.btnCancel);
    	edtUserName=(EditText)findViewById(R.id.edtUserName);
    	edtPassword=(EditText)findViewById(R.id.edtPassword);
    	
	
	}
	
	
	//btn Handler for both buttons
	public void mHandler(View v){
		if (v==btnCancel){
			finish();
		}
		if (v==btnLogin){
		//begin login process
			//next 3 lines for testing purposes only
		/*
			Intent i= new Intent(this,MainActivity.class);
			startActivity(i);
			UserID=2;  //for testing purposes only
		
			*/
	//uncomment below for actual
			
			//connect and verify
			String usr=edtUserName.getText().toString();
			String pass=edtPassword.getText().toString();
			String addr=BaseURL+"?action=LOGIN&username="+usr+"&password="+pass;
			new LoginTask(addr,Login.this).execute();
			//new LoginTask(null,Login.this).execute(new String[] { InovagroConstants.BaseURL+"?action=LOGIN&username="+usr+"&password="+pass });
		
		}
	}//public void
	
	
	class LoginTask extends AsyncTask<String, String, String> {
		private ProgressDialog pd;
		Activity activity;
		
		String thePath;
		public LoginTask(String path, Activity a){
			thePath=path;
			activity=a;
		}
		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			Log.v("mInovagro",thePath);
		
			
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(thePath);
			try {
				HttpResponse execute = client.execute(httpGet);
				InputStream content = execute.getEntity().getContent();
				
				//not part of http;
				publishProgress("Connected...");
				
				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));
				String s = "";
				while ((s = buffer.readLine()) != null) {
					response += s;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		
			//=======
		/*	
			for (String url : urls) {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse execute = client.execute(httpGet);
					InputStream content = execute.getEntity().getContent();
					
					//not part of http;
					publishProgress("Connected...");
					
					BufferedReader buffer = new BufferedReader(
							new InputStreamReader(content));
					String s = "";
					while ((s = buffer.readLine()) != null) {
						response += s;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//=======
		*/	
			pd.dismiss();
			return response;	
		//	publishProgress(item); SystemClock.sleep(200);
			
		
		    }
		
		@Override
		protected void onPreExecute() {
		    pd = ProgressDialog.show(activity, "Signing in",
		            "Please wait while we are signing you in..");
		}
		
		@Override
		protected void onProgressUpdate(String... item) {
		 
		}
		
		@Override
		protected void onPostExecute(String result) {
			
			if (result.endsWith("successOK")){
				UtilityFunctions uf=new UtilityFunctions();
				result=result.replace("successOK","");
				Log.v("login", result);
				String[] data= uf.msplit(result, ":");
				//Log.v("login",result);
				UserID=Integer.valueOf(data[0]);
				Toast.makeText(Login.this, "Logged in", Toast.LENGTH_SHORT) .show();
			
				Intent i= new Intent(getApplicationContext(),MainActivity.class);
				startActivity(i);
			}
			else
			{
				if (result.endsWith("OK")){
					Toast.makeText(Login.this, "Sorry, your username or password are invalid!", Toast.LENGTH_LONG) .show();
				}
				else{
					Toast.makeText(Login.this, "Sorry, There is a communication error", Toast.LENGTH_LONG) .show();
				}
			}
			
			
		}
	}//class LoginTask
		


}//main class


