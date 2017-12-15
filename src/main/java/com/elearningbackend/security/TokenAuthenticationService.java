package com.elearningbackend.security;
import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.CurrentUser;
import com.elearningbackend.dto.UserDto;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.SecurityUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TokenAuthenticationService {

    static void addAuthentication(HttpServletResponse res, UserDto userDto) {
        try {
            res.getWriter().write(Constants.TOKEN_PREFIX+ " " + SecurityUtil.generateToken(userDto)); // write content to body response in filter
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    static Authentication getAuthentication(HttpServletRequest request) throws ElearningException {
        String token = request.getHeader(Constants.HEADER_AUTHORIZATION);
        if (token == null)
            throw new ElearningException(Errors.NOT_TOKEN.getId(),Errors.NOT_TOKEN.getMessage());
        CurrentUser currentUser = new CurrentUser();
        List<GrantedAuthority> authorities = new ArrayList<>();
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(Constants.SECRET)
                    .parseClaimsJws(token.replace(Constants.TOKEN_PREFIX, ""))
                    .getBody();
            currentUser.setUsername(claims.get("username").toString());
            currentUser.setRole(claims.get("role").toString());
            currentUser.setEmail(claims.get("email").toString());
            currentUser.setAvatar(claims.get("avatar").toString());
            currentUser.setDisplayName(claims.get("display_name").toString());
            currentUser.setAddress(claims.get("address").toString());
            currentUser.setPhone(claims.get("phone").toString());
            authorities.add(new SimpleGrantedAuthority(currentUser.getRole()));
        }catch (Exception e){
            throw new ElearningException(Errors.TOKEN_NOT_MATCH.getId(),Errors.TOKEN_NOT_MATCH.getMessage());
        }
        return new UsernamePasswordAuthenticationToken(currentUser, null, authorities);
    }

}