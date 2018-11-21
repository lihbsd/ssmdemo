package com.ckjs.ssmdemo.dao;

import com.ckjs.ssmdemo.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper     //声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
@Repository
public interface UserRoleDao {

    /**
     * @Description: 通过roleid查询
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
    List<UserRole> findById(String id);

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
