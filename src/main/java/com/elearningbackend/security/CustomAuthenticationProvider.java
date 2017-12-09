package com.elearningbackend.security;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningAuthException;
import com.elearningbackend.entity.User;
import com.elearningbackend.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName().trim();
        String password = authentication.getCredentials().toString().trim();
        User user = userRepository.findOne(name);
        if(user==null)
            throw new ElearningAuthException(Errors.USER_NOT_FOUND.getId(),Errors.USER_NOT_FOUND.name());
        if(!user.getPasswordDigest().equals(password))
        throw new ElearningAuthException(Errors.USER_PASSWORD_NOT_MATCH.getId(),Errors.USER_PASSWORD_NOT_MATCH.name());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return new UsernamePasswordAuthenticationToken(name,password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
