package com.inovagro.inovagrofdc1;

import java.util.HashMap;
import java.util.LinkedHashMap;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;

public interface GeneralCallbackIntefaces {
	public void doBasicSearch(int searchType, String searchString);
	public void doAdvancedSearch(HashMap<String,String>values,  int action,int PurposeOfSearch);
	public void fetchListOfFarms(int searchType, String FarmerID); //may need to update this for the current season only!
	public void showVisitTypes(int actionType, String  FarmYearlyDataID); //show menu of farm visit types, param2 was farmID
	public void showVisitForm(int actionType, String FarmYearlyDataID, int VisitType); // show the add visit form
	public void showDatePickerDialog(EditText edt); //show a dialog for picking the date
	public void postData(int actionType, HashMap<String,String>values);//send data excluding image to server, used by add farmer	
	public void doAfterPostData(String result);
	public String [] getCurrentLocation();//call back for fetching GPS coords
	public void takePhoto(Fragment srcFragment);//take picture
	public void showAddFarmForm(String FarmerID, String FarmerName); //show 
	//public void fetchDataForSelectedFarmers(String ids);//post a list of IDs, fetch the corresponding farmer + farm data
	public void deleteLocalFarmerData(String ids); //delete local farmer data previously saved on device
	public void resetLocalFarmersDB(); //delete  and recreate local farms's db, in case it has been corrupted.
	public void showNextSurveyForm(int FormPart); //show partI, IIa, IIb etc
	public void takePhoto(Fragment srcFragment, View v, String prefix);//take picture
	public void showPigeonPeaHarvestForm(String FarmerID, String FarmerName);//show the PigeonPeaHarvest Form
	public void showFarmerDetail(String FarmerID);
	public void showConfirmationForm(HashMap<String, String> Data, int postAction);
	public void showPovertyScoreCard(String FarmerID);
}
