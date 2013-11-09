package com.inovagro.inovagrofdc1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddFarmerDetailFragment extends Fragment implements OnClickListener, AdapterView.OnItemSelectedListener, InovagroConstants{
	/*
	 * To get GPS time: http://stackoverflow.com/questions/7017069/gps-time-in-android
	 * 
	 * To do:
	 * 
	 * generally, how to determine that the local db does not exits and therefore a sync is required
	 * how to preserver the local DB during sync, in case sync fails, so it is restored.
	 */
	
	GeneralCallbackIntefaces callBack;
	
	Button btnCancel;
	Button btnSubmit;
	Button btnFarmerPicture;
	Button btnID1Front;
	Button btnID1Back;
	Button btnID2Front;
	Button btnID2Back;
	Button btnCaptureGPS;
	
	Spinner spnProvince, spnDistrict, spnAdminPost, spnLocality, spnZone, spnFarmerGroup;
	//Spinner spnIDTypes;  //removed
	EditText edtSurname, edtForenames, edtFarmerReferenceNo, edtPhoneNo, edtDateofBirth,  edtHeadofHousehold,edtNumberOfDependents;
	EditText edtBirthCertificate;
	EditText edtTemporaryID;
	EditText edtNationalID;
	EditText edtVoterRegistrationCard;
	EditText edtIncomeTaxNo;
	EditText edtPassport;
	EditText edtDUAT;
	//EditText edtFarmerIDNO;  //removed
	EditText edtGPSLong;
	EditText edtGPSLat;
	
	TextView txtFarmerPicture;
	TextView txtID1Front;
	TextView txtID1Back;
	TextView txtID2Front;
	TextView txtID2Back; 
	
	
	RadioButton rdMale, rdFemale, rdHoHMale, rdHoHFemale, rdGroupLeaderYes, rdGroupLeaderNo;
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

	int EditMode =0;
	HashMap<String, String> FormData;
	//String [] DataOrder; //order in which data appears on the form
	AddFarmerDetailFragment(int EditMode, HashMap<String, String> FormData){
		this.EditMode=EditMode;
		this.FormData=FormData;
		
	}
	
	AddFarmerDetailFragment(){
		//this really does nothing. Just so previous code does not break.
		super();
	}
	
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		
	        View rootView = inflater.inflate(R.layout.frag_add_farmer,container, false);
	        btnCancel=(Button)rootView.findViewById(R.id.btnCancel);
	        btnCancel.setOnClickListener(this);
	        btnSubmit=(Button)rootView.findViewById(R.id.btnSubmit);
	        btnSubmit.setOnClickListener(this);
	    
	        btnFarmerPicture=(Button)rootView.findViewById(R.id.btnFarmerPicture);
	        btnID1Front=(Button)rootView.findViewById(R.id.btnID1Front);
	        btnID1Back=(Button)rootView.findViewById(R.id.btnID1Back);
	        btnID2Front=(Button)rootView.findViewById(R.id.btnID2Front);
	        btnID2Back=(Button)rootView.findViewById(R.id.btnID2Back);
	        btnCaptureGPS=(Button)rootView.findViewById(R.id.btnCaptureGPS);
	        btnFarmerPicture.setOnClickListener(this);
	        btnID1Front.setOnClickListener(this);
	        btnID1Back.setOnClickListener(this);
	        btnID2Front.setOnClickListener(this);
	        btnID2Back.setOnClickListener(this);
	        btnCaptureGPS.setOnClickListener(this);
	        
	        txtFarmerPicture=(TextView)rootView.findViewById(R.id.txtFarmerPicture);
	        txtID1Front=(TextView)rootView.findViewById(R.id.txtID1Front);
	        txtID1Back=(TextView)rootView.findViewById(R.id.txtID1Back);
	        txtID2Front=(TextView)rootView.findViewById(R.id.txtID2Front);
	        txtID2Back=(TextView)rootView.findViewById(R.id.txtID2Back);
	        
	        
	        edtSurname=(EditText)rootView.findViewById(R.id.edtSurname);
			edtForenames=(EditText)rootView.findViewById(R.id.edtForenames);
			edtFarmerReferenceNo=(EditText)rootView.findViewById(R.id.edtFarmerReferenceNo);
			edtPhoneNo=(EditText)rootView.findViewById(R.id.edtPhoneNo);
			edtDateofBirth=(EditText)rootView.findViewById(R.id.edtDateofBirth);
			//edtFarmerIDNO=(EditText)rootView.findViewById(R.id.edtFarmerIDNo);
			edtHeadofHousehold=(EditText)rootView.findViewById(R.id.edtHeadofHousehold);
			edtNumberOfDependents=(EditText)rootView.findViewById(R.id.edtNumberOfDependents);
			
			edtBirthCertificate=(EditText)rootView.findViewById(R.id.edtBirthCertificate);
			edtTemporaryID=(EditText)rootView.findViewById(R.id.edtTemporaryID);
			edtNationalID=(EditText)rootView.findViewById(R.id.edtNationalID);
			edtVoterRegistrationCard=(EditText)rootView.findViewById(R.id.edtVoterRegistrationCard);
			edtIncomeTaxNo=(EditText)rootView.findViewById(R.id.edtIncomeTaxNo);
			edtPassport=(EditText)rootView.findViewById(R.id.edtPassport);
			edtDUAT=(EditText)rootView.findViewById(R.id.edtDUAT);
			edtGPSLong=(EditText)rootView.findViewById(R.id.edtGPSLong);
	        edtGPSLat=(EditText)rootView.findViewById(R.id.edtGPSLat);
	        
	
			rdMale=(RadioButton)rootView.findViewById(R.id.rdMale);
			rdFemale=(RadioButton)rootView.findViewById(R.id.rdFemale);
			rdHoHMale=(RadioButton)rootView.findViewById(R.id.rdHoHMale);
			rdHoHFemale=(RadioButton)rootView.findViewById(R.id.rdHoHFemale);
			rdGroupLeaderYes=(RadioButton)rootView.findViewById(R.id.rdGroupLeaderYes);
			rdGroupLeaderNo=(RadioButton)rootView.findViewById(R.id.rdGroupLeaderNo);
			
	        spnProvince =(Spinner)rootView.findViewById(R.id.spnProvince);
	        spnDistrict =(Spinner)rootView.findViewById(R.id.spnDistrict);
	        spnAdminPost =(Spinner)rootView.findViewById(R.id.spnAdminPost);
	        spnLocality =(Spinner)rootView.findViewById(R.id.spnLocality);
	        spnZone =(Spinner)rootView.findViewById(R.id.spnZone);
	        spnFarmerGroup =(Spinner)rootView.findViewById(R.id.spnFarmerGroup);
	       // spnIDTypes=(Spinner)rootView.findViewById(R.id.spnIDTYpe);
	                
	        spnProvince.setOnItemSelectedListener(this);
	        spnDistrict.setOnItemSelectedListener(this);
	        spnAdminPost.setOnItemSelectedListener(this);
	        spnLocality.setOnItemSelectedListener(this);
	        spnZone.setOnItemSelectedListener(this);
	        spnFarmerGroup.setOnItemSelectedListener(this);
	      //  spnIDTypes.setOnItemSelectedListener(this);
	      
	        
	        SpinnerData s= new SpinnerData(getActivity());
	        alProvince=s.provinceData(-1); //assumes use of -1 for parent
	        aaProvince=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alProvince);	        
	        aaProvince.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	        spnProvince.setAdapter(aaProvince);
	     
	        /*
	        alIDTypes=s.IDTypesData(-1); //assumes use of -1 for parent
	        aaIDTypes=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alIDTypes);	        
	        aaIDTypes.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	        spnIDTypes.setAdapter(aaIDTypes);
	   */     
	        edtDateofBirth.setOnClickListener(this);
	        //set GPS values automatically
	        System.out.println("be4update gps in add farmer");
	        Log.v("addFarmerDetail", "abut to update gps");
	        if(EditMode==ADD_MODE ){
	        	showCurrentLocation();
	        	System.out.println("shd update gps");
	        	
	        }
	        
	        if(EditMode==EDIT_MODE || EditMode==VIEW_MODE){
	        	
	        	edtSurname.setText(FormData.get("Surname"));
	        	edtForenames.setText(FormData.get("ForeNames")); //test
	        	edtFarmerReferenceNo.setText(FormData.get("FarmerReferenceNo"));
	        	edtPhoneNo.setText(FormData.get("PhoneNo"));
	        	edtDateofBirth.setText(UtilityFunctions.SQLdateToAndroidStringDate(FormData.get("DateOfBirth")));
	        	System.out.println("dob="+FormData.get("DateOfBirth"));
	        	edtHeadofHousehold.setText(FormData.get("HeadOfHouseholdName"));
	        	edtNumberOfDependents.setText(FormData.get("NumberOfDependents"));
	        	edtBirthCertificate.setText(FormData.get("BirthCertificate"));
	        	edtTemporaryID.setText(FormData.get("TemporaryID"));
	        	edtNationalID.setText(FormData.get("NationalID"));
	        	edtVoterRegistrationCard.setText(FormData.get("VoterRegistrationCard"));
	        	edtIncomeTaxNo.setText(FormData.get("IncomeTaxNo"));
	        	edtPassport.setText(FormData.get("Passport"));
	        	edtDUAT.setText(FormData.get("DUAT"));
	        	edtGPSLong.setText(FormData.get("GPSLong"));
	        	edtGPSLat.setText(FormData.get("GPSLat"));
	        	
	        	txtFarmerPicture.setText(FormData.get("FarmerPicture"));
	        	txtID1Front.setText(FormData.get("ID1Front"));
	        	txtID1Back.setText(FormData.get("ID1Back"));
	        	txtID2Front.setText(FormData.get("ID2Front"));
	        	txtID2Back.setText(FormData.get("ID2Back"));
	        	
	        	edtDateofBirth.setText(FormData.get("DateofBirth"));
	        	System.out.println("FormData.get(Gender)__"+FormData.get("Gender") +"bool "+FormData.get("Gender").equals("1"));
	        		        	
	        	if (FormData.get("Gender").equals("1"))	
	        		rdMale.setChecked(true);
	        	else
	        		rdFemale.setChecked(true);
	        		
	        	if (FormData.get("IsGroupLeader").equals("1"))
	        		rdGroupLeaderYes.setChecked(true);
	        	else
	        		rdGroupLeaderNo.setChecked(true);
	        	if (FormData.get("HeadOfHouseholdGender").equals("1"))
	        		rdHoHMale.setChecked(true);
	        	else
	        		rdHoHFemale.setChecked(true);
	        	
	        	int position=UtilityFunctions.getSpinnerPosition(spnProvince, FormData.get("ProvinceID") );
	        	spnProvince.setSelection(position);
	        	//the other spinners are set in the OnItemSelected() function in an editMode block for each of them
			    
	        	
		        }
	        if(EditMode==VIEW_MODE){
//				//enable this for true view mode. For now, allow editing.	        	
//	        	edtSurname.setEnabled(false);
//	        	edtForenames.setEnabled(false);
//	        	edtFarmerReferenceNo.setEnabled(false);
//	        	edtPhoneNo.setEnabled(false);
//	        	edtDateofBirth.setEnabled(false);
//	        	edtHeadofHousehold.setEnabled(false);
//	        	edtNumberOfDependents.setEnabled(false);
//	        	edtBirthCertificate.setEnabled(false);
//	        	edtTemporaryID.setEnabled(false);
//	        	edtNationalID.setEnabled(false);
//	        	edtVoterRegistrationCard.setEnabled(false);
//	        	edtIncomeTaxNo.setEnabled(false);
//	        	edtPassport.setEnabled(false);
//	        	edtDUAT.setEnabled(false);
//	        	edtGPSLong.setEnabled(false);
//	        	edtGPSLat.setEnabled(false);
//	        	
//	        	
//	        	btnFarmerPicture.setEnabled(false);
//	        	btnID1Front.setEnabled(false);
//	        	btnID1Back.setEnabled(false);
//	        	btnID2Front.setEnabled(false);
//	        	btnID2Back .setEnabled(false);
	        	
	        	
	        	 
	        	
	        }
	        
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
		   if (v==edtDateofBirth ){
			   callBack.showDatePickerDialog((EditText)v);
			 //  new DatePickerFragment(edtVisitDate).show(getSupportFragmentManager(), "ChooseDate");
			   
			   
		   }
		   if (v==btnCaptureGPS){
				 showCurrentLocation(); //use this fxn to set the GPS coords in text boxes
			 }
			if (v==btnFarmerPicture){
				takePhoto(txtFarmerPicture, "fm_");
			}
			if (v==btnID1Front){
				takePhoto(txtID1Front, "id1fr_");
			}
			if (v==btnID1Back){
				takePhoto(txtID1Back, "id1bk_");
			}
			if (v==btnID2Front){
				takePhoto(txtID2Front, "id2fr_");
			}
			if (v==btnID2Back){
				takePhoto(txtID2Back, "id2bk_");
			}
	   }
	   
	   //for spinners
	   public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
			   //selection.setText(items[position]);
		   SpinnerData s= new SpinnerData(getActivity());
		   System.out.println("in onItemSelected_for_spinner");
		   if (parent==spnProvince){
			   
			   ComboRowData c=(ComboRowData)alProvince.get(position);
			   alDistrict=s.districtData(c.ID);  		        
		       aaDistrict=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alDistrict);
		       aaDistrict.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		       spnDistrict.setAdapter(aaDistrict);
		       if(EditMode==VIEW_MODE){
		    	   int pos=UtilityFunctions.getSpinnerPosition(spnDistrict, FormData.get("DistrictID") );		        	
		    	   spnDistrict.setSelection(pos, true);  //seems not to care what the value of position is!//===============		 			  
		      
		       }
			   
		       
		   }//spnProvince
		   if (parent==spnDistrict){
			   ComboRowData c=(ComboRowData)alDistrict.get(position);
			   alAdminPost=s.adminPostData(c.ID);  		        
		       aaAdminPost=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alAdminPost);
		       aaAdminPost.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		       spnAdminPost.setAdapter(aaAdminPost);	
		       if(EditMode==VIEW_MODE){
		    	   int pos=UtilityFunctions.getSpinnerPosition(spnAdminPost, FormData.get("AdminPostID") );
			       spnAdminPost.setSelection( pos);
			       }
		   }
		   if (parent==spnAdminPost){
			   ComboRowData c=(ComboRowData)alAdminPost.get(position);
			   alLocality=s.LocalityData(c.ID);  		        
		       aaLocality=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alLocality);
		       aaLocality.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		       spnLocality.setAdapter(aaLocality);
		       if(EditMode==VIEW_MODE){
		    	   int pos=UtilityFunctions.getSpinnerPosition(spnLocality, FormData.get("LocalityID") );
			       spnLocality.setSelection( pos);
		       }
		   }
		   if (parent==spnLocality){
			   ComboRowData c=(ComboRowData)alLocality.get(position);
			   alZone=s.zoneData(c.ID);  		        
		       aaZone=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alZone);
		       aaZone.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		       spnZone.setAdapter(aaZone);
		       if(EditMode==VIEW_MODE){
		    	   int pos=UtilityFunctions.getSpinnerPosition(spnZone, FormData.get("ZoneID") );
			       spnZone.setSelection( pos);
			       

		       }
		   }
		   if (parent==spnZone){
			   ComboRowData c=(ComboRowData)alZone.get(position);
			   alFarmerGroup=s.farmerGroupData(c.ID);  		        
		       aaFarmerGroup=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alFarmerGroup);
		       aaFarmerGroup.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
		       spnFarmerGroup.setAdapter(aaFarmerGroup);
		       if(EditMode==VIEW_MODE){
		    	   int pos=UtilityFunctions.getSpinnerPosition(spnFarmerGroup, FormData.get("FarmerGroupID") );
			       spnFarmerGroup.setSelection( pos);
		       }
		   }
	   }
	   public void onNothingSelected(AdapterView<?> parent) { 
		  // selection.setText("");
		}
			   
	   
	   private void fetchDataAndPost(){
		   System.out.println("Edit mode="+EditMode);
		   //alternative to ahve a single var to pass to post method
		   LinkedHashMap<String, String> values = new LinkedHashMap<String, String>();
		   //verify if the order is preserved:
		   if(EditMode==ADD_MODE){
			   System.out.println("in addfarmer-ADD mode currentFarmerID"+MainActivity.CurrentFarmerID);
			   values.put("FarmerID",UtilityFunctions.uniqueID(""));
		   }
		   else
		   {
			   System.out.println("in addfarmer-NOT add mode--currentFarmerID"+MainActivity.CurrentFarmerID);
			   values.put("FarmerID",MainActivity.CurrentFarmerID);
		   }
		   values.put("Surname",edtSurname.getText().toString());
		   values.put("ForeNames",edtForenames.getText().toString());
		   values.put("FarmerReferenceNo",edtFarmerReferenceNo.getText().toString());
		   values.put("PhoneNo",edtPhoneNo.getText().toString());
		   values.put("DateOfBirth",edtDateofBirth.getText().toString());
		   //values.put("FarmerIDNo",edtFarmerIDNO.getText().toString());
		   values.put("HeadOfHouseholdName",edtHeadofHousehold.getText().toString());
		   values.put("NumberOfDependents",edtNumberOfDependents.getText().toString());
		   values.put("Gender",rdMale.isChecked()?"1":"0");
		   values.put("HeadOfHouseholdGender",rdHoHMale.isChecked()?"1":"0");
		   values.put("IsGroupLeader",rdGroupLeaderYes.isChecked()?"1":"0");
		   values.put("UserId",Integer.toString(Login.UserID));
		   
		   values.put("BirthCertificate",edtBirthCertificate.getText().toString());
		   values.put("TemporaryID",edtTemporaryID.getText().toString());
		   values.put("NationalID",edtNationalID.getText().toString());
		   values.put("VoterRegistrationCard",edtVoterRegistrationCard.getText().toString());
		   values.put("IncomeTaxNo",edtIncomeTaxNo.getText().toString());
		   values.put("Passport",edtPassport.getText().toString());
		   values.put("DUAT",edtDUAT.getText().toString());
		   
		   values.put("FarmerPicture",txtFarmerPicture.getText().toString());
		   values.put("ID1Front",txtID1Front.getText().toString());
		   values.put("ID1Back",txtID1Back.getText().toString());
		   values.put("ID2Front",txtID2Front.getText().toString());
		   values.put("ID2Back",txtID2Back.getText().toString());
		   values.put("GPSLong",edtGPSLong.getText().toString());
		   values.put("GPSLat",edtGPSLat.getText().toString());
		   
		   Calendar cal= Calendar.getInstance();
      	   	long timeStamp=cal.getTimeInMillis();
      	   	
      	   //uf.dateToStringTimeStamp(timeStamp);
      	 values.put("MobileTimeStamp",UtilityFunctions.dateToStringTimeStamp(timeStamp));
		   //add time stamp (local time)
		   
		   
		   ComboRowData c;
		   //int index;
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
		  // c=(ComboRowData)alIDTypes.get(spnIDTypes.getSelectedItemPosition());		   
		  // values.put("IDType",String.valueOf(c.ID));
	

		   LinkedHashMap<String, String> rawFormData=new LinkedHashMap<String, String> ();
		   //add teh data here, call the confirmation form to show it
		   
		   
		   
		   callBack.showConfirmationForm(values,actionPOST_ADD_FARMER );  //eventually, this will be the rawFormData as well as values
		   //now use call back to send the post
		   ///callBack.postData(actionPOST_ADD_FARMER, values);   //original that works 
	   }
	   
	   protected void showCurrentLocation() {
	       String[] longLat=callBack.getCurrentLocation();
	       if (longLat!=null){
	       edtGPSLong.setText(longLat[0]);
	       edtGPSLat.setText(longLat[1]);
	       }
	       else {
	    	   Toast.makeText(getActivity(), "AddFarmer: No GPS data", Toast.LENGTH_LONG).show();
	       
	       }
	       
	    }  
	 
	   
	   protected void takePhoto(View v, String prefix){
			  callBack.takePhoto(this,  v,prefix);

		  }
		  public void setPhotoPath(String path, View v){
			  
			  if (v==btnFarmerPicture){
				  txtFarmerPicture.setText(path);
			  }
			  if (v==btnID1Front){
				  txtID1Front.setText(path);
			  }
			  if (v==btnID1Back){
				  txtID1Back.setText(path);
			  }
			  if (v==btnID2Front){
				  txtID2Front.setText(path);
			  }
			  if (v==btnID2Back){
				  txtID2Back.setText(path);
			  }
		  }
		  
	   
	   boolean validateData(){
		   //edtSurname, edtForenames, edtFarmerReferenceNo
		   if (edtSurname.getText().toString().trim().equals("")){
			  Toast.makeText(getActivity(), "Surname is blank", Toast.LENGTH_LONG ).show();
			   edtSurname.requestFocus();
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
	/*	  
		  public LinkedHashMap<String, String> getValuesEntered(){
			  //View rootView = inflater.inflate(R.layout.frag_add_farmer,container, false);
		       
		        
			  LayoutInflater inflater = getLayoutInflater(null);
			  LinearLayout l = (LinearLayout) inflater.inflate(R.layout.frag_add_farmer, null);

			  ViewGroup Current_Widget = (ViewGroup)l.getRootView();

			  for (int i = 0; i < Current_Widget.getChildCount(); i++)
			      Current_Widget.getChildAt(i);
		  return null;
		  }
*/
}
