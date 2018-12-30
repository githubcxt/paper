package com.cxt.program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ע�͵���䶼��һЩ���Ժ͸��ӹ��ܣ���ɾ��
 * url����ͨ��httpwatch�����
 * cookie����ͨ��fiddlerץȡ��
 * @author Administrator
 *
 */
public class JavaHttpHandle{
	public String rr;
	public static void main(String[] args) {
		JavaHttpHandle jhh = new JavaHttpHandle();
		MyURL myurl = MyURL.getMyURL();
		PostCookie postcookie = PostCookie.getPostCookie();
		
		//��ȡcookie
		System.out.println("��shell_send�е�Set-Cookie��ץȡcookie:");
		System.out.println("shell_sendͷ��:");
		String cookie=postcookie.getCookie(myurl.shell_send);
		System.out.println("��½��ҳʱ��cookie:"+cookie);
		
		//�����û�����������е�¼
		jhh.sendPost(myurl.loginx,cookie,"user=admin&pass=admin");//����˵post����Ҫ���������Ϊ��δ����sendPost���������Խ��������
		System.out.println("===========================================");
		
		//�ڽ���index�������5��/api.php���Ų�����_num=5,�����ڵ�½index�����api.php֮ǰ��������Ҫ��_num=5��cookie�ֱ������ACLҳ�����Ҫ
		//ע�⣺�ڵ�һ�����б�����֮��������Ỻ����cookie������ڵڶ������б�����󣬲����������䣬д��д�����ԡ�
		cookie = cookie+";_num=5";
		
		
		System.out.println("��½ACL��������cookie:"+cookie);
		System.out.println("�������post�е�body......");
		//������е���ͣ��id=1��ACL�������������ͬ����down��λup����
		jhh.sendPost(myurl.ACLManage, cookie,"id=1&type=down&r="+postcookie.r);
		
		System.out.println("post�ɹ�....");
		System.out.println("===========================================");
		System.out.println("����ACL��ҳ���鿴HTMLԴ��");
		jhh.sendGet(myurl.ACLindex,cookie);	
	}

	
	//Get����
	public void sendGet(String urlAddress,String cookie){
			try {
				//�������Ӳ���������
				HttpURLConnection urlConnection = null;
				URL url = new URL(urlAddress);
				urlConnection = (HttpURLConnection)url.openConnection();
				urlConnection.setConnectTimeout(5000);
				urlConnection.setReadTimeout(5000);
				urlConnection.setUseCaches(false);
				//����Cookie��ֵÿ������󣬶������
				urlConnection.setRequestProperty("Cookie", cookie);
				urlConnection.connect();
				
				//��ȡ��Ӧ������
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
	//Post����
	public void sendPost(String urlAddress,String cookie,String paramValue){
		try{
			//�������Ӳ���������
			HttpURLConnection urlConnection = null;
			URL url = new URL(urlAddress);
			urlConnection = (HttpURLConnection)url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setConnectTimeout(5000);
			urlConnection.setReadTimeout(5000);
			urlConnection.setUseCaches(false);
			//����cookie
			urlConnection.setRequestProperty("Cookie", cookie);
			urlConnection.connect();
			
			//������paramValue����
			PrintWriter pw = new PrintWriter(urlConnection.getOutputStream());
			pw.print(paramValue);
			pw.flush();
			
			//��ȡ��Ӧ������
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
