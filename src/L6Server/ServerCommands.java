package L6Server;

import java.util.Scanner;

public class ServerCommands extends Thread{
    public boolean exit = false;
    private FlatCollection flatCollection;
    private String fileAddress;

    public ServerCommands(FlatCollection flatCollection, String fileAddress){
        this.flatCollection = flatCollection;
        this.fileAddress = fileAddress;
    }

    @Override
    public void run(){

        Scanner scanner = new Scanner(System.in);
        String str;
        System.out.println("Введите save или exit:");

        while (!exit){
            str = scanner.nextLine();
            if(str.equals("save")){
                System.out.println("Сохраняю коллекцию в файл ...");
                flatCollection.save(flatCollection, fileAddress);
            }
            if(str.equals("exit")){
                System.out.println("Завершаю работу сервера!");
                System.exit(0);
            }
        }
    }
}
