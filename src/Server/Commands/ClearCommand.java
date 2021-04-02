package Server.Commands;


import CommonClasses.CommandsData;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

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
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.clear(answersWaitingSending, dataPacket);
    };
}
