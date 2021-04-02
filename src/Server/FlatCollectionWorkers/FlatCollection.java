package Server.FlatCollectionWorkers;

import CommonClasses.*;
import CommonClasses.ApartmentDescription.ComparisonOfAttractiveness;
import CommonClasses.ApartmentDescription.Transport;
//import L6Server.Commands.CommandsData;
import Server.Commands.Command;
import Server.DataPacket;
import Server.InputeOutputeWork.UpLoadingCollectionToFile;
import Server.TransferCenter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.math.BigInteger;
import java.nio.channels.DatagramChannel;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FlatCollection {

//    private ArrayDeque<Flat> collectionOfFlats = new ArrayDeque<>();
    private ConcurrentLinkedDeque<Flat> collectionOfFlats = new ConcurrentLinkedDeque<>();

    private Date dateOfInitialization;

    public FlatCollection() {
        this.dateOfInitialization = new Date();
    }

    public int getCollectionSize(){
        return collectionOfFlats.size();
    }

    public Iterator getIterator(){
        return collectionOfFlats.iterator();
    }

    public void sortByAttractive(){
        PriorityQueue<Flat> priorityQueue = new PriorityQueue<>();
        for (Flat flat : collectionOfFlats){
            priorityQueue.add(flat);
        }
        collectionOfFlats.clear();
        for (Flat flat : priorityQueue){
            collectionOfFlats.add(flat);
        }
    }

    private Long createId(){

        Random random = new Random();
        Boolean repeat = true;
        long id = 0;
        while (repeat){
            repeat = false;
            Iterator iterator = collectionOfFlats.iterator();
            id = random.nextLong();
            for (int i = 0; i< collectionOfFlats.size(); i++){
                if(((Flat)iterator.next()).getId() == id){
                    repeat = true;
                }
            }
        }
        return Long.valueOf(id);
    }

    public void getInfo(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){

//        DataBlock dataBlock = new DataBlock();
        CommandsData commandsData = dataPacket.getCommandsData();
        commandsData.setPhrase("Тип: " + getClass().getTypeName() + "\n" +
                "Дата инициализации: " + dateOfInitialization + "\n" +
                "Количество элементов: " + collectionOfFlats.size());
        if(commandsData.getCreator().equals(Creator.USER)){
            commandsData.setCommandEnded(true);
        }
        else {
            //В executeScriptCommandRealization после выполнения всех команд нужно отправить end
            //До того момента сервер отправляет сообщения и не получает ответы на них
            commandsData.setCommandEnded(false);
        }
//        System.out.println(commandsData.getCreator());
//        TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
        answersWaitingSending.add(dataPacket);

//        if(commandsData.getCreator().equals(Creator.USER)){
//            dataBlock.setAllRight(true);
//            TransferCenter.sendObject(datagramChannel, commandsData);
//        }
//        else {
//            dataBlock.setAllRight(false);
//            transferCenter.sendObjectToUser(dataBlock);
//            transferCenter.receiveObjectFromUser();
//        }
    }

    public void show(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        CommandsData commandsData = dataPacket.getCommandsData();

//        Arrays.stream(setOfFlats.toArray()).toArray();

        Object[] arr = collectionOfFlats.stream().toArray();

        Flat[] flats = new Flat[arr.length];

        for(int i =0;i<flats.length;i++){
            flats[i] = (Flat) arr[i];
        }


        SortFlatArrBySize sortFlatArrBySize = new SortFlatArrBySize();
        commandsData.setFlats(sortFlatArrBySize.startSorting(flats));

//        Flat[] flats = new Flat[setOfFlats.toArray().length];
//        Iterator iterator = setOfFlats.iterator();
//        int i = 0;
//        while (iterator.hasNext()){
//            flats[i] = (Flat)iterator.next();
//            i++;
//        }





//        SortFlatArrBySize sortFlatArrBySize = new SortFlatArrBySize();

//        DataBlock dataBlock = new DataBlock();

//        dataBlock.setFlats(sortFlatArrBySize.startSorting(flats));
//        dataBlock.setUserNeedToShowFlatArr(true);

        commandsData.setUserNeedToShowFlatArr(true);
        commandsData.setPhrase("Содержимое коллекции: \n");
//        commandsData.setFlats(sortFlatArrBySize.startSorting(flats));


        if(commandsData.getFlats().length == 0){
            commandsData.setPhrase("Коллекция пустая!");
            commandsData.setUserNeedToShowFlatArr(false);
        }
        if(commandsData.getCreator().equals(Creator.USER)){
            commandsData.setCommandEnded(true);
        }
        else {
            //В executeScriptCommandRealization после выполнения всех команд нужно отправить end
            //До того момента сервер отправляет сообщения и не получает ответы на них
            commandsData.setCommandEnded(false);
        }

        answersWaitingSending.add(dataPacket);
//        TransferCenter.sendAnswerToUser(datagramChannel, commandsData);


//        if(command.getCreator().equals(Creator.USER))
//        {
//            dataBlock.setAllRight(true);
//            transferCenter.sendObjectToUser(dataBlock);
//        }
//        else {
//            dataBlock.setAllRight(false);
//            transferCenter.sendObjectToUser(dataBlock);
//            transferCenter.receiveObjectFromUser();
//        }
    }

    //рализация команды
    public void add(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        CommandsData commandsData = dataPacket.getCommandsData();
//        DataBlock dataBlock = new DataBlock();
        if(commandsData.getCreator().equals(Creator.USER)){
            collectionOfFlats.add(commandsData.getFlat());
            commandsData.setPhrase("Объект добавлен в коллекцию!");
//            commandsData.setAllRight(true);
            commandsData.setCommandEnded(true);
//            transferCenter.sendObjectToUser(dataBlock);
//            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
            answersWaitingSending.add(dataPacket);
        }
        else {
            long flatID = createId();
            Flat flat = FlatCreatorForScript.createFlat(commandsData, flatID);
            collectionOfFlats.add(flat);
            commandsData.setPhrase("Объект добавлен в коллекцию!");
//            dataBlock.setAllRight(false);
            commandsData.setCommandEnded(false);
//            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
            answersWaitingSending.add(dataPacket);
//            transferCenter.receiveObjectFromUser();
        }
    }

    //используется при загрузке данных из файла (не является командой)
    public void add(Flat flat){
        collectionOfFlats.add(flat);
    }

    public void clear(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        CommandsData commandsData = dataPacket.getCommandsData();

        collectionOfFlats.clear();
//        System.out.println("Коллекция очищена!");
//        DataBlock dataBlock = new DataBlock();
        commandsData.setPhrase("Коллекция очищена!");
        if(commandsData.getCreator().equals(Creator.USER)){
//            commandsData.setAllRight(true);
            commandsData.setCommandEnded(true);
//            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
            answersWaitingSending.add(dataPacket);
        }
        else {
//            commandsData.setAllRight(false);
            commandsData.setCommandEnded(false);
//            commandsData.sendObjectToUser(dataBlock);
//            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
            answersWaitingSending.add(dataPacket);
//            transferCenter.receiveObjectFromUser();
        }
    }

    //Работает только от запроса сервера
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

    public void removeHead(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        CommandsData commandsData = dataPacket.getCommandsData();
        Iterator iterator = collectionOfFlats.iterator();
        Flat flat;
//        DataBlock dataBlock = new DataBlock();

        Flat[] flats = new Flat[collectionOfFlats.size()];
        for(int i = 0; i< collectionOfFlats.size(); i++){
            flats[i] = (Flat) collectionOfFlats.toArray()[i];
        }

        (new SortFlatArrBySize()).startSorting(flats);

        if(iterator.hasNext()){

            Flat[] flatForSending = new Flat[1];
            flatForSending[0] = flats[0];

            iterator = collectionOfFlats.iterator();
            while (iterator.hasNext()){
                Flat flatFromCollection = (Flat) iterator.next();
                if(flatFromCollection.getId().equals(flats[0].getId())){
                    commandsData.setFlats(flatForSending);
                    commandsData.setUserNeedToShowFlatArr(true);
                    commandsData.setPhrase("Эллемент удалён!");
                    if(commandsData.getCreator().equals(Creator.USER)){
//                        commandsData.setAllRight(true);
                        commandsData.setCommandEnded(true);
//                        TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                        answersWaitingSending.add(dataPacket);
                    }
                    else {
//                        commandsData.setAllRight(false);
                        commandsData.setCommandEnded(false);
//                        .sendObjectToUser(dataBlock);
//                        TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                        answersWaitingSending.add(dataPacket);
//                        transferCenter.receiveObjectFromUser();
                    }

                    iterator.remove();
                }
            }
        }
        else {
            if(commandsData.getCreator().equals(Creator.USER)){
                commandsData.setPhrase("Коллекция пустая!");
//                dataBlock.setAllRight(true);
                commandsData.setCommandEnded(true);
//                transferCenter.sendObjectToUser(dataBlock);
//                TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                answersWaitingSending.add(dataPacket);
            }
            else {
                commandsData.setPhrase("Коллекция пустая!");
//                dataBlock.setAllRight(false);
                commandsData.setCommandEnded(false);
//                commandsData.sendObjectToUser(dataBlock);
//                TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                answersWaitingSending.add(dataPacket);
//                transferCenter.receiveObjectFromUser();
            }
        }
    }

    public void sumOfNumberOfRooms(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        CommandsData commandsData = dataPacket.getCommandsData();
        Iterator iterator = collectionOfFlats.iterator();
        BigInteger numberOfRooms = BigInteger.valueOf(0);
//        DataBlock dataBlock = new DataBlock();
        if(iterator.hasNext()){
            while (iterator.hasNext()){
                Flat flat = (Flat) iterator.next();
                try {
//                    System.out.println(flat.getNumberOfRooms());
//                    numberOfRooms
                    numberOfRooms = numberOfRooms.add(BigInteger.valueOf(flat.getNumberOfRooms()));
                }catch (Exception e){

//                    System.out.println("Общее число комнат слишком большое! Перполнен BigInteger!");
                    commandsData.setPhrase("Общее число комнат слишком большое! Перполнен BigInteger!");
                }

            }
//            System.out.println("Общее число комнат во всех квартирах: " + numberOfRooms);
            commandsData.setPhrase("Общее число комнат во всех квартирах: " + numberOfRooms);
        }
        else {
//            System.out.println("В коллекции нет квартир!");
            commandsData.setPhrase("В коллекции нет квартир!");
        }
        if(commandsData.getCreator().equals(Creator.USER)){
//            commandsData.setAllRight(true);
            commandsData.setCommandEnded(true);
//            transferCenter.sendObjectToUser(dataBlock);
//            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
            answersWaitingSending.add(dataPacket);
        }
        else {
//            dataBlock.setAllRight(false);
            commandsData.setCommandEnded(false);
//            .sendObjectToUser(dataBlock);
//            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
            answersWaitingSending.add(dataPacket);
//            transferCenter.receiveObjectFromUser();
        }
    }

    public void addIfMin(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        CommandsData commandsData = dataPacket.getCommandsData();
        Iterator iterator = getIterator();
        long min;
        min = Long.MAX_VALUE;
//        DataBlock dataBlock = new DataBlock();
        if(iterator.hasNext()){
            while (iterator.hasNext()){
                long flatAttractive = ComparisonOfAttractiveness.compare((Flat) iterator.next());
                if(min > flatAttractive){
                    min = flatAttractive;
                }
            }

            Flat newFlat = commandsData.getFlat();
            if(ComparisonOfAttractiveness.compare(newFlat) < min){
                collectionOfFlats.add(newFlat);
                commandsData.setPhrase("Добавляем элемент в коллекцию!");
            }
            else {
                //НАверное нужно переделать на слишком маленькую
                commandsData.setPhrase("Привлекательность элемента слишком большая!");
            }
        }
        else {
//            System.out.println("Пустая коллекция!");
            commandsData.setPhrase("Пустая коллекция!");
        }
        if(commandsData.getCreator().equals(Creator.USER)){
//            commandsData.setAllRight(true);
            commandsData.setCommandEnded(true);
//            transferCenter.sendObjectToUser(dataBlock);
//            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
            answersWaitingSending.add(dataPacket);
        }
        else {
//            dataBlock.setAllRight(false);
            commandsData.setCommandEnded(false);
//            transferCenter.sendObjectToUser(dataBlock);
//            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
            answersWaitingSending.add(dataPacket);
//            transferCenter.receiveObjectFromUser();
        }

    }

    public void updateId(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        CommandsData commandsData = dataPacket.getCommandsData();

        long id;
        if(commandsData.getCreator().equals(Creator.USER)){
            id = Long.valueOf(commandsData.getParameter());
        }
        else{
            id = Long.valueOf(commandsData.getParameter());
        }
        Iterator iterator = collectionOfFlats.iterator();
        Long flatId = null;
        while (iterator.hasNext()){
            long foundedFlatId= ((Flat)iterator.next()).getId();
            if(foundedFlatId == id){
                flatId = foundedFlatId;
                iterator.remove();
            }
        }
//        DataBlock dataBlock = new DataBlock();
        if(flatId == null){
            commandsData.setPhrase("Неправильно введён ID!\nВведите ID занова:");
            commandsData.setServerNeedStringParameter(true);
//            dataBlock.setAllRight(false);
            commandsData.setCommandEnded(false);
//            transferCenter.sendObjectToUser(dataBlock);
//            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
            answersWaitingSending.add(dataPacket);
//            DataBlock correctInfoFromUser = (DataBlock) transferCenter.receiveObjectFromUser();
//            command.setParameter(correctInfoFromUser.getParameter());
//            updateId(command, transferCenter, commandsData);
        }
        else {
            if(commandsData.getCreator().equals(Creator.USER)){
//                DataBlock requestAboutElement = new DataBlock();
                if(commandsData.getFlat().equals(null)){
                    commandsData.setServerNeedElementParameter(true);
//                    requestAboutElement.setAllRight(false);
                    commandsData.setCommandEnded(false);
                    commandsData.setPhrase("Приступаем к обновлению параметров файла с ID: " + id);
//                transferCenter.sendObjectToUser(requestAboutElement);
//                    commandsData.setServerNeedElementParameter(true);
//                    TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                    answersWaitingSending.add(dataPacket);
                }
                else {
//                    dataBlock = (DataBlock) transferCenter.receiveObjectFromUser();

                    Flat flat = commandsData.getFlat();
                    flat.setId(id);
                    collectionOfFlats.add(flat);

                    commandsData.setCommandEnded(true);
                    commandsData.setPhrase("Элемент успешно обновлён!");
//                    TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                    answersWaitingSending.add(dataPacket);
                }

//                dataBlock = new DataBlock();
//                dataBlock.setPhrase("Элемент обновлён!");
//                dataBlock.setAllRight(true);
//                transferCenter.sendObjectToUser(dataBlock);
            }
            else {
//                DataBlock requestAboutElement = new DataBlock();
//                requestAboutElement.setAllRight(false);
//                requestAboutElement.setPhrase("Приступаем к обновлению параметров файла с ID: " + id);
//                transferCenter.sendObjectToUser(requestAboutElement);
//                transferCenter.receiveObjectFromUser();
                Flat flat = FlatCreatorForScript.createFlat(commandsData, id);
                collectionOfFlats.add(flat);

//                dataBlock = new DataBlock();
                commandsData.setPhrase("Приступаем к обновлению параметров файла с ID: " + id + "\n" + "Элемент обновлён!");
//                dataBlock.setAllRight(false);
                commandsData.setCommandEnded(false);
//                transferCenter.sendObjectToUser(dataBlock);
//                transferCenter.receiveObjectFromUser();
//                TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                answersWaitingSending.add(dataPacket);
            }
        }
    }

    public void removeById(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        CommandsData commandsData = dataPacket.getCommandsData();
        if(commandsData.getCreator().equals(Creator.USER)){
            long id = Long.valueOf(commandsData.getParameter());
//            DataBlock dataBlock = new DataBlock();
            Iterator iterator = collectionOfFlats.iterator();
            boolean nonElement = true;
            while (iterator.hasNext()){
                if(((Flat)iterator.next()).getId() == id){
                    iterator.remove();
                    nonElement = false;
                    commandsData.setPhrase("Элемент удалён.");
//                System.out.println("Элемент удалён.");
//                    dataBlock.setAllRight(true);
                    commandsData.setCommandEnded(true);
//                    transferCenter.sendObjectToUser(dataBlock);
//                    TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                    answersWaitingSending.add(dataPacket);

                }
            }
            if(nonElement){
                commandsData.setPhrase("Квартиры с таким ID не существует!\nПопробуйте ввести ID занова.");
                commandsData.setServerNeedStringParameter(true);
//                dataBlock.setAllRight(false);
                commandsData.setCommandEnded(false);
//                transferCenter.sendObjectToUser(dataBlock);
//                TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                answersWaitingSending.add(dataPacket);

//                dataBlock = (DataBlock) transferCenter.receiveObjectFromUser();
//                command.setParameter(dataBlock.getParameter());
//                removeById(command,transferCenter, commandsData);
            }
        }
        else {
            long id = Long.valueOf(commandsData.getParameter());
//            DataBlock dataBlock = new DataBlock();
            Iterator iterator = collectionOfFlats.iterator();
            boolean nonElement = true;
            while (iterator.hasNext()){
                if(((Flat)iterator.next()).getId() == id){
                    iterator.remove();
                    nonElement = false;
                    commandsData.setPhrase("Элемент удалён.");
//                System.out.println("Элемент удалён.");
//                    dataBlock.setAllRight(false);
                    commandsData.setCommandEnded(false);
//                    TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                    answersWaitingSending.add(dataPacket);
//                    transferCenter.sendObjectToUser(dataBlock);
//                    transferCenter.receiveObjectFromUser();
                }
            }
//            Здесь должна быть реализация обработки ошибки при отсутствии верного параметра в скрипте, но нет...
//            if(nonElement) {
//                dataBlock.setPhrase("Квартиры с таким ID не существует!\nПопробуйте ввести ID занова.");
//                dataBlock.setServerNeedStringParameter(true);
//                dataBlock.setAllRight(false);
//                transferCenter.sendObjectToUser(dataBlock);
//
//                dataBlock = (DataBlock) transferCenter.receiveObjectFromUser();
//                command.setParameter(dataBlock.getParameter());
//                removeById(command, transferCenter, commandsData);
//            }
        }
    }

    public void removeLower(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket) {
        CommandsData commandsData = dataPacket.getCommandsData();
        Flat flatForeCompare;
        if(commandsData.getCreator().equals(Creator.USER)){
            flatForeCompare = commandsData.getFlat();
        }
        else {
            flatForeCompare = FlatCreatorForScript.createFlat(commandsData, 0);
        }

        long compareFlatAttractive = ComparisonOfAttractiveness.compare(flatForeCompare);
        Iterator iterator = collectionOfFlats.iterator();
        boolean nonElement = true;
//        DataBlock dataBlock = new DataBlock();

        if (iterator.hasNext()) {
            while (iterator.hasNext()) {
                if (ComparisonOfAttractiveness.compare((Flat) iterator.next()) < compareFlatAttractive) {
                    iterator.remove();
                    nonElement = false;
                }
            }
            if(commandsData.getCreator().equals(Creator.USER)){
                if(nonElement){
//                System.out.println("Нет подходящих для удаления элементов");
                    commandsData.setPhrase("Нет подходящих для удаления элементов");
//                    dataBlock.setAllRight(true);
                    commandsData.setCommandEnded(true);
//                    TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                    answersWaitingSending.add(dataPacket);
//                    transferCenter.sendObjectToUser(dataBlock);
                }
                else {
//                System.out.println("Подходящие элементы были удалены.");
                    commandsData.setPhrase("Подходящие элементы были удалены.");
//                    dataBlock.setAllRight(true);
                    commandsData.setCommandEnded(true);
//                    transferCenter.sendObjectToUser(dataBlock);
//                    TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                    answersWaitingSending.add(dataPacket);
                }
            }
            else {
                if (nonElement) {
//                System.out.println("Нет подходящих для удаления элементов");
                    commandsData.setPhrase("Нет подходящих для удаления элементов");
//                    dataBlock.setAllRight(false);
                    commandsData.setCommandEnded(false);
//                    TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                    answersWaitingSending.add(dataPacket);
//                    transferCenter.sendObjectToUser(dataBlock);
//                    transferCenter.receiveObjectFromUser();
                } else {
//                System.out.println("Подходящие элементы были удалены.");
                    commandsData.setPhrase("Подходящие элементы были удалены.");
//                    dataBlock.setAllRight(false);
                    commandsData.setCommandEnded(false);
//                    TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                    answersWaitingSending.add(dataPacket);
//                    transferCenter.sendObjectToUser(dataBlock);
//                    transferCenter.receiveObjectFromUser();
                }
            }
        }
        else {
            if(commandsData.getCreator().equals(Creator.USER)){
//                System.out.println("Коллекция пустая!");
                commandsData.setPhrase("Коллекция пустая!");
//                dataBlock.setAllRight(true);
                commandsData.setCommandEnded(true);
//                TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                answersWaitingSending.add(dataPacket);
//                transferCenter.sendObjectToUser(dataBlock);
            }
            else {
//                System.out.println("Коллекция пустая!");
                commandsData.setPhrase("Коллекция пустая!");
//                dataBlock.setAllRight(false);
                commandsData.setCommandEnded(false);
//                TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                answersWaitingSending.add(dataPacket);
//                transferCenter.sendObjectToUser(dataBlock);
//                transferCenter.receiveObjectFromUser();
            }
        }
    }

    public void printFieldAscendingNumberOfRooms(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        CommandsData commandsData = dataPacket.getCommandsData();

        Flat[] flats = new Flat[0];

        try {
            Object obj[] = collectionOfFlats.toArray();
            flats = new Flat[obj.length];
            for(int i =0; i< obj.length;i++){
                flats[i] = (Flat) obj[i];
            }
        }catch (Exception e){
            System.out.println("Проблемма с загрузкой коллекции в массив в методе printFieldAscendingNumberOfRooms");
        }

//        DataBlock dataBlock = new DataBlock();

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
            if(commandsData.getCreator().equals(Creator.USER)){
                commandsData.setPhrase(phrase);
//                dataBlock.setAllRight(true);
                commandsData.setCommandEnded(true);
//                TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                answersWaitingSending.add(dataPacket);
//                transferCenter.sendObjectToUser(dataBlock);
            }
            else {
                commandsData.setPhrase(phrase);
//                dataBlock.setAllRight(false);
                commandsData.setCommandEnded(false);
//                TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                answersWaitingSending.add(dataPacket);
//                transferCenter.sendObjectToUser(dataBlock);
//                transferCenter.receiveObjectFromUser();
            }
        }
        else {
            if(commandsData.getCreator().equals(Creator.USER)){
                if(flats.length == 1){
                    commandsData.setPhrase("В коллекции содержится всего один элемент: ID - " + flats[0].getId() + " numberOfRooms - " + flats[0].getNumberOfRooms()+ "\n");
//                    dataBlock.setAllRight(true);
                    commandsData.setCommandEnded(true);
//                    TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                    answersWaitingSending.add(dataPacket);
//                    transferCenter.sendObjectToUser(dataBlock);
                }
                else {
//                System.out.println("Коллекция пустая!");
                    commandsData.setPhrase("Коллекция пустая!");
                    commandsData.setCommandEnded(true);
//                    TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                    answersWaitingSending.add(dataPacket);
//                    dataBlock.setAllRight(true);
//                    transferCenter.sendObjectToUser(dataBlock);
                }
            }
            else {
                if(flats.length == 1){
                    commandsData.setPhrase("В коллекции содержится всего один элемент: ID - " + flats[0].getId() + " numberOfRooms - " + flats[0].getNumberOfRooms()+ "\n");
                    commandsData.setCommandEnded(false);
//                    TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                    answersWaitingSending.add(dataPacket);
//                    dataBlock.setAllRight(false);
//                    transferCenter.sendObjectToUser(dataBlock);
//                    transferCenter.receiveObjectFromUser();
                }
                else {
//                System.out.println("Коллекция пустая!");
                    commandsData.setPhrase("Коллекция пустая!");
                    commandsData.setCommandEnded(false);
//                    TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                    answersWaitingSending.add(dataPacket);
//                    dataBlock.setAllRight(false);
//                    transferCenter.sendObjectToUser(dataBlock);
//                    transferCenter.receiveObjectFromUser();
                }
            }
        }

    }

    public void filterLessThanTransport(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket dataPacket){
        CommandsData commandsData = dataPacket.getCommandsData();

        Transport transport;
//        DataBlock dataBlock = new DataBlock();
        try {
            if(commandsData.getCreator().equals(Creator.USER)){
                transport = Transport.valueOf(commandsData.getParameter());
            }
            else {
                transport = Transport.valueOf(commandsData.getParameter());
            }
        }catch (Exception e){
//            commandsData.setAllRight(false);
            commandsData.setCommandEnded(false);
            commandsData.setPhrase("Такого варианта транспора не существует!\nВведите другой.");
            commandsData.setServerNeedStringParameter(true);
//            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
            answersWaitingSending.add(dataPacket);
            return;
//            transferCenter.sendObjectToUser(dataBlock);
//            transferCenter.receiveObjectFromUser();
//            transport = createTransport(command, transferCenter);
        }

        boolean wasPrinted = false;
        Iterator iterator = collectionOfFlats.iterator();
        if(iterator.hasNext()){
            while (iterator.hasNext()){
                Flat flat = (Flat)iterator.next();
                if(flat.getTransport() != null){
                    if(flat.getTransport().levelAttractive() < transport.levelAttractive()){
//                        flat.show();
                        Flat[] flats = new Flat[1];
                        flats[0] = flat;
                        if(commandsData.getCreator().equals(Creator.USER)){
                            commandsData.setFlats(flats);
                            commandsData.setUserNeedToShowFlatArr(true);
//                            dataBlock.setAllRight(true);
                            commandsData.setCommandEnded(true);
//                            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                            answersWaitingSending.add(dataPacket);
//                            transferCenter.sendObjectToUser(dataBlock);
                            wasPrinted = true;
                        }
                        else {
                            commandsData.setFlats(flats);
                            commandsData.setUserNeedToShowFlatArr(true);
                            commandsData.setCommandEnded(false);
//                            TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                            answersWaitingSending.add(dataPacket);
//                            dataBlock.setAllRight(false);
//                            transferCenter.sendObjectToUser(dataBlock);
//                            transferCenter.receiveObjectFromUser();
                            wasPrinted = true;
                        }
                    }
                }
            }
            if(!wasPrinted){
                if(commandsData.getCreator().equals(Creator.USER)){
                    commandsData.setPhrase("Нет ни одного подходящего элемента в коллекции!");
//                    dataBlock.setAllRight(true);
                    commandsData.setCommandEnded(true);
//                    TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                    answersWaitingSending.add(dataPacket);
//                    transferCenter.sendObjectToUser(dataBlock);
                }
                else {
                    commandsData.setPhrase("Нет ни одного подходящего элемента в коллекции!");
                    commandsData.setCommandEnded(false);
//                    TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                    answersWaitingSending.add(dataPacket);
//                    dataBlock.setAllRight(false);
//                    transferCenter.sendObjectToUser(dataBlock);
//                    transferCenter.receiveObjectFromUser();
                }
//                System.out.println("Нет ни одного подходящего элемента в коллекции!");
            }
        }
        else {
//            System.out.println("В коллекции нет элементов для сравнения!");
            if(commandsData.getCreator().equals(Creator.USER)){
                commandsData.setPhrase("В коллекции нет элементов для сравнения!");
//                dataBlock.setAllRight(true);
                commandsData.setCommandEnded(true);
//                TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                answersWaitingSending.add(dataPacket);
//                transferCenter.sendObjectToUser(dataBlock);
            }
            else {
                commandsData.setPhrase("В коллекции нет элементов для сравнения!");
                commandsData.setCommandEnded(false);
//                TransferCenter.sendAnswerToUser(datagramChannel, commandsData);
                answersWaitingSending.add(dataPacket);
//                dataBlock.setAllRight(false);
//                transferCenter.sendObjectToUser(dataBlock);
//                transferCenter.receiveObjectFromUser();
            }
        }

    }

    //Непонятно зачем он здесь
