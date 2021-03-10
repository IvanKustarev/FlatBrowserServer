package L6Server.Commands;

import CommonClasses.CommandsData;
import L6Server.FlatCollection;
import L6Server.TransferCenter;

public class HelpCommand implements Command {
    @Override
    public String toString(){
        return "help : вывести справку по доступным командам";
    }

    @Override
    public void execute(CommandsData command, TransferCenter transferCenter, CommandsData commandsData)
    {
        System.out.println((new HelpCommand()).toString());
        System.out.println((new InfoCommand(new FlatCollection())).toString());
        System.out.println((new ShowCommand(new FlatCollection())).toString());
        System.out.println((new AddCommand(new FlatCollection())).toString());
        System.out.println((new UpdateIdCommand(new FlatCollection())).toString());
        System.out.println((new RemoveByIdCommand(new FlatCollection())).toString());
        System.out.println((new ClearCommand(new FlatCollection())).toString());
//        System.out.println((new SaveCommand(new FlatCollection(), "")).toString());
//        System.out.println((new ExecuteScriptCommand(new FlatCollection(), "")).toString());
        System.out.println((new ExitCommand()).toString());
        System.out.println((new RemoveHeadCommand(new FlatCollection())).toString());
        System.out.println((new AddIfMinCommand(new FlatCollection())).toString());
        System.out.println((new RemoveLowerCommand(new FlatCollection())).toString());
        System.out.println((new SumOfNumberOfRoomsCommand(new FlatCollection())
        ).toString());
        System.out.println((new FilterLessThanTransportCommand(new FlatCollection())).toString());
        System.out.println((new PrintFieldAscendingNumberOfRoomsCommand(new FlatCollection())).toString());
    }
}
