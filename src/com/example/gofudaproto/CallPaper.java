package com.example.gofudaproto;

import android.location.Location;

public class CallPaper {
	private Integer mCallId;
	public Boolean getIsActivated() {
		return mIsActivated;
	}

	public void setIsActivated(Boolean mIsActivated) {
		this.mIsActivated = mIsActivated;
	}

	public Integer getCallId() {
		return mCallId;
	}

	public String getEventName() {
		return mEventName;
	}

	public Integer getManNumber() {
		return mManNumber;
	}

	public Client getCallingClient() {
		return mCallingClient;
	}

	public Location getCallLocation() {
		return mCallLocation;
	}

	private Boolean mIsActivated;
	private String mEventName;
	private Integer mManNumber;
	private Client mCallingClient;
	private Truck mCalledTruck;
	private Location mCallLocation;
	
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
