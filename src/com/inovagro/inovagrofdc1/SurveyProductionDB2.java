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

public class SurveyProductionDB2 extends Fragment implements OnClickListener,   InovagroConstants {
	
	GeneralCallbackIntefaces callBack;
	
	Button btnPrevious;
	Button btnNext;
	
	Spinner spnQ3n1a,
			spnQ3n2a,
			spnQ3n3a,
			spnQ3n4a,
			spnQ3n5a,
			spnQ3n1b,
			spnQ3n2b,
			spnQ3n3b,
			spnQ3n4b,
			spnQ3n5b,
			spnQ3p,
			spnQ3q1,
			spnQ3q2,
			spnQ3q3,
			spnQ3r,
			spnQ3riii,
			spnQ3riv,
			spnQ3s,
			spnQ3si;
				
EditText edtQ3n1c,
			edtQ3n2c,
			edtQ3n3c,
			edtQ3n4c,
			edtQ3n5c,
			edtQ3o,
			edtQ3qi,
			edtQ3qii,
			edtQ3ri,
			edtQ3rii,
			edtQ3rv,
			edtQ3sii
			;
	
	
	ArrayAdapter<String> aaspnQ3n1a=null;
	ArrayAdapter<String> aaspnQ3n2a=null;
	ArrayAdapter<String> aaspnQ3n3a=null;
	ArrayAdapter<String> aaspnQ3n4a=null;
	ArrayAdapter<String> aaspnQ3n5a=null;
	ArrayAdapter<String> aaspnQ3n1b=null;
	ArrayAdapter<String> aaspnQ3n2b=null;
	ArrayAdapter<String> aaspnQ3n3b=null;
	ArrayAdapter<String> aaspnQ3n4b=null;
	ArrayAdapter<String> aaspnQ3n5b=null;
	ArrayAdapter<String> aaspnQ3n1c=null;
	ArrayAdapter<String> aaspnQ3n2c=null;
	ArrayAdapter<String> aaspnQ3n3c=null;
	ArrayAdapter<String> aaspnQ3n4c=null;
	ArrayAdapter<String> aaspnQ3n5c=null;
	ArrayAdapter<String> aaspnQ3p=null;
	ArrayAdapter<String> aaspnQ3q1=null;
	ArrayAdapter<String> aaspnQ3q2=null;
	ArrayAdapter<String> aaspnQ3q3=null;
	ArrayAdapter<String> aaspnQ3r=null;
	ArrayAdapter<String> aaspnQ3riii=null;
	ArrayAdapter<String> aaspnQ3riv=null;
	ArrayAdapter<String> aaspnQ3s=null;
	ArrayAdapter<String> aaspnQ3si=null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	 View rootView = inflater.inflate(R.layout.frag_survey_productiondb2,container, false);
     btnPrevious=(Button)rootView.findViewById(R.id.btnPrevious);
     btnPrevious.setOnClickListener(this);
     btnNext=(Button)rootView.findViewById(R.id.btnNext);
     btnNext.setOnClickListener(this);
     
     Resources res = getResources();
	/*	
     String [] arQ2a = res.getStringArray(R.array.A2a);
		aaspnQ2a=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ2a);		
		aaspnQ2a.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ2a=(Spinner)rootView.findViewById(R.id.spnQ2a);
		spnQ2a.setAdapter(aaspnQ2a);
		
		String [] arQ3kb = res.getStringArray(R.array.A3kb);
		aalstQ3kb=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, arQ3kb);		
		//aalstQ3kb.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		lstQ3kb=(ListView)rootView.findViewById(R.id.lstQ3kb);
		lstQ3kb.setAdapter(aalstQ3kb);
		*/
     String [] arQ3n1a = res.getStringArray(R.array.A3n1a);
		aaspnQ3n1a=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n1a);		
		aaspnQ3n1a.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n1a=(Spinner)rootView.findViewById(R.id.spnQ3n1a);
		spnQ3n1a.setAdapter(aaspnQ3n1a);


String [] arQ3n2a = res.getStringArray(R.array.A3n2a);
		aaspnQ3n2a=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n2a);		
		aaspnQ3n2a.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n2a=(Spinner)rootView.findViewById(R.id.spnQ3n2a);
		spnQ3n2a.setAdapter(aaspnQ3n2a);


