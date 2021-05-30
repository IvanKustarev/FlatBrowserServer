package Server.Commands;

import CommonClasses.CommandsData;
import Server.DBWork.DBWorking;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RemoveLowerCommand implements Command{

    FlatCollection flatCollection;
    DBWorking dbWorking;
    public RemoveLowerCommand(FlatCollection flatCollection, DBWorking dbWorking){
        this.flatCollection = flatCollection;
        this.dbWorking = dbWorking;
    }

    @Override
    public String toString(){
        return "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный";
    }

    @Override
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.removeLower(answersWaitingSending, dataPacket);
    }
}
