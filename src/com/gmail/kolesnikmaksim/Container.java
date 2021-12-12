package com.gmail.kolesnikmaksim;

import java.io.*;
import java.lang.reflect.Field;

@SaveTo(path="file.txt")
class Container {

    @Save
    private String text;
    @Save
    private int num;

    private int notSave;

    public Container() {}

    public Container(String text, int num, int notSave) {
        this.text = text;
        this.num = num;
        this.notSave = notSave;
    }

    public void serialization() {
        Class<?> cls = Container.class;
        if (cls.isAnnotationPresent(SaveTo.class)) {
            SaveTo saveToAnn = cls.getAnnotation(SaveTo.class);
            File fileDelete = new File(saveToAnn.path());
            fileDelete.delete();
            File file = new File(saveToAnn.path());
            Field[] fields = cls.getDeclaredFields();
            for (Field f: fields) {
                if (f.isAnnotationPresent(Save.class)){
                    try(PrintWriter pw = new PrintWriter(new FileWriter(file, true))){
                        f.setAccessible(true);
                        pw.println("\"" + f.getName() + "\": " + "\"" + f.get(this) + "\"");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void deserialization() {
        Class<?> cls = Container.class;
        String[] fieldNameAndValue;
        StringBuilder sb;
        if (cls.isAnnotationPresent(SaveTo.class)) {
            SaveTo saveToAnn = cls.getAnnotation(SaveTo.class);
            File file = new File(saveToAnn.path());
            Field f;
            try(FileReader fr = new FileReader(file);
                BufferedReader reader = new BufferedReader(fr)) {
                String line = reader.readLine();
                while (line != null) {
                    sb = new StringBuilder(line);
                    sb.deleteCharAt(0);
                    sb.deleteCharAt(line.length() - 2);
                    line = sb.toString();
                    fieldNameAndValue = line.split("\": \"");
                    f = cls.getDeclaredField(fieldNameAndValue[0]);
                    f.setAccessible(true);
                    if (f.getType() == String.class) {
                        f.set(this, fieldNameAndValue[1]);
                    } else if (f.getType().equals(int.class)){
                        f.setInt(this, Integer.parseInt(fieldNameAndValue[1]));
                    }
                    line = reader.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNotSave() {
        return notSave;
    }

    public void setNotSave(int notSave) {
        this.notSave = notSave;
    }

    @Override
    public String toString() {
        return "Container{" +
                "text='" + text + '\'' +
                ", num=" + num +
                ", notSave=" + notSave +
                '}';
    }
}
