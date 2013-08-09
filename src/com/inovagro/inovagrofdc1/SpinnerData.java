package com.inovagro.inovagrofdc1;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.util.Log;

public class SpinnerData implements  InovagroConstants{
	Activity  activity ;
	DBAdapter db;
	public SpinnerData(Activity a){
		activity=a;
	}
	public SpinnerData(Activity a, DBAdapter db){
		activity=a;
		this.db=db;
	}
	
	//refactoring potential:
	//make all the ff functions into one, but with two params: table name &parentID. All code is identical
	
	public  ArrayList<ComboRowData> provinceData(int parentID){	
		ArrayList<ComboRowData> al=new ArrayList<ComboRowData>();	
		DBAdapter db= new DBAdapter(activity);
		db.open();
		Cursor c = db.getDataSubset(PROVINCES, parentID);
        if (c.moveToFirst())
        {
            do {          
                al.add(new ComboRowData(c.getString(2),c.getInt(1), c.getInt(3) ) );//caption, id, parent
            } while (c.moveToNext());
        }
        c.close();
        db.close();
    	
        
	return al;
		}
	
	
	
	public ArrayList<ComboRowData> districtData(int parentID){
		ArrayList<ComboRowData> al=new ArrayList<ComboRowData>();	
		DBAdapter db= new DBAdapter(activity);
		db.open();
        Cursor c = db.getDataSubset(DISTRICTS, parentID);
        if (c.moveToFirst())
        {
            do {          
                al.add(new ComboRowData(c.getString(2),c.getInt(1), c.getInt(3) ) );//params: caption, id, parent
                //NB: DB order is _id, id, caption, parentID
            } while (c.moveToNext());
        }
        c.close();
        db.close();
	return al;
	
	}
	
	public ArrayList<ComboRowData> adminPostData(int parentID){
		ArrayList<ComboRowData> al=new ArrayList<ComboRowData>();	
		DBAdapter db= new DBAdapter(activity);
		db.open();
        Cursor c = db.getDataSubset(ADMIN_POSTS, parentID);
        if (c.moveToFirst())
        {
            do {          
                al.add(new ComboRowData(c.getString(2),c.getInt(1), c.getInt(3) ) );//caption, id, parent
            } while (c.moveToNext());
        }
        c.close();
        db.close();
	return al;
	
		}
	
	public ArrayList<ComboRowData> LocalityData(int parentID){
		ArrayList<ComboRowData> al=new ArrayList<ComboRowData>();	
		DBAdapter db= new DBAdapter(activity);
		db.open();
        Cursor c = db.getDataSubset(LOCALITY, parentID);
        if (c.moveToFirst())
        {
            do {          
                al.add(new ComboRowData(c.getString(2),c.getInt(1), c.getInt(3) ) );//caption, id, parent
            } while (c.moveToNext());
        }
        c.close();
        db.close();
	return al;
	
		}
	
	public ArrayList<ComboRowData> zoneData(int parentID){
		ArrayList<ComboRowData> al=new ArrayList<ComboRowData>();	
		DBAdapter db= new DBAdapter(activity);
		db.open();
        Cursor c = db.getDataSubset(ZONES, parentID);
        if (c.moveToFirst())
        {
            do {          
                al.add(new ComboRowData(c.getString(2),c.getInt(1), c.getInt(3) ) );//caption, id, parent
            } while (c.moveToNext());
        }
        c.close();
        db.close();
	return al;
	
		}
	
	
	public ArrayList<ComboRowData> farmerGroupData(int parentID){
		ArrayList<ComboRowData> al=new ArrayList<ComboRowData>();	
		DBAdapter db= new DBAdapter(activity);
		db.open();
        Cursor c = db.getDataSubset(FARMER_GROUPS, parentID);
        if (c.moveToFirst())
        {
            do {          
                al.add(new ComboRowData(c.getString(2),c.getInt(1), c.getInt(3) ) );//caption, id, parent
            } while (c.moveToNext());
        }
        c.close();
        db.close();
	return al;
	
		}
	
