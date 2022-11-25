package com.mcgrady.xproject.java.test;

import java.io.Serializable;

/**
 * Created by mcgrady on 2022/8/18.
 */
public class Boxing {


    public static void main(String[] args) {

        int int1 = 1;
        Integer integer1 = Integer.valueOf(int1);   // 手动装箱
        Integer integer2 = int1;    // 自动装箱

        // int1自动装箱为Integer类型，再进行比较
        print("integer1.equals(int1)\t" + integer1.equals(int1));

        // integer1自动拆箱为int类型，再进行比较
        print("integer1 == int1\t" + (integer1 == int1));

        // 比较integer引用地址时，有无缓存的不同结果
        print("Integer.valueOf(1) == Integer.valueOf(1)\t" + (Integer.valueOf(1) == Integer.valueOf(1)));
        print("Integer.valueOf(999) == Integer.valueOf(999)\t" + (Integer.valueOf(999) == Integer.valueOf(999)));

        double double0_1 = 0.1;
        Double d1 = Double.valueOf(double0_1);    // 手动装箱
        Double d2 = double0_1;    // 自动装箱
        //比较Double包装类的引用地址，由于Double包装类没有缓存，d1\d2为不同的两个对象引用
        print("d1 == d2\t" + (d1 == d2));

        Integer sum = 0;
        for (int i = 1; i < 20; i++) {
            print("sum(" + sum + ")+i(" + i +")=" + (sum += i));
        }
    }


    public static void print(String msg) {
        System.out.println(msg);
    }
}
