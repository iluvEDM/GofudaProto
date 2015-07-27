package com.example.gofudaproto;

import java.util.List;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPOIItem.CalloutBalloonButtonType;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPoint.GeoCoordinate;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapView.MapViewEventListener;
import net.daum.mf.map.api.MapView.POIItemEventListener;

import com.nhn.android.maps.NMapView;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.ViewUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link MapViewFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link MapViewFragment#newInstance} factory method to create an instance of
 * this fragment.
 *
 */
public class MapViewFragment extends Fragment implements MapViewEventListener{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	public static interface CurrentLocationChangedListner {
		void setCurrentLocation(double latitude, double longitude);
	}
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private static final String API_KEY = "867b82fb3cbe6e4b39c4e90405b6bc5d";
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	public boolean isHaveToCurrentLocation = false;
	private OnFragmentInteractionListener mListener;
	private MainActivity mParentActivity;
	private MapView mMapView;
	private FrameLayout mMainLayout;
	private Button mConfirmLocationButton;
	MapPOIItem marker;
	private Button mSearchButton;
	private EditText mSearchText;
	private LinearLayout mMapLayout;
	public String currentLocation;
	private CurrentLocationChangedListner mLocListener;
	public MapPoint locationNeedToLoad;
	
	public void setLocationListner(CurrentLocationChangedListner listener){
		mLocListener = listener;
	}
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment ClientFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static MapViewFragment newInstance(String param1, String param2) {
		MapViewFragment fragment = new MapViewFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public MapViewFragment() {
		// Required empty public constructor
	}
	MapPoint currentPoint;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		mMainLayout = (FrameLayout)getActivity().findViewById(R.id.map_mainlayout);
		mMapLayout = (LinearLayout)getActivity().findViewById(R.id.map_layout);
		currentPoint = MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633);
		
