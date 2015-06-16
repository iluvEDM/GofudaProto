package com.example.gofudaproto;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import com.nhn.android.maps.maplib.NGeoPoint;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;
import android.os.Build;
import android.provider.Settings;

public class MainActivity extends ActionBarActivity implements MapView.MapViewEventListener, MapView.CurrentLocationEventListener, MapView.POIItemEventListener{
	private Fragment mPrevFragment;
	private Fragment mStartFragment;
	private Fragment mIntroFragment;
	private static MainActivity mainActivity;
	Boolean mIsHaveToBackFragment = false;
	Boolean mIsHaveToStartFragment = false;
	public boolean isHaveToUseCurrentLocation =false;
	private static final String API_KEY = "867b82fb3cbe6e4b39c4e90405b6bc5d";
	private net.daum.mf.map.api.MapView mMapView;
	LocationManager mLocMgr;
	// GPSTracker class
    private GpsInfo gps;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mIntroFragment = new IntroFragment();
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, mIntroFragment).commit();
		}
//		init();
//		startG();
		mMapView= new net.daum.mf.map.api.MapView(this);
		mMapView.setDaumMapApiKey(API_KEY);
//		mMapView.setMapType(MapView.MapType.Standard);
		mMapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
		mMapView.setShowCurrentLocationMarker(true);
		mMapView.setCurrentLocationEventListener(this);
		mMapView.setMapViewEventListener(this);
		mMapView.setPOIItemEventListener(this);
		mMapView.setShowCurrentLocationMarker(true);
		mainActivity = this;
		
		
//		MapPOIItem marker = new MapPOIItem();
//		marker.setItemName("Default Marker");
//		marker.setTag(0);
//		marker.setMapPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633));
//		marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
//		marker.setMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//
//		mMapView.addPOIItem(marker);
//		mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), 9, true);
		gps = new GpsInfo(MainActivity.this);
        
	}
	
	public void getCurrentLocation(){
		// GPS 사용유무 가져오기
        if (gps.isGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
             
             
            Toast.makeText(
                    getApplicationContext(),
                    "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,
                    Toast.LENGTH_LONG).show();
        } else {
            // GPS 를 사용할수 없으므로
            gps.showSettingsAlert();
        }
	}
	
	private void init() {
		// TODO Auto-generated method stub
		mLocMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}

	private boolean getGPSStatus() {
		String allowedLocationProviders = Settings.System.getString(
				getContentResolver(),
				Settings.System.LOCATION_PROVIDERS_ALLOWED);

		if (allowedLocationProviders == null) {
			allowedLocationProviders = "";
		}

		return allowedLocationProviders.contains(LocationManager.GPS_PROVIDER);
	}

	private void setGPSStatus(boolean pNewGPSStatus) {
		String allowedLocationProviders = Settings.System.getString(
				getContentResolver(),
				Settings.System.LOCATION_PROVIDERS_ALLOWED);

		if (allowedLocationProviders == null) {
			allowedLocationProviders = "";
		}

		boolean networkProviderStatus = allowedLocationProviders
				.contains(LocationManager.NETWORK_PROVIDER);

		allowedLocationProviders = "";
		if (networkProviderStatus == true) {
			allowedLocationProviders += LocationManager.NETWORK_PROVIDER;
		}
		if (pNewGPSStatus == true) {
			allowedLocationProviders += "," + LocationManager.GPS_PROVIDER;
		}

		Settings.System.putString(getContentResolver(),
				Settings.System.LOCATION_PROVIDERS_ALLOWED,
				allowedLocationProviders);


		return;
	}

	public void startG() {
		try {
			if (!getGPSStatus())
				setGPSStatus(true);
		} catch (Exception e) {
			Log.e("%s:%s", e.getClass().getName());
		}
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				sendLocationUpdate(location);
			}

			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub

			}

			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub

			}

			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub

			}
		};
		mLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);
		Location location = mLocMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = mLocMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (location != null) {
				sendLocationUpdate(location);
			}
		}
	}

	public void sendLocationUpdate(Location location) {
		Log.d("location", "Latitud: "+location.getLatitude()+
				"Longitude: "+location.getLongitude()+
				"\nAccuracy: "+location.getAccuracy()+
				"\nProvider: "+location.getProvider());
		Toast.makeText(this, "Latitud: "+location.getLatitude()+
				"Longitude: "+location.getLongitude()+
				"\nAccuracy: "+location.getAccuracy()+
				"\nProvider: "+location.getProvider(), Toast.LENGTH_SHORT).show();
	}
	
	static MainActivity getMainActivity(){
		return mainActivity;
	}
	public void setHaveToBack(Boolean isBack){
		mIsHaveToBackFragment = isBack;
	}
	public void setStartFragment(Fragment fragment){
		this.mStartFragment = fragment;
	}
	
	public void setPrevFragment(Fragment fragment){
		this.mPrevFragment = fragment;
	}
	public MapView getMapView(){
		return mMapView;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		if(mIsHaveToBackFragment){
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.container, mPrevFragment).commit();
			mIsHaveToBackFragment = false;
		}else if(mIsHaveToStartFragment){
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.container, mStartFragment).commit();
			mIsHaveToStartFragment = false;
		}else{
			// TODO Auto-generated method stub
			super.onBackPressed();
		}
	}

	@Override
	public void onCalloutBalloonOfPOIItemTouched(MapView arg0, MapPOIItem arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDraggablePOIItemMoved(MapView arg0, MapPOIItem arg1,
			MapPoint arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPOIItemSelected(MapView arg0, MapPOIItem arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCurrentLocationDeviceHeadingUpdate(MapView arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCurrentLocationUpdate(MapView arg0, MapPoint arg1, float arg2) {
		// TODO Auto-generated method stub
		Log.d("map view", "current location update");
	}

	@Override
	public void onCurrentLocationUpdateCancelled(MapView arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCurrentLocationUpdateFailed(MapView arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapViewCenterPointMoved(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		Log.d("map view", "center moved");
	}

	@Override
	public void onMapViewDoubleTapped(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapViewInitialized(MapView arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapViewLongPressed(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapViewSingleTapped(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapViewZoomLevelChanged(MapView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}


}
