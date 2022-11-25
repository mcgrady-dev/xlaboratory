package com.mcgrady.xproject.java.test;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by mcgrady on 2022/8/18.
 */
public class StringTest {

    public static void main(String[] args) {

//        immutable();

//        literal();

//        intern();

        reflex();
    }

    private static void reflex() {
        String name = "xxxx";
        try {
            Field field = String.class.getDeclaredField("value");
            field.setAccessible(true);
            char value[] = (char[]) field.get(name);
            value[0] = 'a';
            println("name="+name);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 字面量形式声明对象
     */
    private static void literal() {
        String str1 = "Hello";
        String str2 = "World";
        String str3 = "Hello" + "World";
        String str4 = "HelloWorld";
        println("str3 == str4 " + (str3 == str4));//true
        String str5 = str1 + "World";
        str5 = str5.intern();
        println("str5 == str4 " + (str5 == str4));//true
        String str6 = str1 + str2;
        println("str6 == str4 " + (str6 == str4));//false

        String str7 = new String("HelloWorld");
        println("str4 == str7 " + (str7 == str4));//false
    }

    /**
     * intern测试
     */
    private static void intern() {
        String s = new String("1");
//        s.intern();
        String s2 = "1";
        println("s == s2 " + (s == s2));

        String s3 = new String("1") + new String("1");
//        s3.intern();
        String s4 = "11";
        println("s3 == s4 " + (s3 == s4));
    }

    /**
     * 不可变性
     */
    private static void immutable() {
        Map<String, Integer> map = new HashMap<>();
        String str = "Hello";
        System.out.println("put-key-hascode=" + str.hashCode() +" put-key-str=" + str);
        map.put(str, 1);
        str = "World";
        System.out.println("put-key-hascode=" + str.hashCode() +" put-key-str=" + str);
        map.put(str, 2);

        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> next = iterator.next();
            String key = next.getKey();
            System.out.println("key-hascode=" + key.hashCode() +" key-str=" + key + " value="+next.getValue());
        }
    }

    private static void smaple1() {
        String str = "Hello World";
        char[] arr = {'H', 'e', 'l', 'l', 'o'};
        change(str);
        changeArr(arr);
        System.out.println(str);//Hello World
        System.out.println(arr);//Wello
    }

    private static void change(Integer a) {
        a = 2;
    }

//    private static void change(int a) {
//        a = 2;
//    }

    private static void change(String str) {
        str = "World";
    }

    private static void changeArr(char[] arr) {
        arr[0] = 'W';
        arr = new char[5];
    }

    private static void println(String msg) {
        System.out.println(msg);
    }
}
