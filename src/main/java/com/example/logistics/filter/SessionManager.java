package com.example.logistics.filter;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final ConcurrentHashMap<String, HttpSession> sessions = new ConcurrentHashMap<>();

    public static HttpSession getSessoion(String sid){
        return sessions.get(sid);
    }

    public static void addSession(HttpSession session){
        sessions.put(session.getId(), session);
    }

    public static void removeSession(String sid){
        sessions.remove(sid);
    }
}
