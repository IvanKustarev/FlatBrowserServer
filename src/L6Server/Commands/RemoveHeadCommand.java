package L6Server.Commands;


import CommonClasses.CommandsData;
import L6Server.FlatCollection;
import L6Server.TransferCenter;

public class RemoveHeadCommand implements Command{

    FlatCollection flatCollection;

    public RemoveHeadCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;

    }

    @Override
    public void execute(CommandsData command, TransferCenter transferCenter, CommandsData commandsData) {
        flatCollection.removeHead(command, transferCenter, commandsData);
    }

    @Override
    public String toString(){
        return "remove_head : вывести первый элемент коллекции и удалить его";
    }
}
