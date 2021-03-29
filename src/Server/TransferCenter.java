package Server;



import CommonClasses.CommandsData;
import CommonClasses.DataBlock;
import Server.OptionalThrows.ConnectionRequestsChecker;
//import CommonClasses.DataBlock;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.*;

public class TransferCenter {

//    65535

    Selector selector;
    DatagramChannel mainServerDatagramChannel;
    WorkWithUser workWithUser;

    private final static int SIZEOFBUFFER = 500;

    public TransferCenter(WorkWithUser workWithUser){
        this.workWithUser = workWithUser;
//        this.workWithUser.setTransferCenter(this);
        System.out.println("Введите 0, если хотите автоматически создать сервер или 1, если хотите привязать его к определённому порту:");
        if(Integer.valueOf(new Scanner(System.in).nextLine()).equals(1)){
            System.out.println("Ведите порт:");
            mainServerDatagramChannel = createNewChannelWithIP(Integer.valueOf(new Scanner(System.in).nextLine()));
        }else {
            mainServerDatagramChannel = createNewChannelWithIP();
        }
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

//    public static DatagramChannel createNewChannelWithoutIP() {
//        Random random = new Random();
//        DatagramChannel datagramChannel = null;
//        try {
//            datagramChannel = DatagramChannel.open();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        int port = -1;
//
//        boolean workingPort = false;
//        while (!workingPort) {
//            port = random.nextInt(65535);
//
//            try {
//                datagramChannel.bind(new InetSocketAddress(port));
//                workingPort = true;
//            } catch (IOException e) { }
//        }
//        return datagramChannel;
//    }

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

    public static DatagramChannel createNewChannelWithIP(int port) {
//        Random random = new Random();
        DatagramChannel datagramChannel = null;
        try {
            datagramChannel = DatagramChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }


            try {
                datagramChannel.bind(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), port));
            } catch (IOException e) {
                System.out.println("Некорректный или занятый port введите другой:");
                datagramChannel = createNewChannelWithIP((new Scanner(System.in)).nextInt());
            }
        return datagramChannel;
    }

    public void writeInformationAboutServer() {
        System.out.println("IP сервера: " + mainServerDatagramChannel.socket().getLocalAddress().getHostAddress()+ "\nPort сервера: " + mainServerDatagramChannel.socket().getLocalPort() + "\n");
    }

    /**Processing requests from different users and started work with them*/
    public void requestsProcessing(){
        while (true){
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            try {
//                System.out.println("kkk " + selector.selectNow());
//                System.out.println("kkk " + selector.selectNow());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            try {
                if(selector.selectNow() == 0){
//                    System.out.println("0");
                    continue;
                }
//                else {
////                    System.out.println("1");
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator iterator = selectionKeys.iterator();

            while (iterator.hasNext()){
                SelectionKey selectionKey = (SelectionKey) iterator.next();
                iterator.remove();

                Object obj = null;
                DatagramChannel selectedDatagramChannel = null;
                try {
                    selectedDatagramChannel = DatagramChannel.open();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
//                    selectedDatagramChannel = ((DatagramChannel)selectionKey.channel());
//                    ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[10000]);
//                    selectedDatagramChannel.receive(byteBuffer);
//                    obj = ObjectProcessing.deSerializeObject(byteBuffer.array());
//                    System.out.println(obj.getClass().getName());

                    DatagramChannel datagramChannel = (DatagramChannel) selectionKey.channel();
                    obj = receiveObject(datagramChannel);

                    selectedDatagramChannel.connect(((DatagramChannel) selectionKey.channel()).getRemoteAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                CommandsData commandsData = null;
                DataBlock dataBlock = (DataBlock) obj;
                commandsData = dataBlock.getCommandsData();
                TransferCenter.copyFieldsFromTo(dataBlock, commandsData);

                workWithUser.startWorkWithUser(selectedDatagramChannel, commandsData);
            }
        }
    }

    public static Object receiveObject(DatagramChannel datagramChannel) throws IOException {


        Object obj = null;
        boolean endOfReceive = false;
        byte[] objByteArr = new byte[0];
        byte[] receivedArr;

        while (!endOfReceive){
            receivedArr = receiveByteArr(datagramChannel);
            byte[] newArr = new byte[objByteArr.length + receivedArr.length];

            for(int i =0;i<(objByteArr.length + receivedArr.length);i++){
                if(i<objByteArr.length){
                    newArr[i] = objByteArr[i];
                }
                else {
                    newArr[i] = receivedArr[i-objByteArr.length];
                }
            }
            objByteArr = newArr;


            try {
                obj = ObjectProcessing.deSerializeObject(objByteArr);
                endOfReceive = true;

            }catch (Exception e){}
        }


//        boolean endOfReceive = false;
//        byte[] byteObj = new byte[0];
//        Object obj = null;
//        while (!endOfReceive){
//            byte[] receivedArr = receiveByteArr((DatagramChannel) selectionKey.channel());
//            byte[] bytes = new byte[byteObj.length + receivedArr.length];
//
//            for(int i =0;i<byteObj.length + receivedArr.length;i++){
//                if(i<byteObj.length){
//                    bytes[i] = byteObj[i];
//                }
//                else {
//                    bytes[i] = receivedArr[i-byteObj.length];
//                }
//            }
//            byteObj = bytes;
//            boolean hasNewPortion = false;
//            if(selector.selectNow() != 0){
//                Set set = selector.selectedKeys();
//                Iterator iterator = set.iterator();
//                while (iterator.hasNext()){
//                    if(iterator.next().equals(selectionKey)){
//                        hasNewPortion = true;
//                    }
//                }
//            }
//            if(!hasNewPortion){
//                obj = ObjectProcessing.deSerializeObject(byteObj);
//                endOfReceive = true;
//            }
//            if(obj == null){
//                hasNewPortion = true;
//            }
//        }
        return obj;
    }

    private static byte[] receiveByteArr(DatagramChannel datagramChannel) {
        final int size = SIZEOFBUFFER;
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[size]);
        byte[] bytes = null;
        try {
            datagramChannel.receive(byteBuffer);
            bytes = byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    private static void copyFieldsFromTo(CommandsData commandsData, DataBlock dataBlock){
        dataBlock.setCommandWithElementParameter(commandsData.isCommandWithElementParameter());
        dataBlock.setCreator(commandsData.getCreator());
        dataBlock.setParameter(commandsData.getParameter());
        dataBlock.setFlat(commandsData.getFlat());
        dataBlock.setBufferedReader(commandsData.getBufferedReader());
        dataBlock.setOpeningFiles(commandsData.getOpeningFiles());
        dataBlock.setCommandEnded(commandsData.isCommandEnded());
        dataBlock.setPhrase(commandsData.getPhrase());
        dataBlock.setServerNeedStringParameter(commandsData.isServerNeedStringParameter);
        dataBlock.setServerNeedElementParameter(commandsData.isServerNeedElementParameter);
        dataBlock.setUserNeedToShowFlatArr(commandsData.isUserNeedToShowFlatArr);
        dataBlock.setFlats(commandsData.getFlats());

    }

    private static void copyFieldsFromTo(DataBlock dataBlock, CommandsData commandsData){
        commandsData.setCommandWithElementParameter(dataBlock.isCommandWithElementParameter());
        commandsData.setCreator(dataBlock.getCreator());
        commandsData.setParameter(dataBlock.getParameter());
        commandsData.setFlat(dataBlock.getFlat());
        commandsData.setBufferedReader(dataBlock.getBufferedReader());
        commandsData.setOpeningFiles(dataBlock.getOpeningFiles());
        commandsData.setCommandEnded(dataBlock.isCommandEnded());
        commandsData.setPhrase(dataBlock.getPhrase());
        commandsData.setServerNeedStringParameter(dataBlock.isServerNeedStringParameter);
        commandsData.setServerNeedElementParameter(dataBlock.isServerNeedElementParameter);
        commandsData.setUserNeedToShowFlatArr(dataBlock.isUserNeedToShowFlatArr);
        commandsData.setFlats(dataBlock.getFlats());

    }

    public  static void sendAnswerToUser(DatagramChannel datagramChannel, CommandsData commandsData){
        DataBlock dataBlock = new DataBlock();
        BufferedReader bufferedReader = commandsData.getBufferedReader();
        commandsData.setBufferedReader(null);
        copyFieldsFromTo(commandsData, dataBlock);
        dataBlock.setCommandsData(commandsData);
        try {
            sendObject(datagramChannel, dataBlock);
        } catch (IOException e) {
            e.printStackTrace();
        }
//            ByteBuffer byteBuffer = ByteBuffer.wrap(ObjectProcessing.serializeObject(dataBlock));
//            datagramChannel.send(byteBuffer, datagramChannel.socket().getRemoteSocketAddress());
        commandsData.setBufferedReader(bufferedReader);

    }

    public static void sendObject(DatagramChannel datagramChannel, Object object) throws IOException {

            sendByteArr(ObjectProcessing.serializeObject(object), datagramChannel);

    }

    private static void sendByteArr(byte[] bArr, DatagramChannel datagramChannel){
//        System.out.println(bArr.length);
//        if(bArr.length>500){
//            System.out.println(bArr[602]);
//        }
        final int size = SIZEOFBUFFER;
        byte[] bigArr = new byte[bArr.length + size - (bArr.length % size)];
        for (int i =0; i < bArr.length; i++){
            bigArr[i] = bArr[i];
        }
        bArr = bigArr;


        for(int i = 0; i < Math.ceil(Float.valueOf(bArr.length)/size); i++){
            byte[] data = new byte[size];
            for (int j = 0; j<(size);j++){
                data[j] = bArr[j+(size)*i];
            }

//            DatagramPacket datagramPacket = new DatagramPacket(data, size, datagramChannel.getRemoteAddress());
            ByteBuffer byteBuffer = ByteBuffer.wrap(data);
            try {
                datagramChannel.send(byteBuffer, datagramChannel.getRemoteAddress());
//                datagramSocket.send(datagramPacket);
            } catch (IOException e) {
                System.out.println("Проблема с отправкой объекта!");
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
