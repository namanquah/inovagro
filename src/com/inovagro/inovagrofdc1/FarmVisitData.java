package com.inovagro.inovagrofdc1;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

public class FarmVisitData extends Fragment implements OnClickListener, AdapterView.OnItemSelectedListener,InovagroConstants{
	GeneralCallbackIntefaces callBack=null;
	
	/*
	 * todo: below is all done. Testing pending of saving farm visit data. 
	 * 1. see if the data inspiners load correctl.
	 * 2. Note however that saving will be a challenge (not done yet)
	 * 3. more importantly, this form must be called with teh correct FarmYearlyDataID (and VisitType)
	 * 
	 * 
	 * to do:
	 * need to load data into the spinners. Looks like backed for spinners in Spinner data as well as in DB needs to be done.
	 * Currently, code has beeen writtent to allow fetch of data for posting.
	 * For immediate testing, comment out the fecthc and post code.
	 * Next steps: 1. declear supproting array lists
	 * 2. do backend PHP to fetch and DBAdapbter code to save that data to the local db (in the doSYNC fxn)
	 * 
	 * 
	 * Started work on Location/GPS
	 */
	

	
	EditText edtVisitDate,edtScheduledDate,edtDateStarted,edtDateCompleted,edtGPSLong,edtGPSLat,edtHA_KG_SR_GP,
		edtPercentTotalLand, edtPersonMet;
	TextView lblHA_KG_SR_GP,lblCropType,lblSeedVariety,lblPercentTotalLand,lblPicture,
		lblDateStarted, lblDateCompleted, lblScheduledDate, lblServiceProvider, lblServiceProviderType;
	Spinner spnCropType,spnSeedVariety,spnServiceProviderType, spnServiceProvider;
	//MultiAutoCompleteTextView multACTVComments; this was changed midway in the project to a multi-line edt	
	EditText multACTVComments;
	Button btnSubmit,btnCancel, btnPicture, btnCaptureGPS;
	
	//variables to hold spinner values
	ArrayList<ComboRowData> alCropType=null;
	ArrayList<ComboRowData> alSeedVariety=null;
	ArrayList<ComboRowData> alServiceProviderType=null;
	ArrayList<ComboRowData> alServiceProvider=null;
	
	ArrayAdapter<ComboRowData> aaCropType=null;
	ArrayAdapter<ComboRowData> aaSeedVariety=null;
	ArrayAdapter<ComboRowData> aaServiceProviderType=null;
	ArrayAdapter<ComboRowData> aaServiceProvider=null;
	
	
	//variables to hold the values
	
