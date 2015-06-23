package com.example.gofudaproto;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import com.example.gofudaproto.server.ServerManager;
import com.nhn.android.maps.maplib.NGeoPoint;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
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

public class MainActivity extends ActionBarActivity{
	private Fragment mPrevFragment;
	private Fragment mStartFragment;
	private Fragment mIntroFragment;
	Boolean mIsHaveToBackFragment = false;
	Boolean mIsHaveToStartFragment = false;
	public boolean isHaveToUseCurrentLocation =false;
	public static final String CLIENT_REQUEST_ID_INDEX = "gopuda_client_current_request_id";
	public static final String API_KEY = "867b82fb3cbe6e4b39c4e90405b6bc5d";
	private net.daum.mf.map.api.MapView mMapView;
	private int beforeContainerID;
	private ServerManager mServerManager;
	Client CurrentClient;
	Truck CurrentTruck;
	LocationManager mLocMgr;
	// GPSTracker class
    private GpsInfo gps;
    private SharedPreferences mPreference;
	private SharedPreferences.Editor mEditor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mIntroFragment = new IntroFragment();
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, mIntroFragment).commit();
		}
		mPreference = getSharedPreferences("gopuda", MODE_PRIVATE);
		mEditor = mPreference.edit();
		CurrentClient = new Client();
		CurrentTruck = new Truck();
		mMapView= new net.daum.mf.map.api.MapView(this);
		mMapView.setDaumMapApiKey(API_KEY);
		mMapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
		mMapView.setShowCurrentLocationMarker(true);
//		mMapView.setCurrentLocationEventListener(this);
		mServerManager = new ServerManager();
		
		gps = new GpsInfo(MainActivity.this);
        
	}
	public int getCurrentRequirementId(){
		return mPreference.getInt(CLIENT_REQUEST_ID_INDEX, 0);
	}
	public void loadCurrentClient(){
		
	}
	public ServerManager getServerManager(){
		return mServerManager;
	}
	public void setBeforeContainer(int id){
		beforeContainerID = id;
	}
	public MapPoint getCurrentLocation(){
		// GPS 사용유무 가져오기
        if (gps.isGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
             
            MapPoint point = MapPoint.mapPointWithGeoCoord(latitude, longitude);
//            Toast.makeText(
//                    getApplicationContext(),
//                    "당신의 위치 - \n위도: " + latitude + "\n경도: " + longitude,
//                    Toast.LENGTH_LONG).show();
            return point;
        } else {
            // GPS 를 사용할수 없으므로
            gps.showSettingsAlert();
        }
        return null;
	}
	public GpsInfo getGPS(){
		return gps;
	}
	public String makeIndexString(String word){
		return " \""+word+"\"";
	}
	public void setHaveToBack(Boolean isBack){
		mIsHaveToBackFragment = isBack;
	}
	public void setHaveToBackToStart(Boolean isStart){
		mIsHaveToBackFragment = false;
		mIsHaveToBackFragment = isStart;
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
	
	public void setCurrentRequest(int request_id){
		mEditor.putInt(CLIENT_REQUEST_ID_INDEX, request_id);
		mEditor.commit();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public Context getMainContext(){
		return MainActivity.this;
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
	public void setIsHaveToStartFragment(boolean start){
		mIsHaveToStartFragment = start;
		mIsHaveToBackFragment = !start;
	}
	public void setIsHaveToBackFragment(boolean back){
		mIsHaveToStartFragment = !back;
		mIsHaveToBackFragment = back;
	}
	
	@Override
	public void onBackPressed() {
		if(mIsHaveToBackFragment){
			getSupportFragmentManager().beginTransaction()
			.replace(beforeContainerID, mPrevFragment).commit();
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



}
