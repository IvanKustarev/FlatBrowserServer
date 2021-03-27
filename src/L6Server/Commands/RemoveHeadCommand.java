package L6Server.Commands;


import CommonClasses.CommandsData;
import L6Server.FlatCollection;
import L6Server.TransferCenter;

import java.nio.channels.DatagramChannel;

public class RemoveHeadCommand implements Command{

    FlatCollection flatCollection;

    public RemoveHeadCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;

    }

    @Override
    public void execute(DatagramChannel datagramChannel, CommandsData commandsData) {
        flatCollection.removeHead(datagramChannel, commandsData);
    }

    @Override
    public String toString(){
        return "remove_head : вывести первый элемент коллекции и удалить его";
    }
}
