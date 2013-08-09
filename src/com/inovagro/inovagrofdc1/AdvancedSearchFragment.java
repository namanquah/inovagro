package com.inovagro.inovagrofdc1;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
//code here is mostly like in AddFarmerDetailFragment
public class AdvancedSearchFragment extends Fragment implements OnClickListener,AdapterView.OnItemSelectedListener, InovagroConstants{
	Button btnSearch, btnCancel;
	GeneralCallbackIntefaces callBack;
	
	Spinner spnProvince, spnDistrict, spnAdminPost, spnLocality, spnZone, spnFarmerGroup, spnIDTypes;
	ArrayList<ComboRowData> alProvince=null;
	
	ArrayList<ComboRowData> alDistrict=null;
	ArrayList<ComboRowData> alAdminPost=null;
	ArrayList<ComboRowData> alLocality=null;
	ArrayList<ComboRowData> alZone=null;
	ArrayList<ComboRowData> alFarmerGroup=null;
	
	
	ArrayAdapter<ComboRowData> aaProvince=null;
	ArrayAdapter<ComboRowData> aaDistrict=null;
	ArrayAdapter<ComboRowData> aaAdminPost=null;
	ArrayAdapter<ComboRowData> aaLocality=null;
	ArrayAdapter<ComboRowData> aaZone=null;
	ArrayAdapter<ComboRowData> aaFarmerGroup=null;
	
	int PurposeOfSearch;
	
	public AdvancedSearchFragment(int PurposeOfSearch){
		this.PurposeOfSearch=PurposeOfSearch;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
        View rootView = inflater.inflate(R.layout.frag_advanced_search_farmer,container, false);
        btnCancel=(Button)rootView.findViewById(R.id.btnCancel);
        btnSearch=(Button)rootView.findViewById(R.id.btnSubmit);
        btnCancel.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        
        
        spnProvince =(Spinner)rootView.findViewById(R.id.spnProvince);
        spnDistrict =(Spinner)rootView.findViewById(R.id.spnDistrict);
        spnAdminPost =(Spinner)rootView.findViewById(R.id.spnAdminPost);
        spnLocality =(Spinner)rootView.findViewById(R.id.spnLocality);
        spnZone =(Spinner)rootView.findViewById(R.id.spnZone);
        spnFarmerGroup =(Spinner)rootView.findViewById(R.id.spnFarmerGroup);
       
                
        spnProvince.setOnItemSelectedListener(this);
        spnDistrict.setOnItemSelectedListener(this);
        spnAdminPost.setOnItemSelectedListener(this);
        spnLocality.setOnItemSelectedListener(this);
        spnZone.setOnItemSelectedListener(this);
        spnFarmerGroup.setOnItemSelectedListener(this);
        
        
        SpinnerData s= new SpinnerData(getActivity());
        alProvince=s.provinceData(-1); //assumes use of -1 for parent
        aaProvince=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alProvince);	        
        aaProvince.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnProvince.setAdapter(aaProvince);
        
        return rootView;
    }
   
   public void onClick(View v){
	   if (v==btnSearch){
		   fetchDataViaPost();
	   }
	   if (v==btnCancel){
		   getFragmentManager().popBackStack();
	   }
	   
   }//onClick

   
   //for spinners  -->code is identical to that in AddFarmerDetailFragment.
   public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
		   
	   SpinnerData s= new SpinnerData(getActivity());
	   
	   if (parent==spnProvince){
		   
		   ComboRowData c=(ComboRowData)alProvince.get(position);
		   alDistrict=s.districtData(c.ID);  		        
	       aaDistrict=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alDistrict);
	       aaDistrict.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	       spnDistrict.setAdapter(aaDistrict);
		   
	       
	   }//spnProvince
	   if (parent==spnDistrict){
		   ComboRowData c=(ComboRowData)alDistrict.get(position);
		   alAdminPost=s.adminPostData(c.ID);  		        
	       aaAdminPost=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alAdminPost);
	       aaAdminPost.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	       spnAdminPost.setAdapter(aaAdminPost);			   
	   }
	   if (parent==spnAdminPost){
		   ComboRowData c=(ComboRowData)alAdminPost.get(position);
		   alLocality=s.LocalityData(c.ID);  		        
	       aaLocality=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alLocality);
	       aaLocality.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	       spnLocality.setAdapter(aaLocality);			   
	   }
	   if (parent==spnLocality){
		   ComboRowData c=(ComboRowData)alLocality.get(position);
		   alZone=s.zoneData(c.ID);  		        
	       aaZone=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alZone);
	       aaZone.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	       spnZone.setAdapter(aaZone);
	   }
	   if (parent==spnZone){
		   ComboRowData c=(ComboRowData)alZone.get(position);
		   alFarmerGroup=s.farmerGroupData(c.ID);  		        
	       aaFarmerGroup=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alFarmerGroup);
	       aaFarmerGroup.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	       spnFarmerGroup.setAdapter(aaFarmerGroup);
	   }
   }
   public void onNothingSelected(AdapterView<?> parent) { 
	  // selection.setText("");
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
	  
	   private void fetchDataViaPost(){
			
		   //alternative to ahve a single var to pass to post method
		   HashMap<String, String> values = new HashMap<String, String>();
		   //`ProvinceID` , `DistrictID` ,`AdminPostID` ,`LocalityID` ,`ZoneID`
		   
		   ComboRowData c;
		   int index;
		   index=spnProvince.getSelectedItemPosition();
		   if (index>-1){
			   c=(ComboRowData)alProvince.get(spnProvince.getSelectedItemPosition());		   
			   values.put("ProvinceID",String.valueOf(c.ID));
		   }
		   index=spnDistrict.getSelectedItemPosition();
		   if (index>-1){
			   c=(ComboRowData)alDistrict.get(spnDistrict.getSelectedItemPosition());		   
			   values.put("DistrictID",String.valueOf(c.ID));
		   }
		   index=spnAdminPost.getSelectedItemPosition();
		   if (index>-1){
			   c=(ComboRowData)alAdminPost.get(spnAdminPost.getSelectedItemPosition());		   
			   values.put("AdminPostID",String.valueOf(c.ID));
		   }
		   index=spnLocality.getSelectedItemPosition();
		   if (index>-1){
			   c=(ComboRowData)alLocality.get(spnLocality.getSelectedItemPosition());		   
			   values.put("LocalityID",String.valueOf(c.ID));
		   }
		   index=spnZone.getSelectedItemPosition();
		   if (index>-1){
			   c=(ComboRowData)alZone.get(spnZone.getSelectedItemPosition());		   
		       values.put("ZoneID",String.valueOf(c.ID));
		   }
		   index=spnFarmerGroup.getSelectedItemPosition();
		   if (index>-1){
			   c=(ComboRowData)alFarmerGroup.get(spnFarmerGroup.getSelectedItemPosition());		   
			   values.put("FarmerGroupID",String.valueOf(c.ID));
		   }
		 

		   //now use call back to send the post
		   //callBack.postData(actionSEARCH_ADVANCED, values);//also pass purposeOfSearch
		   callBack.doAdvancedSearch(values,actionSEARCH_ADVANCED, PurposeOfSearch);//better to use this?
	   }

}
