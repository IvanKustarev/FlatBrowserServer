package Server;

import CommonClasses.CommandsData;
import CommonClasses.DataBlock;
import CommonClasses.User;

import java.nio.channels.DatagramChannel;

// для хранения данных при обработки их в многопоточном режиме
public class DataPacket {

    public DataPacket(DatagramChannel datagramChannel, CommandsData commandsData, User user){
        this.datagramChannel = datagramChannel;
//        this. = dataBlock;
        this.commandsData = commandsData;
        this.user = user;
    }

//    DataBlock dataBlock  = null;
    private CommandsData commandsData = null;
    private DatagramChannel datagramChannel = null;
    private User user = null;

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

    public User getUser() {
        return user;
    }
}
