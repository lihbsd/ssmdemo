package com.ckjs.ssmdemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/18 13:51
 */
@Controller
@RequestMapping(value = "exception")
public class ExceptionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "ajax.ex")
    public ModelAndView ajax(String message,String stackTrace) throws Exception{

        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":message="+message+":stackTrace="+stackTrace);
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //尝试获取session里的异常信息
        Exception exception = (Exception) subject.getSession().getAttribute("exception");
        //判断是否存在，有则移除session里的信息，把异常抛出。统一处理
        if(exception!= null){
            logger.info("session里存有异常信息，message="+exception.getMessage());
            subject.getSession().removeAttribute("exception");
            throw exception;
        }

        //没有异常，则直接到异常界面
        ModelAndView modelAndView = new ModelAndView();
        //将错误信息传到页面
        modelAndView.addObject("message",message);
        modelAndView.addObject("stackTrace",stackTrace);
        //指向错误页面
        modelAndView.setViewName("error");
        return modelAndView;
    }
}