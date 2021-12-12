package com.gmail.kolesnikmaksim;

public class Main {

    public static void main(String[] args) {
        Container container = new Container("", 0, 0);
        //container.serialization();
        System.out.println(container);
        container.deserialization();
        System.out.println(container);
    }
}
