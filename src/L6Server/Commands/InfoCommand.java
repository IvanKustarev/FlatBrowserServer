package L6Server.Commands;

import CommonClasses.CommandsData;
import L6Server.FlatCollection;
import L6Server.TransferCenter;

public class InfoCommand implements Command {



    @Override
    public String toString(){
        return "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }


    FlatCollection flatCollection;
    public InfoCommand(FlatCollection flatCollection){
        this.flatCollection = flatCollection;
//        this.flatCollection = new FlatCollection();
    }

    @Override
    public void execute(CommandsData command, TransferCenter transferCenter, CommandsData commandsData) {
        flatCollection.getInfo(command, transferCenter, commandsData);
    }
}
