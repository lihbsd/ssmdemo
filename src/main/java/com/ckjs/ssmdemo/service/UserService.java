package com.ckjs.ssmdemo.service;

import com.ckjs.ssmdemo.entity.Permission;
import com.ckjs.ssmdemo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> findFromAjax(@Param("keyword") String keyword);

    /**
     * @Description:通过登录输入的用户名查询用户，输入的可能是username，phone，email
     * @param:
     * @return:
     * @auther@date: ckjs 2018/11/7 13:37
     */
    User findByLoginName(String loginName);

    /**
     * @Description: 通过关键字多字段模糊查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:45
     */
    List<User> findByKeyword(String keyword);

    /**
     * @Description:通过ID查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:03
     */
    User findById(String id);

    /**
     * @Description:新增数据
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:04
     */
    int insert(User user);

    /**
     * @Description:更新数据
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:47
     */
    int update(User user);

    /**
     * @Description:逻辑删除
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:46
     */
    int delete(User user);
    /**
     * @Description:权限验证，通过userid，item，permission查询是否有权限
     * @param:
     * @return:
     * @auther@date: ckjs 2018/11/17 19:55
     */
    Boolean validateRight(String userid, String item, String permission);
}
