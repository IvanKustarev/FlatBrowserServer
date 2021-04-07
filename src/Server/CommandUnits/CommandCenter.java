package Server.CommandUnits;


import CommonClasses.CommandsData;
import CommonClasses.Creator;
//import CommonClasses.DataBlock;
import Server.DBWork.DBWorking;
import Server.Commands.*;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;

/**Класс, который создаётся в user и script и управляет работой команд*/
public class CommandCenter {

    String fileAddress;
    FlatCollection flatCollection;
    AddCommand addCommand;
    AddIfMinCommand addIfMinCommand;
    ClearCommand clearCommand;
    ExecuteScriptCommand executeScriptCommand;
//    saveCommand saveCommand;
    FilterLessThanTransportCommand filterLessThanTransportCommand;
    HelpCommand helpCommand;
    InfoCommand infoCommand;
    PrintFieldAscendingNumberOfRoomsCommand printFieldAscendingNumberOfRoomsCommand;
    RemoveByIdCommand removeByIdCommand;
    RemoveHeadCommand removeHeadCommand;
    RemoveLowerCommand removeLowerCommand;
    ShowCommand showCommand;
    SumOfNumberOfRoomsCommand sumOfNumberOfRoomsCommand;
    UpdateIdCommand updateIdCommand;
    DBWorking dbWorking;


    public CommandCenter(DBWorking dbWorking, String fileAddress, AddCommand addCommand, AddIfMinCommand addIfMinCommand, ClearCommand clearCommand, ExecuteScriptCommand executeScriptCommand, FilterLessThanTransportCommand filterLessThanTransportCommand, HelpCommand helpCommand, InfoCommand infoCommand, PrintFieldAscendingNumberOfRoomsCommand printFieldAscendingNumberOfRoomsCommand, RemoveByIdCommand removeByIdCommand, RemoveHeadCommand removeHeadCommand, RemoveLowerCommand removeLowerCommand, /*SaveCommand saveCommand,*/ ShowCommand showCommand, SumOfNumberOfRoomsCommand sumOfNumberOfRoomsCommand, UpdateIdCommand updateId) {
        this.dbWorking = dbWorking;
        this.fileAddress = fileAddress;
        this.addCommand = addCommand;
        this.addIfMinCommand = addIfMinCommand;
        this.clearCommand = clearCommand;
        this.executeScriptCommand = executeScriptCommand;
        this.filterLessThanTransportCommand = filterLessThanTransportCommand;
        this.helpCommand = helpCommand;
        this.infoCommand = infoCommand;
        this.printFieldAscendingNumberOfRoomsCommand = printFieldAscendingNumberOfRoomsCommand;
        this.removeByIdCommand = removeByIdCommand;
        this.removeHeadCommand = removeHeadCommand;
        this.removeLowerCommand = removeLowerCommand;
//        this.saveCommand = saveCommand;
        this.showCommand = showCommand;
        this.sumOfNumberOfRoomsCommand = sumOfNumberOfRoomsCommand;
        this.updateIdCommand = updateId;
    }

    public void add(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        addCommand.execute(answersWaitingSending, dataPacket);
    }

    public void addIfMin(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        addIfMinCommand.execute(answersWaitingSending, dataPacket);
    }

//    Необходимо доделать уровень доступа
    public void clear(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        clearCommand.execute(answersWaitingSending, dataPacket);
    }

    public void filterLessThanTransport(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        filterLessThanTransportCommand.execute(answersWaitingSending, dataPacket);
    }

    public void executeScript(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        executeScriptCommand.execute(answersWaitingSending, dataPacket);
    }

    public void help(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        helpCommand.execute(answersWaitingSending, dataPacket);
    }

    public void info(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        infoCommand.execute(answersWaitingSending, dataPacket);
    }

    public void printFieldAscendingNumberOfRooms(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        printFieldAscendingNumberOfRoomsCommand.execute(answersWaitingSending, dataPacket);
    }

//    Необходимо доделать уровень доступа
    public void removeById(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        removeByIdCommand.execute(answersWaitingSending, dataPacket);
    }

    public void removeHead(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        removeHeadCommand.execute(answersWaitingSending, dataPacket);
    }

