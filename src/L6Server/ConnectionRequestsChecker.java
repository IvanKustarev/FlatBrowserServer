package L6Server;

import CommonClasses.FirstTimeConnectedData;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class ConnectionRequestsChecker extends Thread{

    Selector selector;
    DatagramChannel datagramChannel;

    public ConnectionRequestsChecker(Selector selector, DatagramChannel datagramChannel){
        this.datagramChannel = datagramChannel;
        this.selector = selector;
    }

    @Override
    public void run(){
        ByteBuffer byteBuffer = null;
        while (true){
            byteBuffer = ByteBuffer.wrap(new byte[10000]);
            SocketAddress userSocketAddress = null;
            try {
                userSocketAddress = datagramChannel.receive(byteBuffer);
            } catch (IOException e) {
                System.out.println("Problem with receive!");
                e.printStackTrace();
            }

//            System.out.println();

            FirstTimeConnectedData firstTimeConnectedData = (FirstTimeConnectedData) ObjectProcessing.deSerializeObject(byteBuffer.array());
            SocketAddress socketAddress = null;
            try {
//                socketAddress = firstTimeConnectedData.getDatagramChannel().getLocalAddress();
                socketAddress = firstTimeConnectedData.getSocketAddress();
//                firstTimeConnectedData.setDatagramChannel(TransferCenter.createNewChannelWithIP());
                DatagramChannel newDatagramChannel = TransferCenter.createNewChannelWithIP();
                firstTimeConnectedData.setSocketAddress(newDatagramChannel.getLocalAddress());
                newDatagramChannel.connect(userSocketAddress);
//                System.out.println(firstTimeConnectedData.getSocketAddress());
                byteBuffer = ByteBuffer.wrap(ObjectProcessing.serializeObject(firstTimeConnectedData));
                datagramChannel.send(byteBuffer, socketAddress);

//                System.out.println(newDatagramChannel.getLocalAddress());
                newDatagramChannel.configureBlocking(false);
                newDatagramChannel.register(selector, SelectionKey.OP_READ);
//                System.out.println(selector.keys().size());

//                DatagramChannel datagramChannel1 = DatagramChannel.open();
//                datagramChannel1.connect(socketAddress);
//                datagramChannel1.configureBlocking(false);
//                datagramChannel1.register(selector, SelectionKey.OP_READ);
////                System.out.println(datagramChannel1.getLocalAddress());
////                System.out.println("ttt");
//                Iterator iterator = selector.keys().iterator();
//                SelectionKey selectionKey = (SelectionKey)iterator.next();
////                selectionKey.channel().

            } catch (IOException e) {
                e.printStackTrace();
            }
//            System.out.println(selector.keys().size());
//            byteBuffer.clear();
        }
    }
}
