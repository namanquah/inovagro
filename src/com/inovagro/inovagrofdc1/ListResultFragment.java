package com.inovagro.inovagrofdc1;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

//version 2 work:
/*
 * btnFarmerInfo.setVisibility(View.GONE);  ***********enable this in version2 
 * btnResetLocalDB.setVisibility(View.GONE); //temporary measure  
 * 
 * *********/

public class ListResultFragment extends ListFragment implements InovagroConstants, OnClickListener {//, AdapterView.OnItemSelectedListener{
	
	GeneralCallbackIntefaces callBack=null;
	Button btnCancel,btnAddFarm, btnSave,btnClearAll, btnCheckAll, btnFarmerInfo;
	Button btnDelete, btnResetLocalDB;
	//btnPigeonPeaHarvestSurvey
	
	ArrayAdapter<String> adapter ;
	String[] values;
	//int[] IDArry;  //id->str
	String[] IDArry;
	int actionType=-1;
	String currentFarmYearlyDataID="-1";
	int PurposeOfSearch;
	
	ListView lv;
	public ListResultFragment(String a[], String numericIDs[], int actionType, int PurposeOfSearch){
	//public ListResultFragment(String a[], int numericIDs[], int actionType, int PurposeOfSearch){
		super();
		values=a;
		IDArry=numericIDs;
		this.actionType=actionType;
		this.PurposeOfSearch=PurposeOfSearch;
		System.out.println("in constructor of ListResultFrag -purposeOfSearch="+this.PurposeOfSearch);
	}

	//used only by actionSHOW_VISIT_TYPES, called from MainActivity
	public ListResultFragment(String a[], int actionType, String FarmYearlyDataID){  
		super();
		values=a;
		IDArry=null;
		this.actionType=actionType;
		currentFarmYearlyDataID=FarmYearlyDataID;
	}
	
	
	

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
        View rootView = inflater.inflate(R.layout.frag_results_list,container, false);   
        btnCancel=(Button)rootView.findViewById(R.id.btnCancel);
        btnAddFarm=(Button)rootView.findViewById(R.id.btnAddFarm);
        btnCheckAll=(Button)rootView.findViewById(R.id.btnCheckAll);
        btnClearAll=(Button)rootView.findViewById(R.id.btnClearAll);
        btnSave=(Button)rootView.findViewById(R.id.btnSave);
        btnFarmerInfo=(Button)rootView.findViewById(R.id.btnFarmerInfo);
        btnDelete=(Button)rootView.findViewById(R.id.btnDelete);
        btnResetLocalDB=(Button)rootView.findViewById(R.id.btnResetLocalDB);
     //   btnPigeonPeaHarvestSurvey=(Button)rootView.findViewById(R.id.btnPigeonPeaHarvestSurvey);
        
        btnCancel.setOnClickListener(this);
        btnAddFarm.setOnClickListener(this);
        btnClearAll.setOnClickListener(this);
        btnCheckAll.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnFarmerInfo.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnResetLocalDB.setOnClickListener(this);
        //btnPigeonPeaHarvestSurvey.setOnClickListener(this);
        
        btnFarmerInfo.setVisibility(View.GONE);  //***********enable this in version2 *********?
        btnAddFarm.setVisibility(View.GONE); //show this only after selecting a particular farmer.
        //btnPigeonPeaHarvestSurvey.setVisibility(View.GONE);
        
        
        Log.v("in ListResFrag","onCreateView actionType="+actionType+"purposeOfSearch="+PurposeOfSearch);
        //otherwise use a context menu on the farmer's name.
        //if (actionType==searchBA_ADD_FARM){
        if (MainActivity.PurposeOfSearch==searchBA_ADD_FARM && actionType==actionSEARCH_CURRENT_FARMS){	
        	btnAddFarm.setVisibility(View.VISIBLE);        	
        }
        else{
        	btnAddFarm.setVisibility(View.GONE);        
	    	
        }
        

        //planVisits
        if (MainActivity.PurposeOfSearch==searchBA_PLAN_VISIT){
        	btnCheckAll.setVisibility(View.VISIBLE);
        	btnClearAll.setVisibility(View.VISIBLE);
        	btnSave.setVisibility(View.VISIBLE);
        }
        else{
        	btnCheckAll.setVisibility(View.GONE);
        	btnClearAll.setVisibility(View.GONE);
        	btnSave.setVisibility(View.GONE);
        }

