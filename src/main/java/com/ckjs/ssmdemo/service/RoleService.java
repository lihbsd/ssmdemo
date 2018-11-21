package com.ckjs.ssmdemo.service;

import com.ckjs.ssmdemo.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    List<Role> findByRoletype(String roletype);

    /**
     * @Description:通过Ajax查询,
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/7 13:34
     */
    List<Role> findFromAjax(@Param("keyword") String keyword);

    /**
     * @Description: 通过关键字多字段模糊查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:45
     */
    List<Role> findByKeyword(String keyword);

    /**
     * @Description:通过ID查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:03
     */
    Role findById(String id);

    /**
     * @Description:新增数据
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:04
     */
    int insert(Role role);

    /**
     * @Description:更新数据
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:47
     */
    int update(Role role);

    /**
     * @Description:逻辑删除
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:46
     */
    int delete(Role role);
}
