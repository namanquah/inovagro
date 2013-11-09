package com.inovagro.inovagrofdc1;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuList extends ListFragment implements InovagroConstants{
	
	Callbacks mCallbacks;
	//ArrayAdapter<String> adapter ;
	ArrayAdapter<String> adapter ;
	 ArrayList<String> values;
	TextView lblMenuTop, lblMenuBottom;
	
	String [] OnlineMenu;
	String [] OfflineMenu;
	String [] FarmerManagementMenu;
	String [] UploadManagementMenu;
	
	 
	
	

	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    
	    String[] rawValues=null; 
	    Resources res = getResources();
		OnlineMenu = res.getStringArray(R.array.arryOnlineMenu);
		//Resources res = getResources();
		OfflineMenu = res.getStringArray(R.array.arryOfflineMenu);
		
		FarmerManagementMenu= res.getStringArray(R.array.arryFarmerManagementMenu);
		UploadManagementMenu=res.getStringArray(R.array.arryUploadsMenu);
		
	     //this might get reloaded everytime the menu shows up. Consider moving into mainactivity.
	    //or do an immediate change menu depending on online state.
		
		
/*	 //no longer in use vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv============    
	    if (MainActivity.OfflineState==OnLineMode){
	    rawValues = new String[] { mnuAddFarmer, mnuAddFarm, mnuFarmVisit, mnuPlanVisits,
	    		mnuViewPlan, mnuUploadSavedData, mnuSynchronize, mnuGoOffline,mnuChangePassword, mnuExit};
	    }else{
	    rawValues = new String[] {  mnuAddFarmer, mnuAddFarm, mnuFarmVisit, 
	    		mnuViewPlan,  mnuGoOnline, mnuExit};
		    	
	    //new String[] { mnuAddFarmer, mnuAddFarm, mnuPlanVisits,
		//mnuViewPlan, mnuFarmVisit, mnuSynchronize, mnuGoOffline,mnuChangePassword, mnuExit};
    	
	    }
	    
	    //==========^^^^^^^^^^^^^^^^^^^^^^
*/	
	    
	    
		//online menu
	    if (MainActivity.OfflineState==OnLineMode){
	    	rawValues=OnlineMenu;
	    	lblMenuTop.setBackgroundColor(getResources().getColor(R.color.lightBlue));
			  lblMenuBottom.setBackgroundColor(getResources().getColor(R.color.lightBlue));
			  lblMenuTop.setText("Online");
			  
	    }
	    //else{
	    //offline menu
	    if (MainActivity.OfflineState==OffLineMode){	
	    	rawValues=OfflineMenu;
	    	lblMenuTop.setBackgroundColor(getResources().getColor(R.color.lightRed));
			  lblMenuBottom.setBackgroundColor(getResources().getColor(R.color.lightRed));
			  lblMenuTop.setText("Offline");
	    }
	    //mnuFarmerManagementMenu
	    if (MainActivity.MenuType==FarmerManagementMenuType){	
	    	rawValues=FarmerManagementMenu;
	    	
	    }
	  //UploadManagementMenu
	    if (MainActivity.MenuType==UploadSavedDataMenuType){	
	    	rawValues=UploadManagementMenu;
	    	
	    }
	     values =new ArrayList<String>();
	     Collections.addAll(values, rawValues);

	    
	    adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);//.simple_list_item_1
	     
	    	setListAdapter(adapter);
	    	
	    	
		}
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		
	        View rootView = inflater.inflate(R.layout.frag_main_menu,container, false);
	        lblMenuTop=(TextView)rootView.findViewById(R.id.lblMenuTop);
	        lblMenuBottom=(TextView)rootView.findViewById(R.id.lblMenuBottom);
	       // if (MainActivity.OfflineState==OnLineMode){
	       // 	changeMenu(mnuGoOnline, values.indexOf(mnuGoOnline)!=-1?values.indexOf(mnuGoOnline):values.indexOf(mnuGoOffline));
	       // }
	        return rootView;
	 }

  @Override
  public void onListItemClick(ListView l, View v, int position, long id) {
    // Do something with the data
	  super.onListItemClick(l, v, position, id);
      mCallbacks.onItemSelected(adapter.getItem(position), this, position);

  }
  public interface Callbacks {
	  
      public void onItemSelected(String id, Fragment frag, int position);
  }
  
  
  //it seems position is not used.
  public void  changeMenu( String var, int position){
	  //values.set(position, var);
	  //adapter.notifyDataSetChanged();
	  //
	  
	  if (MainActivity.OfflineState ==OffLineMode){
		  lblMenuTop.setBackgroundColor(getResources().getColor(R.color.lightRed));
		  lblMenuBottom.setBackgroundColor(getResources().getColor(R.color.lightRed));
		  lblMenuTop.setText("Offline");
		  values.clear();
		  Collections.addAll(values, OfflineMenu);
		  /*
		  if (values.indexOf(mnuUploadSavedData) !=-1){ //size()-3)==mnuUploadSavedData){
			  values.remove(values.indexOf(mnuUploadSavedData));
			  adapter.notifyDataSetChanged(); //added Sun29-06
		  }
		  
		  values.remove(values.indexOf(mnuPlanVisits));
		  */
		  
	  }
	  if (MainActivity.OfflineState ==OnLineMode){
		  lblMenuTop.setBackgroundColor(getResources().getColor(R.color.lightBlue));
		  lblMenuBottom.setBackgroundColor(getResources().getColor(R.color.lightBlue));
		  lblMenuTop.setText("Online");
		  //values.add(values.size()-2,mnuUploadSavedData);
		  //values.add(3,mnuPlanVisits);
		  //adapter.notifyDataSetChanged();  //added Sun29-06
		  Toast.makeText(getActivity(), "mainmenuList: Online Mode", Toast.LENGTH_LONG).show();
		  values.clear();
		  Collections.addAll(values, OnlineMenu);
	  }
	  
	  if (MainActivity.MenuType ==FarmerManagementMenuType){
		  //lblMenuTop.setText("Offline");
		  values.clear();
		  Collections.addAll(values, FarmerManagementMenu);
		  /*
		  if (values.indexOf(mnuUploadSavedData) !=-1){ //size()-3)==mnuUploadSavedData){
			  values.remove(values.indexOf(mnuUploadSavedData));
			  adapter.notifyDataSetChanged(); //added Sun29-06
		  }
		  
		  values.remove(values.indexOf(mnuPlanVisits));
		  */
		  
	  }

	  if (MainActivity.MenuType ==UploadSavedDataMenuType){
		  values.clear();
		  Collections.addAll(values, UploadManagementMenu);
		  
		  
	  }
	  adapter.notifyDataSetChanged();
  }
  
  @Override
  public void onAttach(Activity activity) {
      super.onAttach(activity);
      if (!(activity instanceof Callbacks)) {
          throw new IllegalStateException(
                  "Activity must implement fragment's callbacks.");
      }
      mCallbacks = (Callbacks) activity;
  }
  @Override
  public void onDetach() {
      super.onDetach();
      mCallbacks = null;
  }
}