        //view plans
        if (MainActivity.PurposeOfSearch==view_PLANNED_VISITS){
        	btnCheckAll.setVisibility(View.VISIBLE);
        	btnClearAll.setVisibility(View.VISIBLE);
        	btnDelete.setVisibility(View.VISIBLE);
        	btnResetLocalDB.setVisibility(View.VISIBLE);
        	//btnResetLocalDB.setVisibility(View.GONE); //temporary measure
        }
        else{
        	btnCheckAll.setVisibility(View.GONE);
        	btnClearAll.setVisibility(View.GONE);
        	btnDelete.setVisibility(View.GONE);
        	btnResetLocalDB.setVisibility(View.GONE);
        }

        //pegion pea harvest survey
        if (MainActivity.PurposeOfSearch==searchPIGEONPEA_HARVEST_SURVEY){
        	btnCheckAll.setVisibility(View.GONE);
        	btnClearAll.setVisibility(View.GONE);
        	btnDelete.setVisibility(View.GONE);
        	btnResetLocalDB.setVisibility(View.GONE);
        	btnCheckAll.setVisibility(View.GONE);
        	btnClearAll.setVisibility(View.GONE);
        //	btnPigeonPeaHarvestSurvey.setVisibility(View.VISIBLE);
        }


       
        return rootView;
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

