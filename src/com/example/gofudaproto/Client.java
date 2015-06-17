package com.example.gofudaproto;

import android.R.menu;

public class Client {
	private int mId;
	private int mPhone;
	private int mEmail;
	private String mDevice_id;
	private int mCurrentRequestId;
	public Client(){
		mId = 0;
		mPhone = 0;
		mEmail = 0;
		mDevice_id = "";
		mCurrentRequestId = 0;
	}
	
	public void setDeviceID(String did){
		mDevice_id = did;
	}
	public void setId(int id){
		mId = id;
	}
	public void setMail(int mail){
		mEmail = mail;
	}
	public void setPhone(int phone){
		mPhone = phone;
	}
	public void setCurrentRequest(int id){
		mCurrentRequestId = id;
	}
	
	
	
	
}
