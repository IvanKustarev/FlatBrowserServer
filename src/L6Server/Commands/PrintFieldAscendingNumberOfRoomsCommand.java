package L6Server.Commands;

import CommonClasses.CommandsData;
import L6Server.FlatCollection;
import L6Server.TransferCenter;

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
    public void execute(CommandsData command, TransferCenter transferCenter, CommandsData commandsData) {
        flatCollection.printFieldAscendingNumberOfRooms(command, transferCenter, commandsData);
    }
}
