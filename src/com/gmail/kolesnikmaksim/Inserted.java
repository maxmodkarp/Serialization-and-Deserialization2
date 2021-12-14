package com.gmail.kolesnikmaksim;

import java.lang.reflect.Field;

public class Inserted {
    @Save
    private String txt;
    @Save
    private int num;

    private int numNotSave;

    public Inserted() {
    }

    public Inserted(String txt, int num, int numNotSave) {
        this.txt = txt;
        this.num = num;
        this.numNotSave = numNotSave;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNumNotSave() {
        return numNotSave;
    }

    public void setNumNotSave(int numNotSave) {
        this.numNotSave = numNotSave;
    }

    @Override
    public String toString() {
        Class<?> cls = Inserted.class;
        Field[] fields = cls.getDeclaredFields();
        String str = "Inserted{";
        for (Field f: fields){
            if (f.isAnnotationPresent(Save.class)){
                try {
                    str += f.getName() + " = " + f.get(this) + ", ";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        str += "}";
        str = str.replace(", }", "}");
        return str;
    }
}
