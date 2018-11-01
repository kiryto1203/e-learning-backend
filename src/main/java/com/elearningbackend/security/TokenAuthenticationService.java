package com.elearningbackend.security;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningAuthException;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.CurrentUser;
import com.elearningbackend.dto.LoginDto;
import com.elearningbackend.dto.UserDto;
import com.elearningbackend.service.IAbstractService;
import com.elearningbackend.service.UserService;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.SecurityUtil;
import com.elearningbackend.utility.ServiceUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class TokenAuthenticationService {

    @Autowired
    @Qualifier("userService")
    private static IAbstractService<UserDto, String> userServcie;

    static Authentication getAuthentication(HttpServletRequest request) throws ElearningException {
        String requestToken = request.getHeader(Constants.HEADER_AUTHORIZATION);
        if (requestToken == null)
            throw new ElearningException(Errors.NOT_TOKEN.getId(),Errors.NOT_TOKEN.getMessage());
        CurrentUser currentUser = new CurrentUser();
        List<GrantedAuthority> authorities = new ArrayList<>();
        getClaimsFromToken(requestToken, currentUser, authorities);
        if(userServcie==null){
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            userServcie = webApplicationContext.getBean(UserService.class);
        }
        UserDto userDto = userServcie.getOneByKey(currentUser.getUsername());
        recheckCurrentUser(currentUser, userDto);
        return new UsernamePasswordAuthenticationToken(currentUser, null, authorities);
    }

    private static void getClaimsFromToken(String token, CurrentUser currentUser, List<GrantedAuthority> authorities) throws ElearningException {
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
    }

    private static void recheckCurrentUser(CurrentUser currentUser, UserDto userDto) throws ElearningException{
        UserDto convertObject = (UserDto) ServiceUtils.convertObject(
            userDto, "displayName", "avatar", "address", "email", "phone", "role");
        if(!currentUser.getDisplayName().equals(convertObject.getDisplayName()) ||
           !currentUser.getAvatar().equals(convertObject.getAvatar()) ||
           !currentUser.getAddress().equals(convertObject.getAddress()) ||
           !currentUser.getEmail().equals(convertObject.getEmail()) ||
           !currentUser.getPhone().equals(convertObject.getPhone()) ||
           !currentUser.getRole().equals(convertObject.getRole())) {
            throw new ElearningException(Errors.TOKEN_NOT_MATCH.getId(),Errors.TOKEN_NOT_MATCH.getMessage());
        }
    }
}