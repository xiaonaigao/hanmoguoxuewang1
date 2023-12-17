package com.wzl.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wang
 * @version 1.0
 * 自定义拦截器
 */
//阻挡掉所有@Controller注解的控制器
@ControllerAdvice
public class ControllerExceptionHandler {
//拿到日志记录的对象
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//异常处理的注解
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
//        记录异常
        logger.error("Requst URL : {},Exception : {}",request.getRequestURL(),e);
//传递异常类和状态类,通过注解工具找到一个注解,判断有没有存在状态的注解如果存在true不存在为空
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) !=null){
            throw e;
        }
//返回页面创建一个对象
        ModelAndView mv=new ModelAndView();
        mv.addObject("url",request.getRequestURL());
        mv.addObject("exception",e);
//        返回异常页面
        mv.setViewName("error/error");
        return mv;

    }
}
