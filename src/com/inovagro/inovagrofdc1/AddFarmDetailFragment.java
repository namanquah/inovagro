package com.inovagro.inovagrofdc1;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddFarmDetailFragment extends Fragment  implements OnClickListener, AdapterView.OnItemSelectedListener, InovagroConstants{
	/*
	 * 
	 * To do:
	 * Convert this to fit the AddFarmDetail xml
	 * 
	 */
	
	GeneralCallbackIntefaces callBack;
	
	Button btnCancel;
	Button btnSubmit;
	Button btnCaptureGPS;
	Spinner spnProvince, spnDistrict, spnAdminPost, spnLocality, spnZone, spnFarmerGroup;
	TextView txtFarmerName, txtFarmerID;
	EditText edtFarmName, edtGPSLong, edtGPSLat;
	
	ArrayList<ComboRowData> alProvince=null;
	ArrayList<ComboRowData> alDistrict=null;
	ArrayList<ComboRowData> alAdminPost=null;
	ArrayList<ComboRowData> alLocality=null;
	ArrayList<ComboRowData> alZone=null;
	ArrayList<ComboRowData> alFarmerGroup=null;
	ArrayList<ComboRowData> alIDTypes=null;
	
	ArrayAdapter<ComboRowData> aaProvince=null;
	ArrayAdapter<ComboRowData> aaDistrict=null;
	ArrayAdapter<ComboRowData> aaAdminPost=null;
	ArrayAdapter<ComboRowData> aaLocality=null;
	ArrayAdapter<ComboRowData> aaZone=null;
	ArrayAdapter<ComboRowData> aaFarmerGroup=null;
	ArrayAdapter<ComboRowData> aaIDTypes=null;

	//local class variables passed in
	String FarmerID;
	String FarmerName;
	
	public AddFarmDetailFragment(String FarmerID, String FarmerName){
		this.FarmerID=FarmerID;
		this.FarmerName=FarmerName;
	}
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		
	        View rootView = inflater.inflate(R.layout.frag_add_farm,container, false);
	        btnCancel=(Button)rootView.findViewById(R.id.btnCancel);
	        btnCancel.setOnClickListener(this);
	        btnSubmit=(Button)rootView.findViewById(R.id.btnSubmit);
	        btnSubmit.setOnClickListener(this);
	        btnCaptureGPS=(Button)rootView.findViewById(R.id.btnCaptureGPS);
	        btnCaptureGPS.setOnClickListener(this);
	    
	        edtFarmName=(EditText)rootView.findViewById(R.id.edtFarmName);
	        edtGPSLong=(EditText)rootView.findViewById(R.id.edtGPSLong);
	        edtGPSLat=(EditText)rootView.findViewById(R.id.edtGPSLat);
			//next two are bad variable naming. Short cut taken: Showing Farmer name as ID instead.
	        //txtFarmerName=(TextView)rootView.findViewById(R.id.txtFarmerName); //for the farmer's name
			//txtFarmerID=(TextView)rootView.findViewById(R.id.txtFarmerID); //for the farmer's ID
	        txtFarmerID=(TextView)rootView.findViewById(R.id.txtFarmerID);
			
			//txtFarmerName.setText(FarmerName);
			//txtFarmerID.setText(String.valueOf(FarmerID));
	        txtFarmerID.setText(String.valueOf(FarmerName));//see comment above
			
			
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
	        
	        alIDTypes=s.IDTypesData(-1); //assumes use of -1 for parent
	        aaIDTypes=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alIDTypes);	        
	        aaIDTypes.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	       
	        return rootView;
	    }
	   
	   public void onClick(View v){
		   if (v==btnCancel){
			   getFragmentManager().popBackStack();
		   }
		   if (v==btnSubmit){
			   //validation:
			  if (! validateData()) //if validation fails, exit
				  return;
			   fetchDataAndPost();   
		   }
		   if (v==btnCaptureGPS){
			   showCurrentLocation();
		   }
		  
	   }
	   
	   //for spinners
	   public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
			   //selection.setText(items[position]);
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
			   
	   
	   private void fetchDataAndPost(){
	
		   //alternative to ahve a single var to pass to post method
		   HashMap<String, String> values = new HashMap<String, String>();
		   //FarmID` ,`FarmerID` ,`FarmName` ,`ProvinceID` , `DistrictID` ,`AdminPostID` ,`LocalityID` ,`ZoneID` ,  `GPSLong` ,`GPSLat` ,`UserID`
		   values.put("FarmID",UtilityFunctions.uniqueID(""));
		   values.put("FarmerID",String.valueOf(FarmerID));
		   values.put("FarmName",edtFarmName.getText().toString());
		   values.put("GPSLong",edtGPSLong.getText().toString());
		   values.put("GPSLat",edtGPSLat.getText().toString());
		   values.put("UserID",Integer.toString(Login.UserID));
		   
		   ComboRowData c;
		   c=(ComboRowData)alProvince.get(spnProvince.getSelectedItemPosition());		   
		   values.put("ProvinceID",String.valueOf(c.ID));
		   c=(ComboRowData)alDistrict.get(spnDistrict.getSelectedItemPosition());		   
		   values.put("DistrictID",String.valueOf(c.ID));
		   c=(ComboRowData)alAdminPost.get(spnAdminPost.getSelectedItemPosition());		   
		   values.put("AdminPostID",String.valueOf(c.ID));
		   c=(ComboRowData)alLocality.get(spnLocality.getSelectedItemPosition());		   
		   values.put("LocalityID",String.valueOf(c.ID));
		   c=(ComboRowData)alZone.get(spnZone.getSelectedItemPosition());		   
		   values.put("ZoneID",String.valueOf(c.ID));
		   c=(ComboRowData)alFarmerGroup.get(spnFarmerGroup.getSelectedItemPosition());		   
		   values.put("FarmerGroupID",String.valueOf(c.ID));
		 

		   //now use call back to send the post
		   callBack.postData(actionPOST_ADD_FARM, values);
	   }
	   
	   
	   boolean validateData(){
		   //edtSurname, edtForenames, edtFarmerReferenceNo
		   if (edtFarmName.getText().toString().trim().equals("")){
			  Toast.makeText(getActivity(), "Farm Name is blank", Toast.LENGTH_LONG ).show();
			  edtFarmName.requestFocus();
			   return false;
		   }
		 //validate spinners:
		   if ((spnDistrict.getSelectedItemPosition()==-1)||
				   (spnAdminPost.getSelectedItemPosition()==-1)||
				   (spnLocality.getSelectedItemPosition()==-1)||
				   (spnZone.getSelectedItemPosition()==-1)||
				   (spnFarmerGroup.getSelectedItemPosition()==-1)
				   )
				   {
			   Toast.makeText(getActivity(), "One drop down is blank, Data not saved", Toast.LENGTH_LONG).show();
			   return false;
		   }
		   return true;
	   }

	   //deal with call backs
	   @Override
		  public void onAttach(Activity activity) {
		      super.onAttach(activity);
		      if (!(activity instanceof GeneralCallbackIntefaces)) {
		          throw new IllegalStateException(
		                  "Activity must implement GeneralCallbackIntefaces's callbacks.");
		      }
		      callBack = (GeneralCallbackIntefaces) activity;
		  }
		  @Override
		  public void onDetach() {
		      super.onDetach();
		      callBack = null;
		  }


		    protected void showCurrentLocation() {
		       String[] longLat=callBack.getCurrentLocation();
		       if (longLat!=null){
		       edtGPSLong.setText(longLat[0]);
		       edtGPSLat.setText(longLat[1]);
		       }
		       else {
		    	   Toast.makeText(getActivity(), "FarmDetail: No GPS data", Toast.LENGTH_LONG).show();
		       
		       }
		    }

}
