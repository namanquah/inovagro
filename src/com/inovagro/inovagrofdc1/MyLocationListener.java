package com.inovagro.inovagrofdc1;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

//deal with GPS: location listener class

public class MyLocationListener implements LocationListener {

	private Activity activity;
	public MyLocationListener(Activity a){
		activity=a;
	}

	@Override
	 public void onLocationChanged(Location location) {
        /* String message = String.format(
                 "New Location \n Longitude: %1$s \n Latitude: %2$s",
                 location.getLongitude(), location.getLatitude()
         );
         Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
         */
     }
	@Override
     public void onStatusChanged(String s, int i, Bundle b) {
      //   Toast.makeText(activity, "GPS Provider status changed",
        //         Toast.LENGTH_LONG).show();
     }

	@Override
     public void onProviderDisabled(String s) {
         Toast.makeText(activity,
                 "GPS Provider disabled by the user. GPS turned off",
                 Toast.LENGTH_LONG).show();
     }

	@Override
     public void onProviderEnabled(String s) {
         Toast.makeText(activity,
                 "GPS Provider enabled by the user. GPS turned on",
                 Toast.LENGTH_LONG).show();
     }

}
