package CommonClasses.ConnectionSupport;

import java.net.InetAddress;
import java.nio.channels.DatagramChannel;

public class FirstTimeConnectedData {
    private DatagramChannel datagramChannel = null;

    public DatagramChannel getDatagramChannel() {
        return datagramChannel;
    }

    public void setDatagramChannel(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }
}
