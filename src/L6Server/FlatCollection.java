package L6Server;

import CommonClasses.*;
import L6Server.ApartmentDescription.ComparisonOfAttractiveness;
import L6Server.ApartmentDescription.Transport;
//import L6Server.Commands.CommandsData;
import L6Server.InputeOutputeWork.UpLoadingCollectionToFile;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class FlatCollection {

    private ArrayDeque<Flat> setOfFlats = new ArrayDeque<>();
    private Date dateOfInitialization;

    public FlatCollection() {
        this.dateOfInitialization = new Date();
    }

    public int getCollectionSize(){
        return setOfFlats.size();
    }

    public Iterator getIterator(){
        return setOfFlats.iterator();
    }

    public void sortByAttractive(){
        PriorityQueue<Flat> priorityQueue = new PriorityQueue<>();
        for (Flat flat : setOfFlats){
            priorityQueue.add(flat);
        }
        setOfFlats.clear();
        for (Flat flat : priorityQueue){
            setOfFlats.add(flat);
        }
    }

    private Long createId(){

        Random random = new Random();
        Boolean repeat = true;
        long id = 0;
        while (repeat){
            repeat = false;
            Iterator iterator = setOfFlats.iterator();
            id = random.nextLong();
            for (int i = 0; i< setOfFlats.size();i++){
                if(((Flat)iterator.next()).getId() == id){
                    repeat = true;
                }
            }
        }
        return Long.valueOf(id);
    }

    public void getInfo(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        System.out.println( "Тип: " + getClass().getTypeName() + "\n" +
                "Дата инициализации: " + dateOfInitialization + "\n" +
                "Количество элементов: " + setOfFlats.size());
//        transferCenter.sendAnswerToUser(new DataBlock(){
//            public String phrase = "Тип: " + getClass().getTypeName() + "\n" +
//                "Дата инициализации: " + dateOfInitialization + "\n" +
//                "Количество элементов: " + setOfFlats.size();
//
//            @Override
//            public boolean StartProcessingCommand(AbstractDataBlock answer){
//                System.out.println(phrase);
//                return  true;
//            };
//        });
    }

    public void show(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){

        sortByAttractive();
        Iterator iterator = setOfFlats.iterator();

        if(iterator.hasNext()) {
            Flat[] flatsArr = new Flat[setOfFlats.size()];
            for (int i = 0; i < setOfFlats.size(); i++) {

//                Flat flat = (Flat) iterator.next();
//                flat.show();
                flatsArr[i] = (Flat) iterator.next();
            }
//            transferCenter.sendAnswerToUser(new DataBlock(){
//                private Flat[] flats = flatsArr;
//                @Override
//                public boolean StartProcessingCommand(AbstractDataBlock answer){
//                    for (int i = 0; i < flats.length; i++) {
//                        Flat flat = (Flat) iterator.next();
//                        flat.show();
//                    }
//                    return  true;
//                };
//            });
        }
        else {
//            System.out.println("Коллекция пустая!");
//            transferCenter.sendAnswerToUser(new DataBlock(){
//                public String phrase = "Коллекция пустая!";
//
//                @Override
//                public boolean StartProcessingCommand(AbstractDataBlock answer){
//                    System.out.println(phrase);
//                    return  false;
//                };
//            });
        }
    }

    //рализация команды
    public void add(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        long flatID = createId();
        Flat flat = Flat.createFlat(flatID);
        setOfFlats.add(flat);
    }

    //используется при загрузке данных из файла (не является командой)
    public void add(Flat flat){
        setOfFlats.add(flat);
    }

    public void clear(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        setOfFlats.clear();
        System.out.println("Коллекция очищена!");
    }

    public void save(FlatCollection flatCollection, String fileAddress) {
        UpLoadingCollectionToFile output = new UpLoadingCollectionToFile();
        try {
            output.save(output.convert(flatCollection), fileAddress);
            System.out.println("Коллекция успешно сохранена");
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void removeHead(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        Iterator iterator = setOfFlats.iterator();
        Flat flat;
        if(iterator.hasNext()){
            flat = (Flat)iterator.next();
            flat.show();
            iterator.remove();
        }
        else {
            System.out.println("Коллекция пустая!");
        }
    }

    public void sumOfNumberOfRooms(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        Iterator iterator = setOfFlats.iterator();
        BigInteger numberOfRooms = BigInteger.valueOf(0);
        if(iterator.hasNext()){
            while (iterator.hasNext()){
                Flat flat = (Flat) iterator.next();
                try {
//                    System.out.println(flat.getNumberOfRooms());
//                    numberOfRooms
                    numberOfRooms = numberOfRooms.add(BigInteger.valueOf(flat.getNumberOfRooms()));
                }catch (Exception e){
                    System.out.println("Общее число комнат слишком большое! Перполнен BigInteger!");
                }

            }
            System.out.println("Общее число комнат во всех квартирах: " + numberOfRooms);
        }
        else {
            System.out.println("В коллекции не квартир!");
        }
    }

    public void addIfMin(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        Iterator iterator = getIterator();
        long min;
        min = Long.MAX_VALUE;
        if(iterator.hasNext()){
            while (iterator.hasNext()){
                long flatAttractive = ComparisonOfAttractiveness.compare((Flat) iterator.next());
                if(min > flatAttractive){
                    min = flatAttractive;
                }
            }
            Flat newFlat = Flat.createFlat(createId());
            if(ComparisonOfAttractiveness.compare(newFlat) < min){
                setOfFlats.add(newFlat);
            }
        }
        else {
            System.out.println("Пустая коллекция!");
        }
    }

    public void updateId(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        long id = Long.valueOf(command.getParameter());
        Iterator iterator = setOfFlats.iterator();
        Long flatId = null;
        while (iterator.hasNext()){
            long foundedFlatId= ((Flat)iterator.next()).getId();
            if(foundedFlatId == id){
                flatId = foundedFlatId;
                iterator.remove();
            }
        }
        if(flatId == null){
            System.out.println("Неправильно введён ID!\nВведите ID занова.");
            command.setParameter(informationGetter(command));
            updateId(command, transferCenter, commandsData);
        }
        else {
            System.out.println("Приступаем к обновлению параметров файла с ID: " + id);
            add(Flat.createFlat(id));
            System.out.println("Файл обновлён!");
        }
    }

    public void removeById(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        long id = Long.valueOf(command.getParameter());
        Iterator iterator = setOfFlats.iterator();
        boolean nonElement = true;
        while (iterator.hasNext()){
            if(((Flat)iterator.next()).getId() == id){
                iterator.remove();
                nonElement = false;
                System.out.println("Элемент удалён.");
            }
        }
        if(nonElement){
            System.out.println("Квартиры с таким ID не существует!\nПопробуйте ввести ID занова.");
            command.setParameter(informationGetter(command));
            removeById(command,transferCenter, commandsData);
        }
    }

    public void removeLower(CommandsData command, TransferCenter transferCenter, CommandsData commandsData) {
        System.out.println("Введите элемент для спавнения.");
        Flat flatForeCompare = Flat.createFlat(Long.valueOf(0));
        long compareFlatAttractive = ComparisonOfAttractiveness.compare(flatForeCompare);
        Iterator iterator = setOfFlats.iterator();
        boolean nonElement = true;

        if (iterator.hasNext()) {
            while (iterator.hasNext()) {
                if (ComparisonOfAttractiveness.compare((Flat) iterator.next()) < compareFlatAttractive) {
                    iterator.remove();
                    nonElement = false;
                }
            }
            if(nonElement){
                System.out.println("Нет подходящих для удаления элементов");
            }
            else {
                System.out.println("Подходящие элементы были удалены.");
            }
        }
        else {
            System.out.println("Коллекция пустая!");
        }
    }

    public void printFieldAscendingNumberOfRooms(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){

        Flat[] flats = new Flat[0];

        try {
            Object obj[] = setOfFlats.toArray();
            flats = new Flat[obj.length];
            for(int i =0; i< obj.length;i++){
                flats[i] = (Flat) obj[i];
            }
        }catch (Exception e){
            System.out.println("Проблемма с загрузкой коллекции в массив в методе printFieldAscendingNumberOfRooms");
        }

        boolean repeat = true;
        if(flats.length > 1) {
            while (repeat) {
                repeat = false;
                for (int i = 1; i < flats.length; i++) {
                    if (flats[i].getNumberOfRooms() < flats[i-1].getNumberOfRooms()){
                        Flat flat = flats[i];
                        flats[i] = flats[i-1];
                        flats[i-1] = flat;
                        repeat = true;
                    }
                }
            }
            System.out.println("Выводим элементы в порядке возрастания количества комнат:");
            for (int i =0;i<flats.length;i++){
                System.out.println("ID - " + flats[i].getId() + " numberOfRooms - " + flats[i].getNumberOfRooms());
            }
        }
        else {
            if(flats.length == 1){
                System.out.println("В коллекции содержится всего один элемент: ID - " + flats[0].getId() + " numberOfRooms - " + flats[0].getNumberOfRooms());
            }
            else {
                System.out.println("Коллекция пустая!");
            }
        }

    }

    public void filterLessThanTransport(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){

        Transport transport;
        try {
            transport = Transport.valueOf(command.getParameter());
        }catch (Exception e){
            System.out.println("Такого варианта транспора не существует!");
            transport = createTransport(command);
        }

        boolean wasPrinted = false;
        Iterator iterator = setOfFlats.iterator();
        if(iterator.hasNext()){
            while (iterator.hasNext()){
                Flat flat = (Flat)iterator.next();
                if(flat.getTransport() != null){
                    if(flat.getTransport().levelAttractive() < transport.levelAttractive()){
                        flat.show();
                        wasPrinted = true;
                    }
                }
            }
            if(!wasPrinted){
                System.out.println("Нет ни одного подходящего элемента в коллекции!");
            }
        }
        else {
            System.out.println("В коллекции нет элементов для сравнения!");
        }

    }

    public Transport createTransport(CommandsData commandsData){
//        Scanner input = new Scanner(System.in);
        System.out.println("Транспортные маршруты,проходящие у дома, задаётся одной из следующих констант:");
        Transport[] transports = Transport.values();
        Transport transport;
        for (int i =0;i<transports.length;i++){
            System.out.print(transports[i].name() + " ");
        }
        System.out.println("Нужно выбрать одну из них");
        String str = informationGetter(commandsData);
        if(str.length() == 0){
            System.out.println("Это поле не может быть пустым!");
            transport = createTransport(commandsData);
        }
        else {
            try {
                Transport transport1 = Transport.valueOf(str);
                transport = transport1;
            } catch (Exception e) {
                System.out.println("Некорректный ввод данных!\nВведите поле занова");
                transport = createTransport(commandsData);
            }
        }
        return transport;
    }

    private String informationGetter(CommandsData command){
        if(command.getCreator().equals(Creator.USER)){
            return (new Scanner(System.in).nextLine());
        }
        else {
            try {
                return command.getBufferedReader().readLine();
            } catch (IOException e) {
                System.out.println("Сканер не смог считать стороку из файла");
            }
            return null;
        }
    }
}