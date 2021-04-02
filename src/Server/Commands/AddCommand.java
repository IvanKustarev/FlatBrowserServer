package Server.Commands;

import CommonClasses.CommandsData;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AddCommand implements Command {
    @Override
    public String toString(){
        return "add {element} : добавить новый элемент в коллекцию";
    }

    FlatCollection flatCollection;
    public AddCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
    }

    @Override
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.add(answersWaitingSending, dataPacket);
    }
}
