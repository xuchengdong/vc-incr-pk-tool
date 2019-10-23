package com.df.core;

public class Test {
    public static void main(String[] args) {
        String path = Test.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(path);
    }
}
