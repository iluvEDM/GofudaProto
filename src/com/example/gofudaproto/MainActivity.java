package com.example.gofudaproto;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	private Fragment mPrevFragment;
	private Fragment mStartFragment;
	private Fragment mIntroFragment;
	Boolean mIsHaveToBackFragment = false;
	Boolean mIsHaveToStartFragment = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mIntroFragment = new IntroFragment();
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, mIntroFragment).commit();
		}
	}
	public void setHaveToBack(Boolean isBack){
		mIsHaveToBackFragment = isBack;
	}
	public void setStartFragment(Fragment fragment){
		this.mStartFragment = fragment;
	}
	
	public void setPrevFragment(Fragment fragment){
		this.mPrevFragment = fragment;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		if(mIsHaveToBackFragment){
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.container, mPrevFragment).commit();
			mIsHaveToBackFragment = false;
		}else if(mIsHaveToStartFragment){
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.container, mStartFragment).commit();
			mIsHaveToStartFragment = false;
		}else{
			// TODO Auto-generated method stub
			super.onBackPressed();
		}
	}


}