	public ArrayList<ComboRowData> IDTypesData(int parentID){
		ArrayList<ComboRowData> al=new ArrayList<ComboRowData>();	
		DBAdapter db= new DBAdapter(activity);
		db.open();
        Cursor c = db.getDataSubset(IDTYPES, parentID);
        if (c.moveToFirst())
        {
            do {          
                al.add(new ComboRowData(c.getString(2),c.getInt(1), c.getInt(3) ) );//caption, id, parent
            } while (c.moveToNext());
        }
        c.close();
        db.close();
	return al;
	
		}
	
	//-------additional data : pending --need to fetch from db, do the PHP part
	//spnCropType,spnSeedVariety,spnServiceProviderType, spnServiceProvider;
	public ArrayList<ComboRowData> CropTypeData(int parentID){
		ArrayList<ComboRowData> al=new ArrayList<ComboRowData>();	
		DBAdapter db= new DBAdapter(activity);
		db.open();
        Cursor c = db.getDataSubset(CROP_TYPES, parentID);
        if (c.moveToFirst())
        {
            do {          
                al.add(new ComboRowData(c.getString(2),c.getInt(1), c.getInt(3) ) );//caption, id, parent
            } while (c.moveToNext());
        }
        c.close();
        db.close();
	return al;
	
		}
	public ArrayList<ComboRowData> SeedVarietyData(int parentID){
		ArrayList<ComboRowData> al=new ArrayList<ComboRowData>();	
		DBAdapter db= new DBAdapter(activity);
		db.open();
        Cursor c = db.getDataSubset(SEED_VARIETY, parentID);
        if (c.moveToFirst())
        {
            do {          
                al.add(new ComboRowData(c.getString(2),c.getInt(1), c.getInt(3) ) );//caption, id, parent
            } while (c.moveToNext());
        }
        c.close();
        db.close();
	return al;
	
		}
	public ArrayList<ComboRowData> ServiceProviderTypeData(int parentID){
		ArrayList<ComboRowData> al=new ArrayList<ComboRowData>();	
		DBAdapter db= new DBAdapter(activity);
		db.open();
        Cursor c = db.getDataSubset(SERVICE_PROVIDER_TYPES, parentID);
        if (c.moveToFirst())
        {
            do {          
                al.add(new ComboRowData(c.getString(2),c.getInt(1), c.getInt(3) ) );//caption, id, parent
            } while (c.moveToNext());
        }
        c.close();
        db.close();
	return al;
	
		}
	public ArrayList<ComboRowData> ServiceProviderData(int parentID){
		ArrayList<ComboRowData> al=new ArrayList<ComboRowData>();	
		DBAdapter db= new DBAdapter(activity);
		db.open();
        Cursor c = db.getDataSubset(SERVICE_PROVIDERS, parentID);
        if (c.moveToFirst())
        {
            do {          
                al.add(new ComboRowData(c.getString(2),c.getInt(1), c.getInt(3) ) );//caption, id, parent
            } while (c.moveToNext());
        }
        c.close();
        db.close();
	return al;
	
		}
	
	public ArrayList<ComboRowData> LandOwnershipData(int parentID){
		ArrayList<ComboRowData> al=new ArrayList<ComboRowData>();	
		DBAdapter db= new DBAdapter(activity);
		db.open();
        Cursor c = db.getDataSubset(LAND_OWNERSHIP_TYPES, parentID);
        if (c.moveToFirst())
        {
            do {          
                al.add(new ComboRowData(c.getString(2),c.getInt(1), c.getInt(3) ) );//caption, id, parent
            } while (c.moveToNext());
        }
        c.close();
        db.close();
	return al;
	
		}

	public ArrayList<ComboRowData> SeasonData(int parentID){
		ArrayList<ComboRowData> al=new ArrayList<ComboRowData>();	
		DBAdapter db= new DBAdapter(activity);
		db.open();
        Cursor c = db.getDataSubset(SEASONS, parentID);
        if (c.moveToFirst())
        {
            do {          
                al.add(new ComboRowData(c.getString(2),c.getInt(1), c.getInt(3) ) );//caption, id, parent
            } while (c.moveToNext());
        }
        c.close();
        db.close();
	return al;
	
		}

}
