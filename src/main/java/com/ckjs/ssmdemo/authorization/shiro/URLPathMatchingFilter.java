package com.ckjs.ssmdemo.authorization.shiro;

import com.ckjs.ssmdemo.base.SpringUtils;
import com.ckjs.ssmdemo.entity.*;
import com.ckjs.ssmdemo.service.MappingService;
import com.ckjs.ssmdemo.service.MappingServiceImpl;
import com.ckjs.ssmdemo.service.UserService;
import com.ckjs.ssmdemo.service.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.HostUnauthorizedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.List;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/7 20:45
 */
public class URLPathMatchingFilter extends AccessControlFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;
    @Autowired
    private MappingService mappingService;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        logger.info(this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName() + "");
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        logger.info(this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName() + "");
        return true;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        logger.info(this.getClass().getName() + ":" + Thread.currentThread().getStackTrace()[1].getMethodName() + "");
        //注入service无效时自己通过sping获取
        if (mappingService == null) {
            mappingService = (MappingServiceImpl) SpringUtils.getBean(MappingServiceImpl.class);
        }
        if (userService == null) {
            userService = (UserServiceImpl) SpringUtils.getBean(UserServiceImpl.class);
        }
        //获取subject
        Subject subject = SecurityUtils.getSubject();

        //请求的url
        String requestURL = getPathWithinApplication(request);
        logger.info("请求的url :" + requestURL);
        //去除参数
        requestURL = requestURL.split("\\?")[0];
        //开始解析
        String[] urlArr = requestURL.split("/");
        if (urlArr.length <= 0) {
            logger.info("url为空，无法解析！");
            HostUnauthorizedException ex = new HostUnauthorizedException("URL为空，无法访问。URL=" + requestURL);
            subject.getSession().setAttribute("exception", ex);
            throw ex;
        }
        //urlitem是模块名，一般对应controller里的类，没有类映射到，则为空
        String urlitem = "";
        if (urlArr.length > 1) {
            urlitem = urlArr[urlArr.length - 2];
        }
        //func是方法名，一般对应controller里的方法
        String func = urlArr[urlArr.length - 1];
        logger.info("解析url :urlitem=" + urlitem + ":func=" + func);
        List<Mapping> mappingList = mappingService.findBySourceAndType("url", func);
        if (mappingList == null || mappingList.size() <= 0) {
            logger.info("没有查到url的映射信息！");
            HostUnauthorizedException ex = new HostUnauthorizedException("没有查到url的映射信息！URL=" + requestURL);
            subject.getSession().setAttribute("exception", ex);
            throw ex;
        }
        String urlperm = ((Mapping) mappingList.get(0)).getTarget();
        logger.info("解析url :urlitem=" + urlitem + ":urlperm=" + urlperm);

        boolean hasPermission = false;
        //设置为不需要权限,直接返回
        if (urlperm.equals("anon")) {
            hasPermission = true;
            logger.info("不需要权限即可访问");
            return true;
        }

        //检查登录
        if (!subject.isAuthenticated()) {
            UnknownAccountException ex = new UnknownAccountException("没有查到用户登录信息，请重新登录！");
            subject.getSession().setAttribute("exception", ex);
            throw ex;
        }
        //获取登录信息
        User user = (User) subject.getPrincipal();
        if (user == null) {
            UnknownAccountException ex = new UnknownAccountException("没有查到用户登录信息，请重新登录！");
            subject.getSession().setAttribute("exception", ex);
            throw ex;
        }
        //测试时设置一个超级管理员
        if(user.getId()==1){
            logger.info("超级管理员");
            return true;
        }
        //设置为authc，则登录即可访问。直接返回true；
        if (urlperm.equals("authc")) {
            hasPermission = true;
            logger.info("登录即可访问");
            return true;
        }
        //通过数据库查询是否拥有权限
        if (userService.validateRight(user.getId().toString(), urlitem, urlperm)) {
            hasPermission = true;
            logger.info("有权限");
        }else{
            hasPermission = false;
            logger.info("没有权限");
        }

        if (hasPermission) {
            return true;
        } else {
            UnauthorizedException ex = new UnauthorizedException("当前用户没有访问路径" + requestURL + "的权限");
            subject.getSession().setAttribute("exception", ex);
            throw ex;
        }

    }
}