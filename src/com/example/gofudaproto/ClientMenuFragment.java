package com.example.gofudaproto;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the {@link ClientMenuFragment.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link ClientMenuFragment#newInstance} factory method to create an instance
 * of this fragment.
 *
 */
public class ClientMenuFragment extends android.support.v4.app.Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;
	private MainActivity mParentActivity;
	private Button mMealButton;
	private Button mDesertButton;
	private Button mBeverageButton;
	private OnClickListener mButtonListener;
	private ClientMenuDescriptionFragment mDescriptionFragment;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mMealButton = (Button)getActivity().findViewById(R.id.menu_meal_button);
		mDesertButton = (Button)getActivity().findViewById(R.id.menu_desert_button);
		mBeverageButton = (Button)getActivity().findViewById(R.id.menu_beverage_button);
		mButtonListener = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.menu_meal_button:
					mParentActivity.setBeforeContainer(R.id.client_container);
					mParentActivity.setIsHaveToBackFragment(true);
					makeThisFragmentToPrevFragment();
					mDescriptionFragment.setDescription("this is meal");
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.client_container, mDescriptionFragment).commit();
					break;
				case R.id.menu_desert_button:
					mParentActivity.setBeforeContainer(R.id.client_container);
					mParentActivity.setIsHaveToBackFragment(true);
					makeThisFragmentToPrevFragment();
					mDescriptionFragment.setDescription("this is desert");
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.client_container, mDescriptionFragment).commit();
					break;
				case R.id.menu_beverage_button:
					mParentActivity.setBeforeContainer(R.id.client_container);
					mParentActivity.setIsHaveToBackFragment(true);
					makeThisFragmentToPrevFragment();
					mDescriptionFragment.setDescription("this is beverage");
					getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.client_container, mDescriptionFragment).commit();
					break;
				default:
					break;
				}
			}
		};
		mMealButton.setOnClickListener(mButtonListener);
		mDesertButton.setOnClickListener(mButtonListener);
		mBeverageButton.setOnClickListener(mButtonListener);
	}
	private void makeThisFragmentToPrevFragment(){
		mParentActivity.setPrevFragment(this);
	}
	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment ClientMenuFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static ClientMenuFragment newInstance(String param1, String param2) {
		ClientMenuFragment fragment = new ClientMenuFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public ClientMenuFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
		mDescriptionFragment = new ClientMenuDescriptionFragment();
		mParentActivity = (MainActivity)getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater
				.inflate(R.layout.fragment_client_menu, container, false);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		mParentActivity.setIsHaveToStartFragment(true);
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

}
