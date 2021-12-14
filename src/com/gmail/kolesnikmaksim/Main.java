package com.gmail.kolesnikmaksim;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {
        Inserted ins = new Inserted("Some text", 123, 456);
        Container container1 = new Container("Second some text", 789, ins, 5);
        Container container2 = new Container();
        try {
            container1.serialize("file.txt");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            container2.deserialize("file.txt");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        System.out.println(container2);
    }
}
