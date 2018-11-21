package com.ckjs.ssmdemo.service;

import com.ckjs.ssmdemo.dao.UserRoleDao;
import com.ckjs.ssmdemo.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/9 17:30
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public List<UserRole> findByUserid(Integer userid) {
        return userRoleDao.findByUserid(userid);
    }

    @Override
    public UserRole findById(String id) {
        return userRoleDao.findById(id).get(0);
    }

    @Override
    public int insert(UserRole userRole) {
        return userRoleDao.insert(userRole);
    }

    @Override
    public int delete(UserRole userRole) {
        return userRoleDao.delete(userRole);
    }
}