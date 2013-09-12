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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class SurveyProductionDB1 extends Fragment implements OnClickListener,   InovagroConstants {

	GeneralCallbackIntefaces callBack;
	
	Button btnPrevious;
	Button btnNext;
	
	Spinner spnQ3a,
	spnQ3b,
	spnQ3h,
	spnQ3k1,
	spnQ3k2,
	spnQ3k3,
	spnQ3k4,
	spnQ3k5,
	spnQ3l;
	
	
	ListView  lstQ3d,lstQ3kb;
	
	EditText edtQ3c1,
			edtQ3c2,
			edtQ3c3,
			edtQ3e1,
			edtQ3e2,
			edtQ3e3,
			edtQ3f1,
			edtQ3f2,
			edtQ3f3,
			edtQ3f4,
			edtQ3f5,
			edtQ3f6,
			edtQ3g1,
			edtQ3g2,
			edtQ3i,
			edtQ3m
			;
	ArrayAdapter<String> aaspnQ3a=null;
	ArrayAdapter<String> aaspnQ3b=null;
	ArrayAdapter<String> aaspnQ3h=null;
	ArrayAdapter<String> aaspnQ3k1=null;
	ArrayAdapter<String> aaspnQ3k2=null;
	ArrayAdapter<String> aaspnQ3k3=null;
	ArrayAdapter<String> aaspnQ3k4=null;
	ArrayAdapter<String> aaspnQ3k5=null;
	ArrayAdapter<String> aaspnQ3l=null;
	
	ArrayAdapter<String> aalstQ3d=null;
	ArrayAdapter<String> aalstQ3kb=null;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
        View rootView = inflater.inflate(R.layout.frag_survey_productiondb1,container, false);
        btnPrevious=(Button)rootView.findViewById(R.id.btnPrevious);
        btnPrevious.setOnClickListener(this);
        btnNext=(Button)rootView.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        
        Resources res = getResources();

        String [] arQ3a = res.getStringArray(R.array.A3a);
		aaspnQ3a=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3a);		
		aaspnQ3a.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3a=(Spinner)rootView.findViewById(R.id.spnQ3a);
		spnQ3a.setAdapter(aaspnQ3a);

		String [] arQ3b = res.getStringArray(R.array.A3b);
		aaspnQ3b=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3b);		
		aaspnQ3b.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3b=(Spinner)rootView.findViewById(R.id.spnQ3b);
		spnQ3b.setAdapter(aaspnQ3b);

		String [] arQ3h = res.getStringArray(R.array.A3h);
		aaspnQ3h=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3h);		
		aaspnQ3h.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3h=(Spinner)rootView.findViewById(R.id.spnQ3h);
		spnQ3h.setAdapter(aaspnQ3h);

		String [] arQ3k1 = res.getStringArray(R.array.A3k1);
		aaspnQ3k1=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3k1);		
		aaspnQ3k1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3k1=(Spinner)rootView.findViewById(R.id.spnQ3k1);
		spnQ3k1.setAdapter(aaspnQ3k1);

		String [] arQ3k2 = res.getStringArray(R.array.A3k2);
		aaspnQ3k2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3k2);		
		aaspnQ3k2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3k2=(Spinner)rootView.findViewById(R.id.spnQ3k2);
		spnQ3k2.setAdapter(aaspnQ3k2);

		String [] arQ3k3 = res.getStringArray(R.array.A3k3);
		aaspnQ3k3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3k3);		
		aaspnQ3k3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3k3=(Spinner)rootView.findViewById(R.id.spnQ3k3);
		spnQ3k3.setAdapter(aaspnQ3k3);

		String [] arQ3k4 = res.getStringArray(R.array.A3k4);
		aaspnQ3k4=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3k4);		
		aaspnQ3k4.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3k4=(Spinner)rootView.findViewById(R.id.spnQ3k4);
		spnQ3k4.setAdapter(aaspnQ3k4);

		String [] arQ3k5 = res.getStringArray(R.array.A3k5);
		aaspnQ3k5=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3k5);		
		aaspnQ3k5.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3k5=(Spinner)rootView.findViewById(R.id.spnQ3k5);
		spnQ3k5.setAdapter(aaspnQ3k5);


		String [] arQ3l = res.getStringArray(R.array.A3l);
		aaspnQ3l=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3l);		
		aaspnQ3l.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3l=(Spinner)rootView.findViewById(R.id.spnQ3l);
		spnQ3l.setAdapter(aaspnQ3l);
       
		
		//not a spinner, its a multi-select list
		String [] arQ3d = res.getStringArray(R.array.A3d);
		aalstQ3d=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, arQ3d);		
		//aalstQ3d.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		lstQ3d=(ListView)rootView.findViewById(R.id.lstQ3d);
		lstQ3d.setAdapter(aalstQ3d);
		
		String [] arQ3kb = res.getStringArray(R.array.A3kb);
		aalstQ3kb=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, arQ3kb);		
		//aalstQ3kb.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		lstQ3kb=(ListView)rootView.findViewById(R.id.lstQ3kb);
		lstQ3kb.setAdapter(aalstQ3kb);
		
		UtilityFunctions.setListViewHeightBasedOnChildren(lstQ3d);
		UtilityFunctions.setListViewHeightBasedOnChildren(lstQ3kb);
		
		edtQ3c1=(EditText)rootView.findViewById(R.id.edtQ3c1);
		edtQ3c2=(EditText)rootView.findViewById(R.id.edtQ3c2);
		edtQ3c3=(EditText)rootView.findViewById(R.id.edtQ3c3);
		edtQ3e1=(EditText)rootView.findViewById(R.id.edtQ3e1);
		edtQ3e2=(EditText)rootView.findViewById(R.id.edtQ3e2);
		edtQ3e3=(EditText)rootView.findViewById(R.id.edtQ3e3);
		edtQ3f1=(EditText)rootView.findViewById(R.id.edtQ3f1);
		edtQ3f2=(EditText)rootView.findViewById(R.id.edtQ3f2);
		edtQ3f3=(EditText)rootView.findViewById(R.id.edtQ3f3);
		edtQ3f4=(EditText)rootView.findViewById(R.id.edtQ3f4);
		edtQ3f5=(EditText)rootView.findViewById(R.id.edtQ3f5);
		edtQ3f6=(EditText)rootView.findViewById(R.id.edtQ3f6);
		edtQ3g1=(EditText)rootView.findViewById(R.id.edtQ3g1);
		edtQ3g2=(EditText)rootView.findViewById(R.id.edtQ3g2);
		edtQ3i=(EditText)rootView.findViewById(R.id.edtQ3i);
		edtQ3m=(EditText)rootView.findViewById(R.id.edtQ3m);
		/*
		if(aalstQ3kb.getCount() > 2){
	        View item = aalstQ3kb.getView(0, null, lstQ3kb);
	        item.measure(0, 0);         
	        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, (int) (2.5 * item.getMeasuredHeight()));
	        lstQ3kb.setLayoutParams(params);
	}
		if(aalstQ3d.getCount() > 4){
	        View item = aalstQ3d.getView(0, null, lstQ3d);
	        item.measure(0, 0);         
	        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, (int) (4.5 * item.getMeasuredHeight()));
	        lstQ3d.setLayoutParams(params);
	}	
	*/	
		spnQ3a.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3a") ) );
		spnQ3b.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3b") ) );
		spnQ3h.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3h") ) );
		spnQ3k1.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3k1") ) );
		spnQ3k2.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3k2") ) );
		spnQ3k3.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3k3") ) );
		spnQ3k4.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3k4") ) );
		spnQ3k5.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3k5") ) );
		spnQ3l.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3l") ) );
		
		
		//do multiselect list too
		edtQ3c1.setText(MainActivity.gblAllSurveyValues.get("Q3c1"));
		edtQ3c2.setText(MainActivity.gblAllSurveyValues.get("Q3c2"));
		edtQ3c3.setText(MainActivity.gblAllSurveyValues.get("Q3c3"));
		edtQ3e1.setText(MainActivity.gblAllSurveyValues.get("Q3e1"));
		edtQ3e2.setText(MainActivity.gblAllSurveyValues.get("Q3e2"));
		edtQ3e3.setText(MainActivity.gblAllSurveyValues.get("Q3e3"));
		edtQ3f1.setText(MainActivity.gblAllSurveyValues.get("Q3f1"));
		edtQ3f2.setText(MainActivity.gblAllSurveyValues.get("Q3f2"));
		edtQ3f3.setText(MainActivity.gblAllSurveyValues.get("Q3f3"));
		edtQ3f4.setText(MainActivity.gblAllSurveyValues.get("Q3f4"));
		edtQ3f5.setText(MainActivity.gblAllSurveyValues.get("Q3f5"));
		edtQ3f6.setText(MainActivity.gblAllSurveyValues.get("Q3f6"));
		edtQ3g1.setText(MainActivity.gblAllSurveyValues.get("Q3g1"));
		edtQ3g2.setText(MainActivity.gblAllSurveyValues.get("Q3g2"));
		edtQ3i.setText(MainActivity.gblAllSurveyValues.get("Q3i"));
		edtQ3m.setText(MainActivity.gblAllSurveyValues.get("Q3m"));
		
		//pending
		//lstQ3d,
		//lstQ3kb

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
		   
		   values.put("Q3a",String.valueOf(spnQ3a.getSelectedItemPosition()));
		   values.put("Q3b",String.valueOf(spnQ3b.getSelectedItemPosition()));
		   values.put("Q3k1",String.valueOf(spnQ3k1.getSelectedItemPosition()));
		   values.put("Q3k2",String.valueOf(spnQ3k2.getSelectedItemPosition()));
		   values.put("Q3k3",String.valueOf(spnQ3k3.getSelectedItemPosition()));
		   values.put("Q3k4",String.valueOf(spnQ3k4.getSelectedItemPosition()));
		   values.put("Q3k5",String.valueOf(spnQ3k5.getSelectedItemPosition()));
		   values.put("Q3h",String.valueOf(spnQ3h.getSelectedItemPosition()));
		   values.put("Q3l",String.valueOf(spnQ3l.getSelectedItemPosition()));
		   
		 values.put("Q3c1",edtQ3c1.getText().toString());  //yet to be added
		 values.put("Q3c2",edtQ3c2.getText().toString());  
		 values.put("Q3c3",edtQ3c3.getText().toString()); 
		 values.put("Q3e1",edtQ3e1.getText().toString()); 
		 values.put("Q3e2",edtQ3e2.getText().toString()); 
		 values.put("Q3e3",edtQ3e3.getText().toString()); 
		 values.put("Q3f1",edtQ3f1.getText().toString()); 
		 values.put("Q3f2",edtQ3f2.getText().toString()); 
		 values.put("Q3f3",edtQ3f3.getText().toString()); 
		 values.put("Q3f4",edtQ3f4.getText().toString()); 
		 values.put("Q3f5",edtQ3f5.getText().toString());
		 values.put("Q3f6",edtQ3f6.getText().toString());
		 values.put("Q3g1",edtQ3g1.getText().toString()); 
		 values.put("Q3g2",edtQ3g2.getText().toString()); 
		 values.put("Q3i",edtQ3i.getText().toString()); 
		 values.put("Q3m",edtQ3m.getText().toString()); 
		 
		 String ids="";
		 //lstQ3d.getCheckedItemPositions()
		 SparseBooleanArray chkedItems =lstQ3d.getCheckedItemPositions();
		   if (chkedItems!=null){
			   
			   
			   int len = lstQ3d.getCount();
			   SparseBooleanArray checked = lstQ3d.getCheckedItemPositions();
			   for (int i = 0; i < len; i++)
			    if (checked.get(i)) {
			     //String item = aalstQ3d.get(i);
			    	ids+=String.valueOf(i)+",";
			    }
			   
			   /*
			   for(int i=0; i< chkedItems.size(); i++){
			   if(chkedItems.valueAt(i)){
				   //String item= lv.getAdapter().getItem(chkedItems.keyAt(i)).toString();
				   String item= lstQ3d.getAdapter().getItem(chkedItems.keyAt(i)).toString();
				   ids+=item+",";
				   Log.v("selecteListItems", ids);
				   //ids+=String.valueOf(i)+",";  //this was in the code previously, not the three avove
				   //verify if this ids are correct
				   //working on above!!!
				   //assume that 
			   }
			   
		   } */
//			   ids=ids.substring(0, ids.length()-1); //remove last ,
			   Log.v("ids slected--Q3d", ids);
		   }
		   values.put("Q3d", ids);
			 
		   ids="";
			 //lstQ3d.getCheckedItemPositions()
			 SparseBooleanArray chkedItems2 =lstQ3kb.getCheckedItemPositions();
			   if (chkedItems2!=null){
				
				   int len2 = lstQ3kb.getCount();
				   SparseBooleanArray checked2 = lstQ3kb.getCheckedItemPositions();
				   for (int i = 0; i < len2; i++)
				    if (checked2.get(i)) {				     
				    	ids+=String.valueOf(i)+",";
				    }
	//			   ids=ids.substring(0, ids.length()-1); //remove last ,
				   Log.v("ids slected Q3kb", ids);
			   }
			   values.put("Q3kb", ids);
		 
		   MainActivity.gblPartIIIaValues=values;
		   MainActivity.gblAllSurveyValues.putAll(values);
		   callBack.showNextSurveyForm(surveyPartIIIb);
		 
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
