package Server.Commands;

import CommonClasses.CommandsData;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;

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
    public void execute(DatagramChannel datagramChannel, CommandsData commandsData) {
        flatCollection.getInfo(datagramChannel, commandsData);
    }
}
