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
import java.util.concurrent.ConcurrentLinkedQueue;

public class WorkWithUser {

    FlatCollection flatCollection;
    String fileAddress;


//    Подумать, как это правильно сделать
//    ===================================
//    private ConcurrentLinkedQueue<DataPacket> answersWaitingSending = null;

    public WorkWithUser(FlatCollection flatCollection, String fileAddress) {
        this.flatCollection = flatCollection;
        this.fileAddress = fileAddress;
//        this.answersWaitingSending = answersWaitingSending;
    }

//    public ConcurrentLinkedQueue<DataPacket> getAnswersWaitingSending() {
//        return answersWaitingSending;
//    }

    public void startWorkWithUser(DataPacket dataPacket, ConcurrentLinkedQueue<DataPacket> answersWaitingSending){
//        DatagramChannel datagramChannel =  dataPacket.getDatagramChannel();
        CommandsData commandsData = dataPacket.getCommandsData();

        CommandCenter commandCenter = new CommandCenter(fileAddress, new AddCommand(flatCollection), new AddIfMinCommand(flatCollection), new ClearCommand(flatCollection), new ExecuteScriptCommand(flatCollection, fileAddress),
                new FilterLessThanTransportCommand(flatCollection), new HelpCommand(), new InfoCommand(flatCollection), new PrintFieldAscendingNumberOfRoomsCommand(flatCollection),
                new RemoveByIdCommand(flatCollection), new RemoveHeadCommand(flatCollection), new RemoveLowerCommand(flatCollection), /*new SaveCommand(flatCollection, fileAddress),*/
                new ShowCommand(flatCollection), new SumOfNumberOfRoomsCommand(flatCollection), new UpdateIdCommand(flatCollection));

        //тк операция выполняется только один раз, то нужды в циклах нет
        if (commandsData.isCommandWithElementParameter()) {
            commandsData.setFlat(createNullFieldsOfFlat(commandsData.getFlat()));
        }

        commandsData.setCreator(Creator.USER);
        commandCenter.processingAndStartUserCommand(dataPacket, answersWaitingSending);
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
