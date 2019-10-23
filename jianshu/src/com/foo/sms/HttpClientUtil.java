package com.foo.sms;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	
	public static boolean isTest=Boolean.parseBoolean(SysConfig.getInstance().getProperty("is_test"));

	public static String postJson(String url, String body, String charset) {
		
		String result = null;
		
		if (isTest) {
			if (null == charset) {
				charset = "UTF-8";
			}
			CloseableHttpClient httpClient = null;
			HttpPost httpPost = null;
			try {
				httpClient = HttpConnectionManager.getInstance().getHttpClient();
				httpPost = new HttpPost(url);
				
				// �������ӳ�ʱ,���ö�ȡ��ʱ
				RequestConfig requestConfig = RequestConfig.custom()
						.setConnectTimeout(10000)	
		                .setSocketTimeout(10000)	
		                .build();
				httpPost.setConfig(requestConfig);
				
				httpPost.setHeader("Accept", "application/json");
				httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
				
				// ���ò���
				StringEntity se = new StringEntity(body, "UTF-8");
				httpPost.setEntity(se);
				HttpResponse response = httpClient.execute(httpPost);
				if (response != null) {
					HttpEntity resEntity = response.getEntity();
					if (resEntity != null) {
						result = EntityUtils.toString(resEntity, charset);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			result = "config.properties�� is_test ����ֵΪfalse, ������ȷ��������ֵ��������Ϊtrue���ٴβ���";
		}
		
		return result;
	}
}