String [] arQ3n3a = res.getStringArray(R.array.A3n3a);
		aaspnQ3n3a=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n3a);		
		aaspnQ3n3a.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n3a=(Spinner)rootView.findViewById(R.id.spnQ3n3a);
		spnQ3n3a.setAdapter(aaspnQ3n3a);


String [] arQ3n4a = res.getStringArray(R.array.A3n4a);
		aaspnQ3n4a=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n4a);		
		aaspnQ3n4a.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n4a=(Spinner)rootView.findViewById(R.id.spnQ3n4a);
		spnQ3n4a.setAdapter(aaspnQ3n4a);


String [] arQ3n5a = res.getStringArray(R.array.A3n5a);
		aaspnQ3n5a=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n5a);		
		aaspnQ3n5a.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n5a=(Spinner)rootView.findViewById(R.id.spnQ3n5a);
		spnQ3n5a.setAdapter(aaspnQ3n5a);


String [] arQ3n1b = res.getStringArray(R.array.A3n1b);
		aaspnQ3n1b=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n1b);		
		aaspnQ3n1b.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n1b=(Spinner)rootView.findViewById(R.id.spnQ3n1b);
		spnQ3n1b.setAdapter(aaspnQ3n1b);


String [] arQ3n2b = res.getStringArray(R.array.A3n2b);
		aaspnQ3n2b=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n2b);		
		aaspnQ3n2b.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n2b=(Spinner)rootView.findViewById(R.id.spnQ3n2b);
		spnQ3n2b.setAdapter(aaspnQ3n2b);


String [] arQ3n3b = res.getStringArray(R.array.A3n3b);
		aaspnQ3n3b=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n3b);		
		aaspnQ3n3b.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n3b=(Spinner)rootView.findViewById(R.id.spnQ3n3b);
		spnQ3n3b.setAdapter(aaspnQ3n3b);


String [] arQ3n4b = res.getStringArray(R.array.A3n4b);
		aaspnQ3n4b=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n4b);		
		aaspnQ3n4b.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n4b=(Spinner)rootView.findViewById(R.id.spnQ3n4b);
		spnQ3n4b.setAdapter(aaspnQ3n4b);


String [] arQ3n5b = res.getStringArray(R.array.A3n5b);
		aaspnQ3n5b=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n5b);		
		aaspnQ3n5b.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n5b=(Spinner)rootView.findViewById(R.id.spnQ3n5b);
		spnQ3n5b.setAdapter(aaspnQ3n5b);

/*
String [] arQ3n1c = res.getStringArray(R.array.A3n1c);
		aaspnQ3n1c=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n1c);		
		aaspnQ3n1c.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n1c=(Spinner)rootView.findViewById(R.id.spnQ3n1c);
		spnQ3n1c.setAdapter(aaspnQ3n1c);


String [] arQ3n2c = res.getStringArray(R.array.A3n2c);
		aaspnQ3n2c=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n2c);		
		aaspnQ3n2c.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n2c=(Spinner)rootView.findViewById(R.id.spnQ3n2c);
		spnQ3n2c.setAdapter(aaspnQ3n2c);


String [] arQ3n3c = res.getStringArray(R.array.A3n3c);
		aaspnQ3n3c=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n3c);		
		aaspnQ3n3c.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n3c=(Spinner)rootView.findViewById(R.id.spnQ3n3c);
		spnQ3n3c.setAdapter(aaspnQ3n3c);


String [] arQ3n4c = res.getStringArray(R.array.A3n4c);
		aaspnQ3n4c=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n4c);		
		aaspnQ3n4c.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n4c=(Spinner)rootView.findViewById(R.id.spnQ3n4c);
		spnQ3n4c.setAdapter(aaspnQ3n4c);


String [] arQ3n5c = res.getStringArray(R.array.A3n5c);
		aaspnQ3n5c=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3n5c);		
		aaspnQ3n5c.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3n5c=(Spinner)rootView.findViewById(R.id.spnQ3n5c);
		spnQ3n5c.setAdapter(aaspnQ3n5c);
*/
		String [] arQ3p = res.getStringArray(R.array.A3p);
		aaspnQ3p=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3p);		
		aaspnQ3p.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3p=(Spinner)rootView.findViewById(R.id.spnQ3p);
		spnQ3p.setAdapter(aaspnQ3p);
		
