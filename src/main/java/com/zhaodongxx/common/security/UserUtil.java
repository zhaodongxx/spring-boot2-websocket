package com.zhaodongxx.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用戶工具类
 *
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/6/26 13:57
 */
public class UserUtil {

    /**
     * 获取当前用户
     * @return
     */
    public static MyUserDetails getCurrentUser() {
        MyUserDetails user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = null;
        if (authentication != null) {
            principal = authentication.getPrincipal();
        }
        if (principal != null && principal instanceof MyUserDetails) {
            user = (MyUserDetails) principal;
        }
        return user;
    }
}
