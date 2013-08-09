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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddFarmSeasonDetailFragment extends Fragment implements OnClickListener, AdapterView.OnItemSelectedListener, InovagroConstants{

	GeneralCallbackIntefaces callBack;
	Button btnCancel, btnSubmit;
	Spinner spnCropType, spnSeason, spnSeedVariety, spnLandOwnership;
	//TextView txtFarmerID;
	EditText edtFarmSize;
	RadioButton rdManual, rdMechanized, rdRegularFarm, rdDemoPlot;
	
	ArrayList<ComboRowData> alCropType=null;
	ArrayList<ComboRowData> alSeason=null;
	ArrayList<ComboRowData> alSeedVariety=null;
	ArrayList<ComboRowData> alLandOwnership=null;
	

	ArrayAdapter<ComboRowData> aaCropType=null;
	ArrayAdapter<ComboRowData> aaSeason=null;
	ArrayAdapter<ComboRowData> aaSeedVariety=null;
	ArrayAdapter<ComboRowData> aaLandOwnership=null;
	
	//local variables
	int FarmID=0;
	String FarmName=null;
	public AddFarmSeasonDetailFragment(int FarmID, String FarmName){
		this.FarmID=FarmID;
		this.FarmName=FarmName;
	}
	
	   public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		
	        View rootView = inflater.inflate(R.layout.frag_add_farm_season,container, false);
	        btnCancel=(Button)rootView.findViewById(R.id.btnCancel);
	        btnCancel.setOnClickListener(this);
	        btnSubmit=(Button)rootView.findViewById(R.id.btnSubmit);
	        btnSubmit.setOnClickListener(this);
	        
	        edtFarmSize=(EditText)rootView.findViewById(R.id.edtFarmSize);
	       //txtFarmerID=(TextView)rootView.findViewById(R.id.txtFarmerID); //for the farmer's ID
			//txtFarmerID.setText(String.valueOf(FarmerID));
	        rdManual= (RadioButton)rootView.findViewById(R.id.rdManual);
	        rdMechanized= (RadioButton)rootView.findViewById(R.id.rdMechanized);
	        rdRegularFarm= (RadioButton)rootView.findViewById(R.id.rdRegularFarm);
	        rdDemoPlot= (RadioButton)rootView.findViewById(R.id.rdDemoPlot);
	        
	        
			
	        spnCropType =(Spinner)rootView.findViewById(R.id.spnCropType);
	        spnSeason =(Spinner)rootView.findViewById(R.id.spnSeason);
	        spnSeedVariety =(Spinner)rootView.findViewById(R.id.spnSeedVariety);
	        spnLandOwnership =(Spinner)rootView.findViewById(R.id.spnLandOwnership);
	        
            
	        SpinnerData s= new SpinnerData(getActivity());
	        alCropType=s.CropTypeData(-1); //assumes use of -1 for parent
	        aaCropType=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alCropType);	        
	        aaCropType.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	        spnCropType.setAdapter(aaCropType);
	        
	        alLandOwnership=s.LandOwnershipData(-1); //assumes use of -1 for parent
	        aaLandOwnership=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alLandOwnership);	        
	        aaLandOwnership.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	        spnLandOwnership.setAdapter(aaLandOwnership);
	       
	        spnCropType.setOnItemSelectedListener(this);
	        
	        
	        return rootView;
	    }
	
	   public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
		   //selection.setText(items[position]);
	   SpinnerData s= new SpinnerData(getActivity());
	   
	   if (parent==spnCropType){
		   
		   ComboRowData c=(ComboRowData)alCropType.get(position);
	       alSeedVariety=s.SeedVarietyData(c.ID);  //same parent for both seed varieties and seasons 
	       aaSeedVariety=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alSeedVariety);	        
	       aaSeedVariety.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	       spnSeedVariety.setAdapter(aaSeedVariety);
	       
	        alSeason=s.SeasonData(c.ID); //same parent for both seed varieties and seasons
	        aaSeason=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alSeason);	        
	        aaSeason.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	        spnSeason.setAdapter(aaSeason);
	   }//spnCropType
	   }
	   
	   public void onNothingSelected(AdapterView<?> parent) { 
			  // selection.setText("");
			}
	   

	
	 public void onClick(View v){
		   if (v==btnCancel){
			   //getFragmentManager().popBackStack();
		   }
		   if (v==btnSubmit){
			   //validation:
			  if (! validateData()) //if validation fails, exit
				  return;
			   fetchDataAndPost();   
		   }
		
		  
	   }
	 
	 public void fetchDataAndPost(){
		  HashMap<String, String> values = new HashMap<String, String>();
		   
		   values.put("FarmID",String.valueOf(FarmID));
		 	   
		   ComboRowData c;
		   int index;
		   c=(ComboRowData)alCropType.get(spnCropType.getSelectedItemPosition());		   
		   values.put("CropTypeID",String.valueOf(c.ID));
		   c=(ComboRowData)alSeason.get(spnSeason.getSelectedItemPosition());		   
		   values.put("SeasonID",String.valueOf(c.ID));
		   c=(ComboRowData)alSeedVariety.get(spnSeedVariety.getSelectedItemPosition());		   
		   values.put("SeedVarietyID",String.valueOf(c.ID));
		   c=(ComboRowData)alLandOwnership.get(spnLandOwnership.getSelectedItemPosition());		   
		   values.put("LandOwnershipID",String.valueOf(c.ID));
		    
		   values.put("Mechanized",rdMechanized.isChecked()?"1":"0");
		   values.put("DemoPlot",rdDemoPlot.isChecked()?"1":"0");
		   values.put("TotalFarmSize",edtFarmSize.getText().toString());
		   //values.put("UserId",Integer.toString(Login.UserID));
		   //now use call back to send the post
		   callBack.postData(actionPOST_ADD_FARMS_YEARLY_DATA, values);

	 }
	 public boolean validateData(){
		 //validate season- a season will always be selected in the spinner. Ignore
		//validate spinners:
		 if (edtFarmSize.getText().toString().trim().equals("")){
			  Toast.makeText(getActivity(), "Farm Size is blank", Toast.LENGTH_LONG ).show();
			  edtFarmSize.requestFocus();
			   return false;
		   }
		   if ((spnSeedVariety.getSelectedItemPosition()==-1)||
				   (spnSeason.getSelectedItemPosition()==-1)
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


}
