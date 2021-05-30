package Server.Commands;

import CommonClasses.*;
import Server.DataPacket;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface Command {
//    void execute(CommandsData command, TransferCenter transferCenter, CommandsData commandsData);

    void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket);
}
