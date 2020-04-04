package com.lrhb.utils;

import java.util.List;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiUserListbypageRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse;
import com.dingtalk.api.response.OapiUserListbypageResponse.Userlist;
import com.taobao.api.ApiException;

/**
 * 钉钉获取数据
 * @author 15437
 *
 */
public class Dingding { 
	private static String appkey="dinguoijxfzelwf98gzu"; //应用的唯一标识key
	private static String appsecret="BZ3_1BB_OJpOwdrtFl1e25W2BFFF0GTiTgn4HOeJ4l_YdnYoDWg7bB2zWAOHIAsY";//应用的密钥
	
	public static void main(String[] args) {
		OapiGettokenResponse data=gettoken();
		System.out.println(data.toString());
		if(data.getErrcode().toString().equals("0")) {
			DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/listbypage");
			OapiUserListbypageRequest request = new OapiUserListbypageRequest();
			request.setDepartmentId(1L);
			request.setOffset(0L);
			request.setSize(10L);
			request.setOrder("entry_desc");
			request.setHttpMethod("GET");
			try {
				OapiUserListbypageResponse execute = client.execute(request,data.getAccessToken());
				System.out.println(execute.getErrorCode().toString());
				if(execute.getErrcode().toString().equals("0")) {
					List<Userlist> userList=execute.getUserlist();
					for (int i = 0; i < userList.size(); i++) {
						System.out.println("姓名："+userList.get(i).getName());
						System.out.println("用户id："+userList.get(i).getUserid());
						System.out.println("邮箱：："+userList.get(i).getEmail());
						System.out.println("电话："+userList.get(i).getMobile());
						System.out.println("工号："+userList.get(i).getJobnumber());
						System.out.println("————————————————————————————————————");
					}
				}else {
					System.out.println(execute.getErrmsg());
				}
				
			} catch (ApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println(data.getErrmsg());
		}
	}
	public static OapiGettokenResponse gettoken() {
		
		DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
		OapiGettokenRequest request = new OapiGettokenRequest();
		request.setAppkey(appkey);
		request.setAppsecret(appsecret);
		request.setHttpMethod("GET");
		OapiGettokenResponse response = null;
		try {
			response = client.execute(request);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(response.getAccessToken());
		return response;
	}
}
