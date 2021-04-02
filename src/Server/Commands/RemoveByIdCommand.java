package Server.Commands;

//import CommonClasses.*;
import CommonClasses.CommandsData;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

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
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.removeById(answersWaitingSending, dataPacket);
    }
}
