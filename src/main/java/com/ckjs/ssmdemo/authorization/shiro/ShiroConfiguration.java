package com.ckjs.ssmdemo.authorization.shiro;


import com.ckjs.ssmdemo.exception.MyExceptionResolver;
import com.ckjs.ssmdemo.service.UserService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description:
 * @Auther: ckjs
 * @Date: 2018/11/7 12:52
 */
@Configuration
public class ShiroConfiguration {


    @Resource
    private ShiroRealm shiroRealm;
    @Autowired
    private UserService userService;


    /*@Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }*/

    //将自己的验证方式加入容器
    @Bean
    public ShiroRealm ShiroRealm(){
        ShiroRealm shiroRealm = new ShiroRealm();
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(2);
        shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return shiroRealm;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean(name = "securityManager")
    public DefaultSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(ShiroRealm());
//        securityManager.setCacheManager(getEhCacheManager());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, Filter> filtersMap = shiroFilterFactoryBean.getFilters();

        LinkedHashMap<String,String> filterChainDefinitionMap = new LinkedHashMap<>();


        //静态文件不验证
        filterChainDefinitionMap.put("/static/**","anon");
        filterChainDefinitionMap.put("/static/css/**","anon");
        filterChainDefinitionMap.put("static/**","anon");
        filterChainDefinitionMap.put("/img/**","anon");
        filterChainDefinitionMap.put("/fonts/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/favicon.ico","anon");
        //登录、注册不验证
        filterChainDefinitionMap.put("/tologin.do","anon");
        filterChainDefinitionMap.put("/login.do","anon");
        filterChainDefinitionMap.put("/toregister.do","anon");
        filterChainDefinitionMap.put("/register.do","anon");
        filterChainDefinitionMap.put("/error.html","anon");
        filterChainDefinitionMap.put("/noright.html","anon");
        //登出
        filterChainDefinitionMap.put("/logout.do","logout");
        filterChainDefinitionMap.put("/","authc");


        //登录
        shiroFilterFactoryBean.setLoginUrl("/tologin.do");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/noright.html");

        //异常处理不验证权限
        filterChainDefinitionMap.put("/exception/**","anon");
        //主页登录即可访问
        filterChainDefinitionMap.put("/index","authc");


        //访问动态URL权限配置
        filtersMap.put("requestURL", getURLPathMatchingFilter());

//        filterChainDefinitionMap.put("/**","anon");
        filterChainDefinitionMap.put("/**","requestURL");

        //对所有用户认证
//        filterChainDefinitionMap.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }
    /**
     *   访问 权限 拦截器
     * @return
     */
//    @Bean
    public URLPathMatchingFilter getURLPathMatchingFilter() {
        return new URLPathMatchingFilter();
    }

    @Bean
    /**
     * @Description:密码加密逻辑
     * @param: []
     * @return: org.apache.shiro.authc.credential.HashedCredentialsMatcher
     * @auther@date: ckjs 2018/11/9 9:09
     */
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();

        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));

        return hashedCredentialsMatcher;
    }

    /**
     * 注册全局异常处理
     * @return
     */
    @Bean
    public HandlerExceptionResolver handlerExceptionResolver(){
        return new MyExceptionResolver();
    }

    //加入注解的使用，不加入这个注解不生效
    /*@Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }*/
}