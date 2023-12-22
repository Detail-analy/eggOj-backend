package com.liu.yuoj.common;

import com.liu.yuoj.model.entity.User;

/**
 * @author 刘渠好
 * @date 2023-12-22 22:54
 * ThreadLocal ：储存用户信息
 */
public class UserThreadLocal {
    private static  final ThreadLocal<User> USER_HOLDER = new ThreadLocal<>();

    public static void set(User user){

        USER_HOLDER.set(user);
    }

    public static User get(){

        return USER_HOLDER.get();
    }

    //防止内存泄漏
    public static void remove(){

        USER_HOLDER.remove();
    }

}
