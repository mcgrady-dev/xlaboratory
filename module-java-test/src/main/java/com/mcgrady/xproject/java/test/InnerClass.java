package com.mcgrady.xproject.java.test;

/**
 * Created by mcgrady on 2022/8/23.
 */
public class InnerClass {

    private int marker = 1;

    InnerClass() {
        System.out.println("InnerClass()");
    }

    public void f() {
        Inner inner = getInner();
        inner.f();
    }

    public Inner getInner() {
        return new Inner();
    }

    public static void main(String[] args) {
        InnerClass innerClass = new InnerClass();
        innerClass.f();
        innerClass.anonymousClass();
    }

    private void anonymousClass() {
        AbsInner absInner = new AbsInner(marker) {
            @Override
            protected void f() {
                System.out.println("AbsInner f()");
            }
        };
        absInner.f();
    }

    private void run() {
        int i = 1;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(marker);
                marker = 10;
            }
        };
    }

    static class StaticInner {

        StaticInner() {
            System.out.println("StaticInner()");
        }
    }

    abstract class AbsInner {

        AbsInner() {
            System.out.println("AbsInner()");
        }

        AbsInner(int marker) {
            System.out.println("AbsInner(" + marker + ")");
        }

        protected abstract void f();
    }

    private class Inner {

        Inner() {
            System.out.println("Inner()");
        }

        public void f() {
            System.out.println("Inner f(" + marker + ")");
        }
    }
}
