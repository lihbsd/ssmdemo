package com.ckjs.ssmdemo.service;

import com.ckjs.ssmdemo.dao.MappingDao;
import com.ckjs.ssmdemo.entity.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/16 20:08
 */
@Service
public class MappingServiceImpl implements MappingService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MappingDao mappingDao;

    @Override
    public List<Mapping> findByKeyword(String keyword) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName());
        return mappingDao.findByKeyword(keyword);
    }

    @Override
    public List<Mapping> findBySource(String source) {
        return mappingDao.findBySource(source);
    }

    @Override
    public List<Mapping> findByTarget(String target) {
        return mappingDao.findByTarget(target);
    }

    @Override
    public List<Mapping> findByTargettype(String targettype) {
        return mappingDao.findByTargettype(targettype);
    }

    @Override
    public List<Mapping> findBySourcetype(String sourcetype) {
        return mappingDao.findBySourcetype(sourcetype);
    }

    @Override
    public List<Mapping> findByTargetAndType(String targettype, String target) {
        return mappingDao.findByTargetAndType(targettype,target);
    }

    @Override
    public List<Mapping> findBySourceAndType(String sourcetype, String source) {
        logger.info("sourcetype="+sourcetype+":source="+source);
        return mappingDao.findBySourceAndType(sourcetype,source);
    }


    @Override
    public Mapping findById(String id) {
        return (Mapping)mappingDao.findById(id).get(0);
    }

    @Override
    public int insert(Mapping mapping) {
        return mappingDao.insert(mapping);
    }

    @Override
    public int update(Mapping mapping) {
        return mappingDao.update(mapping);
    }

    @Override
    public int delete(Mapping mapping) {
        return mappingDao.delete(mapping);
    }
}