String [] arQ3q1 = res.getStringArray(R.array.A3q1);
		aaspnQ3q1=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3q1);		
		aaspnQ3q1.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3q1=(Spinner)rootView.findViewById(R.id.spnQ3q1);
		spnQ3q1.setAdapter(aaspnQ3q1);


String [] arQ3q2 = res.getStringArray(R.array.A3q2);
		aaspnQ3q2=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3q2);		
		aaspnQ3q2.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3q2=(Spinner)rootView.findViewById(R.id.spnQ3q2);
		spnQ3q2.setAdapter(aaspnQ3q2);

		String [] arQ3q3 = res.getStringArray(R.array.A3q3);
		aaspnQ3q3=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3q3);		
		aaspnQ3q3.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3q3=(Spinner)rootView.findViewById(R.id.spnQ3q3);
		spnQ3q3.setAdapter(aaspnQ3q3);

String [] arQ3r = res.getStringArray(R.array.A3r);
		aaspnQ3r=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3r);		
		aaspnQ3r.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3r=(Spinner)rootView.findViewById(R.id.spnQ3r);
		spnQ3r.setAdapter(aaspnQ3r);


String [] arQ3riii = res.getStringArray(R.array.A3riii);
		aaspnQ3riii=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3riii);		
		aaspnQ3riii.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3riii=(Spinner)rootView.findViewById(R.id.spnQ3riii);
		spnQ3riii.setAdapter(aaspnQ3riii);

		String [] arQ3riv = res.getStringArray(R.array.A3riv);
		aaspnQ3riv=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3riv);		
		aaspnQ3riv.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3riv=(Spinner)rootView.findViewById(R.id.spnQ3riv);
		spnQ3riv.setAdapter(aaspnQ3riv);


String [] arQ3s = res.getStringArray(R.array.A3s);
		aaspnQ3s=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3s);		
		aaspnQ3s.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3s=(Spinner)rootView.findViewById(R.id.spnQ3s);
		spnQ3s.setAdapter(aaspnQ3s);

/*
String [] arQ3si = res.getStringArray(R.array.A3si);
		aaspnQ3si=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ3si);		
		aaspnQ3si.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ3si=(Spinner)rootView.findViewById(R.id.spnQ3si);
		spnQ3si.setAdapter(aaspnQ3si);
*/

		//edtQ2c=(EditText)rootView.findViewById(R.id.edtQ2c);  
		edtQ3n1c=(EditText)rootView.findViewById(R.id.edtQ3n1c);
		edtQ3n2c=(EditText)rootView.findViewById(R.id.edtQ3n2c);
		edtQ3n3c=(EditText)rootView.findViewById(R.id.edtQ3n3c);
		edtQ3n4c=(EditText)rootView.findViewById(R.id.edtQ3n4c);
		edtQ3n5c=(EditText)rootView.findViewById(R.id.edtQ3n5c);
		edtQ3o=(EditText)rootView.findViewById(R.id.edtQ3o);
		edtQ3qi=(EditText)rootView.findViewById(R.id.edtQ3qi);
		edtQ3qii=(EditText)rootView.findViewById(R.id.edtQ3qii);
		edtQ3ri=(EditText)rootView.findViewById(R.id.edtQ3ri);
		edtQ3rii=(EditText)rootView.findViewById(R.id.edtQ3rii);
		edtQ3rv=(EditText)rootView.findViewById(R.id.edtQ3rv);
		edtQ3sii=(EditText)rootView.findViewById(R.id.edtQ3sii);

		
		spnQ3n1a.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3n1a") ) );
		spnQ3n2a.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3n2a") ) );
		spnQ3n3a.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3n3a") ) );
		spnQ3n4a.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3n4a") ) );
		spnQ3n5a.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3n5a") ) );
		spnQ3n1b.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3n1b") ) );
		spnQ3n2b.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3n2b") ) );
		spnQ3n3b.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3n3b") ) );
		spnQ3n4b.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3n4b") ) );
		spnQ3n5b.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3n5b") ) );
		spnQ3p.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3p") ) );
		spnQ3q1.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3q1") ) );
		spnQ3q2.setSelection(Integer.parseInt(MainActivity.gblAllSurveyValues.get("Q3q2") ) );
		
		
		
		edtQ3n1c.setText(MainActivity.gblAllSurveyValues.get("Q3n1c"));
		edtQ3n2c.setText(MainActivity.gblAllSurveyValues.get("Q3n2c"));
		edtQ3n3c.setText(MainActivity.gblAllSurveyValues.get("Q3n3c"));
		edtQ3n4c.setText(MainActivity.gblAllSurveyValues.get("Q3n4c"));
		edtQ3n5c.setText(MainActivity.gblAllSurveyValues.get("Q3n5c"));
		edtQ3o.setText(MainActivity.gblAllSurveyValues.get("Q3o"));
		edtQ3qi.setText(MainActivity.gblAllSurveyValues.get("Q3qi"));
		edtQ3qii.setText(MainActivity.gblAllSurveyValues.get("Q3qii"));
		edtQ3ri.setText(MainActivity.gblAllSurveyValues.get("Q3ri"));
		edtQ3rii.setText(MainActivity.gblAllSurveyValues.get("Q3rii"));
		edtQ3rv.setText(MainActivity.gblAllSurveyValues.get("Q3rv"));
		edtQ3sii.setText(MainActivity.gblAllSurveyValues.get("Q3sii"));
		
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
		   
