package com.elearningbackend.security;
import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.UserDto;
import com.elearningbackend.utility.Constants;
import com.elearningbackend.utility.ServiceUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

class TokenAuthenticationService {

    static void addAuthentication(HttpServletResponse res, UserDto userDto) {
        try {
            // convert object UserDTO to retricted error null when get claims
            userDto = (UserDto) ServiceUtils.convertObject(userDto,"address","phone","avatar","displayName");
            String JWT = Jwts.builder()
                    .setId(UUID.randomUUID().toString()) // set random id
                    .setSubject(userDto.getUsername()) // set username
                    .claim("username",userDto.getUsername())
                    .claim("role",userDto.getRole())
                    .claim("email",userDto.getEmail())
                    .claim("address",userDto.getAddress())
                    .claim("phone",userDto.getPhone())
                    .claim("avatar",userDto.getAvatar())
                    .claim("display_name",userDto.getDisplayName())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, Constants.SECRET)
                    .compact();
            res.getWriter().write(Constants.TOKEN_PREFIX+ " " + JWT); // write content to body response in filter
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    static Authentication getAuthentication(HttpServletRequest request) throws ElearningException {
        String token = request.getHeader(Constants.HEADER_STRING);
        if (token == null)
            throw new ElearningException(Errors.NOT_TOKEN.getId(),Errors.NOT_TOKEN.getMessage());
        UserDto userDto = new UserDto();
        List<GrantedAuthority> authorities = new ArrayList<>();
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(Constants.SECRET)
                    .parseClaimsJws(token.replace(Constants.TOKEN_PREFIX, ""))
                    .getBody();
            userDto.setUsername(claims.get("username").toString());
            userDto.setRole(claims.get("role").toString());
            userDto.setEmail(claims.get("email").toString());
            userDto.setAvatar(claims.get("avatar").toString());
            userDto.setDisplayName(claims.get("display_name").toString());
            userDto.setAddress(claims.get("address").toString());
            userDto.setPhone(claims.get("phone").toString());
            authorities.add(new SimpleGrantedAuthority(userDto.getRole()));
        }catch (Exception e){
            throw new ElearningException(Errors.TOKEN_NOT_MATCH.getId(),Errors.TOKEN_NOT_MATCH.getMessage());
        }
        return userDto != null ?
                new UsernamePasswordAuthenticationToken(userDto, null, authorities) :
                null;
    }

}