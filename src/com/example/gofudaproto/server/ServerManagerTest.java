package com.example.gofudaproto.server;

import android.util.Log;

public class ServerManagerTest
{
	public static void test1(){
		ServerManager sm = new ServerManager();
		OnServerManagerListener listener = new OnServerManagerListener()
		{
			
			@Override
			public void serverDidError(String error)
			{
				// TODO Auto-generated method stub
				Log.i("server", "result = " + error);
			}
			
			@Override
			public void serverDidEnd(String result)
			{
				// TODO Auto-generated method stub
				Log.i("result","result = " + result);
			}
		};
		
		sm.doSendCall(ServerManager.SHOW_TRUCK_MENU, "{\"truck_id\":\"1\"}", listener);
	}
}