    public void removeLower(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        removeLowerCommand.execute(answersWaitingSending, dataPacket);
    }


    public void show(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        showCommand.execute(answersWaitingSending, dataPacket);
    }

    public void sumOfNumberOfRooms(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        sumOfNumberOfRoomsCommand.execute(answersWaitingSending, dataPacket);
    }

    public void update(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        updateIdCommand.execute(answersWaitingSending, dataPacket);
    }


    /**Отвечает за поиск введённого поля в списке команд. Если такой команды нет, выдаёт null*/
    public CommandsData whatTheCommand(String command){
        CommandsData[] commandsVariations = CommandsData.values();
        for(int i = 0; i < commandsVariations.length; i++){
            if(command.contains(commandsVariations[i].toString())){
                return commandsVariations[i];
            }
        }
        return null;
    }

    private boolean isCommandWithParameter(String command){
        if(command.contains(CommandsData.UPDATE.toString()) | command.contains(CommandsData.REMOVEBYID.toString()) |
                command.contains(CommandsData.EXECUTESCRIPT.toString()) | command.contains(CommandsData.FILTERLESSTHANTRANSPORT.toString())){
            return true;
        }
        return false;
    }

    /**берёт из строки команды пользователя её параметр и пакует это всё в Commands объект*/
    private CommandsData packingCommandInCommandsObject(String command, Boolean commandWithParameter, Creator creator, CommandsData commandsData){
        CommandsData commandObject = null;


        CommandsData[] commands = CommandsData.values();
        for (int i =0; i<commands.length; i++){
            if(command.contains(commands[i].toString())){
                commandObject = commands[i];
            }
        }


        commandObject.setCreator(creator);

        if(commandWithParameter){

            String[] comWords = command.split(" ");
            int index = 0;
            for (int i = 0; i < comWords.length; i++) {
                for(int j = 0; j < commands.length;j++){
                    if (comWords[i].equals(commands[j].toString())) {
                        index = i + 1;
                        if(index==comWords.length){
                            comWords = new String[index+1];
                            comWords[index] = "";
                        }
                        break;
                    }
                    break;
                }
                break;
            }
//            System.out.println(command);
            commandObject.setParameter(comWords[index+1]);
        }

        commandObject.setBufferedReader(commandsData.getBufferedReader());
        commandObject.setCreator(Creator.SCRIPT);


        return commandObject;
    }

//    /**запуск команды осуществляемый скриптом*/
//    public void processingAndStartUserCommand(String command){
//        CommandsData userCommand = packingCommandInCommandsObject(command, isCommandWithParameter(command), Creator.USER);
//        if(userCommand.equals(CommandsData.EXECUTESCRIPT)){
//            //дополнительные параметры для этой команды
//            try {
//                //создаем буффер для чтения файла со скриптом
//                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(userCommand.getParameter())));
//                userCommand.setBufferedReader(bufferedReader);
//
//                //создаётся стек для открытых файлов (ловить рекурсию)
//                Stack<String> openingFiles = new Stack<>();
//                userCommand.setOpeningFiles(openingFiles);
//                userCommand.addOpeningFile(userCommand.getParameter());
//            } catch (FileNotFoundException e) {
//                System.out.println("Проблемы с загрузкой файла со скриптом!");
//            }
//        }
//        startCommand(userCommand);
//    }

    public void processingAndStartUserCommand(DataPacket dataPacket, ConcurrentLinkedQueue<DataPacket> answersWaitingSending){
        CommandsData commandsData = dataPacket.getCommandsData();
//        CommandsData userCommand = packingCommandInCommandsObject(commandsData.toString() + " " + commandsData.getParameter(), isCommandWithParameter(commandsData.toString() + commandsData.getParameter()), Creator.USER);
        if(commandsData.equals(CommandsData.EXECUTESCRIPT)){
            //дополнительные параметры для этой команды
            try {
                //создаем буффер для чтения файла со скриптом
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(commandsData.getParameter())));
                commandsData.setBufferedReader(bufferedReader);

                //создаётся стек для открытых файлов (ловить рекурсию)
                Stack<String> openingFiles = new Stack<>();
                commandsData.setOpeningFiles(openingFiles);
                commandsData.addOpeningFile(commandsData.getParameter());
            } catch (FileNotFoundException e) {
                System.out.println("Проблемы с загрузкой файла со скриптом!");
            }
        }
        startCommand(answersWaitingSending, dataPacket);
    }

