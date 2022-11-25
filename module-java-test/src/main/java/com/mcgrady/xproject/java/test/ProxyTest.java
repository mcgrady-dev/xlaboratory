package com.mcgrady.xproject.java.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by mcgrady on 2022/10/8.
 */
public class ProxyTest {

    public static void main(String[] args) {
        dynamicProxy();
    }

    private static void dynamicProxy() {
        IUserDao target = new UserDao();
        Utils.println(target.getClass().toString());
        IUserDao proxy = (IUserDao) new ProxyFactory(target).getProxyInstance();
        Utils.println(proxy.getClass().toString());
        proxy.save();
    }
}


interface IUserDao {
    public void save();
}

class UserDao implements IUserDao {

    @Override
    public void save() {
        Utils.println("save data");
    }
}

class ProxyFactory {
    private Object target;

    public ProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Utils.println("invoke before");
                method.invoke(target, args);
                Utils.println("invoke after");
                return null;
            }
        });
    }
}


