package com.ckjs.ssmdemo.controller;

import com.ckjs.ssmdemo.entity.Permission;
import com.ckjs.ssmdemo.entity.User;
import com.ckjs.ssmdemo.exception.MyException;
import com.ckjs.ssmdemo.service.PermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
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
import java.util.List;

@Controller
@RequestMapping(value = "permission")
public class PermissionController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //依赖注入
    @Autowired
    private PermissionService permissionService;

    //要显示的分类的sign值，因为多处使用，统一定义=""表示没有需要取值的type
    private static String TYPESIGN = "";


    @RequestMapping(value = "list.do")
    /**
     * 功能描述: 默认查询方法，通过keyword查询。没有查询条件作为keyword=""
     *
     * @param: [model, keyword]
     * @return: java.lang.String
     * @auther: ckjs
     * @date: 2018/10/31 20:42
     */
    public String findAll(Model model, @RequestParam(value="keyword",defaultValue="")String keyword, @RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize)throws Exception {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":keyword="+keyword+":pageNum="+pageNum+":pageSize="+pageSize);

        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            logger.error("查询不到登录信息！");
            throw new UnknownAccountException("查询不到登录信息！");
        }

        //获取分类Map，用于前端展示分类名称和下拉选择框,getTypeMap方法写在BaseController类，方便统一维护
        if(TYPESIGN!="") {
            model.addAttribute("typeMap", super.getTypeMap(TYPESIGN));
        }

        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Permission> dataList = permissionService.findByKeyword(keyword);
        PageInfo<Permission> pageInfo = new PageInfo<Permission>(dataList);
        logger.info("findAll:dataList="+dataList.toString());

        model.addAttribute("page",pageInfo);
        model.addAttribute("pageNum",pageInfo.getPages());
        //返回的list参数名统一用dataList，简化前端取数
        model.addAttribute("dataList",dataList);
        model.addAttribute("keyword",keyword);
        return "user/permissionList";
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
    public String findById(Model model ,String id,@RequestParam(value="keyword",defaultValue="")String keyword) throws Exception {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":id="+id+";keyword="+keyword);
        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            logger.error("查询不到登录信息！");
            throw new UnknownAccountException("查询不到登录信息！");
        }
        //获取分类Map，用于前端展示分类名称和下拉选择框,getTypeMap方法写在BaseController类，方便统一维护
        if(TYPESIGN!="") {
            model.addAttribute("typeMap", super.getTypeMap(TYPESIGN));
        }

        Permission permission =  permissionService.findById(id);
        logger.info("findById:permission="+permission.toString());
        model.addAttribute("permission",permission);
        model.addAttribute("mark","view");
        model.addAttribute("keyword",keyword);
        return "user/permission";
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
        List<Permission> dataList= permissionService.findByKeyword(keyword);
        if(dataList.size()>0) {
            return "{\"msg\":\"false\"}";
        }
        return "{\"msg\":\"true\"}";
    }

    @ResponseBody
    @RequestMapping(value = "findFromAjax.do")
    public  List<Permission> findFromAjax(@RequestParam(value="keyword",defaultValue="")String keyword, @RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize){

        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":keyword="+keyword+":pageNum="+pageNum+":pageSize="+pageSize);
        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Permission> dataList = permissionService.findFromAjax(keyword);
        PageInfo<Permission> pageInfo = new PageInfo<Permission>(dataList);

        return dataList;
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
    public String toAdd(Model model,@RequestParam(value="keyword",defaultValue="")String keyword) throws Exception{
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":keyword="+keyword);
        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            logger.error("查询不到登录信息！");
            throw new UnknownAccountException("查询不到登录信息！");
        }
        //获取分类Map，用于前端展示分类名称和下拉选择框,getTypeMap方法写在BaseController类，方便统一维护
        if(TYPESIGN!="") {
            model.addAttribute("typeMap", super.getTypeMap(TYPESIGN));
        }

        model.addAttribute("mark","add");
        model.addAttribute("keyword",keyword);
        return "user/permission";
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
    public String add(Model model,Permission permission,String mark, @RequestParam(value="keyword",defaultValue="")String keyword, @RequestParam(value="target",defaultValue="")String target, HttpServletRequest request) throws Exception{
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":mark="+mark+";keyword="+keyword+";target="+target);
        logger.debug("permission="+permission.toString());
        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            logger.error("查询不到登录信息！");
            throw new UnknownAccountException("查询不到登录信息！");
        }
