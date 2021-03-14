package L6Server;

import CommonClasses.CommandsData;
import CommonClasses.ConnectionSupport.FirstTimeConnectedData;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

public class ConnectionRequestsChecker extends Thread{

    Selector selector;
    DatagramChannel datagramChannel;

    public ConnectionRequestsChecker(Selector selector, DatagramChannel datagramChannel){
        this.datagramChannel = datagramChannel;
        this.selector = selector;
    }

    @Override
    public void run(){
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[1]);
        while (true){
            try {
                datagramChannel.receive(byteBuffer);
            } catch (IOException e) {
                System.out.println("Problem with receive!");
                e.printStackTrace();
            }
            FirstTimeConnectedData firstTimeConnectedData = (FirstTimeConnectedData) TransferCenter.deSerializeObject(byteBuffer.array());

            SocketAddress socketAddress = null;
            try {
                socketAddress = firstTimeConnectedData.getDatagramChannel().getLocalAddress();
                firstTimeConnectedData.setDatagramChannel(TransferCenter.createNewChannelWithIP());
                byteBuffer = ByteBuffer.wrap(TransferCenter.serializeObject(firstTimeConnectedData));
                datagramChannel.send(byteBuffer, socketAddress);
                datagramChannel.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteBuffer.clear();
        }
    }
}
