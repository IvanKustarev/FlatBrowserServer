package L6Server;



import CommonClasses.CommandsData;
import CommonClasses.DataBlock;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class TransferCenter {

//    65535

    Selector selector;
    DatagramChannel mainServerDatagramChannel;
    WorkWithUser workWithUser;

    public TransferCenter(WorkWithUser workWithUser){
        this.workWithUser = workWithUser;
//        this.workWithUser.setTransferCenter(this);

        createNewChannelWithoutIP();
        writeInformationAboutServer();
        try {
            selector = Selector.open();
        } catch (IOException e) {
            System.out.println("Проблемы с созданием селектора!");
            e.printStackTrace();
        }
        //Создание чеккера, при обращении к IP которого будет устанавливаться соединение с USER-ом
        ConnectionRequestsChecker connectionRequestsChecker = new ConnectionRequestsChecker(selector, mainServerDatagramChannel);
        connectionRequestsChecker.start();
    }

    public static DatagramChannel createNewChannelWithoutIP() {
        Random random = new Random();
        DatagramChannel datagramChannel = null;
        try {
            datagramChannel = DatagramChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int port = -1;

        boolean workingPort = false;
        while (!workingPort) {
            port = random.nextInt(65535);

            try {
                datagramChannel.bind(new InetSocketAddress(port));
                workingPort = true;
            } catch (IOException e) { }
        }
        return datagramChannel;
    }

    public static DatagramChannel createNewChannelWithIP() {
        Random random = new Random();
        DatagramChannel datagramChannel = null;
        try {
            datagramChannel = DatagramChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int port = -1;

        boolean workingPort = false;
        while (!workingPort) {
            port = random.nextInt(65535);

            try {
                datagramChannel.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), port));
                workingPort = true;
            } catch (IOException e) { }
        }
        return datagramChannel;
    }

    public void writeInformationAboutServer() {
        System.out.println("IP сервера: " + mainServerDatagramChannel.socket().getLocalAddress().getHostAddress()+ "\nPort сервера: " + mainServerDatagramChannel.socket().getLocalPort() + "\n");
    }

    /**Processing requests from different users and started work with them*/
    public void requestsProcessing(){
        while (true){
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator iterator = selectionKeys.iterator();

            while (iterator.hasNext()){
                SelectionKey selectionKey = (SelectionKey) iterator.next();
                iterator.remove();

                Object obj = null;
                DatagramChannel selectedDatagramChannel = null;
                try {
//                    selectionKey.channel()
                    selectedDatagramChannel = ((DatagramChannel)selectionKey.channel());/*.receive(ByteBuffer.wrap(new byte[1]));*/
                    ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[1]);
                    selectedDatagramChannel.receive(byteBuffer);
                    obj = ObjectProcessing.serializeObject(byteBuffer.array());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                CommandsData commandsData = null;
                try {
                    commandsData = (CommandsData) obj;
                    workWithUser.startWorkWithUser(selectedDatagramChannel, commandsData);
                }catch (Exception e){}
            }
        }
    }












