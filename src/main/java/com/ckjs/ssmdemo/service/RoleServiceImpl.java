package com.ckjs.ssmdemo.service;

import com.ckjs.ssmdemo.dao.RoleDao;
import com.ckjs.ssmdemo.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/9 17:22
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findByRoletype(String roletype) {
        return roleDao.findByRoletype(roletype);
    }

    @Override
    public List<Role> findFromAjax(String keyword) {
        return roleDao.findFromAjax(keyword);
    }

    @Override
    public List<Role> findByKeyword(String keyword) {
        return roleDao.findByKeyword(keyword);
    }

    @Override
    public Role findById(String id) {
        return roleDao.findById(id).get(0);
    }

    @Override
    public int insert(Role role) {
        return roleDao.insert(role);
    }

    @Override
    public int update(Role role) {
        return roleDao.update(role);
    }

    @Override
    public int delete(Role role) {
        return roleDao.delete(role);
    }
}