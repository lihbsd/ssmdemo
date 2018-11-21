package com.ckjs.ssmdemo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ckjs.ssmdemo.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/5 12:55
 */
public class BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private TypeService typeService;

    @InitBinder
    /**
     * @Description:格式化时间格式，解决时间控件无法传值问题
     * @param: [databinder]
     * @return: void
     * @auther@date: ckjs 2018/11/7 16:25
     */
    public void initBinder(WebDataBinder databinder){

        databinder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }

    public Map getTypeMap(String TYPESIGN){
        Map typeMap = new HashMap();
        typeMap = typeService.findBySign(TYPESIGN);
        logger.debug("typeMap=" + typeMap.toString());
        if(typeMap.isEmpty()){
            logger.error("没有获取到类型Map!" );
        }
        return typeMap;
    }
}