package com.taotao.portal.exceptionresolver;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class GlobalExceptionResolver implements HandlerExceptionResolver{

	private static final Logger logger=LoggerFactory.getLogger(GlobalExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		//打印控制台
		ex.printStackTrace();
		//打印日志
		logger.debug("测试输出的日志。。。。。。。");
		logger.info("系统发生异常了。。。。。。。");
		logger.error("系统发生异常", ex);
		//发邮件、发短信
		//使用jmail工具包。发短信使用第三方的Webservice。
		//显示错误页面
		ModelAndView modelAndView = new ModelAndView();
		//modelandview既可以返回数据，也可以返回逻辑视图
		modelAndView.setViewName("error/exception");
		return modelAndView;
	}

}
