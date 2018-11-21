package com.ckjs.ssmdemo.controller;

import com.ckjs.ssmdemo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TestController {
    //依赖注入
    @Autowired
    /**
     * 功能描述: 
     *
     * @param: 
     * @return: 
     * @auther: ckjs
     * @date: 2018/11/1 18:27
     */
    private TypeService typeService;


    @RequestMapping(value = "test.do")
    /**
     * 功能描述:
     *
     * @param: [model]
     * @return: java.lang.String
     * @auther: ckjs
     * @date: 2018/11/1 18:27
     */
    public String findAll(Model model){

        return "test";
    }


}
