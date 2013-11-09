package com.inovagro.inovagrofdc1;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter implements InovagroConstants {
	
	//TODO: test
	
	   public static final String KEY_ROWID = "_id";
	   public static final String KEY_THEID = "theid";
	    public static final String KEY_THENAME = "thename";
	    public static final String KEY_PARENTID = "parentid";
	    private static final String TAG = "DBAdapter";
	    
	    private static final String DATABASE_NAME = "MyDB";    
	    
	    private static final int DATABASE_VERSION = 7;

	    /*private static final String DATABASE_CREATE =
	        "create table contacts (_id integer primary key autoincrement, "
	        + "name text not null, email text not null);";  */
	    private static final String DATABASE_CREATE1 =
		        "create table provinces (_id integer primary key autoincrement, theid integer not null , thename text not null, parentid integer );"; //too out nul null for parentID
	    private static final String DATABASE_CREATE2 =
		        "create table districts (_id integer primary key autoincrement, theid integer not null , thename text not null, parentid integer not null);";
	    private static final String DATABASE_CREATE3 =
		        "create table admin_posts (_id integer primary key autoincrement, theid integer not null , thename text not null, parentid integer not null);";
	    private static final String DATABASE_CREATE4 =
		        "create table locality (_id integer primary key autoincrement, theid integer not null , thename text not null, parentid integer not null);";
	    private static final String DATABASE_CREATE5 =
		        "create table zones (_id integer primary key autoincrement, theid integer not null , thename text not null, parentid integer not null);";
	    private static final String DATABASE_CREATE6 =
		        "create table farmer_groups (_id integer primary key autoincrement, theid integer not null , thename text not null, parentid integer not null);";
	    private static final String DATABASE_CREATE7 =
		        "create table id_types (_id integer primary key autoincrement, theid integer not null , thename text not null, parentid integer not null);";
	    private static final String DATABASE_CREATE8 =
		        "create table crop_types (_id integer primary key autoincrement, theid integer not null , thename text not null, parentid integer not null);";
	    private static final String DATABASE_CREATE9 =
		        "create table seed_variety (_id integer primary key autoincrement, theid integer not null , thename text not null, parentid integer not null);";
	    private static final String DATABASE_CREATE10 =
		        "create table service_provider_types (_id integer primary key autoincrement, theid integer not null , thename text not null, parentid integer not null);";
	    private static final String DATABASE_CREATE11 =
		        "create table service_providers (_id integer primary key autoincrement, theid integer not null , thename text not null, parentid integer not null);";
	    private static final String DATABASE_CREATE12 =
		        "create table seasons (_id integer primary key autoincrement, theid integer not null , thename text not null, parentid integer not null);";
	    private static final String DATABASE_CREATE13 =
		        "create table land_ownership_types (_id integer primary key autoincrement, theid integer not null , thename text not null, parentid integer not null);";
	   	
	    private static final String createFarmTbl="CREATE TABLE IF NOT EXISTS farms ("+       		
				 "  FarmID text NOT NULL primary key,"+  //FarmID integer NOT NULL primary key,"+
				 "  FarmerID text NOT NULL,"+
				 "  FarmName text NOT NULL,"+
				 "  ProvinceID integer NOT NULL,"+
				 "  DistrictID integer NOT NULL,"+
				 "  AdminPostID integer NOT NULL,"+
				 "  LocalityID integer NOT NULL,"+
				 "  ZoneID integer NOT NULL,"+
				 "  GPSLong real NOT NULL,"+
				 "  GPSLat real NOT NULL,"+
				 "  UserID integer NOT NULL"+
				 ");";
	    
	   private static final  String createFarmerTbl="CREATE TABLE IF NOT EXISTS farmers ("+
				// "_id integer primary key autoincrement,"+
				 "FarmerID text NOT NULL primary key on conflict replace,"+  //"FarmerID integer NOT NULL primary key on conflict replace,"+
				 "Surname text NOT NULL,"+
				 "ForeNames text NOT NULL,"+
				 "FarmerReferenceNo text NOT NULL,"+
				 "Gender integer NOT NULL ,"+
				 "PhoneNo text NOT NULL ,"+
				 "DateOfBirth text NOT NULL,"+
				 "ProvinceID integer NOT NULL,"+
				 "DistrictID integer NOT NULL,"+
				 "AdminPostID integer NOT NULL,"+
				 "LocalityID integer NOT NULL,"+
				 "ZoneID integer NOT NULL,"+
				 "FarmerGroupID integer NOT NULL,"+
				 "IDType integer NOT NULL,"+
				 "FarmerIDNo text NOT NULL,"+
				 "HeadOfHouseholdName text NOT NULL,"+
				 "HeadOfHouseholdGender integer NOT NULL,"+
				 "NumberOfDependents integer NOT NULL,"+
				 "IsGroupLeader tinyint(4) NOT NULL ,"+
				 "UserId integer NOT NULL,"+
				 "BirthCertificate  text,"+
				 "TemporaryID  text,"+
				 "NationalID  text,"+
				 "VoterRegistrationCard  text,"+
				 "IncomeTaxNo  text,"+
				 "Passport  text,"+
				 "DUAT  text,"+
				 "ID1Front  text,"+
				 "ID1Back  text,"+
				 "ID2Front  text,"+
				 "ID2Back  text,"+
				 "FarmerPicture  text,"+
				 "GPSLong  real,"+
				 "GPSLat  real,"+
				 "MobileTimeStamp  text,"+				 
				 "SystemTimeStamp  text"+
				 		" );";
	 
	   private static final String createUserTbl="CREATE TABLE IF NOT EXISTS `users` ("+
			   "  `UserID` integer NOT NULL primary key on conflict replace,"+
			   "  `Username` text NOT NULL,"+
			   "  `Password` text NOT NULL"+
			   ");";
	   
	   private static final String createFarmYearlyData="CREATE TABLE IF NOT EXISTS farms_yearly_data ("+
				 "  FarmYearlyDataID text NOT NULL primary key,"+
				 "  FarmID text NOT NULL,"+
				 "  SeasonID integer NOT NULL,"+
				 "  CropTypeID integer NOT NULL,"+
				 "  SeedVarietyID integer NOT NULL,"+
				 "  LandOwnershipID integer NOT NULL,"+
				 "  Mechanized integer NOT NULL ,"+
				 "  DemoPlot integer NOT NULL,"+
				 "  TotalFarmSize real NOT NULL)";
    	 
	   private static final String createFarmerSeasons="CREATE TABLE IF NOT EXISTS farmer_seasons ("+
				 "  FarmerYearID text NOT NULL primary key,"+
				 "  FarmerID text NOT NULL,"+
				 "  SeasonID integer NOT NULL)";
	   /*
	   private static final String createUserTbl="CREATE TABLE IF NOT EXISTS `users` ("+
			   "  `UserID` integer NOT NULL primary key on conflict replace,"+
			   "  `Username` text NOT NULL,"+
			   "  `Password` text NOT NULL,"+
			   "  `Surname` text NOT NULL,"+
			   "  `Forenames` text NOT NULL,"+
			   "  `CurrentStationID` integer NOT NULL,"+
			   "  `SupervisorID` integer NOT NULL,"+
			   "  `Enabled` integer NOT NULL"+
			   "  `PrivateSectorPartnerID` integer NOT NULL,"+
			   "  `UserTypeID` integer NOT NULL,"+
			   ");";
	   */
	    private final Context context;    

	    private DatabaseHelper DBHelper;
	    private SQLiteDatabase db;

	    public DBAdapter(Context ctx) 
	    {
	        this.context = ctx;
	        DBHelper = new DatabaseHelper(context);
	    }
	        
	    private static class DatabaseHelper extends SQLiteOpenHelper 
	    {
	        DatabaseHelper(Context context) 
	        {
	            super(context, DATABASE_NAME, null, DATABASE_VERSION);
	        }

	        @Override
	        public void onCreate(SQLiteDatabase db) 
	        {
	        	try {
	        		//db.execSQL(DATABASE_CREATE);
	        		db.execSQL(DATABASE_CREATE1);
	        		db.execSQL(DATABASE_CREATE2);
	        		db.execSQL(DATABASE_CREATE3);
	        		db.execSQL(DATABASE_CREATE4);
	        		db.execSQL(DATABASE_CREATE5);
	        		db.execSQL(DATABASE_CREATE6);
	        		db.execSQL(DATABASE_CREATE7);
	        		db.execSQL(DATABASE_CREATE8);
	        		db.execSQL(DATABASE_CREATE9);
	        		db.execSQL(DATABASE_CREATE10);
	        		db.execSQL(DATABASE_CREATE11);
	        		db.execSQL(DATABASE_CREATE12);
	        		db.execSQL(DATABASE_CREATE13);
	        		db.execSQL(createFarmTbl);
	        		db.execSQL(createFarmerTbl);
	        		db.execSQL(createFarmYearlyData);
	        		db.execSQL(createFarmerSeasons);
	        		
	        		//add functions to create the survey data table etc. Call their respective functions
	        		/*
	        		 * 
	        		 * wipeOfflineFarmerData();
        	wipeOfflineFarmerFarmDatabase();
        	wipeOfflinePigeionPeaHarvestData();
        	wipeOfflineSurvey2013Data();
        	wipeOfflineVisitData();
        	
	        		 */
	        		//fetch and write all the data to db.
	        		
	        	} catch (SQLException e) {
	        		e.printStackTrace();
	        	}
	        }

	        @Override
	        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	        {
	            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
	                    + newVersion + ", which will destroy all old data");
	          //  db.execSQL("DROP TABLE IF EXISTS contacts");
	            db.execSQL("DROP TABLE IF EXISTS provinces");
	            db.execSQL("DROP TABLE IF EXISTS districts");
	            db.execSQL("DROP TABLE IF EXISTS admin_posts");
	            db.execSQL("DROP TABLE IF EXISTS locality");
	            db.execSQL("DROP TABLE IF EXISTS zones");
	            db.execSQL("DROP TABLE IF EXISTS farmer_groups");
	            db.execSQL("DROP TABLE IF EXISTS id_types");
	            db.execSQL("DROP TABLE IF EXISTS crop_types");
	            db.execSQL("DROP TABLE IF EXISTS seed_variety");
	            db.execSQL("DROP TABLE IF EXISTS service_provider_types");
	            db.execSQL("DROP TABLE IF EXISTS service_providers");
	            db.execSQL("DROP TABLE IF EXISTS seasons");
	            db.execSQL("DROP TABLE IF EXISTS land_ownership_types");
	            
	            db.execSQL("DROP TABLE IF EXISTS farmers");
	            db.execSQL("DROP TABLE IF EXISTS farms");
	            db.execSQL("DROP TABLE IF EXISTS farms_yearly_data");
	            db.execSQL("DROP TABLE IF EXISTS farmer_seasons");
		         
			            onCreate(db);
	        }
	    }    

	    //---opens the database---
	    public DBAdapter open() throws SQLException 
	    {
	        db = DBHelper.getWritableDatabase();
	        return this;
	    }

	    //---closes the database---    
	    public void close() 
	    {
	        DBHelper.close();
	    }

	    //---insert a row into the database---
	    public long insertOneRow(int table, int id, String theName, int parentID) 
	    {
	    	String Current_DATABASE_TABLE=null;
	    	 switch(table){	    	

		    	case PROVINCES  :           //"provinces":
		    		Current_DATABASE_TABLE=tblPROVINCES;
		    		break;
		   	    case DISTRICTS  :           //"districts": 
		   	    	Current_DATABASE_TABLE=tblDISTRICTS;
		   	    	break;
		   	    case ADMIN_POSTS  :           //"admin_posts":
		   	    	Current_DATABASE_TABLE=tblADMIN_POSTS;
		   	    	break;
		   	    case LOCALITY  :           //"locality":
		   	    	Current_DATABASE_TABLE=tblLOCALITY;
		   	    	break;
		   	    case ZONES  :           //"zones":
		   	    	Current_DATABASE_TABLE=tblZONES;
		   	    	break;
		   	    case FARMER_GROUPS  :           //"farmer_groups"
		   	    	Current_DATABASE_TABLE=tblFARMER_GROUPS;
		   	    	break;
		   	    case IDTYPES  :           //"id_types"
		   	    	Current_DATABASE_TABLE=tblIDTYPES;
		   	    	break;
		   	    case CROP_TYPES  :           //"crop_types"
		   	    	Current_DATABASE_TABLE=tblCROP_TYPES;
		   	    	break;
		   	    case SEED_VARIETY  :           //"seed_variety"
		   	    	Current_DATABASE_TABLE=tblSEED_VARIETY;
		   	    	break;
		   	    case SERVICE_PROVIDER_TYPES  :           //"service_provider_types"
		   	    	Current_DATABASE_TABLE=tblSERVICE_PROVIDER_TYPES;
		   	    	break;
		   	    case SERVICE_PROVIDERS  :           //"service_providers"
		   	    	Current_DATABASE_TABLE=tblSERVICE_PROVIDERS;
		   	    	break;
		   	    case SEASONS  :           //"service_providers"
		   	    	Current_DATABASE_TABLE=tblSEASONS;
		   	    	break;
		   	 case LAND_OWNERSHIP_TYPES  :           //"land_ownership_types"
		   	    	Current_DATABASE_TABLE=tblLAND_OWNERSHIP_TYPES;
		   	    	break;



	    	}
	        ContentValues initialValues = new ContentValues();
	        initialValues.put(KEY_THEID, id);
	        initialValues.put(KEY_THENAME, theName);
	        if (table != PROVINCES){  //decided to put -1 for all provices' parentID. chk for consistency
	        initialValues.put(KEY_PARENTID, parentID);
	        }
	        else {
	        	initialValues.put(KEY_PARENTID, -1);
	        }
	        return db.insert(Current_DATABASE_TABLE, KEY_PARENTID, initialValues);  //key_parentid is the null hac column..
	     
	    }

	    
	   
	    //--insert array of values into specifed table
	    //---insert a contact into the database---
	    public void insertTableRows(String table, String [][] arry) 
	    {
	    	String Current_DATABASE_TABLE=null;
	    	ContentValues initialValues = new ContentValues();
	        for (int i=0; i<arry.length; i++){
		        initialValues.put(KEY_THEID, arry[i][0]);
		        initialValues.put(KEY_THENAME, arry[i][1]);
		        if (!table.equalsIgnoreCase(tblPROVINCES)){
		        	initialValues.put(KEY_PARENTID, arry[i][2]);
		        }
		        else{
		        	initialValues.put(KEY_PARENTID, -1);  //using -1 for parent for provinces
		        }
		         db.insert(table, null, initialValues);
	        }
	       // return db.insert(Current_DATABASE_TABLE, null, initialValues);
	    }
	    
	    
	    /*
	    //---deletes a particular contact---
	    public boolean deleteContact(long rowId) 
	    {
	        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	    }

	    //---retrieves all the contacts---
	    public Cursor getAllContacts() 
	    {
	        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
	                KEY_EMAIL}, null, null, null, null, null);
	    }
*/
	    //---retrieves all the data in a table---
	    public Cursor getAllData(int table) 
	    {
	    	String Current_DATABASE_TABLE=null;
	    	 switch(table){	    	
	    	case PROVINCES  :           //"provinces":
	    		Current_DATABASE_TABLE=tblPROVINCES;
	    		break;
	   	    case DISTRICTS  :           //"districts": 
	   	    	Current_DATABASE_TABLE=tblDISTRICTS;
	   	    	break;
	   	    case ADMIN_POSTS  :           //"admin_posts":
	   	    	Current_DATABASE_TABLE=tblADMIN_POSTS;
	   	    	break;
	   	    case LOCALITY  :           //"locality":
	   	    	Current_DATABASE_TABLE=tblLOCALITY;
	   	    	break;
	   	    case ZONES  :           //"zones":
	   	    	Current_DATABASE_TABLE=tblZONES;
	   	    	break;
	   	    case FARMER_GROUPS  :           //"farmer_groups"
	   	    	Current_DATABASE_TABLE=tblFARMER_GROUPS;
	   	    	break;
	   	    case IDTYPES  :           //"id_types"
	   	    	Current_DATABASE_TABLE=tblIDTYPES;
	   	    	break;	   	    	
	   	    case CROP_TYPES  :           //"crop_types"
	   	    	Current_DATABASE_TABLE=tblCROP_TYPES;
	   	    	break;
	   	    case SEED_VARIETY  :           //"seed_variety"
	   	    	Current_DATABASE_TABLE=tblSEED_VARIETY;
	   	    	break;
	   	    case SERVICE_PROVIDER_TYPES  :           //"service_provider_types"
	   	    	Current_DATABASE_TABLE=tblSERVICE_PROVIDER_TYPES;
	   	    	break;
	   	    case SERVICE_PROVIDERS  :           //"service_providers"
	   	    	Current_DATABASE_TABLE=tblSERVICE_PROVIDERS;
	   	    	break;
	   	 case SEASONS :           //"seasons"
	   	    	Current_DATABASE_TABLE=tblSEASONS;
	   	    	break;
	   	case LAND_OWNERSHIP_TYPES  :           //"land_ownership_types"
   	    	Current_DATABASE_TABLE=tblLAND_OWNERSHIP_TYPES;
   	    	break;

	    	}
	        return db.query(Current_DATABASE_TABLE, new String[] {KEY_ROWID, KEY_THEID, KEY_THENAME,
	                KEY_PARENTID}, null, null, null, null, null);
	    }

	    //---retrieves all the data in a table---
	    public Cursor getDataSubset(int table, long parentID) throws SQLException 
	    {
	    	String Current_DATABASE_TABLE=null;
	    	 switch(table){	    	
	    	case PROVINCES  :           //"provinces":
	    		Current_DATABASE_TABLE=tblPROVINCES;
	    		break;
	   	    case DISTRICTS  :           //"districts": 
	   	    	Current_DATABASE_TABLE=tblDISTRICTS;
	   	    	break;
	   	    case ADMIN_POSTS  :           //"admin_posts":
	   	    	Current_DATABASE_TABLE=tblADMIN_POSTS;
	   	    	break;
	   	    case LOCALITY  :           //"locality":
	   	    	Current_DATABASE_TABLE=tblLOCALITY;
	   	    	break;
	   	    case ZONES  :           //"zones":
	   	    	Current_DATABASE_TABLE=tblZONES;
	   	    	break;
	   	    case FARMER_GROUPS  :           //"farmer_groups"
	   	    	Current_DATABASE_TABLE=tblFARMER_GROUPS;
	   	    	break;
	   	    case IDTYPES  :           //"id_types"
	   	    	Current_DATABASE_TABLE=tblIDTYPES;
	   	    	break;
	   	    case CROP_TYPES  :           //"crop_types"
	   	    	Current_DATABASE_TABLE=tblCROP_TYPES;
	   	    	break;
	   	    case SEED_VARIETY  :           //"seed_variety"
	   	    	Current_DATABASE_TABLE=tblSEED_VARIETY;
	   	    	break;
	   	    case SERVICE_PROVIDER_TYPES  :           //"service_provider_types"
	   	    	Current_DATABASE_TABLE=tblSERVICE_PROVIDER_TYPES;
	   	    	break;
	   	    case SERVICE_PROVIDERS  :           //"service_providers"
	   	    	Current_DATABASE_TABLE=tblSERVICE_PROVIDERS;
	   	    	break;
	   	    case SEASONS :           //"seasons"
	   	    	Current_DATABASE_TABLE=tblSEASONS;
	   	    	break;
	   	 case LAND_OWNERSHIP_TYPES  :           //"land_ownership_types"
	   	    	Current_DATABASE_TABLE=tblLAND_OWNERSHIP_TYPES;
	   	    	break;

	    	 }
	     
	        
	        //deteled true as first param
	        Cursor mCursor =
	                db.query(true, Current_DATABASE_TABLE, new String[] {KEY_ROWID, KEY_THEID, KEY_THENAME,
	                		KEY_PARENTID}, KEY_PARENTID + "=" + parentID, null,
	                null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;
	    }
	    
	    
	    /*
	    //---retrieves a particular contact---
	    public Cursor getContact(long rowId) throws SQLException 
	    {
	        Cursor mCursor =
	                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
	                KEY_NAME, KEY_EMAIL}, KEY_ROWID + "=" + rowId, null,
	                null, null, null, null);
	        if (mCursor != null) {
	            mCursor.moveToFirst();
	        }
	        return mCursor;
	    }

	    //---updates a contact---
	    public boolean updateContact(long rowId, String name, String email) 
	    {
	        ContentValues args = new ContentValues();
	        args.put(KEY_NAME, name);
	        args.put(KEY_EMAIL, email);
	        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	    }
	    
	    */
	    
        public void prepareForSync(){ 
        {
            
          //  db.execSQL("DROP TABLE IF EXISTS contacts");
            db.execSQL("DROP TABLE IF EXISTS provinces");
            db.execSQL("DROP TABLE IF EXISTS districts");
            db.execSQL("DROP TABLE IF EXISTS admin_posts");
            db.execSQL("DROP TABLE IF EXISTS locality");
            db.execSQL("DROP TABLE IF EXISTS zones");
            db.execSQL("DROP TABLE IF EXISTS farmer_groups");
            db.execSQL("DROP TABLE IF EXISTS id_types");
            db.execSQL("DROP TABLE IF EXISTS crop_types");
            db.execSQL("DROP TABLE IF EXISTS seed_variety");
            db.execSQL("DROP TABLE IF EXISTS service_provider_types");
            db.execSQL("DROP TABLE IF EXISTS service_providers");
            db.execSQL("DROP TABLE IF EXISTS seasons");
            db.execSQL("DROP TABLE IF EXISTS land_ownership_types");
             
             try {
        		//db.execSQL(DATABASE_CREATE);
        		db.execSQL(DATABASE_CREATE1);
        		db.execSQL(DATABASE_CREATE2);
        		db.execSQL(DATABASE_CREATE3);
        		db.execSQL(DATABASE_CREATE4);
        		db.execSQL(DATABASE_CREATE5);
        		db.execSQL(DATABASE_CREATE6);
        		db.execSQL(DATABASE_CREATE7);
        		db.execSQL(DATABASE_CREATE8);
        		db.execSQL(DATABASE_CREATE9);
        		db.execSQL(DATABASE_CREATE10);
        		db.execSQL(DATABASE_CREATE11);
        		db.execSQL(DATABASE_CREATE12);
        		db.execSQL(DATABASE_CREATE13);
        		
        		//db.execSQL("CREATE TABLE  seasons (  SeasonID integer NOT NULL,  Season text NOT NULL,  CropTypeID integer NOT NULL)");
        		
        		//fetch and write all the data to db.
        		
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
        }
    }//prepareToSync
        
        public void initOfflineFarmerFarmTables(){
        	
     	 
        	 db.execSQL(createFarmerTbl);
        	 db.execSQL(createFarmTbl);
        	 db.execSQL(createFarmYearlyData);
//        	 db.execSQL(createFarmerSeasons);  //-->temporary removal
        	 
        }
        
        public String saveFarmerFarmDataOffline(String result){
        	
        	initOfflineFarmerFarmTables(); //only creates if not exist
        	 //------------
        	 UtilityFunctions fxn= new UtilityFunctions();
				result=result.replace("successOK","");
				String tmp[]=fxn.msplit(result,"</br>");	
				//System.out.println(result);
				//write each data as a query!
				//==================sqlite does not insert multiple rows!========

				String sql="Replace into farmers  "+fixInsertQuery(tmp[0]);
				System.out.println("sql--saveFarmarFarmTables="+sql);
				db.execSQL(sql);
				System.out.println(sql);
				//now there is a possiblity that a farmer is saved offline who does not yet have farms so next could be b!ank
				try{
				sql="Replace into farms  "+fixInsertQuery(tmp[1]);
				db.execSQL(sql);
				System.out.println("sql--saveFarmarFarmTables="+sql);
				}catch(ArrayIndexOutOfBoundsException aob){
					System.out.println("in saveFarmerFarmDataOffline -NO data for farms");
				}
//				//Temporary omission of farmer_seasons. NO real use currently found. Is also 
//				//disabled in the phpbackend as well
				//consequently changed index of farms_yearly_data to two
//				sql="Replace into farmer_seasons  "+fixInsertQuery(tmp[2]);
//				db.execSQL(sql);
				try{
				sql="Replace into farms_yearly_data  "+fixInsertQuery(tmp[2]);
				db.execSQL(sql);
				}catch(ArrayIndexOutOfBoundsException aob){
					System.out.println("in saveFarmerFarmDataOffline -NO data for farms_yearly_data");
				}
				return "successOK";
        	 //-----------
        } //saveFarmer...

        public String deleteFarmerFarmOfflineData(String IDList){
        	
        	initOfflineFarmerFarmTables(); //only creates if not exist
        	 //------------
        	 	//==================sqlite does not insert multiple rows!========
        	try{
				String sql="Delete from farmers  where FarmerID in ("+IDList+")";
				db.execSQL(sql);
				sql="Delete from farms  where FarmerID in ("+IDList+")";
				db.execSQL(sql);
//				temporary ommission of farmer_seasons				
//				sql="Delete from farmer_seasons  where FarmerID in ("+IDList+")";
//				db.execSQL(sql);				
				//sql="Delete from  farms_yearly_data  where FarmerID in ("+IDList+")";
				sql="delete FROM farms_yearly_data WHERE farmid in (select farmid from farms where FarmerID in ("+IDList+"))";
				db.execSQL(sql);
				
        	}catch(SQLException e){
        		System.out.println("SQLException in deleteFarmerFarmOfflineData"+e.toString());
        		return "LocalDBFailedOK";
        	}
        	return "successOK";
        	 //-----------
        } //saveFarmer...

        
        public void wipeOfflineFarmerFarmDatabase(){
        	 db.execSQL("DROP TABLE IF EXISTS farmers");
        	 db.execSQL("DROP TABLE IF EXISTS farms");
        	 db.execSQL("DROP TABLE IF EXISTS farms_yearly_data");
//        	 db.execSQL("DROP TABLE IF EXISTS farmer_seasons");
        	 initOfflineFarmerFarmTables();
        }
        
        public String fetchLocalFarmerList(String searchString){
           	String sql="Select ForeNames,Surname, FarmerID FROM farmers where ForeNames like '%"+searchString+"%' or Surname like '%"+searchString+"%' order by Surname ASC";
	       	StringBuffer sb= new StringBuffer(); 
           	Cursor c=null;
           	try{
           		c=   db.rawQuery(sql, null);
           	}catch(Exception e){
           		
           	}
           	if (c != null) {
		            c.moveToFirst();
		            //sb.append(c.getString(c.getColumnIndex("Surname"))+", "+c.getString(c.getColumnIndex("Forenames"))+":"+ c.getString(c.getColumnIndex("FarmerID"))+"</br>");
		            if (c.moveToFirst())
		            {
		                do {          
		                    
		                	sb.append(c.getString(c.getColumnIndex("Surname"))+", "+c.getString(c.getColumnIndex("ForeNames"))+":"+ c.getString(c.getColumnIndex("FarmerID"))+"</br>");
		                } while (c.moveToNext());
		            }
		            
		        }
           c.close();
           
		        return sb.toString();
		        
        }
        
        public String fetchLocalFarmerListByID(String searchString){
        	String sql="Select ForeNames,Surname, FarmerID FROM farmers where FarmerReferenceNo like '%"+searchString+"%'  order by Surname ASC";
        	StringBuffer sb= new StringBuffer();
        	Cursor c=null;
           	try{
           		c=   db.rawQuery(sql, null);
           	}catch(Exception e){
           		
           	}
	       	 if (c != null) {
		            c.moveToFirst();
		            if (c.moveToFirst())
		            {
		                do {          
		                    sb.append(c.getString(c.getColumnIndex("Surname"))+", "+c.getString(c.getColumnIndex("ForeNames"))+":"+ c.getString(c.getColumnIndex("FarmerID"))+"</br>");
		                } while (c.moveToNext());
		            }

		            
		        }
	       	 c.close();
		     return sb.toString();   	
        }
        
        public String fetchLocalFarmList(String searchString){
        	String sql="SELECT FarmName, FarmYearlyDataID  FROM farms inner join farms_yearly_data on farms.farmID=farms_yearly_data.farmID where farmerid='"+searchString+"' order by FarmName ASC";
        	//System.out.println("in fetchLocalFarmList-sql= "+sql);
        	StringBuffer sb= new StringBuffer(); 
        	Cursor c=null;
           	try{
           		c=   db.rawQuery(sql, null);
           	}catch(Exception e){
           		
           	}
	       	 if (c != null) {
	       		c.moveToFirst();
	            if (c.moveToFirst())
	            {
	                do {          
	                    
	                    sb.append(c.getString(c.getColumnIndex("FarmName"))+":"+ c.getString(c.getColumnIndex("FarmYearlyDataID")) +"</br>");
	                } while (c.moveToNext());
	            }

		        }
	       	 c.close();
		        return sb.toString();//   	
        }
        
        public String fetchLocalAdvancedSearch(HashMap<String,String>values,  int action, int PurposeOfSearch){
        	if (PurposeOfSearch==searchBA_PLAN_VISIT){  //verify this  //pending
        		//Toast.makeText(getApplicationContext(), "YOu cannot do this offline, pls go online", Toast.LENGTH_LONG).show();
        		Log.v("fetchLocalAdv", "searchBA_PLAN_VISIT");
        		return null;
        	}
        	String 	queryBase="Select ForeNames,Surname, FarmerID FROM farmers where 1=1 ";        	
    		for (Map.Entry<String, String> entry: values.entrySet()){
    			String key=entry.getKey();
    			String value =entry.getValue();
    			queryBase+=" and "+key+"="+value;
    		}
        	
				String sql=queryBase;
			 	
	        	StringBuffer sb= new StringBuffer(); 
	        	Cursor c=null;
	           	try{
	           		c=   db.rawQuery(sql, null);
	           	}catch(Exception e){
	           		
	           	}
		       	 if (c != null) {
			            c.moveToFirst();
			            sb.append(c.getString(c.getColumnIndex("Surname"))+", "+c.getString(c.getColumnIndex("Forenames"))+":"+ c.getString(c.getColumnIndex("FarmerID"))+"</br>");
			        }
			        return sb.toString();        	
        	
        }
        
        public String fetchLocalFarmerDetails(String searchString){
            	String sql="SELECT FarmerID, Surname, ForeNames, FarmerReferenceNo, Gender, PhoneNo, DateOfBirth, ProvinceID, DistrictID, AdminPostID, LocalityID, ZoneID, FarmerGroupID, IDType, FarmerIDNo, HeadOfHouseholdName, HeadOfHouseholdGender, NumberOfDependents, IsGroupLeader, UserId, BirthCertificate, TemporaryID, NationalID, VoterRegistrationCard, IncomeTaxNo, Passport, DUAT, ID1Front, ID1Back, ID2Front, ID2Back, FarmerPicture, GPSLong, GPSLat, SystemTimeStamp, MobileTimeStamp from farmers where FarmerID like '%"+searchString+"%'  ";
        		StringBuffer sb= new StringBuffer();
            	Cursor c=null;
               	try{
               		c=   db.rawQuery(sql, null);
               	}catch(Exception e){
               		System.out.println("error in fetchLocalFarmerDetails: "+e.toString());
               	}
               	StringBuffer ValuesPart=new StringBuffer();
            	if (c.moveToFirst())
    	        {
    	            do {  
    	            	for (int i=0; i<c.getColumnCount(); i++){
    	            		ValuesPart.append(c.getColumnName(i));//
    	            			ValuesPart.append(":"+c.getString(i)+"</br>");  
    	            		    	            			
    	            		}
    	            } while (c.moveToNext());
    	        }

            	return ValuesPart.toString()+"successOK";
        }
        
        public void initOfflineVisitTables(){
        	String createVistTbl="CREATE TABLE IF NOT EXISTS visits ("+
				 "  VisitID text NOT NULL,"+		//using text timestamp instead of id->"  VisitID integer NOT NULL,"+	
				 "  VisitTypeID integer ,"+
				 "  FarmYearlyDataID integer ,"+
				 "  VisitDate text ,"+
				 "  ScheduledDate text ,"+
				 "  DateStarted text ,"+
				 "  DateCompleted text ,"+
				 "  GPSLong real ,"+
				 "  GPSLat real ,"+
				 "  EntryDate text ,"+
				 "  UserID integer ,"+
				 "  HA_KG_SR_GP real  ,"+
				 "  CropTypeID integer ,"+
				 "  SeedVarietyID integer ,"+
				 "  PercentageTotalLand real ,"+
				 "  PersonMet text ,"+
				 "  SeasonID integer DEFAULT 0,"+
				 "  Comments text ,"+
				 "  PicturePath text ,"+
				 "  ServiceProviderTypeID integer ,"+
				 "  ServiceProviderID integer "+
				 "  )";
     
        	db.execSQL(createVistTbl);
        }
        
        public String saveVisitDataOffline(HashMap<String, String>values){
        	// db.execSQL("DROP TABLE IF EXISTS visits"); //drop this before deployment
        	initOfflineVisitTables();//only create the table if it does not exist
        	//verify if null inserted into db
        	String theEntryDate=null; //for now. update when uploading to server.
        	
        	Calendar cal= Calendar.getInstance();
        	long timeStamp=cal.getTimeInMillis();
        	String uniqueID=""+timeStamp+"_"+Login.UserID;
        	
        	//theEntryDate=""+cal.YEAR+"-"+(cal.MONTH+1)+"-"+cal.DAY_OF_MONTH;
        	UtilityFunctions fxn= new UtilityFunctions();
        	//theEntryDate=fxn.dateToString(timeStamp);
        	theEntryDate=fxn.dateToStringTimeStamp(timeStamp);

        	
        	String sql="INSERT INTO visits "+
        			"(VisitID, VisitTypeID, FarmYearlyDataID, VisitDate, ScheduledDate, DateStarted, DateCompleted, "+
        			"GPSLong, GPSLat, EntryDate, UserID, HA_KG_SR_GP, CropTypeID, SeedVarietyID, "+
        			"PercentageTotalLand,PersonMet, Comments, PicturePath, ServiceProviderTypeID, ServiceProviderID) "+
        			"VALUES "+
        	"('"+uniqueID+"', '"+values.get("VisitTypeID")+"', '"+(values.get("FarmYearlyDataID")==null?"":values.get("FarmYearlyDataID"))+"', '"+(values.get("VisitDate")==null?"":values.get("VisitDate"))+"',"+
        			"'"+(values.get("ScheduledDate")==null?"":values.get("ScheduledDate"))+"', '"+(values.get("DateStarted")==null?"":values.get("DateStarted"))+"', '"+(values.get("DateCompleted")==null?"":values.get("DateCompleted"))+"', "+
        			"'"+(values.get("GPSLong")==null?"":values.get("GPSLong"))+"', '"+(values.get("GPSLat")==null?"":values.get("GPSLat"))+"', '"+theEntryDate+"', '"+(values.get("UserID")==null?"":values.get("UserID"))+"',"+
        			"'"+(values.get("HA_KG_SR_GP")==null?"":values.get("HA_KG_SR_GP"))+"', '"+(values.get("CropTypeID")==null?"":values.get("CropTypeID"))+"', '"+(values.get("SeedVarietyID")==null?"":values.get("SeedVarietyID"))+"', "+
        			"'"+(values.get("PercentageTotalLand")==null?"":values.get("PercentageTotalLand"))+"', '"+(values.get("PersonMet")==null?"":values.get("PersonMet"))+"',  "+
        			"'"+(values.get("Comments")==null?"":values.get("Comments"))+"', '"+(values.get("PicturePath")==null?"":values.get("PicturePath"))+"', '"+(values.get("ServiceProviderTypeID")==null?"":values.get("ServiceProviderTypeID"))+"', '"+(values.get("ServiceProviderID")==null?"":values.get("ServiceProviderID"))+"')";
        	
        	
        	//sql=sql.replace("null", "0");
        	//System.out.println(sql);
        	//note: above get will return null if key is not found. Monitor effect on db entries if null rather than "" is returned. May fail.
        	//perhaps do a replacement of null with "" before executing.
        	try{
        	db.execSQL(sql);
        	}catch (Exception e){  //SQLException
        		System.out.println("Local Save Error-saveVisitDataOffline"+e.toString());
        		return e.toString()+"SaveLocalfailedOK";
        	}
        	
        	return "successOK";
        	
        }
        

        public void wipeOfflineVisitData(){
       	 db.execSQL("DROP TABLE IF EXISTS visits");
       	 initOfflineVisitTables();
       }
        
        public String uploadSavedVisitData_getInsertValuesPart(){
        	//this function will read saved data and write it directly to the web server.
        	/*
        	 * it will sync with onilne db using insert or update, and make use of a unique
        	 * key on the millisecs+userid code. shd be reasonably unique.
        	 * will deal with the case where response from server is not received, and prevent duplicated data.
        	 *
        	 */
        	String sql="SELECT VisitID, VisitTypeID, FarmYearlyDataID, VisitDate, ScheduledDate, DateStarted, DateCompleted, "+
        			"GPSLong, GPSLat, EntryDate, UserID, HA_KG_SR_GP, CropTypeID, SeedVarietyID, "+
        			"PercentageTotalLand,PersonMet, SeasonID, Comments, PicturePath, ServiceProviderTypeID, ServiceProviderID "+
        			" from visits";
        	Cursor c=null;
           	try{
           		c=   db.rawQuery(sql, null);
           	}catch(Exception e){
           		
           	}
	       	 if (c != null) {
		            c.moveToFirst();
		        }
	       	 //convert cursor into a long string to be uploaded/posted to php
		        // mCursor;
	       	StringBuffer ValuesPart=new StringBuffer();
	       	
	       	if (c.moveToFirst())
	        {
	            do {  
	            	ValuesPart.append("(");
	            	//prepare the values part of an insert or replace statement
	            	for (int i=0; i<c.getColumnCount(); i++){
	            		if (i==c.getColumnCount()-1){
	            			ValuesPart.append("'"+c.getString(i)+"')");  
	            		}else{
	            			ValuesPart.append("'"+c.getString(i)+"'");//
	            		}
	            		if (!(c.isLast() && i==c.getColumnCount()-1)) { //last column of last row has no  comma but bracket 
	            			
	            			ValuesPart.append(","); 
	            		
	            		}
	            	}
	                
	            } while (c.moveToNext());
	        }
	        c.close();
        	 
        	return ValuesPart.toString();
        }
        
        String fixInsertQuery(String qry){
        	String qry1=new String(qry);
        	//System.out.println("old: "+qry);
        	qry1=qry1.replace("(", " select ");
        	
        	qry1=qry1.replace("),", " union ");
        	qry1=qry1.replace(")", "");
        	//System.out.println("new: "+qry1);
        	return qry1;
			/*
			 * use
			 * 
				insert into mytable (col1, col2)				  
				select 'a','b'
				union 
				select 'c','d'
				union ...

			*perhaps replace ), with "union" and replace ( with "select". 
			*But rist replace the last ')' at the end of the string

			 */
        }//fixInsertQuery
  
        
        
        public void initOfflineSurvey2013Tables(){
        	String createSurveyTbl="CREATE TABLE IF NOT EXISTS survey_data ("+
				"  `UserID` integer,"
				+"  `SurveyDataID` text NOT NULL,"
				+"  `Q1a1` text,"
				+"  `Q1a2` text,"
				+"  `Q1b` integer DEFAULT -1,"
				+"  `Q1c` text,"
				+"  `Q1d` integer DEFAULT -1,"
				+"  `Q1d1` text,"
				+"  `Q1f0` integer DEFAULT -1,"
				+"  `Q1f1` integer DEFAULT -1,"
				+"  `Q1f2` integer DEFAULT -1,"
				+"  `Q1f3` integer DEFAULT -1,"
				+"  `Q1f4` integer DEFAULT -1,"
				+"  `Q1f5` integer DEFAULT -1,"
				+"  `Q1g` integer DEFAULT -1,"
				+"  `Q1h` integer DEFAULT -1,"
				+"  `Q1i1` integer DEFAULT '0',"
				+"  `Q1i2` integer DEFAULT '0',"
				+"  `Q1i3` integer DEFAULT '0',"
				+"  `Q1i4` integer DEFAULT '0',"
				+"  `Q1i5` integer DEFAULT '0',"
				+"  `Q1j` integer DEFAULT -1,"
				+"  `Q1k` text,"
				+"  `Q2a` integer DEFAULT -1,"
				+"  `Q2b` integer DEFAULT -1,"
				+"  `Q2c` integer DEFAULT -1,"
				+"  `Q2dMFIQ1` integer DEFAULT -1,"
				+"  `Q2dMFIQ2` integer DEFAULT -1,"
				+"  `Q2dMFIQ3` integer DEFAULT -1,"
				+"  `Q2dMFIQ4` integer DEFAULT -1,"
				+"  `Q2dMFIQ5` integer DEFAULT -1,"
				+"  `Q2dMFIQ6` integer DEFAULT -1,"
				+"  `Q2dMFIQ7` integer DEFAULT -1,"
				+"  `Q2dMFIQ8` integer DEFAULT -1,"
				+"  `Q2dMFIQ9` integer DEFAULT -1,"
				+"  `Q2dMFIQ10` integer DEFAULT -1,"
				+"  `Q3a` integer DEFAULT -1,"
				+"  `Q3b` integer DEFAULT -1,"
				+"  `Q3c1` text,"
				+"  `Q3c2` text,"
				+"  `Q3c3` text,"
				+"  `Q3d` text,"
				+"  `Q3e1` text,"
				+"  `Q3e2` text,"
				+"  `Q3e3` text,"
				+"  `Q3f1` text,"
				+"  `Q3f2` text,"
				+"  `Q3f3` text,"
				+"  `Q3f4` text,"
				+"  `Q3f5` text,"
				+"  `Q3f6` text,"
				+"  `Q3g1` text,"
				+"  `Q3g2` text,"
				+"  `Q3h` integer DEFAULT -1,"
				+"  `Q3i` text,"
				+"  `Q3k1` integer DEFAULT -1,"
				+"  `Q3k2` integer DEFAULT -1,"
				+"  `Q3k3` integer DEFAULT -1,"
				+"  `Q3k4` integer DEFAULT -1,"
				+"  `Q3k5` integer DEFAULT -1,"
				+"  `Q3kb` integer DEFAULT -1,"
				+"  `Q3l` integer DEFAULT -1,"
				+"  `Q3m` integer DEFAULT '0',"
				+"  `Q3n1a` integer DEFAULT -1,"
				+"  `Q3n1b` integer DEFAULT -1,"
				+"  `Q3n1c` text,"
				+"  `Q3n2a` integer DEFAULT -1,"
				+"  `Q3n2b` integer DEFAULT -1,"
				+"  `Q3n2c` text,"
				+"  `Q3n3a` integer DEFAULT -1,"
				+"  `Q3n3b` integer DEFAULT -1,"
				+"  `Q3n3c` text,"
				+"  `Q3n4a` integer DEFAULT -1,"
				+"  `Q3n4b` integer DEFAULT -1,"
				+"  `Q3n4c` text,"
				+"  `Q3n5a` integer DEFAULT -1,"
				+"  `Q3n5b` integer DEFAULT -1,"
				+"  `Q3n5c` text,"
				+"  `Q3o` real,"
				+"  `Q3p` integer DEFAULT -1,"
				+"  `Q3q1` integer DEFAULT -1,"
				+"  `Q3q2` integer DEFAULT -1,"
				+"  `Q3q3` integer DEFAULT -1,"
				+"  `Q3qi` text,"
				+"  `Q3qii` real,"
				+"  `Q3r` integer DEFAULT -1,"
				+"  `Q3ri` text,"
				+"  `Q3rii` text,"
				+"  `Q3riii` integer DEFAULT -1,"
				+"  `Q3riv` integer DEFAULT -1,"
				+"  `Q3rv` text,"
				+"  `Q3s` integer DEFAULT -1,"
				+"  `Q3sii` integer DEFAULT -1,"
				+"  `Q4a` integer DEFAULT -1,"
				+"  `Q4ai` text,"
				+"  `Q4b` integer DEFAULT -1,"
				+"  `Q4bi` integer DEFAULT -1,"
				+"  `Q4bii` text,"
				+"  `Q4biii` integer DEFAULT -1,"
				+"  `Q4biv` text,"
				+"  `Q4c` text,"
				+"  `Q4d` real,"
				+"  `Q4e` real,"
				+"  `Q4f1a` real,"
				+"  `Q4f1b` text,"
				+"  `Q4f1c` text,"
				+"  `Q4f1d` real,"
				+"  `Q4f2a` real,"
				+"  `Q4f2b` text,"
				+"  `Q4f2c` text,"
				+"  `Q4f2d` real,"
				+"  `Q4f3a` real,"
				+"  `Q4f3b` text,"
				+"  `Q4f3c` text,"
				+"  `Q4f3d` real,"
				+"  `Q4g` integer DEFAULT -1,"
				+"  `Q4g2` text"
				+")" ;
				     
        	db.execSQL(createSurveyTbl);
        }
        
        public void wipeOfflineSurvey2013Data(){
          	 db.execSQL("DROP TABLE IF EXISTS survey_data");
          	 initOfflineVisitTables();
          }
        
        public String saveSurvey2013DataOffline(HashMap<String, String>values){
        	//wipeOfflineSurvey2013Data();  // keep this for testing purposes only
        	initOfflineSurvey2013Tables();//only create the table if it does not exist
        	/*
        	String sql=values.get("theSQL");
        	try{
        	db.execSQL(sql);
        	}catch (Exception e){  //SQLException
        		System.out.println("Local Save Error-saveSurveyDataOffline"+e.toString());
        		return e.toString()+"SaveLocalfailedOK";
        	}
        	*/
        	
        	//verify if null inserted into db
        	
        	String theEntryDate=null; //for now. update when uploading to server.
        	
        	Calendar cal= Calendar.getInstance();
        	long timeStamp=cal.getTimeInMillis();
        	String uniqueID=""+timeStamp+"_"+Login.UserID;
        	
        	//theEntryDate=""+cal.YEAR+"-"+(cal.MONTH+1)+"-"+cal.DAY_OF_MONTH;
        	UtilityFunctions fxn= new UtilityFunctions();
        	//theEntryDate=fxn.dateToString(timeStamp);
        	theEntryDate=fxn.dateToStringTimeStamp(timeStamp);

        	
        	String sql="INSERT INTO survey_data "+
        			"(UserID ,SurveyDataID ,Q1a1 ,Q1a2 ,Q1b ,Q1c ,Q1d ,Q1d1 ,Q1f0 ,Q1f1 ,Q1f2 ,Q1f3 ,Q1f4 ,Q1f5 ,Q1g ,Q1h ,Q1i1 ,Q1i2 ,Q1i3 ,Q1i4 ,Q1i5 ,Q1j ,Q1k ,Q2a ,Q2b ,Q2c ,Q2dMFIQ1 ,Q2dMFIQ2 ,Q2dMFIQ3 ,Q2dMFIQ4 ,Q2dMFIQ5 ,Q2dMFIQ6 ,Q2dMFIQ7 ,Q2dMFIQ8 ,Q2dMFIQ9 ,Q2dMFIQ10 ,Q3a ,Q3b ,Q3c1 ,Q3c2 ,Q3c3 ,Q3d ,Q3e1 ,Q3e2 ,Q3e3 ,Q3f1 ,Q3f2 ,Q3f3 ,Q3f4 ,Q3f5 ,Q3f6 ,Q3g1 ,Q3g2 ,Q3h ,Q3i ,Q3k1 ,Q3k2 ,Q3k3 ,Q3k4 ,Q3k5 ,Q3kb ,Q3l ,Q3m ,Q3n1a ,Q3n1b ,Q3n1c ,Q3n2a ,Q3n2b ,Q3n2c ,Q3n3a ,Q3n3b ,Q3n3c ,Q3n4a ,Q3n4b ,Q3n4c ,Q3n5a ,Q3n5b ,Q3n5c ,Q3o ,Q3p ,Q3q1 ,Q3q2 ,Q3q3 ,Q3qi ,Q3qii ,Q3r ,Q3ri ,Q3rii ,Q3riii ,Q3riv ,Q3rv ,Q3s ,Q3sii ,Q4a ,Q4ai ,Q4b ,Q4bi ,Q4bii ,Q4biii ,Q4biv ,Q4c ,Q4d ,Q4e ,Q4f1a ,Q4f1b ,Q4f1c ,Q4f1d ,Q4f2a ,Q4f2b ,Q4f2c ,Q4f2d ,Q4f3a ,Q4f3b ,Q4f3c ,Q4f3d ,Q4g ,Q4g2 ) "+
        			"VALUES "+
        	"('"+values.get("UserID")+"', '"+values.get("SurveyDataID")+"', '"+values.get("Q1a1")+"', '"+values.get("Q1a2")+"', '"+values.get("Q1b")+"', '"+values.get("Q1c")+"', '"+values.get("Q1d")+"', '"+values.get("Q1d1")+"', '"+values.get("Q1f0")+"', '"+values.get("Q1f1")+"', '"+values.get("Q1f2")+"', '"+values.get("Q1f3")+"', '"+values.get("Q1f4")+"', '"+values.get("Q1f5")+"', '"+values.get("Q1g")+"', '"+values.get("Q1h")+"', '"+values.get("Q1i1")+"', '"+values.get("Q1i2")+"', '"+values.get("Q1i3")+"', '"+values.get("Q1i4")+"', '"+values.get("Q1i5")+"', '"+values.get("Q1j")+"', '"+values.get("Q1k")+"', '"+values.get("Q2a")+"', '"+values.get("Q2b")+"', '"+values.get("Q2c")+"', '"+values.get("Q2dMFIQ1")+"', '"+values.get("Q2dMFIQ2")+"', '"+values.get("Q2dMFIQ3")+"', '"+values.get("Q2dMFIQ4")+"', '"+values.get("Q2dMFIQ5")+"', '"+values.get("Q2dMFIQ6")+"', '"+values.get("Q2dMFIQ7")+"', '"+values.get("Q2dMFIQ8")+"', '"+values.get("Q2dMFIQ9")+"', '"+values.get("Q2dMFIQ10")+"', '"+values.get("Q3a")+"', '"+values.get("Q3b")+"', '"+values.get("Q3c1")+"', '"+values.get("Q3c2")+"', '"+values.get("Q3c3")+"', '"+values.get("Q3d")+"', '"+values.get("Q3e1")+"', '"+values.get("Q3e2")+"', '"+values.get("Q3e3")+"', '"+values.get("Q3f1")+"', '"+values.get("Q3f2")+"', '"+values.get("Q3f3")+"', '"+values.get("Q3f4")+"', '"+values.get("Q3f5")+"', '"+values.get("Q3f6")+"', '"+values.get("Q3g1")+"', '"+values.get("Q3g2")+"', '"+values.get("Q3h")+"', '"+values.get("Q3i")+"', '"+values.get("Q3k1")+"', '"+values.get("Q3k2")+"', '"+values.get("Q3k3")+"', '"+values.get("Q3k4")+"', '"+values.get("Q3k5")+"', '"+values.get("Q3kb")+"', '"+values.get("Q3l")+"', '"+values.get("Q3m")+"', '"+values.get("Q3n1a")+"', '"+values.get("Q3n1b")+"', '"+values.get("Q3n1c")+"', '"+values.get("Q3n2a")+"', '"+values.get("Q3n2b")+"', '"+values.get("Q3n2c")+"', '"+values.get("Q3n3a")+"', '"+values.get("Q3n3b")+"', '"+values.get("Q3n3c")+"', '"+values.get("Q3n4a")+"', '"+values.get("Q3n4b")+"', '"+values.get("Q3n4c")+"', '"+values.get("Q3n5a")+"', '"+values.get("Q3n5b")+"', '"+values.get("Q3n5c")+"', '"+values.get("Q3o")+"', '"+values.get("Q3p")+"', '"+values.get("Q3q1")+"', '"+values.get("Q3q2")+"', '"+values.get("Q3q3")+"', '"+values.get("Q3qi")+"', '"+values.get("Q3qii")+"', '"+values.get("Q3r")+"', '"+values.get("Q3ri")+"', '"+values.get("Q3rii")+"', '"+values.get("Q3riii")+"', '"+values.get("Q3riv")+"', '"+values.get("Q3rv")+"', '"+values.get("Q3s")+"', '"+values.get("Q3sii")+"', '"+values.get("Q4a")+"', '"+values.get("Q4ai")+"', '"+values.get("Q4b")+"', '"+values.get("Q4bi")+"', '"+values.get("Q4bii")+"', '"+values.get("Q4biii")+"', '"+values.get("Q4biv")+"', '"+values.get("Q4c")+"', '"+values.get("Q4d")+"', '"+values.get("Q4e")+"', '"+values.get("Q4f1a")+"', '"+values.get("Q4f1b")+"', '"+values.get("Q4f1c")+"', '"+values.get("Q4f1d")+"', '"+values.get("Q4f2a")+"', '"+values.get("Q4f2b")+"', '"+values.get("Q4f2c")+"', '"+values.get("Q4f2d")+"', '"+values.get("Q4f3a")+"', '"+values.get("Q4f3b")+"', '"+values.get("Q4f3c")+"', '"+values.get("Q4f3d")+"', '"+values.get("Q4g")+"', '"+values.get("Q4g2")+"')";
        	
        	//sql=sql.replace("null", "0");
        	//System.out.println(sql);
        	//note: above get will return null if key is not found. Monitor effect on db entries if null rather than "" is returned. May fail.
        	//perhaps do a replacement of null with "" before executing.
        	try{
        	db.execSQL(sql);
        	}catch (Exception e){  //SQLException
        		System.out.println("Local Save Error-saveVisitDataOffline"+e.toString());
        		return e.toString()+"SaveLocalfailedOK";
        	}
        	
        	
        	return "successOK";
        	
        }

        public String uploadSavedSurvey2013Data_getInsertValuesPart(){
        	//this function will read saved data and write it directly to the web server.
        	/*
        	 * it will sync with onilne db using insert or update, and make use of a unique
        	 * key on the millisecs+userid code. shd be reasonably unique.
        	 * will deal with the case where response from server is not received, and prevent duplicated data.
        	 *
        	 */
        	Log.v("in upload sved survey data","1c is null");
        	String sql="SELECT UserID ,SurveyDataID ,Q1a1 ,Q1a2 ,Q1b ,Q1c ,Q1d ,Q1d1 ,Q1f0 ,Q1f1 ,Q1f2 ,Q1f3 ,Q1f4 ,Q1f5 ,Q1g ,Q1h ,Q1i1 ,Q1i2 ,Q1i3 ,Q1i4 ,Q1i5 ,Q1j ,Q1k ,Q2a ,Q2b ,Q2c ,Q2dMFIQ1 ,Q2dMFIQ2 ,Q2dMFIQ3 ,Q2dMFIQ4 ,Q2dMFIQ5 ,Q2dMFIQ6 ,Q2dMFIQ7 ,Q2dMFIQ8 ,Q2dMFIQ9 ,Q2dMFIQ10 ,Q3a ,Q3b ,Q3c1 ,Q3c2 ,Q3c3 ,Q3d ,Q3e1 ,Q3e2 ,Q3e3 ,Q3f1 ,Q3f2 ,Q3f3 ,Q3f4 ,Q3f5 ,Q3f6 ,Q3g1 ,Q3g2 ,Q3h ,Q3i ,Q3k1 ,Q3k2 ,Q3k3 ,Q3k4 ,Q3k5 ,Q3kb ,Q3l ,Q3m ,Q3n1a ,Q3n1b ,Q3n1c ,Q3n2a ,Q3n2b ,Q3n2c ,Q3n3a ,Q3n3b ,Q3n3c ,Q3n4a ,Q3n4b ,Q3n4c ,Q3n5a ,Q3n5b ,Q3n5c ,Q3o ,Q3p ,Q3q1 ,Q3q2 ,Q3q3 ,Q3qi ,Q3qii ,Q3r ,Q3ri ,Q3rii ,Q3riii ,Q3riv ,Q3rv ,Q3s ,Q3sii ,Q4a ,Q4ai ,Q4b ,Q4bi ,Q4bii ,Q4biii ,Q4biv ,Q4c ,Q4d ,Q4e ,Q4f1a ,Q4f1b ,Q4f1c ,Q4f1d ,Q4f2a ,Q4f2b ,Q4f2c ,Q4f2d ,Q4f3a ,Q4f3b ,Q4f3c ,Q4f3d ,Q4g ,Q4g2 from survey_data"; //is this in correct sequence?
        	//if not use
        	//sql="SELECT `UserID`, `SurveyDataID`, `Q1a1`, `Q1a2`, `Q1b`, `Q1c`, `Q1d`, `Q1f0`, `Q1f1`, `Q1f2`, `Q1f3`, `Q1f4`, `Q1f5`, `Q1g`, `Q1h`, `Q1i1`, `Q1i2`, `Q1i3`, `Q1i4`, `Q1i5`, `Q1j`, `Q1k`, `Q2a`, `Q2b`, `Q2c`, `Q2dMFIQ1`, `Q2dMFIQ2`, `Q2dMFIQ3`, `Q2dMFIQ4`, `Q2dMFIQ5`, `Q2dMFIQ6`, `Q2dMFIQ7`, `Q2dMFIQ8`, `Q2dMFIQ9`, `Q2dMFIQ10`, `Q3a`, `Q3b`, `Q3c1`, `Q3c2`, `Q3c3`, `Q3d`, `Q3e1`, `Q3e2`, `Q3e3`, `Q3f1`, `Q3f2`, `Q3f3`, `Q3f4`, `Q3f5`, `Q3f6`, `Q3g1`, `Q3g2`, `Q3h`, `Q3i`, `Q3k1`, `Q3k2`, `Q3k3`, `Q3k4`, `Q3k5`, `Q3kb`, `Q3l`, `Q3m`, `Q3n1a`, `Q3n1b`, `Q3n1c`, `Q3n2a`, `Q3n2b`, `Q3n2c`, `Q3n3a`, `Q3n3b`, `Q3n3c`, `Q3n4a`, `Q3n4b`, `Q3n4c`, `Q3n5a`, `Q3n5b`, `Q3n5c`, `Q3o`, `Q3p`, `Q3q1`, `Q3q2`, `Q3q3`, `Q3qi`, `Q3qii`, `Q3r`, `Q3ri`, `Q3rii`, `Q3riii`, `Q3riv`, `Q3rv`, `Q3s`, `Q3sii`, `Q4a`, `Q4ai`, `Q4b`, `Q4bi`, `Q4bii`, `Q4biii`, `Q4biv`, `Q4c`, `Q4d`, `Q4e`, `Q4f1a`, `Q4f1b`, `Q4f1c`, `Q4f1d`, `Q4f2a`, `Q4f2b`, `Q4f2c`, `Q4f2d`, `Q4f3a`, `Q4f3b`, `Q4f3c`, `Q4f3d`, `Q4g`, `Q4g2` FROM `survey_data` "
        	Cursor c=null;
           	try{
           		c=   db.rawQuery(sql, null);
           	}catch(Exception e){
           		
           	}
           	
	       	 if (c != null) {
		            c.moveToFirst();
		    
		        }
	      
	       	 //convert cursor into a long string to be uploaded/posted to php
		        // mCursor;
	       	StringBuffer ValuesPart=new StringBuffer();
	       	
	       	if (c.moveToFirst())
	        {
	            do {  
	            	ValuesPart.append("(");
	            	//prepare the values part of an insert or replace statement
	            	for (int i=0; i<c.getColumnCount(); i++){
	            		if (i==c.getColumnCount()-1){
	            			ValuesPart.append("'"+c.getString(i)+"')");  
	            		}else{
	            			ValuesPart.append("'"+c.getString(i)+"'");//
	            		}
	            		if (!(c.isLast() && i==c.getColumnCount()-1)) { //last column of last row has no  comma but bracket 
	            			
	            			ValuesPart.append(","); 
	            		
	            		}
	            	}
	                
	            } while (c.moveToNext());
	        }
	        c.close();
        	 
        	return ValuesPart.toString();
        }

        
        
        //offline saving code for PigeionPea Harvest (2013)

        public void initOfflinePigeionPeaHarvestTables(){  //+"//  `PigeonPeaHarvestID` int(11) NOT NULL AUTO_INCREMENT,"+
        	String createPigeionPeaTbl="CREATE TABLE IF NOT EXISTS `pigeonpea_harvest` ("	+    
        			"`PigeonPeaHarvestID`  text NOT NULL primary key on conflict replace,"+
        			"  `SalesContract` integer DEFAULT -1,"+
        			"  `Planted2011` real,"+
        			"  `Planted2012` real,"+
        			"  `SeedVarietyPlanted` text,"+
        			"  `QuantitySeedReceived` real,"+
        			"  `SeedRateUsed` real,"+
        			"  `Q7Kg` real,"+
        			"  `Q7NoBags` real,"+
        			"  `Q7WtOfOneBag` real,"+
        			"  `Q7NoBuckets` real,"+
        			"  `Q7WtOfOneBucket` real,"+
        			"  `Q8Kg` real,"+
        			"  `Q8NoBags` real,"+
        			"  `Q8WtOfOneBag` real,"+
        			"  `Q8NoBuckets` integer,"+
        			"  `Q8WtOfOneBucket` real,"+
        			"  `Q9Kg` real,"+
        			"  `Q9NoBags` real,"+
        			"  `Q9WtOfOneBag` real,"+
        			"  `Q9NoBuckets` real,"+
        			"  `Q9WtOfOneBucket` real,"+
        			"  `BuyersGreen` text,"+
        			"  `PriceSoldGreen` real,"+
        			"  `Q12Kg` real,"+
        			"  `Q12NoBags` real,"+
        			"  `Q12WtOfOneBag` real,"+
        			"  `Q12NoBuckets` real,"+
        			"  `Q12WtOfOneBucket` real,"+
        			"  `BuyersOutsideContractDry` text,"+
        			"  `PriceSoldOutsideContractDry` real,"+
        			"  `Q15Kg` real,"+
        			"  `Q15NoBags` real,"+
        			"  `Q15WtOfOneBag` real,"+
        			"  `Q15NoBuckets` real,"+
        			"  `Q15WtOfOneBucket` real,"+
        			"  `BuyersWithinContract` text,"+
        			"  `PriceSoldWithinContract` real,"+
        			"  `FarmerID` integer,"+
        			"  `UserID` integer,"+
        			" `FinishedHarvest` integer,"+        			 
        			" `MobileTimeStamp` text, "+
        			" `GPSLong` real, "+
        			" `GPSLat` real "+
        			") ";
//" `SystemTime`
        		
				     
        	db.execSQL(createPigeionPeaTbl);
        }
        
        public void wipeOfflinePigeionPeaHarvestData(){
          	 db.execSQL("DROP TABLE IF EXISTS pigeonpea_harvest");
          	initOfflinePigeionPeaHarvestTables();
          }
        
        public String savePigeionPeaHarvestDataOffline(HashMap<String, String>values){
        	//wipeOfflinePigeionPeaHarvestData();  // keep this for testing purposes only
        	initOfflinePigeionPeaHarvestTables();//only create the table if it does not exist
        	/*
        	String sql=values.get("theSQL");
        	try{
        	db.execSQL(sql);
        	}catch (Exception e){  //SQLException
        		System.out.println("Local Save Error-saveSurveyDataOffline"+e.toString());
        		return e.toString()+"SaveLocalfailedOK";
        	}
        	*/
        	
        	//verify if null inserted into db
        	
        	String theEntryDate=null; //for now. update when uploading to server.
        	
        	//Calendar cal= Calendar.getInstance();
        	//long timeStamp=cal.getTimeInMillis();
        	//String uniqueID=""+timeStamp+"_"+Login.UserID;
        	
        	//theEntryDate=""+cal.YEAR+"-"+(cal.MONTH+1)+"-"+cal.DAY_OF_MONTH;
        	//UtilityFunctions fxn= new UtilityFunctions();
        	//theEntryDate=fxn.dateToString(timeStamp);
        	//theEntryDate=fxn.dateToStringTimeStamp(timeStamp);
        	
        	String uniqueID=UtilityFunctions.uniqueID("");

        	String sql="INSERT INTO `pigeonpea_harvest` ( `PigeonPeaHarvestID`,  `SalesContract`,  `Planted2011`,  `Planted2012`,  `SeedVarietyPlanted`,  `QuantitySeedReceived`,  `SeedRateUsed`,   `Q7Kg`,  `Q7NoBags`,  `Q7WtOfOneBag`,  `Q7NoBuckets`,  `Q7WtOfOneBucket`,   `Q8Kg`,  `Q8NoBags`,  `Q8WtOfOneBag`,  `Q8NoBuckets`,  `Q8WtOfOneBucket`,    `Q9Kg`,  `Q9NoBags`,  `Q9WtOfOneBag`,  `Q9NoBuckets`,  `Q9WtOfOneBucket`,  `BuyersGreen`,  `PriceSoldGreen`,   `Q12Kg`,  `Q12NoBags`,  `Q12WtOfOneBag`,  `Q12NoBuckets`,  `Q12WtOfOneBucket`,  `BuyersOutsideContractDry`,  `PriceSoldOutsideContractDry`,   `Q15Kg`,  `Q15NoBags`,  `Q15WtOfOneBag`,  `Q15NoBuckets`,  `Q15WtOfOneBucket`,  `BuyersWithinContract`,  `PriceSoldWithinContract`,  `FarmerID`, `UserID`, `FinishedHarvest`,  `MobileTimeStamp`, `GPSLong`, `GPSLat`) "+ 
        			"Values( "+ 
        			"		'"+uniqueID+"', '"+values.get("SalesContract")+"', '"+values.get("Planted2011")+"', '"+values.get("Planted2012")+"', '"+values.get("SeedVarietyPlanted")+"', '"+values.get("QuantitySeedReceived")+"', '"+values.get("SeedRateUsed")+"', '"+values.get("Q7Kg")+"', '"+values.get("Q7NoBags")+"', '"+values.get("Q7WtOfOneBag")+"', '"+values.get("Q7NoBuckets")+"', '"+values.get("Q7WtOfOneBucket")+"', '"+values.get("Q8Kg")+"', '"+values.get("Q8NoBags")+"', '"+values.get("Q8WtOfOneBag")+"', '"+values.get("Q8NoBuckets")+"', '"+values.get("Q8WtOfOneBucket")+"', '"+values.get("Q9Kg")+"', '"+values.get("Q9NoBags")+"', '"+values.get("Q9WtOfOneBag")+"', '"+values.get("Q9NoBuckets")+"', '"+values.get("Q9WtOfOneBucket")+"', '"+values.get("BuyersGreen")+"', '"+values.get("PriceSoldGreen")+"', '"+values.get("Q12Kg")+"', '"+values.get("Q12NoBags")+"', '"+values.get("Q12WtOfOneBag")+"', '"+values.get("Q12NoBuckets")+"', '"+values.get("Q12WtOfOneBucket")+"', '"+values.get("BuyersOutsideContractDry")+"', '"+values.get("PriceSoldOutsideContractDry")+"', '"+values.get("Q15Kg")+"', '"+values.get("Q15NoBags")+"', '"+values.get("Q15WtOfOneBag")+"', '"+values.get("Q15NoBuckets")+"', '"+values.get("Q15WtOfOneBucket")+"', '"+values.get("BuyersWithinContract")+"', '"+values.get("PriceSoldWithinContract")+"', '"+values.get("FarmerID")+"', '"+values.get("UserID")+"' , '"+values.get("FinishedHarvest")+"',  '"+values.get("MobileTimeStamp")+"', '"+values.get("GPSLong")+"', '"+values.get("GPSLat")+"'  "+ 
        			"		)";
        	
        	
        	//sql=sql.replace("null", "0");
        	//System.out.println(sql);
        	//note: above get will return null if key is not found. Monitor effect on db entries if null rather than "" is returned. May fail.
        	//perhaps do a replacement of null with "" before executing.
        	try{
        	db.execSQL(sql);
        	}catch (Exception e){  //SQLException
        		System.out.println("Local Save Error-savePigeonPeaHarvestDataOffline"+e.toString());
        		return e.toString()+"SaveLocalfailedOK";
        	}
        	
        	
        	return "successOK";
        	
        }

        public String uploadSavedPigeionPeaHarvestData_getInsertValuesPart(){
        	//this function will read saved data and write it directly to the web server.
        	/*
        	 * it will sync with onilne db using insert or update, and make use of a unique
        	 * key on the millisecs+userid code. shd be reasonably unique.
        	 * will deal with the case where response from server is not received, and prevent duplicated data.
        	 *
        	 */
        	//Log.v("in upload sved pigeionPea harvest data","---");
        	String sql="select `PigeonPeaHarvestID`, `SalesContract`, `Planted2011`, `Planted2012`, `SeedVarietyPlanted`, `QuantitySeedReceived`, `SeedRateUsed`, `Q7Kg`, `Q7NoBags`, `Q7WtOfOneBag`, `Q7NoBuckets`, `Q7WtOfOneBucket`, `Q8Kg`, `Q8NoBags`, `Q8WtOfOneBag`, `Q8NoBuckets`, `Q8WtOfOneBucket`, `Q9Kg`, `Q9NoBags`, `Q9WtOfOneBag`, `Q9NoBuckets`, `Q9WtOfOneBucket`, `BuyersGreen`, `PriceSoldGreen`, `Q12Kg`, `Q12NoBags`, `Q12WtOfOneBag`, `Q12NoBuckets`, `Q12WtOfOneBucket`, `BuyersOutsideContractDry`, `PriceSoldOutsideContractDry`, `Q15Kg`, `Q15NoBags`, `Q15WtOfOneBag`, `Q15NoBuckets`, `Q15WtOfOneBucket`, `BuyersWithinContract`, `PriceSoldWithinContract`, `FarmerID`, `UserID`, `FinishedHarvest`, `MobileTimeStamp`, `GPSLong`, `GPSLat` from pigeonpea_harvest ";
        	
        	Cursor c=null;
           	try{
           		c=   db.rawQuery(sql, null);
           	}catch(Exception e){
           		
           	}
           	
	       	 if (c != null) {
		            c.moveToFirst();
		    
		        }
	      
	       	 //convert cursor into a long string to be uploaded/posted to php
		        // mCursor;
	       	StringBuffer ValuesPart=new StringBuffer();
	       	if (c != null) {  //placed in here in case no data exists locally.
	       	if (c.moveToFirst())
	        {
	            do {  
	            	ValuesPart.append("(");
	            	//prepare the values part of an insert or replace statement
	            	for (int i=0; i<c.getColumnCount(); i++){
	            		if (i==c.getColumnCount()-1){
	            			ValuesPart.append("'"+c.getString(i)+"', NOW() )");  //closing brace + remote system time stamp
	            		}else{
	            			ValuesPart.append("'"+c.getString(i)+"'");//
	            		}
	            		if (!(c.isLast() && i==c.getColumnCount()-1)) { //last column of last row has no  comma but bracket 
	            			
	            			ValuesPart.append(","); 
	            		
	            		}
	            	}
	                
	            } while (c.moveToNext());
	        }
	        c.close();
        	 
        	return ValuesPart.toString();
	       	}
	       	return null;
        }

        //offline saving code for farmer data //new sept 2013!
        public void initOfflineFarmerTables(){  //+"//  only Farmer table updated.
        /*	 String createFarmerTbl="CREATE TABLE IF NOT EXISTS farmers ("+
     				// "_id integer primary key autoincrement,"+
     				 "  FarmerID integer NOT NULL primary key on conflict replace,"+
     				 "  Surname text NOT NULL,"+
     				 "  ForeNames text NOT NULL,"+
     				 "  FarmerReferenceNo text NOT NULL,"+
     				 "  Gender integer NOT NULL ,"+
     				 "  PhoneNo text NOT NULL ,"+
     				 "  textOfBirth text NOT NULL,"+
     				 "  ProvinceID integer NOT NULL,"+
     				 "  DistrictID integer NOT NULL,"+
     				 "  AdminPostID integer NOT NULL,"+
     				 "  LocalityID integer NOT NULL,"+
     				 "  ZoneID integer NOT NULL,"+
     				 "  FarmerGroupID integer NOT NULL,"+
     				 "  IDType integer NOT NULL,"+
     				 "  FarmerIDNo text NOT NULL,"+
     				 "  HeadOfHouseholdName text NOT NULL,"+
     				 "  HeadOfHouseholdGender integer NOT NULL,"+
     				 "  NumberOfDependents integer NOT NULL,"+
     				 "  IsGroupLeader tinyint(4) NOT NULL ,"+
     				 "  UserId integer NOT NULL,"+
     				 " BirthCertificate  text,"+
     				 " TemporaryID  text,"+
     				 " NationalID  text,"+
     				 " VoterRegistrationCard  text,"+
     				 " IncomeTaxNo  text,"+
     				 " Passport  text,"+
     				 " DUAT  text,"+
     				 " ID1Front  text,"+
     				 " ID1Back  text,"+
     				 " ID2Front  text,"+
     				 " ID2Back  text,"+
     				 " FarmerPicture  text,"+
     				 " GPSLong  real,"+
     				 " GPSLat  real,"+
     				 " MobileTimeStamp  text"+
     				 		" );";
        	 */
//" `SystemTime`
        		
				     
        	db.execSQL(createFarmerTbl);
        }
        
        public void wipeOfflineFarmerData_new(){
          	 db.execSQL("DROP TABLE IF EXISTS farmers");
          	initOfflineFarmerTables();
          }
        
        public String saveFarmerDataOffline(HashMap<String, String>values){
        	//wipeOfflineFarmerData_new();  // keep this for testing purposes only
        	initOfflineFarmerTables();//only create the table if it does not exist
        	
        	
        	//verify if null inserted into db
        	/*
        	String theEntryDate=null; //for now. update when uploading to server.
        	
        	Calendar cal= Calendar.getInstance();
        	long timeStamp=cal.getTimeInMillis();
        	String uniqueID=""+timeStamp+"_"+Login.UserID;
        	
        	//theEntryDate=""+cal.YEAR+"-"+(cal.MONTH+1)+"-"+cal.DAY_OF_MONTH;
        	UtilityFunctions fxn= new UtilityFunctions();
        	//theEntryDate=fxn.dateToString(timeStamp);
        	theEntryDate=fxn.dateToStringTimeStamp(timeStamp); */
        	
        	//String uniqueID=UtilityFunctions.uniqueID("");

        	String sql="REPLACE INTO `farmers` ( `FarmerID`, `Surname`, `ForeNames`, `FarmerReferenceNo`, `Gender`, `PhoneNo`, `DateOfBirth`, `ProvinceID`, `DistrictID`, `AdminPostID`, `LocalityID`, `ZoneID`, `FarmerGroupID`, `IDType`, `FarmerIDNo`, `HeadOfHouseholdName`, `HeadOfHouseholdGender`, `NumberOfDependents`, `IsGroupLeader`, `UserId`, `BirthCertificate`, `TemporaryID`, `NationalID`, `VoterRegistrationCard`, `IncomeTaxNo`, `Passport`, `DUAT`, `ID1Front`, `ID1Back`, `ID2Front`, `ID2Back`, `FarmerPicture`, `GPSLong`, `GPSLat`, `MobileTimeStamp`) "+ 
        			"Values( "+ 
        			"		'"+values.get("FarmerID")+"', '"+values.get("Surname")+"', '"+values.get("ForeNames")+"', '"+values.get("FarmerReferenceNo")+"', '"+values.get("Gender")+"', '"+values.get("PhoneNo")+"', '"+values.get("DateOfBirth")+"', '"+values.get("ProvinceID")+"', '"+values.get("DistrictID")+"', '"+values.get("AdminPostID")+"', '"+values.get("LocalityID")+"', '"+values.get("ZoneID")+"', '"+values.get("FarmerGroupID")+"', '"+values.get("IDType")+"', '"+values.get("FarmerIDNo")+"', '"+values.get("HeadOfHouseholdName")+"', '"+values.get("HeadOfHouseholdGender")+"', '"+values.get("NumberOfDependents")+"', '"+values.get("IsGroupLeader")+"', '"+values.get("UserId")+"', '"+values.get("BirthCertificate")+"', '"+values.get("TemporaryID")+"', '"+values.get("NationalID")+"', '"+values.get("VoterRegistrationCard")+"', '"+values.get("IncomeTaxNo")+"', '"+values.get("Passport")+"', '"+values.get("DUAT")+"', '"+values.get("ID1Front")+"', '"+values.get("ID1Back")+"', '"+values.get("ID2Front")+"', '"+values.get("ID2Back")+"', '"+values.get("FarmerPicture")+"', '"+values.get("GPSLong")+"', '"+values.get("GPSLat")+"',  '"+values.get("MobileTimeStamp")+"'  "+ 
        			"		)";
        	
        	
        	try{
        	db.execSQL(sql);
        	}catch (Exception e){  //SQLException
        		System.out.println("Local Save Error-saveFarmerDataOffline"+e.toString());
        		return e.toString()+"SaveLocalfailedOK";
        	}
        	
        	
        	return "successOK";
        	
        }

        public String uploadSavedFarmerData_getInsertValuesPart(){
        	//this function will read saved data and write it directly to the web server.
        	/*
        	 * it will sync with onilne db using insert or update, and make use of a unique
        	 * key on the millisecs+userid code. shd be reasonably unique.
        	 * will deal with the case where response from server is not received, and prevent duplicated data.
        	 *
        	 */
        	//Log.v("in upload sved pigeionPea harvest data","---");
        	String sql="select `FarmerID`, `Surname`, `ForeNames`, `FarmerReferenceNo`, `Gender`, `PhoneNo`, `DateOfBirth`, `ProvinceID`, `DistrictID`, `AdminPostID`, `LocalityID`, `ZoneID`, `FarmerGroupID`, `IDType`, `FarmerIDNo`, `HeadOfHouseholdName`, `HeadOfHouseholdGender`, `NumberOfDependents`, `IsGroupLeader`, `UserId`, `BirthCertificate`, `TemporaryID`, `NationalID`, `VoterRegistrationCard`, `IncomeTaxNo`, `Passport`, `DUAT`, `ID1Front`, `ID1Back`, `ID2Front`, `ID2Back`, `FarmerPicture`, `GPSLong`, `GPSLat`,  `MobileTimeStamp` from farmers ";
        	
        	Cursor c=null;
           	try{
           		c=   db.rawQuery(sql, null);
           	}catch(Exception e){
           		
           	}
           	
	       	 if (c != null) {
		            c.moveToFirst();
		    
		        }
	      
	       	 //convert cursor into a long string to be uploaded/posted to php
		        // mCursor;
	       	StringBuffer ValuesPart=new StringBuffer();
	       	if (c != null) {  //placed in here in case no data exists locally.
	       	if (c.moveToFirst())
	        {
	            do {  
	            	ValuesPart.append("(");
	            	//prepare the values part of an insert or replace statement
	            	for (int i=0; i<c.getColumnCount(); i++){
	            		if (i==c.getColumnCount()-1){
	            			ValuesPart.append("'"+c.getString(i)+"', NOW() )");  //closing paranthesis, but added timestamp
	            		}else{
	            			ValuesPart.append("'"+c.getString(i)+"'");//
	            		}
	            		if (!(c.isLast() && i==c.getColumnCount()-1)) { //last column of last row has no  comma but bracket 
	            			
	            			ValuesPart.append(","); 
	            		
	            		}
	            	}
	                
	            } while (c.moveToNext());
	        }
	        c.close();
        	 
        	return ValuesPart.toString();
	       	}
	       	return null;
        }
        
        //offline saving code for FARM data: saveFarmDataOffline //Oct 2-2013
        
        public void initOfflineFarmTables(){  //+"//  only Farmer table updated.
       	 
				     
       	db.execSQL(createFarmTbl);
       }
       
       public void wipeOfflineFarmData_new(){
         	 db.execSQL("DROP TABLE IF EXISTS farms");
         	initOfflineFarmTables();
         }
       
       public String saveFarmDataOffline(HashMap<String, String>values){      	
       	initOfflineFarmTables();//only create the table if it does not exist
       	String uniqueID=UtilityFunctions.uniqueID("");

       	String theEntryDate=UtilityFunctions.currentStringTimeStamp(); 
       	//no timeStamp here yet!
       	
       	String sql="INSERT INTO `farms` (`FarmID`, `FarmerID`, `FarmName`, `ProvinceID`, `DistrictID`, `AdminPostID`, `LocalityID`, `ZoneID`, `GPSLong`, `GPSLat`, `UserID`) "+
       			"VALUES ("+
       			"		'"+uniqueID+"', '"+values.get("FarmerID")+"', '"+values.get("FarmName")+"', '"+values.get("ProvinceID")+"', '"+values.get("DistrictID")+"', '"+values.get("AdminPostID")+"', '"+values.get("LocalityID")+"', '"+values.get("ZoneID")+"', '"+values.get("GPSLong")+"', '"+values.get("GPSLat")+"', '"+values.get("UserID")+"'  "+
       			"		)";
       	
       	try{
       	db.execSQL(sql);
       	}catch (Exception e){  //SQLException
       		System.out.println("Local Save Error-saveFarmDataOffline"+e.toString());
       		return e.toString()+"SaveLocalfailedOK";
       	}
       	return uniqueID+":successOK";  //special case. The online mode returns both lastFarmID and success.

       }

       public String uploadSavedFarmData_getInsertValuesPart(){
       	//this function will read saved data and write it directly to the web server.
       	/*
       	 * it will sync with onilne db using insert or update, and make use of a unique
       	 * key on the millisecs+userid code. shd be reasonably unique.
       	 * will deal with the case where response from server is not received, and prevent duplicated data.
       	 *
       	 */
       	//Log.v("in upload sved pigeionPea harvest data","---");
       	String sql="SELECT `FarmID`, `FarmerID`, `FarmName`, `ProvinceID`, `DistrictID`, `AdminPostID`, `LocalityID`, `ZoneID`, `GPSLong`, `GPSLat`, `UserID` FROM `farms`";
       	
       	Cursor c=null;
          	try{
          		c=   db.rawQuery(sql, null);
          	}catch(Exception e){
          		
          	}
          	
	       	 if (c != null) {
		            c.moveToFirst();
		    
		        }
	      
	       	 //convert cursor into a long string to be uploaded/posted to php
		        // mCursor;
	       	StringBuffer ValuesPart=new StringBuffer();
	       	if (c != null) {  //placed in here in case no data exists locally.
	       	if (c.moveToFirst())
	        {
	            do {  
	            	ValuesPart.append("(");
	            	//prepare the values part of an insert or replace statement
	            	for (int i=0; i<c.getColumnCount(); i++){
	            		if (i==c.getColumnCount()-1){
	            			ValuesPart.append("'"+c.getString(i)+"')");  
	            		}else{
	            			ValuesPart.append("'"+c.getString(i)+"'");//
	            		}
	            		if (!(c.isLast() && i==c.getColumnCount()-1)) { //last column of last row has no  comma but bracket 
	            			
	            			ValuesPart.append(","); 
	            		
	            		}
	            	}
	                
	            } while (c.moveToNext());
	        }
	        c.close();
       	 
       	return ValuesPart.toString();
	       	}
	       	return null;
       }

       //======vv=============farm-yearly-data
       public void initOfflineFarmYearlyDataTables(){  //+"//  only Farmer table updated.
         	 
		     
          	db.execSQL(createFarmYearlyData);
          }
          
          public void wipeOfflineFarmYearlyData(){
            	 db.execSQL("DROP TABLE IF EXISTS farms_yearly_data");
            	initOfflineFarmYearlyDataTables();
            }
          
          public String saveFarmYearlyDataOffline(HashMap<String, String>values){      	
          	initOfflineFarmYearlyDataTables();//only create the table if it does not exist
          	String uniqueID=UtilityFunctions.uniqueID("");

          	String theEntryDate=UtilityFunctions.currentStringTimeStamp(); 

          	String sql="INSERT INTO `farms_yearly_data` (`FarmYearlyDataID`, `FarmID`, `SeasonID`, `CropTypeID`, `SeedVarietyID`, `LandOwnershipID`, `Mechanized`, `DemoPlot`, `TotalFarmSize`) "+
          				" VALUES ("+
          				"   	'"+uniqueID+"', '"+values.get("FarmID")+"', '"+values.get("SeasonID")+"', '"+values.get("CropTypeID")+"', '"+values.get("SeedVarietyID")+"', '"+values.get("LandOwnershipID")+"', '"+values.get("Mechanized")+"', '"+values.get("DemoPlot")+"', '"+values.get("TotalFarmSize")+"' "+
          			" )";
          	System.out.println("saveFarmYearlyData in dbadapter: sql="+sql);
          	try{
          	db.execSQL(sql);
          	}catch (Exception e){  //SQLException
          		System.out.println("Local Save Error-saveFarmDataOffline"+e.toString());
          		return e.toString()+"SaveLocalfailedOK";
          	}
          	return "successOK";
          	
          }

          public String uploadSavedFarmYearlyData_getInsertValuesPart(){
          	//this function will read saved data and write it directly to the web server.
          	/*
          	 * it will sync with onilne db using insert or update, and make use of a unique
          	 * key on the millisecs+userid code. shd be reasonably unique.
          	 * will deal with the case where response from server is not received, and prevent duplicated data.
          	 *
          	 */
          	//Log.v("in upload sved pigeionPea harvest data","---");
          	String sql="SELECT `FarmYearlyDataID`, `FarmID`, `SeasonID`, `CropTypeID`, `SeedVarietyID`, `LandOwnershipID`, `Mechanized`, `DemoPlot`, `TotalFarmSize` FROM `farms_yearly_data` ";
          	
          	Cursor c=null;
             	try{
             		c=   db.rawQuery(sql, null);
             	}catch(Exception e){
             		
             	}
             	
   	       	 if (c != null) {
   		            c.moveToFirst();
   		    
   		        }
   	      
   	       	 //convert cursor into a long string to be uploaded/posted to php
   		        // mCursor;
   	       	StringBuffer ValuesPart=new StringBuffer();
   	       	if (c != null) {  //placed in here in case no data exists locally.
   	       	if (c.moveToFirst())
   	        {
   	            do {  
   	            	ValuesPart.append("(");
   	            	//prepare the values part of an insert or replace statement
   	            	for (int i=0; i<c.getColumnCount(); i++){
   	            		if (i==c.getColumnCount()-1){
   	            			ValuesPart.append("'"+c.getString(i)+"')");  
   	            		}else{
   	            			ValuesPart.append("'"+c.getString(i)+"'");//
   	            		}
   	            		if (!(c.isLast() && i==c.getColumnCount()-1)) { //last column of last row has no  comma but bracket 
   	            			
   	            			ValuesPart.append(","); 
   	            		
   	            		}
   	            	}
   	                
   	            } while (c.moveToNext());
   	        }
   	        c.close();
          	 
          	return ValuesPart.toString();
   	       	}
   	       	return null;
          }

       
       //======^^============farm-yearly-data
       
        public void masterDBReset(){
        	//use this to clear all dbs. Not called by any button yet.
        	wipeOfflineFarmerData_new();
        	wipeOfflineFarmerFarmDatabase();
        	wipeOfflinePigeionPeaHarvestData();
        	wipeOfflineSurvey2013Data();
        	wipeOfflineVisitData();
        	prepareForSync();
        	
        	
        }
     
        //saving offline username & password of last login
        public void initOfflineUsersTables(){ 
          	 
		     
           	db.execSQL(createUserTbl);
           }
           
           public void wipeOfflineUsersData_new(){
             	 db.execSQL("DROP TABLE IF EXISTS users");
             	initOfflineFarmerTables();
             }
           
           public String saveUsersDataOffline(String userName, String password, String UserID){
        	   wipeOfflineUsersData_new(); //always delete old user table in case password has changed.
        	   initOfflineUsersTables();
             	
           	String sql="INSERT INTO `users` (`UserID`, `Username`, `Password`) "+
           			"VALUES ("+
           			"		'"+UserID+"', '"+userName+"', '"+password+"'   "+
           			"		)";
           	
           	try{
           	db.execSQL(sql);
           	}catch (Exception e){  //SQLException
           		System.out.println("Local Save Error-saveUserDataOffline"+e.toString());
           		return e.toString()+"SaveLocalfailedOK";
           	}
           	return "successOK";
           	
           }//fxn saveUserDataOffline
           
           public String verifyUsersDataOffline(String userName, String password){
        	   String sql="SELECT Username, UserID, Password FROM users where Username='"+userName+"' ";
           	StringBuffer sb= new StringBuffer(); 
           	Cursor c=null;
              	try{
              		c=   db.rawQuery(sql, null);
              	}catch(Exception e){
              		
              	}
   	       	 if (c != null) {
   	       		c.moveToFirst();
   	            if (c.moveToFirst())
   	            {
   	            //user exisits
   	            	
   	            
   	            	String strEquiv=UtilityFunctions.md5(password);
   	            	
   	            	
   	            	if ((strEquiv.equals(c.getString(c.getColumnIndex("Password"))))){
   	            		sb.append(c.getString(c.getColumnIndex("UserID"))); //userID:success:ok, read userID as data[0]
   	            		sb.append(":successOK");
   	            		c.close();
   	   			        return sb.toString();	
   	            	}
   	            	
   	            }
   	            return "shdNotOccurfailedOK";
   	         
   		        }
   	       	 else{
   	       		 //user does not exist
   	       		 c.close();
   	       		 return sb.append("failedOK").toString();
   	       	 }
   	       	 
//   	       	 c.close();
//   		        return sb.toString();
           }//fxn verifyUserDataOffline

        
}//main class
