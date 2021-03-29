package Server;

import CommonClasses.CommandsData;
import CommonClasses.Creator;
//import CommonClasses.DataBlock;
import CommonClasses.Flat;
import Server.CommandUnits.CommandCenter;
import Server.Commands.AddCommand;
import Server.Commands.AddIfMinCommand;
import Server.Commands.*;
import Server.FlatCollectionWorkers.FlatCollection;
//import L6Server.Commands.CommandsData;

import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

public class WorkWithUser {

    FlatCollection flatCollection;
    String fileAddress;
//    TransferCenter transferCenter;
//    CommandCenter commandCenter = new CommandCenter(fileAddress, flatCollection);


    public WorkWithUser(FlatCollection flatCollection, String fileAddress) {
        this.flatCollection = flatCollection;
        this.fileAddress = fileAddress;
//        transferCenter = new TransferCenter();
    }

//    public void setTransferCenter(TransferCenter transferCenter) {
//        this.transferCenter = transferCenter;
//    }

    //    public WorkWithUser(FlatCollection flatCollection, String fileAddress/*, TransferCenter transferCenter*/) {
//        this.flatCollection = flatCollection;
//        this.fileAddress = fileAddress;
////        this.transferCenter = transferCenter;
//    }

    public void startWorkWithUser(DatagramChannel datagramChannel, CommandsData commandsData){

        CommandCenter commandCenter = new CommandCenter(fileAddress, new AddCommand(flatCollection), new AddIfMinCommand(flatCollection), new ClearCommand(flatCollection), new ExecuteScriptCommand(flatCollection, fileAddress),
                new FilterLessThanTransportCommand(flatCollection), new HelpCommand(), new InfoCommand(flatCollection), new PrintFieldAscendingNumberOfRoomsCommand(flatCollection),
                new RemoveByIdCommand(flatCollection), new RemoveHeadCommand(flatCollection), new RemoveLowerCommand(flatCollection), /*new SaveCommand(flatCollection, fileAddress),*/
                new ShowCommand(flatCollection), new SumOfNumberOfRoomsCommand(flatCollection), new UpdateIdCommand(flatCollection));

//        ServerCommands serverCommands = new ServerCommands(flatCollection, fileAddress);
//        serverCommands.start();

//        while (!serverCommands.exit) {

        //тк операция выполняется только один раз, то нужды в циклах нет
        if (commandsData.isCommandWithElementParameter()) {
            commandsData.setFlat(createNullFieldsOfFlat(commandsData.getFlat()));
        }

        commandsData.setCreator(Creator.USER);
//        System.out.println("processingAndStartUserCommand");
        commandCenter.processingAndStartUserCommand(commandsData, datagramChannel);


//            CommandsData commandsData = null;
//            Object object = null;
//            try {
//                object = transferCenter.receiveObjectFromUser();
//                commandsData = (CommandsData) object;
//            } catch (Exception e) {
//                try {
//                    DataBlock dataBlock = (DataBlock) object;
//                    transferCenter.createConnectionForSending(dataBlock);
////                    transferCenter.writeInformation();
//                    WorkWithUser workWithUser = new WorkWithUser(flatCollection, fileAddress, transferCenter);
//                    workWithUser.startWorkWithUser();
//                    break;
//                }catch (Exception e1){
//                    System.out.println("Хз, что за файл пришел... Пропустим его :)");
//                    continue;
//                }
//
//            }
//
//            DataBlock dataBlock = (DataBlock) transferCenter.receiveObjectFromUser();
//            commandsData.setParameter(dataBlock.getParameter());
//            commandsData.setFlat(dataBlock.getFlat());
//
//            if (commandsData.isCommandWithElementParameter()) {
//                commandsData.setFlat(createNullFieldsOfFlat(commandsData.getFlat()));
//            }
//            commandsData.setCreator(Creator.USER);
//            commandCenter.processingAndStartUserCommand(commandsData, transferCenter);
//        }
//        flatCollection.save(flatCollection, fileAddress);
    }

    /**Заполняет незаполненные User-ом поля в Flat*/
    private Flat createNullFieldsOfFlat(Flat flat){
            flat.setCreationDate(new Date());
            boolean err = true;
            while (err) {
                Random random = new Random();
                Iterator iterator = flatCollection.getIterator();
                Long newId = random.nextLong();

                Flat flatFromCollection;
                while (iterator.hasNext()) {
                    flatFromCollection = (Flat) iterator.next();
                    err = false;
                    if (flatFromCollection.getId().equals(newId)) {
                        err = true;
                    }
                }
                if (!err) {
                    flat.setId(newId);
                }
            }
            return flat;
    }

}
