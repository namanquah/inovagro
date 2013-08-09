package com.inovagro.inovagrofdc1;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements MainMenuList.Callbacks, 
				BasicAdvancedSearchFragment.SearchCallbacks,GeneralCallbackIntefaces, InovagroConstants{

	//boolean twoPanes=false;
	private boolean mTwoPane;
	//GPS sampling intervals:
	private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 5; // in Meters  -1
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 2000; // in Milliseconds, 1000
    protected LocationManager locationManager;

	//intent result no for taking picture
	private final int RES_IMAGE_CAPTURE=2020;
	
	static int OfflineState=0; //can be online or offline - used accros the entire application
	static int PurposeOfSearch;    //this is CLOSELY ALLIED TO THE MENU CHOICE in main Menu List. unlike actionType 
	static int CurrentFarmerID;  //short cut- use for addFarm, (farmVist) etc to track current farmer
	static String CurrentFarmerName;
	private int glbActionType; //action type to be used in results ie after post
	
	
	////////////--------v definitions for menu
	public static  String mnuAddFarmer;
    public static  String mnuFarmVisit;
    public static  String mnuSynchronize;
    public static  String mnuChangePassword;
    public static  String mnuExit;
    public static  String mnuAddFarm;
    
    public static  String mnuPlanVisits;
    public static  String mnuViewPlan;
    public static  String mnuGoOnline ;
    public static  String mnuGoOffline;
    public static  String mnuUploadSavedData;
    ////---^
	   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
       
        setContentView(R.layout.activity_main_2);
        
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           MainMenuList fragment = new MainMenuList();            
            fragmentTransaction.add(R.id.leftFrame, fragment);
            //fragmentTransaction.add(android.R.id.content, fragment);//this also work, but is generic
            fragmentTransaction.commit();	 
        }
        
        if (findViewById(R.id.rightFrame)!=null ){ 
            mTwoPane = true;          
       }
        //GPS initialization:
        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 
                MINIMUM_TIME_BETWEEN_UPDATES, 
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new MyLocationListener(this)
        );
        MainActivity.OfflineState=OnLineMode;  //for initial load, determine its effect
        
        //obtain the menu items that correspond to the strings for use in this code
        Resources res = getResources();
		String [] OnlineMenu = res.getStringArray(R.array.arryOnlineMenu);
		//Resources res = getResources();
		String [] OfflineMenu = res.getStringArray(R.array.arryOfflineMenu);
		
		//-----------------V
		 mnuAddFarmer=OnlineMenu[0];
		 mnuAddFarm=OnlineMenu[1];
		 mnuFarmVisit=OnlineMenu[2];
		 mnuPlanVisits=OnlineMenu[3];
		 mnuViewPlan=OnlineMenu[4];
		 mnuUploadSavedData=OnlineMenu[5];
		 mnuSynchronize=OnlineMenu[6];
		 mnuGoOffline=OnlineMenu[7];
	     mnuChangePassword=OnlineMenu[8];
	     mnuExit=OnlineMenu[9];
	      
	     mnuGoOnline=OfflineMenu[4] ;
	     
	    
		
	}
