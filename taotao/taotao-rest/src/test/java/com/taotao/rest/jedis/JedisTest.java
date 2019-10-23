package com.taotao.rest.jedis;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	/**
	 * 测试单机版
	 */
	@Test
	public void testJedis() {
		//创建一个jedis对象
		Jedis jedis=new Jedis("192.168.2.130",6379);
		//调用jedis对象的方法，方法名称和redis的命令一致
		jedis.set("key1","jedis test");
		System.out.println(jedis.get("key1"));
		//关闭jedis
		jedis.close();
	}
	
	/**
	 * 使用连接池
	 */
	@Test
	public void testJedisPool() {
		//创建连接池
		JedisPool jedisPool=new JedisPool("192.168.2.130",6379);
		Jedis jedis=jedisPool.getResource();
		System.out.println(jedis.get("key1"));
		jedis.close();
		jedisPool.close();
	}
	
	/**
	 * 测试集群
	 */
	@Test
	public void testJedisCluster() {
		HashSet<HostAndPort> nodes=new HashSet<>();
		nodes.add(new HostAndPort("192.168.2.130", 7001));
		nodes.add(new HostAndPort("192.168.2.130", 7002));
		nodes.add(new HostAndPort("192.168.2.130", 7003));
		nodes.add(new HostAndPort("192.168.2.130", 7004));
		nodes.add(new HostAndPort("192.168.2.130", 7005));
		nodes.add(new HostAndPort("192.168.2.130", 7006));
		JedisCluster jedisCluster=new JedisCluster(nodes);
		jedisCluster.set("key1", "1000");
		System.out.println(jedisCluster.get("key1"));
		jedisCluster.close();
	}
	
	/**
	 * 单机版
	 */
	@Test
	public void testSpringJedisSingle() {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool jedisPool=(JedisPool)applicationContext.getBean("redisClient");
		Jedis jedis=jedisPool.getResource();
		System.out.println(jedis.get("key1"));
		jedis.close();
		jedisPool.close();
		
	}
	
	/**
	 * 集群版
	 */
	@Test
	public void testSpringJedisCluster() {
		ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisCluster jedisCluster=(JedisCluster)applicationContext.getBean("redisClient");
		System.out.println(jedisCluster.get("key1"));
		jedisCluster.close();
	}
	
}
