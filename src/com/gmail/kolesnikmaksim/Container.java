package com.gmail.kolesnikmaksim;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SaveTo(path="file.txt")
class Container {
    private String text = "Some text";

    public Container() {}

    public Container(String text) {
        this.text = text;
    }

    @Saver
    public void save() {
        Class<?> cls = Container.class;
        if (cls.isAnnotationPresent(SaveTo.class)) {
            SaveTo saveToAnn = cls.getAnnotation(SaveTo.class);
            File file = new File(saveToAnn.path());
            try {
                Method getTextMethod = cls.getDeclaredMethod("getText");
                try(PrintWriter pw = new PrintWriter(file)){
                    pw.println(getTextMethod.invoke(new Container()));
                }
                catch (IOException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
