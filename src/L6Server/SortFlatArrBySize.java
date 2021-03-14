package L6Server;

import CommonClasses.Flat;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class SortFlatArrBySize {
    public Flat[] startSorting (Flat[] flats){


        PriorityQueue bArr = new PriorityQueue<byte[]> (new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                byte[] ba1 = (byte[]) o1;
                byte[] ba2 = (byte[]) o2;
                return Integer.valueOf(ba1.length-ba2.length);
            }
        });

        for(int i =0;i<flats.length;i++){
            bArr.add(serializeObject(flats[i]));
        }

        for(int i = 0;i<flats.length;i++){
            byte[] bytes = (byte[]) bArr.poll();
            flats[i] = (Flat) deSerialize(bytes);
        }

        return flats;
    }

    private Object deSerialize(byte[] objectByteArr){
        ByteArrayInputStream inputStream = new ByteArrayInputStream(objectByteArr);

        ObjectInputStream objectInputStream = null;

        try {
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            System.out.println("Проблема с созданием ObjectInputStream!");
            e.printStackTrace();
        }

        Object object = null;
        try {
            object = objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

     private <T> byte[] serializeObject(T obj){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        } catch (IOException e) {
            System.out.println("Проблема с созданием потока для серилизации объектов!");
        }
        byte[] serObj = null;

        try {
            objectOutputStream.writeObject(obj);
            serObj = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            System.out.println("Проблема с серелизацией объекта для отправки на сервер!");
            e.printStackTrace();
        }

        return serObj;
    }
}
