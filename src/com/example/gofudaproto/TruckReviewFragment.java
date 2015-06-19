package com.example.gofudaproto;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gofudaproto.server.ServerManager;
import com.example.gofudaproto.server.ServerManager.OnServerManagerListener;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link TruckReviewFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link TruckReviewFragment#newInstance} factory method to create an instance
 * of this fragment.
 *
 */
public class TruckReviewFragment extends android.support.v4.app.Fragment implements OnServerManagerListener{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;
	private ListView mReviewList;
	private BaseAdapter mAdapter;
	private ArrayList<ClientReviewView> mReviewArray;
	private ArrayList<Integer> mReviewIds;
	private MainActivity mParentActivity;
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment TruckReviewFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static TruckReviewFragment newInstance(String param1, String param2) {
		TruckReviewFragment fragment = new TruckReviewFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public TruckReviewFragment() {
		// Required empty public constructor
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mParentActivity.setIsHaveToStartFragment(true);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		mParentActivity = (MainActivity)getActivity();
		mReviewArray = new ArrayList<ClientReviewView>();
		mReviewIds = new ArrayList<Integer>();
	}
	

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mReviewList = (ListView)getActivity().findViewById(R.id.event_calls);
		mAdapter = new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				return mReviewArray.get(position);
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mReviewArray.size();
			}
		};
		
		mReviewList.setAdapter(mAdapter);
		sendGetReviewList();
	}
	
	//역시 1 대신 해당 트럭 아이디 값이 들어가야 함. 
	public void sendGetReviewList(){
		mParentActivity.getServerManager().doSendCall(ServerManager.SHOW_NEW_COMMENTS, makeShowingTrucksParameter(1), this);
	}
	private String makeShowingTrucksParameter(int request_id){
		return "{"+ makeIndexString("truck_id") + ":"+makeIndexString(String.valueOf(request_id)) +"}";
	}
	private String makeIndexString(String word){
		return " \""+word+"\"";
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.truck_review, container,
				false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
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

	@Override
	public void serverDidEnd(String result) {
		// TODO Auto-generated method stub
		if(result != null){
			try {
				JSONArray array = new JSONArray(result);
				for (int i = 0; i < array.length(); i++) {
					JSONObject jobj = array.getJSONObject(i);
					ClientReviewView crv = new ClientReviewView(getActivity().getApplicationContext());
					crv.id = jobj.getInt("id");
					crv.truck_id = jobj.getInt("truck_id");
					crv.request_id = jobj.getInt("request_id");
					crv.customer_id = jobj.getInt("customer_id");
					crv.setStar(jobj.getString("star"));
					crv.setContentDescription(jobj.getString("content"));
					mReviewArray.add(crv);
				}
				
				mAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@Override
	public void serverDidError(String error) {
		// TODO Auto-generated method stub
		
	}

}
