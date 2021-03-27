package L6Server;

//import L.Commands.*;
//import L5.Commands.CommandsData;

import CommonClasses.CommandsData;
//import CommonClasses.DataBlock;
import CommonClasses.Creator;
import L6Server.Commands.*;

import java.io.*;
import java.nio.channels.DatagramChannel;

/**новый объект этого класса создаётся, когда вводится команда execute_script. Аналогичен по работе user-у, но поток ввода меняется на поток из файла
 * + команды создвются с дополнительным параметром bufferReader для заполнения этого поля в CommandsData объектах создаваемых коман,
 * чтобы чтение продолжалось из одного и того же файла с без остановки
 */
public class ExecuteScriptCommandRealization {

    /**метод запускаемый при вводе команды execute_script*/
    public void startScript(FlatCollection flatCollection, String fileAddress, CommandsData commandsData, DatagramChannel datagramChannel){
        CommandCenter cc = new CommandCenter(fileAddress, new AddCommand(flatCollection), new AddIfMinCommand(flatCollection), new ClearCommand(flatCollection), new ExecuteScriptCommand(flatCollection, fileAddress),
                new FilterLessThanTransportCommand(flatCollection), new HelpCommand(), new InfoCommand(flatCollection), new PrintFieldAscendingNumberOfRoomsCommand(flatCollection),
                new RemoveByIdCommand(flatCollection), new RemoveHeadCommand(flatCollection), new RemoveLowerCommand(flatCollection), /*new SaveCommand(flatCollection, fileAddress),*/
                new ShowCommand(flatCollection), new SumOfNumberOfRoomsCommand(flatCollection), new UpdateIdCommand(flatCollection));


        boolean exit = false;
        String line = null;
        BufferedReader bufferedReader = null;
        commandsData.setCreator(Creator.SCRIPT);
        try {
            bufferedReader = new BufferedReader(new FileReader(new File(commandsData.getParameter())));
        }catch (FileNotFoundException e){
            commandsData.setPhrase("Проблемва с загрузкой файла " + commandsData.getParameter());
            commandsData.setCommandEnded(true);
            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
            return;
        }

        try {
            line = bufferedReader.readLine();
        } catch (Exception e) {
//            System.out.println("Проблема с загрузкой скрипта из файла в первой же строке!");
            commandsData.setPhrase("Проблема с загрузкой скрипта из файла в первой же строке!" + commandsData.getParameter());
            commandsData.setCommandEnded(true);
            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
            return;
        }

//        System.out.println("ttt");

        while ((line != null) & (!exit)){
//        System.out.println(line);
            String command = line;

                if(command.equals("")){ }
                else {
                    CommandsData commandVariation = cc.whatTheCommand(command);
                    if(commandVariation == null){
                        System.out.println("Такой команды не существует: " + line);
                    }
                    else {
                        if(commandVariation.equals(CommandsData.EXIT)){
//                            exit = true;
                            System.out.println("В файле была найдена команда exit. Программа завершается!");
                            System.exit(0);
                        }
                        else {
//                            System.out.println("Start new command");
//                            System.out.println(line);
                            cc.processingAndStartScriptCommand(commandsData, bufferedReader, datagramChannel, command);
                        }
                    }
                }
            try {
                line = bufferedReader.readLine();
//                System.out.println(line);
            } catch (IOException e) {
//                line = null;
//                System.out.println("Проблема с загрузкой скрипта из файла. Хотя несколько строк уже было прочитано!");
                commandsData.setPhrase("Проблема с загрузкой скрипта из файла. Хотя несколько строк уже было прочитано!" + commandsData.getParameter());
                commandsData.setCommandEnded(true);
                TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                return;
            }
        }
        commandsData.getOpeningFiles().pop();
        //убирает последнее добавленне в стек имя файла тк он полностью прочитан
//        System.out.println("end File");

        if(commandsData.getOpeningFiles().size() == 0){
            System.out.println("Скрипт завершён!");
            commandsData.setPhrase("Скрипт завершён!");
            commandsData.setCommandEnded(true);
            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
//            DataBlock dataBlock = new DataBlock();
//            dataBlock.setPhrase("Скрипт завершён!");
//            dataBlock.setAllRight(true);
//            transferCenter.sendObjectToUser(dataBlock);
        }
    }
}























