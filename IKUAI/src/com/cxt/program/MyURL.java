package com.cxt.program;

public class MyURL {
	public static MyURL myurl;
	public String login = "http://192.168.1.1/login";
	public String shell_send = "http://192.168.1.1/login/shell_send";
	public String loginx = "http://192.168.1.1/login/x";
	public String index = "http://192.168.1.1/index";
	public String ACLindex = "http://192.168.1.1/index.php/Firewall/acl";
	public String ACLManage = "http://192.168.1.1/index.php/Firewall/acl/api";
	public String page_data="http://192.168.1.1/index.php/Firewall/acl/page_data";
	
	public static MyURL getMyURL(){
		if(myurl==null){
			myurl = new MyURL();
		}
		return myurl;
	}
}
