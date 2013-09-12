package com.inovagro.inovagrofdc1;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SurveyBackgroundFragment extends Fragment implements OnClickListener,  AdapterView.OnItemSelectedListener, InovagroConstants{

	GeneralCallbackIntefaces callBack;
	
	Button btnCancel;
	Button btnNext, btnQ1c, btnQ1d, btnQ1d2;
	
	Spinner spnProvince, spnDistrict, spnAdminPost, spnLocality, spnZone, spnFarmerGroup;
	Spinner spnQ1b,
			spnQ1d,
			spnQ1g,
			spnQ1h,
			spnQ1j;
	//TextView txtFarmerName, txtFarmerID;
	EditText
			edtQ1a1,
			edtQ1a2,
			edtQ1g,
			edtQ1i1,
			edtQ1i2,
			edtQ1i3,
			edtQ1i4,
			edtQ1i5,
			edtQ1i1b,
			edtQ1i2b,
			edtQ1i3b,
			edtQ1i4b,
			edtQ1i5b,
			edtQ1k;
	TextView txtQ1c, txtQ1d1, txtQ1d12;
	
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
	
	ArrayAdapter<String> aaspnQ1b=null;
	ArrayAdapter<String> aaspnQ1d=null;
	ArrayAdapter<String> aaspnQ1h=null;
	ArrayAdapter<String> aaspnQ1g=null;
	ArrayAdapter<String> aaspnQ1j=null;
	
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
        View rootView = inflater.inflate(R.layout.frag_survey_backgroundinfo,container, false);
        btnCancel=(Button)rootView.findViewById(R.id.btnPrevious);
        btnCancel.setOnClickListener(this);
        btnNext=(Button)rootView.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);
        btnQ1c=(Button)rootView.findViewById(R.id.btnQ1c);
        btnQ1c.setOnClickListener(this);
        btnQ1d=(Button)rootView.findViewById(R.id.btnQ1d);
        btnQ1d.setOnClickListener(this);
        btnQ1d2=(Button)rootView.findViewById(R.id.btnQ1d2);
        btnQ1d2.setOnClickListener(this);
        
        txtQ1c=(TextView)rootView.findViewById(R.id.txtQ1c);
        txtQ1d1=(TextView)rootView.findViewById(R.id.txtQ1d1);
        txtQ1d12=(TextView)rootView.findViewById(R.id.txtQ1d12);
        
        Resources res = getResources();
		String [] arQ1b = res.getStringArray(R.array.A1b);
		aaspnQ1b=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ1b);		
		aaspnQ1b.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ1b=(Spinner)rootView.findViewById(R.id.spnQ1b);
		spnQ1b.setAdapter(aaspnQ1b);
		
		String [] arQ1d = res.getStringArray(R.array.A1d);
		aaspnQ1d=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ1d);		
		aaspnQ1d.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ1d=(Spinner)rootView.findViewById(R.id.spnQ1d);
		spnQ1d.setAdapter(aaspnQ1d);
		
		String [] arQ1g = res.getStringArray(R.array.A1g);
		aaspnQ1g=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ1g);		
		aaspnQ1g.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ1g=(Spinner)rootView.findViewById(R.id.spnQ1g);
		spnQ1g.setAdapter(aaspnQ1g);
		
		String [] arQ1h = res.getStringArray(R.array.A1h);
		aaspnQ1h=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ1h);		
		aaspnQ1h.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ1h=(Spinner)rootView.findViewById(R.id.spnQ1h);
		spnQ1h.setAdapter(aaspnQ1h);
		
		String [] arQ1j = res.getStringArray(R.array.A1j);
		aaspnQ1j=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arQ1j);		
		aaspnQ1j.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		spnQ1j=(Spinner)rootView.findViewById(R.id.spnQ1j);
		spnQ1j.setAdapter(aaspnQ1j);
		
        //edtFarmName=(EditText)rootView.findViewById(R.id.edtFarmName);
		edtQ1a1=(EditText)rootView.findViewById(R.id.edtQ1a1);
		edtQ1a2=(EditText)rootView.findViewById(R.id.edtQ1a2);
		//edtQ1g=(EditText)rootView.findViewById(R.id.edtQ1g); //yet to be added
		edtQ1i1=(EditText)rootView.findViewById(R.id.edtQ1i1);
		edtQ1i2=(EditText)rootView.findViewById(R.id.edtQ1i2);
		edtQ1i3=(EditText)rootView.findViewById(R.id.edtQ1i3);
		edtQ1i4=(EditText)rootView.findViewById(R.id.edtQ1i4);
		edtQ1i5=(EditText)rootView.findViewById(R.id.edtQ1i5);
		
		edtQ1i1b=(EditText)rootView.findViewById(R.id.edtQ1i1b);
		edtQ1i2b=(EditText)rootView.findViewById(R.id.edtQ1i2b);
		edtQ1i3b=(EditText)rootView.findViewById(R.id.edtQ1i3b);
		edtQ1i4b=(EditText)rootView.findViewById(R.id.edtQ1i4b);
		edtQ1i5b=(EditText)rootView.findViewById(R.id.edtQ1i5b);
		
		edtQ1k=(EditText)rootView.findViewById(R.id.edtQ1k);
		
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

	@Override
	public void onClick(View v) {
		 if (v==btnCancel){
			   getFragmentManager().popBackStack();
		   }
		 if (v==btnNext){
			 if (!validateData()) //if validation fails, exit
				 return;
			 fetchDataAndGoNext();
		   }
		if (v==btnQ1c){
			takePhoto(txtQ1c, "sv_fm");
		}
		if (v==btnQ1d){
			takePhoto(txtQ1d1,"sv_id");
		}
		if (v==btnQ1d2){
			takePhoto(txtQ1d12,"sv_id");
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
	
	   private void fetchDataAndGoNext(){
			
		   //alternative to ahve a single var to pass to post method
		   HashMap<String, String> values = new HashMap<String, String>();
		   //list of fields
		   		   
		   values.put("UserID",Integer.toString(Login.UserID));
		   
		   ComboRowData c;
		   /*
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
		   */
		 
		   c=(ComboRowData)alProvince.get(spnProvince.getSelectedItemPosition());		   
		   values.put("Q1f0",String.valueOf(c.ID));
		   c=(ComboRowData)alDistrict.get(spnDistrict.getSelectedItemPosition());		   
		   values.put("Q1f1",String.valueOf(c.ID));
		   c=(ComboRowData)alAdminPost.get(spnAdminPost.getSelectedItemPosition());		   
		   values.put("Q1f2",String.valueOf(c.ID));
		   c=(ComboRowData)alLocality.get(spnLocality.getSelectedItemPosition());		   
		   values.put("Q1f3",String.valueOf(c.ID));
		   c=(ComboRowData)alZone.get(spnZone.getSelectedItemPosition());		   
		   values.put("Q1f4",String.valueOf(c.ID));
		   c=(ComboRowData)alFarmerGroup.get(spnFarmerGroup.getSelectedItemPosition());		   
		   values.put("Q1f5",String.valueOf(c.ID));
		   
		   
		   values.put("Q1b",String.valueOf(spnQ1b.getSelectedItemPosition()));
		   values.put("Q1d",String.valueOf(spnQ1d.getSelectedItemPosition()));
		   values.put("Q1g",String.valueOf(spnQ1g.getSelectedItemPosition()));
		   values.put("Q1h",String.valueOf(spnQ1h.getSelectedItemPosition()));
		   values.put("Q1j",String.valueOf(spnQ1j.getSelectedItemPosition()));
		   //Log.v("q1b value=",String.valueOf(spnQ1b.getSelectedItemPosition()));
		   values.put("Q1a1",edtQ1a1.getText().toString());
		   values.put("Q1a2",edtQ1a2.getText().toString());
		   values.put("Q1i1",edtQ1i1.getText().toString());
		   values.put("Q1i2",edtQ1i2.getText().toString());
		   values.put("Q1i3",edtQ1i3.getText().toString());
		   values.put("Q1i4",edtQ1i4.getText().toString());
		   values.put("Q1i5",edtQ1i5.getText().toString());
		   values.put("Q1i1b",edtQ1i1b.getText().toString());
		   values.put("Q1i2b",edtQ1i2b.getText().toString());
		   values.put("Q1i3b",edtQ1i3b.getText().toString());
		   values.put("Q1i4b",edtQ1i4b.getText().toString());
		   values.put("Q1i5b",edtQ1i5b.getText().toString());
		   values.put("Q1k",edtQ1k.getText().toString());
		   
		   values.put("Q1c",txtQ1c.getText().toString());
		   values.put("Q1d1",txtQ1d1.getText().toString());
		   values.put("Q1d12",txtQ1d12.getText().toString());
		   
		   MainActivity.gblPartIValues.putAll(values);
		   MainActivity.gblAllSurveyValues.putAll(values);
		   callBack.showNextSurveyForm(surveyPartII);
		   //do i need to keep the values selected?
		   
		   //todo: now use call back to send the post
		   //callBack.postData(action___, values);
	   }
	   
	   
	   boolean validateData(){
		   //edtSurname, edtForenames, edtFarmerReferenceNo
		   if (edtQ1a1.getText().toString().trim().equals("")){
			  Toast.makeText(getActivity(), "Surname is blank", Toast.LENGTH_LONG ).show();
			  edtQ1a1.requestFocus();
			   return false;
		   }
		   if (edtQ1a2.getText().toString().trim().equals("")){
				  Toast.makeText(getActivity(), "Name is blank", Toast.LENGTH_LONG ).show();
				  edtQ1a2.requestFocus();
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
	   
	   protected void takePhoto(View v, String prefix){
			  callBack.takePhoto(this,  v,prefix);

		  }
		  public void setPhotoPath(String path, View v){
			  
			  if (v==btnQ1c){
				  txtQ1c.setText(path);
			  }
			  if (v==btnQ1d){
				  txtQ1d1.setText(path);
			  }
			  if (v==btnQ1d2){
				  txtQ1d12.setText(path);
			  }
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
