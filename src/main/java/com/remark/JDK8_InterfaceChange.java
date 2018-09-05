package com.remark;

public interface JDK8_InterfaceChange {

    public void normalMethod();

    public default void defaultMethod(){
        System.out.println("接口的默认方法");
    }

    public default void defaultMethod1(){
        System.out.println("接口的默认方法- 1");
    }

    public static void staticMethod(){
        System.out.println("接口的静态方法");
    }

    public static void staticMethod1(){
        System.out.println("接口的静态方法 - 1");
    }
}
