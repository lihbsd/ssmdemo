package com.ckjs.ssmdemo.controller;

import com.ckjs.ssmdemo.entity.User;
import com.ckjs.ssmdemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RightController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //依赖注入
    @Autowired
    private UserService userService;

    @RequestMapping(value = "tologin.do")
    public String tologin(User user,Model model) {
        model.addAttribute("message","");
        return  "login";
    }


    @RequestMapping(value = "toregister.do")
    public String toregister(User user,Model model){
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName());
        model.addAttribute("message","");
        model.addAttribute("status","");
        return  "register";
    }
    @RequestMapping(value = "register.do")
    public String register(User user,Model model) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":user="+user.toString());
        //先获取盐值
        SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
        String salt = secureRandom.nextBytes(16).toHex();
        logger.info("获取盐值:salt="+salt);
        user.setSalt(salt);
        try {
            //通过SimpleHash 来进行加密操作
            SimpleHash hash = new SimpleHash("MD5", user.getPassword(), ByteSource.Util.bytes(user.getSalt()), 2);
            user.setPassword(hash.toString());
        }catch (Exception e){
            logger.error(e.getMessage());
            model.addAttribute("status","false");
            model.addAttribute("message","注册失败，请重试！->");
            return  "register";

        }
        int num = userService.insert(user);
        if(num==0){
            logger.error("新增user失败:user="+user.toString());

            model.addAttribute("status","false");
            model.addAttribute("message","注册失败，请重试！->");
            return  "register";
        }

        model.addAttribute("user",user);
        model.addAttribute("status","true");
        model.addAttribute("message","注册成功，请登录系统！->");
        return  "register";
    }

    @RequestMapping(value = "login.do")
    /**
     * 功能描述: 默认查询方法，通过keyword查询。没有查询条件作为keyword=""
     *
     * @param: [model, keyword]
     * @return: java.lang.String
     * @auther: ckjs
     * @date: 2018/10/31 20:42
     */
    public String login(User user, Model model) {

        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":user="+user.toString());

        if(user == null || user.getUsername() == null){
            model.addAttribute("message","");
            return "login";
        }

        try{
            //添加用户认证信息
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                    user.getUsername(),
                    user.getPassword());
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);

            return "redirect:/index";

        }catch(AuthenticationException e) {
            logger.info("登录错误:"+e.getMessage());

            model.addAttribute("message","登录名或者密码错误，请重新登录！");
            return  "login";
        }
    }
}
