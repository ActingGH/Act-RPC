package com.acting.actrpc.provider;

import com.acting.actrpc.common.model.User;
import com.acting.actrpc.common.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名："+user.getName());
        return user;
    }
}
