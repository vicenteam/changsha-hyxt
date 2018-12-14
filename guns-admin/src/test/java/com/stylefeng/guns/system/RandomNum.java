package com.stylefeng.guns.system;

import java.util.*;

public class RandomNum {
    private static Integer maxNum = 2000;
    private static Integer leNum = 0;
    static List<Integer> list = new ArrayList<>();

    static {
        for (int i = 0; i < 2000; i++) {
            list.add(i);
        }
    }

    static int a = 0, b = 0, c = 0, d = 0;
    static Map<String, Object> map = new HashMap<>();
    public static void main(String[] args) {

//        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < 3000; i++) {
//            int random = random();
//            Integer integer = list.get(random);
//            map.put(integer + "", integer);
//            if (integer >= 1990 && integer < 2000) {
//                a++;
//            } else if (integer >= 1890 && integer < 1990) {
//                b++;
//            }else if(integer >= 1650&& integer < 1890){
//                c++;
//            }else {
//                d++;
//            }
            new Runnable() {
                @Override
                public void run() {
                    init();
                }
            }.run();
//            list.remove(random);

        }
        System.out.println("MapSize=" + map.size());
        System.out.println("a-"+a);
        System.out.println("b-"+b);
        System.out.println("c-"+c);
        System.out.println("d-"+d);

    }

    public static int random() {
        return ((int) (Math.random() * (list.size())));
    }

    public synchronized static void init(){
        if(list.size()>0){
            int random = random();
            Integer integer = list.get(random);
            map.put(integer + "", integer);
            if (integer >= 1990 && integer < 2000) {
                a++;
            } else if (integer >= 1890 && integer < 1990) {
                b++;
            }else if(integer >= 1650&& integer < 1890){
                c++;
            }else {
                d++;
            }
            list.remove(random);
        }
    }
}
