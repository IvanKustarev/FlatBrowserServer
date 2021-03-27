package L6Server.Commands;

import CommonClasses.*;
import L6Server.TransferCenter;

import java.nio.channels.DatagramChannel;

public interface Command {
//    void execute(CommandsData command, TransferCenter transferCenter, CommandsData commandsData);

    void execute(DatagramChannel datagramChannel, CommandsData commandsData);
}
