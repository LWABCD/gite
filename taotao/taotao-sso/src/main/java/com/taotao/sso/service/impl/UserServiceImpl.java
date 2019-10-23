package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;

import redis.clients.jedis.Jedis;

/**
 * 用户管理service
 * @author LWABCD
 *
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;
	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE;
	
	@Override
	public TaotaoResult checkData(String content, Integer type) {
		//创建查询条件
		TbUserExample example=new TbUserExample();
		Criteria criteria=example.createCriteria();
		//可选参数1、2、3分别代表username、phone、email
		//用户名校验
		if(1==type) {
			criteria.andUsernameEqualTo(content);
		//电话号码校验
		}else if(2==type) {
			criteria.andPhoneEqualTo(content);
		//email校验
		}else {
			criteria.andEmailEqualTo(content);
		}
		List<TbUser> list=userMapper.selectByExample(example);
		//没被注册过，数据可用
		if(list==null||list.size()==0) {
			return TaotaoResult.ok(true);
		}
		//数据不可用
		return TaotaoResult.ok(false);
	}

	@Override
	public TaotaoResult createUser(TbUser user) {
		user.setUpdated(new Date());
		user.setCreated(new Date());
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult userLogin(String username, String password,
			HttpServletRequest request,HttpServletResponse response) {
		TbUserExample example=new TbUserExample();
		Criteria criteria=example.createCriteria();
		criteria.andUsernameEqualTo(username);
		//根据用户名查询用户
		List<TbUser> list=userMapper.selectByExample(example);
		//该用户不存在
		if(list==null||list.size()==0) {
			return TaotaoResult.build(400,"用户名或密码错误");
		}
		TbUser user=list.get(0);
		//比对密码
		if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
			return TaotaoResult.build(400,"用户名或密码错误");
		}
		//生成token
		String token=UUID.randomUUID().toString();
		//保存用户之前，为安全起见，把用密码清空
		user.setPassword(null);
		//把用户信息写入redis
		jedisClient.set(REDIS_USER_SESSION_KEY+":"+token,JsonUtils.objectToJson(user));
		//设置session的过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY+":"+token,SSO_SESSION_EXPIRE);
		
		//向cookie中添加token,表示用户已登录，不设置有效期，默认为浏览器关闭就失效
		CookieUtils.setCookie(request, response, "TT_TOKEN", token);
		//返回token
		return TaotaoResult.ok(token);
	}

	@Override
	public TaotaoResult getUserByToken(String token) {
		//根据token从redis中查询用户信息
		String json=jedisClient.get(REDIS_USER_SESSION_KEY+":"+token);
		if(StringUtils.isBlank(json)) {
			return TaotaoResult.build(400,"此session已经过期，请重新登录");
		}
		//重新设置过期时间
		jedisClient.expire(REDIS_USER_SESSION_KEY+":"+token,SSO_SESSION_EXPIRE);
		//返回
		return TaotaoResult.ok(JsonUtils.jsonToPojo(json,TbUser.class));
	}

	@Override
	public TaotaoResult logout(String token,HttpServletRequest request,HttpServletResponse response) {
		//删除redis中用户登录过后产生的token
		jedisClient.del(REDIS_USER_SESSION_KEY+":"+token);
		//删除cookie中的token，表示用户已退出
		CookieUtils.deleteCookie(request, response, REDIS_USER_SESSION_KEY+":"+token);
		return TaotaoResult.ok();
	}
}
