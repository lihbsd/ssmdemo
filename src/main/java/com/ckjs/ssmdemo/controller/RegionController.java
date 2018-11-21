package com.ckjs.ssmdemo.controller;

import com.ckjs.ssmdemo.entity.Region;
import com.ckjs.ssmdemo.entity.User;
import com.ckjs.ssmdemo.service.RegionService;
import com.ckjs.ssmdemo.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jdk.internal.dynalink.beans.StaticClass;
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
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "region")
/**
 * @Description:controller类
 * @Auther: ckjs
 * @Date: 2018/11/1 18:39
 */
public class RegionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //依赖注入
    @Autowired
    private RegionService regionService;
    @Autowired
    private TypeService typeService;
    //要显示的分类的sign值，因为多处使用，统一定义=""表示没有需要取值的type
    private static String TYPESIGN = "REGIONLEVEL";
    //统一定义模块关键字，用于权限控制
//    private static String SIGN = "region";

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
        //获取分类Map，用于前端展示分类名称和下拉选择框
        Map typeMap = new HashMap();
        typeMap = typeService.findBySign(TYPESIGN);
        model.addAttribute("typeMap", typeMap);
        logger.debug("typeMap=" + typeMap.toString());
        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Region> regionList = regionService.findByKeyword(keyword);
        PageInfo<Region> pageInfo = new PageInfo<Region>(regionList);
        logger.debug("findAll:regionList="+regionList.toString());
        logger.debug("findAll:pageInfo="+pageInfo.toString());
        model.addAttribute("page",pageInfo);
        model.addAttribute("pageNum",pageInfo.getPages());
        //返回的list参数名统一用dataList，简化前端取数
        model.addAttribute("dataList",regionList);
        model.addAttribute("keyword",keyword);
        return "admin/regionList";
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

        //获取分类Map，用于前端展示分类名称和下拉选择框
        if(TYPESIGN!="") {
            Map typeMap = new HashMap();
            typeMap = typeService.findBySign(TYPESIGN);
            model.addAttribute("typeMap", typeMap);
            logger.debug("typeMap=" + typeMap.toString());
        }

        Region region =  regionService.findById(id);
        logger.info("findById:region="+region.toString());
        model.addAttribute("region",region);
        model.addAttribute("mark","view");
        model.addAttribute("keyword",keyword);
        return "admin/region";
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
        List<Region> regionList= regionService.findByKeyword(keyword);
        if(regionList.size()>0) {
            return "{\"msg\":\"false\"}";
        }
        return "{\"msg\":\"true\"}";
    }

    @ResponseBody
    @RequestMapping(value = "findFromAjax.do")
    public  List<Region> findFromAjax(@RequestParam(value="keyword",defaultValue="")String keyword, @RequestParam(value="level",defaultValue="")String level, @RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize){

        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":keyword="+keyword+":level="+level+":pageNum="+pageNum+":pageSize="+pageSize);
        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Region> regionList = regionService.findFromAjax(keyword,level);
        PageInfo<Region> pageInfo = new PageInfo<Region>(regionList);

        return regionList;
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

        //获取分类Map，用于前端展示分类名称和下拉选择框
        if(TYPESIGN!="") {
            Map typeMap = new HashMap();
            typeMap = typeService.findBySign(TYPESIGN);
            model.addAttribute("typeMap", typeMap);
            logger.debug("typeMap=" + typeMap.toString());
        }

        model.addAttribute("mark","add");
        model.addAttribute("keyword",keyword);
        return "admin/region";
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

        //获取分类Map，用于前端展示分类名称和下拉选择框
        if(TYPESIGN!="") {
            Map typeMap = new HashMap();
            typeMap = typeService.findBySign(TYPESIGN);
            model.addAttribute("typeMap", typeMap);
            logger.debug("typeMap=" + typeMap.toString());
        }

        Region region = regionService.findById(id);
        model.addAttribute("mark","edit");
        model.addAttribute("region",region);
        model.addAttribute("keyword",keyword);
        return "admin/region";
    }

    @RequestMapping(value = "edit.do")
    /**
     * 功能描述: 更新数据（包括新增和修改）
     *
     * @param: [model, region, mark, keyword, request]
     * @return: java.lang.String
     * @auther: ckjs
     * @date: 2018/11/1 18:36
     */
    public String edit(Model model,Region region,String mark, @RequestParam(value="keyword",defaultValue="")String keyword, HttpServletRequest request) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+"mark="+mark+";keyword="+keyword);
        logger.debug("region="+region.toString());
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
            logger.info("执行add:region"+region.toString());
            region.setCreator(user.getId());
            region.setModifier(user.getId());
            //执行插入操作，返回插入数据的id
            int num = regionService.insert(region);
            String id = region.getId().toString();
            logger.debug("执行add:id="+id);
            region = regionService.findById(id);
            //取新增数据id，重新取数，获得插入数据库后的默认值
            model.addAttribute("region",region);
            model.addAttribute("mark","view");
        }else {
            logger.info("执行update:region"+region.toString());
            //改变更新人
            region.setModifier(user.getId());
            int num = regionService.update(region);
            String id = region.getId().toString();
            //取id，重新取数，获得插入数据库后的默认值
            model.addAttribute("region", regionService.findById(id));
            model.addAttribute("mark","view");
        }

        //获取分类Map，用于前端展示分类名称和下拉选择框
        if(TYPESIGN!="") {
            Map typeMap = new HashMap();
            typeMap = typeService.findBySign(TYPESIGN);
            model.addAttribute("typeMap", typeMap);
            logger.debug("typeMap=" + typeMap.toString());
        }

        model.addAttribute("keyword",keyword);
        return "admin/region";
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
        Region region = new Region();
        region.setId(Integer.parseInt(id));
        //逻辑删除
        int num = regionService.delete(region);
        if(num!=1){
            logger.error(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":删除id="+id+"的记录是 "+num+"条");
        }

        attr.addAttribute("keyword",keyword);
        return "redirect:/region/findAll.do";
    }
}