	int FarmYearlyDataID;
	int VisitType;
	public FarmVisitData(int FarmYearlyDataID, int VisitType){
		super();
		this.FarmYearlyDataID=FarmYearlyDataID;
		this.VisitType=VisitType;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
        View rootView = inflater.inflate(R.layout.frag_farm_visit,container, false);        
        //inflate all the controls
        //HA_KG=(TextView)rootView.findViewById(R.id.lblHA_KG_SR_GP);
        
        
       
        
        edtVisitDate=(EditText)rootView.findViewById(R.id.edtVisitDate);
        edtScheduledDate=(EditText)rootView.findViewById(R.id.edtScheduledDate);
        edtDateStarted=(EditText)rootView.findViewById(R.id.edtDateStarted);
        edtDateCompleted=(EditText)rootView.findViewById(R.id.edtDateCompleted);
        edtGPSLong=(EditText)rootView.findViewById(R.id.edtGPSLong);
        edtGPSLat=(EditText)rootView.findViewById(R.id.edtGPSLat);
        edtHA_KG_SR_GP=(EditText)rootView.findViewById(R.id.edtHA_KG_SR_GP);
        edtPercentTotalLand=(EditText)rootView.findViewById(R.id.edtPercentTotalLand );        
        edtPersonMet=(EditText)rootView.findViewById(R.id.edtPersonMet );
        multACTVComments=(EditText)rootView.findViewById(R.id.multACTVComments);
        
        edtVisitDate.setOnClickListener(this);
        edtVisitDate.setOnClickListener(this);
        edtScheduledDate.setOnClickListener(this);
        edtDateStarted.setOnClickListener(this);
        edtDateCompleted.setOnClickListener(this);
        
        
        lblDateStarted=(TextView)rootView.findViewById(R.id.lblDateStarted);
        lblDateCompleted=(TextView)rootView.findViewById(R.id.lblDateCompleted);
        lblScheduledDate=(TextView)rootView.findViewById(R.id.lblScheduledDate);
        lblHA_KG_SR_GP=(TextView)rootView.findViewById(R.id.lblHA_KG_SR_GP);
        lblCropType=(TextView)rootView.findViewById(R.id.lblCropType);
        lblPercentTotalLand=(TextView)rootView.findViewById(R.id.lblPercentTotalLand);
        lblSeedVariety=(TextView)rootView.findViewById(R.id.lblSeedVariety);
        lblPicture=(TextView)rootView.findViewById(R.id.lblPicture);
        lblServiceProviderType=(TextView)rootView.findViewById(R.id.lblServiceProviderType);
        lblServiceProvider=(TextView)rootView.findViewById(R.id.lblServiceProvider);

        spnCropType=(Spinner)rootView.findViewById(R.id.spnCropType);
        spnServiceProviderType=(Spinner)rootView.findViewById(R.id.spnServiceProviderType);
        spnServiceProvider=(Spinner)rootView.findViewById(R.id.spnServiceProvider);
        spnSeedVariety=(Spinner)rootView.findViewById(R.id.spnSeedVariety);
                
        spnCropType.setOnItemSelectedListener(this);
        spnServiceProviderType.setOnItemSelectedListener(this);
        //----
        
        btnSubmit=(Button)rootView.findViewById(R.id.btnSubmit);
        btnCancel=(Button)rootView.findViewById(R.id.btnCancel);
        btnPicture=(Button)rootView.findViewById(R.id.btnPicture);
        btnCaptureGPS=(Button)rootView.findViewById(R.id.btnCaptureGPS);
        
        btnCancel.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnPicture.setOnClickListener(this);
        btnCaptureGPS.setOnClickListener(this);
      //setup the spinners 
        
        SpinnerData s= new SpinnerData(getActivity());
        alCropType=s.CropTypeData(-1); //assumes use of -1 for parent
        aaCropType=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alCropType);	        
        aaCropType.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnCropType.setAdapter(aaCropType);
       /* 
        alSeedVariety=s.SeedVarietyData(-1); //assumes use of -1 for parent
        aaSeedVariety=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alSeedVariety);	        
        aaSeedVariety.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnSeedVariety.setAdapter(aaSeedVariety);
        */
        alServiceProviderType=s.ServiceProviderTypeData(-1); //assumes use of -1 for parent
        aaServiceProviderType=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alServiceProviderType);	        
        aaServiceProviderType.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnServiceProviderType.setAdapter(aaServiceProviderType);
        /*
        alServiceProvider=s.ServiceProviderData(-1); //assumes use of -1 for parent
        aaServiceProvider=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alServiceProvider);	        
        aaServiceProvider.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spnServiceProvider.setAdapter(aaServiceProvider);
        */
        
        //customize the look depending on visit type
        switch (VisitType){
        	
        case  visitMANUAL_LAND_PREP:
        case  visitDISKING:
        case  visitPLOUGH:
        case  visitRIP:
        	lblHA_KG_SR_GP.setText("HA");
        	lblCropType.setVisibility(View.GONE);
        	spnCropType.setVisibility(View.GONE);
        	lblSeedVariety.setVisibility(View.GONE);
        	spnSeedVariety.setVisibility(View.GONE);
        	
    		break;
        case  visitSEED_DISTRIBUTION:
        	lblHA_KG_SR_GP.setText("KG");
        	lblCropType.setVisibility(View.GONE);
        	spnCropType.setVisibility(View.GONE);
        	lblSeedVariety.setVisibility(View.GONE);
        	spnSeedVariety.setVisibility(View.GONE);        	
    		break;
    		
        case  visitPLANTING:
        	lblHA_KG_SR_GP.setText("HA");
        	lblPercentTotalLand.setVisibility(View.GONE);
        	edtPercentTotalLand.setVisibility(View.GONE);
    		break;
        case  visitGERMINATION:
        	lblDateStarted.setVisibility(View.GONE);
        	lblDateCompleted.setVisibility(View.GONE);
        	edtDateStarted.setVisibility(View.GONE);
        	edtDateCompleted.setVisibility(View.GONE);
        	lblHA_KG_SR_GP.setText("Germination Percentagae");
        	lblCropType.setVisibility(View.GONE);
        	spnCropType.setVisibility(View.GONE);
        	lblSeedVariety.setVisibility(View.GONE);
        	spnSeedVariety.setVisibility(View.GONE);
        	break;
        	
        case  visitWEED1:
        case  visitWEED2:
        case  visitWEED3:
        	lblDateStarted.setVisibility(View.GONE);
        	lblDateCompleted.setVisibility(View.GONE);
        	edtDateStarted.setVisibility(View.GONE);
        	edtDateCompleted.setVisibility(View.GONE);
        	lblHA_KG_SR_GP.setVisibility(View.GONE);
        	edtHA_KG_SR_GP.setVisibility(View.GONE);
        	lblCropType.setVisibility(View.GONE);
        	spnCropType.setVisibility(View.GONE);
        	lblSeedVariety.setVisibility(View.GONE);
        	spnSeedVariety.setVisibility(View.GONE);
        	lblPercentTotalLand.setVisibility(View.GONE);
        	edtPercentTotalLand.setVisibility(View.GONE);
        	break;

        case visitAD_HOC:  
        case  visitHARVESTYIELD:
        case  visitTHRESHINGINFO:
        case  visitDEMO_PLOT_VISIT:
    	case  visitYIELD_FORECAST:
        	lblHA_KG_SR_GP.setText("KG");
        	lblCropType.setVisibility(View.GONE);
        	spnCropType.setVisibility(View.GONE);
        	lblSeedVariety.setVisibility(View.GONE);
        	spnSeedVariety.setVisibility(View.GONE);  
        	lblPercentTotalLand.setVisibility(View.GONE);
        	edtPercentTotalLand.setVisibility(View.GONE);
        	lblDateStarted.setVisibility(View.GONE);
        	lblDateCompleted.setVisibility(View.GONE);
        	edtDateStarted.setVisibility(View.GONE);
        	edtDateCompleted.setVisibility(View.GONE);
        	edtScheduledDate.setVisibility(View.GONE);
        	lblScheduledDate.setVisibility(View.GONE);
        	lblServiceProvider.setVisibility(View.GONE);
        	lblServiceProviderType.setVisibility(View.GONE);
        	spnServiceProvider.setVisibility(View.GONE);
        	spnServiceProviderType.setVisibility(View.GONE);
        	
    		break;
        }
       // lblPicture.setVisibility(View.GONE);
       // btnPicture.setVisibility(View.GONE);
        return rootView;
    }
   
   public void onClick(View v){
	   if (v==btnCancel){
		   getFragmentManager().popBackStack();
		   return;
	   }
	   if (v==btnSubmit){
		   //collect form data, submit via http to remote server or local db
		   collectFormData();
		   
		   return;
	   }
	   if ( v==edtVisitDate || v == edtScheduledDate || v== edtDateStarted || v== edtDateCompleted ){
		   callBack.showDatePickerDialog((EditText)v);
		 //  new DatePickerFragment(edtVisitDate).show(getSupportFragmentManager(), "ChooseDate");
	   }
	   if (v==btnCaptureGPS){
			 showCurrentLocation(); //use this fxn to set the GPS coords in text boxes
		 }
	   if (v==btnPicture){
		   takePhoto();
	   }
   }//onClick