		if(mParentActivity.getCurrentLocation() != null && locationNeedToLoad == null){
			mMapView.setMapCenterPoint(mParentActivity.getCurrentLocation(), true);
			currentPoint = mParentActivity.getCurrentLocation();
		}else{
			mMapView.setMapCenterPoint(locationNeedToLoad, true);
			currentPoint = locationNeedToLoad;
		}
		marker = new MapPOIItem();
		marker.setItemName("이 위치로 지정");
		marker.setTag(0);
		marker.setMapPoint(currentPoint);
		marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
		marker.setShowDisclosureButtonOnCalloutBalloon(false);
//		marker.setMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
		mMapView.addPOIItem(marker);
		mMapView.setPOIItemEventListener(new MapView.POIItemEventListener() {
			
			@Override
			public void onPOIItemSelected(MapView arg0, MapPOIItem arg1) {
				// TODO Auto-generated method stub
				currentPoint = arg1.getMapPoint();
				
				Toast.makeText(getActivity().getApplicationContext(), "selected", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onDraggablePOIItemMoved(MapView arg0, MapPOIItem arg1,
					MapPoint arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCalloutBalloonOfPOIItemTouched(MapView arg0, MapPOIItem arg1) {
				// TODO Auto-generated method stub
				currentPoint = arg1.getMapPoint();
				Toast.makeText(getActivity().getApplicationContext(), "selected", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onCalloutBalloonOfPOIItemTouched(MapView arg0,
					MapPOIItem arg1, CalloutBalloonButtonType arg2) {
				// TODO Auto-generated method stub
				
			}
		});
//		getActivity().getSupportFragmentManager().beginTransaction()
//		.replace(R.id.client_container, mCallFragment).commit();
		mParentActivity.setHaveToBack(true);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(100, 100);
		mConfirmLocationButton = new Button(getActivity().getBaseContext());
		mConfirmLocationButton.setLayoutParams( new LayoutParams(LayoutParams.WRAP_CONTENT, 100));
		mConfirmLocationButton.setText("drag");
		params = new FrameLayout.LayoutParams(android.widget.FrameLayout.LayoutParams.WRAP_CONTENT, android.widget.FrameLayout.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
		mConfirmLocationButton.setLayoutParams(params);
		mConfirmLocationButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
		mMapView.setMapViewEventListener(this);
		mSearchText =(EditText)getActivity().findViewById(R.id.map_search);
		
		mSearchButton = (Button)getActivity().findViewById(R.id.map_search_button);
		mSearchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String query = mSearchText.getText().toString();
				if (query == null || query.length() == 0) {
					Toast.makeText(getActivity().getApplicationContext(), "please insert search text", Toast.LENGTH_SHORT).show();
					return;
				}
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
					      Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);// 키보드 숨김
				GeoCoordinate geoCoordinate = currentPoint.getMapPointGeoCoord();
				double latitude = geoCoordinate.latitude; // 위도
				double longitude = geoCoordinate.longitude; // 경도
				int radius = 10000; // 중심 좌표부터의 반경거리. 특정 지역을 중심으로 검색하려고 할 경우 사용. meter 단위 (0 ~ 10000)
				int page = 1; // 페이지 번호 (1 ~ 3). 한페이지에 15개
				Searcher searcher = new Searcher(); // net.daum.android.map.openapi.search.Searcher
				searcher.searchKeyword(getActivity().getApplicationContext(), query, latitude, longitude, radius, page, mParentActivity.API_KEY, new OnFinishSearchListener() {
					@Override
					public void onSuccess(List<Item> itemList) {
						mMapView.removeAllPOIItems(); // 기존 검색 결과 삭제
						showResult(itemList); // 검색 결과 보여줌 
					}
					
					@Override
					public void onFail() {
						Toast.makeText(getActivity().getApplicationContext(), "api error", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		
		
		
	}
	private void showResult(List<Item> itemList) {
//		MapPointBounds mapPointBounds = new MapPointBounds();
		
		for (int i = 0; i < itemList.size(); i++) {
			Item item = itemList.get(i);

			MapPOIItem poiItem = new MapPOIItem();
			poiItem.setItemName(item.title);
			poiItem.setTag(i);
			MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(item.latitude, item.longitude);
			poiItem.setMapPoint(mapPoint);
//			mapPointBounds.add(mapPoint);
			poiItem.setMarkerType(MapPOIItem.MarkerType.BluePin);
//			poiItem.setCustomImageResourceId(R.drawable.map_pin_blue);
			poiItem.setMarkerType(MapPOIItem.MarkerType.BluePin);
//			poiItem.setCustomSelectedImageResourceId(R.drawable.map_pin_red);
//			poiItem.setCustomImageAutoscale(false);
//			poiItem.setCustomImageAnchor(0.5f, 1.0f);
			
			mMapView.addPOIItem(poiItem);
//			mTagItemMap.put(poiItem.getTag(), item);
		}
		
//		mMapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds));
		
		MapPOIItem[] poiItems = mMapView.getPOIItems();
		if (poiItems.length > 0) {
			mMapView.selectPOIItem(poiItems[0], false);
			mMapView.setMapCenterPoint(poiItems[0].getMapPoint(), true);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		mParentActivity = (MainActivity)getActivity();
		mMapView = mParentActivity.getMapView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.activity_map, container, false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(mMapView.getParent() == null){
			mMapLayout.addView(mMapView);
		}else{
			((ViewGroup)mMapView.getParent()).removeView(mMapView);
			mMapLayout.addView(mMapView);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//		try {
//			mListener = (OnFragmentInteractionListener) activity;
//		} catch (ClassCastException e) {
//			throw new ClassCastException(activity.toString()
//					+ " must implement OnFragmentInteractionListener");
//		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

	private void updateCurrentRequirements(){
		
	}

	@Override
	public void onMapViewCenterPointMoved(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		
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
	private Searcher mSearcher;
	@Override
	public void onMapViewSingleTapped(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		marker.setMapPoint(arg1);
		marker.setItemName("이 위치로 지정");
		mMapView.removeAllPOIItems();
		mMapView.addPOIItem(marker);
		mLocListener.setCurrentLocation(arg1.getMapPointGeoCoord().latitude, arg1.getMapPointGeoCoord().longitude);
		Toast.makeText(getActivity().getApplicationContext(), "이 위치로 설정 되었습니다.", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onMapViewZoomLevelChanged(MapView arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMapViewDragEnded(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMapViewDragStarted(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onMapViewMoveFinished(MapView arg0, MapPoint arg1) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void onCalloutBalloonOfPOIItemTouched(MapView arg0, MapPOIItem arg1) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onDraggablePOIItemMoved(MapView arg0, MapPOIItem arg1,
//			MapPoint arg2) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onPOIItemSelected(MapView arg0, MapPOIItem arg1) {
//		// TODO Auto-generated method stub
//		currentPoint = arg1.getMapPoint();
//		Toast.makeText(getActivity().getApplicationContext(), "selected", Toast.LENGTH_SHORT).show();
//	}

}
