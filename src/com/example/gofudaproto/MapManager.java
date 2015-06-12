package com.example.gofudaproto;

import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapController;
import com.nhn.android.maps.NMapLocationManager;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.NMapView.OnMapStateChangeListener;
import com.nhn.android.maps.NMapView.OnMapViewTouchEventListener;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.nmapmodel.NMapPlacemark;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MapManager extends NMapActivity  implements OnMapStateChangeListener,OnMapViewTouchEventListener{
	
	private static final String mMapKeyString = "6b9ff94c789c7a68080b650b44842a06";
	private NMapView mMapView;
	private NMapController mMapController;
	private NMapLocationManager mMapLocationManager;
	static MapManager mapManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);	
		// create map view
		mMapView = new NMapView(this);
		
		mMapController = mMapView.getMapController();
		// set a registered API key for Open MapViewer Library
		mMapView.setApiKey(mMapKeyString);

		// set the activity content to the map view
		setContentView(mMapView);
		
		// initialize map view
		mMapView.setClickable(true);
		
		// register listener for map state changes
		mMapView.setOnMapStateChangeListener(this);
		mMapView.setOnMapViewTouchEventListener(this);

		// use map controller to zoom in/out, pan and set map center, zoom level etc.

		// use built in zoom controls
        mMapView.setBuiltInZoomControls(true, null); 
        super.setMapDataProviderListener(onDataProviderListener);
        
		mMapLocationManager = new NMapLocationManager(this);
        
	}
	private final NMapActivity.OnDataProviderListener onDataProviderListener = new NMapActivity.OnDataProviderListener() {

		@Override
		public void onReverseGeocoderResponse(NMapPlacemark arg0, NMapError arg1) {
			// TODO Auto-generated method stub
			if (arg1 != null) {
				Log.e("myLog", "Failed to findPlacemarkAtLocation: error=" + arg1.toString());
				Toast.makeText(MapManager.this, arg1.toString(), Toast.LENGTH_LONG).show();
				return;
			}else{
				Toast.makeText(MapManager.this, arg1.toString(), Toast.LENGTH_LONG).show();
			}
		}

	};
	public static MapManager getInstance(){
		return mapManager;
	}
	public NGeoPoint getCurrentLocation(){
		return mMapLocationManager.getMyLocation();
	}
	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(MainActivity.getMainActivity().isHaveToUseCurrentLocation){
			setCurrentMapCenter(getCurrentLocation());
		}
	}

	public NMapView getMapView(){
		return mMapView;
	}
	public void setCurrentMapCenter(NGeoPoint point){
		if(point == null){
			Toast.makeText(getBaseContext(), "We can't detect your location!", Toast.LENGTH_SHORT).show();
		}else{
			mMapController.setMapCenter(point);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
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
	public void onAnimationStateChange(NMapView arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapCenterChange(NMapView arg0, NGeoPoint arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapCenterChangeFine(NMapView arg0) {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public void onMapInitHandler(NMapView arg0, NMapError arg1) {
		// TODO Auto-generated method stub
		if (arg1 == null) { // success
			mMapController.setMapCenter(new NGeoPoint(126.978371, 37.5666091), 11);
		} else { // fail
			Log.e("nmap error", "onMapInitHandler: error=" + arg1.toString());
		}

	}

	@Override
	public void onZoomLevelChange(NMapView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLongPress(NMapView arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLongPressCanceled(NMapView arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(NMapView arg0, MotionEvent arg1, MotionEvent arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSingleTapUp(NMapView arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouchDown(NMapView arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTouchUp(NMapView arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		
	}
}
