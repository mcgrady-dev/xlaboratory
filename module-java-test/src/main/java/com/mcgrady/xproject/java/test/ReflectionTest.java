package com.mcgrady.xproject.java.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by mcgrady on 2022/9/28.
 */
public class ReflectionTest {

    public static void main(String[] args) {

        Class clz = null;
        try {
            clz = Class.forName("com.mcgrady.xproject.java.test.Apple");
            Method setPriceMethod = clz.getMethod("setPrice", int.class);
            Object instance = clz.newInstance();
            setPriceMethod.invoke(instance, 10);

        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }


    static class Apple {
        private int price;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
