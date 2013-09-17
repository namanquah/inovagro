package com.inovagro.inovagrofdc1;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class BasicSearchFragment extends Fragment implements OnClickListener, InovagroConstants{
	Button btnSearch, btnCancel;
	RadioButton rbFarmerName, rbFarmerReferenceNo;
	EditText edtSearchString;
	GeneralCallbackIntefaces callBack;
	
	int PurposeOfSearch;
	
	public BasicSearchFragment(int PurposeOfSearch){
		this.PurposeOfSearch=PurposeOfSearch;
	}
	//Purpose of Search was not used further. Seems its passed globally
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
        View rootView = inflater.inflate(R.layout.frag_basic_search_farmer,container, false);
        btnCancel=(Button)rootView.findViewById(R.id.btnCancel);
        btnSearch=(Button)rootView.findViewById(R.id.btnSubmit);
        btnCancel.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        
        rbFarmerName=(RadioButton)rootView.findViewById(R.id.rdFarmerName);
        rbFarmerReferenceNo=(RadioButton)rootView.findViewById(R.id.rdFarmerReferenceNo);
        edtSearchString=(EditText)rootView.findViewById(R.id.edtSearchString);
        
        
        return rootView;
    }
   
   public void onClick(View v){
	   if (v==btnSearch){
		 //  Log.v("nna_basicsearch","about to search");
		   String s=edtSearchString.getText().toString();
		   if (s.trim().equals("")){
			   Toast.makeText(getActivity(), "Please enter some text", Toast.LENGTH_LONG).show();
			   edtSearchString.requestFocus();
			   return;
		   }
		  // Log.v("nna","search for"+s);
		   if (rbFarmerName.isChecked()){
		   callBack.doBasicSearch(actionSEARCH_FARMERNAME,s);//,SEARCH_FARMERNAME
		   }
		   if (rbFarmerReferenceNo.isChecked()){
			   callBack.doBasicSearch(actionSEARCH_FARMERREFERENCENO,s);//SEARCH_FARMERREFERENCENO
			   }
	   }
	   if (v==btnCancel){
		   getFragmentManager().popBackStack();
	   }
	   
   }//onClick


	  
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
}
