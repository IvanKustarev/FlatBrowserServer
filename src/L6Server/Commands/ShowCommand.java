package L6Server.Commands;


import CommonClasses.CommandsData;
import L6Server.FlatCollection;
import L6Server.TransferCenter;

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
    public void execute(CommandsData command, TransferCenter transferCenter, CommandsData commandsData) {
        flatCollection.show(command, transferCenter, commandsData);
    }
}
