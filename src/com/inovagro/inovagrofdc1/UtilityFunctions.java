package com.inovagro.inovagrofdc1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class UtilityFunctions {
	public String urlEncode(String sUrl){ 
		 StringBuffer urlOK = new StringBuffer();
		 for(int e=0; e<sUrl.length(); e++){
			 char ch=sUrl.charAt(e);
			switch(ch){
				case '<': urlOK.append("%3C"); break;
				case '>': urlOK.append("%3E"); break;
				case ' ': urlOK.append("%20"); break;
				case ':': urlOK.append("%3A"); break;
				case '-': urlOK.append("%2D"); break;
	                        //case '&': urlOK.append("%26"); break;
				default: urlOK.append(ch); break;
			}
		}
	    return urlOK.toString();
	}
	

public String[] msplit(String original, String sep){
return original.split(sep);
}

	String [][] create2DArray(String original, String rowSep, String colSep){
	String [] rowData= original.split(rowSep);
	 int numrows= rowData.length;
	    String tmp[][]=new String[numrows][];
	    for (int i=0; i<rowData.length; i++){
	        tmp[i]=rowData[i].split(colSep);
	    }
	    return tmp;
	}


	Object [] create2_1DArrays(String original, String rowSep, String colSep){//, String[] NameArry, int IDArry, int [] arry2){
		//if (original==null || original.trim().equals("")) return null;//new addition.
		String[] NameArry=null;
		//int [] IDArry=null;
		String [] IDArry=null;
		String [] rowData= original.split(rowSep);
	 int numrows= rowData.length;
	    String tmp[][]=new String[numrows][];
	    for (int i=0; i<rowData.length; i++){
	        tmp[i]=rowData[i].split(colSep);
	    }
	    //IDArry=new int[tmp.length];
	    IDArry=new String[tmp.length];
	   NameArry=new String[tmp.length];
		 for (int i=0; i<tmp.length; i++){
			 //System.out.println("i..."+i+"=="+tmp[i][0]+"--"+tmp[i][1]);
			 NameArry[i]=(tmp[i][0]);
			 //IDArry[i]=Integer.parseInt(tmp[i][1]);
			 IDArry[i]=tmp[i][1];
		 }//for i
		 return new Object[]{NameArry, IDArry};
	}
	
	/**
	 * Splits a string into a 2-d array. this does not drop the trailing blank string in case they 
	 * are blank. Also the result can be treated as a key-value pair
	 * @param original the character delimited string
	 * @param rowSep the char(s) that separate the rows eg </br>
	 * @param colSep  the char(s) that separate teh columns eg :
	 * @return
	 */
	Object [] createKey_ValuePairs(String original, String rowSep, String colSep){// eg orig, "</br>", ":" //, String[] NameArry, int IDArry, int [] arry2){
		//if (original==null || original.trim().equals("")) return null;//new addition.
		String[] NameArry=null;
		//int [] IDArry=null;
		String [] IDArry=null;
		String [] rowData= original.split(rowSep);
	 int numrows= rowData.length;
	    String tmp[][]=new String[numrows][];
	    for (int i=0; i<rowData.length; i++){
	        tmp[i]=rowData[i].split(colSep, -1);
	    }
	    //IDArry=new int[tmp.length];
	    IDArry=new String[tmp.length];
	   NameArry=new String[tmp.length];
		 for (int i=0; i<tmp.length; i++){
			 //System.out.println("i..."+i+"=="+tmp[i][0]+"--"+tmp[i][1]);
			 NameArry[i]=(tmp[i][0]);
			 //IDArry[i]=Integer.parseInt(tmp[i][1]);
			 IDArry[i]=tmp[i][1];
		 }//for i
		 return new Object[]{NameArry, IDArry};
	}
	
/*	
// * using native functions as above.
	public String[] msplit(String original, String sep) { 
	         Vector<String> nodes = new Vector<String>(); 
	        String separator = sep;
	         // Parse nodes into vector 
	        int index = original.indexOf(separator);
	        while(index>=0) {
	                nodes.addElement(original.substring(0, index) ); 
	                original = original.substring(index+separator.length());
	                index = original.indexOf(separator); 
	        }// Get the last node 
	        nodes.addElement( original );// Create splitted string array 
	        String[] result = new String[ nodes.size() ];
	        if( nodes.size()>0 ) {
	                for(int loop=0; loop<nodes.size(); loop++){
	                        result[loop] = (String)nodes.elementAt(loop); 
	                } 
	        }
	        return result; 
	}
	String [][] create2DArray(String original, String rowSep, String colSep){
	    String [] rowData= msplit(original, rowSep);
	    int numrows= rowData.length;
	    String tmp[][]=new String[numrows][];
	    for (int i=0; i<rowData.length; i++){
	        tmp[i]=msplit(rowData[i], colSep);
	    }
	    //tmp[tmp.length-1]=null;  //<--explore
	    return tmp;
	}
*/	
	/**********************************/
	/**
	* Create a string from the TimeOfDay portion of a time/date as
	hh::mm::ss
	* @param data The date/time as milliseconds since the epoch.
	*/
	public  String timeToString (long date)
	{
	Calendar c = Calendar.getInstance();
	c.setTime(new Date(date));
	int h = c.get(Calendar.HOUR_OF_DAY);
	int m = c.get(Calendar.MINUTE);
	int s = c.get(Calendar.SECOND);
	String t = (h<10? "0": "")+h+":"+(m<10? "0": "")+m+":"+(s<10?
	"0": "")+s;
	return t;
	}
	public  String timeToString (Date d)
	{
	   
	Calendar c = Calendar.getInstance();
	c.setTime(d);
	int h = c.get(Calendar.HOUR_OF_DAY);
	int m = c.get(Calendar.MINUTE);
	int s = c.get(Calendar.SECOND);
	String t = (h<10? "0": "")+h+":"+(m<10? "0": "")+m+":"+(s<10?
	"0": "")+s;
	return t;
	}

	/**
	* Create a string from the date portion of a time/date as yyyy-mm-dd
	* @param date the dat/time as milliseconds since the epoch
	*/
	public  String dateToString (long date)
	{
	Calendar c = Calendar.getInstance();
	c.setTime(new Date(date));
	int y = c.get(Calendar.YEAR);
	int m = c.get(Calendar.MONTH) + 1;
	int d = c.get(Calendar.DATE);
	String t = (y<10? "0": "")+y+"-"+(m<10? "0": "")+m+"-"+(d<10?
	"0": "")+d;
	return t;
	}

	/**
	 * Creates timeStamp given a date in Long format
	 * @param date as a time stamp
	 * @return
	 */
	public  static String dateToStringTimeStamp (long date)  //made changes to this> verify 
	{
	UtilityFunctions thisClass= new UtilityFunctions();
	Calendar c = Calendar.getInstance();
	c.setTime(new Date(date));
	int y = c.get(Calendar.YEAR);
	int m = c.get(Calendar.MONTH) + 1;
	int d = c.get(Calendar.DATE);
	String t = (y<10? "0": "")+y+"-"+(m<10? "0": "")+m+"-"+(d<10?
	"0": "")+d;
	t=t+" "+thisClass.timeToString(date);
	return t;
	}
	
	/**
	 * creates a timeStamp usign current Time, in string format to save directly in db.
	 * @return timeStamp. 
	 */
	public  static String currentStringTimeStamp (){ //new- oct 	
		Calendar cal= Calendar.getInstance();
       	long timeStamp=cal.getTimeInMillis();       	
       	String theEntryDate=dateToStringTimeStamp(timeStamp);
		return theEntryDate;
	}
	
	/*
	public  String dateToString (Date date)
	{
	//    System.out.println("date received____");
	//    System.out.println(date);
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	int y = c.get(Calendar.YEAR);
	int m = c.get(Calendar.MONTH) + 1;
	int d = c.get(Calendar.DATE);
	String t = (y<10? "0": "")+y+"-"+(m<10? "0": "")+m+"-"+(d<10?
	"0": "")+d;
	return t;
	}
*/
	/**********************************/
		
	public static String getHumanDate(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);

	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH); //zero based
	    int day = calendar.get(Calendar.DAY_OF_MONTH);
	    int hour = calendar.get(Calendar.HOUR_OF_DAY);
	    int minute = calendar.get(Calendar.MINUTE);
	    int second = calendar.get(Calendar.SECOND);

	    return " "+day+"-"+month+1+"-"+year;
	}
	public static String getSQLDateString(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);

	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH); //zero based
	    int day = calendar.get(Calendar.DAY_OF_MONTH);
	    int hour = calendar.get(Calendar.HOUR_OF_DAY);
	    int minute = calendar.get(Calendar.MINUTE);
	    int second = calendar.get(Calendar.SECOND);

	    return " "+year+"-"+month+1+"-"+day;
	}

	/**
	 * This creates a unique ID that can be used eg as FarmerID in offline mode. It si based on timestamp
	 * 
	 * @param qualifier adds a string at end to qualify the identifier
	 * @return
	 */
	public static String uniqueID(String qualifier){
		
		Calendar cal= Calendar.getInstance();
    	long timeStamp=cal.getTimeInMillis();
    	String uniqueID=""+timeStamp+"_"+Login.UserID+qualifier;
    	
		return uniqueID;
	}
	
	//use this to handle listview within a scrollView. this should not normally be done.
	// call Utility.setListViewHeightBasedOnChildren(yourListView) and it will be resized to exactly accommodate the height of its items.
    //public class Utility {
	
	  //original code
        public static void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter(); 
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
       
	
	/*
	 public static void setListViewHeightBasedOnChildren(ListView listView) {
	        ListAdapter listAdapter = listView.getAdapter();
	        if (listAdapter == null) {
	            // pre-condition
	            return;
	        }

	        int totalHeight = 0;
	        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
	        for (int i = 0; i < listAdapter.getCount(); i++) {
	            View listItem = listAdapter.getView(i, null, listView);
	            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
	            totalHeight += listItem.getMeasuredHeight();
	        }

	        ViewGroup.LayoutParams params = listView.getLayoutParams();
	        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	        listView.setLayoutParams(params);
	        listView.requestLayout();
	    }
	 */
   // }

        /**
         * Utility functions for settign spinner -used in Inovagro Project
         * @param s
         * @param id
         * @return
         */
        public static int getSpinnerPosition(Spinner s, String id){
			  ArrayAdapter<ComboRowData> aa=(ArrayAdapter<ComboRowData>)s.getAdapter();
			  int pos=-1;
			  ComboRowData cbr;
			  for (int i=0; i<aa.getCount(); i++){
			  		cbr=aa.getItem(i);
			  		if (cbr.ID==Integer.parseInt(id)){
			  			pos=i; break;
			  		}
			  	}
			  return pos;
		  }
        
  
        public static final String md5(final String s) {
            try {
                // Create MD5 Hash
                MessageDigest digest = java.security.MessageDigest
                        .getInstance("MD5");
                digest.update(s.getBytes());
                byte messageDigest[] = digest.digest();

                // Create Hex String
                StringBuffer hexString = new StringBuffer();
                for (int i = 0; i < messageDigest.length; i++) {
                    String h = Integer.toHexString(0xFF & messageDigest[i]);
                    while (h.length() < 2)
                        h = "0" + h;
                    hexString.append(h);
                }
                return hexString.toString();

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return "";
        }
        
        public static final String SQLdateToAndroidStringDate(String sqlDate){
        	return sqlDate.replace("-", "/");
        }
}
