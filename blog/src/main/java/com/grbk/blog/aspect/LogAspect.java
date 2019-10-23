package com.grbk.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect   //在前端请求后端和后端响应前端的整个过程中进行相应的切面处理，即aop
@Component   //该注解会让springboot扫描该类
public class LogAspect {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    //切面
    @Pointcut("execution(* com.grbk.blog.web.*.*(..))")
    public void log(){

    }

    //在请求的方法执行之前执行
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String url=request.getRequestURL().toString();
        String ip=request.getRemoteAddr();
        String classMethod= joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();
        Object[] ressults=joinPoint.getArgs();
        RequestLog requestLog=new RequestLog(url, ip, classMethod, ressults);
        logger.info("Request:{}", requestLog);
    }

    //在请求的方法执行之后执行
    @After("log()")
    public void doAfter(){
        //System.out.println("--doafter--");
    }

    //在请求的方法执行过后拿到它返回的数据
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("Result:{}",result);
    }

    private class RequestLog{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] results;

        public RequestLog(String url, String ip, String classMethod, Object[] results) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.results = results;
        }

        @Override
        public String toString() {
            return "RequestLog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", results=" + Arrays.toString(results) +
                    '}';
        }
    }
}
