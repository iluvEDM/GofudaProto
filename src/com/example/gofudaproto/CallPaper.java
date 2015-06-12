package com.example.gofudaproto;

import com.nhn.android.maps.maplib.NGeoPoint;

import android.location.Location;

public class CallPaper {
	private Integer mCallId;
	private Boolean mIsActivated;
	private String mEventName;
	private Integer mManNumber;
	private Client mCallingClient;
	private Truck mCalledTruck;
	private NGeoPoint mCallLocation;
	
	public CallPaper(){
		mCallId = 0;
		mIsActivated = false;
		mEventName = null;
		mManNumber = 0;
		mCallingClient = null;
		mCalledTruck = null;
		mCallLocation = null;
	}
}
