package com.example.logistics.utils;

import org.springframework.beans.BeanUtils;

import javax.swing.text.html.parser.Entity;

public class BeanUtil {

    public static <T> T copyProperties(Class<T> c, Object o) {
        try {
            T target = c.newInstance();
            BeanUtils.copyProperties(o, target);
            return target;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
