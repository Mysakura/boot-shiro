package com.example.demo.conf;

import com.example.demo.auth.PermissionRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;

/**
 * @program: boot-shiro
 * @description:
 * @author: 001977
 * @create: 2018-07-17 18:22
 */
@Configuration
public class ShiroConfig {

    /**
     * 1. 配置SecurityManager
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(cacheManager());
        securityManager.setRealm(realm());
        return securityManager;
    }

    /**
     * 2. 配置缓存
     * @return
     */
    @Bean
    public CacheManager cacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return ehCacheManager;
    }

    /**
     * 3. 配置Realm
     * @return
     */
    @Bean
    public AuthorizingRealm realm(){
        PermissionRealm realm = new PermissionRealm();
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 指定加密算法
        matcher.setHashAlgorithmName("MD5");
        // 指定加密次数
        matcher.setHashIterations(10);
        // 指定这个就不会报错
        matcher.setStoredCredentialsHexEncoded(true);
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    /**
     * 4. 配置LifecycleBeanPostProcessor，可以来自动的调用配置在Spring IOC容器中 Shiro Bean 的生命周期方法
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 5. 启用IOC容器中使用Shiro的注解，但是必须配置第四步才可以使用
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }

    /**
     * 6. 配置ShiroFilter
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        // 静态资源
        map.put("/css/**", "anon");
        map.put("/js/**", "anon");

        // 公共路径
        map.put("/login", "anon");
        map.put("/register", "anon");
        //map.put("/*", "anon");

        // 登出,项目中没有/logout路径,因为shiro是过滤器,而SpringMVC是Servlet,Shiro会先执行
        map.put("/logout", "logout");

        // 授权
        map.put("/user/**", "authc,roles[user]");
        map.put("/admin/**", "authc,roles[admin]");

        // everything else requires authentication:
        map.put("/**", "authc");

        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 配置SecurityManager
        factoryBean.setSecurityManager(securityManager());
        // 配置权限路径
        factoryBean.setFilterChainDefinitionMap(map);
        // 配置登录url
        factoryBean.setLoginUrl("/");
        // 配置无权限路径
        factoryBean.setUnauthorizedUrl("/unauthorized");
        return factoryBean;
    }

}
