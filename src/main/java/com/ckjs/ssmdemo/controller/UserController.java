package com.ckjs.ssmdemo.controller;

import com.ckjs.ssmdemo.entity.*;
import com.ckjs.ssmdemo.exception.MyException;
import com.ckjs.ssmdemo.service.RoleService;
import com.ckjs.ssmdemo.service.UserRoleService;
import com.ckjs.ssmdemo.service.UserService;
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
@RequestMapping(value = "user")
public class UserController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //依赖注入
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;

    //要显示的分类的sign值，因为多处使用，统一定义=""表示没有需要取值的type
    private static String TYPESIGN = "ROLETYPE";

    //统一定义模块关键字，用于权限控制
//    private static String SIGN = "person";



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
        List<User> roleList= userService.findByKeyword(keyword);
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
        List<User> userList = userService.findFromAjax(keyword);
        PageInfo<User> pageInfo = new PageInfo<User>(userList);
        //格式转换，把list对象全部转换成key-value形式
        List<KeyValue> keyValueList = new ArrayList();
        for(User user : userList){
            KeyValue keyValue = new KeyValue();
            keyValue.setKey(user.getId().toString());
            keyValue.setValue(user.getUsername());
            keyValueList.add(keyValue);
        }

        return keyValueList;
    }

    @RequestMapping(value = "toempower.do")
    /**
     * @Description:授权前查询授权数据
     * @param: [model, rolePermission, keyword]
     * @return: java.lang.String
     * @auther@date: ckjs 2018/11/15 22:06
     */
    public String toempower(Model model , String userid, @RequestParam(value="keyword",defaultValue="")String keyword) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":userid="+userid);
        if(userid==null || userid == ""){
            List<Role> roleList = roleService.findByKeyword("");
            User user = new User();
            user.setId(0);
            Map<Integer, Integer> userRoleMap = new LinkedHashMap();
            userRoleMap.put(0,0);
            model.addAttribute("mark", "edit");
            model.addAttribute("user", user);
            model.addAttribute("roleList", roleList);
            model.addAttribute("userRoleMap", userRoleMap);

        }else {
            User user = userService.findById(userid);
            List<Role> roleList = roleService.findByKeyword("");
            List<UserRole> userRoleList = userRoleService.findByUserid(user.getId());

            Map<Integer, Integer> userRoleMap = new LinkedHashMap();
            for (int i = 0; i < userRoleList.size(); i++) {
                UserRole userRole = (UserRole) userRoleList.get(i);
                userRoleMap.put(userRole.getRoleid(), userRole.getUserid());
            }
            logger.info("toempower:user=" + user.toString());
            logger.info("toempower:roleList=" + roleList.toString());
            logger.info("toempower:userRoleList=" + userRoleList.toString());
            model.addAttribute("mark", "edit");
            model.addAttribute("user", user);
            model.addAttribute("roleList", roleList);
            model.addAttribute("userRoleMap", userRoleMap);
        }
        return "user/userrole";
    }

    @RequestMapping(value = "empower.do")
    @ResponseBody
    /**
     * @Description:授权
     * @param: [model, roleid, permissionid]
     * @return: java.lang.String
     * @auther@date: ckjs 2018/11/15 21:56
     */
    public String empower(Model model , Integer userid,Integer roleid,String mark) throws Exception {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":roleid="+roleid+":userid="+userid+":mark="+mark);
        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            logger.error("查询不到登录信息！");
            throw new MyException("查询不到登录信息！");
        }
        int num = 0;
        if(mark.equals("true")){
            UserRole userRole = new UserRole();
            userRole.setUserid(userid);
            userRole.setRoleid(roleid);
            userRole.setCreator(user.getId());
            userRole.setModifier(user.getId());
            num = userRoleService.insert(userRole);
            if(num==0){
                throw new MyException("角色授权数据插入数据库失败！");
            }
        }else if(mark.equals("false")){
            UserRole userRole = new UserRole();
            userRole.setUserid(userid);
            userRole.setRoleid(roleid);
            num = userRoleService.delete(userRole);
            if(num==0){
                throw new MyException("角色授权数据删除数据库失败！");
            }
        }else{
            throw new MyException("mark标识未知，无法处理：mark="+mark);
        }

        return "{"+num+"}";
    }
}