//    public static boolean scriptWorkingNow = false;
//
//    public static int counterOfLinesInFile = 0;
////    public static int changeOfLinesInFile = 0;
//
//    public static void scriptStartWorking(){
//        scriptWorkingNow = true;
//    }
//    public static void scriptStopWorking(){
//        scriptWorkingNow = false;
//    }
//
//    public static boolean isScriptWorkingNow() {
//        return scriptWorkingNow;
//    }
//
////    public static String scriptAddress = null;
//    public static Stack<ReadingFiles> stackOfAddresses = new Stack<>();
//
//    public static void setScriptAddress(String scriptAddress, int line, int change) {
//        stackOfAddresses.push(new ReadingFiles(scriptAddress, line, change));
//    }
//
//    public static ReadingFiles getScriptAddress() {
//        return stackOfAddresses.peek();
//    }
//    public static void deleteLastScriptAddress(){
//        stackOfAddresses.pop();
//    }
//
//    public void startScript(String fileAddress, L6Server.FlatCollection flatCollection){
//
//        try {
//            File file = new File(fileAddress);
//            FileReader fileReader = new FileReader(file);
//            User user = new User();
//            boolean endOfProgram = false;
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//
//            CommandCenter cc = new CommandCenter(new AddCommand(flatCollection), new AddIfMinCommand(flatCollection), new ClearCommand(flatCollection), new ExecuteScriptCommand(flatCollection),
//                    new FilterLessThanTransportCommand(flatCollection), new HelpCommand(), new InfoCommand(flatCollection), new PrintFieldAscendingNumberOfRoomsCommand(flatCollection),
//                    new RemoveByIdCommand(flatCollection), new RemoveHeadCommand(flatCollection), new RemoveLowerCommand(flatCollection), new SaveCommand(flatCollection, fileAddress),
//                    new ShowCommand(flatCollection), new SumOfNumberOfRoomsCommand(flatCollection), new UpdateIdCommand(flatCollection));
//
//
//            String line = bufferedReader.readLine();
//
//            while ((line != null) & (!endOfProgram)){
//
//
//                counterOfLinesInFile++;
//
//                L6Server.ExecuteScriptCommandRealization.setScriptAddress(fileAddress, counterOfLinesInFile, 0);
//                endOfProgram = user.processingTheCommand(line, cc);
//                for(int i=0;i< getScriptAddress().changeOfLinesInFile;i++){
//                    bufferedReader.readLine();
//                }
//                L6Server.ExecuteScriptCommandRealization.deleteLastScriptAddress();
//
//                line = bufferedReader.readLine();
//            }
//
//            if(endOfProgram){
//                System.out.println("Программа завершается тк в файле была команда exit!");
//                System.exit(0);
//            }
//
//        }  catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static class RecursFinder {
//
//        private static ArrayList<String> filesWithoutEnd = new ArrayList<>();
//
//        public static boolean wasRecursStopped(){
//            return !filesWithoutEnd.isEmpty();
//        }
//
//        public static boolean wasRecursStart(String fileAddress){
//            if(fileAddress.equals("")){
//                return false;
//            }
//            else{
//                if(filesWithoutEnd.contains(fileAddress)){
//                    return true;
//                }
//                else {
//                    try {
//                        File file = new File(fileAddress);
//                        FileReader fileReader = new FileReader(file);
//                        BufferedReader bufferedReader = new BufferedReader(fileReader);
//                        String line = new String();
//                        boolean hasExit = false;
//
//                        line = bufferedReader.readLine();
//                        while (line!=null){
//                            if(line.contains("exit")){
//                                hasExit = true;
//                            }
//                            line = bufferedReader.readLine();
//                        }
//                        if(!hasExit){
//                            filesWithoutEnd.add(fileAddress);
//                        }
//
//                    }catch (Exception e){
//                        return false;
//                    }
//                    return false;
//                }
//            }
//        }
//
//        public static void clearRecurs(){
//            filesWithoutEnd.clear();
//        }
//    }
//}
