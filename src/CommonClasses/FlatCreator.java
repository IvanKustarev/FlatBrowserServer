package CommonClasses;

import L6Server.ApartmentDescription.*;

import java.io.BufferedReader;
import java.util.Scanner;

public class FlatCreator {

    BufferedReader bufferedReader;
//    CommandsData commandsData;

    public FlatCreator(){
//        this.bufferedReader = commandsData.getBufferedReader();
//        this.commandsData = commandsData;
    }

    public String informationGetter(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
//        if(creator.equals(Creator.USER)){
//            return (new Scanner(System.in).nextLine());
//        }
//        else {
//            try {
//                return bufferedReader.readLine();
//            } catch (Exception e){
//                System.out.println("Проблема с чтением файла в классе L6User.L6User.FlatCreator!");
//            }
//            return null;
//        }
    }

    public String createName(){

        System.out.println("Введите имя квартиры:");
        String name = informationGetter();
//        System.out.println(name);
        if(name.length() == 0){
            name = null;
        }
        if(name == null){
            System.out.println("У квартиры обязательно должно быть имя!");
            name = (new FlatCreator()).createName();
        }
        boolean empty = true;
        for (int i =0;i<name.length();i++){
            if(name.charAt(i) != ' '){
                empty = false;
            }
        }
        if(empty){
            System.out.println("У квартиры обязательно должно быть имя!");
            name = (new FlatCreator()).createName();
        }
        return name;
    }

    public Coordinates createCoordinates(){
        Coordinates coordinates = new Coordinates();
        coordinates.setX(createXcoordinate());
        coordinates.setY(createYcoordinate());
        return coordinates;
    }

    private Double createXcoordinate(){
//        Scanner input = new Scanner(System.in);
        System.out.println("Введите координату по X:");
        Double x;
        try {
            x = Double.valueOf(informationGetter());
        }catch (Exception e){
            System.out.println("Некорректный ввод данных!\nВведите поле занова");
            x = (new FlatCreator()).createXcoordinate();
        }
        if(x <= -587){
            System.out.println("Значение поля должно быть больше -587!");
            x = (new FlatCreator()).createXcoordinate();
        }
        return x;
    }

    private Integer createYcoordinate(){
//        Scanner input = new Scanner(System.in);
        System.out.println("Введите координату по Y:");
        Integer y;
        try {
            y = Integer.valueOf(informationGetter());
        }catch (Exception e){
            System.out.println("Некорректный ввод данных!\nВведите поле занова");
            y = (new FlatCreator()).createYcoordinate();
        }
        if(y > 77){
            System.out.println("Максимальное значение поля: 77!");
            y = (new FlatCreator()).createYcoordinate();
        }
        return y;
    }

    public Long createArea(){
//        Scanner input = new Scanner(System.in);
        System.out.println("Введите номер района:");
        Long area;
        try {
            area = Long.valueOf(informationGetter());
        }catch (Exception e){
            System.out.println("Некорректный ввод данных!\nВведите поле занова");
            area = (new FlatCreator()).createArea();
        }
        if(area <= 0){
            System.out.println("Значение поля должно быть больше 0!");
            area = (new FlatCreator()).createArea();
        }
        return area;
    }

    public long createNumberOfRooms(){
//        Scanner input = new Scanner(System.in);
        System.out.println("Введите количество комнат:");
        Long numberOfRooms;
        try {
            numberOfRooms = Long.valueOf(informationGetter());
        }catch (Exception e){
            System.out.println("Некорректный ввод данных!\nВведите поле занова");
            numberOfRooms = (new FlatCreator()).createNumberOfRooms();
        }
        if((numberOfRooms <= 0) | (numberOfRooms == null)){
            System.out.println("Значение поля должно быть больше 0!");
            numberOfRooms = (new FlatCreator()).createNumberOfRooms();
        }
        return numberOfRooms;
    }

    public Furnish createFurnish(){
//        Scanner input = new Scanner(System.in);
        System.out.println("Обстановка квартиры задаётся одной из следующих констант:");
        Furnish[] furnishes = Furnish.values();
        Furnish furnish;
        for (int i =0;i<furnishes.length;i++){
            System.out.print(furnishes[i].name() + " ");
        }
        System.out.println("\nНужно выбрать одну из них:");
        try {
            furnish = Furnish.valueOf(informationGetter());
        }catch (Exception e){
            System.out.println("Некорректный ввод данных!\nВведите поле занова");
            furnish = (new FlatCreator()).createFurnish();
        }
        return furnish;
    }

    public View createView(){
//        Scanner input = new Scanner(System.in);
        System.out.println("Вид из квартиры задаётся одной из следующих констант:");
        View[] views = View.values();
        View view;
        for (int i =0;i<views.length;i++){
            System.out.print(views[i].name() + " ");
        }
        System.out.println("Нужно выбрать одну из них");
        String str = informationGetter();
        if(str.length() == 0){
            System.out.println("Это поле остаётся пустым");
            view = null;
        }
        else {
            try {
                view = View.valueOf(str);
            } catch (Exception e) {
                System.out.println("Некорректный ввод данных!\nВведите поле занова");
                view = (new FlatCreator()).createView();
            }
        }
        return view;
    }

