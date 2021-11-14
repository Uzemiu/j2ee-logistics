package com.example.logistics.interceptor;

import com.example.logistics.annotation.AnonymousAccess;
import com.example.logistics.annotation.Permission;
import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.enums.Role;
import com.example.logistics.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod)handler;
            if(method.hasMethodAnnotation(AnonymousAccess.class)){
                return true;
            }
            User user = SecurityUtil.getCurrentUser();
            if(user == null){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            Permission permission = method.getMethodAnnotation(Permission.class);
            Role[] roles = {Role.ADMIN}; // if there is no @Permission, only admin is allowed
            if(permission != null){
                if(user instanceof Client && permission.allowClient()
                        || user instanceof Employee && permission.allowEmployee()){
                    return true;
                }
                roles = permission.allowRoles();
            }
            if(user instanceof Employee){
                Employee employee = (Employee) user;
                for (Role role : roles) {
                    if(role.equals(employee.getRole())){
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityUtil.removeCurrentUser();
    }
}
