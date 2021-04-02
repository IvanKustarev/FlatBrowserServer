package Server.Commands;

import CommonClasses.CommandsData;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SumOfNumberOfRoomsCommand implements Command{

    FlatCollection flatCollection;

    public SumOfNumberOfRoomsCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
    }

    @Override
    public String toString(){
        return "sum_of_number_of_rooms : вывести сумму значений поля numberOfRooms для всех элементов коллекции";
    }

    @Override
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.sumOfNumberOfRooms(answersWaitingSending, dataPacket);
    }
}
