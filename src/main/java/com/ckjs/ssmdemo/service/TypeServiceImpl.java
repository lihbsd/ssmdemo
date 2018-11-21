package com.ckjs.ssmdemo.service;

import com.ckjs.ssmdemo.dao.TypeDao;
import com.ckjs.ssmdemo.entity.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
/**
 * @Description:service的实现类
 * @Auther: ckjs
 * @Date: 2018/11/1 18:39
 */
public class TypeServiceImpl implements TypeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TypeDao typeDao;

    @Override
    /**
     * 功能描述: 查询所有未删除数据
     *
     * @param: []
     * @return: java.util.List<com.ckjs.ssmdemo.entity.Type>
     * @auther: ckjs
     * @date: 2018/11/1 18:41
     */
    public List<Type> findAll() {
        return typeDao.findAll();
    }

    @Override
    /**
     * 功能描述: 通过关键词查询数据
     *
     * @param: [keyword]
     * @return: java.util.List<com.ckjs.ssmdemo.entity.Type>
     * @auther: ckjs
     * @date: 2018/11/1 18:42
     */
    public List<Type> findByKeyword(String keyword) {
        return typeDao.findByKeyword(keyword);
    }

    @Override
    public Map findBySign(String sign) {
        List<Type> typeList = typeDao.findBySign(sign);
        logger.debug("typeList="+typeList.toString());
        Map<String, String> typeMap = new LinkedHashMap();
        for (int i = 0 ; i < typeList.size() ; i++){
            Type type = (Type)typeList.get(i);
            typeMap.put(type.getCode(),type.getName());
        }

        logger.debug("typeMap="+typeMap.toString());
        return typeMap;
    }

    @Override
    public Type findById(String id) {
        return typeDao.findById(id);
    }

    public Type findByCode(String code) {
        return typeDao.findByCode(code);
    }

    @Override
    public String insert(Type type) {
        typeDao.insert(type);
        return type.getId().toString();
    }

    @Override
    public String update(Type type) {
        typeDao.update(type);
        return type.getId().toString();
    }

    @Override
    public String delete(Type type) {
        //逻辑删除
        typeDao.delete(type);
        return type.getId().toString();
    }
}
