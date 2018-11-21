package com.ckjs.ssmdemo.dao;

import com.ckjs.ssmdemo.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper     //声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
@Repository
/**
 * @Description:dao类
 * @Auther: ckjs
 * @Date: 2018/11/1 18:39
 */
public interface TypeDao {
    /**
     * @Description:查询所有
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:44
     */
    List<Type> findAll();

    /**
     * @Description: 通过关键字多字段模糊查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:45
     */
    List<Type> findByKeyword(String keyword);
    //通过标识查询
    List<Type> findBySign(String sign);
    //通过ID查询
    Type findById(String id);
    //通过编码查询
    Type findByCode(String code);
    //新增
    void insert(Type type);

    /**
     * @Description:更新数据
     * @param:
     * @return: 
     * @auther: ckjs
     * @date: 2018/11/1 18:47
     */
    void update(Type type);
    /**
     * @Description:逻辑删除
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:46
     */
    void delete(Type type);
}
