package com.inovagro.inovagrofdc1;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyPigeonPeaHarvestFragment extends Fragment implements OnClickListener, InovagroConstants {
/*
 * To do: do display farmer's name and keep his ID. May need to include that in the parameter passed 
 * to instantiate this class. 
 * 
 * May need to replace seed variety with spinner.
 */
GeneralCallbackIntefaces callBack;

String FarmerID, FarmerName;
	
	Button btnCancel;
	Button btnSubmit;
	
	TextView txtFarmerName;
	
	Spinner spnSalesContract;
	EditText edtPlanted2011;
	EditText edtPlanted2012;
	EditText edtSeedVarietyPlanted;   // <--------this shd be combination of seed type and variety
	EditText edtQuantitySeedReceived;
	EditText edtSeedRateUsed;
	//EditText edtTotalPigeionPeaHarvested;
	EditText edtQ7Kg;
	EditText edtQ7NoBags;
	EditText edtQ7WtOfOneBag;
	EditText edtQ7NoBuckets;
	EditText edtQ7WtOfOneBucket;
	//EditText edtQuantityHarvestedGreen;
	EditText edtQ8Kg;
	EditText edtQ8NoBags;
	EditText edtQ8WtOfOneBag;
	EditText edtQ8NoBuckets;
	EditText edtQ8WtOfOneBucket;
	//EditText edtQuantitySoldOutsideContractGreen;
	EditText edtQ9Kg;
	EditText edtQ9NoBags;
	EditText edtQ9WtOfOneBag;
	EditText edtQ9NoBuckets;
	EditText edtQ9WtOfOneBucket;
	EditText edtBuyersGreen;
	EditText edtPriceSoldGreen;
	//EditText edtQuantitySoldOutsideContractDry;
	EditText edtQ12Kg;
	EditText edtQ12NoBags;
	EditText edtQ12WtOfOneBag;
	EditText edtQ12NoBuckets;
	EditText edtQ12WtOfOneBucket;
	EditText edtBuyersOutsideContractDry;
	EditText edtPriceSoldOutsideContractDry;
	//EditText edtQuantitySoldOutsideContract;
	EditText edtQ15Kg;
	EditText edtQ15NoBags;
	EditText edtQ15WtOfOneBag;
	EditText edtQ15NoBuckets;
	EditText edtQ15WtOfOneBucket;
	EditText edtBuyersWithinContract;
	EditText edtPriceSoldWithinContract;
	
	ArrayAdapter<String> aaspnSalesContract=null;
	
	ArrayList<ComboRowData> alSeedVarietyPlanted=null;
	ArrayAdapter<ComboRowData> aaSeedVarietyPlanted=null;
	
	public SurveyPigeonPeaHarvestFragment(int FarmerID, String FarmerName){
		this.FarmerID=Integer.toString(FarmerID);
		this.FarmerName=FarmerName;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
        View rootView = inflater.inflate(R.layout.frag_survey_pigeonpea_harvest,container, false);
        btnCancel=(Button)rootView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnSubmit=(Button)rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
  
        txtFarmerName=(TextView)rootView.findViewById(R.id.txtFarmerName);
        //edtSalesContract=(EditText)rootView.findViewById(R.id.edtSalesContract);
        edtPlanted2011=(EditText)rootView.findViewById(R.id.edtPlanted2011);
        edtPlanted2012=(EditText)rootView.findViewById(R.id.edtPlanted2012);
        edtSeedVarietyPlanted=(EditText)rootView.findViewById(R.id.edtSeedVarietyPlanted);
        edtQuantitySeedReceived=(EditText)rootView.findViewById(R.id.edtQuantitySeedReceived);
        edtSeedRateUsed=(EditText)rootView.findViewById(R.id.edtSeedRateUsed);
        //edtTotalPigeionPeaHarvested=(EditText)rootView.findViewById(R.id.edtTotalPigeionPeaHarvested);
        edtQ7Kg=(EditText)rootView.findViewById(R.id.edtQ7Kg);
        edtQ7NoBags=(EditText)rootView.findViewById(R.id.edtQ7NoBags);
        edtQ7WtOfOneBag=(EditText)rootView.findViewById(R.id.edtQ7WtOfOneBag);
        edtQ7NoBuckets=(EditText)rootView.findViewById(R.id.edtQ7NoBuckets);
        edtQ7WtOfOneBucket=(EditText)rootView.findViewById(R.id.edtQ7WtOfOneBucket);
        //edtQuantityHarvestedGreen=(EditText)rootView.findViewById(R.id.edtQuantityHarvestedGreen);
        edtQ8Kg=(EditText)rootView.findViewById(R.id.edtQ8Kg);
        edtQ8NoBags=(EditText)rootView.findViewById(R.id.edtQ8NoBags);
        edtQ8WtOfOneBag=(EditText)rootView.findViewById(R.id.edtQ8WtOfOneBag);
        edtQ8NoBuckets=(EditText)rootView.findViewById(R.id.edtQ8NoBuckets);
        edtQ8WtOfOneBucket=(EditText)rootView.findViewById(R.id.edtQ8WtOfOneBucket);
        //edtQuantitySoldOutsideContractGreen=(EditText)rootView.findViewById(R.id.edtQuantitySoldOutsideContractGreen);
        edtQ9Kg=(EditText)rootView.findViewById(R.id.edtQ9Kg);
        edtQ9NoBags=(EditText)rootView.findViewById(R.id.edtQ9NoBags);
        edtQ9WtOfOneBag=(EditText)rootView.findViewById(R.id.edtQ9WtOfOneBag);
        edtQ9NoBuckets=(EditText)rootView.findViewById(R.id.edtQ9NoBuckets);
        edtQ9WtOfOneBucket=(EditText)rootView.findViewById(R.id.edtQ9WtOfOneBucket);
        edtBuyersGreen=(EditText)rootView.findViewById(R.id.edtBuyersGreen);
        edtPriceSoldGreen=(EditText)rootView.findViewById(R.id.edtPriceSoldGreen);
        //edtQuantitySoldOutsideContractDry=(EditText)rootView.findViewById(R.id.edtQuantitySoldOutsideContractDry);
        edtQ12Kg=(EditText)rootView.findViewById(R.id.edtQ12Kg);
        edtQ12NoBags=(EditText)rootView.findViewById(R.id.edtQ12NoBags);
        edtQ12WtOfOneBag=(EditText)rootView.findViewById(R.id.edtQ12WtOfOneBag);
        edtQ12NoBuckets=(EditText)rootView.findViewById(R.id.edtQ12NoBuckets);
        edtQ12WtOfOneBucket=(EditText)rootView.findViewById(R.id.edtQ12WtOfOneBucket);
        edtBuyersOutsideContractDry=(EditText)rootView.findViewById(R.id.edtBuyersOutsideContractDry);
        edtPriceSoldOutsideContractDry=(EditText)rootView.findViewById(R.id.edtPriceSoldOutsideContractDry);
        //edtQuantitySoldOutsideContract=(EditText)rootView.findViewById(R.id.edtQuantitySoldOutsideContract);
        edtQ15Kg=(EditText)rootView.findViewById(R.id.edtQ15Kg);
        edtQ15NoBags=(EditText)rootView.findViewById(R.id.edtQ15NoBags);
        edtQ15WtOfOneBag=(EditText)rootView.findViewById(R.id.edtQ15WtOfOneBag);
        edtQ15NoBuckets=(EditText)rootView.findViewById(R.id.edtQ15NoBuckets);
        edtQ15WtOfOneBucket=(EditText)rootView.findViewById(R.id.edtQ15WtOfOneBucket);
        edtBuyersWithinContract=(EditText)rootView.findViewById(R.id.edtBuyersWithinContract);
        edtPriceSoldWithinContract=(EditText)rootView.findViewById(R.id.edtPriceSoldWithinContract);
        
        Resources res = getResources();
		String [] arSalesContract = res.getStringArray(R.array.arrySalesContract);
		aaspnSalesContract=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arSalesContract);		
		aaspnSalesContract.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnSalesContract=(Spinner)rootView.findViewById(R.id.spnSalesContract);
		spnSalesContract.setAdapter(aaspnSalesContract);
		
		/*
		SpinnerData s= new SpinnerData(getActivity());
		 ComboRowData c=(ComboRowData)alCropType.get(position); //determine posiiton in array
	       alSeedVarietyPlanted=s.SeedVarietyData(c.ID);  //same parent for both seed varieties and seasons 
	       aaSeedVarietyPlanted=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alSeedVarietyPlanted);	        
	       aaSeedVarietyPlanted.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	       spnSeedVarietyPlanted.setAdapter(aaSeedVarietyPlanted);
	      */
		
		
		//spnSeedVarietyPlanted
		
//        edtFarmName=(EditText)rootView.findViewById(R.id.edtFarmName);
       

        //next two are bad variable naming. Short cut taken: Showing Farmer name as ID instead.
        //txtFarmerName=(TextView)rootView.findViewById(R.id.txtFarmerName); //for the farmer's name
		//txtFarmerID=(TextView)rootView.findViewById(R.id.txtFarmerID); //for the farmer's ID
   ////     txtFarmerID=(TextView)rootView.findViewById(R.id.txtFarmerID);
		
		//txtFarmerName.setText(FarmerName);
		//txtFarmerID.setText(String.valueOf(FarmerID));
   ////     txtFarmerID.setText(String.valueOf(FarmerName));//see comment above
		
		
        //spnProvince =(Spinner)rootView.findViewById(R.id.spnProvince);     
        //spnProvince.setOnItemSelectedListener(this);
      
      txtFarmerName.setText(FarmerName);  
      
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
	   
	  
   }
   
		   
   
   private void fetchDataAndPost(){

	   //alternative to ahve a single var to pass to post method
	   HashMap<String, String> values = new HashMap<String, String>();
	   
	   values.put("FarmerID",FarmerID);
	   //values.put("FarmName",edtFarmName.getText().toString());
	   
	   values.put("UserID",Integer.toString(Login.UserID));
	  
	   values.put("SalesContract",String.valueOf(spnSalesContract.getSelectedItemPosition()));
	   
	   values.put("Planted2011",edtPlanted2011.getText().toString());
	   values.put("Planted2012",edtPlanted2012.getText().toString());
	   values.put("SeedVarietyPlanted",edtSeedVarietyPlanted.getText().toString());
	   values.put("QuantitySeedReceived",edtQuantitySeedReceived.getText().toString());
	   values.put("SeedRateUsed",edtSeedRateUsed.getText().toString());
	   //values.put("TotalPigeionPeaHarvested",edtTotalPigeionPeaHarvested.getText().toString());
	   values.put("Q7Kg",edtQ7Kg.getText().toString());
	   values.put("Q7NoBags",edtQ7NoBags.getText().toString());
	   values.put("Q7WtOfOneBag",edtQ7WtOfOneBag.getText().toString());
	   values.put("Q7NoBuckets",edtQ7NoBuckets.getText().toString());
	   values.put("Q7WtOfOneBucket",edtQ7WtOfOneBucket.getText().toString());
	   //values.put("QuantityHarvestedGreen",edtQuantityHarvestedGreen.getText().toString());
	   values.put("Q8Kg",edtQ8Kg.getText().toString());
	   values.put("Q8NoBags",edtQ8NoBags.getText().toString());
	   values.put("Q8WtOfOneBag",edtQ8WtOfOneBag.getText().toString());
	   values.put("Q8NoBuckets",edtQ8NoBuckets.getText().toString());
	   values.put("Q8WtOfOneBucket",edtQ8WtOfOneBucket.getText().toString());
	   //values.put("QuantitySoldOutsideContractGreen",edtQuantitySoldOutsideContractGreen.getText().toString());
	   values.put("Q9Kg",edtQ9Kg.getText().toString());
	   values.put("Q9NoBags",edtQ9NoBags.getText().toString());
	   values.put("Q9WtOfOneBag",edtQ9WtOfOneBag.getText().toString());
	   values.put("Q9NoBuckets",edtQ9NoBuckets.getText().toString());
	   values.put("Q9WtOfOneBucket",edtQ9WtOfOneBucket.getText().toString());
	   values.put("BuyersGreen",edtBuyersGreen.getText().toString());
	   values.put("PriceSoldGreen",edtPriceSoldGreen.getText().toString());
	   //values.put("QuantitySoldOutsideContractDry",edtQuantitySoldOutsideContractDry.getText().toString());
	   values.put("Q12Kg",edtQ12Kg.getText().toString());
	   values.put("Q12NoBags",edtQ12NoBags.getText().toString());
	   values.put("Q12WtOfOneBag",edtQ12WtOfOneBag.getText().toString());
	   values.put("Q12NoBuckets",edtQ12NoBuckets.getText().toString());
	   values.put("Q12WtOfOneBucket",edtQ12WtOfOneBucket.getText().toString());
	   values.put("BuyersOutsideContractDry",edtBuyersOutsideContractDry.getText().toString());
	   values.put("PriceSoldOutsideContractDry",edtPriceSoldOutsideContractDry.getText().toString());
	   //values.put("QuantitySoldOutsideContract",edtQuantitySoldOutsideContract.getText().toString());
	   values.put("Q15Kg",edtQ15Kg.getText().toString());
	   values.put("Q15NoBags",edtQ15NoBags.getText().toString());
	   values.put("Q15WtOfOneBag",edtQ15WtOfOneBag.getText().toString());
	   values.put("Q15NoBuckets",edtQ15NoBuckets.getText().toString());
	   values.put("Q15WtOfOneBucket",edtQ15WtOfOneBucket.getText().toString());
	   values.put("BuyersWithinContract",edtBuyersWithinContract.getText().toString());
	   values.put("PriceSoldWithinContract",edtPriceSoldWithinContract.getText().toString());
	   
	   
//	   ComboRowData c;
//	   c=(ComboRowData)alProvince.get(spnProvince.getSelectedItemPosition());		   
//	   values.put("ProvinceID",String.valueOf(c.ID));


	   //now use call back to send the post
	   callBack.postData(actionPIGEONPEA_HARVEST_SURVEY, values);
   }
   
   
   boolean validateData(){
	   //edtSurname, edtForenames, edtFarmerReferenceNo
	   if (edtPlanted2012.getText().toString().trim().equals("")){
		  Toast.makeText(getActivity(), "Data for 2012 is blank", Toast.LENGTH_LONG ).show();
		  edtPlanted2012.requestFocus();
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
