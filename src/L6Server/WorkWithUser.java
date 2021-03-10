package L6Server;

import CommonClasses.CommandsData;
import CommonClasses.Flat;
import L6Server.Commands.AddCommand;
import L6Server.Commands.AddIfMinCommand;
import L6Server.Commands.*;
//import L6Server.Commands.CommandsData;

import java.util.Date;
import java.util.Iterator;
import java.util.Random;

public class WorkWithUser {

    FlatCollection flatCollection;
    String fileAddress;
    TransferCenter transferCenter = new TransferCenter();
//    CommandCenter commandCenter = new CommandCenter(fileAddress, flatCollection);


    public WorkWithUser(FlatCollection flatCollection, String fileAddress) {
        this.flatCollection = flatCollection;
        this.fileAddress = fileAddress;
    }

    public void startWorkWithUser(){

        CommandCenter commandCenter = new CommandCenter(fileAddress, new AddCommand(flatCollection), new AddIfMinCommand(flatCollection), new ClearCommand(flatCollection), /*new ExecuteScriptCommand(flatCollection, fileAddress),*/
                new FilterLessThanTransportCommand(flatCollection), new HelpCommand(), new InfoCommand(flatCollection), new PrintFieldAscendingNumberOfRoomsCommand(flatCollection),
                new RemoveByIdCommand(flatCollection), new RemoveHeadCommand(flatCollection), new RemoveLowerCommand(flatCollection), /*new SaveCommand(flatCollection, fileAddress),*/
                new ShowCommand(flatCollection), new SumOfNumberOfRoomsCommand(flatCollection), new UpdateIdCommand(flatCollection));


        CommandsData commandsData = null;
        while (true){
            commandsData = (CommandsData) transferCenter.receiveObjectFromUser();
//            transferCenter.checkingRequests();
            if(commandsData.isComandWithElementParameter()){
                commandsData.getFlat().setCreationDate(new Date());
                boolean err = true;
                while (err){
                    Random random = new Random();
                    Iterator iterator = flatCollection.getIterator();
                    Long newId = random.nextLong();

                    while (iterator.hasNext()){
                        Flat flat = (Flat) iterator.next();
                        err = false;
                        if(flat.getId().equals(newId)){
                            err = true;
                        }
                    }
                    if(!err){
                        commandsData.getFlat().setId(newId);
                    }
                }
            }
            commandCenter.processingAndStartUserCommand(commandsData, transferCenter);
        }
    }
}
