package com.sdxx.security.config.service;

import com.sdxx.security.entity.Permission;
import com.sdxx.security.entity.ElecUser;
import com.sdxx.security.service.PermissionService;
import com.sdxx.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Autowired
    PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || "".equals(username)) {
            throw new RuntimeException("用户不能为空");
        }
        //根据用户名查询用户
        ElecUser elecUser = userService.selectByName(username);
        if (elecUser == null) {
            throw new RuntimeException("用户不存在");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (elecUser != null) {
            //获取该用户所拥有的权限
            List<Permission> permissions = permissionService.selectListByUser(elecUser.getId());
            // 声明用户授权
            permissions.forEach(permission -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getCode());
                grantedAuthorities.add(grantedAuthority);
            });
        }
        return new User(elecUser.getUserName(), elecUser.getPassword(),
                elecUser.getEnabled(),elecUser.getAccountNonExpired(),false,
                elecUser.getAccountNonLocked(), grantedAuthorities);
    }
}
