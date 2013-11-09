package com.inovagro.inovagrofdc1;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmationFragment extends Fragment implements OnClickListener, InovagroConstants {

	GeneralCallbackIntefaces callBack;
	Button btnCancel, btnSubmit;
	TextView txtSummary;
	
	
	HashMap<String, String> FormData;
	int postAction;
	public ConfirmationFragment(HashMap<String, String> Data, int postAction) {
		FormData=Data;
		this.postAction=postAction;
	}
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		
	        View rootView = inflater.inflate(R.layout.frag_survey_confirm_data,container, false);
	        btnCancel=(Button)rootView.findViewById(R.id.btnCancel);
	        btnCancel.setOnClickListener(this);
	        btnSubmit=(Button)rootView.findViewById(R.id.btnSubmit);
	        btnSubmit.setOnClickListener(this);
	    
	        String s="";
	        String EoL="<br />";
	        
	        
	        for (Map.Entry<String, String> entry: FormData.entrySet()){
	   			String key=entry.getKey();
	   			String value =entry.getValue();
	   			s+="<b>"+key+":</b> "+value+EoL;
	        }
	        txtSummary=(TextView)rootView.findViewById(R.id.txtSummary);
	        txtSummary.setText(Html.fromHtml(s));
	       
	        return rootView;
	 }
	 
	 
	 public void onClick(View v){
		   if (v==btnCancel){
			   getFragmentManager().popBackStack();
		   }
		   if (v==btnSubmit){
			   ////=======>>>>>>>>>>>>>>>>need to pass the actionType for posting to this function!
			   //callBack.postData(actionPOST_ADD_FARMER, FormData);
			   callBack.postData(postAction, FormData);
			   //validation:
	//		  if (! validateData()) //if validation fails, exit
	//			  return;
	//		   fetchDataAndPost();
			   
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