//    /**запуск команды осуществляемый user-ом*/
    public void processingAndStartScriptCommand(/*String command, CommandsData commandsData, BufferedReader bufferedReader, TransferCenter transferCenter, CommandsData otherCommandsData*/
    DataPacket dataPacket, BufferedReader bufferedReader, ConcurrentLinkedQueue<DataPacket> answersWaitingSending, String command/*TransferCenter transferCenter,*/){
        CommandsData commandsData = dataPacket.getCommandsData();
        CommandsData scriptCommand = packingCommandInCommandsObject(command, isCommandWithParameter(command), Creator.SCRIPT, commandsData);
//        System.out.println(scriptCommand.name());
        boolean recursWasStarted = false;
        scriptCommand.setBufferedReader(bufferedReader);
        if(scriptCommand.equals(CommandsData.EXECUTESCRIPT)){
            //дополнительные параметры для этой команды

            String[] nameOfOpenedFiles = new String[scriptCommand.getOpeningFiles().toArray().length];
            for(int i =0; i < scriptCommand.getOpeningFiles().toArray().length; i++){
                nameOfOpenedFiles[i] = (String) (scriptCommand.getOpeningFiles().toArray())[i];
//                System.out.println(nameOfOpenedFiles[i]);
            }

            for (String nameOfOpenedFile : nameOfOpenedFiles){
                if(nameOfOpenedFile.equals(scriptCommand.getParameter())){
                    recursWasStarted = true;
                }
            }
        }

//        System.out.println(commandsData.name());
//        recursWasStarted = false;
        if(recursWasStarted){
//            CommandsData dataBlock = new DataBlock();
//            dataBlock.setAllRight(true);
//            dataBlock.setPhrase("Сорри, бро, тут рекурсия, мы прикрываем это лавочку...");
//            transferCenter.sendObjectToUser(dataBlock);
            scriptCommand.setCommandEnded(false);
            scriptCommand.setPhrase("Сорри, бро, тут рекурсия, мы прикрываем это лавочку...");
//            TransferCenter.sendAnswerToUser(answersWaitingSending, scriptCommand);
//            DataPacket dataPacket = new DataPacket();
//            dataPacket = new DataPacket(dataPacket.getDatagramChannel(), scriptCommand, dataPacket.getUser());
            answersWaitingSending.add(dataPacket);

//            System.out.println("Сорри, бро, тут рекурсия, мы прикрываем это лавочку...");
        }
        else {
            if(scriptCommand.equals(CommandsData.EXECUTESCRIPT)){
                scriptCommand.addOpeningFile(scriptCommand.getParameter());
            }
            dataPacket = new DataPacket(dataPacket.getDatagramChannel(), scriptCommand, dataPacket.getUser());
            startCommand(/*scriptCommand, transferCenter, otherCommandsData*/ answersWaitingSending, dataPacket);
        }
    }

    /**получает уже запакованную со всеми параметрами команду и запускает её*/
    public void startCommand(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        CommandsData commandsData = dataPacket.getCommandsData();
        String commandName = gettingNormalFormatOfName(commandsData.toString());


        Method[] methods = getClass().getDeclaredMethods();
        for (Method method : methods){
            if(method.getName().equals(commandName)){
                if(commandName.equals("executeScript")){
                    executeScript(answersWaitingSending, dataPacket);
                }
                else {
                    try {
                        method.invoke(this, answersWaitingSending, dataPacket);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**меняет "_a" на "A" и тп и переводит первую букву в верхний регистр*/
    private String gettingNormalFormatOfName(String str){

        boolean normalFormat = false;
        while (!normalFormat){
            normalFormat = true;
            int ind;
            if(str.contains("_")){
                ind = str.indexOf("_");
                str = str.substring(0, ind) + str.substring(ind+1, ind+2).toUpperCase() + str.substring(ind+2);
                normalFormat = false;
            }
        }
        return str;
    }
}
