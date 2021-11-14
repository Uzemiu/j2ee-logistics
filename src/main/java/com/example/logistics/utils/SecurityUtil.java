package com.example.logistics.utils;


import com.example.logistics.model.entity.Client;
import com.example.logistics.model.entity.Employee;
import com.example.logistics.model.entity.User;
import com.example.logistics.model.enums.Role;

public class SecurityUtil {

    private final static ThreadLocal<User> currentUser = new ThreadLocal<>();

    public static User getCurrentUser(){
        return currentUser.get();
    }

    public static void setCurrentUser(User user){
        currentUser.set(user);
    }

    public static void removeCurrentUser(){
        currentUser.remove();
    }

    public static boolean isClient(){
        return currentUser.get() instanceof Client;
    }

    public static boolean isEmployee(){
        return currentUser.get() instanceof Employee;
    }

    public static boolean isRole(Role role){
        User user = currentUser.get();
        return user instanceof Employee && ((Employee)user).getRole().equals(role);
    }

    public static boolean isLogin(){
        return currentUser.get() != null;
    }
}
