package Server;

import CommonClasses.CommandsData;
import CommonClasses.DataBlock;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.DatagramChannel;

public class SendingAnswerThread implements Runnable{

    DataPacket dataPacket = null;

    public SendingAnswerThread(DataPacket dataPacket){
        this.dataPacket = dataPacket;
    }

    @Override
    public void run() {
        TransferCenter.sendAnswerToUser(dataPacket.getDatagramChannel(), dataPacket.getCommandsData());
    }


}
