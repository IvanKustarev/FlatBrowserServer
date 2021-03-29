package L6Server.Commands;

import CommonClasses.CommandsData;
import L6Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;

public class UpdateIdCommand implements Command{

    FlatCollection flatCollection;

    public UpdateIdCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
    }

    @Override
    public String toString(){
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public void execute(DatagramChannel datagramChannel, CommandsData commandsData) {
        flatCollection.updateId(datagramChannel, commandsData);
    }

}
