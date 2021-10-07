package com.example.Security;


import com.example.Entites.Userr;
import com.example.Service.initDataImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    initDataImp serviceRoleUserImp;
    //************************** Convert UserAPP to spring User
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Userr userApp = serviceRoleUserImp.ConsultUserByName(s);
        //**************************  to add roles to spring user from UserApp they must be a collection of GrantedAuthority
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        userApp.getRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        });
        return new User(userApp.getUserLogin(), userApp.getUserPassword(), grantedAuthorities);
    }
}
