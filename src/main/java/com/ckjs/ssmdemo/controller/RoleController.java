package com.ckjs.ssmdemo.controller;

import com.ckjs.ssmdemo.entity.*;
import com.ckjs.ssmdemo.exception.MyException;
import com.ckjs.ssmdemo.service.PermissionService;
import com.ckjs.ssmdemo.service.RolePermissionService;
import com.ckjs.ssmdemo.service.RoleService;
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
@RequestMapping(value = "role")
public class RoleController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //依赖注入
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;

    //要显示的分类的sign值，因为多处使用，统一定义=""表示没有需要取值的type
    private static String TYPESIGN = "ROLETYPE";

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

        //获取分类Map，用于前端展示分类名称和下拉选择框,getTypeMap方法写在BaseController类，方便统一维护
        if(TYPESIGN!="") {
            model.addAttribute("typeMap", super.getTypeMap(TYPESIGN));
        }

        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Role> roleList = roleService.findByKeyword(keyword);
        PageInfo<Role> pageInfo = new PageInfo<Role>(roleList);
        logger.info("findAll:roleList="+roleList.toString());

        model.addAttribute("page",pageInfo);
        model.addAttribute("pageNum",pageInfo.getPages());
        //返回的list参数名统一用dataList，简化前端取数
        model.addAttribute("dataList",roleList);
        model.addAttribute("keyword",keyword);
        return "user/roleList";
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

        //获取分类Map，用于前端展示分类名称和下拉选择框,getTypeMap方法写在BaseController类，方便统一维护
        if(TYPESIGN!="") {
            model.addAttribute("typeMap", super.getTypeMap(TYPESIGN));
        }

        Role role =  roleService.findById(id);
        logger.info("findById:role="+role.toString());
        model.addAttribute("role",role);
        model.addAttribute("mark","view");
        model.addAttribute("keyword",keyword);
        return "user/role";
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
        List<Role> roleList= roleService.findByKeyword(keyword);
        if(roleList.size()>0) {
            return "{\"msg\":\"false\"}";
        }
        return "{\"msg\":\"true\"}";
    }

    @ResponseBody
    @RequestMapping(value = "findFromAjax.do")
    /**
     * @Description:ajax通过keyword查询，返回keyvalue形式list的json数据。
     * @param: [keyword, pageNum, pageSize]
     * @return: java.util.List<com.ckjs.ssmdemo.entity.Role>
     * @auther@date: ckjs 2018/11/16 14:35
     */
    public  List<KeyValue> findFromAjax(@RequestParam(value="keyword",defaultValue="")String keyword, @RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize){

        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":keyword="+keyword+":pageNum="+pageNum+":pageSize="+pageSize);
        //分页
        PageHelper.startPage(pageNum,pageSize);
        List<Role> roleList = roleService.findFromAjax(keyword);
        PageInfo<Role> pageInfo = new PageInfo<Role>(roleList);
        //格式转换，把list对象全部转换成key-value形式
        List<KeyValue> keyValueList = new ArrayList();
        for(Role role : roleList){
            KeyValue keyValue = new KeyValue();
            keyValue.setKey(role.getId().toString());
            keyValue.setValue(role.getRolename());
            keyValueList.add(keyValue);
        }

        return keyValueList;
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

        //获取分类Map，用于前端展示分类名称和下拉选择框,getTypeMap方法写在BaseController类，方便统一维护
        if(TYPESIGN!="") {
            model.addAttribute("typeMap", super.getTypeMap(TYPESIGN));
        }

        model.addAttribute("mark","add");
        model.addAttribute("keyword",keyword);
        return "user/role";
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
    public String add(Model model,Role role,String mark, @RequestParam(value="keyword",defaultValue="")String keyword, HttpServletRequest request) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+"mark="+mark+";keyword="+keyword);
        logger.debug("role="+role.toString());
        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //补充创建人和修改人
        logger.info("执行add:role"+role.toString());
        role.setCreator(user.getId());
        role.setModifier(user.getId());

        //执行插入操作，返回插入数据的id
        int num = roleService.insert(role);
        String id = role.getId().toString();
        logger.info("执行add:id="+id);
        role = roleService.findById(id);
        //取新增数据id，重新取数，获得插入数据库后的默认值
        model.addAttribute("role",role);
        model.addAttribute("mark","view");

        //获取分类Map，用于前端展示分类名称和下拉选择框,getTypeMap方法写在BaseController类，方便统一维护
        if(TYPESIGN!="") {
            model.addAttribute("typeMap", super.getTypeMap(TYPESIGN));
        }

        model.addAttribute("keyword",keyword);
        return "user/role";
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

        //获取分类Map，用于前端展示分类名称和下拉选择框,getTypeMap方法写在BaseController类，方便统一维护
        if(TYPESIGN!="") {
            model.addAttribute("typeMap", super.getTypeMap(TYPESIGN));
        }

        Role role = roleService.findById(id);
        model.addAttribute("mark","edit");
        model.addAttribute("role",role);
        model.addAttribute("keyword",keyword);
        return "user/role";
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
    public String edit(Model model,Role role,String mark, @RequestParam(value="keyword",defaultValue="")String keyword, HttpServletRequest request) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+"mark="+mark+";keyword="+keyword);
        logger.debug("role="+role.toString());
        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        logger.info("执行update:role"+role.toString());
        //改变更新人
        role.setModifier(user.getId());
        int num = roleService.update(role);
        String id = role.getId().toString();
        //取id，重新取数，获得插入数据库后的默认值
        model.addAttribute("person", roleService.findById(id));
        model.addAttribute("mark","view");

        //获取分类Map，用于前端展示分类名称和下拉选择框,getTypeMap方法写在BaseController类，方便统一维护
        if(TYPESIGN!="") {
            model.addAttribute("typeMap", super.getTypeMap(TYPESIGN));
        }

        model.addAttribute("keyword",keyword);
        return "user/role";
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
        Role role = new Role();
        role.setId(Integer.parseInt(id));
        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        role.setModifier(user.getId());
        //逻辑删除
        int num = roleService.delete(role);
        if(num!=1){
            logger.error(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":删除id="+id+"的记录是 "+num+"条");
        }

        attr.addAttribute("keyword",keyword);
        return "redirect:/role/list.do";
    }

    @RequestMapping(value = "toempower.do")
    /**
     * @Description:授权前查询授权数据
     * @param: [model, rolePermission, keyword]
     * @return: java.lang.String
     * @auther@date: ckjs 2018/11/15 22:06
     */
    public String toempower(Model model , String roleid, @RequestParam(value="keyword",defaultValue="")String keyword) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":roleid="+roleid);

        Role role =  roleService.findById(roleid);
        List<Permission> permissionList = permissionService.findByKeyword("");
        List<RolePermission> rolePermissionList = rolePermissionService.findByRoleid(role.getId());

        Map<Integer, Integer> rolePermissionMap = new LinkedHashMap();
        for (int i = 0 ; i < rolePermissionList.size() ; i++){
            RolePermission rolePermission = (RolePermission)rolePermissionList.get(i);
            rolePermissionMap.put(rolePermission.getPermissionid(),rolePermission.getRoleid());
        }
        logger.info("toempower:role="+role.toString());
        logger.info("toempower:permissionList="+permissionList.toString());
        logger.info("toempower:rolePermissionList="+rolePermissionList.toString());
        model.addAttribute("mark","edit");
        model.addAttribute("role",role);
        model.addAttribute("permissionList",permissionList);
        model.addAttribute("rolePermissionMap",rolePermissionMap);
        return "user/rolepermission";
    }

    @RequestMapping(value = "empower.do")
    @ResponseBody
    /**
     * @Description:授权
     * @param: [model, roleid, permissionid]
     * @return: java.lang.String
     * @auther@date: ckjs 2018/11/15 21:56
     */
    public String empower(Model model , Integer roleid,Integer permissionid,String mark) throws Exception {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":roleid="+roleid+":permissionid="+permissionid+":mark="+mark);
        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            logger.error("查询不到登录信息！");
            throw new MyException("查询不到登录信息！");
        }
        int num = 0;
        if(mark.equals("true")){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleid(roleid);
            rolePermission.setPermissionid(permissionid);
            rolePermission.setCreator(user.getId());
            rolePermission.setModifier(user.getId());
            num = rolePermissionService.insert(rolePermission);
            if(num==0){
                throw new MyException("角色授权数据插入数据库失败！");
            }
        }else if(mark.equals("false")){
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleid(roleid);
            rolePermission.setPermissionid(permissionid);
            num = rolePermissionService.delete(rolePermission);
            if(num==0){
                throw new MyException("角色授权数据插入数据库失败！");
            }
        }else{
            throw new MyException("mark标识未知，无法处理：mark="+mark);
        }

        return "{"+num+"}";
    }
}
