package com.taotao.search.activemq;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestActiveMqCosumer {

	@Test
	public void messageConsumer()throws Exception{
		//初始化容器
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
		//等待接收消息
		System.in.read();
	}
}
