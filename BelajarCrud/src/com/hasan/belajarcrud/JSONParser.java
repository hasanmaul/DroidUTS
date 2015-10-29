package com.hasan.belajarcrud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONException;

import android.util.Log;

public class JSONParser {

	public JSONParser() {
		// TODO Auto-generated constructor stub
		
		static InputStream is = null;
		static JSONObject jobj = null;
		static String json = "";
		
		public JSONParser(){
			
		}
		
		public JSONObject makeHTTPRequest(String url,
				String method, List<NameValuePair> params) {
			
		
			try {
				if(method == "POST") {
					DefaultHttpCilent httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(url);
					httpPost.setEntity(new UrlEncodedFormEntity(params));
					
					
					HttpResponse HttpResponse = httpClient.execute(httpPost);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();
					
				} else if(method == "GET"){
					DefaultHttpCilent httpClient = new DefaultHttpCilent();
					String paramString = URLEncodedUtils.format(params, "utf-8");
					url 
					+= "?" + paramString;
					HttpGet httpGet = new HttpGet(url);
					
					HttpResponse httpResponse = httpClient.execute(httpGet);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();
				}
			
			
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolExeption e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			}
			
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());		
		}
		
		try {
			jobj = new JSONObject(json);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}
		
		return jobj;
	}

}
			// 		CopyRight Santoy @2015
			//		hasanlana95@gmail.com
