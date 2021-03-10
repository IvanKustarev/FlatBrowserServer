package L6Server;//import L5.L6Server.InputeOutputeWork.LoadingCollectionFromFile;
import CommonClasses.AbstractDataBlock;
import CommonClasses.DataBlock;
import L6Server.InputeOutputeWork.*;
//import L6User.L6User.AnswerToServer;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
//    AnswerToServer answerToServer;

    private static FlatCollection flatCollection;
    private static String fileAddress = "NewTest.txt";
    private static boolean isScriptWorkingNow = false;
    private static InetSocketAddress serverSocketAddress = null;

    /**метод отвечает за создание нового пользователя, запуск методов отвечающих за загрузку файла из памяти и запуск метода, получения команды*/
    public static void main(String[] args) throws ParserConfigurationException, IOException, ClassNotFoundException {

        TransferCenter transferCenter = new TransferCenter();
        DataBlock dataBlock = new DataBlock();
        dataBlock.phrase = "ты лох!";
        transferCenter.sendObjectToUser(dataBlock);
        dataBlock = (DataBlock) transferCenter.receiveObjectFromUser();
        System.out.println(dataBlock.phrase);







//        User user = new User();

        fileAddress = gettingAddress(args);
        System.out.println("Файл успешно загружен.");
//        fileAddress = "NewTest.txt";

        loadFile(); //      Загружаем файл из пямяти в коллекцию

        WorkWithUser workWithUser = new WorkWithUser(flatCollection, fileAddress);
        workWithUser.startWorkWithUser();


    }

    /**Загружает данные из файла в памяти в объект класса File. Проверяет на наличие ошибок доступа и прав к файлу в памяти*/
    public static Document startLoading() throws ParserConfigurationException {
        LoadingCollectionFromFile input = new LoadingCollectionFromFile();
        return input.load(fileAddress);
    }


    private static void loadFile() throws ParserConfigurationException {
        LoadingCollectionFromFile input = new LoadingCollectionFromFile();
//        flatCollection = input.convert(startLoading());
        flatCollection = input.convert(startLoading());
    }

    public static String gettingAddress(String[] args){

        String fileAddress = null;

        if(args.length == 0) {
            while (fileAddress == null){
                fileAddress = printAndRead("Хотелось бы получить адресс файла, а не пустоту:");
            }
        }
        else {
            fileAddress = args[0];
        }

        File file = new File(checkingFileAvailability(fileAddress));
        if(!(file.canRead() & file.canWrite())){
            if(!file.canRead()){
                System.out.println("У файла нет права на чтение!");
            }
            if(!file.canWrite()){
                System.out.println("У файла нет права на запись!");
            }
            String[] arr = new String[1];
            arr[0] = printAndRead("Нужен другой файл:");
            fileAddress = gettingAddress(arr);
        }
//        System.out.println("Файл успешно загружен.");
        return fileAddress;
    }

    public static String printAndRead(String parameter){
        System.out.println(parameter);
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        return str;
    }

    public static String checkingFileAvailability(String parameter){
        String fileAddress;
        try {
//            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(parameter));
            File file = new File(parameter);
            fileAddress = parameter;
        }catch (Exception e){
            System.out.println("Такого файла не существует.");
            fileAddress = checkingFileAvailability(printAndRead("Необходимо корректное имя файла:"));
        }
        return fileAddress;
    }

}
