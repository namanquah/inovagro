package com.inovagro.inovagrofdc1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
			//next 3 lines for testing purposes only. comment during actual use and use next 4 code lines
			
			 MainActivity.OfflineState=OnLineMode;  //for initial load, determine its effect		
//			Intent i= new Intent(this,MainActivity.class);
//			startActivity(i);
//			UserID=2;  //for testing purposes only
		
			
	//uncomment below for actual
			
			//connect and verify
			String usr=edtUserName.getText().toString();
			String pass=edtPassword.getText().toString();
			//backdoor to reset the localDB
										/*
										if (usr.equalsIgnoreCase("m")) {
											if (pass.equals("12")){ //12bms34std
									        	DBAdapter db= new DBAdapter(getApplicationContext());
									        	db.open();
									        	db.masterDBReset();
									        	db.close();
									        	return;
															
												/*
												 DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
													    @Override
													    public void onClick(DialogInterface dialog, int which) {
													        switch (which){
													        case DialogInterface.BUTTON_POSITIVE:
													            //Yes button clicked
													        	DBAdapter db= new DBAdapter(getApplicationContext());
																db.masterDBReset();
																Toast.makeText(getApplicationContext(), "Add Data Deleted!", Toast.LENGTH_LONG).show();
																//getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
													            break;
							
													        case DialogInterface.BUTTON_NEGATIVE:
													            //No button clicked
													            break;
													        }
													    }
													};
							
													AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
													builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
													    .setNegativeButton("No", dialogClickListener).show();
											 */
											/*
											}
										}  */
	
   //login code - enable this so that acutal login occurs, not a bypass			
			String addr=BaseURL+"?action=LOGIN&username="+usr+"&password="+pass;
			new LoginTask(addr,Login.this).execute();

			
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
			
				//get data from server and save locally.
				cacheLoginInfo(edtUserName.getText().toString(), data[1], data[0]);
				
				 MainActivity.OfflineState=OnLineMode;  //for initial load, determine its effect
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
					//attempt local log in. Ask First?
					attemptLocalLogin();
				}
			}
			
			
		}
		void cacheLoginInfo(String userName, String password, String UserID){
		
			DBAdapter db;
			db=new DBAdapter(getApplicationContext());
			db.open();
			String res=db.saveUsersDataOffline(userName, password, UserID);
			if (res.endsWith("successOK")){
				Toast.makeText(getApplicationContext(), "You may also log in offline", Toast.LENGTH_LONG).show();
				//close the dialog box, or reset the values.
				//getSupportFragmentManager().popBackStack();
				
				
			}
			else {//
				Toast.makeText(getApplicationContext(), "Sorry, Local Save failed", Toast.LENGTH_LONG).show();
			}
			db.close();
			
		}//fxn
		
		
		void attemptLocalLogin(){
			String usr=edtUserName.getText().toString();
			String pass=edtPassword.getText().toString();
			DBAdapter db;
			db=new DBAdapter(getApplicationContext());
			db.open();
			String res=db.verifyUsersDataOffline(usr, pass );
			db.close();
			if (res.endsWith("successOK")){
				Toast.makeText(getApplicationContext(), "Your are logged in Offline Mode", Toast.LENGTH_LONG).show();
				
				UtilityFunctions uf=new UtilityFunctions();
				res=res.replace("successOK","");				
				String[] data= uf.msplit(res, ":");
				
				UserID=Integer.valueOf(data[0]);
				
				
				
				MainActivity.OfflineState=MainActivity.OffLineMode;
				Intent i= new Intent(getApplicationContext(),MainActivity.class);
				startActivity(i);
				
			}
			else {//
				Toast.makeText(getApplicationContext(), "Sorry, Cannot login in Offline mode", Toast.LENGTH_LONG).show();
			}
			//db.close();
			
			
			
		}
	}//class LoginTask
		


}//main class


