package com.mcgrady.xproject.java.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ConcurrentModificationException;

/**
 * Created by mcgrady on 2022/9/21.
 */
public class AssignTest {

    public static void main(String[] args) {

        assignBasicType();

        try {
            cloneObject();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    private static void assignBasicType() {
//        Integer a = 1;
//        Integer b = a;
//        a = 2;
        String a = new String("1");
        String b = a;
//        a = "2";
        println("a == b " + (a == b));
    }

    private static void cloneObject() throws CloneNotSupportedException {
        Person person1 = new Person("mmm", "1111111");
        Person person2 = (Person) person1.clone();
//        Person person2 = person1;
        person2.setName("xxx");
        person2.setDesc("22222");
        println("person1: " + person1 + " person2: " + person2);
    }

    public static void println(String msg) {
        System.out.println(msg);
    }

    public static class Person implements Cloneable {

        private static final long serialVersionUID = 369285298572941L;
        private String name;
        private PersonDesc personDesc;

        public Person(String name, String desc) {
            this.name = new String("mcgrady");
            this.personDesc = new PersonDesc(desc);
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDesc(String desc) {
            this.personDesc.setDesc(desc);
        }

//        public Person clone() {
//            Person person = null;
//            try { // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                ObjectOutputStream oos = new ObjectOutputStream(baos);
//                oos.writeObject(this);
//                // 将流序列化成对象
//                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//                ObjectInputStream ois = new ObjectInputStream(bais);
//                person = (Person) ois.readObject();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//            return person;
//        }


        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
//            Person clone = (Person) super.clone();
//            clone.personDesc = (PersonDesc) clone.personDesc.clone();
//            return clone;
        }

        @Override
        public String toString() {
            return "name=" + name + " " + personDesc.toString();
        }
    }

    public static class PersonDesc implements Cloneable {

        public PersonDesc(String desc) {
            this.desc = desc;
        }

        private static final long serialVersionUID = 872390113109L;
        private String desc;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        @Override
        public String toString() {
            return "desc=" + desc;
        }
    }
}
