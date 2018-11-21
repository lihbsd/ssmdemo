package com.ckjs.ssmdemo.service;

import com.ckjs.ssmdemo.entity.RolePermission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RolePermissionService {

    /**
     * @Description: 通过roleid查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:45
     */
    List<RolePermission> findByRoleid(Integer roleid);

    /**
     * @Description:通过ID查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:03
     */
    RolePermission findById(String id);

    /**
     * @Description:新增数据
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:04
     */
    int insert(RolePermission rolePermission);


    /**
     * @Description:逻辑删除
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:46
     */
    int delete(RolePermission rolePermission);
}
