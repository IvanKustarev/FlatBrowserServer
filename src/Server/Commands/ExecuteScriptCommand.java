package Server.Commands;

//import L5.ExecuteScriptCommandRealization;
//import L5.FlatCollection;

import CommonClasses.CommandsData;
//import L6Server.CommandUnits.ExecuteScriptCommandRealization;
import Server.CommandUnits.ExecuteScriptCommandRealization;
import Server.DataPacket;
import Server.FlatCollectionWorkers.FlatCollection;

import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ExecuteScriptCommand implements Command{

    FlatCollection flatCollection;
    String fileAddress; //адресс файла для сохранения коллекции
    public ExecuteScriptCommand(FlatCollection flatCollection, String fileAddress){
        this.flatCollection = flatCollection;
        this.fileAddress = fileAddress;
    }

    @Override
    public String toString(){
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме";
    }




    public void execute(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        (new ExecuteScriptCommandRealization()).startScript(flatCollection, fileAddress, answersWaitingSending, dataPacket);
    }
}