//        logger.info("执行add:user"+user.toString());
        //补充创建人和修改人
        logger.info("执行add:permission"+permission.toString());
        permission.setCreator(user.getId());
        permission.setModifier(user.getId());

        //执行插入操作，返回插入数据的id
        int num = permissionService.insert(permission);
        if(num == 0){
            logger.error("插入数据不成功：permission="+permission.toString());
            throw new UnknownAccountException("插入数据不成功！");
        }
        logger.info("执行add:num"+num);
        String id = permission.getId().toString();
        logger.info("执行add:id="+id);
        permission = permissionService.findById(id);
        //取新增数据id，重新取数，获得插入数据库后的默认值
        model.addAttribute("permission",permission);
        model.addAttribute("mark","view");

        //获取分类Map，用于前端展示分类名称和下拉选择框,getTypeMap方法写在BaseController类，方便统一维护
        if(TYPESIGN!="") {
            model.addAttribute("typeMap", super.getTypeMap(TYPESIGN));
        }

        model.addAttribute("keyword",keyword);
        logger.info("target="+target+":跳转："+target.equals(""));
        if(target.equals("")){
            return "user/permission";
        }else{
            return "redirect:/permission/"+target;
        }
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
    public String toEdit(Model model,String id,@RequestParam(value="keyword",defaultValue="")String keyword) throws Exception{
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+"id="+id+";keyword="+keyword);//获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            logger.error("查询不到登录信息！");
            throw new UnknownAccountException("查询不到登录信息！");
        }

        //获取分类Map，用于前端展示分类名称和下拉选择框,getTypeMap方法写在BaseController类，方便统一维护
        if(TYPESIGN!="") {
            model.addAttribute("typeMap", super.getTypeMap(TYPESIGN));
        }

        Permission permission = permissionService.findById(id);
        model.addAttribute("mark","edit");
        model.addAttribute("permission",permission);
        model.addAttribute("keyword",keyword);
        return "user/permission";
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
    public String edit(Model model,Permission permission,String mark, @RequestParam(value="keyword",defaultValue="")String keyword, HttpServletRequest request) throws Exception {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+"mark="+mark+";keyword="+keyword);
        logger.debug("permission="+permission.toString());//获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            logger.error("查询不到登录信息！");
            throw new UnknownAccountException("查询不到登录信息！");
        }

        logger.info("执行update:permission"+permission.toString());
        //改变更新人
        permission.setModifier(user.getId());
        int num = permissionService.update(permission);
        String id = permission.getId().toString();
        //取id，重新取数，获得插入数据库后的默认值
        model.addAttribute("permission", permissionService.findById(id));
        model.addAttribute("mark","view");

        //获取分类Map，用于前端展示分类名称和下拉选择框,getTypeMap方法写在BaseController类，方便统一维护
        if(TYPESIGN!="") {
            model.addAttribute("typeMap", super.getTypeMap(TYPESIGN));
        }

        model.addAttribute("keyword",keyword);
        return "user/permission";
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
        Permission permission = new Permission();
        permission.setId(Integer.parseInt(id));
        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            logger.error("查询不到登录信息！");
            throw new UnknownAccountException("查询不到登录信息！");
        }
        permission.setModifier(user.getId());
        //逻辑删除
        int num = permissionService.delete(permission);
        if(num!=1){
            logger.error(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":删除id="+id+"的记录是 "+num+"条");
        }

        attr.addAttribute("keyword",keyword);
        return "redirect:/permission/list.do";
    }
}
