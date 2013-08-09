package com.inovagro.inovagrofdc1;

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
import android.widget.Toast;

public class DBAdapter implements InovagroConstants {
	
	
	   public static final String KEY_ROWID = "_id";
	   public static final String KEY_THEID = "theid";
	    public static final String KEY_THENAME = "thename";
	    public static final String KEY_PARENTID = "parentid";
	    private static final String TAG = "DBAdapter";
	    
	    private static final String DATABASE_NAME = "MyDB";    
	    
	    private static final int DATABASE_VERSION = 2;

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
        	 String createFarmerTbl="CREATE TABLE IF NOT EXISTS farmers ("+
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
				 "  UserId integer NOT NULL"+
				 		" );";
				 
        	 String createFarmTbl="CREATE TABLE IF NOT EXISTS farms ("+
        		//"_id integer primary key autoincrement,"+
				 "  FarmID integer NOT NULL primary key,"+
				 "  FarmerID integer NOT NULL,"+
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
        	
        	 String createFarmYearlyData="CREATE TABLE IF NOT EXISTS farms_yearly_data ("+
					 "  FarmYearlyDataID integer NOT NULL primary key,"+
					 "  FarmID integer NOT NULL,"+
					 "  SeasonID integer NOT NULL,"+
					 "  CropTypeID integer NOT NULL,"+
					 "  SeedVarietyID integer NOT NULL,"+
					 "  LandOwnershipID integer NOT NULL,"+
					 "  Mechanized integer NOT NULL ,"+
					 "  DemoPlot integer NOT NULL,"+
					 "  TotalFarmSize real NOT NULL)";
        	 
        	 String createFarmerSeasons="CREATE TABLE IF NOT EXISTS farmer_seasons ("+
					 "  FarmerYearID integer NOT NULL primary key,"+
					 "  FarmerID integer NOT NULL,"+
					 "  SeasonID integer NOT NULL)";	 
        	 
        	 db.execSQL(createFarmerTbl);
        	 db.execSQL(createFarmTbl);
        	 db.execSQL(createFarmYearlyData);
        	 db.execSQL(createFarmerSeasons);
        	 
        }
        
        public void saveFarmerFarmDataOffline(String result){
        	
        	initOfflineFarmerFarmTables(); //only creates if not exist
        	 //------------
        	 UtilityFunctions fxn= new UtilityFunctions();
				result=result.replace("successOK","");
				String tmp[]=fxn.msplit(result,"</br>");	
				//System.out.println(result);
				//write each data as a query!
				//==================sqlite does not insert multiple rows!========

				String sql="Replace into farmers  "+fixInsertQuery(tmp[0]);
				db.execSQL(sql);
				System.out.println(sql);
				sql="Replace into farms  "+fixInsertQuery(tmp[1]);
				db.execSQL(sql);
				sql="Replace into farmer_seasons  "+fixInsertQuery(tmp[2]);
				db.execSQL(sql);				
				sql="Replace into farms_yearly_data  "+fixInsertQuery(tmp[3]);
				db.execSQL(sql);
				
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
				sql="Delete from farmer_seasons  where FarmerID in ("+IDList+")";
				db.execSQL(sql);				
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

        
        public void wipeOfflineFarmerDatabase(){
        	 db.execSQL("DROP TABLE IF EXISTS farmers");
        	 db.execSQL("DROP TABLE IF EXISTS farms");
        	 db.execSQL("DROP TABLE IF EXISTS farms_yearly_data");
        	 db.execSQL("DROP TABLE IF EXISTS farmer_seasons");
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
		        return sb.toString();   	
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
  
	}//main class
