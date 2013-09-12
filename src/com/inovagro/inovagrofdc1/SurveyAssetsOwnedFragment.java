package com.inovagro.inovagrofdc1;

import java.util.HashMap;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SurveyAssetsOwnedFragment extends Fragment implements OnClickListener,   InovagroConstants {
	
	GeneralCallbackIntefaces callBack;
	
	Button btnPrevious;
	Button btnNext;
	
	Spinner spnQ2a,
		spnQ2b,
		spnQ2c,
		spnMFIQ1,
		spnMFIQ2,
		spnMFIQ3,
		spnMFIQ4,
		spnMFIQ5,
		spnMFIQ6,
		spnMFIQ7,
		spnMFIQ8,
		spnMFIQ9,
		spnMFIQ10;
	
	EditText edtQ2c;
	
	ArrayAdapter<String> aaspnQ2a=null;
	ArrayAdapter<String> aaspnQ2b=null;
	ArrayAdapter<String> aaspnQ2c=null;
	ArrayAdapter<String> aaspnMFIQ1=null;
	ArrayAdapter<String> aaspnMFIQ2=null;
	ArrayAdapter<String> aaspnMFIQ3=null;
	ArrayAdapter<String> aaspnMFIQ4=null;
	ArrayAdapter<String> aaspnMFIQ5=null;
	ArrayAdapter<String> aaspnMFIQ6=null;
	ArrayAdapter<String> aaspnMFIQ7=null;
	ArrayAdapter<String> aaspnMFIQ8=null;
	ArrayAdapter<String> aaspnMFIQ9=null;
	ArrayAdapter<String> aaspnMFIQ10=null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
        View rootView = inflater.inflate(R.layout.frag_survey_assetsowned,container, false);
        btnPrevious=(Button)rootView.findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(this);
        btnNext=(Button)rootView.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        
        Resources res = getResources();
        String [] arQ2a = res.getStringArray(R.array.A2a);
		aaspnQ2a=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ2a);		
		aaspnQ2a.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ2a=(Spinner)rootView.findViewById(R.id.spnQ2a);
		spnQ2a.setAdapter(aaspnQ2a);

		String [] arQ2b = res.getStringArray(R.array.A2b);
		aaspnQ2b=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ2b);		
		aaspnQ2b.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ2b=(Spinner)rootView.findViewById(R.id.spnQ2b);
		spnQ2b.setAdapter(aaspnQ2b);

		String [] arQ2c = res.getStringArray(R.array.A2c);
		aaspnQ2c=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ2c);		
		aaspnQ2c.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ2c=(Spinner)rootView.findViewById(R.id.spnQ2c);
		spnQ2c.setAdapter(aaspnQ2c);

		String [] arMFIQ1 = res.getStringArray(R.array.MFIQ1);
		aaspnMFIQ1=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arMFIQ1);		
		aaspnMFIQ1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnMFIQ1=(Spinner)rootView.findViewById(R.id.spnMFIQ1);
		spnMFIQ1.setAdapter(aaspnMFIQ1);

		String [] arMFIQ2 = res.getStringArray(R.array.MFIQ2);
		aaspnMFIQ2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arMFIQ2);		
		aaspnMFIQ2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnMFIQ2=(Spinner)rootView.findViewById(R.id.spnMFIQ2);
		spnMFIQ2.setAdapter(aaspnMFIQ2);

		String [] arMFIQ3 = res.getStringArray(R.array.MFIQ3);
		aaspnMFIQ3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arMFIQ3);		
		aaspnMFIQ3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnMFIQ3=(Spinner)rootView.findViewById(R.id.spnMFIQ3);
		spnMFIQ3.setAdapter(aaspnMFIQ3);

		String [] arMFIQ4 = res.getStringArray(R.array.MFIQ4);
		aaspnMFIQ4=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arMFIQ4);		
		aaspnMFIQ4.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnMFIQ4=(Spinner)rootView.findViewById(R.id.spnMFIQ4);
		spnMFIQ4.setAdapter(aaspnMFIQ4);

		String [] arMFIQ5 = res.getStringArray(R.array.MFIQ5);
		aaspnMFIQ5=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arMFIQ5);		
		aaspnMFIQ5.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnMFIQ5=(Spinner)rootView.findViewById(R.id.spnMFIQ5);
		spnMFIQ5.setAdapter(aaspnMFIQ5);

		String [] arMFIQ6 = res.getStringArray(R.array.MFIQ6);
		aaspnMFIQ6=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arMFIQ6);		
		aaspnMFIQ6.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnMFIQ6=(Spinner)rootView.findViewById(R.id.spnMFIQ6);
		spnMFIQ6.setAdapter(aaspnMFIQ6);

		String [] arMFIQ7 = res.getStringArray(R.array.MFIQ7);
		aaspnMFIQ7=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arMFIQ7);		
		aaspnMFIQ7.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnMFIQ7=(Spinner)rootView.findViewById(R.id.spnMFIQ7);
		spnMFIQ7.setAdapter(aaspnMFIQ7);

		String [] arMFIQ8 = res.getStringArray(R.array.MFIQ8);
		aaspnMFIQ8=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arMFIQ8);		
		aaspnMFIQ8.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnMFIQ8=(Spinner)rootView.findViewById(R.id.spnMFIQ8);
		spnMFIQ8.setAdapter(aaspnMFIQ8);

		String [] arMFIQ9 = res.getStringArray(R.array.MFIQ9);
		aaspnMFIQ9=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arMFIQ9);		
		aaspnMFIQ9.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnMFIQ9=(Spinner)rootView.findViewById(R.id.spnMFIQ9);
		spnMFIQ9.setAdapter(aaspnMFIQ9);
		
		
		String [] arMFIQ10 = res.getStringArray(R.array.MFIQ10);
		aaspnMFIQ10=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arMFIQ10);		
		aaspnMFIQ10.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnMFIQ10=(Spinner)rootView.findViewById(R.id.spnMFIQ10);
		spnMFIQ10.setAdapter(aaspnMFIQ10);
		
		
		//set the old values back:
		spnQ2a.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q2a") ) );
		spnQ2b.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q2b") ) );
		spnQ2c.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q2c") ) );
		spnMFIQ1.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q2dMFIQ1") ) );
		spnMFIQ2.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q2dMFIQ2") ) );
		spnMFIQ3.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q2dMFIQ3") ) );
		spnMFIQ4.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q2dMFIQ4") ) );
		spnMFIQ5.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q2dMFIQ5") ) );
		spnMFIQ6.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q2dMFIQ6") ) );
		spnMFIQ7.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q2dMFIQ7") ) );
		spnMFIQ8.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q2dMFIQ8") ) );
		spnMFIQ9.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q2dMFIQ9") ) );
		spnMFIQ10.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q2dMFIQ10") ) );
		
        
		//edtQ2c=(EditText)rootView.findViewById(R.id.edtQ2c);  //yet to be added
		       
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
			 fetchDataAndGoNext();
			  
		   }
		
	}
	
	 
	
	   private void fetchDataAndGoNext(){
			
		   //alternative to ahve a single var to pass to post method
		   HashMap<String, String> values = new HashMap<String, String>();
		   //list of fields
		   
		   values.put("UserID",Integer.toString(Login.UserID));
		   
		  
		   values.put("Q2a",String.valueOf(spnQ2a.getSelectedItemPosition()));
		   values.put("Q2b",String.valueOf(spnQ2b.getSelectedItemPosition()));
		   values.put("Q2c",String.valueOf(spnQ2c.getSelectedItemPosition()));
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
		// values.put("edtQ1a2",edtQ1a2.getText().toString());  //yet to be added
		   
		   MainActivity.gblPartIIValues=values; //will be redundant but used to group the final confirm screen by question no
		   MainActivity.gblAllSurveyValues .putAll(values);
		   callBack.showNextSurveyForm(surveyPartIIIa);
		   //do i need to keep the values selected?
		   
		   //todo: now use call back to send the post
		   //callBack.postData(action___, values);
	   }
	   
	   
	   boolean validateData(){
		 /*  //edtSurname, edtForenames, edtFarmerReferenceNo
		   if (edtFarmName.getText().toString().trim().equals("")){
			  Toast.makeText(getActivity(), "Farm Name is blank", Toast.LENGTH_LONG ).show();
			  edtFarmName.requestFocus();
			   return false;
		   }
		   */
		 
		  
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
