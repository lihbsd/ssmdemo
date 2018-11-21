package com.ckjs.ssmdemo.authorization.dataright;

import com.ckjs.ssmdemo.annotation.DataRightAop;
import org.apache.ibatis.mapping.MappedStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @Description:数据权限工具类
 * @Auther: ckjs
 * @Date: 2018/11/20 19:05
 */
public class DataRightUtils {

    public static DataRightAop getDataRightAopByDelegate(MappedStatement mappedStatement){
        DataRightAop dataRightAop = null;
        try {
            String id = mappedStatement.getId();
            String className = id.substring(0, id.lastIndexOf("."));
            String methodName = id.substring(id.lastIndexOf(".") + 1, id.length());
            final Class cls = Class.forName(className);
            final Method[] method = cls.getMethods();
            for (Method me : method) {
                if (me.getName().equals(methodName) && me.isAnnotationPresent(DataRightAop.class)) {
                    dataRightAop = me.getAnnotation(DataRightAop.class);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return dataRightAop;
    }
}