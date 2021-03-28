package L6Server.Commands;

import CommonClasses.CommandsData;
import L6Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;

public class RemoveLowerCommand implements Command{

    FlatCollection flatCollection;
    public RemoveLowerCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
    }

    @Override
    public String toString(){
        return "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный";
    }

    @Override
    public void execute(DatagramChannel datagramChannel, CommandsData commandsData) {
        flatCollection.removeLower(datagramChannel, commandsData);
    }
}
