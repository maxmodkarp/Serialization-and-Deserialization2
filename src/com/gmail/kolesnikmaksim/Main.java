package com.gmail.kolesnikmaksim;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        Container container = new Container();
        Class<?> cls = container.getClass();
        Method[] methods = cls.getMethods();
        for (Method method: methods) {
            if(method.isAnnotationPresent(Saver.class)){
                try {
                    method.invoke(container);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
