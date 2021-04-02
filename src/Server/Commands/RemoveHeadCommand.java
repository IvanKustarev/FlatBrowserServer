package Server.Commands;


import CommonClasses.CommandsData;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RemoveHeadCommand implements Command{

    FlatCollection flatCollection;

    public RemoveHeadCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;

    }

    @Override
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.removeHead(answersWaitingSending, dataPacket);
    }

    @Override
    public String toString(){
        return "remove_head : вывести первый элемент коллекции и удалить его";
    }
}
