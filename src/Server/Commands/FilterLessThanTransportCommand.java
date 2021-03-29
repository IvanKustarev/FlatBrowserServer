package Server.Commands;


import CommonClasses.CommandsData;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;

public class FilterLessThanTransportCommand implements Command{

    FlatCollection flatCollection;
    public FilterLessThanTransportCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
    }

    @Override
    public String toString(){
        return "filter_less_than_transport transport : вывести элементы, значение поля transport которых меньше заданного";
    }

    @Override
    public void execute(DatagramChannel datagramChannel, CommandsData commandsData) {
        flatCollection.filterLessThanTransport(datagramChannel, commandsData);
    }
}
