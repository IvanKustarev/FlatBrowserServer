package Server.Commands;

import CommonClasses.CommandsData;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PrintFieldAscendingNumberOfRoomsCommand implements Command {

    FlatCollection flatCollection;
    public PrintFieldAscendingNumberOfRoomsCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
    }

    @Override
    public String toString(){
        return "print_field_ascending_number_of_rooms : вывести значения поля numberOfRooms всех элементов в порядке возрастания";
    }

    @Override
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        flatCollection.printFieldAscendingNumberOfRooms(answersWaitingSending, dataPacket);
    }
}
