package com.example.logistics.filter;

import com.example.logistics.model.entity.User;
import com.example.logistics.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
@Order
public class AuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("user");
        SecurityUtil.setCurrentUser(user);
//        String token = TokenUtil.resolveToken(httpServletRequest);
//        if(token != null){
//            String userId = (String) redisUtil.get(token);
//            if(userId != null){
//                Optional<User> user = userService.getById(userId);
//                if(!user.isPresent()){
//                    filterChain.doFilter(httpServletRequest,httpServletResponse);
//                    return;
//                }
//                SecurityUtil.setCurrentUser(user.get());
//
//                long expire = redisUtil.getExpire(token);
//                if(expire < securityConfig.getTokenRefreshTime() * 3600_000){
//                    redisUtil.expire(token, securityConfig.getTokenExpireTime(), TimeUnit.HOURS);
//                }
//            }
//        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

}
