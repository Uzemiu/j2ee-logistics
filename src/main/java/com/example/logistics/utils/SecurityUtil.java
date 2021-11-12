package com.example.logistics.utils;


import com.example.logistics.model.entity.User;

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

    public static boolean isLogin(){
        return currentUser.get() != null;
    }
}
