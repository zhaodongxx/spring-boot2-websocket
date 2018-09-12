package com.zhaodongxx.common.security;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.Collection;

/**
 * 访问决策管理器类
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/6/11 14:07
 */
@Configuration
@Slf4j
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * 当前环境
     */
    @Value("${spring.profiles.active}")
    private String env;

    /**
     * 判定是否拥有权限的决策方法，
     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
     *
     * @param authentication 是UserDetail对象中的getAuthorities方法返回的GrantedAuthority权限信息集合
     * @param object 包含客户端发起的请求的requset信息，可转换为HttpServletRequest request = ((FilterInvocation)object).getHttpRequest();
     * @param configAttributes 为MyInvocationSecurityMetadataSource的getAttributes(Object object)这个方法返回的结果，
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        log.info("进入了 访问决策 方法");
        log.info("用户拥有的权限列表 {}",JSON.toJSONString(authentication.getAuthorities()));
        log.info("该路径需要的权限列表 {}",JSON.toJSONString(configAttributes));


        //未登录时security会默认生成一个匿名用户,
        if (authentication instanceof AnonymousAuthenticationToken ||
                authentication.getPrincipal().toString() == "anonymousUser") {
            //未登录用户不能访问接口
            throw new AccessDeniedException("permission denied");
        }

        //开发环境不启用权限验证
        if (StringUtils.equals(env, "dev") || true) {
            return;
        }

        if (null == configAttributes || configAttributes.size() <= 0) {
            return;
        }

//        //当前用户拥有的角色集合
//        List<String> roleCodes = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//
//        //访问路径所需要的角色集合
//        List<String> configRoleCodes = configAttributes.stream().map(ConfigAttribute::getAttribute).collect(Collectors.toList());
//        for (String roleCode : roleCodes){
//            if(configRoleCodes.contains(roleCode)){
//                return;
//            }
//        }
//        throw new AccessDeniedException("permission denied");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
