package com.ckjs.ssmdemo.controller;

import com.ckjs.ssmdemo.entity.Type;
import com.ckjs.ssmdemo.entity.User;
import com.ckjs.ssmdemo.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "type")
/**
 * @Description:类型设置controller类
 * @Auther: ckjs
 * @Date: 2018/11/1 18:39
 */
public class TypeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //依赖注入
    @Autowired
    private TypeService typeService;

    @RequestMapping(value = "findAll.do")
    /**
     * 功能描述: 默认查询方法，通过keyword查询。没有查询条件作为keyword=""
     *
     * @param: [model, keyword]
     * @return: java.lang.String
     * @auther: ckjs
     * @date: 2018/10/31 20:42
     */
    public String findAll(Model model, @RequestParam(value="keyword",defaultValue="")String keyword, @RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":keyword="+keyword+":pageNum="+pageNum+":pageSize="+pageSize);

        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Type> typeList = typeService.findByKeyword(keyword);
        PageInfo<Type> pageInfo = new PageInfo<Type>(typeList);
        logger.debug("TypeController:findAll:typeList="+typeList.toString());
        logger.debug("TypeController:findAll:pageInfo="+pageInfo.toString());
        model.addAttribute("page",pageInfo);
        model.addAttribute("pageNum",pageInfo.getPages());
        model.addAttribute("typeList",typeList);
        model.addAttribute("keyword",keyword);
        return "admin/typeList";
    }

    @RequestMapping(value = "findById.do")
    /**
     *
     * 功能描述: 通过ID查找
     *
     * @param: [model, id]
     * @return: java.lang.String
     * @auther: ckjs
     * @date: 2018/10/31 20:41
     */
    public String findById(Model model ,String id,@RequestParam(value="keyword",defaultValue="")String keyword) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":id="+id+";keyword="+keyword);
        Type type = typeService.findById(id);
        System.out.println("TypeController:findById:type="+type);
        model.addAttribute("type",type);
        model.addAttribute("mark","view");
        model.addAttribute("keyword",keyword);
        return "admin/type";
    }

    @RequestMapping(value = "checkRepeat.do")
    /**
     * 功能描述: 通过关键字查重，返回对错
     *
     * @param: [keyword]
     * @return: java.lang.String
     * @auther: ckjs
     * @date: 2018/11/1 18:33
     */
    public String checkRepeat(String keyword) {
        List<Type> typeList= typeService.findByKeyword(keyword);
        if(typeList.size()>0) {
            return "{\"msg\":\"false\"}";
        }
        return "{\"msg\":\"true\"}";
    }

    @RequestMapping(value = "toAdd.do")
    /**
     * 功能描述: html模板不能直接访问，通过toAdd跳转
     *
     * @param: [model, keyword]
     * @return: java.lang.String
     * @auther: ckjs
     * @date: 2018/11/1 18:35
     */
    public String toAdd(Model model,@RequestParam(value="keyword",defaultValue="")String keyword) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":keyword="+keyword);

        model.addAttribute("mark","add");
        model.addAttribute("keyword",keyword);
        return "admin/type";
    }

    @RequestMapping(value = "toEdit.do")
    /**
     * 功能描述: 查询要编辑的对象，返回到编辑页面
     *
     * @param: [model, id, keyword]
     * @return: java.lang.String
     * @auther: ckjs
     * @date: 2018/11/1 18:35
     */
    public String toEdit(Model model,String id,@RequestParam(value="keyword",defaultValue="")String keyword) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+"id="+id+";keyword="+keyword);
        Type type = typeService.findById(id);
        model.addAttribute("mark","edit");
        model.addAttribute("type",type);
        model.addAttribute("keyword",keyword);
        return "admin/type";
    }

    @RequestMapping(value = "edit.do")
    /**
     * 功能描述: 更新数据（包括新增和修改）
     *
     * @param: [model, type, mark, keyword, request]
     * @return: java.lang.String
     * @auther: ckjs
     * @date: 2018/11/1 18:36
     */
    public String edit(Model model,Type type,String mark, @RequestParam(value="keyword",defaultValue="")String keyword, HttpServletRequest request) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+"mark="+mark+";keyword="+keyword);
        logger.debug("type="+type.toString());
        //获得登录信息
        HttpSession session = request.getSession();
        User user  = (User)session.getAttribute("user");
        //测试时没有登录信息，插入一个默认值
        if (user == null || user.getId() == null || user.getId().toString().equals("")) {
            logger.debug("user=null");
            user = new User();
            user.setId(1);
        }else{
            logger.debug("user="+user.toString());
        }
        if(mark.equals("add")){
            logger.info("执行add");
            type.setCreator(user.getId());
            type.setModifier(user.getId());
            //执行插入操作，返回插入数据的id
            String id = typeService.insert(type);
            logger.debug("执行add:id="+id);
            type = typeService.findById(id);
            //取新增数据id，重新取数，获得插入数据库后的默认值
            model.addAttribute("type",type);
            model.addAttribute("mark","view");
        }else {
            logger.info("执行update");
            //改变更新人
            type.setModifier(user.getId());
            String id = typeService.update(type);
            //取id，重新取数，获得插入数据库后的默认值
            model.addAttribute("type", typeService.findById(id));
            model.addAttribute("mark","view");
        }
        model.addAttribute("keyword",keyword);
        return "admin/type";
    }

    @RequestMapping(value = "delete.do")
    /**
     * 功能描述: 逻辑删除数据
     *
     * @param: [attr, id, keyword]
     * @return: java.lang.String
     * @auther: ckjs
     * @date: 2018/11/1 18:37
     */
    public String delete(RedirectAttributes attr, String id, @RequestParam(value="keyword",defaultValue="")String keyword) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":id="+id+";keyword="+keyword);
        Type type = new Type();
        type.setId(Integer.parseInt(id));
        //逻辑删除
        typeService.delete(type);
        attr.addAttribute("keyword",keyword);
        return "redirect:/type/findAll.do";
    }
}
