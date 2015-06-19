package com.example.gofudaproto.server;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.gofudaproto.MyApplication;







public class ServerManager
{
	public static interface OnServerManagerListener {
		void serverDidEnd(String result);
		void serverDidError(String error);
	}
	public static final String SERVER_ADDRESS = "http://52.69.4.83/";
	public static final String SEND_COMMENT_REPLY = "send_comment_reply.php";
	public static final String SHOW_ACCEPTED_REQUEST = "show_accepted_request.php";
	public static final String SHOW_COMING_TRUCKS = "show_coming_trucks.php";
	public static final String SHOW_COMPLETE_TRUCKS = "show_complete_trucks.php";
	public static final String SEND_TRUCK_NEW = "send_truck_new.php";
	public static final String SHOW_TRUCK_PROFILE = "show_truck_profile.php";
	public static final String SEND_COMMENT = "send_comment.php";
	public static final String SHOW_OLD_REQUESTS = "show_old_requests.php";
	public static final String SHOW_NEW_REVIEW_IDS = "show_new_review_ids.php";
	public static final String SHOW_REQUEST = "show_request.php";
	public static final String SEND_TRUCK_GO_REQUEST = "send_truck_go_request.php";
	public static final String SHOW_COMMENT_TARGETS = "show_comment_targets.php";
	public static final String SHOW_TRUCK_MENU = "show_truck_menu.php";
	public static final String SEND_COMMENT_NEW = "send_comment_new.php";
	public static final String SHOW_INCOMPLETE_REQUESTS = "show_incomplete_requests.php";
	public static final String SEND_CUSTOMER_NEW = "send_customer_new.php";
	public static final String SHOW_TRUCK_COMMENTS = "show_truck_comments.php";
	public static final String SEND_REQUEST = "send_request.php";
	public static final String SHOW_NEW_COMMENTS = "show_new_comments.php";
	public static final String SEND_CUSTOMER_COME_REQUEST = "send_customer_come_request.php";
	public static final String CANCEL_REQUEST = "cancel_request.php";
	
	
	public void doSendCall(final String function, final String param, final OnServerManagerListener listener){
		Log.d("params string :"+ function, param);
		AsyncTask<String, Void, String> sendTask = new AsyncTask<String, Void, String>()
		{
			String serverURL = SERVER_ADDRESS + function;
			DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
			InputStream inputStream = null;
			String result = null;
			
			@Override
			protected String doInBackground(String... params)
			{
				try
				{
					URL url = new URL(serverURL);
					HttpURLConnection http = (HttpURLConnection) url.openConnection();
					http.setDefaultUseCaches(false);
					http.setConnectTimeout(5000);
					http.setReadTimeout(5000);
					http.setDoInput(true);
					http.setDoOutput(true);
					http.setRequestMethod("POST");
					http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
					
					
					StringBuffer buffer = new StringBuffer();
					buffer.append(URLEncoder.encode("JSON","UTF-8"));
					buffer.append("=");
					buffer.append(URLEncoder.encode(param,"UTF-8"));
					OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "utf-8");
					PrintWriter writer = new PrintWriter(outStream);
					writer.write(buffer.toString());
					writer.flush();
					writer.close();
					InputStreamReader tmp = new InputStreamReader(
							http.getInputStream(), "utf-8");
					BufferedReader reader = new BufferedReader(tmp);
					StringBuilder builder = new StringBuilder();
					String str;
					while ((str = reader.readLine()) != null) {
						builder.append(str);
					}
					reader.close();
					tmp.close();

					return builder.toString();
					
				}  catch (Exception e)
				{
					defaultHttpClient.getConnectionManager().shutdown();
					listener.serverDidError(e.getMessage());
					e.printStackTrace();
				} finally {
					try {
						if (inputStream != null)
							inputStream.close();
					} catch (Exception squish) {
					}
				}
				return null;
			}
			@Override
			protected void onPostExecute(String result)
			{
				super.onPostExecute(result);
				listener.serverDidEnd(result);
				Log.d("server ended result : ",""+ result);
			}
		};
		
		if (isNetworkStat(MyApplication.getAppContext()))
			sendTask.execute();
		else {
			listener.serverDidError("network error");
		}
	}
	
	public Drawable loadImage(String imageName) {
		return null;
	}
	
	

	public static boolean isNetworkStat(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		NetworkInfo lte_4g = manager.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);
		boolean blte_4g = false;
		if (lte_4g != null)
			blte_4g = lte_4g.isConnected();
		if (mobile != null) {
			if (mobile.isConnected() || wifi.isConnected() || blte_4g)
				return true;
		} else {
			if (wifi.isConnected() || blte_4g)
				return true;
		}

		return false;
	}
}
