package L6Server.Commands;


import CommonClasses.CommandsData;
import CommonClasses.Creator;
import CommonClasses.DataBlock;
import L6Server.FlatCollection;
import L6Server.TransferCenter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Stack;

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
//
//    AddCommand addCommand = new AddCommand(flatCollection);
//    AddIfMinCommand addIfMinCommand = new AddIfMinCommand(flatCollection);
//    ClearCommand clearCommand = new ClearCommand(flatCollection);
//    //    ExecuteScriptCommand executeScriptCommand;
//    FilterLessThanTransportCommand filterLessThanTransportCommand = new FilterLessThanTransportCommand(flatCollection);
//    HelpCommand helpCommand = new HelpCommand();
//    InfoCommand infoCommand = new InfoCommand(flatCollection);
//    PrintFieldAscendingNumberOfRoomsCommand printFieldAscendingNumberOfRoomsCommand = new PrintFieldAscendingNumberOfRoomsCommand(flatCollection);
//    RemoveByIdCommand removeByIdCommand = new RemoveByIdCommand(flatCollection);
//    RemoveHeadCommand removeHeadCommand = new RemoveHeadCommand(flatCollection);
//    RemoveLowerCommand removeLowerCommand = new RemoveLowerCommand(flatCollection);


//    SaveCommand saveCommand;
//    ShowCommand showCommand = new ShowCommand(flatCollection);
//    SumOfNumberOfRoomsCommand sumOfNumberOfRoomsCommand = new SumOfNumberOfRoomsCommand(flatCollection);
//    UpdateIdCommand updateIdCommand = new UpdateIdCommand(flatCollection);

    public CommandCenter(String fileAddress, AddCommand addCommand, AddIfMinCommand addIfMinCommand, ClearCommand clearCommand, ExecuteScriptCommand executeScriptCommand, FilterLessThanTransportCommand filterLessThanTransportCommand, HelpCommand helpCommand, InfoCommand infoCommand, PrintFieldAscendingNumberOfRoomsCommand printFieldAscendingNumberOfRoomsCommand, RemoveByIdCommand removeByIdCommand, RemoveHeadCommand removeHeadCommand, RemoveLowerCommand removeLowerCommand, /*SaveCommand saveCommand,*/ ShowCommand showCommand, SumOfNumberOfRoomsCommand sumOfNumberOfRoomsCommand, UpdateIdCommand updateId) {
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

    public void add(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        addCommand.execute(command, transferCenter, commandsData);
    }

    public void addIfMin(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        addIfMinCommand.execute(command, transferCenter, commandsData);
    }

    public void clear(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        clearCommand.execute(command, transferCenter, commandsData);
    }

    public void filterLessThanTransport(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        filterLessThanTransportCommand.execute(command, transferCenter, commandsData);
    }

    public void executeScript(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        executeScriptCommand.execute(command, transferCenter, commandsData);
    }

    public void help(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        helpCommand.execute(command, transferCenter, commandsData);
    }

    public void info(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        infoCommand.execute(command, transferCenter, commandsData);
    }

    public void printFieldAscendingNumberOfRooms(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        printFieldAscendingNumberOfRoomsCommand.execute(command, transferCenter, commandsData);
    }

    public void removeById(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        removeByIdCommand.execute(command, transferCenter, commandsData);
    }

    public void removeHead(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        removeHeadCommand.execute(command, transferCenter, commandsData);
    }

    public void removeLower(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        removeLowerCommand.execute(command, transferCenter, commandsData);
    }

//    public void save(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
//        saveCommand.execute(command, transferCenter, commandsData);
//    }

    public void show(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        showCommand.execute(command, transferCenter, commandsData);
    }

    public void sumOfNumberOfRooms(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        sumOfNumberOfRoomsCommand.execute(command, transferCenter, commandsData);
    }

    public void update(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        updateIdCommand.execute(command, transferCenter, commandsData);
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
    private CommandsData packingCommandInCommandsObject(String command, Boolean commandWithParameter, Creator creator){
        CommandsData commandObject = null;


        CommandsData[] commands = CommandsData.values();
        for (int i =0; i<commands.length; i++){
            if(command.contains(commands[i].toString())){
                commandObject = commands[i];
            }
        }

//        System.out.println("====================");
//        commandObject.getFlat().show();
//        System.out.println("====================");

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

    public void processingAndStartUserCommand(CommandsData commandsData, TransferCenter transferCenter){
        CommandsData userCommand = packingCommandInCommandsObject(commandsData.toString() + " " + commandsData.getParameter(), isCommandWithParameter(commandsData.toString() + commandsData.getParameter()), Creator.USER);
        if(userCommand.equals(CommandsData.EXECUTESCRIPT)){
            //дополнительные параметры для этой команды
            try {
                //создаем буффер для чтения файла со скриптом
                BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(userCommand.getParameter())));
                userCommand.setBufferedReader(bufferedReader);

                //создаётся стек для открытых файлов (ловить рекурсию)
                Stack<String> openingFiles = new Stack<>();
                userCommand.setOpeningFiles(openingFiles);
                userCommand.addOpeningFile(userCommand.getParameter());
            } catch (FileNotFoundException e) {
                System.out.println("Проблемы с загрузкой файла со скриптом!");
            }
        }
        startCommand(userCommand, transferCenter, commandsData);
    }

    /**запуск команды осуществляемый user-ом*/
    public void processingAndStartScriptCommand(String command, CommandsData commandsData, BufferedReader bufferedReader, TransferCenter transferCenter, CommandsData otherCommandsData){
        CommandsData scriptCommand = packingCommandInCommandsObject(command, isCommandWithParameter(command), Creator.SCRIPT);

        boolean recursWasStarted = false;
        scriptCommand.setBufferedReader(bufferedReader);
        if(scriptCommand.equals(CommandsData.EXECUTESCRIPT)){
            //дополнительные параметры для этой команды

            String[] nameOfOpenedFiles = new String[commandsData.getOpeningFiles().toArray().length];
            for(int i =0; i < commandsData.getOpeningFiles().toArray().length; i++){
                nameOfOpenedFiles[i] = (String) (commandsData.getOpeningFiles().toArray())[i];
            }

            for (String nameOfOpenedFile : nameOfOpenedFiles){
                if(nameOfOpenedFile.equals(commandsData.getParameter())){
                    recursWasStarted = true;
                }
            }
        }

        if(recursWasStarted){
            DataBlock dataBlock = new DataBlock();
            dataBlock.setAllRight(true);
            dataBlock.setPhrase("Сорри, бро, тут рекурсия, мы прикрываем это лавочку...");
            transferCenter.sendObjectToUser(dataBlock);

//            System.out.println("Сорри, бро, тут рекурсия, мы прикрываем это лавочку...");
        }
        else {
            if(scriptCommand.equals(CommandsData.EXECUTESCRIPT)){
                scriptCommand.addOpeningFile(scriptCommand.getParameter());
            }
            startCommand(scriptCommand, transferCenter, otherCommandsData);
        }
    }

    /**получает уже запакованную со всеми параметрами команду и запускает её*/
    public void startCommand(CommandsData commandObject, TransferCenter transferCenter, CommandsData commandsData){
        String commandName = gettingNormalFormatOfName(commandObject.toString());
//        System.out.println(commandName);

        Method[] methods = getClass().getDeclaredMethods();
        for (Method method : methods){
            if(method.getName().equals(commandName)){
                if(commandName.equals("executeScript")){
                    executeScript(commandObject, transferCenter, commandsData);
                }
                else {
                    try {
                        method.invoke(this, commandObject, transferCenter, commandsData);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

//                try {
//                    method.invoke(this, commandObject, transferCenter, commandsData);
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                }

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
