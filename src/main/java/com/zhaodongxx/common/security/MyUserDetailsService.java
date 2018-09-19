package com.zhaodongxx.common.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Security 默认会使用 DaoAuthenticationProvider。
 * DaoAuthenticationProvider 在进行认证的时候需要UserDetailsService 来获取用户的信息 UserDetails
 *
 * @author zhaodong zhaodongxx@outlook.com
 * @version v1.0
 * @since 2018/6/7 10:48
 */

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.debug("MyUserDetailsService loadUserByUsername " + username);
//        User user = userService.findByUserName(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("用户名不存在");
//        }
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        log.info(JSON.toJSONString(permissionCustomMapper.selectPersByAccount(username)));
//
//        for (Permission per : permissionCustomMapper.selectPersByAccount(username)) {
//            authorities.add(new SimpleGrantedAuthority(per.getUrl()));
//            authorities.add(new SimpleGrantedAuthority(per.getName()));
//        }
//        return new MyUserDetails(user.getId(), user.getUsername(),
//                user.getPassword(), authorities);
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        return new MyUserDetails(1, username, "123456", authorities);


    }
}
