package com.inyaw.admin.sys.service;

import com.inyaw.admin.config.RedisService;
import com.inyaw.admin.sys.bean.InyaaSysUser;
import com.inyaw.admin.sys.bean.InyaaSysUserDetail;
import com.inyaw.admin.sys.dao.InyaaSysRoleDao;
import com.inyaw.admin.sys.dao.InyaaSysUserDao;
import com.inyaw.admin.sys.dao.SysAppRoleDao;
import com.inyaw.admin.sys.dto.LoginUser;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class InyaaSysUserService implements UserDetailsService {

    @Resource
    private InyaaSysUserDao inyaaSysUserDao;
    @Resource
    private InyaaSysRoleDao inyaaSysRoleDao;

    @Resource
    private SysAppRoleDao sysAppRoleDao;
    @Resource
    private RedisService redisService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InyaaSysUser user = inyaaSysUserDao.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            LoginUser loginUser = new LoginUser();
            if (redisService.get(user.getUsername()) != null) {
                loginUser = (LoginUser) redisService.get(user.getUsername());
                String token = loginUser.getToken();
                BeanUtils.copyProperties(user, loginUser);
                loginUser.setToken(token);
            } else {
                BeanUtils.copyProperties(user, loginUser);
                String token = UUID.randomUUID().toString();
                loginUser.setToken(token);
            }
            redisService.set(user.getUsername(), loginUser);
            List<String> roles = inyaaSysRoleDao.findRoleKeyByUserId(user.getId());
            //List<String> roles = new ArrayList<>();
            return buildUserForAuthentication(user, buildUserAuthority(roles));
        }
    }

    public InyaaSysUser getByUsername(String username) {
        return inyaaSysUserDao.getByUsername(username);
    }

    private User buildUserForAuthentication(InyaaSysUser user, List<GrantedAuthority> authorities) {
        return new User(user.getUsername(), user.getPassword(), user.getEnabled(), user.getAccountNonExpired(), user.getCredentialsNonExpired(), user.getAccountNonLocked(), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<String> userRoles) {
        Set<GrantedAuthority> setAuths = new HashSet<>();

        for (String userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole));
        }
        return new ArrayList<>(setAuths);
    }

    public List<InyaaSysUser> findAll(InyaaSysUser user) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        Example<InyaaSysUser> ex = Example.of(user, matcher);
        return inyaaSysUserDao.findAll(ex);
    }

    public List<InyaaSysUser> findUserList() {
        return inyaaSysUserDao.findAll();
    }

    public void delete(InyaaSysUser user) {
        inyaaSysUserDao.delete(user);
    }

    public void save(InyaaSysUser user, boolean updatePassword) {
        if (StringUtils.isNotBlank(user.getPassword()) && updatePassword) {
            BCryptPasswordEncoder bcry = new BCryptPasswordEncoder();
            user.setPassword(bcry.encode(user.getPassword()));
        }
        if (user.getInyaaSysUserDetail() == null) {
            user.setInyaaSysUserDetail(new InyaaSysUserDetail());
        }
        if (user.getInyaaSysUserDetail().getCreateTime() == null) {
            user.getInyaaSysUserDetail().setCreateTime(LocalDateTime.now());
        }
        if (user.getAccountNonLocked() != null) {
            user.setAccountNonLocked(true);
        }
        if (user.getAccountNonExpired() != null) {
            user.setAccountNonExpired(true);
        }
        if (user.getCredentialsNonExpired() != null) {
            user.setCredentialsNonExpired(true);
        }
        if (user.getRoleList() != null && user.getRoleList().size() > 0) {
            sysAppRoleDao.saveAll(user.getRoleList());
        }
        inyaaSysUserDao.save(user);
    }
}
