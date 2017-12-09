package com.elearningbackend.security;
import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security
            .authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static java.util.Collections.emptyList;

class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    static void addAuthentication(HttpServletResponse res, String username) {
        try {
            String JWT = Jwts.builder()
                    .setSubject(username)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET)
                    .compact();
            res.getWriter().write(TOKEN_PREFIX + " " + JWT); // write content to body response in filter
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    static Authentication getAuthentication(HttpServletRequest request) throws ElearningException {
        String token = request.getHeader(HEADER_STRING);
        if (token == null)
            throw new ElearningException(Errors.NOT_TOKEN.getId(),Errors.NOT_TOKEN.getMessage());
        String user;
        try{ user = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
        }catch (Exception e){
            throw new ElearningException(Errors.TOKEN_NOT_MATCH.getId(),Errors.TOKEN_NOT_MATCH.getMessage());
        }
        return user != null ?
                new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
                null;
    }
}