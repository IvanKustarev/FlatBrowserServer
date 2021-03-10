package L6Server;

import CommonClasses.AbstractDataBlock;
import CommonClasses.DataBlock;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Random;

public class TransferCenter {

//    65535
    private DatagramSocket datagramSocketReceive;//для приёма сообщений от сервера
    private InetSocketAddress socketAddressReceive;

    private InetSocketAddress socketAddressForSend;
    private DatagramChannel datagramChannelForSend;

    public TransferCenter(){
        createSocketAddressForReceive();
        System.out.println("IP-aдрес сервера: " + socketAddressReceive.getAddress().getHostAddress());
        System.out.println("Порт сервера: " + socketAddressReceive.getPort());

        DataBlock dataBlock = (DataBlock)receiveObjectFromUser();
        String[] parametersForSendingProcesses = dataBlock.parameter.split("; ");
        socketAddressForSend = new InetSocketAddress(parametersForSendingProcesses[0], Integer.valueOf(parametersForSendingProcesses[1]));
        try {
            datagramChannelForSend = DatagramChannel.open();
            datagramChannelForSend.bind(null);
        } catch (IOException e) {
            System.out.println("Проблема с созданием канала!");
            e.printStackTrace();
        }
        DataBlock allIsRightData = new DataBlock();
        allIsRightData.allRight = true;

        sendObjectToUser(allIsRightData);
    }

    public <T> void sendObjectToUser(T object){
        byte[] serObject = serializeObject(object);
        DataBlock warningAboutSize = new DataBlock();
        warningAboutSize.parameter = String.valueOf(serObject.length);
        sendByteArr(serializeObject(warningAboutSize));
        //Необходимо получать allRight --- доделать
        sendByteArr(serializeObject(object));
    }

    private void sendByteArr(byte[] bArr){
        ByteBuffer buffer = ByteBuffer.wrap(bArr);
        try {
            datagramChannelForSend.send(buffer, socketAddressForSend);
            buffer.clear();
        } catch (IOException e) {
            System.out.println("Проблема с отправкой через канал!");
            e.printStackTrace();
        }
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

    private int createSocketAddressForReceive(){
        Random random = new Random();

        int port = -1;

        boolean workingSocket = false;
        while (!workingSocket) {
            port = random.nextInt(65535);

            try {
                socketAddressReceive = new InetSocketAddress(InetAddress.getLocalHost(), port);
                datagramSocketReceive = new DatagramSocket(port);
                workingSocket = true;
            } catch (/*UnknownHostException | */SocketException | UnknownHostException e) {
                System.out.println("Проблема с созданием порта!");
                e.printStackTrace();
            }

        }
        return port;
    }

//    public static InetSocketAddress getServerSocketAddress() {
//        return serverSocketAddress;
//    }
//
//    /**Считывает запрос и возвращает CommandsData объект*/
//    public CommandsData checkingRequests(){
//        byte[] bArr = new byte[76];
//        DatagramPacket datagramPacket = new DatagramPacket(bArr, bArr.length);
//        try {
//            datagramSocket.receive(datagramPacket);
//        } catch (IOException e) {
//            System.out.println("Проблема с получением файла!");
//        }
//        InputStream inputStream = new ByteArrayInputStream(bArr);
//
//        ObjectInputStream objectInputStream = null;
//        try {
//            objectInputStream = new ObjectInputStream(inputStream);
//        } catch (IOException e) {
//            System.out.println("Проблема с созданием ObjectInputStream!");
//        }
//
//
//        for(byte b : bArr){
//            System.out.println(b);
//        }
//
//
//        CommandsData commandsData = null;
//        try{
//            commandsData = (CommandsData) objectInputStream.readObject();
//        }catch (Exception e){
//            System.out.println("Проблема с десерелизацией объекта!");
//        }
//        return commandsData;
//    }
//
//    /**Считывает запрос и возвращает CommandsData объект*/
//    public CommandsData checkingRequests(){
//        byte[] bArr = new byte[200];
//        DatagramPacket datagramPacket = new DatagramPacket(bArr, bArr.length);
//        try {
//            datagramSocket.receive(datagramPacket);
//        } catch (IOException e) {
//            System.out.println("Проблема с получением файла!");
//        }
//        InputStream inputStream = new ByteArrayInputStream(bArr);
//
//        ObjectInputStream objectInputStream = null;
//        try {
//            objectInputStream = new ObjectInputStream(inputStream);
//        } catch (IOException e) {
//            System.out.println("Проблема с созданием ObjectInputStream!");
//        }
//
//        CommonClasses.CommandsData commandsData = null;
//
//        try {
//            commandsData = (CommonClasses.CommandsData) objectInputStream.readObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
////        System.out.println(commandsData.name());
////        commandsData.show();
//        return commandsData;
//    }
//
//    public void sendAnswerToUser(AbstractDataBlock answerToUser){
//        try {
//            byte[] bArr = new byte[1000];
//            DatagramChannel datagramChannel = DatagramChannel.open();
//            SocketAddress socketAddress = new InetSocketAddress("192.168.1.135", 6667);
//            datagramChannel.connect(socketAddress);
//            ByteBuffer byteBuffer = ByteBuffer.wrap(bArr);
//            byteBuffer.flip();
//            datagramChannel.send(byteBuffer, socketAddress);
//            System.out.println("Send == true");
//        } catch (IOException e) {
//            System.out.println("Проблема с каналом для обмена!");
//        }
//    }

    /**считывает object в массив неизвестной длины*/
    public Object receiveObjectFromUser(){

        return receiveObjectFromUser(receiveObjectArrSize());
//        return receiveObjectFromUser(receiveObjectArrSize());






//        byte[] objectByteArr = new byte[receiveObjectArrSize()];

//        DatagramPacket datagramPacket = new DatagramPacket(objectByteArr, objectByteArr.length);


//        byte[] objectByteArr = new byte[200];
//        DatagramPacket datagramPacket = new DatagramPacket(objectByteArr, objectByteArr.length);
//        try {
//            datagramSocket.receive(datagramPacket);
//        } catch (IOException e) {
//            System.out.println("Проблема с получением файла!");
//        }
//        InputStream inputStream = new ByteArrayInputStream(objectByteArr);
//
//        ObjectInputStream objectInputStream = null;
//        try {
//            objectInputStream = new ObjectInputStream(inputStream);
//        } catch (IOException e) {
//            System.out.println("Проблема с созданием ObjectInputStream!");
//        }
//
//        CommonClasses.CommandsData commandsData = null;
//
//        try {
//            commandsData = (CommonClasses.CommandsData) objectInputStream.readObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
////        System.out.println(commandsData.name());
////        commandsData.show();
//        return commandsData;
    }

    /**считывает object в массив уже известной длины*/
    private Object receiveObjectFromUser(int objectArrSize){

        byte[] objectByteArr = new byte[objectArrSize];
        DatagramPacket datagramPacket = new DatagramPacket(objectByteArr, objectByteArr.length);

        try {
            datagramSocketReceive.receive(datagramPacket);
        } catch (IOException e) {
            System.out.println("Проблема с получением файла!");
            e.printStackTrace();
        }

        return deSerialize(objectByteArr);
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

    /**ждёт от юзера объект, в параметре которого будет размер следующего объекта и возвращает этот размер*/
    private int receiveObjectArrSize(){
        AbstractDataBlock objectWithSizeParameter = (AbstractDataBlock) receiveObjectFromUser(500);
        return Integer.valueOf(objectWithSizeParameter.parameter);
    }

}
