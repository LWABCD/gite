package com.grbk.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
 import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcWebConfigSupport implements WebMvcConfigurer {

/**
* 默认访问的是首页 //保留了SpringBoot的自动配置，也使用了自己的SpringMmv的配置
 * @param registry
 */
        @Override
     public void addViewControllers(ViewControllerRegistry registry) {
           registry.addViewController("/").setViewName("index");//前拼templates，后拼.html
            // registry.addViewController("/index.html").setViewName("index");//浏览器发送/请求来到login.html页面，不用写controller控制层的请求方法了
          }

            /**
      * 将static下面的js，css文件加载出来
       * @param registry
      */
            @Override
     public void addResourceHandlers(ResourceHandlerRegistry registry) {
                //registry.addResourceHandler("/static/").addResourceLocations("classpath:/static/");
                 registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
             }
 }
