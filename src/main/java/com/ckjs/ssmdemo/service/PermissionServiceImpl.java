package com.ckjs.ssmdemo.service;

import com.ckjs.ssmdemo.dao.PermissionDao;
import com.ckjs.ssmdemo.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/9 17:25
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<Permission> findByItem(String item) {
        return permissionDao.findByItem(item);
    }

    @Override
    public List<Permission> findFromAjax(String keyword) {
        return permissionDao.findFromAjax(keyword);
    }

    @Override
    public List<Permission> findByKeyword(String keyword) {
        return permissionDao.findByKeyword(keyword);
    }

    @Override
    public Permission findById(String id) {
        return permissionDao.findById(id).get(0);
    }

    @Override
    public int insert(Permission permission) {
        return permissionDao.insert(permission);
    }

    @Override
    public int update(Permission permission) {
        return permissionDao.update(permission);
    }

    @Override
    public int delete(Permission permission) {
        return permissionDao.delete(permission);
    }
}