//    public Transport createTransport(CommandsData commandsData, TransferCenter transferCenter){
////        Scanner input = new Scanner(System.in);
//        if(commandsData.getCreator().equals(Creator.USER)) {
//            if(commandsData.getParameter().equals(null)) {
//                String phrase = "";
//                phrase += "Транспортные маршруты,проходящие у дома, задаётся одной из следующих констант:\n";
//                Transport[] transports = Transport.values();
//                Transport transport;
//                for (int i = 0; i < transports.length; i++) {
//                    phrase += transports[i].name() + " ";
//                }
//                phrase += "Нужно выбрать одну из них";
////        DataBlock dataBlock = new DataBlock();
//                commandsData.setPhrase(phrase);
//                commandsData.setServerNeedStringParameter(true);
////                dataBlock.setAllRight(false);
//                commandsData.setCommandEnded(false);
//                TransferCenter.sendObject();
////                transferCenter.sendObjectToUser(dataBlock);
////                dataBlock = (DataBlock) transferCenter.receiveObjectFromUser();
////        String str = informationGetter(commandsData);
//                return null;
//            }
//            if (commandsData.getParameter().length() == 0) {
//                dataBlock = new DataBlock();
//                dataBlock.setPhrase("Это поле не может быть пустым!\n");
////            System.out.println("Это поле не может быть пустым!");
//                dataBlock.setAllRight(false);
//                transferCenter.sendObjectToUser(dataBlock);
//                transferCenter.receiveObjectFromUser();
//                transport = createTransport(commandsData, transferCenter);
//            } else {
//                try {
//                    transport = Transport.valueOf(dataBlock.getParameter());
//                } catch (Exception e) {
//                    dataBlock = new DataBlock();
//                    dataBlock.setPhrase("Некорректный ввод данных!\nВведите поле занова\n");
//                    dataBlock.setAllRight(false);
//                    transferCenter.sendObjectToUser(dataBlock);
//                    transferCenter.receiveObjectFromUser();
//                    transport = createTransport(commandsData, transferCenter);
//                }
//            }
//            return transport;
//        }
//        else {
//            Transport transport = null;
//            try {
//                transport = Transport.valueOf(commandsData.getBufferedReader().readLine());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return transport;
//        }
//    }

//    private String informationGetter(CommandsData command){
//        if(command.getCreator().equals(Creator.USER)){
//            return (new Scanner(System.in).nextLine());
//        }
//        else {
//            try {
//                return command.getBufferedReader().readLine();
//            } catch (IOException e) {
//                System.out.println("Сканер не смог считать стороку из файла");
//            }
//            return null;
//        }
//    }
}