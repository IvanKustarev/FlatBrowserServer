package Server;

import CommonClasses.CommandsData;
import CommonClasses.Creator;
//import CommonClasses.DataBlock;
import CommonClasses.Flat;
import Server.DBWork.AnswerDBWorkerCommands;
import Server.DBWork.DBWorking;
import Server.CommandUnits.CommandCenter;
import Server.Commands.AddCommand;
import Server.Commands.AddIfMinCommand;
import Server.Commands.*;
import Server.FlatCollectionWorkers.FlatCollection;
//import L6Server.Commands.CommandsData;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WorkWithUser {

    FlatCollection flatCollection;
    String fileAddress;
    DBWorking dbWorking;


//    Подумать, как это правильно сделать
//    ===================================
//    private ConcurrentLinkedQueue<DataPacket> answersWaitingSending = null;

    public WorkWithUser(FlatCollection flatCollection, String fileAddress, DBWorking dbWorking) {
        this.flatCollection = flatCollection;
        this.fileAddress = fileAddress;
        this.dbWorking = dbWorking;
    }


    public void startWorkWithUser(DataPacket dataPacket, ConcurrentLinkedQueue<DataPacket> answersWaitingSending){



        if(dataPacket.getCommandsData().equals(CommandsData.CHECKUSER)){
            TransferCenter.sendAnswerToUser(dataPacket.getDatagramChannel(), userAnalysis(dataPacket));
        }

        CommandsData commandsData = dataPacket.getCommandsData();

        dbWorking.load();


        CommandCenter commandCenter = new CommandCenter(dbWorking, fileAddress, new AddCommand(flatCollection, dbWorking), new AddIfMinCommand(flatCollection, dbWorking), new ClearCommand(flatCollection, dbWorking), new ExecuteScriptCommand(flatCollection, fileAddress, dbWorking),
                new FilterLessThanTransportCommand(flatCollection), new HelpCommand(dbWorking), new InfoCommand(flatCollection), new PrintFieldAscendingNumberOfRoomsCommand(flatCollection),
                new RemoveByIdCommand(flatCollection, dbWorking), new RemoveHeadCommand(flatCollection, dbWorking), new RemoveLowerCommand(flatCollection, dbWorking), /*new SaveCommand(flatCollection, fileAddress),*/
                new ShowCommand(flatCollection), new SumOfNumberOfRoomsCommand(flatCollection), new UpdateIdCommand(flatCollection, dbWorking));

        //тк операция выполняется только один раз, то нужды в циклах нет
//        System.out.println(commandsData.isCommandWithElementParameter());
//        System.out.println("ttt");
//
//        boolean commandWithElementParameter = commandsData.isCommandWithElementParameter();
//        System.out.println(commandWithElementParameter);

        try {
//            Для проверки на NullPointer
            commandsData.isCommandWithElementParameter().equals(true);

            if (commandsData.isCommandWithElementParameter()) {
                createNullFieldsOfFlat(commandsData.getFlat());
                commandsData.getFlat().setUserName(dataPacket.getUser().getLogin());
            }
        }catch (Exception e){
            commandsData.setCommandWithElementParameter(false);
        }





        commandsData.setCreator(Creator.USER);
//        System.out.println(commandsData.name());


        commandCenter.processingAndStartUserCommand(dataPacket, answersWaitingSending);
    }

    private CommandsData userAnalysis(DataPacket dataPacket){
        if(dataPacket.getUser().isNewUser()){
            AnswerDBWorkerCommands answerDBWorkerCommands = dbWorking.pushNewUser(dataPacket.getUser());
            dataPacket.getCommandsData().setPhrase(answerDBWorkerCommands.getPhrase());
            dataPacket.getCommandsData().setParameter(String.valueOf(answerDBWorkerCommands.isSuccessfulResult()));
        }
        else {
            AnswerDBWorkerCommands answerDBWorkerCommands = dbWorking.checkUser(dataPacket.getUser());
            dataPacket.getCommandsData().setPhrase(answerDBWorkerCommands.getPhrase());
            dataPacket.getCommandsData().setParameter(String.valueOf(answerDBWorkerCommands.isSuccessfulResult()));
        }
        return dataPacket.getCommandsData();
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
                err = false;
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
