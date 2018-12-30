package com.cxt.program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 注释的语句都是一些测试和附加功能，可删除
 * url都是通过httpwatch捕获的
 * cookie都是通过fiddler抓取的
 * @author Administrator
 *
 */
public class JavaHttpHandle{
	public String rr;
	public static void main(String[] args) {
		JavaHttpHandle jhh = new JavaHttpHandle();
		MyURL myurl = MyURL.getMyURL();
		PostCookie postcookie = PostCookie.getPostCookie();
		
		//获取cookie
		System.out.println("从shell_send中的Set-Cookie中抓取cookie:");
		System.out.println("shell_send头部:");
		String cookie=postcookie.getCookie(myurl.shell_send);
		System.out.println("登陆首页时的cookie:"+cookie);
		
		//输入用户名，密码进行登录
		jhh.sendPost(myurl.loginx,cookie,"user=admin&pass=admin");//按理说post不需要输出，这里为了未重载sendPost函数，所以进行了输出
		System.out.println("===========================================");
		
		//在进入index后加载了5遍/api.php，才产生的_num=5,所以在登陆index后加载api.php之前，都不需要传_num=5到cookie里，直到进入ACL页面才需要
		//注意：在第一次运行本程序之后，浏览器会缓存下cookie，因此在第二次运行本程序后，不必下面的语句，写不写都可以。
		cookie = cookie+";_num=5";
		
		
		System.out.println("登陆ACL管理界面的cookie:"+cookie);
		System.out.println("正在添加post中的body......");
		//这里进行的是停用id=1的ACL规则操作，启用同理，将down换位up即可
		jhh.sendPost(myurl.ACLManage, cookie,"id=1&type=down&r="+postcookie.r);
		
		System.out.println("post成功....");
		System.out.println("===========================================");
		System.out.println("返回ACL主页，查看HTML源码");
		jhh.sendGet(myurl.ACLindex,cookie);	
	}

	
	//Get请求
	public void sendGet(String urlAddress,String cookie){
			try {
				//建立连接并发送请求
				HttpURLConnection urlConnection = null;
				URL url = new URL(urlAddress);
				urlConnection = (HttpURLConnection)url.openConnection();
				urlConnection.setConnectTimeout(5000);
				urlConnection.setReadTimeout(5000);
				urlConnection.setUseCaches(false);
				//这里Cookie的值每次清除后，都会更新
				urlConnection.setRequestProperty("Cookie", cookie);
				urlConnection.connect();
				
				//获取相应的内容
				BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
				String line = br.readLine();
				while(line!=null){
					System.out.println(line);
					line = br.readLine();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	//Post请求
	public void sendPost(String urlAddress,String cookie,String paramValue){
		try{
			//建立连接并发送请求
			HttpURLConnection urlConnection = null;
			URL url = new URL(urlAddress);
			urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setConnectTimeout(5000);
			urlConnection.setReadTimeout(5000);
			urlConnection.setUseCaches(false);
			//传入cookie
			urlConnection.setRequestProperty("Cookie", cookie);
			urlConnection.connect();
			
			//将参数paramValue传入
			PrintWriter pw = new PrintWriter(urlConnection.getOutputStream());
			pw.print(paramValue);
			pw.flush();
			
			//获取相应的内容
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
			String line = br.readLine();
			while(line!=null){
				System.out.println(line);
				line = br.readLine();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
