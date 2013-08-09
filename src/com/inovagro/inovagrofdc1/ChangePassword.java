package com.inovagro.inovagrofdc1;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassword extends Fragment implements OnClickListener, InovagroConstants {
	GeneralCallbackIntefaces callBack;
	
	Button btnCancel, btnSubmit;
	EditText edtOldPassword, edtNewPassword, edtRepeatPassword;
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
        View rootView = inflater.inflate(R.layout.frag_change_password,container, false);
        btnCancel=(Button)rootView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnSubmit=(Button)rootView.findViewById(R.id.btnChange);
        btnSubmit.setOnClickListener(this);
        
        edtOldPassword=(EditText)rootView.findViewById(R.id.edtOldPassword);
        edtNewPassword=(EditText)rootView.findViewById(R.id.edtNewPassword);
        edtRepeatPassword=(EditText)rootView.findViewById(R.id.edtRepeatPassword);
        
        return rootView;
	}
	
	 public void onClick(View v){
		   if (v==btnCancel){
			   getFragmentManager().popBackStack();
		   }
		   if (v==btnSubmit){
			   if (validateData()){
				   postData();
			   }
		   }
	 }
	 
	
	 private boolean validateData(){
		 
		if (! edtNewPassword.getText().toString().equals( edtRepeatPassword.getText().toString() ) ){
			edtNewPassword.requestFocus();
			Toast.makeText(getActivity(), "The new passwords are not the same", Toast.LENGTH_LONG).show();
			return false;
		}
		 return true;
		 
	 }
	private void postData(){
		   HashMap<String, String> values = new HashMap<String, String>();
		   values.put("userID",Integer.toString(Login.UserID));
		   values.put("newPassword",edtNewPassword.getText().toString());
		   values.put("oldPassword",edtOldPassword.getText().toString());
		 

		   //now use call back to send the post
		   callBack.postData(actionCHANGE_PASSWORD, values);	
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
