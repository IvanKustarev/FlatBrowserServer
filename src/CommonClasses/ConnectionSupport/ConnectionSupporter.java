//package CommonClasses.ConnectionSupport;
//
//import java.net.*;
//import java.util.Random;
//
//public class ConnectionSupporter extends Thread{
//
//    InetSocketAddress socketAddressReceive;
//    DatagramSocket datagramSocketReceive;
//
//    @Override
//    public void run(){
//        createSocketsForReceive();
//
//    }
//
//    private int createSocketsForReceive(){
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
//}
