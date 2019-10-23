package com.taotao.sso.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;

/**
 * 用户管理controller
 * @author LWABCD
 *
 */

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Value("${PORTAL_BASE_URL}")
	private String PORTAL_BASE_URL;
	
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public Object checkData(@PathVariable String param,@PathVariable Integer type,String callback) {
		TaotaoResult result=null;
		//参数有效性校验
		if(StringUtils.isBlank(param)) {
			result=TaotaoResult.build(400,"校验内容不能为空");
		}
		if(type==null) {
			result=TaotaoResult.build(400,"校验内容类型不能为空");
		}
		if(type!=1&&type!=2&&type!=3) {
			result=TaotaoResult.build(400,"校验内容类型错误");
		}
		//校验出错
		if(result!=null) {
			if(callback!=null) {
				MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
				mappingJacksonValue.setJsonpFunction(callback);
				return mappingJacksonValue;
			}else {
				return result;
			}
		}
		//调用服务
		try {
			result=userService.checkData(param, type);
		} catch (Exception e) {
			result =TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
		}
		if(callback!=null) {
			MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}else {
			return result;
		}
	}
	
	@RequestMapping("/register")
	@ResponseBody
	public TaotaoResult createUser(TbUser user) {
		try {
			TaotaoResult result=userService.createUser(user);
			return result;
		} catch (Exception e) {
			return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
		}
	}
	
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult userLogin(String username,String password,
			HttpServletRequest request,HttpServletResponse response) {
		try {
			TaotaoResult result=userService.userLogin(username, password,request,response);
			return result;
		} catch (Exception e) {
			return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
		}
	}
	
	@RequestMapping("/token/{token}")
	@ResponseBody
	public Object getUserByToken(@PathVariable String token,String callback) {
		TaotaoResult result=null;
		try {
			result=userService.getUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result=TaotaoResult.build(400,ExceptionUtil.getStackTrace(e));
		}
		//判断是否为jsonp调用
		if(StringUtils.isBlank(callback)) {
			return result;
		}
		MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
	}
	
	/**
	 * 用户退出
	 * @throws IOException 
	 */
	@RequestMapping("/logout/{token}")
	@ResponseBody
	public TaotaoResult logout(@PathVariable String token,HttpServletRequest request,HttpServletResponse response) throws IOException {
		TaotaoResult result=userService.logout(token, request, response);
		return result;
	}
}
