package com.mcgrady.xkotlin_demo

import kotlin.reflect.KClass

/**
 * <p>类说明</p>
 * @author mcgrady
 * @date   2019/5/9
 */

var str = "Hello Kotlin"

val strl = "Hi! Kotlin"

fun main() {

    println("$str this is my first kotlin code.")

    Member.sendMessage("10101010010101")

    testClass(TestJava::class.java)
}

fun <T> testClass(clz: Class<T>) {
    println(clz.simpleName)
}

inline fun <reified T: Any> testKClass(kclz: KClass<T>) {
    println(kclz.simpleName)
}

object Member {
    fun sendMessage(msg: String) {
        println("Message: $msg")
    }

    @JvmStatic
    fun sendMsg(msg: String) {
        println(msg)
    }
}

