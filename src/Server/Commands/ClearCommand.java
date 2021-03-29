package Server.Commands;


import CommonClasses.CommandsData;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;

public class ClearCommand implements Command {

    FlatCollection flatCollection;

    public ClearCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
    }

    @Override
    public String toString(){
        return "clear : очистить коллекцию";
    }

    @Override
    public void execute(DatagramChannel datagramChannel, CommandsData commandsData) {
        flatCollection.clear(datagramChannel, commandsData);
    };
}
