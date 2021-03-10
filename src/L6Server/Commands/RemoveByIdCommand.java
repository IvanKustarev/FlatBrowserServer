package L6Server.Commands;

import CommonClasses.CommandsData;
import L6Server.FlatCollection;
import L6Server.TransferCenter;

public class RemoveByIdCommand implements Command{

    FlatCollection flatCollection;

    public RemoveByIdCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
    }
    @Override
    public String toString(){
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }

    @Override
    public void execute(CommandsData command, TransferCenter transferCenter, CommandsData commandsData) {
        flatCollection.removeById(command, transferCenter, commandsData);
    }
}
