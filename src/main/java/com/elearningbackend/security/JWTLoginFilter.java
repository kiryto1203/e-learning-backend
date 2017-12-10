package com.elearningbackend.security;

import com.elearningbackend.customerrorcode.Errors;
import com.elearningbackend.customexception.ElearningAuthException;
import com.elearningbackend.customexception.ElearningException;
import com.elearningbackend.dto.Result;
import com.elearningbackend.dto.UserDto;
import com.elearningbackend.entity.User;
import com.elearningbackend.repository.IUserRepository;
import com.elearningbackend.service.IAbstractService;
import com.elearningbackend.service.UserService;
import com.elearningbackend.utility.ServiceUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private UserService userService;

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {
        UserDto userDto = new UserDto(req.getParameter("username"),req.getParameter("password"));
        Map<String, List<String>> maps = ServiceUtils.validateRequired(userDto,"username","password");
        if(maps.size()>0)
            throw new ElearningAuthException(Errors.USERNAME_AND_PASSWORD_IS_NOT_MEPTY.getId(), Errors.USERNAME_AND_PASSWORD_IS_NOT_MEPTY.name());
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUsername(),userDto.getPassword(),Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {
            System.out.println(userService);
            if(userService==null){
                ServletContext servletContext = req.getServletContext();
                WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                userService = webApplicationContext.getBean(UserService.class);
            }
        UserDto userDto = null;
        try {
            userDto = userService.getOneByKey(auth.getName());
            TokenAuthenticationService
                    .addAuthentication(res, userDto);
        } catch (ElearningException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed)
            throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        response.getWriter()
                .write(mapper.writeValueAsString(
                                new Result(
                                        Errors.valueOf(failed.getMessage()).getId(),
                                        failed.getMessage(),
                                        null))
                );
    }
}