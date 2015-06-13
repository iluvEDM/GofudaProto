package com.example.gofudaproto;

import com.nhn.android.maps.maplib.NGeoPoint;

import android.location.Location;

public class CallPaper {
	private Integer mCallId;
	private Boolean mIsActivated;
	private String mEventName;
	public Integer getCallId() {
		return mCallId;
	}

	public void setCallId(Integer mCallId) {
		this.mCallId = mCallId;
	}

	public Boolean getIsActivated() {
		return mIsActivated;
	}

	public void setIsActivated(Boolean mIsActivated) {
		this.mIsActivated = mIsActivated;
	}

	public String getEventName() {
		return mEventName;
	}

	public void setEventName(String mEventName) {
		this.mEventName = mEventName;
	}

	public Integer getManNumber() {
		return mManNumber;
	}

	public void setManNumber(Integer mManNumber) {
		this.mManNumber = mManNumber;
	}

	public Client getCallingClient() {
		return mCallingClient;
	}

	public void setCallingClient(Client mCallingClient) {
		this.mCallingClient = mCallingClient;
	}

	public Truck getCalledTruck() {
		return mCalledTruck;
	}

	public void setCalledTruck(Truck mCalledTruck) {
		this.mCalledTruck = mCalledTruck;
	}

	public NGeoPoint getCallLocation() {
		return mCallLocation;
	}

	public void setCallLocation(NGeoPoint mCallLocation) {
		this.mCallLocation = mCallLocation;
	}

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
