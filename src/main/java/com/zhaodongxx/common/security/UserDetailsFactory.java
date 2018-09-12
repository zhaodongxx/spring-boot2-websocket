package com.zhaodongxx.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户实体类的工厂，未启用
 *
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/6/11 15:26
 */
public class UserDetailsFactory {

    private UserDetailsFactory() {
    }

//    static UserDetails create(User user) {
//        return new User(
//                user.getId(),
//                user.getUsername(),
//                user.getPassword(),
//                mapToGrantedAuthorities(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
//        );
//        return null;
//    }

    //将与用户类一对多的角色类的名称集合转换为 GrantedAuthority 集合
    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
