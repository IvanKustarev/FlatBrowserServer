package Server.Commands;

import CommonClasses.CommandsData;
//import CommonClasses.DataBlock;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;
import Server.TransferCenter;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HelpCommand implements Command {
    @Override
    public String toString(){
        return "help : вывести справку по доступным командам";
    }

    @Override
    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket)
    {
//         dataBlock = new DataBlock();

        String phrase = "";
        phrase += (new HelpCommand().toString()) + "\n";
        phrase += (new InfoCommand(new FlatCollection()).toString()) + "\n";
        phrase += ((new ShowCommand(new FlatCollection())).toString()) + "\n";
        phrase += ((new AddCommand(new FlatCollection())).toString()) + "\n";
        phrase += ((new UpdateIdCommand(new FlatCollection())).toString()) + "\n";
        phrase += ((new RemoveByIdCommand(new FlatCollection())).toString()) + "\n";
        phrase += ((new ClearCommand(new FlatCollection())).toString()) + "\n";
//        System.out.println((new SaveCommand(new FlatCollection(), "")).toString());
        phrase +=((new ExecuteScriptCommand(new FlatCollection(), "")).toString()) + "\n";
        phrase += ((new ExitCommand()).toString()) + "\n";
        phrase += ((new RemoveHeadCommand(new FlatCollection())).toString()) + "\n";
        phrase += ((new AddIfMinCommand(new FlatCollection())).toString()) + "\n";
        phrase += ((new RemoveLowerCommand(new FlatCollection())).toString()) + "\n";
        phrase += ((new SumOfNumberOfRoomsCommand(new FlatCollection())
        ).toString()) + "\n";
        phrase += ((new FilterLessThanTransportCommand(new FlatCollection())).toString()) + "\n";
        phrase += ((new PrintFieldAscendingNumberOfRoomsCommand(new FlatCollection())).toString()) + "\n";

        CommandsData commandsData = dataPacket.getCommandsData();
        commandsData.setPhrase(phrase);
        commandsData.setCommandEnded(true);
//        System.out.println(phrase);
//        TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
        answersWaitingSending.add(dataPacket);
//        dataBlock.setAllRight(true);
//        transferCenter.sendObjectToUser(dataBlock);
    }
}
