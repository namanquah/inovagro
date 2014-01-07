package com.inovagro.inovagrofdc1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
	private final int RESIZE_IMAGE_CAPTURED=2021;
	//private final int RESIZE_IMAGE_CAPTUREDorig=2022;
	private String captured_image; //use this to store path of the captured image for use in both TextView/button and onActivityResult fxns
	
	
	static int OfflineState=0; //can be online or offline - used accros the entire application
	static int PurposeOfSearch;    //this is CLOSELY ALLIED TO THE MENU CHOICE in main Menu List. unlike actionType 
	static String CurrentFarmerID;  //short cut- use for addFarm, (farmVist) etc to track current farmer //was an int
	static String CurrentFarmerName;
	private int glbActionType; //action type to be used in results ie after post
	static int MenuType=-1;//is it normal online/offline menu or FarmerManagementMenu etc? perhaps combine w OfflineState
	
	static HashMap<String, String> gblPartIValues;
	static HashMap<String, String> gblPartIIValues;
	static HashMap<String, String> gblPartIIIaValues;
	static HashMap<String, String> gblPartIIIbValues;
	static HashMap<String, String> gblPartIVValues;
	static HashMap<String, String> gblAllSurveyValues; //new and different from that in confirm form
	
	//HashMap<String, String> values = new HashMap<String, String>();
	
	////////////--------v definitions for menu
	public static  String mnuAddFarmer;
    public static  String mnuFarmVisit;
    public static  String mnuSynchronize;
    public static  String mnuChangePassword;
    public static  String mnuExit;
    public static  String mnuAddFarm;
    
    public static  String mnuSurvey2013;
    
    public static  String mnuPlanVisits;
    public static  String mnuViewPlan;
    public static  String mnuGoOnline ;
    public static  String mnuGoOffline;
    public static  String mnuUploadSavedDataMenu;
    public static  String mnuUploadSavedVisitData;
    public static String mnuUploadSavedSurvey2013Data;
    
    public static String mnuPigeonPeaHarvestSurvey;
    public static String mnuPovertyScoreCard;
    public static String mnuUploadPigeionPeaHarvestSurveyData;
    public static String mnuUploadFarmerData;
    public static String mnuUploadFarmData;
    public static String mnuUploadPovertyScoreCardData;
    
    public static String mnuFarmerManagementMenu;
    public static String mnuViewFarmer;
    public static String mnuBackToMainMenu;
    
    
    //see onCreate fxn below where these menus are assigned.
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
       // MainActivity.OfflineState=OnLineMode;  //for initial load, determine its effect
        
        //obtain the menu items that correspond to the strings for use in this code
        Resources res = getResources();
		String [] OnlineMenu = res.getStringArray(R.array.arryOnlineMenu);
		//Resources res = getResources();
		String [] OfflineMenu = res.getStringArray(R.array.arryOfflineMenu);
		String [] FarmerManagementMenu= res.getStringArray(R.array.arryFarmerManagementMenu);
		String [] UploadsMenu= res.getStringArray(R.array.arryUploadsMenu);
		//-----------------V
		 
		//farmer Management Menu
		mnuAddFarmer=FarmerManagementMenu[0];
		mnuViewFarmer=FarmerManagementMenu[1];
		mnuBackToMainMenu=FarmerManagementMenu[2];
		//mnuEditFarmer=FarmerManagementMenu[1];
		
		
		//main menu
		 mnuFarmerManagementMenu=OnlineMenu[0];
		 mnuAddFarm=OnlineMenu[1];
		 mnuFarmVisit=OnlineMenu[2];
		 mnuPlanVisits=OnlineMenu[3];
		 mnuViewPlan=OnlineMenu[4];		 
		 mnuUploadSavedDataMenu=OnlineMenu[5]; 
		 mnuSynchronize=OnlineMenu[6];
		 mnuGoOffline=OnlineMenu[7];
	     mnuChangePassword=OnlineMenu[8];
	     mnuSurvey2013=OnlineMenu[9];
	     mnuPigeonPeaHarvestSurvey=OnlineMenu[10];
	     mnuPovertyScoreCard=OnlineMenu[11];
	     mnuExit=OnlineMenu[12]; 
	     
	     //uploads menu
	     mnuUploadSavedVisitData=UploadsMenu[0];
	     mnuUploadSavedSurvey2013Data=UploadsMenu[1];
	     mnuUploadPigeionPeaHarvestSurveyData=UploadsMenu[2];
	     mnuUploadFarmerData=UploadsMenu[3];
	     mnuUploadFarmData=UploadsMenu[4];
	     mnuUploadPovertyScoreCardData=UploadsMenu[5];
	    
	    //ofline menu
	    mnuGoOnline=OfflineMenu[7] ;
	    
	    //create arrays to hold survey results:
	     gblPartIValues= new HashMap<String, String>();
	     gblPartIIValues= new HashMap<String, String>();
	     gblPartIIIaValues= new HashMap<String, String>();
	     gblPartIIIbValues= new HashMap<String, String>();
		 gblPartIVValues= new HashMap<String, String>();
		 gblAllSurveyValues= new HashMap<String, String>();
		
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
            	fragment=new AddFarmerDetailFragment(ADD_MODE, null);//just added.
            	   fragmentTransaction.replace(TargetPane, fragment); //as .add
            	   fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN); //new addition
                   fragmentTransaction.addToBackStack(null);
                   fragmentTransaction.commit(); 
            }
		  //addition for viewing or editing Farmer data
            if(id.equals(mnuViewFarmer)){         
            	MainActivity.PurposeOfSearch=searchBA_FARMER_DETAIL; //a short circuit. Not passing to fragment and back
            	fragment=new BasicAdvancedSearchFragment(searchBA_FARMER_DETAIL);
            	   fragmentTransaction.replace(TargetPane, fragment); //as .add
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
            	//GOOD CODE.
            	
            	MainMenuList mml=(MainMenuList)frag;
            	mml.changeMenu(mnuGoOnline, position);
            	//change the text in the menu to Offline
            	//change the background from Blue to Red
            	
            	
            	/*  //bad code. wanted to see if i could initiallize new menu so i can use the back btn
               fragment = new MainMenuList();  
            	//MainMenuList mml=(MainMenuList)fragment;
            	//mml.changeMenu(mnuGoOnline, position);
            	
                //fragmentTransaction.add(R.id.leftFrame, fragment);                
                fragmentTransaction.replace(R.id.leftFrame, fragment); 
                fragmentTransaction.addToBackStack(null);
               
                fragmentTransaction.commit();
                ((MainMenuList)fragment).changeMenu(mnuGoOnline, position);
                
                */
                return;
                
            	
            }
            if (id.equals(mnuGoOnline)){
            	OfflineState=OnLineMode;
            	MainMenuList mml=(MainMenuList)frag;
            	mml.changeMenu(mnuGoOffline, position);
            	//Toast.makeText(getApplicationContext(), "Position is "+position, Toast.LENGTH_LONG).show();
            	//change the text in the menu to Online
            	//change the background from Red to Blue
            }
            //mnuUploadSavedVisitData
            if (id.equals(mnuUploadSavedVisitData)){
            	HashMap<String,String> data= new HashMap<String, String>();
            	String strData;
            	DBAdapter db = new DBAdapter(getApplicationContext());
 				db.open();
 				strData =db.uploadSavedVisitData_getInsertValuesPart();
 				db.close();
            	data.put("valuesPart", strData);
            	postData( actionUPLOAD_SAVED_VISIT_DATA, data); //fetch the data in the postData or PostThread
            
            }
            
            if (id.equals(mnuUploadSavedSurvey2013Data)){
            	HashMap<String,String> data= new HashMap<String, String>();
            	String strData;
            	DBAdapter db = new DBAdapter(getApplicationContext());
 				db.open();
 				strData =db.uploadSavedSurvey2013Data_getInsertValuesPart();
 				db.close();
            	data.put("valuesPart", strData);
            	postData( actionUPLOAD_SAVED_SURVEY2013_DATA, data); //fetch the data in the postData or PostThread
            
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
            if(id.equals(mnuSurvey2013)){
            	showSuveyAug2013Fragment();
            	/*
            	//short circuit, not passing to fragment and back:
            	glbActionType =actionSURVEY2013;
            	//initialize the values array to hold new data (make it empty). 
            	gblPartIValues.clear();
            	gblPartIIValues.clear();
            	gblPartIIIaValues.clear();
            	gblPartIIIbValues.clear();
            	gblPartIVValues.clear();
            	  
           	   Calendar cal= Calendar.getInstance();
           	   	long timeStamp=cal.getTimeInMillis();
           	   	String uniqueID=""+timeStamp+"_"+Login.UserID;
           	   	gblPartIValues.put("SurveyDataID", uniqueID);  //will not be overwritten inspite of back and forth while collecting data. will be lost if new data collected again.
            	fragment=new SurveyBackgroundFragment();
            	   fragmentTransaction.replace(TargetPane, fragment); //as .add
                   fragmentTransaction.addToBackStack(null);
                   fragmentTransaction.commit(); */
            }
            /*
            if(id.equals(mnuPigeonPeaHarvestSurvey)){
            	//short circuit, not passing to fragment and back:
            	glbActionType =actionPIGEONPEA_HARVEST_SURVEY;
            	fragment=new SurveyPigeonPeaHarvestFragment();
            	   fragmentTransaction.replace(TargetPane, fragment); //as .add
                   fragmentTransaction.addToBackStack(null);
                   fragmentTransaction.commit(); 
            } */
            if(id.equals(mnuPigeonPeaHarvestSurvey)){        //search for farmer for pegionPea      
            	//short circuit, not passing to fragment and back:
            	MainActivity.PurposeOfSearch=searchPIGEONPEA_HARVEST_SURVEY;
            	fragment=new BasicAdvancedSearchFragment(searchPIGEONPEA_HARVEST_SURVEY);
            	   fragmentTransaction.replace(TargetPane, fragment); //as .add
                   fragmentTransaction.addToBackStack(null);
                   fragmentTransaction.commit(); 
            }
            
            //mnuUploadPigeionPeaHarvestSurveyData
            if (id.equals(mnuUploadPigeionPeaHarvestSurveyData)){
            	HashMap<String,String> data= new HashMap<String, String>();
            	String strData;
            	DBAdapter db = new DBAdapter(getApplicationContext());
 				db.open();
 				strData =db.uploadSavedPigeionPeaHarvestData_getInsertValuesPart();
 				db.close();
            	data.put("valuesPart", strData);
            	postData( actionUPLOAD_SAVED_PIGEONPEA_HARVEST_DATA, data); //fetch the data in the postData or PostThread
            
            }
            if (id.equals(mnuUploadFarmerData)){
            	HashMap<String,String> data= new HashMap<String, String>();
            	String strData;
            	DBAdapter db = new DBAdapter(getApplicationContext());
 				db.open();
 				strData =db.uploadSavedFarmerData_getInsertValuesPart();
 				db.close();
            	data.put("valuesPart", strData);
            	postData( actionUPLOAD_SAVED_FARMER_DATA, data); //fetch the data in the postData or PostThread
            
            }
            //uploadSavedFarmData_getInsertValuesPart
            if (id.equals(mnuUploadFarmData)){
            	HashMap<String,String> data= new HashMap<String, String>();
            	String strData;
            	DBAdapter db = new DBAdapter(getApplicationContext());
 				db.open();
 				strData =db.uploadSavedFarmData_getInsertValuesPart();				
            	data.put("valuesPart1", strData);//farmData
            	strData =db.uploadSavedFarmYearlyData_getInsertValuesPart();				
            	data.put("valuesPart2", strData);//farmData
            	
            	db.close();
            	postData( actionUPLOAD_SAVED_FARM_DATA, data); //fetch the data in the postData or PostThread
            
            }
            if (id.equals(mnuFarmerManagementMenu)){
            	MainActivity.MenuType=FarmerManagementMenuType;
            	MainMenuList mml=(MainMenuList)frag;
            	mml.changeMenu(mnuFarmerManagementMenu, position);
            	//Toast.makeText(getApplicationContext(), "Position is "+position, Toast.LENGTH_LONG).show();
            	//change the text in the menu to Online
            	//change the background from Red to Blue
            }
            if (id.equals(mnuBackToMainMenu)){  
            	MainActivity.MenuType=-1;  //this is a hack, to allow menu to return to either the previous online or offline state.
            	MainMenuList mml=(MainMenuList)frag;
            	mml.changeMenu(mnuBackToMainMenu, position);
            	//Toast.makeText(getApplicationContext(), "Position is "+position, Toast.LENGTH_LONG).show();
            	//change the text in the menu to Online
            	//change the background from Red to Blue
            }
            //mnuUploadSavedDataMenu
            if (id.equals(mnuUploadSavedDataMenu)){
            	MainActivity.MenuType=UploadSavedDataMenuType;
            	MainMenuList mml=(MainMenuList)frag;
            	mml.changeMenu(mnuUploadSavedDataMenu, position);
            	//Toast.makeText(getApplicationContext(), "Position is "+position, Toast.LENGTH_LONG).show();
            	//change the text in the menu to Online
            	//change the background from Red to Blue
            }
            
            //PovertyScoreCard   
            if (id.equals(mnuPovertyScoreCard)){
            	//short circuit, not passing to fragment and back:
            	MainActivity.PurposeOfSearch=searchPOVERTY_SCORE_CARD;
            	fragment=new BasicAdvancedSearchFragment(searchPOVERTY_SCORE_CARD);
            	   fragmentTransaction.replace(TargetPane, fragment); //as .add
                   fragmentTransaction.addToBackStack(null);
                   fragmentTransaction.commit();
            }
            
            //mnuUploadPovertyScoreCardData
            if (id.equals(mnuUploadPovertyScoreCardData)){
            	HashMap<String,String> data= new HashMap<String, String>();
            	String strData;
            	DBAdapter db = new DBAdapter(getApplicationContext());
 				db.open();
 				strData =db.uploadSavedPovertyScoreCardData_getInsertValuesPart();				
            	data.put("valuesPart", strData);//farmData
            	
            	db.close();
            	postData( actionUPLOAD_SAVED_POVERTY_SCORE_CARD_DATA, data); //fetch the data in the postData or PostThread
            
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
		//searchType is either actionSEARCH_FARMERNAME or actionSEARCH_FARMERREFERENCENO
		//fxn does not receive PurposeOfSearch, even though calling fxn has that info. 
		//make use of the global value of PurposeOfSearch
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
		UtilityFunctions uf= new UtilityFunctions();
		
		searchString=uf.urlEncode(searchString); //added to encode data passed. to enable search including spaces. Sept 15,2012, NNA
		//the encoding has not been done for the advanced case. Do encode the tmp variable.
		
		if (searchType==actionSEARCH_FARMERNAME){
		 addr=BaseURL+"?action=SEARCH_FARMERS_BY_NAME&searchString="+searchString+"&CurrentUserID="+Login.UserID;
		 action=searchType;//actionSEARCH_FARMERNAME;
		}
		if (searchType==actionSEARCH_FARMERREFERENCENO){
			 addr=BaseURL+"?action=SEARCH_FARMERS_BY_REFNO&searchString="+searchString+"&CurrentUserID="+Login.UserID;
			 action=searchType;//actionSEARCH_FARMERREFERENCENO;
			}
		if (MainActivity.PurposeOfSearch ==view_PLANNED_VISITS){   
			doSearchOffline(searchType, searchString);
			return;
		}
		/*   //perhaps not needed. Look at the returned data instead.
		if (MainActivity.PurposeOfSearch==searchBA_FARMER_DETAIL){
			addr=BaseURL+"?action=SEARCH_FARMERS_BY_REFNO&searchString="+searchString;
			 action=searchType;//actionSEARCH_FARMERREFERENCENO;
		}*/
		
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
			String addr=BaseURL+"?action=SEARCH_ADVANCED"+tmp+"&currentUserID="+Login.UserID;
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

	public void fetchListOfFarms(int searchType, String FarmerID){
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
		 addr=BaseURL+"?action=SEARCH_CURRENT_FARMS&searchString="+FarmerID+ "&CurrentUserID="+Login.UserID;
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
	public void showVisitTypes(int actionType, String FarmYearlyDataID){
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
	public void showVisitForm(int actionType, String FarmYearlyDataID, int VisitType){
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
			 addr=BaseURL+"?action=FETCH_FARMER_FARM_DATA4_OFFLINE&CurrentUserID="+Login.UserID;
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
		 case actionPOST_AUGUST2013_SURVEY:
			 addr=BaseURL+"?action=POST_AUGUST2013_SURVEY";
			 action=actionType;
			 glbActionType=action;
			 break;	
		 case actionUPLOAD_SAVED_SURVEY2013_DATA:
			 addr=BaseURL+"?action=UPLOAD_SAVED_SURVEY2013_DATA";
			 action=actionType;
			 glbActionType=action;
			 break;
		 case actionPIGEONPEA_HARVEST_SURVEY:
			 addr=BaseURL+"?action=PIGEONPEA_HARVEST_SURVEY";
			 action=actionType;
			 glbActionType=action;
			 break;
		 case actionUPLOAD_SAVED_PIGEONPEA_HARVEST_DATA:
			 addr=BaseURL+"?action=UPLOAD_SAVED_PIGEONPEA_HARVEST_DATA";
			 action=actionType;
			 glbActionType=action;
			 break;
		 case actionUPLOAD_SAVED_FARMER_DATA:
			 addr=BaseURL+"?action=UPLOAD_SAVED_FARMER_DATA";
			 action=actionType;
			 glbActionType=action;
			 break;
		 case actionUPLOAD_SAVED_FARM_DATA:
			 addr=BaseURL+"?action=UPLOAD_SAVED_FARM_DATA";
			 action=actionType;
			 glbActionType=action;
			 break;
		 case actionSAVE_POVERTY_SCORE_CARD:
			 addr=BaseURL+"?action=SAVE_POVERTY_SCORE_CARD";
			 action=actionType;
			 glbActionType=action;
			 break;
		 case actionUPLOAD_SAVED_POVERTY_SCORE_CARD_DATA:
			 addr=BaseURL+"?action=UPLOAD_SAVED_POVERTY_SCORE_CARD_DATA";
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
			DBAdapter db;
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
			 
				  db = new DBAdapter(getApplicationContext());
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
			 case actionPOST_AUGUST2013_SURVEY:
				  db = new DBAdapter(getApplicationContext());
					db.open();
					String res1=db.saveSurvey2013DataOffline(values);
					if (res1.endsWith("successOK")){
						Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
						//close the dialog box, or reset the values.
						//getSupportFragmentManager().popBackStack();
						showSuveyAug2013Fragment();
					}
					else {//
						Toast.makeText(getApplicationContext(), "Sorry, Local Save failed", Toast.LENGTH_LONG).show();
					}
					db.close();

				 break;
			 case actionPIGEONPEA_HARVEST_SURVEY:
				 db = new DBAdapter(getApplicationContext());
					db.open();
					String res2=db.savePigeionPeaHarvestDataOffline(values);
					if (res2.endsWith("successOK")){
						Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
						//close the dialog box, or reset the values.
						getSupportFragmentManager().popBackStack();
						//showSuveyAug2013Fragment();
					}
					else {//
						Toast.makeText(getApplicationContext(), "Sorry, Local Save failed", Toast.LENGTH_LONG).show();
					}
					db.close();

				 break;
				 
			 case actionPOST_ADD_FARMER:
				 db = new DBAdapter(getApplicationContext());
					db.open();
					String res4=db.saveFarmerDataOffline(values);
					db.close();
					if (res4.endsWith("successOK")){
						Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
						//close the dialog box, or reset the values.
						getSupportFragmentManager().popBackStack();
						if (glbActionType==actionPOST_ADD_FARMER){
							//pop 2nd time bc of confirm screen
							getSupportFragmentManager().popBackStack();
						}
						//showSuveyAug2013Fragment();
					}
					else {//
						Toast.makeText(getApplicationContext(), "Sorry, Local Save failed", Toast.LENGTH_LONG).show();
					}
					
				//	db.close();

				 break;	
				 
			 case actionPOST_ADD_FARM:
				 db = new DBAdapter(getApplicationContext());
					db.open();
					String res3=db.saveFarmDataOffline(values);
					db.close();
					if (res3.endsWith("successOK")){
						//load the second form.
						System.out.println("in main-offline: actionPOST_ADD_FARM- result="+res3);
						UtilityFunctions fxn= new UtilityFunctions ();
						String results[]=fxn.msplit(res3, ":"); ///---------->the problem is here!
						String farmID=results[0];
						//int farmID=Integer.valueOf(results[0]);
						showAddFarmSeasonDetailFragment(farmID);
						
					}
					
//					if (res3.endsWith("successOK")){
//						Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
//						//close the dialog box, or reset the values.
//						getSupportFragmentManager().popBackStack();
//						//showSuveyAug2013Fragment();
//					}
					else {
						Toast.makeText(getApplicationContext(), "Sorry, Local Save failed", Toast.LENGTH_LONG).show();
					}
					//db.close();

				 break;	
			 case actionPOST_ADD_FARMS_YEARLY_DATA:
				 //working on this!
				 
				 db = new DBAdapter(getApplicationContext());
					db.open();
					String res5=db.saveFarmYearlyDataOffline(values);
					db.close();
					if (res5.endsWith("successOK")){
						Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
						//close the dialog box, or reset the values.
						getSupportFragmentManager().popBackStack();
						getSupportFragmentManager().popBackStack();
						
					}
					else {//
						Toast.makeText(getApplicationContext(), "Sorry, Local Save failed", Toast.LENGTH_LONG).show();
					}
					//db.close();
				 break;
				
			 case actionSAVE_POVERTY_SCORE_CARD:
				 db = new DBAdapter(getApplicationContext());
					db.open();
					String res6=db.savePovertyScoreCardDataOffline(values);
					db.close();
					if (res6.endsWith("successOK")){
						Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
						//close the dialog box, or reset the values.
						getSupportFragmentManager().popBackStack();
						getSupportFragmentManager().popBackStack();
						
					}
					else {//
						Toast.makeText(getApplicationContext(), "Sorry, Local Save failed", Toast.LENGTH_LONG).show();
					}
					db.close();
				 
				 break;
				 
				 
			default:
				Toast.makeText(getApplicationContext(), "Sorry, You can do this online only", Toast.LENGTH_LONG).show();
					 
			}//switch
		} //offline mode

		
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
		if (glbActionType==actionPOST_ADD_FARMER){
			//pop 2nd time bc of confirm screen
			getSupportFragmentManager().popBackStack();
		}
		
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
				String localSave=db.saveFarmerFarmDataOffline(result);
				
				db.close();
				if (localSave.endsWith("successOK")){
					Toast.makeText(getApplicationContext(), "Saved Locally", Toast.LENGTH_LONG).show();
				}
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
				System.out.println("in main: actionPOST_ADD_FARM- result="+result);
				UtilityFunctions fxn= new UtilityFunctions ();
				String results[]=fxn.msplit(result, ":");
				String farmID=results[0];
				//int farmID=Integer.valueOf(results[0]);
				showAddFarmSeasonDetailFragment(farmID);
				
			}
		}
		//actionPOST_AUGUST2013_SURVEY
		if (glbActionType==actionPOST_AUGUST2013_SURVEY){ //pop all forms
			
			if (result.endsWith("successOK")){
				getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                
				
			//	-->showSuveyAug2013Fragment();
				//pop all forms and show blank new form
				//getSupportFragmentManager().popBackStack();
				//getSupportFragmentManager().popBackStack(arg0, arg1)
				//perhaps pop enought and wipe the old values.
				
				/*
				glbActionType =actionSURVEY2013;
            	//initialize the values array to hold new data (make it empty). 
            	gblPartIValues.clear();
            	gblPartIIValues.clear();
            	gblPartIIIaValues.clear();
            	gblPartIIIbValues.clear();
            	gblPartIVValues.clear();
            	  
           	   Calendar cal= Calendar.getInstance();
           	   	long timeStamp=cal.getTimeInMillis();
           	   	String uniqueID=""+timeStamp+"_"+Login.UserID;
           	   	gblPartIValues.put("SurveyDataID", uniqueID);  //will not be overwritten inspite of back and forth while collecting data. will be lost if new data collected again.
           	   	  fragment=new SurveyBackgroundFragment();
            	   fragmentTransaction.replace(TargetPane, fragment); //as .add
                   fragmentTransaction.addToBackStack(null);
                   fragmentTransaction.commit(); 
				*/
				
			}//if successOK
		}
		
		if (glbActionType==actionUPLOAD_SAVED_SURVEY2013_DATA){ //clear local table bc was successful.
			if (result.endsWith("successOK")){
			 DBAdapter db = new DBAdapter(getApplicationContext());
				db.open();
				db.wipeOfflineSurvey2013Data();
				db.initOfflineSurvey2013Tables();
				db.close();
			}
			if (result.endsWith("failedOK")){
				Toast.makeText(getApplicationContext(), "No data or bad data to upload", Toast.LENGTH_LONG).show();
			}
		}
		//upload farm
		if (glbActionType==actionUPLOAD_SAVED_FARM_DATA){ //clear local table bc was successful.
			if (result.endsWith("successOK")){
			 DBAdapter db = new DBAdapter(getApplicationContext());
				db.open();
				//db.wipeOfflineFarmData_new();
				//db.initOfflineFarmTables();
				db.close();
			}
			if (result.endsWith("failedOK")){
				Toast.makeText(getApplicationContext(), "No data or bad data to upload", Toast.LENGTH_LONG).show();
			}
		}
		//upload farmer data
		if (glbActionType==actionUPLOAD_SAVED_FARMER_DATA){ //clear local table bc was successful.
			if (result.endsWith("successOK")){
			 DBAdapter db = new DBAdapter(getApplicationContext());
				db.open();
				//db.wipeOfflineFarmerData_new();
				//db.initOfflineFarmTables();
				db.close();
			}
			if (result.endsWith("failedOK")){
				Toast.makeText(getApplicationContext(), "No data or bad data to upload", Toast.LENGTH_LONG).show();
			}
		}
		//upload pigeonPea Harvest data
		if (glbActionType==actionUPLOAD_SAVED_PIGEONPEA_HARVEST_DATA){ //clear local table bc was successful.
			if (result.endsWith("successOK")){
			 DBAdapter db = new DBAdapter(getApplicationContext());
				db.open();
				db.wipeOfflinePigeionPeaHarvestData();
				db.initOfflinePigeionPeaHarvestTables();
				db.close();
			}
			if (result.endsWith("failedOK")){
				Toast.makeText(getApplicationContext(), "No data or bad data to upload", Toast.LENGTH_LONG).show();
			}
		}
		
		//UPLOAD_SAVED_POVERTY_SCORE_CARD_DATA
		if (glbActionType==actionUPLOAD_SAVED_POVERTY_SCORE_CARD_DATA){ //clear local table bc was successful.
			if (result.endsWith("successOK")){
			 DBAdapter db = new DBAdapter(getApplicationContext());
				db.open();
				db.wipeOfflinePovertyScoreCardData();
				db.initOfflinePovertyScoreCardDataTables();
				db.close();
			}
			if (result.endsWith("failedOK")){
				Toast.makeText(getApplicationContext(), "No data or bad data to upload", Toast.LENGTH_LONG).show();
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
	public void takePhoto(Fragment srcFragment, View v, String prefix){//take picture
		
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		  String thePath=prefix+"_"+System.currentTimeMillis() + ".jpg";
		  //File f = new File(Environment.getExternalStorageDirectory(),  thePath);
	       // intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f)); //take this out if i intend to use onactivityResult 2 save file	        
	        //Uri mUri = Uri.fromFile(f);
	        //System.out.println("filepath="+mUri.toString()); 
	        intent.putExtra("thePath", thePath);
	        captured_image=thePath;  //this is a class variable!
	        startActivityForResult(intent, RESIZE_IMAGE_CAPTURED);
	        
	        ((TextView)v).setText(thePath);
	        
		   
	}
	public void takePhoto1(){//take picture
		  Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
		  /*
		  String captured_image = System.currentTimeMillis() + ".jpg";
		    File file = new File(Environment.getExternalStorageDirectory(), captured_image); 
		    captured_image = file.getAbsolutePath();
		    Uri outputFileUri = Uri.fromFile(file); 
		    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri); 
		    intent.putExtra("return-data", true);  */  
		    startActivityForResult(intent, RES_IMAGE_CAPTURE);
		    /*
		     *  //this is stock camera code.
		    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
          startActivityForResult(intent, TAKE_PHOTO_CODE);
          */
	}
	
	public void showAddFarmForm(String FarmerID, String FarmerName){
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
			db.wipeOfflineFarmerFarmDatabase();
			db.wipeOfflineVisitData();
			//db.initOfflineFarmerFarmTables();  
			db.close();
			//if (res.endsWith("successOK")){
				Toast.makeText(getApplicationContext(), "Local DB wiped clean", Toast.LENGTH_LONG).show();
				getSupportFragmentManager().popBackStack();
				
		//	}
			//if (res.endsWith("failedOK")){
				//Toast.makeText(getApplicationContext(), "Local Deleted FAILED", Toast.LENGTH_LONG).show();
			//}
	}
	
	public void showNextSurveyForm(int FormPart){ //show partI, IIa, IIb etc
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
		//
		switch  (FormPart){
		/*
		case surveyPartI: 
			fragment = new SurveyProductionDB1();
			break;*/
		case surveyPartII: 
			//Toast.makeText(getApplicationContext(), "show assets-2", Toast.LENGTH_LONG).show();
			fragment = new SurveyAssetsOwnedFragment();
			break;
		case surveyPartIIIa: 
			//Toast.makeText(getApplicationContext(), "show prod db-3a", Toast.LENGTH_LONG).show();
			fragment = new SurveyProductionDB1();
			break;
		case surveyPartIIIb: 
			//Toast.makeText(getApplicationContext(), "show prod db-3b", Toast.LENGTH_LONG).show();
			fragment = new SurveyProductionDB2();
			break;
		case surveyPartIV: 
			fragment = new SurveyMarketTradeCreditFragment();
			break;
		case surveyConfirmData:
			fragment = new SurveyConfirmData();
			break;
		}
		fragmentTransaction.replace(TargetPane , fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
	
	public void showPigeonPeaHarvestForm(String FarmerID, String FarmerName){
		int TargetPane;
		
		if (mTwoPane) {
	          TargetPane=R.id.rightFrame;
		}
		else
		{
		      TargetPane=R.id.leftFrame;			
		}
		
		
		 //double check that FarmYearlyDataID is being used!
			//call a different constructor bc not all the array is needed
			
			//fetch list of visit types from resource
			FragmentManager fragmentManager = getSupportFragmentManager();	
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			android.support.v4.app.Fragment fragment=null;
			//show the new fragment -add visit data form
			//todo: do pass both FarmYearlyDataID and vistType to the constructor 
			//done----v
			fragment = new SurveyPigeonPeaHarvestFragment(FarmerID, FarmerName);
			fragmentTransaction.replace(TargetPane , fragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
				
	}
	
	public void showFarmerDetail(String FarmerID){
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
		//if (searchType==actionFETCH_FARMER_DETAILS){
		 addr=BaseURL+"?action=FETCH_FARMER_DETAILS&searchString="+FarmerID;
		 action=actionFETCH_FARMER_DETAILS; //actionSEARCH_CURRENT_FARMS
		 CurrentFarmerID=FarmerID;  //shortCircuit. Using a static globl var
		//}

		
		//new SyncTask(action,addr,MainActivity.this).execute();
		if (OfflineState ==OnLineMode){
			new SyncTask(action,addr,MainActivity.this).execute();
		//do not use the ff: was for dummy testing
		}
		else{ //is offline, search locally
			
			doSearchOffline(actionFETCH_FARMER_DETAILS, String.valueOf(FarmerID));
		}
		
		/*
		 //double check that FarmYearlyDataID is being used!
			//call a different constructor bc not all the array is needed
			
			//fetch list of visit types from resource
			FragmentManager fragmentManager = getSupportFragmentManager();	
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			android.support.v4.app.Fragment fragment=null;
			//show the new fragment -add visit data form
			//todo: do pass both FarmYearlyDataID and vistType to the constructor 
			//done----v
			fragment = new SurveyPigeonPeaHarvestFragment(FarmerID, FarmerName);
			fragmentTransaction.replace(TargetPane , fragment);
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.commit();
			*/	
	}
	
	public void showConfirmationForm(HashMap<String, String> Data, int postAction){
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
			fragment = new ConfirmationFragment(Data, postAction);
		fragmentTransaction.replace(TargetPane , fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();

	}
	
	public void showPovertyScoreCard(String FarmerID){
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
		intializeSurveyData();
		fragment = new PovertyScoreCard(FarmerID);
		fragmentTransaction.replace(TargetPane , fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();	
	}
	
	//**************---^---general call backs***************************/
	
	public void showAddFarmSeasonDetailFragment(String FarmID){
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
			glbActionType =actionPOST_ADD_FARMS_YEARLY_DATA;
					
        	//initialize the values array to hold new data (make it empty). 
        	
        	  
       	   	  fragment=new AddFarmSeasonDetailFragment(FarmID, CurrentFarmerName );
        	   fragmentTransaction.replace(TargetPane, fragment); //as .add
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.commit(); 
			
	}
	
	public void showSuveyAug2013Fragment(){
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
			//short circuit, not passing to fragment and back:
        	glbActionType =actionSURVEY2013;
        	//initialize the values array to hold new data (make it empty). 
        	gblPartIValues.clear();
        	gblPartIIValues.clear();
        	gblPartIIIaValues.clear();
        	gblPartIIIbValues.clear();
        	gblPartIVValues.clear();
        	intializeSurveyData();  
        	/*  //now collecting this timestamp info only at saving time. 
       	   Calendar cal= Calendar.getInstance();
       	   	long timeStamp=cal.getTimeInMillis();
       	   	String uniqueID=""+timeStamp+"_"+Login.UserID;
       	   	gblPartIValues.put("SurveyDataID", uniqueID);  //will not be overwritten inspite of back and forth while collecting data. will be lost if new data collected again.
        	*/
       	   	fragment=new SurveyBackgroundFragment();
        	   fragmentTransaction.replace(TargetPane, fragment); //as .add
               fragmentTransaction.addToBackStack(null);
               fragmentTransaction.commit();
			
	}
	
	public void intializeSurveyData(){
		gblPartIValues.clear();
    	gblPartIIValues.clear();
    	gblPartIIIaValues.clear();
    	gblPartIIIbValues.clear();
    	gblPartIVValues.clear();
    	
    	//gblAllSurveyValues.put("UserID","");
    	gblAllSurveyValues.put("SurveyDataID","");
    	gblAllSurveyValues.put("Q1a1","");
    	gblAllSurveyValues.put("Q1a2","");
    	gblAllSurveyValues.put("Q1b","-1");
    	gblAllSurveyValues.put("Q1c","");
    	gblAllSurveyValues.put("Q1d","-1");
    	gblAllSurveyValues.put("Q1d1","");
    	gblAllSurveyValues.put("Q1f0","-1");
    	gblAllSurveyValues.put("Q1f1","-1");
    	gblAllSurveyValues.put("Q1f2","-1");
    	gblAllSurveyValues.put("Q1f3","-1");
    	gblAllSurveyValues.put("Q1f4","-1");
    	gblAllSurveyValues.put("Q1f5","-1");
    	gblAllSurveyValues.put("Q1g","-1");
    	gblAllSurveyValues.put("Q1h","-1");
    	gblAllSurveyValues.put("Q1i1","0");
    	gblAllSurveyValues.put("Q1i2","0");
    	gblAllSurveyValues.put("Q1i3","0");
    	gblAllSurveyValues.put("Q1i4","0");
    	gblAllSurveyValues.put("Q1i5","0");
    	gblAllSurveyValues.put("Q1j","-1");
    	gblAllSurveyValues.put("Q1k","");
    	gblAllSurveyValues.put("Q2a","-1");
    	gblAllSurveyValues.put("Q2b","-1");
    	gblAllSurveyValues.put("Q2c","-1");
    	gblAllSurveyValues.put("Q2dMFIQ1","-1");
    	gblAllSurveyValues.put("Q2dMFIQ2","-1");
    	gblAllSurveyValues.put("Q2dMFIQ3","-1");
    	gblAllSurveyValues.put("Q2dMFIQ4","-1");
    	gblAllSurveyValues.put("Q2dMFIQ5","-1");
    	gblAllSurveyValues.put("Q2dMFIQ6","-1");
    	gblAllSurveyValues.put("Q2dMFIQ7","-1");
    	gblAllSurveyValues.put("Q2dMFIQ8","-1");
    	gblAllSurveyValues.put("Q2dMFIQ9","-1");
    	gblAllSurveyValues.put("Q2dMFIQ10","-1");
    	gblAllSurveyValues.put("Q3a","-1");
    	gblAllSurveyValues.put("Q3b","-1");
    	gblAllSurveyValues.put("Q3c1","");
    	gblAllSurveyValues.put("Q3c2","");
    	gblAllSurveyValues.put("Q3c3","");
    	gblAllSurveyValues.put("Q3d","");
    	gblAllSurveyValues.put("Q3e1","");
    	gblAllSurveyValues.put("Q3e2","");
    	gblAllSurveyValues.put("Q3e3","");
    	gblAllSurveyValues.put("Q3f1","");
    	gblAllSurveyValues.put("Q3f2","");
    	gblAllSurveyValues.put("Q3f3","");
    	gblAllSurveyValues.put("Q3f4","");
    	gblAllSurveyValues.put("Q3f5","");
    	gblAllSurveyValues.put("Q3f6","");
    	gblAllSurveyValues.put("Q3g1","");
    	gblAllSurveyValues.put("Q3g2","");
    	gblAllSurveyValues.put("Q3h","-1");
    	gblAllSurveyValues.put("Q3i","");
    	gblAllSurveyValues.put("Q3k1","-1");
    	gblAllSurveyValues.put("Q3k2","-1");
    	gblAllSurveyValues.put("Q3k3","-1");
    	gblAllSurveyValues.put("Q3k4","-1");
    	gblAllSurveyValues.put("Q3k5","-1");
    	gblAllSurveyValues.put("Q3kb","-1");
    	gblAllSurveyValues.put("Q3l","-1");
    	gblAllSurveyValues.put("Q3m","0");
    	gblAllSurveyValues.put("Q3n1a","-1");
    	gblAllSurveyValues.put("Q3n1b","-1");
    	gblAllSurveyValues.put("Q3n1c","");
    	gblAllSurveyValues.put("Q3n2a","-1");
    	gblAllSurveyValues.put("Q3n2b","-1");
    	gblAllSurveyValues.put("Q3n2c","");
    	gblAllSurveyValues.put("Q3n3a","-1");
    	gblAllSurveyValues.put("Q3n3b","-1");
    	gblAllSurveyValues.put("Q3n3c","");
    	gblAllSurveyValues.put("Q3n4a","-1");
    	gblAllSurveyValues.put("Q3n4b","-1");
    	gblAllSurveyValues.put("Q3n4c","");
    	gblAllSurveyValues.put("Q3n5a","-1");
    	gblAllSurveyValues.put("Q3n5b","-1");
    	gblAllSurveyValues.put("Q3n5c","");
    	gblAllSurveyValues.put("Q3o","0");
    	gblAllSurveyValues.put("Q3p","-1");
    	gblAllSurveyValues.put("Q3q1","-1");
    	gblAllSurveyValues.put("Q3q2","-1");
    	gblAllSurveyValues.put("Q3q3","-1");
    	gblAllSurveyValues.put("Q3qi","");
    	gblAllSurveyValues.put("Q3qii","0");
    	gblAllSurveyValues.put("Q3r","-1");
    	gblAllSurveyValues.put("Q3ri","");
    	gblAllSurveyValues.put("Q3rii","");
    	gblAllSurveyValues.put("Q3riii","-1");
    	gblAllSurveyValues.put("Q3riv","-1");
    	gblAllSurveyValues.put("Q3rv","");
    	gblAllSurveyValues.put("Q3s","-1");
    	gblAllSurveyValues.put("Q3sii","-1");
    	gblAllSurveyValues.put("Q4a","-1");
    	gblAllSurveyValues.put("Q4ai","");
    	gblAllSurveyValues.put("Q4b","-1");
    	gblAllSurveyValues.put("Q4bi","-1");
    	gblAllSurveyValues.put("Q4bii","");
    	gblAllSurveyValues.put("Q4biii","-1");
    	gblAllSurveyValues.put("Q4biv","");
    	gblAllSurveyValues.put("Q4c","");
    	gblAllSurveyValues.put("Q4d","0");
    	gblAllSurveyValues.put("Q4e","0");
    	gblAllSurveyValues.put("Q4f1a","0");
    	gblAllSurveyValues.put("Q4f1b","");
    	gblAllSurveyValues.put("Q4f1c","");
    	gblAllSurveyValues.put("Q4f1d","0");
    	gblAllSurveyValues.put("Q4f2a","0");
    	gblAllSurveyValues.put("Q4f2b","");
    	gblAllSurveyValues.put("Q4f2c","");
    	gblAllSurveyValues.put("Q4f2d","0");
    	gblAllSurveyValues.put("Q4f3a","0");
    	gblAllSurveyValues.put("Q4f3b","");
    	gblAllSurveyValues.put("Q4f3c","");
    	gblAllSurveyValues.put("Q4f3d","0");
    	gblAllSurveyValues.put("Q4g","-1");
    	gblAllSurveyValues.put("Q4g2","");
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
				
				BufferedReader buffer = new BufferedReader(	new InputStreamReader(content), 8192); //was getting a dalvik msg so used this buffer size
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
				try{
			doSYNC(result);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}

			if ((actionType==actionSEARCH_FARMERNAME) || (actionType==actionSEARCH_ADVANCED)){
				//doSEARCH_FARMERNAME(result);
				doSEARCH_FARMERNAME( result, activity, actionType);
			
			}//if action=search Farmer
			//actionFETCH_FARMER_DETAILS
			if ((actionType==actionFETCH_FARMER_DETAILS) ){
				doFETCH_FARMER_DETAILS(result, activity);
			
			}//actionFETCH_FARMER_DETAILS
			
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
			        String IDArry[]=null;
			        
			        //seems the following call does not work---------------------------vvv
			        Object A[]=fxn.create2_1DArrays(result, "</br>", ":");  //convention: 1st col is strings, 2nd col is numbers
			        NameArry=(String[])A[0];
			        IDArry=(String[])A[1];
			        
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
				doSEARCH_CURRENT_FARMS(result, activity, actionType);

			}//if actionSEARCH_CURRENT_FARMS
			
		}//on post execute fxn
	
	

		
//		private void doFETCH_FARMER_DETAILS(String result){
//			if (result.endsWith("failedOK")){
//				Toast.makeText(getApplicationContext(), "NO matching records", Toast.LENGTH_LONG).show();
//				return;
//			}
//			//String listData[][]=null;
//			if (result.endsWith("successOK")){
//				Toast.makeText(activity, "Successful!", Toast.LENGTH_SHORT) .show();
//				UtilityFunctions fxn= new UtilityFunctions();
//				result=result.replace("successOK","");
//
//				//listData=fxn.create2DArray(result, "</br>", ":");
//				
//			    FragmentManager fragmentManager = getSupportFragmentManager();	
//		        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//		      //  String[] values = listData[1];
//		        String[] NameArry=null;	//value	       
//		        String IDArry[]=null; //key
//		        HashMap<String, String> FormData= new HashMap<String, String>();
//		        //seems the following call does not work---------------------------vvv
//		        Log.v("fmrDetails_result",result);
//		        Object A[]=fxn.createKey_ValuePairs(result, "</br>", ":");//, NameArry, IDArry, arry2);
//		        NameArry=(String[])A[1];		        
//		        IDArry=(String[])A[0];
//		        
//		        for (int i=0; i<NameArry.length; i++){
//		        	FormData.put(IDArry[i], NameArry[i]);
//		        }
//		        
//		           //above may not work bc of applicaitoncontext
//		        int TargetPane;
//		    	//	android.support.v4.app.Fragment fragment=null;
//		    		if (mTwoPane) {
//		    	          TargetPane=R.id.rightFrame;
//		    		}
//		    		else
//		    		{
//		    		      TargetPane=R.id.leftFrame;
//		    				
//		    		}
//		        	android.support.v4.app.Fragment fragment=null;
//		            
//		            //fragment=new ListResultFragment(NameArry, IDArry, actionType, PurposeOfSearch);
//		        	fragment=new AddFarmerDetailFragment(VIEW_MODE,FormData);
//		        	fragmentTransaction.replace(TargetPane , fragment);
//		            fragmentTransaction.addToBackStack(null);
//		            fragmentTransaction.commit();
//				
//			}
//			else
//			{
//				if (result.endsWith("OK")){
//					Toast.makeText(getApplicationContext(), "Connected but other error occured", Toast.LENGTH_LONG).show();
//				}
//				else{
//					
//				Toast.makeText(activity, "Sorry, operation failed. Please report this", Toast.LENGTH_LONG) .show();
//				} //else --> ends with OK
//			}//else-> ends with successOK
//		}
	
		private void doSYNC(String result){
			if (actionType==actionSYNC){
				if (result.endsWith("failedOK")){
					Toast.makeText(getApplicationContext(), "NO matching records", Toast.LENGTH_LONG).show();
					return;
				}
				String supportData[][]=null;
				if (result.endsWith("successOK")){
					Toast.makeText(activity, "Successful!", Toast.LENGTH_LONG) .show();
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
                     
                	  Toast.makeText(this, "Picture Saved-check", Toast.LENGTH_LONG).show();
                      break;
                  }
              break;
          case RESIZE_IMAGE_CAPTURED: 
              
              switch( resultCode )
              {
                  case Activity.RESULT_CANCELED:
                      Toast.makeText(this, "Camera action Cancelled", Toast.LENGTH_LONG).show();
                      break;
                  case Activity.RESULT_OK:
                	  try
                      {
                		  
                	/*    //works OK..
                		  
                		  String captured_image = System.currentTimeMillis() + ".jpg";
                		  */
                		  File folder = new File(Environment.getExternalStorageDirectory() , "IFDC");
                		  boolean success = true;
                		  if (!folder.exists()) {
                		      success = folder.mkdir();
                		  }
                		  if (success) {
                		      // Do something on success
                			  Toast.makeText(getApplicationContext(), "DIR created", Toast.LENGTH_LONG ).show();
                			  
                		  } else {
                		      // Do something else on failure 
                			  Toast.makeText(getApplicationContext(), "NOT created", Toast.LENGTH_LONG ).show();
                		  }
                		  
                		  
          		    File file = new File(Environment.getExternalStorageDirectory(), "IFDC"+File.separator+captured_image);           		              		  
          		    captured_image = file.getAbsolutePath();   //note: this will also change the global var
          		      Bitmap bitmap = Bitmap.createScaledBitmap((Bitmap)data.getExtras().get("data"), 1600, 1200, true);//w,h //chnaged from 800x600
                      //try{
                          FileOutputStream out = new FileOutputStream(captured_image);//destinationFile, captured_image is global var
                          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); //change from 50 to 100. 0 = min size, 100 =max quality
                          out.flush();
                          out.close();
                      } 
                      catch (Exception e) 
                      {
                    	  Toast.makeText(getApplicationContext(), "Error saving photo"+e.toString(), Toast.LENGTH_LONG ).show();
                          Log.e("PhotoResize", "ERROR:" + e.toString());
                          return;
                      }
                      
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
		  	//System.out.println("Expected farmerID="+searchString);
		  	
		  	switch (searchType){
		  	case actionSEARCH_FARMERNAME:
System.out.println("doSearchOffline- search farmers");				
		  		result=db.fetchLocalFarmerList(searchString);		  		
		  		db.close();
		  		//normalize so i can use exisiting online mode code:
		        if(result==null || result.trim().equals("") ){
		        	Toast.makeText(getApplicationContext(), "No Local Records", Toast.LENGTH_LONG).show();
		        	result+="failedOK";
		        }
		        else{
		        	result+="successOK";
		        }
		  		doSEARCH_FARMERNAME(result,MainActivity.this,actionSEARCH_FARMERNAME);
		  		break;
		  	case actionSEARCH_FARMERREFERENCENO:
		  		result=db.fetchLocalFarmerListByID(searchString);
		  		db.close();
		  	//normalize so i can use exisiting online mode code:
		        if(result==null || result.trim().equals("") ){
		        	Toast.makeText(getApplicationContext(), "No Local Records", Toast.LENGTH_LONG).show();
		        	result+="failedOK";
		        }
		        else{
		        	result+="successOK";
		        }
		  		break;
		  	case actionSEARCH_CURRENT_FARMS:
System.out.println("doSearchOffline- search current farms");
		  		result=db.fetchLocalFarmList(searchString);
System.out.println("doSearchOffline -result= "+result);
		  		db.close();
		  	//normalize so i can use exisiting online mode code:
		        if(result==null || result.trim().equals("") ){
		        	Toast.makeText(getApplicationContext(), "No Local Records", Toast.LENGTH_LONG).show();
		        	result+="failedOK";
		        }
		        else{
		        	result+="successOK";
		        }
		  		//perhaps simply go ahead to show the form to add new farms. Whats the case in online mode?
		  		doSEARCH_CURRENT_FARMS(result,MainActivity.this,actionSEARCH_CURRENT_FARMS);
		  		break;
		  	case actionFETCH_FARMER_DETAILS:
		  		result=db.fetchLocalFarmerDetails(searchString);
		  		db.close();
		  		System.out.println("result from offline read"+result);
			  	doFETCH_FARMER_DETAILS(result, MainActivity.this);
			break;

		  	}
		  			  	
//	        //db.close();  //part of original code
//		  	
//		  	
//		  	
//	        
//	        if(result==null || result.trim().equals("") ){
//	        	Toast.makeText(getApplicationContext(), "No Local Records", Toast.LENGTH_LONG).show();
//	        	System.out.println("doSearchOffline: returns here");
//	        	
//	        		
//	        		return;  //do not return, but the other conditions should be false.
//	        }
//	        
//	        /**==========================================if no records found but was to be add records       	 */
//        	if (PurposeOfSearch== searchBA_ADD_FARM ){
//				
//				 FragmentManager fragmentManager = getSupportFragmentManager();	
//			        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//			        String[] NameArry=null;
//			        String IDArry[]=null;
//			           
//			        int TargetPane;
//			    	
//			    		if (mTwoPane) {
//			    	          TargetPane=R.id.rightFrame;
//			    		}
//			    		else
//			    		{
//			    		      TargetPane=R.id.leftFrame;
//			    				
//			    		}
//			        	android.support.v4.app.Fragment fragment=null;
//			            fragment=new ListResultFragment(NameArry, IDArry, searchType, PurposeOfSearch);
//			            fragmentTransaction.replace(TargetPane , fragment);
//			            fragmentTransaction.addToBackStack(null);
//			            fragmentTransaction.commit();
//			            return;
//				
//				}
//        	//===================================================
//        
//	        
//	        
//		  UtilityFunctions fxn= new UtilityFunctions();
//			//result=result.replace("successOK","");  //comment out or use alternative mechanism
//			//listData=fxn.create2DArray(result, "</br>", ":");
//			
//		    FragmentManager fragmentManager = getSupportFragmentManager();	
//	        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//	      
//	        String[] NameArry=null;
//	        String IDArry[]=null;
//	        
//	        //seems the following call does not work---------------------------vvv
//	        Object A[]=fxn.create2_1DArrays(result, "</br>", ":");//, NameArry, IDArry, arry2);
//	        NameArry=(String[])A[0];
//	        IDArry=(String[])A[1];
//	        
//	           //above may not work bc of applicaitoncontext
//	        int TargetPane;
//	    	//	android.support.v4.app.Fragment fragment=null;
//	    		if (mTwoPane) {
//	    	          TargetPane=R.id.rightFrame;
//	    		}
//	    		else
//	    		{
//	    		      TargetPane=R.id.leftFrame;
//	    				
//	    		}
//	        	android.support.v4.app.Fragment fragment=null;
//	            //fragment=new ListResultFragment(values);
//	        	System.out.println("b4 call--in dosearch offine: searchType="+searchType+ " searchstr="+searchString);
//	            fragment=new ListResultFragment(NameArry, IDArry, searchType, PurposeOfSearch); //searchTyep was actionType previously
//	            fragmentTransaction.replace(TargetPane , fragment);
//	            fragmentTransaction.addToBackStack(null);
//	            fragmentTransaction.commit();
//		
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
	        String IDArry[]=null;
	        
	        //seems the following call does not work---------------------------vvv
	        Object A[]=fxn.create2_1DArrays(result, "</br>", ":");//, NameArry, IDArry, arry2);
	        NameArry=(String[])A[0];
	        IDArry=(String[])A[1];
	        
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

	  //extracting common functions used by online and offline search for processing results
		//a test -- a function to make the onpost execute look more readable
		private void doSEARCH_FARMERNAME(String result, Activity activity, int actionType){
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
		       // int IDArry[]=null; //changed int->str
		        String IDArry[]=null;
		        
		        //seems the following call does not work---------------------------vvv
		        Object A[]=fxn.create2_1DArrays(result, "</br>", ":");//, NameArry, IDArry, arry2);
		        NameArry=(String[])A[0];
		        //IDArry=(int[])A[1]; //changed int->str
		        IDArry=(String[])A[1];
		        
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

		private void doSEARCH_CURRENT_FARMS(String result, Activity activity, int actionType){
			if (result.endsWith("failedOK")){
				Toast.makeText(getApplicationContext(), "NO matching records", Toast.LENGTH_LONG).show();
				if (PurposeOfSearch== searchBA_ADD_FARM ){
				//==================================================testing if i can load a blank list
				 FragmentManager fragmentManager = getSupportFragmentManager();	
			        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			        String[] NameArry=null;
			        String IDArry[]=null;
			           
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
				Toast.makeText(activity, "Successful!", Toast.LENGTH_LONG) .show();
				UtilityFunctions fxn= new UtilityFunctions();
				result=result.replace("successOK","");
				//listData=fxn.create2DArray(result, "</br>", ":");
				
			    FragmentManager fragmentManager = getSupportFragmentManager();	
		        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		        String[] NameArry=null;
		        String IDArry[]=null;
		        
		        //seems the following call does not work---------------------------vvv
		        Object A[]=fxn.create2_1DArrays(result, "</br>", ":");
		        NameArry=(String[])A[0];
		        IDArry=(String[])A[1];
		        
		           
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
		}

		private void doFETCH_FARMER_DETAILS(String result, Activity activity){  //, int actionType
			
			if (result.endsWith("failedOK")){
				Toast.makeText(getApplicationContext(), "NO matching records", Toast.LENGTH_LONG).show();
				return;
			}
			
			System.out.println("inside doFETCH_FARMER_DETAILS----0");
			//String listData[][]=null;
			if (result.endsWith("successOK")){
				Toast.makeText(activity, "Successful!", Toast.LENGTH_SHORT) .show();
				UtilityFunctions fxn= new UtilityFunctions();
				result=result.replace("successOK","");
				System.out.println("inside doFETCH_FARMER_DETAILS----1");
				//listData=fxn.create2DArray(result, "</br>", ":");
				
			    FragmentManager fragmentManager = getSupportFragmentManager();	
		        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		      //  String[] values = listData[1];
		        String[] NameArry=null;	//value	       
		        String IDArry[]=null; //key
		        HashMap<String, String> FormData= new HashMap<String, String>();
		        //seems the following call does not work---------------------------vvv
		        Log.v("fmrDetails_result",result);
		        Object A[]=fxn.createKey_ValuePairs(result, "</br>", ":"); //-----
		        NameArry=(String[])A[1];		        
		        IDArry=(String[])A[0];
		        
		        for (int i=0; i<NameArry.length; i++){
		        	FormData.put(IDArry[i], NameArry[i]);
		        }
		        System.out.println("inside doFETCH_FARMER_DETAILS----2");
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
		            
		            //fragment=new ListResultFragment(NameArry, IDArry, actionType, PurposeOfSearch);
		        	fragment=new AddFarmerDetailFragment(VIEW_MODE,FormData);
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
		}

}//main activity
