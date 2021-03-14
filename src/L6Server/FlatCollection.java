package L6Server;

import CommonClasses.*;
import CommonClasses.ApartmentDescription.ComparisonOfAttractiveness;
import CommonClasses.ApartmentDescription.Transport;
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

        DataBlock dataBlock = new DataBlock();
        dataBlock.phrase = "Тип: " + getClass().getTypeName() + "\n" +
                "Дата инициализации: " + dateOfInitialization + "\n" +
                "Количество элементов: " + setOfFlats.size();
        if(command.getCreator().equals(Creator.USER)){
            dataBlock.setAllRight(true);
            transferCenter.sendObjectToUser(dataBlock);
        }
        else {
            dataBlock.setAllRight(false);
            transferCenter.sendObjectToUser(dataBlock);
            transferCenter.receiveObjectFromUser();
        }
    }

    public void show(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){


        Flat[] flats = new Flat[setOfFlats.toArray().length];
        Iterator iterator = setOfFlats.iterator();
        int i = 0;
        while (iterator.hasNext()){
            flats[i] = (Flat)iterator.next();
            i++;
        }

        SortFlatArrBySize sortFlatArrBySize = new SortFlatArrBySize();

        DataBlock dataBlock = new DataBlock();

        dataBlock.setFlats(sortFlatArrBySize.startSorting(flats));
        dataBlock.setUserNeedToShowFlatArr(true);


        if(dataBlock.getFlats().length == 0){
            dataBlock.setPhrase("Коллекция пустая!");
        }

        if(command.getCreator().equals(Creator.USER))
        {
            dataBlock.setAllRight(true);
            transferCenter.sendObjectToUser(dataBlock);
        }
        else {
            dataBlock.setAllRight(false);
            transferCenter.sendObjectToUser(dataBlock);
            transferCenter.receiveObjectFromUser();
        }

//        sortByAttractive();
//        Iterator iterator = setOfFlats.iterator();



//        if(iterator.hasNext()) {
//            Flat[] flatsArr = new Flat[setOfFlats.size()];
//            for (int i = 0; i < setOfFlats.size(); i++) {
//
////                Flat flat = (Flat) iterator.next();
////                flat.show();
//                flatsArr[i] = (Flat) iterator.next();
//            }
//
//
//
////            transferCenter.sendAnswerToUser(new DataBlock(){
////                private Flat[] flats = flatsArr;
////                @Override
////                public boolean StartProcessingCommand(AbstractDataBlock answer){
////                    for (int i = 0; i < flats.length; i++) {
////                        Flat flat = (Flat) iterator.next();
////                        flat.show();
////                    }
////                    return  true;
////                };
////            });
//        }
//        else {
////            System.out.println("Коллекция пустая!");
////            transferCenter.sendAnswerToUser(new DataBlock(){
////                public String phrase = "Коллекция пустая!";
////
////                @Override
////                public boolean StartProcessingCommand(AbstractDataBlock answer){
////                    System.out.println(phrase);
////                    return  false;
////                };
////            });
//        }
    }

    //рализация команды
    public void add(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        DataBlock dataBlock = new DataBlock();
        if(command.getCreator().equals(Creator.USER)){
            setOfFlats.add(commandsData.getFlat());
            dataBlock.setPhrase("Объект добавлен в коллекцию!");
            dataBlock.setAllRight(true);
            transferCenter.sendObjectToUser(dataBlock);
        }
        else {
//            System.out.println("aaaaaaaa");
            long flatID = createId();
            Flat flat = FlatCreatorForScript.createFlat(command, flatID);
            setOfFlats.add(flat);
            dataBlock.setPhrase("Объект добавлен в коллекцию!");
            dataBlock.setAllRight(false);
            transferCenter.sendObjectToUser(dataBlock);
            transferCenter.receiveObjectFromUser();
        }
    }

    //используется при загрузке данных из файла (не является командой)
    public void add(Flat flat){
        setOfFlats.add(flat);
    }

    public void clear(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){

        setOfFlats.clear();
//        System.out.println("Коллекция очищена!");
        DataBlock dataBlock = new DataBlock();
        dataBlock.setPhrase("Коллекция очищена!");
        if(command.getCreator().equals(Creator.USER)){
            dataBlock.setAllRight(true);
            transferCenter.sendObjectToUser(dataBlock);
        }
        else {
            dataBlock.setAllRight(false);
            transferCenter.sendObjectToUser(dataBlock);
            transferCenter.receiveObjectFromUser();
        }
    }

    public void save(FlatCollection flatCollection, String fileAddress) {
        UpLoadingCollectionToFile output = new UpLoadingCollectionToFile();
        try {
            output.save(output.convert(flatCollection), fileAddress);
            System.out.println("Коллекция успешно сохранена!");
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void removeHead(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        Iterator iterator = setOfFlats.iterator();
        Flat flat;
        DataBlock dataBlock = new DataBlock();

        Flat[] flats = new Flat[setOfFlats.size()];
        for(int i =0;i<setOfFlats.size();i++){
            flats[i] = (Flat) setOfFlats.toArray()[i];
        }

        (new SortFlatArrBySize()).startSorting(flats);

        if(iterator.hasNext()){

            Flat[] flatForSending = new Flat[1];
            flatForSending[0] = flats[0];

            iterator = setOfFlats.iterator();
            while (iterator.hasNext()){
                Flat flatFromCollection = (Flat) iterator.next();
                if(flatFromCollection.getId().equals(flats[0].getId())){
                    dataBlock.setFlats(flatForSending);
                    dataBlock.setUserNeedToShowFlatArr(true);
                    if(command.getCreator().equals(Creator.USER)){
                        dataBlock.setAllRight(true);
                        transferCenter.sendObjectToUser(dataBlock);
                    }
                    else {
                        dataBlock.setAllRight(false);
                        transferCenter.sendObjectToUser(dataBlock);
                        transferCenter.receiveObjectFromUser();
                    }

                    iterator.remove();
                }
            }
        }
        else {
            if(command.getCreator().equals(Creator.USER)){
                dataBlock.setPhrase("Коллекция пустая!");
                dataBlock.setAllRight(true);
                transferCenter.sendObjectToUser(dataBlock);
            }
            else {
                dataBlock.setPhrase("Коллекция пустая!");
                dataBlock.setAllRight(false);
                transferCenter.sendObjectToUser(dataBlock);
                transferCenter.receiveObjectFromUser();
            }
        }
    }

    public void sumOfNumberOfRooms(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        Iterator iterator = setOfFlats.iterator();
        BigInteger numberOfRooms = BigInteger.valueOf(0);
        DataBlock dataBlock = new DataBlock();
        if(iterator.hasNext()){
            while (iterator.hasNext()){
                Flat flat = (Flat) iterator.next();
                try {
//                    System.out.println(flat.getNumberOfRooms());
//                    numberOfRooms
                    numberOfRooms = numberOfRooms.add(BigInteger.valueOf(flat.getNumberOfRooms()));
                }catch (Exception e){

//                    System.out.println("Общее число комнат слишком большое! Перполнен BigInteger!");
                    dataBlock.setPhrase("Общее число комнат слишком большое! Перполнен BigInteger!");
                }

            }
//            System.out.println("Общее число комнат во всех квартирах: " + numberOfRooms);
            dataBlock.setPhrase("Общее число комнат во всех квартирах: " + numberOfRooms);
        }
        else {
//            System.out.println("В коллекции нет квартир!");
            dataBlock.setPhrase("В коллекции нет квартир!");
        }
        if(command.getCreator().equals(Creator.USER)){
            dataBlock.setAllRight(true);
            transferCenter.sendObjectToUser(dataBlock);
        }
        else {
            dataBlock.setAllRight(false);
            transferCenter.sendObjectToUser(dataBlock);
            transferCenter.receiveObjectFromUser();
        }
    }

    public void addIfMin(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        Iterator iterator = getIterator();
        long min;
        min = Long.MAX_VALUE;
        DataBlock dataBlock = new DataBlock();
        if(iterator.hasNext()){
            while (iterator.hasNext()){
                long flatAttractive = ComparisonOfAttractiveness.compare((Flat) iterator.next());
                if(min > flatAttractive){
                    min = flatAttractive;
                }
            }

            Flat newFlat = commandsData.getFlat();
            if(ComparisonOfAttractiveness.compare(newFlat) < min){
                setOfFlats.add(newFlat);
                dataBlock.setPhrase("Добавляем элемент в коллекцию!");
            }
            else {
                //НАверное нужно переделать на слишком маленькую
                dataBlock.setPhrase("Привлекательность элемента слишком большая!");
            }
        }
        else {
//            System.out.println("Пустая коллекция!");
            dataBlock.setPhrase("Пустая коллекция!");
        }
        if(command.getCreator().equals(Creator.USER)){
            dataBlock.setAllRight(true);
            transferCenter.sendObjectToUser(dataBlock);
        }
        else {
            dataBlock.setAllRight(false);
            transferCenter.sendObjectToUser(dataBlock);
            transferCenter.receiveObjectFromUser();
        }

    }

    public void updateId(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){

        long id;
        if(command.getCreator().equals(Creator.USER)){
            id = Long.valueOf(commandsData.getParameter());
        }
        else{
            id = Long.valueOf(command.getParameter());
        }
        Iterator iterator = setOfFlats.iterator();
        Long flatId = null;
        while (iterator.hasNext()){
            long foundedFlatId= ((Flat)iterator.next()).getId();
            if(foundedFlatId == id){
                flatId = foundedFlatId;
                iterator.remove();
            }
        }
        DataBlock dataBlock = new DataBlock();
        if(flatId == null){
            dataBlock.setPhrase("Неправильно введён ID!\nВведите ID занова:");
            dataBlock.setServerNeedStringParameter(true);
            dataBlock.setAllRight(false);
            transferCenter.sendObjectToUser(dataBlock);
            DataBlock correctInfoFromUser = (DataBlock) transferCenter.receiveObjectFromUser();
            command.setParameter(correctInfoFromUser.getParameter());
            updateId(command, transferCenter, commandsData);
        }
        else {
            if(command.getCreator().equals(Creator.USER)){
                DataBlock requestAboutElement = new DataBlock();
                requestAboutElement.setServerNeedElementParameter(true);
                requestAboutElement.setAllRight(false);
                requestAboutElement.setPhrase("Приступаем к обновлению параметров файла с ID: " + id);
                transferCenter.sendObjectToUser(requestAboutElement);
                dataBlock = (DataBlock) transferCenter.receiveObjectFromUser();
                Flat flat = dataBlock.getFlat();
                flat.setId(id);
                setOfFlats.add(flat);

                dataBlock = new DataBlock();
                dataBlock.setPhrase("Элемент обновлён!");
                dataBlock.setAllRight(true);
                transferCenter.sendObjectToUser(dataBlock);
            }
            else {
                DataBlock requestAboutElement = new DataBlock();
                requestAboutElement.setAllRight(false);
                requestAboutElement.setPhrase("Приступаем к обновлению параметров файла с ID: " + id);
                transferCenter.sendObjectToUser(requestAboutElement);
                transferCenter.receiveObjectFromUser();
                Flat flat = FlatCreatorForScript.createFlat(command, id);
                setOfFlats.add(flat);

                dataBlock = new DataBlock();
                dataBlock.setPhrase("Элемент обновлён!");
                dataBlock.setAllRight(false);
                transferCenter.sendObjectToUser(dataBlock);
                transferCenter.receiveObjectFromUser();
            }
        }
    }

    public void removeById(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){
        if(command.getCreator().equals(Creator.USER)){
            long id = Long.valueOf(commandsData.getParameter());
            DataBlock dataBlock = new DataBlock();
            Iterator iterator = setOfFlats.iterator();
            boolean nonElement = true;
            while (iterator.hasNext()){
                if(((Flat)iterator.next()).getId() == id){
                    iterator.remove();
                    nonElement = false;
                    dataBlock.setPhrase("Элемент удалён.");
//                System.out.println("Элемент удалён.");
                    dataBlock.setAllRight(true);
                    transferCenter.sendObjectToUser(dataBlock);

                }
            }
            if(nonElement){
                dataBlock.setPhrase("Квартиры с таким ID не существует!\nПопробуйте ввести ID занова.");
                dataBlock.setServerNeedStringParameter(true);
                dataBlock.setAllRight(false);
                transferCenter.sendObjectToUser(dataBlock);

                dataBlock = (DataBlock) transferCenter.receiveObjectFromUser();
                command.setParameter(dataBlock.getParameter());
                removeById(command,transferCenter, commandsData);
            }
        }
        else {
            long id = Long.valueOf(command.getParameter());
            DataBlock dataBlock = new DataBlock();
            Iterator iterator = setOfFlats.iterator();
            boolean nonElement = true;
            while (iterator.hasNext()){
                if(((Flat)iterator.next()).getId() == id){
                    iterator.remove();
                    nonElement = false;
                    dataBlock.setPhrase("Элемент удалён.");
//                System.out.println("Элемент удалён.");
                    dataBlock.setAllRight(false);
                    transferCenter.sendObjectToUser(dataBlock);
                    transferCenter.receiveObjectFromUser();
                }
            }
            if(nonElement) {
                dataBlock.setPhrase("Квартиры с таким ID не существует!\nПопробуйте ввести ID занова.");
                dataBlock.setServerNeedStringParameter(true);
                dataBlock.setAllRight(false);
                transferCenter.sendObjectToUser(dataBlock);

                dataBlock = (DataBlock) transferCenter.receiveObjectFromUser();
                command.setParameter(dataBlock.getParameter());
                removeById(command, transferCenter, commandsData);
            }
        }
    }

    public void removeLower(CommandsData command, TransferCenter transferCenter, CommandsData commandsData) {
        Flat flatForeCompare;
        if(command.getCreator().equals(Creator.USER)){
            flatForeCompare = commandsData.getFlat();
        }
        else {
            flatForeCompare = FlatCreatorForScript.createFlat(command, 0);
        }

        long compareFlatAttractive = ComparisonOfAttractiveness.compare(flatForeCompare);
        Iterator iterator = setOfFlats.iterator();
        boolean nonElement = true;
        DataBlock dataBlock = new DataBlock();

        if (iterator.hasNext()) {
            while (iterator.hasNext()) {
                if (ComparisonOfAttractiveness.compare((Flat) iterator.next()) < compareFlatAttractive) {
                    iterator.remove();
                    nonElement = false;
                }
            }
            if(command.getCreator().equals(Creator.USER)){
                if(nonElement){
//                System.out.println("Нет подходящих для удаления элементов");
                    dataBlock.setPhrase("Нет подходящих для удаления элементов");
                    dataBlock.setAllRight(true);
                    transferCenter.sendObjectToUser(dataBlock);
                }
                else {
//                System.out.println("Подходящие элементы были удалены.");
                    dataBlock.setPhrase("Подходящие элементы были удалены.");
                    dataBlock.setAllRight(true);
                    transferCenter.sendObjectToUser(dataBlock);
                }
            }
            else {
                if (nonElement) {
//                System.out.println("Нет подходящих для удаления элементов");
                    dataBlock.setPhrase("Нет подходящих для удаления элементов");
                    dataBlock.setAllRight(false);
                    transferCenter.sendObjectToUser(dataBlock);
                    transferCenter.receiveObjectFromUser();
                } else {
//                System.out.println("Подходящие элементы были удалены.");
                    dataBlock.setPhrase("Подходящие элементы были удалены.");
                    dataBlock.setAllRight(false);
                    transferCenter.sendObjectToUser(dataBlock);
                    transferCenter.receiveObjectFromUser();
                }
            }
        }
        else {
            if(command.getCreator().equals(Creator.USER)){
//                System.out.println("Коллекция пустая!");
                dataBlock.setPhrase("Коллекция пустая!");
                dataBlock.setAllRight(true);
                transferCenter.sendObjectToUser(dataBlock);
            }
            else {
//                System.out.println("Коллекция пустая!");
                dataBlock.setPhrase("Коллекция пустая!");
                dataBlock.setAllRight(false);
                transferCenter.sendObjectToUser(dataBlock);
                transferCenter.receiveObjectFromUser();
            }
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

        DataBlock dataBlock = new DataBlock();

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
            String phrase;
            phrase = "Выводим элементы в порядке возрастания количества комнат:\n";
            for (int i =0;i<flats.length;i++){
                phrase += "ID - " + flats[i].getId() + " numberOfRooms - " + flats[i].getNumberOfRooms() + "\n";
            }
            if(command.getCreator().equals(Creator.USER)){
                dataBlock.setPhrase(phrase);
                dataBlock.setAllRight(true);
                transferCenter.sendObjectToUser(dataBlock);
            }
            else {
                dataBlock.setPhrase(phrase);
                dataBlock.setAllRight(false);
                transferCenter.sendObjectToUser(dataBlock);
                transferCenter.receiveObjectFromUser();
            }
        }
        else {
            if(command.getCreator().equals(Creator.USER)){
                if(flats.length == 1){
                    dataBlock.setPhrase("В коллекции содержится всего один элемент: ID - " + flats[0].getId() + " numberOfRooms - " + flats[0].getNumberOfRooms()+ "\n");
                    dataBlock.setAllRight(true);
                    transferCenter.sendObjectToUser(dataBlock);
                }
                else {
//                System.out.println("Коллекция пустая!");
                    dataBlock.setPhrase("Коллекция пустая!");
                    dataBlock.setAllRight(true);
                    transferCenter.sendObjectToUser(dataBlock);
                }
            }
            else {
                if(flats.length == 1){
                    dataBlock.setPhrase("В коллекции содержится всего один элемент: ID - " + flats[0].getId() + " numberOfRooms - " + flats[0].getNumberOfRooms()+ "\n");
                    dataBlock.setAllRight(false);
                    transferCenter.sendObjectToUser(dataBlock);
                    transferCenter.receiveObjectFromUser();
                }
                else {
//                System.out.println("Коллекция пустая!");
                    dataBlock.setPhrase("Коллекция пустая!");
                    dataBlock.setAllRight(false);
                    transferCenter.sendObjectToUser(dataBlock);
                    transferCenter.receiveObjectFromUser();
                }
            }
        }

    }

    public void filterLessThanTransport(CommandsData command, TransferCenter transferCenter, CommandsData commandsData){

        Transport transport;
        DataBlock dataBlock = new DataBlock();
        try {
            if(command.getCreator().equals(Creator.USER)){
                transport = Transport.valueOf(commandsData.getParameter());
            }
            else {
                transport = Transport.valueOf(command.parameter);
            }
        }catch (Exception e){
            dataBlock.setAllRight(false);
            dataBlock.setPhrase("Такого варианта транспора не существует!");
            transferCenter.sendObjectToUser(dataBlock);
            transferCenter.receiveObjectFromUser();
            transport = createTransport(command, transferCenter);
        }

        boolean wasPrinted = false;
        Iterator iterator = setOfFlats.iterator();
        if(iterator.hasNext()){
            while (iterator.hasNext()){
                Flat flat = (Flat)iterator.next();
                if(flat.getTransport() != null){
                    if(flat.getTransport().levelAttractive() < transport.levelAttractive()){
//                        flat.show();
                        Flat[] flats = new Flat[1];
                        flats[0] = flat;
                        if(command.getCreator().equals(Creator.USER)){
                            dataBlock.setFlats(flats);
                            dataBlock.setUserNeedToShowFlatArr(true);
                            dataBlock.setAllRight(true);
                            transferCenter.sendObjectToUser(dataBlock);
                            wasPrinted = true;
                        }
                        else {
                            dataBlock.setFlats(flats);
                            dataBlock.setUserNeedToShowFlatArr(true);
                            dataBlock.setAllRight(false);
                            transferCenter.sendObjectToUser(dataBlock);
                            transferCenter.receiveObjectFromUser();
                            wasPrinted = true;
                        }
                    }
                }
            }
            if(!wasPrinted){
                if(command.getCreator().equals(Creator.USER)){
                    dataBlock.setPhrase("Нет ни одного подходящего элемента в коллекции!");
                    dataBlock.setAllRight(true);
                    transferCenter.sendObjectToUser(dataBlock);
                }
                else {
                    dataBlock.setPhrase("Нет ни одного подходящего элемента в коллекции!");
                    dataBlock.setAllRight(false);
                    transferCenter.sendObjectToUser(dataBlock);
                    transferCenter.receiveObjectFromUser();
                }
//                System.out.println("Нет ни одного подходящего элемента в коллекции!");
            }
        }
        else {
//            System.out.println("В коллекции нет элементов для сравнения!");
            if(command.getCreator().equals(Creator.USER)){
                dataBlock.setPhrase("В коллекции нет элементов для сравнения!");
                dataBlock.setAllRight(true);
                transferCenter.sendObjectToUser(dataBlock);
            }
            else {
                dataBlock.setPhrase("В коллекции нет элементов для сравнения!");
                dataBlock.setAllRight(false);
                transferCenter.sendObjectToUser(dataBlock);
                transferCenter.receiveObjectFromUser();
            }
        }

    }

    public Transport createTransport(CommandsData commandsData, TransferCenter transferCenter){
//        Scanner input = new Scanner(System.in);
        String phrase = "";
        phrase +="Транспортные маршруты,проходящие у дома, задаётся одной из следующих констант:\n";
        Transport[] transports = Transport.values();
        Transport transport;
        for (int i =0;i<transports.length;i++){
            phrase +=transports[i].name() + " ";
        }
        phrase +="Нужно выбрать одну из них";
        DataBlock dataBlock = new DataBlock();
        dataBlock.setPhrase(phrase);
        dataBlock.setServerNeedStringParameter(true);
        dataBlock.setAllRight(false);
        transferCenter.sendObjectToUser(dataBlock);
        dataBlock = (DataBlock) transferCenter.receiveObjectFromUser();
//        String str = informationGetter(commandsData);
        if(dataBlock.getParameter().length() == 0){
            dataBlock = new DataBlock();
            dataBlock.setPhrase("Это поле не может быть пустым!\n");
//            System.out.println("Это поле не может быть пустым!");
            dataBlock.setAllRight(false);
            transferCenter.sendObjectToUser(dataBlock);
            transferCenter.receiveObjectFromUser();
            transport = createTransport(commandsData, transferCenter);
        }
        else {
            try {
                transport = Transport.valueOf(dataBlock.getParameter());
            } catch (Exception e) {
                dataBlock = new DataBlock();
                dataBlock.setPhrase("Некорректный ввод данных!\nВведите поле занова\n");
                dataBlock.setAllRight(false);
                transferCenter.sendObjectToUser(dataBlock);
                transferCenter.receiveObjectFromUser();
                transport = createTransport(commandsData, transferCenter);
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