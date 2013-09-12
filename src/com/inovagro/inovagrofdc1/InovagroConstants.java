package com.inovagro.inovagrofdc1;

public interface InovagroConstants {
	//public static String BaseURL="http://10.0.2.2/mywebs/inovagro_android/utilities4android.php";
	public static String BaseURL="http://www.ghmeforum.org/inovagro/inovagro_android/utilities4android.php";
    public static final String DATABASE_TABLE1 = "provinces";
    public static final String DATABASE_TABLE2 = "districts";
    public static final String DATABASE_TABLE3 = "admin_posts";
    public static final String DATABASE_TABLE4 = "locality";
    public static final String DATABASE_TABLE5 = "zones";
    public static final String DATABASE_TABLE6 = "farmer_groups";
    public static final String DATABASE_TABLE7 = "id_types";
    public static final String DATABASE_TABLE8 = "crop_types";
    public static final String DATABASE_TABLE9 = "seed_variety";
    public static final String DATABASE_TABLE10 = "service_provider_types";
    public static final String DATABASE_TABLE11 = "service_providers";
    public static final String DATABASE_TABLE12 = "seasons";
    public static final String DATABASE_TABLE13 = "land_ownership_types";
    

    public static final int PROVINCES = 1;//"provinces";
    public static final int DISTRICTS = 2;//"districts";
    public static final int ADMIN_POSTS = 3;//"admin_posts";
    public static final int LOCALITY = 4;//"locality";
    public static final int ZONES = 5;//"zones";
    public static final int FARMER_GROUPS = 6;//"farmer_groups";
    public static final int IDTYPES = 7;//"id_types;
    public static final int CROP_TYPES = 8;//"crop_types;
    public static final int SEED_VARIETY = 9;//"seed_variety;
    public static final int SERVICE_PROVIDER_TYPES = 10;//"service_provider_types;
    public static final int SERVICE_PROVIDERS = 11;//"service_providers;
    public static final int SEASONS=12;//"seasons"  //SeasonID,Season,CropTypeID
    public static final int LAND_OWNERSHIP_TYPES=13;// LandOwnershipTypeID,LandOwnershipType -1
    
    
    //will use these to replace Database_table1 etc. this is clearer
    public static final String tblPROVINCES = "provinces";
    public static final String tblDISTRICTS = "districts";
    public static final String tblADMIN_POSTS = "admin_posts";
    public static final String tblLOCALITY = "locality";
    public static final String tblZONES = "zones";
    public static final String tblFARMER_GROUPS = "farmer_groups";
    public static final String tblIDTYPES = "id_types";
    public static final String tblCROP_TYPES = "crop_types";
    public static final String tblSEED_VARIETY = "seed_variety";
    public static final String tblSERVICE_PROVIDER_TYPES = "service_provider_types";
    public static final String tblSERVICE_PROVIDERS = "service_providers";
    public static final String tblSEASONS = "seasons";
    public static final String tblLAND_OWNERSHIP_TYPES = "land_ownership_types";
    
    
    //search related constant
    public static final int BASIC_SEARCH= 1;
    public static final int ADVANCED_SEARCH= 2;
    public static final int SEARCH_FARMERNAME= 1; //may not be needed anymore. do verify 
    public static final int SEARCH_FARMERREFERENCENO= 2; //may not be needed. do verify
    
    
    //type of list to show
    public static final int actionSYNC=1;
    public static final int actionSEARCH_FARMERNAME=2;
    public static final int actionSEARCH_FARMERREFERENCENO=3;
    public static final int actionSEARCH_CURRENT_FARMS=4;
    public static final int actionSHOW_VISIT_TYPES=5;
    public static final int actionSHOW_VISIT_FORM=6;
    public static final int actionSEARCH_ADVANCED=112;//109
    public static final int actionSURVEY2013=213;
    
    //post actions (will be used alongside visit types in POST data, so must not be in same range)
    public static final int actionPOST_ADD_FARMER=107;
    public static final int actionPOST_ADD_FARM=108;
    public static final int actionCHANGE_PASSWORD=109;
    public static final int actionFETCH_FARMER_FARM_DATA4_OFFLINE=110;
    public static final int actionUPLOAD_SAVED_VISIT_DATA=111;
    public static final int actionPOST_ADD_FARMS_YEARLY_DATA=112;
    public static final int actionPOST_AUGUST2013_SURVEY=113;
    public static final int actionUPLOAD_SAVED_SURVEY2013_DATA=114;
    
    
   // public static final int actionSAVE_ADD_FARMER=9;
    
    
    //constants for visit types --dont change these. They are related to array entries. Preserver the order too.
    public static  final int visitAD_HOC=1;
    public static  final int visitMANUAL_LAND_PREP=2;
    public static  final int visitRIP=3;
    public static  final int visitPLOUGH=4;
    public static  final int visitDISKING=5;
    public static  final int visitSEED_DISTRIBUTION=6;
    public static  final int visitPLANTING=7;
    public static  final int visitGERMINATION=8;
    public static  final int visitWEED1=9;
    public static  final int visitWEED2=10;
    public static  final int visitWEED3=15;
    public static  final int visitYIELD_FORECAST=11;
    public static  final int visitHARVESTYIELD=12;
    public static  final int visitTHRESHINGINFO=13;
    public static  final int visitDEMO_PLOT_VISIT=14;
    

    //used to call the basic_advanced search form to look for a farmer, but to carry out different operations
    public static  final int searchBA_FARM_VISIT=1001;
    public static  final int searchBA_ADD_FARM=1002;
    public static  final int searchBA_PLAN_VISIT=1003;
    public static  final int view_PLANNED_VISITS=1004;
    
    //used for online-offlien status
    public static final int OffLineMode=2000;
    public static final int OnLineMode=2001;
    
    
    //menu items, perhaps add mnu to the constatns
    
    
//these here are likely not used anylonger!
    /*
	public static final String mnuAddFarmer="Add Farmer";
    public static final String mnuFarmVisit="Farm Visit";
    public static final String mnuSynchronize="Update Configuration Data";
    public static final String mnuChangePassword="Change Password";
    public static final String mnuExit="Exit";
    public static final String mnuAddFarm="Add Farm";
    
    public static final String mnuPlanVisits="Plan Visits";
    public static final String mnuViewPlan="Manage Stored Farmer Data";
    public static final String mnuGoOnline ="Go Online";
    public static final String mnuGoOffline="Go Offline";
    public static final String mnuUploadSavedData="Upload Saved Data";
    
    */
    
    public static final int surveyPartI=1;
    public static final int surveyPartII=2;
    public static final int surveyPartIIIa=31;
    public static final int surveyPartIIIb=32;
    public static final int surveyPartIV=4;
    public static final int surveyConfirmData=5;
    
}