//this is a call back function from menulist
	@Override
	public void onItemSelected(String id, Fragment frag, int position) {
		
		int TargetPane;
		if (mTwoPane) {
			TargetPane=R.id.rightFrame;
		}
		else
		{
			TargetPane=R.id.leftFrame;		
		}
		
		  if(id.equals(mnuSynchronize)){
          	String addr=BaseURL+"?action=SUPPORT_DATA";
  			new SyncTask(actionSYNC, addr,MainActivity.this).execute();
  	       return;  
	            
          }
		  if(id.equals(mnuExit)){
          	//as to confirm
          	//finish();
			  Intent intent=new Intent(this,Login.class);
			  intent.putExtra("EXIT", true);
			  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			  startActivity(intent);
          	return;
          }

			android.support.v4.app.Fragment fragment=null;
		    FragmentManager fragmentManager = getSupportFragmentManager();	
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(id.equals(mnuAddFarmer)){	            
            	fragment=new AddFarmerDetailFragment();
            	   fragmentTransaction.replace(TargetPane, fragment); //as .add
            	   fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN); //new addition
                   fragmentTransaction.addToBackStack(null);
                   fragmentTransaction.commit(); 
            }
		  
            
            if(id.equals(mnuFarmVisit)){         
            	MainActivity.PurposeOfSearch=searchBA_FARM_VISIT; //a short circuit. Not passing to fragment and back
            	fragment=new BasicAdvancedSearchFragment(searchBA_FARM_VISIT);
            	   fragmentTransaction.replace(TargetPane, fragment); //as .add
                   fragmentTransaction.addToBackStack(null);
                   fragmentTransaction.commit(); 
            }
            if(id.equals(mnuAddFarm)){              
            	//short circuit, not passing to fragment and back:
            	MainActivity.PurposeOfSearch=searchBA_ADD_FARM;
            	fragment=new BasicAdvancedSearchFragment(searchBA_ADD_FARM);
            	   fragmentTransaction.replace(TargetPane, fragment); //as .add
                   fragmentTransaction.addToBackStack(null);
                   fragmentTransaction.commit(); 
            }
           
            if(id.equals(mnuPlanVisits)){
            	//short circuit, not passing to fragment and back:
            	MainActivity.PurposeOfSearch=searchBA_PLAN_VISIT;
            	fragment=new BasicAdvancedSearchFragment(searchBA_PLAN_VISIT);
            	   fragmentTransaction.replace(TargetPane, fragment); //as .add
                   fragmentTransaction.addToBackStack(null);
                   fragmentTransaction.commit(); 
            }
            
            if (id.equals(mnuGoOffline)){
            	OfflineState=OffLineMode;
            	MainMenuList mml=(MainMenuList)frag;
            	mml.changeMenu(mnuGoOnline, position);
            	//change the text in the menu to Offline
            	//change the background from Blue to Red
            }
            if (id.equals(mnuGoOnline)){
            	OfflineState=OnLineMode;
            	MainMenuList mml=(MainMenuList)frag;
            	mml.changeMenu(mnuGoOffline, position);
            	//change the text in the menu to Online
            	//change the background from Red to Blue
            }
            //mnuUploadSavedData
            if (id.equals(mnuUploadSavedData)){
            	HashMap<String,String> data= new HashMap<String, String>();
            	String strData;
            	DBAdapter db = new DBAdapter(getApplicationContext());
 				db.open();
 				strData =db.uploadSavedVisitData_getInsertValuesPart();
 				db.close();
            	data.put("valuesPart", strData);
            	postData( actionUPLOAD_SAVED_VISIT_DATA, data); //fetch the data in the postData or PostThread
            
            }
            
            if(id.equals(mnuChangePassword)){
            	//short circuit, not passing to fragment and back:
            	glbActionType =actionCHANGE_PASSWORD;
            	fragment=new ChangePassword();
            	   fragmentTransaction.replace(TargetPane, fragment); //as .add
                   fragmentTransaction.addToBackStack(null);
                   fragmentTransaction.commit(); 
            }
            if(id.equals(mnuViewPlan)){
            	//short circuit, not passing to fragment and back:
            	MainActivity.PurposeOfSearch=view_PLANNED_VISITS;
            	fragment=new BasicAdvancedSearchFragment(view_PLANNED_VISITS);
            	   fragmentTransaction.replace(TargetPane, fragment); //as .add
                   fragmentTransaction.addToBackStack(null);
                   fragmentTransaction.commit(); 
            }
          
	}//on item selected
	
	//call back function from basic+adv serach
	public void doSearch(int searchType, int PurposeOfSearch){
		int TargetPane;
		android.support.v4.app.Fragment fragment=null;
		if (mTwoPane) {
	          TargetPane=R.id.rightFrame;
		}
		else
		{
		      TargetPane=R.id.leftFrame;
				
		}
		    FragmentManager fragmentManager = getSupportFragmentManager();	
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if(searchType==BASIC_SEARCH){
	             fragment=new BasicSearchFragment(PurposeOfSearch);
            }

            if(searchType==ADVANCED_SEARCH){
	            fragment=new AdvancedSearchFragment(PurposeOfSearch);
            }
	            
            	fragmentTransaction.replace(TargetPane, fragment);
            	fragmentTransaction.addToBackStack(null);
	            fragmentTransaction.commit();
		
	}
	
	//**************---v----general call backs***************************/
	//called by basic search form. Can do either farmer name or farmerRefNo
	public void doBasicSearch(int searchType, String searchString){
		//connect to server, return data and show a list!
		int TargetPane;
	
		if (mTwoPane) {
	          TargetPane=R.id.rightFrame;
		}
		else
		{
		      TargetPane=R.id.leftFrame;
				
		}
		String addr=null;
		int action=-1;
		if (searchType==actionSEARCH_FARMERNAME){
		 addr=BaseURL+"?action=SEARCH_FARMERS_BY_NAME&searchString="+searchString;
		 action=searchType;//actionSEARCH_FARMERNAME;
		}
		if (searchType==actionSEARCH_FARMERREFERENCENO){
			 addr=BaseURL+"?action=SEARCH_FARMERS_BY_REFNO&searchString="+searchString;
			 action=searchType;//actionSEARCH_FARMERREFERENCENO;
			}
		if (MainActivity.PurposeOfSearch ==view_PLANNED_VISITS){
			doSearchOffline(searchType, searchString);
			return;
		}
		if (MainActivity.OfflineState ==OnLineMode){
			new SyncTask(action,addr,MainActivity.this).execute();
		
		}
		else{ //is offline, search locally
			
			doSearchOffline(searchType, searchString);
		}

	}
	
	//called by advanced search form
	public void doAdvancedSearch(HashMap<String,String>values,  int action, int PurposeOfSearch){
		//connect to server, return data and show a list!
		this.PurposeOfSearch=PurposeOfSearch; //
		int TargetPane;
		android.support.v4.app.Fragment fragment=null;
		if (mTwoPane) {
	          TargetPane=R.id.rightFrame;
		}
		else
		{
		      TargetPane=R.id.leftFrame;
				
		}
		
		String tmp="";
		for (Map.Entry<String, String> entry: values.entrySet()){
			String key=entry.getKey();
			String value =entry.getValue();
			tmp+="&"+key+"="+value;
		}
		if (OfflineState==OnLineMode){
			String addr=BaseURL+"?action=SEARCH_ADVANCED"+tmp;
			new SyncTask(action,addr,MainActivity.this).execute();
		}
		else{
			doAdvancedSearchOffline(values,action, PurposeOfSearch);
		}
		
		/*
		 *  //was commented on Thurs 13 June 2013. This shd not be called.
		 * //i shd be returning a list of results!
	    FragmentManager fragmentManager = getSupportFragmentManager();	
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      //show a list fragement with data from db!
        fragment=new AdvancedSearchFragment();
        fragmentTransaction.replace(TargetPane, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        */
       
	}

	public void fetchListOfFarms(int searchType, int FarmerID){
		//connect to server, return data and show a list!
		int TargetPane;
	
		if (mTwoPane) {
	          TargetPane=R.id.rightFrame;
		}
		else
		{
		      TargetPane=R.id.leftFrame;
				
		}
		String addr=null;
		int action=-1;
		if (searchType==actionSEARCH_CURRENT_FARMS){
		 addr=BaseURL+"?action=SEARCH_CURRENT_FARMS&searchString="+FarmerID;
		 action=searchType; //actionSEARCH_CURRENT_FARMS
		 CurrentFarmerID=FarmerID;  //shortCircuit. Using a static globl var
		}

		
		//new SyncTask(action,addr,MainActivity.this).execute();
		if (OfflineState ==OnLineMode){
			new SyncTask(action,addr,MainActivity.this).execute();
		//do not use the ff: was for dummy testing
		}
		else{ //is offline, search locally
			
			doSearchOffline(searchType, String.valueOf(FarmerID));
		}
	}
	
	//show the fixed list of visit types. No need for assync task.
	public void showVisitTypes(int actionType, int FarmYearlyDataID){
		//actually, FarmYearlyDataID is not needed here, but has to be passed to the event after
		//a visit type has been chosen so that records get created properly. 
		//DO NOT DELETE IT
		int TargetPane;
		
		if (mTwoPane) {
	          TargetPane=R.id.rightFrame;
		}
		else
		{
		      TargetPane=R.id.leftFrame;			
		}

		if (actionType==actionSHOW_VISIT_TYPES){ // by the way, this can be the only action
		 //double check that FarmYearlyDataID is being used!
			//call a different constructor bc not all the array is needed
			
			//fetch list of visit types from resource
			Resources res = getResources();
			String[] arryVisitTypes = res.getStringArray(R.array.arryVisitTypes);
			//show the list as a menu
			FragmentManager fragmentManager = getSupportFragmentManager();	
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			android.support.v4.app.Fragment fragment=null;
			
			fragment=new ListResultFragment(arryVisitTypes, actionType, FarmYearlyDataID);
			fragmentTransaction.replace(TargetPane , fragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}			
	}
	
	// show the add visit form
	public void showVisitForm(int actionType, int FarmYearlyDataID, int VisitType){
		int TargetPane;
		
		if (mTwoPane) {
	          TargetPane=R.id.rightFrame;
		}
		else
		{
		      TargetPane=R.id.leftFrame;			
		}
		
		if (actionType==actionSHOW_VISIT_FORM){ // by the way, this can be the only action
		 //double check that FarmYearlyDataID is being used!
			//call a different constructor bc not all the array is needed
			
			//fetch list of visit types from resource
			FragmentManager fragmentManager = getSupportFragmentManager();	
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			android.support.v4.app.Fragment fragment=null;
			//show the new fragment -add visit data form
			//todo: do pass both FarmYearlyDataID and vistType to the constructor 
			//done----v
			fragment = new FarmVisitData(FarmYearlyDataID, VisitType);
			fragmentTransaction.replace(TargetPane , fragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
		}		
	}
	
	public void showDatePickerDialog(EditText edt){
	    DialogFragment newFragment = new DatePickerFragment(edt);
	    newFragment.show(getSupportFragmentManager(), "ChooseDate");
	}
	
	public void postData(int actionType, HashMap<String,String>values){//send data excluding image to server
		//This calls teh PostDataAsyncTask
		//verify if this is working and remove it!
		//Toast.makeText(getApplicationContext(), "Post Test pending..!", Toast.LENGTH_LONG).show();
		glbActionType=actionType; //will be used in results
		int TargetPane;
		
		if (mTwoPane) {
	          TargetPane=R.id.rightFrame;
		}
		else
		{
		      TargetPane=R.id.leftFrame;
				
		}
		String addr=null;
		int action=-1;
		switch ( actionType){
		 case    visitAD_HOC:
		 case     visitMANUAL_LAND_PREP:
		 case     visitRIP:
		 case     visitPLOUGH:
		 case     visitDISKING:
		 case     visitSEED_DISTRIBUTION:
		 case     visitPLANTING:
		 case     visitGERMINATION:
		 case     visitWEED1:
		 case     visitWEED2:
		 case     visitWEED3:
		 case     visitYIELD_FORECAST:
		 case     visitHARVESTYIELD:
		 case     visitTHRESHINGINFO:
		 case     visitDEMO_PLOT_VISIT:
			 addr=BaseURL+"?action=ADD_FARM_VISIT_DATA";
			 action=actionType;
			 break;
		 case   actionPOST_ADD_FARMER:
			 addr=BaseURL+"?action=ADD_FARMER_DATA";
			 action=actionType;
			 glbActionType=action;
			 break;
		 case actionPOST_ADD_FARM:
			 addr=BaseURL+"?action=ADD_FARM_DATA";
			 action=actionType;
			 break;
		 case actionCHANGE_PASSWORD:
			 addr=BaseURL+"?action=CHANGE_PASSWORD"; //was chnged from actionCHANGE_PASSWORD
			 action=actionType;
			 break;
		 case actionFETCH_FARMER_FARM_DATA4_OFFLINE:
			 addr=BaseURL+"?action=FETCH_FARMER_FARM_DATA4_OFFLINE";
			 action=actionType;
			 break;
		 case actionUPLOAD_SAVED_VISIT_DATA:
			 addr=BaseURL+"?action=UPLOAD_SAVED_VISIT_DATA";
			 action=actionType;
			 break;	 
		 case actionPOST_ADD_FARMS_YEARLY_DATA:
			 addr=BaseURL+"?action=ADD_FARMS_YEARLY_DATA";
			 action=actionType;
			 glbActionType=action;
			 break;
			 
		}
		Log.v("postData msg","OfflineState="+OfflineState);
		if (MainActivity.OfflineState ==OnLineMode){
			Log.v("postData msg","Online_");
			new PostDataTask(action,addr,MainActivity.this, values).execute();
		}
		if (MainActivity.OfflineState ==OffLineMode){  //is offline. Currently saves only visit data
			//pending: update to save other types of data too eg farmer, farm etc
			Log.v("postData msg","OFFline_processing");
			switch ( actionType){
			 case    visitAD_HOC:
			 case     visitMANUAL_LAND_PREP:
			 case     visitRIP:
			 case     visitPLOUGH:
			 case     visitDISKING:
			 case     visitSEED_DISTRIBUTION:
			 case     visitPLANTING:
			 case     visitGERMINATION:
			 case     visitWEED1:
			 case     visitWEED2:
			 case     visitWEED3:
			 case     visitYIELD_FORECAST:
			 case     visitHARVESTYIELD:
			 case     visitTHRESHINGINFO:
			 case     visitDEMO_PLOT_VISIT:
				 DBAdapter db = new DBAdapter(getApplicationContext());
				db.open();
				String res=db.saveVisitDataOffline(values);
				if (res.endsWith("successOK")){
					Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
					//close the dialog box, or reset the values.
					getSupportFragmentManager().popBackStack();
				}
				else {//
					Toast.makeText(getApplicationContext(), "Sorry, Local Save failed", Toast.LENGTH_LONG).show();
				}
				db.close();
				
				 break;
			
			default:
				Toast.makeText(getApplicationContext(), "Sorry, You can do this online only", Toast.LENGTH_LONG).show();
					 
			}
		}

		
	}
	
	public void doAfterPostData(String result){
		Log.v("doAfterPostData msg", "result= "+result);
		if (result==null){
			Log.v("doAfterPostData msg","resultIsNull");
			Toast.makeText(getApplicationContext(), "AfterPostData: Communications Error occured", Toast.LENGTH_LONG).show();
			return;
		}
		if (result.endsWith("successOK")){
		Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
		//close the dialog box, or reset the values.
		getSupportFragmentManager().popBackStack();
		}
		
		/*
		else {
			Toast.makeText(getApplicationContext(), "Post Err: "+result, Toast.LENGTH_LONG).show();;
			return;
		}
		*/
		Log.v("in post Data","glbactiontype="+glbActionType);
		if (glbActionType ==actionCHANGE_PASSWORD){//actionCHANGE_PASSWORD
			Log.v("in postData chg pw", result);
			if (result.endsWith("successOK")){
				Toast.makeText(getApplicationContext(), "Password is Set", Toast.LENGTH_LONG).show();
				}
			if  (result.endsWith("mismatch:failedOK")){
				Toast.makeText(getApplicationContext(), "Incorrect old password", Toast.LENGTH_LONG).show();
				}
	
		}//if chg passwd
		
		if (glbActionType ==actionFETCH_FARMER_FARM_DATA4_OFFLINE){
			if (result.endsWith("successOK")){
				//do actual saving to local db
				//pending
				System.out.println("about to save...in do_after_post");
				DBAdapter db = new DBAdapter(getApplicationContext());
				db.open();
				db.saveFarmerFarmDataOffline(result);
				
				db.close();
				
				}//if ok
			
		}
		if (glbActionType==actionUPLOAD_SAVED_VISIT_DATA){ //clear local table bc was successful.
			if (result.endsWith("successOK")){
			 DBAdapter db = new DBAdapter(getApplicationContext());
				db.open();
				db.wipeOfflineVisitData();
				db.initOfflineVisitTables();
				db.close();
			}
			if (result.endsWith("failedOK")){
				Toast.makeText(getApplicationContext(), "No data or bad data to upload", Toast.LENGTH_LONG).show();
			}
		}
		if (glbActionType==actionPOST_ADD_FARM){ //clear local table bc was successful.
			if (result.endsWith("successOK")){
				//load the second form.
				UtilityFunctions fxn= new UtilityFunctions ();
				String results[]=fxn.msplit(result, ":");
				int farmID=Integer.valueOf(results[0]);
				showAddFarmSeasonDetailFragment(farmID);
				
			}
		}
		/*
		 *  //will need to pass actionTypes to doAfterPostData to specialize the result handling.

		*/
	}

	public String [] getCurrentLocation(){//call back for fetching GPS coords
		 Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		 String LongLat[]=new String[2];
	        if (location != null) {
	        	LongLat[0]=String.valueOf(location.getLongitude());
	        	LongLat[1]=String.valueOf(location.getLatitude());
	            /*String message = String.format(
	                    "Current Location \n Longitude: %1$s \n Latitude: %2$s",
	                    location.getLongitude(), location.getLatitude()
	            );
	            Toast.makeText(this, message,
	                    Toast.LENGTH_LONG).show();
	                    */
	        	return LongLat;
	        }
	        return null;
	}
	public void takePhoto(Fragment srcFragment){//take picture
		  Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		  String thePath=System.currentTimeMillis() + ".jpg";
		  File f = new File(Environment.getExternalStorageDirectory(),  thePath);
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
	        Uri mUri = Uri.fromFile(f);
	        System.out.println("filepath="+mUri.toString());
	        startActivityForResult(intent, RES_IMAGE_CAPTURE);
	        ((FarmVisitData)srcFragment).setPhotoPath(thePath);
	        
		   /*String captured_image = System.currentTimeMillis() + ".jpg";
		    File file = new File(Environment.getExternalStorageDirectory(), captured_image); 
		    captured_image = file.getAbsolutePath();
		    Uri outputFileUri = Uri.fromFile(file); 
		    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri); 
		    intent.putExtra("return-data", true);  
		    startActivityForResult(intent, RES_IMAGE_CAPTURE);
		    /*
		     *  //this is stock camera code.
		    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
            startActivityForResult(intent, TAKE_PHOTO_CODE);
            */
	}
	public void takePhoto1(){//take picture
		  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
		   String captured_image = System.currentTimeMillis() + ".jpg";
		    File file = new File(Environment.getExternalStorageDirectory(), captured_image); 
		    captured_image = file.getAbsolutePath();
		    Uri outputFileUri = Uri.fromFile(file); 
		    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri); 
		    intent.putExtra("return-data", true);  
		    startActivityForResult(intent, RES_IMAGE_CAPTURE);
		    /*
		     *  //this is stock camera code.
		    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
          startActivityForResult(intent, TAKE_PHOTO_CODE);
          */
	}
	
	public void showAddFarmForm(int FarmerID, String FarmerName){
		int TargetPane;
		
		if (mTwoPane) {
	          TargetPane=R.id.rightFrame;
		}
		else
		{
		      TargetPane=R.id.leftFrame;			
		}
		FragmentManager fragmentManager = getSupportFragmentManager();	
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		android.support.v4.app.Fragment fragment=null;
		//show the new fragment -add visit data form
		//todo: do pass both FarmYearlyDataID and vistType to the constructor 
		//done----v
		fragment = new AddFarmDetailFragment(FarmerID, FarmerName);
		fragmentTransaction.replace(TargetPane , fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
		
	}
	
//	public void fetchDataForSelectedFarmers(String ids){//post a list of IDs, fetch the corresponding farmer + farm data
		
	//}

	public void deleteLocalFarmerData(String ids){ //delete local farmer data previously saved on device
	
		 DBAdapter db = new DBAdapter(getApplicationContext());
		db.open();
		String res=db.deleteFarmerFarmOfflineData(ids);
		db.close();
		if (res.endsWith("successOK")){
			Toast.makeText(getApplicationContext(), "Local Records Deleted", Toast.LENGTH_LONG).show();
			getSupportFragmentManager().popBackStack();
			
		}
		if (res.endsWith("failedOK")){
			Toast.makeText(getApplicationContext(), "Local Deleted FAILED", Toast.LENGTH_LONG).show();
		}
	}
	
	public void resetLocalFarmersDB(){ //delete  and recreate local farms's db, in case it has been corrupted.
		 DBAdapter db = new DBAdapter(getApplicationContext());
			db.open();
			db.wipeOfflineFarmerDatabase();
			db.wipeOfflineVisitData();
			db.initOfflineFarmerFarmTables();
			db.close();
			//if (res.endsWith("successOK")){
				Toast.makeText(getApplicationContext(), "Local DB wiped clean", Toast.LENGTH_LONG).show();
				getSupportFragmentManager().popBackStack();
				
		//	}
			//if (res.endsWith("failedOK")){
				//Toast.makeText(getApplicationContext(), "Local Deleted FAILED", Toast.LENGTH_LONG).show();
			//}
	}
	//**************---^---general call backs***************************/
	
	public void showAddFarmSeasonDetailFragment(int FarmID){
		int TargetPane;
		
		if (mTwoPane) {
	          TargetPane=R.id.rightFrame;
		}
		else
		{
		      TargetPane=R.id.leftFrame;			
		}
		
			
			FragmentManager fragmentManager = getSupportFragmentManager();	
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			android.support.v4.app.Fragment fragment=null;
			//show the new fragment -add farm_season data form
			fragment = new AddFarmSeasonDetailFragment(FarmID, null);//update query 
						//on pphp backend to return farm nanme so i can pass it
			fragmentTransaction.replace(TargetPane , fragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
			
	}
	
	
	//use this to fetch sync data
	class SyncTask extends AsyncTask<String, String, String> {
		private ProgressDialog pd;
		Activity activity;
		int actionType;
		
		String thePath;
		public SyncTask(int action, String path, Activity a){
			thePath=path;
			activity=a;
			this.actionType =action;
			
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
		    }
		
		@Override
		protected void onPreExecute() {
			String msg="connecting";
			if (actionType==actionSYNC){
				msg="Fetching Support Data..";
			}
			if (actionType==actionSEARCH_CURRENT_FARMS){
				msg="Fetching Current Farms..";
			}
		    pd = ProgressDialog.show(activity, "Connecting...",
		            msg);
		}
		
		@Override
		protected void onProgressUpdate(String... item) {
		 
		}
		
		@Override
		protected void onPostExecute(String result) {
			
			if (actionType==actionSYNC){
			doSYNC(result);
			}

			if ((actionType==actionSEARCH_FARMERNAME) || (actionType==actionSEARCH_ADVANCED)){
				doSEARCH_FARMERNAME(result);
			
			}//if action=search Farmer
			
			
			if (actionType==actionSEARCH_FARMERREFERENCENO){
				//consider using doSearch_farmerName again. Give it a more generic name
				if (result.endsWith("failedOK")){
					Toast.makeText(getApplicationContext(), "NO matching records", Toast.LENGTH_LONG).show();
					return;
				}
				if (result.endsWith("successOK")){
					Toast.makeText(activity, "Successful!", Toast.LENGTH_SHORT) .show();
					UtilityFunctions fxn= new UtilityFunctions();
					result=result.replace("successOK","");
				
					
				    FragmentManager fragmentManager = getSupportFragmentManager();	
			        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			    
			        String[] NameArry=null;
			        int IDArry[]=null;
			        
			        //seems the following call does not work---------------------------vvv
			        Object A[]=fxn.create2_1DArrays(result, "</br>", ":");  //convention: 1st col is strings, 2nd col is numbers
			        NameArry=(String[])A[0];
			        IDArry=(int[])A[1];
			        
			           //above may not work bc of applicaitoncontext
			        int TargetPane;
			    	//	android.support.v4.app.Fragment fragment=null;
			    		if (mTwoPane) {
			    	          TargetPane=R.id.rightFrame;
			    		}
			    		else
			    		{
			    		      TargetPane=R.id.leftFrame;
			    				
			    		}
			        	android.support.v4.app.Fragment fragment=null;
			            //fragment=new ListResultFragment(values);
			            fragment=new ListResultFragment(NameArry, IDArry, actionType, PurposeOfSearch);
			            fragmentTransaction.replace(TargetPane , fragment);
			            fragmentTransaction.addToBackStack(null);
			            fragmentTransaction.commit();
					
				}
				else
				{
					if (result.endsWith("OK")){
						Toast.makeText(getApplicationContext(), "Connected but other error occured", Toast.LENGTH_LONG).show();
					}
					else{
						
					Toast.makeText(activity, "A connection error occured. Check Internet Connection", Toast.LENGTH_LONG) .show();
					}
					
				}
			}//if actionSEARCH_FARMERREFERENCENO
			
			if (actionType==actionSEARCH_CURRENT_FARMS){
				if (result.endsWith("failedOK")){
					Toast.makeText(getApplicationContext(), "NO matching records", Toast.LENGTH_LONG).show();
					if (PurposeOfSearch== searchBA_ADD_FARM ){
					//==================================================testing if i can load a blank list
					 FragmentManager fragmentManager = getSupportFragmentManager();	
				        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				        String[] NameArry=null;
				        int IDArry[]=null;
				           
				        int TargetPane;
				    	
				    		if (mTwoPane) {
				    	          TargetPane=R.id.rightFrame;
				    		}
				    		else
				    		{
				    		      TargetPane=R.id.leftFrame;
				    				
				    		}
				        	android.support.v4.app.Fragment fragment=null;
				            fragment=new ListResultFragment(NameArry, IDArry, actionType, PurposeOfSearch);
				            fragmentTransaction.replace(TargetPane , fragment);
				            fragmentTransaction.addToBackStack(null);
				            fragmentTransaction.commit();
						
					//===================================================
					}
					/* */
					return;
				}
				//String listData[][]=null;
				if (result.endsWith("successOK")){
					Toast.makeText(activity, "Successful!", Toast.LENGTH_SHORT) .show();
					UtilityFunctions fxn= new UtilityFunctions();
					result=result.replace("successOK","");
					//listData=fxn.create2DArray(result, "</br>", ":");
					
				    FragmentManager fragmentManager = getSupportFragmentManager();	
			        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			        String[] NameArry=null;
			        int IDArry[]=null;
			        
			        //seems the following call does not work---------------------------vvv
			        Object A[]=fxn.create2_1DArrays(result, "</br>", ":");
			        NameArry=(String[])A[0];
			        IDArry=(int[])A[1];
			        
			           
			        int TargetPane;
			    	
			    		if (mTwoPane) {
			    	          TargetPane=R.id.rightFrame;
			    		}
			    		else
			    		{
			    		      TargetPane=R.id.leftFrame;
			    				
			    		}
			        	android.support.v4.app.Fragment fragment=null;
			            fragment=new ListResultFragment(NameArry, IDArry, actionType, PurposeOfSearch);
			            fragmentTransaction.replace(TargetPane , fragment);
			            fragmentTransaction.addToBackStack(null);
			            fragmentTransaction.commit();
					
				}
				else
				{
					if (result.endsWith("OK")){
						Toast.makeText(getApplicationContext(), "Connected but other error occured", Toast.LENGTH_LONG).show();
					}
					else{
						
					Toast.makeText(activity, "Sorry, operation failed. Please report this", Toast.LENGTH_LONG) .show();
					}		
				}
			}//if actionSEARCH_CURRENT_FARMS
			
		}//on post execute fxn
	
	
		//a test -- a function to make the onpost execute look more readable
		private void doSEARCH_FARMERNAME(String result){
			if (result.endsWith("failedOK")){
				Toast.makeText(getApplicationContext(), "NO matching records", Toast.LENGTH_LONG).show();
				return;
			}
			//String listData[][]=null;
			if (result.endsWith("successOK")){
				Toast.makeText(activity, "Successful!", Toast.LENGTH_SHORT) .show();
				UtilityFunctions fxn= new UtilityFunctions();
				result=result.replace("successOK","");
				//listData=fxn.create2DArray(result, "</br>", ":");
				
			    FragmentManager fragmentManager = getSupportFragmentManager();	
		        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		      //  String[] values = listData[1];
		        String[] NameArry=null;
		        int IDArry[]=null;
		        
		        //seems the following call does not work---------------------------vvv
		        Object A[]=fxn.create2_1DArrays(result, "</br>", ":");//, NameArry, IDArry, arry2);
		        NameArry=(String[])A[0];
		        IDArry=(int[])A[1];
		        
		           //above may not work bc of applicaitoncontext
		        int TargetPane;
		    	//	android.support.v4.app.Fragment fragment=null;
		    		if (mTwoPane) {
		    	          TargetPane=R.id.rightFrame;
		    		}
		    		else
		    		{
		    		      TargetPane=R.id.leftFrame;
		    				
		    		}
		        	android.support.v4.app.Fragment fragment=null;
		            //fragment=new ListResultFragment(values);
		            fragment=new ListResultFragment(NameArry, IDArry, actionType, PurposeOfSearch);
		            fragmentTransaction.replace(TargetPane , fragment);
		            fragmentTransaction.addToBackStack(null);
		            fragmentTransaction.commit();
				
			}
			else
			{
				if (result.endsWith("OK")){
					Toast.makeText(getApplicationContext(), "Connected but other error occured", Toast.LENGTH_LONG).show();
				}
				else{
					
				Toast.makeText(activity, "Sorry, operation failed. Please report this", Toast.LENGTH_LONG) .show();
				} //else --> ends with OK
			}//else-> ends with successOK
		}//doSearchFarmerName
		
	
		private void doSYNC(String result){
			if (actionType==actionSYNC){
				if (result.endsWith("failedOK")){
					Toast.makeText(getApplicationContext(), "NO matching records", Toast.LENGTH_LONG).show();
					return;
				}
				String supportData[][]=null;
				if (result.endsWith("successOK")){
					Toast.makeText(activity, "Successful!", Toast.LENGTH_SHORT) .show();
					//now write to db.
					DBAdapter db = new DBAdapter(getApplicationContext());
					db.open();
					db.prepareForSync();//just added
					UtilityFunctions fxn= new UtilityFunctions();
					result=result.replace("successOK","");
					String tmp[]=fxn.msplit(result,"</br>");	
					//System.out.println(result);
					
					for (int k=0; k<tmp.length; k++)  {  //tested. not crashed. need to verify if correct data returned.				
						//this will iterate for each db table in order:  province, district, adminpost, locality, zone farmergrp
						supportData=fxn.create2DArray(tmp[k], "#", ":");				
						for (int i=0; i<supportData.length; i++){
							if (k==0){
								//System.out.println("Fetched DB Data: "+(k+1)+"- "+ Integer.parseInt(supportData[i][0]) +"  "+ supportData[i][1]+" "+ -1);
								db.insertOneRow(k+1, Integer.parseInt(supportData[i][0]), supportData[i][1], -1); //using k+1 bc is zero based. constants start from 1
							}else{
								//System.out.println("Fetched DB Data: "+(k+1)+"- "+ Integer.parseInt(supportData[i][0])+" "+ supportData[i][1]+" "+ Integer.parseInt(supportData[i][2]));
								db.insertOneRow(k+1, Integer.parseInt(supportData[i][0]), supportData[i][1], Integer.parseInt(supportData[i][2]));
								//
							}
						}//for i 
					}//f
					db.close();
				}
				else
				{
					if (result.endsWith("OK")){
						Toast.makeText(getApplicationContext(), "sync: Connected but other error occured", Toast.LENGTH_LONG).show();
					}
					else{
						
					Toast.makeText(activity, "sync: Sorry, operation failed. Please report this", Toast.LENGTH_LONG) .show();
					}
				}
			}//if action=sync
		}//doSYNCC
	
	}//class SyncTask
	
	//all purpose handler for intents
	  //listen when intent returns
	  @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)	{
		  super.onActivityResult(requestCode, resultCode, data);
	      switch (requestCode) { 
          case RES_IMAGE_CAPTURE: 

              //Log.i( "MakeMachine", "resultCode: " + resultCode );
              switch( resultCode )
              {
                  case Activity.RESULT_CANCELED:
                      Toast.makeText(this, "Camera action Cancelled", Toast.LENGTH_LONG).show();
                      break;
                  case Activity.RESULT_OK:
                      //image storead, now load it in the web
                	  //[[Uri imagePath = getImageUri(); ]]//ca be fxn w ff 2 lines
                	  //File file = new File(Environment.getExternalStorageDirectory() + "/DCIM", CAPTURE_TITLE);
                	  //Uri imgUri = Uri.fromFile(file);
                      //doSomething();
                	  Toast.makeText(this, "Picture Saved-check", Toast.LENGTH_LONG).show();
                      break;
                  }
              break;

      }   
	}
	
	  
	  
	  //offline function equivalents:
	  public void doSearchOffline(int searchType, String searchString){
		  	DBAdapter db= new DBAdapter(getApplication());
		  	db.open();
		  	String result=null;
		  	switch (searchType){
		  	case actionSEARCH_FARMERNAME:
				result=db.fetchLocalFarmerList(searchString);
		  		break;
		  	case actionSEARCH_FARMERREFERENCENO:
		  		result=db.fetchLocalFarmerListByID(searchString);
		  		break;
		  	case actionSEARCH_CURRENT_FARMS:
		  		result=db.fetchLocalFarmList(searchString);
		  		break;
		  	
		  	}
	        
	        db.close();
	        
	        if(result==null || result.trim().equals("") ){
	        	Toast.makeText(getApplicationContext(), "No Local Records", Toast.LENGTH_LONG).show();
	        	return;
	        }
	        
	        
		  UtilityFunctions fxn= new UtilityFunctions();
			//result=result.replace("successOK","");  //comment out or use alternative mechanism
			//listData=fxn.create2DArray(result, "</br>", ":");
			
		    FragmentManager fragmentManager = getSupportFragmentManager();	
	        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	      
	        String[] NameArry=null;
	        int IDArry[]=null;
	        
	        //seems the following call does not work---------------------------vvv
	        Object A[]=fxn.create2_1DArrays(result, "</br>", ":");//, NameArry, IDArry, arry2);
	        NameArry=(String[])A[0];
	        IDArry=(int[])A[1];
	        
	           //above may not work bc of applicaitoncontext
	        int TargetPane;
	    	//	android.support.v4.app.Fragment fragment=null;
	    		if (mTwoPane) {
	    	          TargetPane=R.id.rightFrame;
	    		}
	    		else
	    		{
	    		      TargetPane=R.id.leftFrame;
	    				
	    		}
	        	android.support.v4.app.Fragment fragment=null;
	            //fragment=new ListResultFragment(values);
	            fragment=new ListResultFragment(NameArry, IDArry, searchType, PurposeOfSearch); //searchTyep was actionType previously
	            fragmentTransaction.replace(TargetPane , fragment);
	            fragmentTransaction.addToBackStack(null);
	            fragmentTransaction.commit();
		
	  }
	  
	  
	  public void  doAdvancedSearchOffline(HashMap<String,String>values,  int action, int PurposeOfSearch){
		  
		  DBAdapter db= new DBAdapter(getApplication());
		  db.open();
		  String result=null;
		  result=db.fetchLocalAdvancedSearch(values, action, PurposeOfSearch);//serachType is PurposeOfSearch
		  db.close();
		 
		  
		  
		  UtilityFunctions fxn= new UtilityFunctions();
			//result=result.replace("successOK","");  //comment out or use alternative mechanism
			//listData=fxn.create2DArray(result, "</br>", ":");
			
		    FragmentManager fragmentManager = getSupportFragmentManager();	
	        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	      
	        String[] NameArry=null;
	        int IDArry[]=null;
	        
	        //seems the following call does not work---------------------------vvv
	        Object A[]=fxn.create2_1DArrays(result, "</br>", ":");//, NameArry, IDArry, arry2);
	        NameArry=(String[])A[0];
	        IDArry=(int[])A[1];
	        
	           //above may not work bc of applicaitoncontext
	        int TargetPane;
	    	//	android.support.v4.app.Fragment fragment=null;
	    		if (mTwoPane) {
	    	          TargetPane=R.id.rightFrame;
	    		}
	    		else
	    		{
	    		      TargetPane=R.id.leftFrame;
	    				
	    		}
	        	android.support.v4.app.Fragment fragment=null;
	            //fragment=new ListResultFragment(values);
	            fragment=new ListResultFragment(NameArry, IDArry, action, PurposeOfSearch); //searchTyep was actionType previously
	            fragmentTransaction.replace(TargetPane , fragment);
	            fragmentTransaction.addToBackStack(null);
	            fragmentTransaction.commit();
	}
	
}//main activity
