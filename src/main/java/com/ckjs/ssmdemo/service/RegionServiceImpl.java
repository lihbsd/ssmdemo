package com.ckjs.ssmdemo.service;

import com.ckjs.ssmdemo.dao.RegionDao;
import com.ckjs.ssmdemo.entity.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/2 16:21
 */
@Service
public class RegionServiceImpl implements RegionService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RegionDao regionDao;

    @Override
    public List<Region> findBySuperid(Integer superid) {
        return regionDao.findBySuperid(superid);
    }

    @Override
    public List<Region> findByStartup(boolean startup) {
        return regionDao.findByStartup(startup);
    }

    @Override
    public List<Region> findByKeyword(String keyword) {
        return regionDao.findByKeyword(keyword);
    }

    @Override
    public List<Region> findFromAjax(String keyword, String level) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":keyword="+keyword);
        return regionDao.findFromAjax(keyword , level);
    }

    @Override
    public Region findById(String id) {
//        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName()+":id="+id);
        return regionDao.findById(id).get(0);
    }

    @Override
    public int insert(Region region) {
        return regionDao.insert(region);
    }

    @Override
    public int update(Region region) {
        return regionDao.update(region);
    }

    @Override
    public int delete(Region region) {
        return regionDao.delete(region);
    }
}