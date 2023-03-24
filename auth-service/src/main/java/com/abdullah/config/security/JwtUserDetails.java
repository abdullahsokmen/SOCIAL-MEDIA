package com.abdullah.config.security;

import com.abdullah.exception.AuthManagerException;
import com.abdullah.exception.ErrorType;
import com.abdullah.repository.entity.Auth;
import com.abdullah.service.AuthService;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.CipherSpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetails implements UserDetailsService {

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }


    public UserDetails loadUserByUserId(Long id) throws UsernameNotFoundException {
        Optional<Auth>auth=authService.findById(id);

        if (auth.isPresent()){
            List<GrantedAuthority> authorityList =new ArrayList<>();
            authorityList.add(new SimpleGrantedAuthority(auth.get().getRole().toString()));

            return User.builder()
                    .username(auth.get().getUsername())
                    .password("")
                    .accountExpired(false)
                    .accountLocked(false)
                    .authorities(authorityList)
                    .build();
        }


        throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
    }



}
