package Server;

import CommonClasses.CommandsData;
import CommonClasses.DataBlock;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ReadRequestThread implements Runnable{

    DatagramChannel datagramChannel = null;
    ConcurrentLinkedQueue requestsWaitingProcessing = null;
    final int sizeOfBuffer;
    SelectionKey selectionKey = null;
    Stack openedSelectionKeys = null;

    public ReadRequestThread(
            SelectionKey selectionKey, ConcurrentLinkedQueue requestsWaitingProcessing, int sizeOfBuffer, Stack openedSelectionKeys){
        this.selectionKey = selectionKey;
        this.datagramChannel = (DatagramChannel) selectionKey.channel();
        this.requestsWaitingProcessing = requestsWaitingProcessing;
        this.sizeOfBuffer = sizeOfBuffer;
        this.openedSelectionKeys = openedSelectionKeys;
    }

    @Override
    public void run() {
        try {
            DataBlock dataBlock = (DataBlock) TransferCenter.receiveObject(datagramChannel);
//            DatagramChannel channelForAnswer = DatagramChannel.open();
//            channelForAnswer.connect(datagramChannel.getRemoteAddress());
//            datagramChannel.connect(datagramChannel.getRemoteAddress());
            TransferCenter.copyFieldsFromTo(dataBlock, dataBlock.getCommandsData());
            DataPacket dataPacket = new DataPacket(datagramChannel, dataBlock.getCommandsData());
            requestsWaitingProcessing.add(dataPacket);

//            requestsWaitingProcessing.add(receiveObject(datagramChannel, requestsWaitingProcessing, sizeOfBuffer));
//            requestsWaitingProcessing.add(TransferCenter.receiveObject(datagramChannel));
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Ключ удаляет себя из стека уже запущенных в обработку ключей
        Iterator iterator = openedSelectionKeys.iterator();
        while (iterator.hasNext()){
            if(iterator.next().equals(selectionKey)){
                iterator.remove();
            }
        }
    }

//    private Object receiveObject(DatagramChannel datagramChannel, ConcurrentLinkedQueue synchronousQueue, int sizeOfBuffer) throws IOException {
//
//
//        Object obj = null;
//        boolean endOfReceive = false;
//        byte[] objByteArr = new byte[0];
//        byte[] receivedArr;
//
//        while (!endOfReceive){
////            receivedArr = receiveByteArr(datagramChannel, sizeOfBuffer);
//            receivedArr = TransferCenter.receiveByteArr(datagramChannel);
//            byte[] newArr = new byte[objByteArr.length + receivedArr.length];
//
//            for(int i =0;i<(objByteArr.length + receivedArr.length);i++){
//                if(i<objByteArr.length){
//                    newArr[i] = objByteArr[i];
//                }
//                else {
//                    newArr[i] = receivedArr[i-objByteArr.length];
//                }
//            }
//            objByteArr = newArr;
//
//
//            try {
//                obj = ObjectProcessing.deSerializeObject(objByteArr);
//                endOfReceive = true;
//
//            }catch (Exception e){}
//        }
//
//        CommandsData commandsData = null;
//        DataBlock dataBlock = (DataBlock) obj;
//        commandsData = dataBlock.getCommandsData();
//        TransferCenter.copyFieldsFromTo(dataBlock, commandsData);
//
//        DatagramChannel selectedDatagramChannel = DatagramChannel.open();
//        selectedDatagramChannel.connect(datagramChannel.getRemoteAddress());
//
//        DataPacket dataPacket = new DataPacket(selectedDatagramChannel, commandsData);
//
//        return dataPacket;
//    }






//    public static Object receiveObject(DatagramChannel datagramChannel) throws IOException {
//
//
//        Object obj = null;
//        boolean endOfReceive = false;
//        byte[] objByteArr = new byte[0];
//        byte[] receivedArr;
//
//        while (!endOfReceive){
//            receivedArr = receiveByteArr(datagramChannel);
//            byte[] newArr = new byte[objByteArr.length + receivedArr.length];
//
//            for(int i =0;i<(objByteArr.length + receivedArr.length);i++){
//                if(i<objByteArr.length){
//                    newArr[i] = objByteArr[i];
//                }
//                else {
//                    newArr[i] = receivedArr[i-objByteArr.length];
//                }
//            }
//            objByteArr = newArr;
//
//
//            try {
//                obj = ObjectProcessing.deSerializeObject(objByteArr);
//                endOfReceive = true;
//
//            }catch (Exception e){}
//        }
//        return obj;
//    }
}
