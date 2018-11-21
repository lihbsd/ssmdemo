package com.ckjs.ssmdemo.authorization.dataright;

import com.ckjs.ssmdemo.annotation.DataRightAop;
import com.ckjs.ssmdemo.base.ReflectUtil;
import com.ckjs.ssmdemo.entity.User;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.apache.ibatis.reflection.SystemMetaObject.DEFAULT_OBJECT_FACTORY;
import static org.apache.ibatis.reflection.SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/20 10:07
 */
@Component
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = { Statement.class, ResultHandler.class })
})
public class SqlInterceptor implements Interceptor {
    /** 日志 */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {}

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.info("进入 SqlInterceptor 拦截器...");
        if(invocation.getTarget() instanceof RoutingStatementHandler) {
            try {

                RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
                StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
                //通过反射获取delegate父类BaseStatementHandler的mappedStatement属性
                MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
                BoundSql boundSql = delegate.getBoundSql();
                String sql = boundSql.getSql();
                logger.info("***********sql="+sql);
                logger.info("***********mappedStatement="+mappedStatement.toString());

                //通过注解获取权限表名
                DataRightAop dataRightAop = DataRightUtils.getDataRightAopByDelegate(mappedStatement);
                if(dataRightAop==null){
                    logger.info("***********dataRightAop没有权限注解，不处理");
                    return invocation.proceed();
                }
                logger.info("***********dataRightAop.tableName="+dataRightAop.tableName());
                //传入sql和映射，重新包装sql
                sql = permissionSql(sql,dataRightAop);

                ReflectUtil.setFieldValue(boundSql, "sql", sql);

            } catch (Exception e) {
                logger.error("mybatis拦截器发生错误：message="+e.getMessage());
                throw e;
            }
        }
        return invocation.proceed();
    }


    protected String permissionSql(String sql,DataRightAop dataRightAop) {
        logger.info(this.getClass().getName()+":"+ Thread.currentThread().getStackTrace()[1].getMethodName());

        //获得登录信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            logger.error("查询不到登录信息！");
            throw new UnknownAccountException("查询不到登录信息！");
        }
        logger.info("***********user="+user.getUsername());
        //根据映射传入dataRightAop取值tablename
        //通过t_data_right获取规则列表，参照字段
        //根据规则列表解析规则，获得userid列表
        //组成user.getid和userid列表比对的sql
        //重组sql返回

        StringBuilder sbSql = new StringBuilder(sql);
        return sbSql.toString();
    }
}