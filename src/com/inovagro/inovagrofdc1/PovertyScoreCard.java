package com.inovagro.inovagrofdc1;

import java.util.HashMap;
import java.util.LinkedHashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PovertyScoreCard extends SurveyAssetsOwnedFragment {

	
	String FarmerID="-1";
	//String FarmerName=null;
		
	
	PovertyScoreCard(String FarmerID){
		this.FarmerID=FarmerID;
		//this.FarmerName=FarmerName;
	}
	
	TextView txt1, txt2, txt3;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		

		//inflater.inflate(R.layout.frag_survey_assetsowned,container, false);
		View rootView =super.onCreateView(inflater, container, savedInstanceState); 
		txt1=(TextView)rootView.findViewById(R.id.txt1);
		txt2=(TextView)rootView.findViewById(R.id.txt2);
		txt3=(TextView)rootView.findViewById(R.id.txt3);

		txt1.setVisibility(View.GONE);
		txt2.setVisibility(View.GONE);
		txt3.setVisibility(View.GONE);
		spnQ2a.setVisibility(View.GONE);
		spnQ2b.setVisibility(View.GONE);
		spnQ2c.setVisibility(View.GONE);
		
		btnPrevious.setText("Cancel"); //use R.soemthing
		btnNext.setText("Save");
		return rootView;
	}
	@Override
	public void onClick(View v) {
		 if (v==btnPrevious){
			   getFragmentManager().popBackStack();
		   }
		 if (v==btnNext){
			 if (!validateData()) //if validation fails, exit
				 return;
			 fetchDataAndSave();
			  
		   }
		
	}

	  private void fetchDataAndSave(){
			
		   //alternative to ahve a single var to pass to post method
		   LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
		   //list of fields
		   
		   values.put("UserID",Integer.toString(Login.UserID));
		   values.put("FarmerID",FarmerID);
		   values.put("PovertyScoreCardID",UtilityFunctions.uniqueID(""));
		   
		   values.put("Q2dMFIQ1",String.valueOf(spnMFIQ1.getSelectedItemPosition()));
		   values.put("Q2dMFIQ2",String.valueOf(spnMFIQ2.getSelectedItemPosition()));
		   values.put("Q2dMFIQ3",String.valueOf(spnMFIQ3.getSelectedItemPosition()));
		   values.put("Q2dMFIQ4",String.valueOf(spnMFIQ4.getSelectedItemPosition()));
		   values.put("Q2dMFIQ5",String.valueOf(spnMFIQ5.getSelectedItemPosition()));
		   values.put("Q2dMFIQ6",String.valueOf(spnMFIQ6.getSelectedItemPosition()));
		   values.put("Q2dMFIQ7",String.valueOf(spnMFIQ7.getSelectedItemPosition()));
		   values.put("Q2dMFIQ8",String.valueOf(spnMFIQ8.getSelectedItemPosition()));
		   values.put("Q2dMFIQ9",String.valueOf(spnMFIQ9.getSelectedItemPosition()));
		   values.put("Q2dMFIQ10",String.valueOf(spnMFIQ9.getSelectedItemPosition()));
		   values.put("MobileTimeStamp",UtilityFunctions.currentStringTimeStamp());
		    
		   
		   
		   callBack.postData(actionSAVE_POVERTY_SCORE_CARD,values);
		  
	   }


}
