package com.gmail.kolesnikmaksim;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

class Container{
    @Save
    private String text;
    @Save
    private int num;
    @Save
    private Inserted inserted;

    private int notSave;

    public Container() {}

    public Container(String text, int num, Inserted inserted, int notSave) {
        this.text = text;
        this.num = num;
        this.inserted = inserted;
        this.notSave = notSave;
    }

    void writeNameOfClass(String path){
        try(PrintWriter pw = new PrintWriter(new FileWriter(path))){
            pw.println("Container {");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeEnd(String path){
        try(PrintWriter pw = new PrintWriter(new FileWriter(path, true))){
            pw.println("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writer(Field f, String path) throws IllegalAccessException {
        try(PrintWriter pw = new PrintWriter(new FileWriter(path, true))){
            pw.println("\"" + f.getName() + "\" = \"" + f.get(this) + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void serialize(String path) throws IllegalAccessException {
        Class<?> cls = Container.class;
        writeNameOfClass(path);
        Field[] fields = cls.getDeclaredFields();
        for (Field f: fields) {
            if(f.isAnnotationPresent(Save.class)){
                writer(f, path);
            }
        }
        writeEnd(path);
    }

    Map<String, Map<String, String>> getData(File file){
        Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
        try(FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr)) {
            String line = reader.readLine();
            String[] str;
            String[] buf;
            String[] buf2;
            Map<String, String> mainMap = new HashMap<String, String>();
            data.put("MainMap", mainMap);
            while (line != null) {
                if(line.split(" = ").length == 1){
                    line = reader.readLine();
                } else if(line.split(" = ").length == 2){
                    line = line.replace("\"", "");
                    str = line.split(" = ");
                    mainMap.put(str[0], str[1]);
                    line = reader.readLine();
                } else {
                    line = line.replace("\"", "");
                    str = line.split(" = ", 2);
                    str[1] = str[1].substring(str[1].indexOf("{") + 1, str[1].indexOf("}"));
                    buf = str[1].split(", ");
                    Map<String, String> bufMap = new HashMap<String, String>();
                    for (int i = 0; i < buf.length; i++) {
                        buf2 = buf[i].split(" = ");
                        bufMap.put(buf2[0], buf2[1]);
                    }
                    data.put(str[0], bufMap);
                    line = reader.readLine();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    void deserialize(String path) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Class<?> cls = Container.class;
        File file = new File(path);
        Map<String, Map<String, String>> data = getData(file);
        Field f;
        for (Map.Entry<String, Map<String, String>> pair: data.entrySet()){
            if (pair.getKey().equals("MainMap")){
                for (Map.Entry<String, String> thisPair: pair.getValue().entrySet()) {
                    f = cls.getDeclaredField(thisPair.getKey());
                    f.setAccessible(true);
                    if (f.getType() == String.class) {
                        f.set(this, thisPair.getValue());
                    } else if (f.getType().equals(int.class)){
                        f.setInt(this, Integer.parseInt(thisPair.getValue()));
                    }
                }
            } else {
                Field fBuf;
                f = cls.getDeclaredField(pair.getKey());
                f.setAccessible(true);
                Class<?> clsBuf = f.getType();
                Constructor<?> constructor = clsBuf.getConstructor();
                Object object = constructor.newInstance();
                for (Map.Entry<String, String> thisPair: pair.getValue().entrySet()) {
                    fBuf = clsBuf.getDeclaredField(thisPair.getKey());
                    fBuf.setAccessible(true);
                    if (fBuf.getType() == String.class) {
                        fBuf.set(object, thisPair.getValue());
                    } else if (fBuf.getType().equals(int.class)){
                        fBuf.setInt(object, Integer.parseInt(thisPair.getValue()));
                    }
                }
                f.set(this, object);
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

    public Inserted getInserted() {
        return inserted;
    }

    public void setInserted(Inserted inserted) {
        this.inserted = inserted;
    }

    @Override
    public String toString() {
        return "Container{" +
                "text='" + text + '\'' +
                ", num=" + num +
                ", inserted=" + inserted +
                ", notSave=" + notSave +
                '}';
    }
}
