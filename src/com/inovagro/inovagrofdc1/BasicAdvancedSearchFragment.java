/**
 * 
 */
package com.inovagro.inovagrofdc1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * @author namanquah
 *
 */
public class BasicAdvancedSearchFragment extends Fragment implements OnClickListener, InovagroConstants{
	Button btnAdvanced, btnBasic, btnBack;
	SearchCallbacks mSearchCallbacks;
	int PurposeOfSearch;
	
	public BasicAdvancedSearchFragment(int PurposeOfSearch){
		this.PurposeOfSearch=PurposeOfSearch;
	}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
	
        View rootView = inflater.inflate(R.layout.frag_basic_advanced_search_switch,container, false);
        btnBasic=(Button)rootView.findViewById(R.id.btnBasicSearch);
        btnAdvanced=(Button)rootView.findViewById(R.id.btnAdvancedSearch);
        btnBack=(Button)rootView.findViewById(R.id.btnBack);
        btnBasic.setOnClickListener(this);
        btnAdvanced.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        
        
        return rootView;
    }
   
   public void onClick(View v){
	   if (v==btnBasic){
		   mSearchCallbacks.doSearch(BASIC_SEARCH,PurposeOfSearch);
	   }
	   if (v==btnAdvanced){
		   mSearchCallbacks.doSearch(ADVANCED_SEARCH,PurposeOfSearch);
	   }
	   if (v==btnBack){
		   getFragmentManager().popBackStack();
	   }
   }//onClick

   public interface SearchCallbacks {
	      public void doSearch(int searchType, int PurposeOfSearch);//1=basic, 2 is advanced
	      			//PurposeOfSearch may be add farm, farm vist, plan visits etc
	  }
	  
	  @Override
	  public void onAttach(Activity activity) {
	      super.onAttach(activity);
	      if (!(activity instanceof SearchCallbacks)) {
	          throw new IllegalStateException(
	                  "Activity must implement fragment's callbacks.");
	      }
	      mSearchCallbacks = (SearchCallbacks) activity;
	  }
	  @Override
	  public void onDetach() {
	      super.onDetach();
	      mSearchCallbacks = null;
	  }

}//main class
