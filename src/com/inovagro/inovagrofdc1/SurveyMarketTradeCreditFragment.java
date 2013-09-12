package com.inovagro.inovagrofdc1;

import java.util.HashMap;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class SurveyMarketTradeCreditFragment extends Fragment implements OnClickListener,   InovagroConstants {
	
	GeneralCallbackIntefaces callBack;
	
	Button btnPrevious;
	Button btnNext;
	
	Spinner spnQ4a,
	spnQ4b,
	spnQ4bi,
	spnQ4biii,
	spnQ4g;
EditText edtQ4ai,
		edtQ4bii,
		edtQ4biv,
		edtQ4d,
		edtQ4e,
		edtQ4f1a,
		edtQ4f1b,
		edtQ4f1c,
		edtQ4f1d,
		edtQ4f2a,
		edtQ4f2b,
		edtQ4f2c,
		edtQ4f2d,
		edtQ4f3a,
		edtQ4f3b,
		edtQ4f3c,
		edtQ4f3d,
		edtQ4g2
		;

ListView lstQ4c;

	ArrayAdapter<String> aaspnQ4a=null;
	ArrayAdapter<String> aaspnQ4b=null;
	ArrayAdapter<String> aaspnQ4bi=null;
	ArrayAdapter<String> aaspnQ4biii=null;
	ArrayAdapter<String> aaspnQ4g=null;
	
	ArrayAdapter<String> aalstQ4c=null;
	
	
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		
	        View rootView = inflater.inflate(R.layout.frag_survey_markettradecredit,container, false);
	        btnPrevious=(Button)rootView.findViewById(R.id.btnPrevious);
	        btnPrevious.setOnClickListener(this);
	        btnNext=(Button)rootView.findViewById(R.id.btnNext);
	        btnNext.setOnClickListener(this);
	        
	        Resources res = getResources();
	        String [] arQ4a = res.getStringArray(R.array.A4a);
			aaspnQ4a=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ4a);		
			aaspnQ4a.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
			spnQ4a=(Spinner)rootView.findViewById(R.id.spnQ4a);
			spnQ4a.setAdapter(aaspnQ4a);
			
			String [] arQ4b = res.getStringArray(R.array.A4b);
			aaspnQ4b=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ4b);		
			aaspnQ4b.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
			spnQ4b=(Spinner)rootView.findViewById(R.id.spnQ4b);
			spnQ4b.setAdapter(aaspnQ4b);
			
			String [] arQ4bi = res.getStringArray(R.array.A4bi);
			aaspnQ4bi=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ4bi);		
			aaspnQ4bi.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
			spnQ4bi=(Spinner)rootView.findViewById(R.id.spnQ4bi);
			spnQ4bi.setAdapter(aaspnQ4bi);
			
			String [] arQ4biii = res.getStringArray(R.array.A4biii);
			aaspnQ4biii=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ4biii);		
			aaspnQ4biii.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
			spnQ4biii=(Spinner)rootView.findViewById(R.id.spnQ4biii);
			spnQ4biii.setAdapter(aaspnQ4biii);
			
			String [] arQ4g = res.getStringArray(R.array.A4g);
			aaspnQ4g=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ4g);		
			aaspnQ4g.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
			spnQ4g=(Spinner)rootView.findViewById(R.id.spnQ4g);
			spnQ4g.setAdapter(aaspnQ4g);
			
			
			//lsit:
			String [] arQ4c = res.getStringArray(R.array.A4c);
			aalstQ4c=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, arQ4c);		
			//aalstQ4c.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
			lstQ4c=(ListView)rootView.findViewById(R.id.lstQ4c);
			lstQ4c.setAdapter(aalstQ4c);
			//edtQ2c=(EditText)rootView.findViewById(R.id.edtQ2c);  //yet to be added
			UtilityFunctions.setListViewHeightBasedOnChildren(lstQ4c); //make list its max height
			
			edtQ4ai=(EditText)rootView.findViewById(R.id.edtQ4ai);
			edtQ4bii=(EditText)rootView.findViewById(R.id.edtQ4bii);
			edtQ4biv=(EditText)rootView.findViewById(R.id.edtQ4biv);
			edtQ4d=(EditText)rootView.findViewById(R.id.edtQ4d);
			edtQ4e=(EditText)rootView.findViewById(R.id.edtQ4e);
			edtQ4f1a=(EditText)rootView.findViewById(R.id.edtQ4f1a);
			edtQ4f1b=(EditText)rootView.findViewById(R.id.edtQ4f1b);
			edtQ4f1c=(EditText)rootView.findViewById(R.id.edtQ4f1c);
			edtQ4f1d=(EditText)rootView.findViewById(R.id.edtQ4f1d);
			edtQ4f2a=(EditText)rootView.findViewById(R.id.edtQ4f2a);
			edtQ4f2b=(EditText)rootView.findViewById(R.id.edtQ4f2b);
			edtQ4f2c=(EditText)rootView.findViewById(R.id.edtQ4f2c);
			edtQ4f2d=(EditText)rootView.findViewById(R.id.edtQ4f2d);
			edtQ4f3a=(EditText)rootView.findViewById(R.id.edtQ4f3a);
			edtQ4f3b=(EditText)rootView.findViewById(R.id.edtQ4f3b);
			edtQ4f3c=(EditText)rootView.findViewById(R.id.edtQ4f3c);
			edtQ4f3d=(EditText)rootView.findViewById(R.id.edtQ4f3d);
			edtQ4g2=(EditText)rootView.findViewById(R.id.edtQ4g2);
			
			edtQ4ai.setText(MainActivity.gblAllSurveyValues.get("Q4ai"));
			edtQ4bii.setText(MainActivity.gblAllSurveyValues.get("Q4bii"));
			edtQ4biv.setText(MainActivity.gblAllSurveyValues.get("Q4biv"));
			edtQ4d.setText(MainActivity.gblAllSurveyValues.get("Q4d"));
			edtQ4e.setText(MainActivity.gblAllSurveyValues.get("Q4e"));
			edtQ4f1a.setText(MainActivity.gblAllSurveyValues.get("Q4f1a"));
			edtQ4f1b.setText(MainActivity.gblAllSurveyValues.get("Q4f1b"));
			edtQ4f1c.setText(MainActivity.gblAllSurveyValues.get("Q4f1c"));
			edtQ4f1d.setText(MainActivity.gblAllSurveyValues.get("Q4f1d"));
			edtQ4f2a.setText(MainActivity.gblAllSurveyValues.get("Q4f2a"));
			edtQ4f2b.setText(MainActivity.gblAllSurveyValues.get("Q4f2b"));
			edtQ4f2c.setText(MainActivity.gblAllSurveyValues.get("Q4f2c"));
			edtQ4f2d.setText(MainActivity.gblAllSurveyValues.get("Q4f2d"));
			edtQ4f3a.setText(MainActivity.gblAllSurveyValues.get("Q4f3a"));
			edtQ4f3b.setText(MainActivity.gblAllSurveyValues.get("Q4f3b"));
			edtQ4f3c.setText(MainActivity.gblAllSurveyValues.get("Q4f3c"));
			edtQ4f3d.setText(MainActivity.gblAllSurveyValues.get("Q4f3d"));
			edtQ4g2.setText(MainActivity.gblAllSurveyValues.get("Q4g2"));


			spnQ4a.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q4a") ) );
			spnQ4b.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q4b") ) );
			spnQ4bi.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q4bi") ) );
			spnQ4biii.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q4biii") ) );
			spnQ4g.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q4g") ) );
			
			//do lstQ4c too
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
			   
			  
			   //
			   values.put("Q4a",String.valueOf(spnQ4a.getSelectedItemPosition()));
			   values.put("Q4b",String.valueOf(spnQ4b.getSelectedItemPosition()));
			   values.put("Q4bi",String.valueOf(spnQ4bi.getSelectedItemPosition()));
			   values.put("Q4biii",String.valueOf(spnQ4biii.getSelectedItemPosition()));
			   values.put("Q4g",String.valueOf(spnQ4g.getSelectedItemPosition()));
			
			   values.put("Q4ai",edtQ4ai.getText().toString());
			   values.put("Q4bii",edtQ4bii.getText().toString());
			   values.put("Q4biv",edtQ4biv.getText().toString());
			   values.put("Q4d",edtQ4d.getText().toString());
			   values.put("Q4e",edtQ4e.getText().toString());
			   values.put("Q4f1a",edtQ4f1a.getText().toString());
			   values.put("Q4f1b",edtQ4f1b.getText().toString());
			   values.put("Q4f1c",edtQ4f1c.getText().toString());
			   values.put("Q4f1d",edtQ4f1d.getText().toString());
			   values.put("Q4f2a",edtQ4f2a.getText().toString());
			   values.put("Q4f2b",edtQ4f2b.getText().toString());
			   values.put("Q4f2c",edtQ4f2c.getText().toString());
			   values.put("Q4f2d",edtQ4f2d.getText().toString());
			   values.put("Q4f3a",edtQ4f3a.getText().toString());
			   values.put("Q4f3b",edtQ4f3b.getText().toString());
			   values.put("Q4f3c",edtQ4f3c.getText().toString());
			   values.put("Q4f3d",edtQ4f3d.getText().toString());
			   values.put("Q4g2",edtQ4g2.getText().toString());
			   
			   String ids="";
				 //lstQ3d.getCheckedItemPositions()
				 SparseBooleanArray chkedItems2 =lstQ4c.getCheckedItemPositions();
				 /*
				   if (chkedItems!=null){
					   for(int i=0; i< chkedItems.size(); i++){
					   if(chkedItems.valueAt(i)){
						   //String item= lv.getAdapter().getItem(chkedItems.keyAt(i)).toString();
						   ids+=String.valueOf(i)+",";
						   //verify if this ids are correct
						   //assume that 
					   }
				   }
					   */
				
				 if (chkedItems2!=null){
						
					   int len2 = lstQ4c.getCount();
					   SparseBooleanArray checked2 = lstQ4c.getCheckedItemPositions();
					   for (int i = 0; i < len2; i++)
					    if (checked2.get(i)) {				     
					    	ids+=String.valueOf(i)+",";
					    }				 
				 
					  // ids=ids.substring(0, ids.length()-1); //remove last ,
					   Log.v("4C ids slected", ids);
				   }
				   values.put("Q4c", ids);
			   
			   MainActivity.gblPartIVValues=values;
			   MainActivity.gblAllSurveyValues.putAll(values);
			   callBack.showNextSurveyForm(surveyConfirmData);
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
