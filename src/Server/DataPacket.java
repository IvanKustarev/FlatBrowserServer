package Server;

import CommonClasses.CommandsData;
import CommonClasses.DataBlock;
import CommonClasses.User;

import java.nio.channels.DatagramChannel;
import java.util.ResourceBundle;

// для хранения данных при обработки их в многопоточном режиме
public class DataPacket {

    private String resourceBundleName;

    public DataPacket(DatagramChannel datagramChannel, CommandsData commandsData, User user, String resourceBundleName){
        this.datagramChannel = datagramChannel;
//        this. = dataBlock;
        this.commandsData = commandsData;
        this.user = user;
        this.resourceBundleName = resourceBundleName;
    }

//    DataBlock dataBlock  = null;
    private CommandsData commandsData = null;
    private DatagramChannel datagramChannel = null;
    private User user = null;

    public String getResourceBundleName() {
        return resourceBundleName;
    }

    public void setResourceBundleName(String resourceBundleName) {
        this.resourceBundleName = resourceBundleName;
    }

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