//
//    public void writeInformation(){
//        System.out.println("IP-aдрес сервера: " + socketAddressReceive.getAddress().getHostAddress());
//        System.out.println("Порт сервера: " + socketAddressReceive.getPort());
//    }
//
//    public void createConnectionForSending(DataBlock dataBlock){
//        String[] parametersForSendingProcesses = dataBlock.parameter.split("; ");
//        socketAddressForSend = new InetSocketAddress(parametersForSendingProcesses[0], Integer.valueOf(parametersForSendingProcesses[1]));
//        try {
//            datagramChannelForSend = DatagramChannel.open();
//            datagramChannelForSend.bind(null);
//        } catch (IOException e) {
//            System.out.println("Проблема с созданием канала!");
//            e.printStackTrace();
//        }
//        DataBlock allIsRightData = new DataBlock();
//        allIsRightData.allRight = true;
//
//        sendObjectToUser(allIsRightData);
//    }
//
//    public <T> void sendObjectToUser(T object){
//        byte[] serObject = serializeObject(object);
//        DataBlock warningAboutSize = new DataBlock();
//        warningAboutSize.parameter = String.valueOf(serObject.length);
//        sendByteArr(serializeObject(warningAboutSize));
//        //Необходимо получать allRight --- доделать
//        sendByteArr(serializeObject(object));
//    }
//
//    private void sendByteArr(byte[] bArr){
//        ByteBuffer buffer = ByteBuffer.wrap(bArr);
//        try {
//            datagramChannelForSend.send(buffer, socketAddressForSend);
//            buffer.clear();
//        } catch (IOException e) {
//            System.out.println("Проблема с отправкой через канал!");
//            e.printStackTrace();
//        }
//    }
//
//    public static <T> byte[] serializeObject(T obj){
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ObjectOutputStream objectOutputStream = null;
//        try {
//            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//        } catch (IOException e) {
//            System.out.println("Проблема с созданием потока для серилизации объектов!");
//        }
//        byte[] serObj = null;
//
//        try {
//            objectOutputStream.writeObject(obj);
//            serObj = byteArrayOutputStream.toByteArray();
//        } catch (IOException e) {
//            System.out.println("Проблема с серелизацией объекта для отправки на сервер!");
//            e.printStackTrace();
//        }
//
//        return serObj;
//    }
//
//    private int createSocketAddressForReceive(){
//        Random random = new Random();
//
//        int port = -1;
//
//        boolean workingSocket = false;
//        while (!workingSocket) {
//            port = random.nextInt(65535);
//
//            try {
//                socketAddressReceive = new InetSocketAddress(InetAddress.getLocalHost(), port);
//                datagramSocketReceive = new DatagramSocket(port);
//                workingSocket = true;
//            } catch (/*UnknownHostException | */SocketException | UnknownHostException e) {
//                System.out.println("Проблема с созданием порта!");
//                e.printStackTrace();
//            }
//
//        }
//        return port;
//    }
//
////    public static InetSocketAddress getServerSocketAddress() {
////        return serverSocketAddress;
////    }
////
////    /**Считывает запрос и возвращает CommandsData объект*/
////    public CommandsData checkingRequests(){
////        byte[] bArr = new byte[76];
////        DatagramPacket datagramPacket = new DatagramPacket(bArr, bArr.length);
////        try {
////            datagramSocket.receive(datagramPacket);
////        } catch (IOException e) {
////            System.out.println("Проблема с получением файла!");
////        }
////        InputStream inputStream = new ByteArrayInputStream(bArr);
////
////        ObjectInputStream objectInputStream = null;
////        try {
////            objectInputStream = new ObjectInputStream(inputStream);
////        } catch (IOException e) {
////            System.out.println("Проблема с созданием ObjectInputStream!");
////        }
////
////
////        for(byte b : bArr){
////            System.out.println(b);
////        }
////
////
////        CommandsData commandsData = null;
////        try{
////            commandsData = (CommandsData) objectInputStream.readObject();
////        }catch (Exception e){
////            System.out.println("Проблема с десерелизацией объекта!");
////        }
////        return commandsData;
////    }
////
////    /**Считывает запрос и возвращает CommandsData объект*/
////    public CommandsData checkingRequests(){
////        byte[] bArr = new byte[200];
////        DatagramPacket datagramPacket = new DatagramPacket(bArr, bArr.length);
////        try {
////            datagramSocket.receive(datagramPacket);
////        } catch (IOException e) {
////            System.out.println("Проблема с получением файла!");
////        }
////        InputStream inputStream = new ByteArrayInputStream(bArr);
////
////        ObjectInputStream objectInputStream = null;
////        try {
////            objectInputStream = new ObjectInputStream(inputStream);
////        } catch (IOException e) {
////            System.out.println("Проблема с созданием ObjectInputStream!");
////        }
////
////        CommonClasses.CommandsData commandsData = null;
////
////        try {
////            commandsData = (CommonClasses.CommandsData) objectInputStream.readObject();
////        } catch (IOException e) {
////            e.printStackTrace();
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        }
//////        System.out.println(commandsData.name());
//////        commandsData.show();
////        return commandsData;
////    }
////
////    public void sendAnswerToUser(AbstractDataBlock answerToUser){
////        try {
////            byte[] bArr = new byte[1000];
////            DatagramChannel datagramChannel = DatagramChannel.open();
////            SocketAddress socketAddress = new InetSocketAddress("192.168.1.135", 6667);
////            datagramChannel.connect(socketAddress);
////            ByteBuffer byteBuffer = ByteBuffer.wrap(bArr);
////            byteBuffer.flip();
////            datagramChannel.send(byteBuffer, socketAddress);
////            System.out.println("Send == true");
////        } catch (IOException e) {
////            System.out.println("Проблема с каналом для обмена!");
////        }
////    }
//
//    /**считывает object в массив неизвестной длины*/
//    public Object receiveObjectFromUser(){
//
////        CommandsData commandsData = (CommandsData) receiveObjectFromUser(receiveObjectArrSize());
////        commandsData.getFlat().show();
////        return commandsData;
//
//
//        Object object = receiveObjectFromUser(receiveObjectArrSize());
//        try {
//            DataBlock dataBlock = (DataBlock) object;
//            if(dataBlock.getParameter().equals("checkingConnect") & dataBlock.isAllRight()){
//                sendObjectToUser(dataBlock);
//                object = receiveObjectFromUser();
//            }
//        }catch (Exception e){
//        }
//
//        return object;
//
//
//
//
//
//
////        byte[] objectByteArr = new byte[receiveObjectArrSize()];
//
////        DatagramPacket datagramPacket = new DatagramPacket(objectByteArr, objectByteArr.length);
//
//
////        byte[] objectByteArr = new byte[200];
////        DatagramPacket datagramPacket = new DatagramPacket(objectByteArr, objectByteArr.length);
////        try {
////            datagramSocket.receive(datagramPacket);
////        } catch (IOException e) {
////            System.out.println("Проблема с получением файла!");
////        }
////        InputStream inputStream = new ByteArrayInputStream(objectByteArr);
////
////        ObjectInputStream objectInputStream = null;
////        try {
////            objectInputStream = new ObjectInputStream(inputStream);
////        } catch (IOException e) {
////            System.out.println("Проблема с созданием ObjectInputStream!");
////        }
////
////        CommonClasses.CommandsData commandsData = null;
////
////        try {
////            commandsData = (CommonClasses.CommandsData) objectInputStream.readObject();
////        } catch (IOException e) {
////            e.printStackTrace();
////        } catch (ClassNotFoundException e) {
////            e.printStackTrace();
////        }
//////        System.out.println(commandsData.name());
//////        commandsData.show();
////        return commandsData;
//    }
//
//    /**считывает object в массив уже известной длины*/
//    private Object receiveObjectFromUser(int objectArrSize){
//
//        byte[] objectByteArr = new byte[objectArrSize];
//        DatagramPacket datagramPacket = new DatagramPacket(objectByteArr, objectByteArr.length);
//
//        try {
//            datagramSocketReceive.receive(datagramPacket);
//        } catch (IOException e) {
//            System.out.println("Проблема с получением файла!");
//            e.printStackTrace();
//        }
//        return deSerialize(objectByteArr);
//    }
//
//    public static Object deSerializeObject(byte[] objectByteArr){
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(objectByteArr);
//
//        ObjectInputStream objectInputStream = null;
//
//        try {
//            objectInputStream = new ObjectInputStream(inputStream);
//        } catch (IOException e) {
//            System.out.println("Проблема с созданием ObjectInputStream!");
//            e.printStackTrace();
//        }
//
//        Object object = null;
//        try {
//            object = objectInputStream.readObject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return object;
//    }
//
//    /**ждёт от юзера объект, в параметре которого будет размер следующего объекта и возвращает этот размер*/
//    private int receiveObjectArrSize(){
//        AbstractDataBlock objectWithSizeParameter = (AbstractDataBlock) receiveObjectFromUser(500);
//        return Integer.valueOf(objectWithSizeParameter.parameter);
//    }

}
