package Server;

import CommonClasses.CommandsData;
import CommonClasses.DataBlock;

import java.nio.channels.DatagramChannel;

// для хранения данных при обработки их в многопоточном режиме
public class DataPacket {

    public DataPacket(DatagramChannel datagramChannel, CommandsData commandsData){
        this.datagramChannel = datagramChannel;
//        this. = dataBlock;
        this.commandsData = commandsData;
    }

//    DataBlock dataBlock  = null;
    private CommandsData commandsData = null;
    private DatagramChannel datagramChannel = null;

    public void setCommandsData(CommandsData commandsData) {
        this.commandsData = commandsData;
    }

    public void setDatagramChannel(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
    }

    public CommandsData getCommandsData() {
        return commandsData;
    }

    public DatagramChannel getDatagramChannel() {
        return datagramChannel;
    }
}
