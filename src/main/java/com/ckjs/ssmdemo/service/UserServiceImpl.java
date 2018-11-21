package com.ckjs.ssmdemo.service;

import com.ckjs.ssmdemo.dao.UserDao;
import com.ckjs.ssmdemo.entity.Permission;
import com.ckjs.ssmdemo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/7 12:28
 */
@Service
public class UserServiceImpl implements UserService {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userDao;


    @Override
    public User findByLoginName(String loginName) {
        List<User> userList = userDao.findByLoginName(loginName);
        if (userList== null || userList.size()==0){
            return null;
        }else if(userList.size()>1){
            //如果通过登录名查出多条数据，写错误日志，但不报错，默认取第一条记录
            logger.error("用户登录名（包括username，phone，email）有重复，重复的登录名是："+loginName);
        }
        return userList.get(0);
    }

    @Override
    public List<User> findFromAjax(String keyword) {
        return userDao.findFromAjax(keyword);
    }

    @Override
    public List<User> findByKeyword(String keyword) {
        return userDao.findByKeyword(keyword);
    }

    @Override
    public User findById(String id) {
        List<User> userList = userDao.findById(id);
        if (userList== null || userList.size()==0){
            return null;
        }
        return userDao.findById(id).get(0);
    }

    @Override
    public int insert(User user) {
        return userDao.insert(user);
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public int delete(User user) {
        return userDao.delete(user);
    }

    /**
     * @Description:权限验证，通过userid，item，permission查询是否有权限
     * @param:
     * @return:
     * @auther@date: ckjs 2018/11/17 19:55
     */
    @Override
    public Boolean validateRight(String userid, String item, String permission){
        List<User> userList = userDao.validateRight(userid,item,permission);
        if(userList.size()>0){
            return true;
        }else{
            return false;
        }
    }
}