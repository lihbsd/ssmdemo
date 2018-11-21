package com.ckjs.ssmdemo.service;

import com.ckjs.ssmdemo.entity.Type;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
/**
 * @Description:service类
 * @Auther: ckjs
 * @Date: 2018/11/1 18:39
 */
public interface TypeService {
    //查询所有
    List<Type> findAll();
    //通过关键字多字段模糊查询
    List<Type> findByKeyword(String keyword);
    //通过标识查询
    Map findBySign(String sign);
    //通过ID查询
    Type findById(String id);
    //通过编码查询
    Type findByCode(String code);
    //新增
    String insert(Type type);
    //修改
    String update(Type type);
    //逻辑删除
    String delete(Type type);
}
