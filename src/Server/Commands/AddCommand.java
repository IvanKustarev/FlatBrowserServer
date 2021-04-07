package Server.Commands;

import Server.DBWork.DBWorking;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.util.concurrent.ConcurrentLinkedQueue;

public class AddCommand implements Command {
    @Override
    public String toString(){
        return "add {element} : добавить новый элемент в коллекцию";
    }

    FlatCollection flatCollection;
    DBWorking dbWorking;
    public AddCommand(FlatCollection flatCollection, DBWorking dbWorking){
        this.flatCollection = flatCollection;
        this.dbWorking = dbWorking;
    }

    @Override
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.add(answersWaitingSending, dataPacket, dbWorking);
    }
}
