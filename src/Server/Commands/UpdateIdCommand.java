package Server.Commands;

import CommonClasses.CommandsData;
import Server.DBWork.DBWorking;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UpdateIdCommand implements Command{

    FlatCollection flatCollection;
    DBWorking dbWorking;
    public UpdateIdCommand(FlatCollection flatCollection, DBWorking dbWorking){
        this.flatCollection = flatCollection;
        this.dbWorking = dbWorking;
    }

    @Override
    public String toString(){
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.updateId(answersWaitingSending, dataPacket, dbWorking);
    }

}