    public Transport createTransport(){
//        Scanner input = new Scanner(System.in);
        System.out.println("Транспортные маршруты,проходящие у дома, задаётся одной из следующих констант:");
        Transport[] transports = Transport.values();
        Transport transport;
        for (int i =0;i<transports.length;i++){
            System.out.print(transports[i].name() + " ");
        }
        System.out.println("Нужно выбрать одну из них");
        String str = informationGetter();
        if(str.length() == 0){
            System.out.println("Это поле остаётся пустым");
            transport = null;
        }
        else {
            try {
                transport = Transport.valueOf(str);
            } catch (Exception e) {
                System.out.println("Некорректный ввод данных!\nВведите поле занова");
                transport = (new FlatCreator()).createTransport();
            }
        }
        return transport;
    }

    public House createHouse(){
        House house = new House();
        System.out.println("Следующее поле содержит пораметры дома, в котором находится квартира.\nЕсли это поле нужно оставить пустым - необходимо ввести 0,\nесли оно будет заполняться - 1");
//        Scanner input = new Scanner(System.in);
        String str = informationGetter();
        System.out.println(str);
        if (str.equals("0")){
            System.out.println("Это поле остаётся пустым");
            house = null;
        }
        else {
            if(str.equals("1")){
                house.setName(createHouseName());
                house.setYear(createHouseYear());
                house.setNumberOfFloors(createHouseNumberOfFloors());
                house.setNumberOfFlatsOnFloor(createHouseNumberOfFlatsOnFloor());
                house.setNumberOfLifts(createHouseNumberOfLifts());
            }
            else {
                System.out.println("Некорректный ввод данных!\nВведите поле занова");
                house = (new FlatCreator()).createHouse();
            }
        }
        return house;
    }

    private String createHouseName(){
//        Scanner input = new Scanner(System.in);
        System.out.println("Введите имя дома:");
        String houseName;
        houseName = informationGetter();
        if(houseName.length() == 0){
            System.out.println("Это поле обязательно для заполнения!");
            houseName = (new FlatCreator()).createHouseName();
        }
        return houseName;
    }

    private long createHouseYear(){
//        Scanner input = new Scanner(System.in);
        System.out.println("Введите год постройки дома:");
        long houseYear;
        try {
            houseYear = Long.valueOf(informationGetter());
        }catch (Exception e){
            System.out.println("Некорректный ввод данных!\nВведите поле занова");
            houseYear = (new FlatCreator()).createHouseYear();
        }
        if(houseYear <= 0){
            System.out.println("Значение поля должно быть больше 0!");
            houseYear = (new FlatCreator()).createHouseYear();
        }
        return houseYear;
    }

    private long createHouseNumberOfFloors(){
//        Scanner input = new Scanner(System.in);
        System.out.println("Введите количество этажей в доме:");
        long houseNumberOfFloors;
        try {
            houseNumberOfFloors = Long.valueOf(informationGetter());
        }catch (Exception e){
            System.out.println("Некорректный ввод данных!\nВведите поле занова");
            houseNumberOfFloors = (new FlatCreator()).createHouseYear();
        }
        if((houseNumberOfFloors <= 0) | (houseNumberOfFloors >86)){
            System.out.println("Значение поля должно быть больше 0 и меньше 87!");
            houseNumberOfFloors = (new FlatCreator()).createHouseYear();
        }
        return houseNumberOfFloors;
    }

    private int createHouseNumberOfFlatsOnFloor(){
//        Scanner input = new Scanner(System.in);
        System.out.println("Введите количество квартир на этаже:");
        int houseNumberOfFlatsOnFloor;
        try {
            houseNumberOfFlatsOnFloor = Integer.valueOf(informationGetter());
        }catch (Exception e){
            System.out.println("Некорректный ввод данных!\nВведите поле занова");
            houseNumberOfFlatsOnFloor = (new FlatCreator()).createHouseNumberOfFlatsOnFloor();
        }
        if(houseNumberOfFlatsOnFloor <= 0){
            System.out.println("Значение поля должно быть больше 0!");
            houseNumberOfFlatsOnFloor = (new FlatCreator()).createHouseNumberOfFlatsOnFloor();
        }
        return houseNumberOfFlatsOnFloor;
    }

    private int createHouseNumberOfLifts(){
//        Scanner input = new Scanner(System.in);
        System.out.println("Введите количество лифтов в доме:");
        int houseNumberOfLifts;
        try {
            houseNumberOfLifts = Integer.valueOf(informationGetter());
        }catch (Exception e){
            System.out.println("Некорректный ввод данных!\nВведите поле занова");
            houseNumberOfLifts = (new FlatCreator()).createHouseNumberOfLifts();
        }
        if(houseNumberOfLifts <= 0){
            System.out.println("Значение поля должно быть больше 0!");
            houseNumberOfLifts = (new FlatCreator()).createHouseNumberOfLifts();
        }
        return houseNumberOfLifts;
    }
}
