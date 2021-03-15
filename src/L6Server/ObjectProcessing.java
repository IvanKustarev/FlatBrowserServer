package L6Server;

import java.io.*;

public class ObjectProcessing {
    public static <T> byte[] serializeObject(T obj){
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

    public static Object deSerializeObject(byte[] objectByteArr){
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
}
