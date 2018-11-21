package com.ckjs.ssmdemo.controller;

import com.ckjs.ssmdemo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //依赖注入
    @Autowired
    private UserService userService;

    @RequestMapping(value = "index")
    public String toindex(Model model) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName());
        model.addAttribute("message","");
        return  "index";
    }

    @RequestMapping(value = "home")
    public String tohome(Model model) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName());
        model.addAttribute("message","");
        return  "home";
    }

}
