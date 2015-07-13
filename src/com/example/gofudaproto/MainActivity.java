package com.example.gofudaproto;

import java.io.IOException;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.example.gofudaproto.server.ServerManager;
import com.example.gofudaproto.server.ServerManager.OnServerManagerListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends ActionBarActivity{
	private Fragment mPrevFragment;
	private Fragment mStartFragment;
	private Fragment mIntroFragment;
	Boolean mIsHaveToBackFragment = false;
	Boolean mIsHaveToStartFragment = false;
	public boolean isHaveToUseCurrentLocation =false;
	public static final String CLIENT_REQUEST_ID_INDEX = "gopuda_client_current_request_id";
	public static final String API_KEY = "867b82fb3cbe6e4b39c4e90405b6bc5d";
	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	// SharedPreferences에 저장할 때 key 값으로 사용됨.
    public static final String PROPERTY_REG_ID_T = "registration_id_t";
    public static final String PROPERTY_REG_ID_C = "registration_id_c";
    // SharedPreferences에 저장할 때 key 값으로 사용됨.
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String TAG = "icelancer";
    String SENDER_ID = "207372261885";

    GoogleCloudMessaging gcm;
    Context context;

    String regid;
    private TextView mDisplay;
	
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
	private String mRegisterId = "";
	private String mTruckRegisterId = "";
	private String mClientRegiserId = "";
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
		if (mIsGoneToBackground) {
			mMapView= new net.daum.mf.map.api.MapView(this);
			mMapView.setDaumMapApiKey(API_KEY);
			mMapView.setCurrentLocationTrackingMode(net.daum.mf.map.api.MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
			mMapView.setShowCurrentLocationMarker(true);
		}
		
//		mMapView.setCurrentLocationEventListener(this);
		mServerManager = new ServerManager();
		
		gps = new GpsInfo(MainActivity.this);
		
		if (checkPlayServices()) {
			gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            if (regid.isEmpty()) {
                registerInBackground();
            }else {
                Log.i(TAG, "No valid Google Play Services APK found.");
            }
        }
        
	}
	private boolean mIsGoneToBackground = false;
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("life", "onPause");
		mIsGoneToBackground = true;
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.d("life", "onDestroy");
		super.onDestroy();
	}


	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		Log.d("life", "onStop");
		super.onStop();
	}


	private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getGCMPreferences(context);
        String registrationId ;
        if (prefs.getString(PROPERTY_REG_ID_T, "").equals("")) {
        	 registrationId = prefs.getString(PROPERTY_REG_ID_C, "");
		}else{
			 registrationId = prefs.getString(PROPERTY_REG_ID_T, "");
		}
       
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        
        // 앱이 업데이트 되었는지 확인하고, 업데이트 되었다면 기존 등록 아이디를 제거한다.
        // 새로운 버전에서도 기존 등록 아이디가 정상적으로 동작하는지를 보장할 수 없기 때문이다.
        int registeredVersion = mPreference.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }

    private SharedPreferences getGCMPreferences(Context context) {
        return getSharedPreferences(MainActivity.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            // should never happen
//            throw new RuntimeException("Could not get package name: " + e);
        	return 0;
        }
    }
    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;

                    // 서버에 발급받은 등록 아이디를 전송한다.
                    // 등록 아이디는 서버에서 앱에 푸쉬 메시지를 전송할 때 사용된다.
//                    sendRegistrationIdToBackend(true,regid);

                    // 등록 아이디를 저장해 등록 아이디를 매번 받지 않도록 한다.
//                    storeRegistrationId(context, regid);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
            }

        }.execute(null, null, null);
    }

    private void storeRegistrationId(Context context, String regid, boolean isTruck) {
        final SharedPreferences prefs = getGCMPreferences(context);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        if (isTruck) {
        	editor.putString(PROPERTY_REG_ID_T, regid);
		}else{
			editor.putString(PROPERTY_REG_ID_C, regid);
		}
//        editor.putString(PROPERTY_REG_ID, regid);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }
    
    public String getRegisterId(boolean isTruck){
    	if (isTruck) {
			return mTruckRegisterId;
		}else{
			return mClientRegiserId;
		}
    }
    private Fragment nextFragment;
    public void sendRegistrationIdToBackend(final boolean isTruck) {
    	final SharedPreferences prefs = getGCMPreferences(context);	
    	String reg_id;
    	if (isTruck) {
			reg_id =  prefs.getString(PROPERTY_REG_ID_T, "");
		}else{
			reg_id =  prefs.getString(PROPERTY_REG_ID_C, "");
		}
    	if (reg_id.equals("")) {
    		String param = String.format("{\"device_id\":\"%s\"}",regid);
    		mServerManager.doSendCall(isTruck ? ServerManager.REGISTER_TRUCK_ID : ServerManager.REGISTER_CUSTOMER_ID, param, new OnServerManagerListener() {
    			@Override
    			public void serverDidError(String error) {
    			}
    			
    			@Override
    			public void serverDidEnd(String result) {
    				if (result != null) {
    					
    					if (isTruck) {
    						mTruckRegisterId = result;
    						setBeforeContainer(R.id.container);
    						nextFragment = new TruckFragment();
    						getSupportFragmentManager().beginTransaction().replace(R.id.container, nextFragment).commit();
    						storeRegistrationId(getBaseContext(), result,true);
    						
    					}else{
    						mClientRegiserId = result;
    						setBeforeContainer(R.id.container);
    						nextFragment = new ClientFragment();
    						getSupportFragmentManager().beginTransaction().replace(R.id.container, nextFragment).commit();
    						storeRegistrationId(getBaseContext(), result,false);
    					}
    				}
    				
    				
//    				Log.i(isTruck ? ServerManager.REGISTER_TRUCK_ID : ServerManager.REGISTER_CUSTOMER_ID, result);
    				
    			}
    		});
		}else{
			if (isTruck) {
				mTruckRegisterId = prefs.getString(PROPERTY_REG_ID_T, "");
				setBeforeContainer(R.id.container);
				nextFragment = new TruckFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.container, nextFragment).commit();
				
			}else{
				mClientRegiserId = prefs.getString(PROPERTY_REG_ID_C, "");
				setBeforeContainer(R.id.container);
				nextFragment = new ClientFragment();
				getSupportFragmentManager().beginTransaction().replace(R.id.container, nextFragment).commit();
			}
		}
    	
    }
    
	@Override
	protected void onPostResume() {
		// TODO Auto-generated method stub
		super.onPostResume();
		checkPlayServices();
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
	private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("ICELANCER", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
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
	public void hideKeyboard() {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }


}
