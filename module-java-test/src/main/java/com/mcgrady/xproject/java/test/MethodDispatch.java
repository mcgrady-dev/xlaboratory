package com.mcgrady.xproject.java.test;

/**
 * 方法分派
 * Created by mcgrady on 2022/9/29.
 */
public class MethodDispatch {

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Woman();
        MethodDispatch root = new MethodDispatch();
        root.sayHello(man);
        root.sayHello(woman);
    }
    public void sayHello(Human guy) { System.out.println("hello,guy!"); }

    public void sayHello(Man guy) { System.out.println("hello,gentleman!"); }

    public void sayHello(Woman guy) { System.out.println("hello,lady!"); }

    static abstract class Human { }

    static class Man extends Human { }

    static class Woman extends Human { }
}
