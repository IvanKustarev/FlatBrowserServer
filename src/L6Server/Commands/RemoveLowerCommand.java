package L6Server.Commands;

import CommonClasses.CommandsData;
import L6Server.FlatCollection;
import L6Server.TransferCenter;

public class RemoveLowerCommand implements Command{

    FlatCollection flatCollection;
    public RemoveLowerCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
    }

    @Override
    public String toString(){
        return "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный";
    }

    @Override
    public void execute(CommandsData command, TransferCenter transferCenter, CommandsData commandsData) {
        flatCollection.removeLower(command, transferCenter, commandsData);
    }
}
