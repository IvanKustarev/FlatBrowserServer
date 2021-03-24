package L6Server.Commands;

import CommonClasses.CommandsData;
//import CommonClasses.DataBlock;
import L6Server.FlatCollection;
import L6Server.TransferCenter;

import java.nio.channels.DatagramChannel;

public class HelpCommand implements Command {
    @Override
    public String toString(){
        return "help : вывести справку по доступным командам";
    }

    @Override
    public void execute(DatagramChannel datagramChannel, CommandsData commandsData)
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

        commandsData.setPhrase(phrase);
        commandsData.setCommandEnded(true);
        TransferCenter.sendObject(datagramChannel, commandsData);
//        dataBlock.setAllRight(true);
//        transferCenter.sendObjectToUser(dataBlock);
    }
}
