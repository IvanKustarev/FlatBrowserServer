package Server.Commands;


import CommonClasses.CommandsData;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ShowCommand implements Command {
    @Override
    public String toString(){
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    FlatCollection flatCollection;
    public ShowCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
    }

    @Override
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.show(answersWaitingSending, dataPacket);
    }
}
