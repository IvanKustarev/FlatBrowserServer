package L6Server.Commands;

import CommonClasses.CommandsData;
import L6Server.FlatCollection;
import L6Server.TransferCenter;

import java.nio.channels.DatagramChannel;

public class AddCommand implements Command {
    @Override
    public String toString(){
        return "add {element} : добавить новый элемент в коллекцию";
    }

    FlatCollection flatCollection;
    public AddCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
    }

    @Override
    public void execute(DatagramChannel datagramChannel, CommandsData commandsData) {
        flatCollection.add(datagramChannel, commandsData);
    }
}
