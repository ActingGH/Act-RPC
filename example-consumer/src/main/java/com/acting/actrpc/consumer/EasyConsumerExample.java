package com.acting.actrpc.consumer;

import com.acting.actrpc.common.model.User;
import com.acting.actrpc.common.service.UserService;
import com.acting.actrpc.proxy.ServiceProxyFactory;
import com.acting.actrpc.server.HttpServer;
import com.acting.actrpc.server.VertxHttpServer;

public class EasyConsumerExample {
    public static void main(String[] args) {
        UserService userService= ServiceProxyFactory.getProxy(UserService.class);
        User user=new User();
        user.setName("acting");
        User newUser=userService.getUser(user);
        if(newUser==null)
        {
            System.out.println("user == null");
        }else{
            System.out.println(newUser.getName());
        }


    }


}
