package Server.Commands;

//import CommonClasses.*;
import Server.DBWork.DBWorking;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.util.concurrent.ConcurrentLinkedQueue;

public class RemoveByIdCommand implements Command{

    FlatCollection flatCollection;
    DBWorking dbWorking;

    public RemoveByIdCommand(FlatCollection flatCollection, DBWorking dbWorking){
        this.flatCollection = flatCollection;
        this.dbWorking = dbWorking;
    }
    @Override
    public String toString(){
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }

    @Override
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.removeById(answersWaitingSending, dataPacket, dbWorking);
    }
}
