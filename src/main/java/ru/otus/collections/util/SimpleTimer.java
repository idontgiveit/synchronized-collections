package ru.otus.collections.util;

public class SimpleTimer {
    static long time;

    public static void start() {
        time = System.currentTimeMillis();
    }

    public static void print() {
        time = System.currentTimeMillis() - time;
        System.out.println("Time :" + time);
    }
}
