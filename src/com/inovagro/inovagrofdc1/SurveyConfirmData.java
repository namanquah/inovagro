package com.inovagro.inovagrofdc1;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SurveyConfirmData extends Fragment implements OnClickListener,   InovagroConstants {
	
	GeneralCallbackIntefaces callBack;
	
	Button btnPrevious;
	Button btnSubmit;
	Button btnCancel;
	TextView txtSummary;
	HashMap<String, String>values=null;
	HashMap<String, String>allValues=null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	 View rootView = inflater.inflate(R.layout.frag_survey_confirm_data,container, false);
     btnPrevious=(Button)rootView.findViewById(R.id.btnPrevious);
     btnPrevious.setOnClickListener(this);
     btnSubmit=(Button)rootView.findViewById(R.id.btnSubmit);
     btnSubmit.setOnClickListener(this);
     btnCancel=(Button)rootView.findViewById(R.id.btnCancel);
     btnCancel.setOnClickListener(this);
     
     //Resources res = getResources();  
     String s="";
     String EoL="<br />";
     values=MainActivity.gblPartIValues;
     s+="<b>Part I- Background Info</b><br>";
     for (Map.Entry<String, String> entry: values.entrySet()){
			String key=entry.getKey();
			String value =entry.getValue();
			s+="<b>"+key+":</b> "+value+EoL;
     }
     s+="<b>Part II- Assets Owned</b><br>";
     values=MainActivity.gblPartIIValues;
     for (Map.Entry<String, String> entry: values.entrySet()){
			String key=entry.getKey();
			String value =entry.getValue();
			s+="<b>"+key+":</b> "+value+EoL;
     }
     s+="<b>Part IIIa- Production DB1</b></br>";
     values=MainActivity.gblPartIIIaValues;
     for (Map.Entry<String, String> entry: values.entrySet()){
			String key=entry.getKey();
			String value =entry.getValue();
			s+="<b>"+key+":</b> "+value+EoL;
     }
     s+="<b>Part IIIb- Production DB2</b></br>";
     values=MainActivity.gblPartIIIbValues;
     for (Map.Entry<String, String> entry: values.entrySet()){
			String key=entry.getKey();
			String value =entry.getValue();
			s+="<b>"+key+":</b> "+value+EoL;
     }
     s+="<b>Part IV- Market Trade/Credit</b></br>";
     values=MainActivity.gblPartIVValues;
     for (Map.Entry<String, String> entry: values.entrySet()){
			String key=entry.getKey();
			String value =entry.getValue();
			s+="<b>"+key+":</b> "+value+EoL;
     }
     
     txtSummary=(TextView)rootView.findViewById(R.id.txtSummary);
     txtSummary.setText(Html.fromHtml(s));
     return rootView;
    }
	
	
	@Override
	public void onClick(View v) {
		 if (v==btnPrevious){
			   getFragmentManager().popBackStack();
		   }
		 if (v==btnSubmit){
			 postData();
			  
		   }
		 if (v==btnCancel){
			 /*  //note: this also works correctly!
			  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			    builder.setTitle("Confirm");
			    builder.setMessage("Are you sure?");

			    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

			        public void onClick(DialogInterface dialog, int which) {
			            // Do the action & close -->
			        	getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
			            dialog.dismiss();
			        }

			    });

			    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

			        @Override
			        public void onClick(DialogInterface dialog, int which) {
			            // Do nothing
			            dialog.dismiss();
			        }
			    });
			    AlertDialog alert = builder.create();
			    alert.show();
			 */
			 DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				    @Override
				    public void onClick(DialogInterface dialog, int which) {
				        switch (which){
				        case DialogInterface.BUTTON_POSITIVE:
				            //Yes button clicked
				        	getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				            break;

				        case DialogInterface.BUTTON_NEGATIVE:
				            //No button clicked
				            break;
				        }
				    }
				};

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
				    .setNegativeButton("No", dialogClickListener).show();
		 }
		
	}
	
	public void postData(){
		Calendar cal= Calendar.getInstance();
	   	long timeStamp=cal.getTimeInMillis();
	   	String uniqueID=""+timeStamp+"_"+Login.UserID;
	   	MainActivity.gblPartIValues.put("SurveyDataID", uniqueID);  //written just before saving.
		allValues= new HashMap<String, String>();
		allValues.putAll(MainActivity.gblPartIValues);
		allValues.putAll(MainActivity.gblPartIIValues);
		allValues.putAll(MainActivity.gblPartIIIaValues);
		allValues.putAll(MainActivity.gblPartIIIbValues);
		allValues.putAll(MainActivity.gblPartIVValues);
		callBack.postData(actionPOST_AUGUST2013_SURVEY, allValues);
		
 	
	   	
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
		  DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        switch (which){
			        case DialogInterface.BUTTON_POSITIVE:
			            //Yes button clicked
			            break;

			        case DialogInterface.BUTTON_NEGATIVE:
			            //No button clicked
			            break;
			        }
			    }
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
			    .setNegativeButton("No", dialogClickListener).show();
*/
	


}
