package Server.Commands;


import CommonClasses.CommandsData;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

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
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.filterLessThanTransport(answersWaitingSending, dataPacket);
    }
}
