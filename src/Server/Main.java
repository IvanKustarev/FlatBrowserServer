package Server;//import L5.L6Server.InputeOutputeWork.LoadingCollectionFromFile;
//import CommonClasses.ConnectionSupport.ConnectionSupporter;
//import CommonClasses.DataBlock;
import CommonClasses.User;
import Server.DBWork.*;
import Server.FlatCollectionWorkers.FlatCollection;
import Server.OptionalThrows.ServerCommands;
import Server.InputeOutputeWork.*;
import org.w3c.dom.Document;
//import L6User.L6User.AnswerToServer;

        import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
        import java.io.IOException;
import java.net.*;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
//    AnswerToServer answerToServer;

    private static FlatCollection flatCollection;
    private static String fileAddress = "NewTest.txt";
    private static boolean isScriptWorkingNow = false;
    private static InetSocketAddress serverSocketAddress = null;
    private static ResourceBundle[] resourceBundles;

    static Logger LOGGER;
    static {
        try {
            FileInputStream ins = new FileInputStream("logConfig");
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(Main.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**метод отвечает за создание нового пользователя, запуск методов отвечающих за загрузку файла из памяти и запуск метода, получения команды*/
    public static void main(String[] args) throws ParserConfigurationException, IOException, ClassNotFoundException {

//        createResources();

        Class.forName("org.postgresql.Driver");

//        loadFile();

        flatCollection = new FlatCollection();
        Connector connector = null;
        try {
            connector = new Connector();
        } catch (SQLException throwables) {
            System.out.println("Проблемы с подключением к базе данных. Завершаем работу!");
            System.exit(1);
        }
        DBWorking dbWorking = new DBWorker(flatCollection, connector.getConnection());
        dbWorking.load();


//        =======================================================

        WorkWithUser workWithUser;
        TransferCenter transferCenter = null;
        try {
            LOGGER.log(Level.INFO, "Создание workWithUser и Transfer center");
            workWithUser = new WorkWithUser(flatCollection, fileAddress, dbWorking);
            transferCenter = new TransferCenter(workWithUser);
        }catch (Exception e){
            LOGGER.log(Level.WARNING, "Проблем с созданием workWithUser и Transfer center ", e);
            System.exit(1);
        }

        try {
            LOGGER.log(Level.INFO, "Начало процесса проверки команд от сервера (exit и save)");
            startControllingServerCommands();
        }catch (Exception e){
            LOGGER.log(Level.WARNING, "Проблема с началом проверки команд от сервера ", e);
        }

        try {
            LOGGER.log(Level.INFO, "Начало приёма сервером пользовательских команд");
            transferCenter.requestsProcessing();
        }catch (Exception e){
            LOGGER.log(Level.INFO, "Проблема с приёмом пользовательских команд ", e);
        }
    }

//    private static void createResources(){
//        ResourceBundle bundleRu = ResourceBundle.getBundle("Resources.Resources_ru", new Locale("ru", "RU"));
//        ResourceBundle bundleDE = ResourceBundle.getBundle("Resources.Resources_de", new Locale("de", "GR"));
//
//        resourceBundles = new ResourceBundle[]{bundleRu, bundleDE};
//    }

    public static ResourceBundle getResourceByName(String resourceName){
        if(resourceName != null){
            if(resourceName.equals("Resources.Resources_ru")){
                return ResourceBundle.getBundle("Resources.Resources_ru");
            }
            if(resourceName.equals("Resources.Resources_de")){
                return ResourceBundle.getBundle("Resources.Resources_de");
            }
            if(resourceName.equals("Resources.Resources_lv")){
                return ResourceBundle.getBundle("Resources.Resources_lv");
            }
            if(resourceName.equals("Resources.Resources_es")){
                return ResourceBundle.getBundle("Resources.Resources_es");
            }


        }
        return ResourceBundle.getBundle("Resources.Resources_ru");
    }

    public static Locale getLocaleByResourceName(String resourceName){
        if(resourceName != null) {
            if (resourceName.equals("Resources.Resources_ru")) {
                return new Locale("ru", "Ru");
            }
            if (resourceName.equals("Resources.Resources_de")) {
                return new Locale("de", "GR");
            }
            if (resourceName.equals("Resources.Resources_lv")) {
                return new Locale("lv", "LV");
            }
            if (resourceName.equals("Resources.Resources_es")) {
                return new Locale("es", "ES");
            }
        }
        return new Locale("ru", "Ru");
    }

    private static void startControllingServerCommands(){
        ServerCommands serverCommands = new ServerCommands(flatCollection, fileAddress);
        serverCommands.start();
    }

    /**Загружает данные из файла в памяти в объект класса File. Проверяет на наличие ошибок доступа и прав к файлу в памяти*/
    public static Document startLoading() throws ParserConfigurationException {
        LoadingCollectionFromFile input = new LoadingCollectionFromFile();
        return input.load(fileAddress);
    }


    private static void loadFile() throws ParserConfigurationException {
        LoadingCollectionFromFile input = new LoadingCollectionFromFile();
//        flatCollection = input.convert(startLoading());
//        LoadingCollectionFromFile input = new LoadingCollectionFromFile();
//        return input.load(fileAddress);
        flatCollection = input.convert(input.load(fileAddress));
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
