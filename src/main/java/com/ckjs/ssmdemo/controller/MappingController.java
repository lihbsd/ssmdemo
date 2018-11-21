package com.ckjs.ssmdemo.controller;

import com.ckjs.ssmdemo.entity.KeyValue;
import com.ckjs.ssmdemo.entity.Mapping;
import com.ckjs.ssmdemo.entity.User;
import com.ckjs.ssmdemo.exception.MyException;
import com.ckjs.ssmdemo.service.MappingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "mapping")
public class MappingController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //依赖注入
    @Autowired
    private MappingService mappingService;

    //统一定义模块关键字，用于权限控制
//    private static String SIGN = "person";


    @RequestMapping(value = "list.do")
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
        List<Mapping> mappingList = mappingService.findByKeyword(keyword);
        PageInfo<Mapping> pageInfo = new PageInfo<Mapping>(mappingList);
        logger.info("findAll:mappingList="+mappingList.toString());

        model.addAttribute("page",pageInfo);
        model.addAttribute("pageNum",pageInfo.getPages());
        //返回的list参数名统一用dataList，简化前端取数
        model.addAttribute("dataList",mappingList);
        model.addAttribute("keyword",keyword);
        return "admin/mappingList";
    }

    /**
     *
     * 功能描述: 通过ID查找
     *
     * @param: [model, id]
     * @return: java.lang.String
     * @auther: ckjs
     * @date: 2018/10/31 20:41
     */
    @RequestMapping(value = "view.do")
    public String findById(Model model ,String id,@RequestParam(value="keyword",defaultValue="")String keyword) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":id="+id+";keyword="+keyword);

        Mapping mapping =  mappingService.findById(id);
        logger.info("findById:mapping="+mapping.toString());
        model.addAttribute("mapping",mapping);
        model.addAttribute("mark","view");
        model.addAttribute("keyword",keyword);
        return "admin/mapping";
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
        List<Mapping> mappingList = mappingService.findByKeyword(keyword);
        if(mappingList.size()>0) {
            return "{\"msg\":\"false\"}";
        }
        return "{\"msg\":\"true\"}";
    }

    @RequestMapping(value = "toadd.do")
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
        return "admin/mapping";
    }

    @RequestMapping(value = "add.do")
    /**
     * 功能描述: 更新数据（包括新增和修改）
     *
     * @param: [model, person, mark, keyword, request]
     * @return: java.lang.String
     * @auther: ckjs
     * @date: 2018/11/1 18:36
     */
    public String add(Model model,Mapping mapping,String mark, @RequestParam(value="keyword",defaultValue="")String keyword) throws Exception {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+"mark="+mark+";keyword="+keyword);
        logger.debug("mapping="+mapping.toString());
        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            logger.error("查询不到登录信息！");
            throw new MyException("查询不到登录信息！");
        }
        //补充创建人和修改人
        logger.info("执行add:mapping"+mapping.toString());
        mapping.setCreator(user.getId());
        mapping.setModifier(user.getId());

        //执行插入操作，返回插入数据的id
        int num = mappingService.insert(mapping);
        String id = mapping.getId().toString();
        logger.info("执行add:id="+id);
        mapping = mappingService.findById(id);
        //取新增数据id，重新取数，获得插入数据库后的默认值
        model.addAttribute("mapping",mapping);
        model.addAttribute("mark","view");
        model.addAttribute("keyword",keyword);

        return "admin/mapping";
    }

    @RequestMapping(value = "toedit.do")
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

        Mapping mapping = mappingService.findById(id);
        model.addAttribute("mark","edit");
        model.addAttribute("mapping",mapping);
        model.addAttribute("keyword",keyword);
        return "admin/mapping";
    }

    @RequestMapping(value = "edit.do")
    /**
     * 功能描述: 更新数据（包括新增和修改）
     *
     * @param: [model, person, mark, keyword, request]
     * @return: java.lang.String
     * @auther: ckjs
     * @date: 2018/11/1 18:36
     */
    public String edit(Model model,Mapping mapping,String mark, @RequestParam(value="keyword",defaultValue="")String keyword, HttpServletRequest request) throws Exception {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+"mark="+mark+";keyword="+keyword);
        logger.debug("mapping="+mapping.toString());
        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            logger.error("查询不到登录信息！");
            throw new MyException("查询不到登录信息！");
        }

        logger.info("执行update:mapping"+mapping.toString());
        //改变更新人
        mapping.setModifier(user.getId());
        int num = mappingService.update(mapping);
        String id = mapping.getId().toString();
        //取id，重新取数，获得插入数据库后的默认值
        model.addAttribute("person", mappingService.findById(id));
        model.addAttribute("mark","view");
        model.addAttribute("keyword",keyword);
        return "admin/mapping";
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
    public String delete(RedirectAttributes attr, String id, @RequestParam(value="keyword",defaultValue="")String keyword) throws Exception {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":id="+id+";keyword="+keyword);
        Mapping mapping = new Mapping();
        mapping.setId(Integer.parseInt(id));
        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            logger.error("查询不到登录信息！");
            throw new MyException("查询不到登录信息！");
        }
        mapping.setModifier(user.getId());
        //逻辑删除
        int num = mappingService.delete(mapping);
        if(num!=1){
            logger.error(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":删除id="+id+"的记录是 "+num+"条");
        }

        attr.addAttribute("keyword",keyword);
        return "redirect:/mapping/list.do";
    }

}
