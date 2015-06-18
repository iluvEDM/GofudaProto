package com.example.gofudaproto;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.gofudaproto.ClientReviewView.SendReviewListener;
import com.example.gofudaproto.server.ServerManager;
import com.example.gofudaproto.server.ServerManager.OnServerManagerListener;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link ClientReviewFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link ClientReviewFragment#newInstance} factory method to create an instance
 * of this fragment.
 *
 */
public class ClientReviewFragment extends android.support.v4.app.Fragment implements OnServerManagerListener,SendReviewListener{
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	private ArrayList<ClientReviewView> mReviewViewArray;
	private OnFragmentInteractionListener mListener;
	
	private ListView mListView;
	private MainActivity mParentActivity;
	private int mRequestID = 0;
	private BaseAdapter mAdapter;
	private Button mSelectMealButton;
	private Button mSelectDesertButton;
	private Button mSelectBeverageButton;
	private OnClickListener mButtonListener;
	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment ClientReviewFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ClientReviewFragment newInstance(String param1, String param2) {
		ClientReviewFragment fragment = new ClientReviewFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public ClientReviewFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);		
		}
		mParentActivity = (MainActivity)getActivity();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mSelectMealButton = (Button)getActivity().findViewById(R.id.review_client_meal);
		mSelectDesertButton = (Button)getActivity().findViewById(R.id.review_client_desert);
		mSelectBeverageButton = (Button)getActivity().findViewById(R.id.review_client_beverage);
		mButtonListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.review_client_meal:
					filteringReviewsByTruckType(Truck.TruckType.MEAL);
					break;
				case R.id.review_client_desert:
					filteringReviewsByTruckType(Truck.TruckType.DESERT);
					break;
				case R.id.review_client_beverage:
					filteringReviewsByTruckType(Truck.TruckType.BEVERAGE);
					break;

				default:
					break;
				}
			}
		};
		mSelectMealButton.setOnClickListener(mButtonListener);
		mSelectDesertButton.setOnClickListener(mButtonListener);
		mSelectBeverageButton.setOnClickListener(mButtonListener);
		mRequestID = getActivity().getSharedPreferences("gopuda", Context.MODE_PRIVATE).getInt(MainActivity.CLIENT_REQUEST_ID_INDEX, 0);
		mReviewViewArray = new ArrayList<>();
		mAdapter = new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				return mReviewViewArray.get(position);
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
				return mReviewViewArray.size();
			}
		};	
		mListView = (ListView)getActivity().findViewById(R.id.review_detail_list);
		mListView.setAdapter(mAdapter);
		getAllReviewsFromServer();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_client_review, container,
				false);
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mParentActivity.setIsHaveToStartFragment(true);
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
	private String makeIndexString(String word){
		return " \""+word+"\"";
	}
	public void getAllReviewsFromServer(){
		mParentActivity.getServerManager().doSendCall(ServerManager.SHOW_COMMENT_TARGETS, makeCommentTargetParams(mRequestID), this);
	}
	public String makeCommentTargetParams(int request_id){
		return "{"+ makeIndexString("request_id") + ":"+makeIndexString(String.valueOf(request_id)) +"}";
	}
	public void filteringReviewsByTruckType(Truck.TruckType type){
		
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
		try {
			JSONArray array = new JSONArray(result);
			for (int i = 0; i < array.length() ; i++) {
				JSONObject jobj = array.getJSONObject(i);
//				 "id"=>$input["id"],
//					        "truck_id"=>$input["truck_id"],
//					        "request_id"=>$input["request_id"],
//					        "customer_id"=>$input["customer_id"],
//					        "star"=>$input["star"],
//					        "content"=>$input["content"],
//					        "reply"=>$input["reply"],
//					        "time"=>$input["time"]
				int id = jobj.getInt("id");
				int truck_id = jobj.getInt("truck_id");
				int request_id = jobj.getInt("request_id");
				int customer_id = jobj.getInt("customer_id");
				int star = jobj.getInt("star");
				String content = jobj.getString("content");
				String reply = jobj.getString("reply");
				int time = jobj.getInt("time");
				ClientReviewView crv = new ClientReviewView(mParentActivity.getApplicationContext());
				crv.id = id;
				crv.truck_id = truck_id;
				crv.request_id = request_id;
				crv.customer_id = customer_id;
				crv.setStar(String.valueOf(star));
				crv.setDescription(content);
				mReviewViewArray.add(crv);
			}
			
			mAdapter.notifyDataSetChanged();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void serverDidError(String error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendThisReviewToServer(int id, String content) {
		// TODO Auto-generated method stub
		mParentActivity.getServerManager().doSendCall(ServerManager.SHOW_COMMENT_TARGETS, makeCommentTargetParams(mRequestID), this);
	}
	public String makeRegisterReviewParam(int id,String content){
		return "{"+ makeIndexString("id") + ":"+makeIndexString(String.valueOf(id)) 
				+ makeIndexString("content") + ":"+makeIndexString(content) +"}";
	}

}
