package com.ckjs.ssmdemo.dao;

import com.ckjs.ssmdemo.entity.Region;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/2 12:01
 */
@Mapper     //声明是一个Mapper,与springbootApplication中的@MapperScan二选一写上即可
@Repository
public interface RegionDao {

    /**
     * @Description:查下一级地区
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:11
     */
    List<Region> findBySuperid(Integer superid);

    /**
     * @Description:查已开始推广的地区
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:12
     */
    List<Region> findByStartup(boolean startup);

    List<Region> findFromAjax(@Param("keyword")String keyword, @Param("level")String level);

    /**
     * @Description: 通过关键字多字段模糊查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:45
     */
    List<Region> findByKeyword(String keyword);

    /**
     * @Description:通过ID查询
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:03
     */
    List<Region> findById(String id);

    /**
     * @Description:新增数据
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/2 12:04
     */
    int insert(Region region);

    /**
     * @Description:更新数据
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:47
     */
    int update(Region region);

    /**
     * @Description:逻辑删除
     * @param:
     * @return:
     * @auther: ckjs
     * @date: 2018/11/1 18:46
     */
    int delete(Region region);
}
