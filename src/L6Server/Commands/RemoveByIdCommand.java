package L6Server.Commands;

//import CommonClasses.*;
import CommonClasses.CommandsData;
import L6Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;

public class RemoveByIdCommand implements Command{

    FlatCollection flatCollection;

    public RemoveByIdCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
    }
    @Override
    public String toString(){
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }

    @Override
    public void execute(DatagramChannel datagramChannel, CommandsData commandsData) {
        flatCollection.removeById(datagramChannel, commandsData);
    }
}