/*
   public boolean onTouch(View v, MotionEvent event) {
	   if ( v==edtVisitDate || v == edtScheduledDate || v== edtDateStarted || v== edtDateCompleted ){
		   callBack.showDatePickerDialog((EditText)v);
		 //  new DatePickerFragment(edtVisitDate).show(getSupportFragmentManager(), "ChooseDate");
	   }
	return true;   
   }
   */
   //for spinners
   public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
		   //selection.setText(items[position]);
	   SpinnerData s= new SpinnerData(getActivity());
	   
	   if (parent==spnCropType){
		   
		   ComboRowData c=(ComboRowData)alCropType.get(position);
	       alSeedVariety=s.SeedVarietyData(c.ID); 
	       aaSeedVariety=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alSeedVariety);	        
	       aaSeedVariety.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	       spnSeedVariety.setAdapter(aaSeedVariety);
	       
	   }//spnCropType
	   
	   
	   if (parent==spnServiceProviderType){
		   ComboRowData c=(ComboRowData)alServiceProviderType.get(position);
	       alServiceProvider=s.ServiceProviderData(c.ID); 
	       aaServiceProvider=new ArrayAdapter<ComboRowData>(getActivity(),android.R.layout.simple_spinner_item,alServiceProvider);	        
	       aaServiceProvider.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
	       spnServiceProvider.setAdapter(aaServiceProvider);
	   }
	   
	   
	   
   }
   public void onNothingSelected(AdapterView<?> parent) { 
	  // selection.setText("");
	}
 
   private void collectFormData(){
	   //collect all form data and build URL. Posting? or getting?
	   //POST!
	   //declare all variables globally
	   /*
	    * String edtDateStartedVal;
	   if (edtDateStarted.getVisibility()!=View.GONE){
		   edtDateStartedVal=edtDateStarted.getText().toString();
	   }
	   */
	   HashMap<String, String> values = new HashMap<String, String>();
	   //check visibility before reading value
	   Calendar cal= Calendar.getInstance();
	   	long timeStamp=cal.getTimeInMillis();
	   	String uniqueID=""+timeStamp+"_"+Login.UserID;

	   	values.put("VisitID", uniqueID);
	   values.put("VisitTypeID",Integer.toString(VisitType));
	   values.put("FarmYearlyDataID",Integer.toString(FarmYearlyDataID));
	   values.put("VisitDate",edtVisitDate.getText().toString());
	   if (edtScheduledDate.getVisibility()!=View.GONE)
		   values.put("ScheduledDate",edtScheduledDate.getText().toString());
	   if (edtDateStarted.getVisibility()!=View.GONE)
		   values.put("DateStarted",edtDateStarted.getText().toString());
	   if (edtDateCompleted.getVisibility()!=View.GONE)
			values.put("DateCompleted",edtDateCompleted.getText().toString());
	   if (edtGPSLong.getVisibility()!=View.GONE)
			values.put("GPSLong",edtGPSLong.getText().toString());
	   if (edtGPSLat.getVisibility()!=View.GONE)
			values.put("GPSLat",edtGPSLat.getText().toString());
	   //saveEntryDate on server side
	   //fetch global UserID
	   
		values.put("UserID",Integer.toString(Login.UserID));
	   if (edtHA_KG_SR_GP.getVisibility()!=View.GONE)
			values.put("HA_KG_SR_GP",edtHA_KG_SR_GP.getText().toString());
	   if (edtPercentTotalLand.getVisibility()!=View.GONE)
			values.put("PercentageTotalLand",edtPercentTotalLand.getText().toString());
	   //PersonMet
	   if (multACTVComments.getVisibility()!=View.GONE)
			values.put("Comments",multACTVComments.getText().toString());
	   //PicturePath--lblPicture
	   if (lblPicture.getVisibility()!=View.GONE)
			values.put("PicturePath",lblPicture.getText().toString());
	   ComboRowData c;
	   if (spnCropType.getVisibility()!=View.GONE){
			c=(ComboRowData)alCropType.get(spnCropType.getSelectedItemPosition());		   
			values.put("CropTypeID",String.valueOf(c.ID));
	   }
	   if (spnSeedVariety.getVisibility()!=View.GONE){
			c=(ComboRowData)alSeedVariety.get(spnSeedVariety.getSelectedItemPosition());		   
	   		values.put("SeedVarietyID",String.valueOf(c.ID));
	   }
	   if (spnServiceProviderType.getVisibility()!=View.GONE){
			c=(ComboRowData)alServiceProviderType.get(spnServiceProviderType.getSelectedItemPosition());		   
	   	values.put("ServiceProviderTypeID",String.valueOf(c.ID));
   		}
	   if (spnServiceProvider.getVisibility()!=View.GONE){
			c=(ComboRowData)alServiceProvider.get(spnServiceProvider.getSelectedItemPosition());		   
			values.put("ServiceProviderID",String.valueOf(c.ID));
	   }
	  
	   callBack.postData(VisitType, values); //the POST data shd be able to deal with either "VisitType" or actionPOST___ constants
	   
   }
	  
   
   //deal with interface
	  @Override
	  public void onAttach(Activity activity) {
	      super.onAttach(activity);
	      if (!(activity instanceof GeneralCallbackIntefaces)) {
	          throw new IllegalStateException(
	                  "Activity must implement fragment's callbacks.");
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
	    	   Toast.makeText(getActivity(), "FarmVisit: No GPS data", Toast.LENGTH_LONG).show();
	       
	       }
	       
	    }  
	  protected void takePhoto(){
		  callBack.takePhoto(this);

	  }
	  public void setPhotoPath(String path){
		  lblPicture.setText(path);
	  }
	  

}
