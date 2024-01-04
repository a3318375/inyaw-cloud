package com.inyaa.oauth.sys.service;

import com.inyaa.oauth.sys.bean.InyaaSysUser;
import com.inyaa.oauth.sys.bean.SysAppRole;
import com.inyaa.oauth.sys.dao.InyaaSysUserDao;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class InyaaSysUserService implements UserDetailsService {

    @Resource
    private InyaaSysUserDao inyaaSysUserDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InyaaSysUser user = inyaaSysUserDao.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return buildUserForAuthentication(user, buildUserAuthority(user.getRoleList()));
        }
    }

    private User buildUserForAuthentication(InyaaSysUser user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), user.getEnabled(), user.getAccountNonExpired(), user.getCredentialsNonExpired(), user.getAccountNonLocked(), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<SysAppRole> roleList) {
        Set<GrantedAuthority> setAuths = new HashSet<>();

        for (SysAppRole role : roleList) {
            setAuths.add(new SimpleGrantedAuthority(role.getRoleKey()));
        }
        return new ArrayList<>(setAuths);
    }
}
