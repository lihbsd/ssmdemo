package com.ckjs.ssmdemo.dao;

import com.ckjs.ssmdemo.entity.Mapping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper     //声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
@Repository
public interface MappingDao {

    /**
     * @Description: 通过keyword查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:45
     */
    List<Mapping> findByKeyword(String keyword);

    /**
     * @Description: 通过source查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:45
     */
    List<Mapping> findBySource(String source);

    /**
     * @Description: 通过target查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:45
     */
    List<Mapping> findByTarget(String target);

    /**
     * @Description: 通过targettype查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:45
     */
    List<Mapping> findByTargetAndType(String targettype,String target);

    /**
     * @Description: 通过sourcetype查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:45
     */
    List<Mapping> findBySourceAndType(@Param("sourcetype") String sourcetype, @Param("source") String source);

    /**
     * @Description: 通过targettype查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:45
     */
    List<Mapping> findByTargettype(String targettype);

    /**
     * @Description: 通过sourcetype查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:45
     */
    List<Mapping> findBySourcetype(String sourcetype);

    /**
     * @Description:通过ID查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:03
     */
    List<Mapping> findById(String id);

    /**
     * @Description:新增数据
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:04
     */
    int insert(Mapping mapping);

    /**
     * @Description:修改数据
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:04
     */
    int update(Mapping mapping);

    /**
     * @Description:逻辑删除
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:46
     */
    int delete(Mapping mapping);
}