//		   values.put("Q3a",String.valueOf(spnQ3a.getSelectedItemPosition()));
	//	 values.put("edtQ3c2",edtQ3c2.getText().toString());  
		   values.put("Q3n1a",String.valueOf(spnQ3n1a.getSelectedItemPosition()));
		   values.put("Q3n2a",String.valueOf(spnQ3n2a.getSelectedItemPosition()));
		   values.put("Q3n3a",String.valueOf(spnQ3n3a.getSelectedItemPosition()));
		   values.put("Q3n4a",String.valueOf(spnQ3n4a.getSelectedItemPosition()));
		   values.put("Q3n5a",String.valueOf(spnQ3n5a.getSelectedItemPosition()));
		   values.put("Q3n1b",String.valueOf(spnQ3n1b.getSelectedItemPosition()));
		   values.put("Q3n2b",String.valueOf(spnQ3n2b.getSelectedItemPosition()));
		   values.put("Q3n3b",String.valueOf(spnQ3n3b.getSelectedItemPosition()));
		   values.put("Q3n4b",String.valueOf(spnQ3n4b.getSelectedItemPosition()));
		   values.put("Q3n5b",String.valueOf(spnQ3n5b.getSelectedItemPosition()));
		   values.put("Q3p",String.valueOf(spnQ3p.getSelectedItemPosition()));
		   values.put("Q3q1",String.valueOf(spnQ3q1.getSelectedItemPosition()));
		   values.put("Q3q2",String.valueOf(spnQ3q2.getSelectedItemPosition()));
		   values.put("Q3q3",String.valueOf(spnQ3q3.getSelectedItemPosition()));
		   values.put("Q3r",String.valueOf(spnQ3r.getSelectedItemPosition()));
		   values.put("Q3riii",String.valueOf(spnQ3riii.getSelectedItemPosition()));
		   values.put("Q3riv",String.valueOf(spnQ3riii.getSelectedItemPosition()));
		   values.put("Q3s",String.valueOf(spnQ3s.getSelectedItemPosition()));
		   //values.put("Q3si",String.valueOf(spnQ3si.getSelectedItemPosition()));
	
		   values.put("Q3n1c",edtQ3n1c.getText().toString());
		   values.put("Q3n2c",edtQ3n2c.getText().toString());
		   values.put("Q3n3c",edtQ3n3c.getText().toString());
		   values.put("Q3n4c",edtQ3n4c.getText().toString());
		   values.put("Q3n5c",edtQ3n5c.getText().toString());
		   values.put("Q3o",edtQ3o.getText().toString());
		   values.put("Q3qi",edtQ3qi.getText().toString());
		   values.put("Q3qii",edtQ3qii.getText().toString());
		   values.put("Q3ri",edtQ3ri.getText().toString());
		   values.put("Q3rii",edtQ3rii.getText().toString());
		   values.put("Q3rv",edtQ3rv.getText().toString());
		   values.put("Q3sii",edtQ3sii.getText().toString());
		   
	
		   MainActivity.gblPartIIIbValues=values;
		   MainActivity.gblAllSurveyValues.putAll(values);
		   callBack.showNextSurveyForm(surveyPartIV);
		 
	   }
	   
	   
	   boolean validateData(){
		  
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
