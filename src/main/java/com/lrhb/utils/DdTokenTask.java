package com.lrhb.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.lrhb.utils.list.JedisClient;
import com.lrhb.utils.list.JedisClientPool;

/**
 * 定时获取钉钉的token
 */
@Component
@EnableScheduling
public class DdTokenTask {

	@Autowired
	private JedisClientPool jedisClientPool;

	public static final long cacheTime = 1000 * 60 * 10;// 1小时50分钟
	// public static final long cacheTime = 1000 * 60 * 55 * 2;//1小时50分钟

	private String tokenUrl = "https://oapi.dingtalk.com/gettoken";
	private String appKey = "dinguoijxfzelwf98gzu";
	private String appSecret = "BZ3_1BB_OJpOwdrtFl1e25W2BFFF0GTiTgn4HOeJ4l_YdnYoDWg7bB2zWAOHIAsY";
	private String tokenKey = "ddTokenKey";
	private String taskRun = "true";

	/**
	 * 每隔1小时50分钟获取钉钉的access_token
	 */
	@Scheduled(fixedRate = cacheTime)
	@Async
	public void getDdTokenTask() {
		if ("true".equals(taskRun)) {
			// 刷新
			System.out.println("--->>>>>-------------获取钉钉token的定时任务开始了：" + DateUtil.date2Str(new Date(), "HH:mm:ss"));

			DefaultDingTalkClient client = new DefaultDingTalkClient(tokenUrl);
			OapiGettokenRequest request = new OapiGettokenRequest();
			request.setAppkey(appKey);
			request.setAppsecret(appSecret);
			request.setHttpMethod("GET");
			OapiGettokenResponse response = null;
			try {
				response = client.execute(request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(response.getAccessToken());

			String accessToken = response.getAccessToken();

			// 放入到redis中
			jedisClientPool.set(tokenKey, accessToken);

			System.out.println("--->>>>>-------------获取钉钉token的定时任务结束了，token：" + accessToken);
//			String accessTokenUrl = tokenUrl + "?appkey=" + appKey + "&appsecret=" + appSecret;
//			访问获取access_token 有效期是2小时
//			String response = HttpUtils.sendGet(accessTokenUrl, "");
//			System.out.println(response);
// 			System.out.println("--->>>>>-------------获取钉钉token的定时任务结束了，token："+accessToken);
		}
	}

}
