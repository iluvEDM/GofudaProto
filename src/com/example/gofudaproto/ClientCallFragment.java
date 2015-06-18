package com.example.gofudaproto;

import java.util.ArrayList;

import net.daum.android.map.MapView;
import net.daum.android.map.MapViewEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.example.gofudaproto.server.ServerManager;
import com.example.gofudaproto.server.ServerManager.OnServerManagerListener;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;

import android.app.Activity;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link ClientCallFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link ClientCallFragment#newInstance} factory method to create an instance
 * of this fragment.
 *
 */
public class ClientCallFragment extends android.support.v4.app.Fragment implements OnServerManagerListener{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	
	
	
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";
	private MainActivity mParentActivity;
	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
//	private ArrayList<CallPaper> mReadyCallArray;
	private ArrayList<CallThumbNailView> mCallThumbNailArray;
	private ArrayList<Truck> mReadyTrucks;
	private boolean isCallCountZero;
	private OnFragmentInteractionListener mListener;
	
	private FrameLayout mFrameLayout;
	private ProgressBar mProgressBar;
	private SharedPreferences mPreference;
	private SharedPreferences.Editor mEditor;
	
	private ClientMakeRequestFagment mRequestFragment;
	private ClientShowTruckFragment mTruckListFragment;
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment TruckConfirmFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ClientCallFragment newInstance(String param1, String param2) {
		ClientCallFragment fragment = new ClientCallFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public ClientCallFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		mPreference = getActivity().getSharedPreferences("gopuda", Activity.MODE_PRIVATE);
		mParentActivity = (MainActivity)getActivity();
		mReadyTrucks = new ArrayList<Truck>();
		mRequestFragment = new ClientMakeRequestFagment();
		mTruckListFragment = new ClientShowTruckFragment();
		mTruckListFragment.initData();
		

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.client_call, container, false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mFrameLayout = (FrameLayout)getActivity().findViewById(R.id.client_call_frame);
		mProgressBar = new ProgressBar(getActivity().getApplicationContext(), null, android.R.attr.progressBarStyleLarge);
		mProgressBar.setIndeterminate(true);
		mProgressBar.setVisibility(View.VISIBLE);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(100, 100);
		params.gravity = Gravity.CENTER;
		mParentActivity.setHaveToBackToStart(true);
		mProgressBar.setLayoutParams(params);
		loadReadyTrucksFromServer();
	}
	private void loadReadyTrucksFromServer(){
		// TODO: 서버에서 현재 준비된 트럭 정보를 얻어온 뒤 iteration마다 addTruckToComingTruckList()를 통해 mReadyTrucks에 트럭 정보를 추가한다.
		int request_id = mPreference.getInt(mParentActivity.CLIENT_REQUEST_ID_INDEX, 0);
		String param =  makeShowingTrucksParameter(request_id);
		mParentActivity.getServerManager().doSendCall(ServerManager.SHOW_COMING_TRUCKS, makeShowingTrucksParameter(request_id), this);
		mFrameLayout.addView(mProgressBar);
	}
	
	private String makeShowingTrucksParameter(int request_id){
		return "{"+ makeIndexString("request_id") + ":"+makeIndexString(String.valueOf(request_id)) +"}";
	}
	private String makeIndexString(String word){
		return " \""+word+"\"";
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
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
	
	public void setThisFragmentToPrev(){
		mParentActivity.setPrevFragment(this);
	}


	@Override
	public void serverDidEnd(String result) {
		// TODO Auto-generated method stub
		mFrameLayout.removeView(mProgressBar);
		if(result != null){
			try {

				JSONArray array = new JSONArray(result);
				for(int i = 0; i<array.length(); i++){
					JSONObject truck = array.getJSONObject(i);
					String truck_name = truck.getString("name");
					Truck.TruckType type;
					switch (truck.getInt("type")) {
					case 0:
						type = Truck.TruckType.MEAL;
						break;
					case 1:
						type = Truck.TruckType.DESERT;
						break;
					case 2:
						type = Truck.TruckType.BEVERAGE;
						break;
					default:
						type = Truck.TruckType.MEAL;
						break;
					}
					int truck_id = truck.getInt("id");
					String description = truck.getString("description");
					mTruckListFragment.addTruckToComingTruckList(truck_id, description, truck_name, type);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	

		if(!mTruckListFragment.isTruckEmpty()){
			mParentActivity.setIsHaveToStartFragment(true);
			
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.client_container, mTruckListFragment).commit();
		}else{
			mParentActivity.setIsHaveToStartFragment(true);
			getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.client_container, mRequestFragment).commit();
			
		}
	}

	@Override
	public void serverDidError(String error) {
		// TODO Auto-generated method stub
		
	}


}
