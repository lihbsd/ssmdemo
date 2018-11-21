package com.ckjs.ssmdemo.service;

import com.ckjs.ssmdemo.entity.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserRoleService {

    /**
     * @Description: 通过userid查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:45
     */
    List<UserRole> findByUserid(Integer userid);

    /**
     * @Description:通过ID查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:03
     */
    UserRole findById(String id);

    /**
     * @Description:新增数据
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:04
     */
    int insert(UserRole userRole);


    /**
     * @Description:逻辑删除
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:46
     */
    int delete(UserRole userRole);
}
