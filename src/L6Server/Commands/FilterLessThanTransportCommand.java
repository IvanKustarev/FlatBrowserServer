package L6Server.Commands;


import CommonClasses.CommandsData;
import L6Server.FlatCollection;
import L6Server.TransferCenter;

public class FilterLessThanTransportCommand implements Command{

    FlatCollection flatCollection;
    public FilterLessThanTransportCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
    }

    @Override
    public String toString(){
        return "filter_less_than_transport transport : вывести элементы, значение поля transport которых меньше заданного";
    }

    @Override
    public void execute(CommandsData command, TransferCenter transferCenter, CommandsData commandsData) {
        flatCollection.filterLessThanTransport(command, transferCenter, commandsData);
    }
}
