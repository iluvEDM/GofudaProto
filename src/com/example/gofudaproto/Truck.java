package com.example.gofudaproto;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;


public class Truck {
	public enum TruckType {
	    MEAL, DESERT , BEVERAGE
	}
	TruckType mType;
	private int mId;
	private int mDevice_id;
	private String mName;
	private TruckProfile mProfile;
	
	public Truck(){
		mType = TruckType.MEAL;
		mId = 0;
		mDevice_id = 0;
		mName = "";
		mProfile = new TruckProfile();
	}
	
	public void setReady(int id, String des, String name , TruckType type){
		mId = id;
		mName = name;
		mProfile.description = des;
		mType = type;
	}
	public String getName(){
		return mName;
	}
	public int getID(){
		return mId;
	}
	
	public String getDescription(){
		return mProfile.description;
	}
	
	private class TruckProfile{
		public int truck_id;
		public ImageView cart_img;
		public ImageView menu_img;
		public String description;
		
		public TruckProfile(){
			truck_id = 0;
			cart_img = null;
			menu_img = null;
			description = "";
		}
		
	}


}