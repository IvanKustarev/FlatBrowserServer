package CommonClasses;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;



import java.net.InetAddress;
import java.nio.channels.DatagramChannel;

public class FirstTimeConnectedData implements Serializable {
//    private DatagramChannel datagramChannel = null;
//
//    public DatagramChannel getDatagramChannel() {
//        return datagramChannel;
//    }
//
//    public void setDatagramChannel(DatagramChannel datagramChannel) {
//        this.datagramChannel = datagramChannel;
//    }

    private SocketAddress socketAddress = null;

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }
}


