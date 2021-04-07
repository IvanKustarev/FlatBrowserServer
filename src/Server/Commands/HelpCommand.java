package Server.Commands;

import CommonClasses.CommandsData;
//import CommonClasses.DataBlock;
import Server.DBWork.DBWorking;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.util.concurrent.ConcurrentLinkedQueue;

public class HelpCommand implements Command {

    DBWorking dbWorking;

    public HelpCommand(DBWorking dbWorking){
        this.dbWorking = dbWorking;
    }
    @Override
    public String toString(){
        return "help : вывести справку по доступным командам";
    }

    @Override
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket)
    {
//         dataBlock = new DataBlock();

        String phrase = "";
        phrase += (new HelpCommand(dbWorking).toString()) + "\n";
        phrase += (new InfoCommand(new FlatCollection()).toString()) + "\n";
        phrase += ((new ShowCommand(new FlatCollection())).toString()) + "\n";
        phrase += ((new AddCommand(new FlatCollection(), dbWorking)).toString()) + "\n";
        phrase += ((new UpdateIdCommand(new FlatCollection(), dbWorking)).toString()) + "\n";
        phrase += ((new RemoveByIdCommand(new FlatCollection(), dbWorking)).toString()) + "\n";
        phrase += ((new ClearCommand(new FlatCollection(), dbWorking)).toString()) + "\n";
//        System.out.println((new SaveCommand(new FlatCollection(), "")).toString());
        phrase +=((new ExecuteScriptCommand(new FlatCollection(), "", dbWorking)).toString()) + "\n";
        phrase += ((new ExitCommand()).toString()) + "\n";
        phrase += ((new RemoveHeadCommand(new FlatCollection(), dbWorking)).toString()) + "\n";
        phrase += ((new AddIfMinCommand(new FlatCollection(), dbWorking)).toString()) + "\n";
        phrase += ((new RemoveLowerCommand(new FlatCollection(), dbWorking)).toString()) + "\n";
        phrase += ((new SumOfNumberOfRoomsCommand(new FlatCollection())
        ).toString()) + "\n";
        phrase += ((new FilterLessThanTransportCommand(new FlatCollection())).toString()) + "\n";
        phrase += ((new PrintFieldAscendingNumberOfRoomsCommand(new FlatCollection())).toString()) + "\n";

        CommandsData commandsData = dataPacket.getCommandsData();
        commandsData.setPhrase(phrase);
        commandsData.setCommandEnded(true);
        answersWaitingSending.add(dataPacket);
    }
}
