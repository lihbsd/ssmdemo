package com.ckjs.ssmdemo.service;

import com.ckjs.ssmdemo.dao.RolePermissionDao;
import com.ckjs.ssmdemo.entity.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/9 17:28
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public List<RolePermission> findByRoleid(Integer roleid) {
        return rolePermissionDao.findByRoleid(roleid);
    }

    @Override
    public RolePermission findById(String id) {
        return rolePermissionDao.findById(id).get(0);
    }

    @Override
    public int insert(RolePermission rolePermission) {
        return rolePermissionDao.insert(rolePermission);
    }

    @Override
    public int delete(RolePermission rolePermission) {
        return rolePermissionDao.delete(rolePermission);
    }
}