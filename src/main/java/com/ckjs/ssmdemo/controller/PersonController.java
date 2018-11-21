package com.ckjs.ssmdemo.controller;

import com.ckjs.ssmdemo.entity.Person;
import com.ckjs.ssmdemo.entity.User;
import com.ckjs.ssmdemo.service.PersonService;
import com.ckjs.ssmdemo.service.TypeService;
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
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "person")
public class PersonController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //依赖注入
    @Autowired
    private PersonService personService;
    @Autowired
    private TypeService typeService;
    //要显示的分类的sign值，因为多处使用，统一定义=""表示没有需要取值的type
    private static String TYPESIGN = "GENDER";
    //统一定义模块关键字，用于权限控制
//    private static String SIGN = "person";


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
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        logger.info("SecurityUtils.getSubject().getPrincipal().user="+user.toString());
        //获取分类Map，用于前端展示分类名称和下拉选择框
        if(TYPESIGN!="") {
            Map typeMap = new HashMap();
            typeMap = typeService.findBySign(TYPESIGN);
            model.addAttribute("typeMap", typeMap);
            logger.debug("typeMap=" + typeMap.toString());
        }
        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Person> personList = personService.findByKeyword(keyword);
        PageInfo<Person> pageInfo = new PageInfo<Person>(personList);
        logger.info("findAll:personList="+personList.toString());
        logger.debug("findAll:pageInfo="+pageInfo.toString());
        model.addAttribute("page",pageInfo);
        model.addAttribute("pageNum",pageInfo.getPages());
        //返回的list参数名统一用dataList，简化前端取数
        model.addAttribute("dataList",personList);
        model.addAttribute("keyword",keyword);
        return "person/personList";
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
    @RequestMapping(value = "findById.do")
    public String findById(Model model ,String id,@RequestParam(value="keyword",defaultValue="")String keyword) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":id="+id+";keyword="+keyword);

        //获取分类Map，用于前端展示分类名称和下拉选择框
        if(TYPESIGN!="") {
            Map typeMap = new HashMap();
            typeMap = typeService.findBySign(TYPESIGN);
            model.addAttribute("typeMap", typeMap);
            logger.debug("typeMap=" + typeMap.toString());
        }

        Person person =  personService.findById(id);
        logger.info("findById:person="+person.toString());
        model.addAttribute("person",person);
        model.addAttribute("mark","view");
        model.addAttribute("keyword",keyword);
        return "person/person";
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
        List<Person> personList= personService.findByKeyword(keyword);
        if(personList.size()>0) {
            return "{\"msg\":\"false\"}";
        }
        return "{\"msg\":\"true\"}";
    }

    @ResponseBody
    @RequestMapping(value = "findFromAjax.do")
    public  List<Person> findFromAjax(@RequestParam(value="keyword",defaultValue="")String keyword, @RequestParam(value="level",defaultValue="")String level, @RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize){

        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":keyword="+keyword+":level="+level+":pageNum="+pageNum+":pageSize="+pageSize);
        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Person> personList = personService.findFromAjax(keyword,level);
        PageInfo<Person> pageInfo = new PageInfo<Person>(personList);

        return personList;
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
        return "person/person";
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

        Person person = personService.findById(id);
        model.addAttribute("mark","edit");
        model.addAttribute("person",person);
        model.addAttribute("keyword",keyword);
        return "person/person";
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
    public String edit(Model model,Person person,String mark, @RequestParam(value="keyword",defaultValue="")String keyword, HttpServletRequest request) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+"mark="+mark+";keyword="+keyword);
        logger.debug("person="+person.toString());
        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(mark.equals("add")){
            logger.info("执行add:person"+person.toString());
            person.setCreator(user.getId());
            person.setModifier(user.getId());
            //执行插入操作，返回插入数据的id
            int num = personService.insert(person);
            String id = person.getId().toString();
            logger.info("执行add:id="+id);
            person = personService.findById(id);
            //取新增数据id，重新取数，获得插入数据库后的默认值
            model.addAttribute("person",person);
            model.addAttribute("mark","view");
        }else {
            logger.info("执行update:person"+person.toString());
            //改变更新人
            person.setModifier(user.getId());
            int num = personService.update(person);
            String id = person.getId().toString();
            //取id，重新取数，获得插入数据库后的默认值
            model.addAttribute("person", personService.findById(id));
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
        return "person/person";
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
        Person person = new Person();
        person.setId(Integer.parseInt(id));
        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        person.setModifier(user.getId());
        //逻辑删除
        int num = personService.delete(person);
        if(num!=1){
            logger.error(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":删除id="+id+"的记录是 "+num+"条");
        }

        attr.addAttribute("keyword",keyword);
        return "redirect:/person/findAll.do";
    }
}
