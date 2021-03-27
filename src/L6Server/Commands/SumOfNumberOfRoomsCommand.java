package L6Server.Commands;

import CommonClasses.CommandsData;
import L6Server.FlatCollection;
import L6Server.TransferCenter;

import java.nio.channels.DatagramChannel;

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
    public void execute(DatagramChannel datagramChannel, CommandsData commandsData) {
        flatCollection.sumOfNumberOfRooms(datagramChannel, commandsData);
    }
}
