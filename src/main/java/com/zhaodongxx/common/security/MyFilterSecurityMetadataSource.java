package com.zhaodongxx.common.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.*;

/**
 * 路径拦截处理类
 *
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/6/11 17:26
 */
@Configuration
@Slf4j
public class MyFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    /**
     * Accesses the {@code ConfigAttribute}s that apply to a given secure object.
     *
     * @param object the object being secured
     * @return the attributes that apply to the passed in secured object. Should return an
     * empty collection if there are no applicable attributes.
     * @throws IllegalArgumentException if the passed object is not of a type supported by
     *                                  the <code>SecurityMetadataSource</code> implementation
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        //当前请求对象
        FilterInvocation fi = (FilterInvocation) object;
        //return null 表示允许访问，不做拦截
        if (isMatcherAllowedRequest(fi)) {
            return null;
        }

        log.info("当前请求的路径 {}", fi.getRequestUrl());

        List<ConfigAttribute> configAttributes = getMatcherConfigAttribute(fi.getRequestUrl());
        //返回当前路径所需角色，如果没有则拒绝访问
        return configAttributes.size() > 0 ? configAttributes : deniedRequest();
    }

    /**
     * If available, returns all of the {@code ConfigAttribute}s defined by the
     * implementing class.
     * <p>
     * This is used by the @link AbstractSecurityInterceptor to perform startup time
     * validation of each {@code ConfigAttribute} configured against it.
     *
     * @return the {@code ConfigAttribute}s or {@code null} if unsupported
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * Indicates whether the {@code SecurityMetadataSource} implementation is able to
     * provide {@code ConfigAttribute}s for the indicated secure object type.
     *
     * @param clazz the class that is being queried
     * @return true if the implementation can process the indicated class
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    /**
     * 获取当前路径所需要的角色
     *
     * @param url 当前路径
     * @return 所需角色集合
     */
    private List<ConfigAttribute> getMatcherConfigAttribute(String url) {
        List<ConfigAttribute> confs = new ArrayList<>(5);
        confs.add(new SecurityConfig(url));

        return confs;
    }

    /**
     * 判断当前请求是否在允许请求的范围内
     *
     * @param fi 当前请求
     * @return 是否在范围中
     */
    private boolean isMatcherAllowedRequest(FilterInvocation fi) {
        return allowedRequest().stream().map(AntPathRequestMatcher::new)
                .filter(requestMatcher -> requestMatcher.matches(fi.getHttpRequest()))
                .toArray().length > 0;
    }

    /**
     * @return 定义允许请求的列表
     */
    private List<String> allowedRequest() {
        return Arrays.asList(
                "/login/**",
                "/css/**",
                "/fonts/**",
                "/js/**",
                "/scss/**",
                "/img/**",
                "/logout/success",
                "/any-socket/**",
                "/ws/**",
                "/admin/**"
        );
    }

    /**
     * @return 默认拒绝访问配置
     */
    private List<ConfigAttribute> deniedRequest() {
        return Collections.singletonList(new SecurityConfig("ROLE_DENIED"));
    }
}

