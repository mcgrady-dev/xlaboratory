package com.mcgrady.xkotlin_demo;

/**
 * <p>类说明</p>
 *
 * @author mcgrady
 * @date 2019/5/9
 */
public class TestJava {

    public static void main(String[] args) {
        TestKt.main();

        Member.INSTANCE.sendMessage("10101010010101");

        System.out.println(TestKt.class.getSimpleName());
    }
}
