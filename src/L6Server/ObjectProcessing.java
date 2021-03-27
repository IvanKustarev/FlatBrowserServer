package L6Server;

import CommonClasses.CommandsData;
import CommonClasses.DataBlock;

import java.io.*;
import java.nio.ByteBuffer;

public class ObjectProcessing {
    public static <T> byte[] serializeObject(T obj){

//        try {
//            BufferedReader bufferedReader = null;
//
//            CommandsData commandsData = (CommandsData) obj;
//            CommandsData commandsData1 = CommandsData.valueOf(commandsData.name());
//
//            commandsData1.setCommandWithElementParameter(commandsData.isCommandWithElementParameter());
//            commandsData1.setCreator(commandsData.getCreator());
//            commandsData1.setParameter(commandsData.getParameter());
//            commandsData1.setFlat(commandsData.getFlat());
//            commandsData1.setOpeningFiles(commandsData.getOpeningFiles());
//            commandsData1.setCommandEnded(commandsData.isCommandEnded());
//            commandsData1.setPhrase(commandsData.getPhrase());
//            commandsData1.setServerNeedStringParameter(commandsData.isServerNeedStringParameter);
//            commandsData1.setServerNeedElementParameter(commandsData.isServerNeedElementParameter);
//            commandsData1.setUserNeedToShowFlatArr(commandsData.isUserNeedToShowFlatArr);
//            commandsData1.setFlats(commandsData.getFlats());
//
//
////        }catch (Exception e){}
//
//        boolean isCommandData = false;
//        try {
//            CommandsData commandsData = (CommandsData) obj;
//            isCommandData = true;
//            System.out.println("ppp");
////            commandsData.setBufferedReader(bufferedReader);
//
//        }catch (Exception e){
//            e.printStackTrace();
//            System.out.println("BAD");
//        }
//
//        byte[] serObj = null;
//        if(isCommandData){
//            CommandsData commandsData = (CommandsData) obj;
//            BufferedReader bufferedReader = commandsData.getBufferedReader();
//            commandsData.setBufferedReader(null);
//            obj = (T)commandsData;
//
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            ObjectOutputStream objectOutputStream = null;
//            try {
//                objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//            } catch (IOException e) {
//                System.out.println("Проблема с созданием потока для серилизации объектов!");
//            }
//            serObj = null;
//
//            try {
//                objectOutputStream.writeObject(obj);
//                serObj = byteArrayOutputStream.toByteArray();
//            } catch (IOException e) {
//                System.out.println("Проблема с серелизацией объекта для отправки на сервер!");
//                e.printStackTrace();
//            }
//            commandsData = (CommandsData) obj;
//            commandsData.setBufferedReader(bufferedReader);
//
//        }
//        else {
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
//        }


//        try {
//            CommandsData commandsData = (CommandsData) obj;
//            commandsData.setBufferedReader(bufferedReader);
//
//        }catch (Exception e){}

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
