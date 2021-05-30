package Server.Commands;


import Server.DBWork.DBWorking;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.util.concurrent.ConcurrentLinkedQueue;

public class ClearCommand implements Command {

    FlatCollection flatCollection;
    DBWorking dbWorking;

    public ClearCommand(FlatCollection flatCollection, DBWorking dbWorking){
        this.flatCollection = flatCollection;
        this.dbWorking = dbWorking;
    }

    @Override
    public String toString(){
        return "clear : очистить коллекцию";
    }

    @Override
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.clear(answersWaitingSending, dataPacket, dbWorking);
    };
}
