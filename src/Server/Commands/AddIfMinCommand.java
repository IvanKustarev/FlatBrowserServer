package Server.Commands;

import CommonClasses.CommandsData;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AddIfMinCommand implements Command {
    @Override
    public String toString(){
        return "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    FlatCollection flatCollection;

    public AddIfMinCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
    }

    @Override
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.addIfMin(answersWaitingSending, dataPacket);
    }
}
