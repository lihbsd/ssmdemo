package com.ckjs.ssmdemo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/8 19:06
 */
@ControllerAdvice
public class MyExceptionResolver implements HandlerExceptionResolver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 系统抛出的异常
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e 系统抛出的异常
     * @return
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+e.getMessage());
        // 解析出异常类型
        MyException myException = null;
        // 异常堆栈信息。
        String stackTrace = getTrace(e);
        logger.error("stackTrace="+stackTrace);
        //异常信息
        String exmessage = e.getMessage();
        logger.error("exmessage="+exmessage);
        //异常类型
        String exceptionType = e.getClass().getSimpleName();
        logger.error("exceptionType="+exceptionType);
        //展示给用户看到异常信息，默认等于异常信息
        String message = exmessage;
        //跳转路径
        String targetUrl = "error";
        //不同异常不同处理分支：修饰给用户看的信息，以及不同给错误展示页面
        switch(exceptionType){
            //<!-- 身份令牌异常，不支持的身份令牌 -->
            case "UnsupportedTokenException":
                message="身份异常，请重新登录！";
                targetUrl = "relogin";
                break;
            //<!-- 未知账户/没找到帐号,登录失败 -->
            case "UnknownAccountException":
                message="没有登录或者登录过期，请重新登录！";
                targetUrl = "relogin";
                break;
            //<!-- 帐号锁定 -->
            case "LockedAccountException":
                message="账号被锁定，请联系系统管理员处理！";
                targetUrl = "noright";
                break;
            //<!-- 用户禁用 -->
            case "DisabledAccountException":
                message="账号被禁用，请联系系统管理员处理！";
                targetUrl = "noright";
                break;
            //<!-- 登录重试次数，超限。只允许在一段时间内允许有一定数量的认证尝试 -->
            case "ExcessiveAttemptsException":
                message="登录次数过多，请稍后再重新登录！";
                targetUrl = "noright";
                break;
            //<!-- 一个用户多次登录异常：不允许多次登录，只能登录一次 。即不允许多处登录-->
            case "ConcurrentAccessException":
                message="重复登录，如果不是本人登录，请重新登录并立即修改密码！";
                targetUrl = "relogin";
                break;
            //<!-- 账户异常 -->
            case "AccountException":
                message="账号异常，请联系系统管理员处理！";
                targetUrl = "noright";
                break;
            //<!-- 过期的凭据异常 -->
            case "ExpiredCredentialsException":
                message="登录超时，请重新登录！";
                targetUrl = "relogin";
                break;
            //<!-- 错误的凭据异常 -->
            case "IncorrectCredentialsException":
                message="登录异常，请重新登录！";
                targetUrl = "relogin";
                break;
            //<!-- 凭据异常 -->
            case "CredentialsException":
                message="登录异常，请重新登录！";
                targetUrl = "relogin";
                break;
            //<!-- 凭据异常 -->
            case "AuthenticationException":
                message="登录异常，请重新登录！";
                targetUrl = "relogin";
                break;
            //<!-- 没有访问权限，访问异常 -->
            case "HostUnauthorizedException":
                message="您没有访问权限，请联系系统管理员处理！";
                targetUrl = "noright";
                break;
            //<!-- 没有访问权限，访问异常 -->
            case "UnauthorizedException":
                message="您没有访问权限，请联系系统管理员处理！";
                targetUrl = "noright";
                break;
            //<!-- 授权异常 -->
            case "UnauthenticatedException":
                message="您没有访问权限，请联系系统管理员处理！";
                targetUrl = "noright";
                break;
            //<!-- 授权异常 -->
            case "AuthorizationException":
                message="您没有访问权限，请联系系统管理员处理！";
                targetUrl = "noright";
                break;
            //<!-- shiro全局异常 -->
            case "ShiroException":
                message="权限发生异常，请联系系统管理员处理！";
                targetUrl = "noright";
                break;
            //未定义的异常类型
            default:
                //...;
                break;
        }

        logger.error("message="+message);
        logger.error("targetUrl="+targetUrl);

        ModelAndView modelAndView = new ModelAndView();

        //将错误信息传到页面
        modelAndView.addObject("message",message);
        modelAndView.addObject("exmessage",exmessage);
        modelAndView.addObject("stackTrace",stackTrace);
        //指向错误页面
        modelAndView.setViewName(targetUrl);
        return modelAndView;
    }

    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}