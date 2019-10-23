package com.foo.util;


import java.io.IOException;


import com.foo.sms.AbsRestClient;
import com.foo.sms.JsonReqClient;

public class SendValiCode {

	static AbsRestClient InstantiationRestAPI() {
		return new JsonReqClient();
	}

	public static void testSendSms(String sid, String token, String appid, String templateid, String param,
			String mobile, String uid) {
		try {
			String result = InstantiationRestAPI().sendSms(sid, token, appid, templateid, param, mobile, uid);
			System.out.println("Response content is: " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����˵�� ����main���������ڿ���̨��������(���ֶ�Ӧ ��Ӧ�ĵ��÷���)���س������� �������ƺ��壬��ο�rest api �ĵ�
	 * 
	 * @throws IOException
	 * @method main
	 */
	public static void main(String[] args) throws IOException {
		String valicodeString=GenerateValiCode.getCode();
		SendSms(valicodeString,"18398222379");
		
	}

	public static void SendSms(String code,String phone) {
		String sid = "d783812e19132600fe2528a3f824d079";
		String token = "4a5f13f73a5ad1cba51098e963887a46";
		String appid = "4f9e6888b2404af89b92e0fdb5d5dedd";
		String templateid = "484720";
		String param = code;
		String mobile = phone;
		String uid = "";
		testSendSms(sid, token, appid, templateid, param, mobile, uid);
	}
}
