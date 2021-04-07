package Server.Commands;


import CommonClasses.CommandsData;
import Server.DBWork.DBWorking;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RemoveHeadCommand implements Command{

    FlatCollection flatCollection;
    DBWorking dbWorking;

    public RemoveHeadCommand(FlatCollection flatCollection, DBWorking dbWorking){
        this.flatCollection = flatCollection;
        this.dbWorking = dbWorking;

    }

    @Override
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.removeHead(answersWaitingSending, dataPacket, dbWorking);
    }

    @Override
    public String toString(){
        return "remove_head : вывести первый элемент коллекции и удалить его";
    }
}