	    lv= getListView();
	    //test pending
	    if ((MainActivity.PurposeOfSearch==searchBA_PLAN_VISIT && actionType==actionSEARCH_FARMERNAME) 
	    			|| (MainActivity.PurposeOfSearch ==view_PLANNED_VISITS)){
	    	adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_multiple_choice, values);
	    	lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }
	    else
	    {
	    	if (values==null) values=new String [0];
	    	adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, values);
	    	
	    }
	    	setListAdapter(adapter);
	    	//ListView l= getListView();
	    	lv.setTextFilterEnabled(true);	
	    	
		}

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    // Do something with the data
	  super.onListItemClick(l, v, position, id);
      //mCallbacks.onItemSelected(adapter.getItem(position));
	  if ((MainActivity.PurposeOfSearch==searchBA_PLAN_VISIT)|| (MainActivity.PurposeOfSearch ==view_PLANNED_VISITS)){  
		  //l.setItemChecked(position, l.isItemChecked(position));
		 // l.setItemChecked(position, true);
		  return;
	  }
  
	//  if (actionType==actionSEARCH_CURRENT_FARMS){ //works!
	  //if (PurposeOfSearch==searchBA_FARM_VISIT){
	  
			if ((actionType==actionSEARCH_FARMERNAME) || (actionType==actionSEARCH_FARMERREFERENCENO) || (actionType==actionSEARCH_ADVANCED)){  
								//add a new callback to list farms.
				
				  //int FarmerID= IDArry[position];
				  String FarmerID= IDArry[position];
				  //using a shortcircuit:_____vvv___
				  MainActivity.CurrentFarmerID=FarmerID;
				  MainActivity.CurrentFarmerName=values[position];
				  
				  //Toast.makeText(getActivity(), " "+position+" farmer id  "+FarmerID +values[position], Toast.LENGTH_LONG).show();
				  //***************************NOTE  NOTE  NOTE  NOTE  NOTE  NOTE  ****************************/
				  //in future, may need to pass the purpose of search to the called fxns! Currently using global in MainActivity
				  if (MainActivity.PurposeOfSearch==searchPIGEONPEA_HARVEST_SURVEY){
					  callBack.showPigeonPeaHarvestForm(FarmerID, values[position]);
					  return;
				  }
				  if (MainActivity.PurposeOfSearch==searchBA_FARMER_DETAIL){
					  callBack.showFarmerDetail(FarmerID);
					  return;
				  }
				  if (MainActivity.PurposeOfSearch==searchPOVERTY_SCORE_CARD){
					  callBack.showPovertyScoreCard(FarmerID);
					  return;
				  }
				  //wont get here if its pigeionPea
				  
				  callBack.fetchListOfFarms(actionSEARCH_CURRENT_FARMS, FarmerID);
				  return;
				  
			  }
	  //}
	  //do another purposeOfSearch="add farm"
		//do not show visits if purpose is add form
		if (MainActivity.PurposeOfSearch==searchBA_FARM_VISIT){  //using the global one. Have not been consistent. Use#1
		  if (actionType==actionSEARCH_CURRENT_FARMS){
			  String FarmYearlyDataID= IDArry[position]; //fetch a particular farm, keep this ID. start Visit List, and show appropriate form
			  callBack.showVisitTypes(actionSHOW_VISIT_TYPES, FarmYearlyDataID);
			  return;
		  }
		}
	  if (actionType==actionSHOW_VISIT_TYPES){ 
		//add a new callback to list farms.  		 
		  int visitType=position+1; // look at the positions in the array resource in the strings file. 
		  							//DO NOT CHANGE. no need to declare constants for this purpose 
		  							//constants also exist in InovagroConstants file that are corresponding
		  callBack.showVisitForm(actionSHOW_VISIT_FORM, currentFarmYearlyDataID, visitType );
		  return;
	  }

  }//onListItemClick
  
  //deal with button clicks
  public void onClick(View v){
	   if (v==btnCancel){
		   getFragmentManager().popBackStack();
		   return;
	   }
	   if (v==btnAddFarm){
		   
		   callBack.showAddFarmForm(MainActivity.CurrentFarmerID, MainActivity.CurrentFarmerName);
		   return;
	   }
	   if (v==btnClearAll){
            for (int i = 0; i < lv.getCount(); i++)
               lv.setItemChecked(i, false);
   
	   }
	   if (v==btnCheckAll){
		   for (int i = 0; i < lv.getCount(); i++)
               lv.setItemChecked(i, true);
	   }
	   if(v==btnSave){
		  //int vars[]= getCheckedItemPosition();
		   String ids="";
		   ListView lv=getListView();
		   SparseBooleanArray chkedItems=lv.getCheckedItemPositions();
		   if (chkedItems!=null){
			   for(int i=0; i< chkedItems.size(); i++){
			   if(chkedItems.valueAt(i)){
				   //String item= lv.getAdapter().getItem(chkedItems.keyAt(i)).toString();
				   ids+="'"+String.valueOf(IDArry[chkedItems.keyAt(i)])+"',";
				   //callBack.fetchDataForSelectedFarmers(ids);
				   
				   //save this array of stringized ids
				   //verify if this ids are correct
				   //assume that 
			   }
		   }
			   HashMap<String, String>values = new HashMap<String, String>();
			   values.put("IDs", ids);
			   System.out.println("the IDS selected are "+ids);
			   
			   callBack.postData(actionFETCH_FARMER_FARM_DATA4_OFFLINE, values);
			   
		   
		   }  
	   }//if delete
	   if(v==btnDelete){
			  String ids="";
			   ListView lv=getListView();
			   SparseBooleanArray chkedItems=lv.getCheckedItemPositions();
			   if (chkedItems!=null){
				   if (chkedItems.size()==0){
					   Toast.makeText(getActivity(), "Nothing Selected", Toast.LENGTH_SHORT).show();
					   return;
				   }
				   for(int i=0; i< chkedItems.size(); i++){
				   if(chkedItems.valueAt(i)){
					   //ids+=String.valueOf(IDArry[chkedItems.keyAt(i)])+",";
					   ids+="'"+String.valueOf(IDArry[chkedItems.keyAt(i)])+"',";
					   //callBack.fetchDataForSelectedFarmers(ids);
					    
				   }
			   }
			   }
			   //remove last ,
			   Log.v("inListresult--ids value", ids);
			   if (ids.length()!=0){
				   ids=ids.substring(0, ids.length()-1);
			   }
			   Log.v("inListresult --ids values after", ids);
			   callBack.deleteLocalFarmerData(ids);
		   }//delete
	   
	   if (v==btnResetLocalDB){
		   //TODO: provide a dialog box to confirm the deletion action.
		   callBack.resetLocalFarmersDB();
		   return;
	   }
/*	   if (v==btnPigeonPeaHarvestSurvey){
		   
		   callBack.showPigeonPeaHarvestForm(MainActivity.CurrentFarmerID, MainActivity.CurrentFarmerName);
		   return;
	   } */
  }
	
  
  
  @Override
  public void onAttach(Activity activity) {
      super.onAttach(activity);
      if (!(activity instanceof GeneralCallbackIntefaces)) {
          throw new IllegalStateException(
                  "Activity must implement fragment's callbacks.");
      }
      callBack = (GeneralCallbackIntefaces) activity;
  }
  @Override
  public void onDetach() {
      super.onDetach();
      callBack = null;
  }
}
