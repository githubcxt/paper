package com.cxt.program;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class PostCookie {
	public static PostCookie postCookie;
	public  String r;
	public PostCookie(){
		r = (double)(Math.random())+"";
	}
	public static PostCookie getPostCookie(){
		if(postCookie==null){
			postCookie=new PostCookie();
		}
		return postCookie;
	}
	public String getCookie(String urlAddress){
		try {
			//建立连接并发送请求
			HttpURLConnection urlConnection = null;
			URL url = new URL(urlAddress);
			urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setConnectTimeout(5000);
			urlConnection.setReadTimeout(5000);
			urlConnection.setUseCaches(false);
			Map<String,List<String>> map = urlConnection.getHeaderFields();
			System.out.println(map);
			List<String> list = (List<String>)map.get("Set-Cookie");
			for(int i = 0; i < list.size(); i++){
				String temp = list.get(i).toString();
				String value[] = temp.split(";");
				return value[0];
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
