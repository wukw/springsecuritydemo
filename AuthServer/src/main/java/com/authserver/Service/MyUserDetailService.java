package com.authserver.Service;

import com.alibaba.fastjson.JSON;
import com.authserver.Model.User;
import com.authserver.Model.UserDetailsModel;
import com.authserver.Util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailService implements UserDetailsManager,Serializable,Ordered {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${createUserUrl}")
    String createUser;

    @Value("${updateUserUrl}")
    String updateUser;

    @Value("${loadUserByUsernameUrl}")
    String loadUserByUsernameUrl;




    @Override
    public void createUser(UserDetails user) {
        System.out.println("创建用户");

    }

    @Override
    public void updateUser(UserDetails user) {
        System.out.println("跟新用户");
    }

    @Override
    public void deleteUser(String username) {
        System.out.println("删除用户");
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        System.out.println("修改密码");
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("查询用户");
        String userJson = HttpUtil.doGet(loadUserByUsernameUrl+"/"+username);
        return jsonUserToUserDetails(userJson,username);
    }

    /**
     * 手机查询登陆
     * @param phone
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        System.out.println("查询用户");
        String userJson = HttpUtil.doGet(loadUserByUsernameUrl+"/"+phone);
        return jsonUserToUserDetails(userJson,phone);
    }




    /**
     * db user to UserDetails
     * @param userJson
     * @param username
     * @return
     */
    public UserDetails jsonUserToUserDetails(String userJson,String username){
        User user = null;
        try{
            user = JSON.parseObject(userJson, User.class);
        }catch (Exception e){
            throw new UsernameNotFoundException(username);
        }
        if(user == null || user.getUsername() == null)
            throw new UsernameNotFoundException(username);
        else{
            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
            authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
            user.setAuthority(authorityList);
            user.setIsAccountNonExpired(1);
            user.setIsAccountNonLocked(1);
            user.setIsEnabled(1);
            user.setIsCredentialsNonExpired(1);
            user.setPassword(bCryptPasswordEncoder.encode("123456"));
            UserDetailsModel userDetailsModel = new UserDetailsModel(user);
            return userDetailsModel;
        }
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
