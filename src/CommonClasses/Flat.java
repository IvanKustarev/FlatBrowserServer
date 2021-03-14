package CommonClasses;



import CommonClasses.ApartmentDescription.*;

import java.io.Serializable;
import java.lang.instrument.Instrumentation;
import java.util.Date;

public class Flat implements Serializable, Comparable<Flat>{

    static final long serialVersionUID = 2;

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long area; //Значение поля должно быть больше 0
    private long numberOfRooms; //Значение поля должно быть больше 0
    private Furnish furnish; //Поле не может быть null
    private CommonClasses.ApartmentDescription.View view; //Поле может быть null
    private Transport transport; //Поле может быть null
    private House house; //Поле может быть null

    public void show(){
        System.out.printf("%-30s %s \n", "id", id);
        System.out.printf("%-30s %s \n", "name", name);
        System.out.println("coordinates:");
        coordinates.show();
//        System.out.println("creationDate:\t\t\t\t" + creationDate);
        System.out.printf("%-30s %s \n", "creationDate", creationDate);
        System.out.printf("%-30s %s \n", "area", area);
        System.out.printf("%-30s %s \n", "numberOfRooms", numberOfRooms);
        System.out.printf("%-30s %s \n", "furnish", furnish.name());
//        System.out.println("area:\t\t\t\t" + area);
//        System.out.println("numberOfRooms:\t\t\t\t" + numberOfRooms);
//        System.out.println("furnish:\t\t\t\t"+furnish.name());

        try {
//            System.out.println("view:\t\t\t\t" + view.name());
            System.out.printf("%-30s %s \n", "view", view.name());
        }catch (Exception e){
            System.out.println("view - пустое поле");
        }
        try {
//            System.out.println("transport:\t\t\t\t" + transport.name());
            System.out.printf("%-30s %s \n", "transport", transport.name());
        }catch (Exception e){
            System.out.println("transport - пустое поле");
        }
        try {
            System.out.println("house:");
            house.getFields();
        }catch (Exception e){
            System.out.println("house - пустое поле");
        }



        System.out.println("\n\n");
    }

    public static Flat createFlat(Long id){
        Flat flat = new Flat();
        FlatCreator flatCreator = new FlatCreator();

        flat.setId(id);
        flat.setName(flatCreator.createName());
        flat.setCoordinates(flatCreator.createCoordinates());
//        flat.setCreationDate(new Date());
        flat.setCreationDate(null);
        flat.setArea(flatCreator.createArea());
        flat.setNumberOfRooms(flatCreator.createNumberOfRooms());
        flat.setFurnish(flatCreator.createFurnish());
        flat.setView(flatCreator.createView());
        flat.setTransport(flatCreator.createTransport());
        flat.setHouse(flatCreator.createHouse());
        return flat;
    }



    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Long getArea() {
        return area;
    }

    public long getNumberOfRooms() {
        return numberOfRooms;
    }

    public House getHouse(){
        return this.house;
    }

    public Coordinates getCoordinates(){
        return this.coordinates;
    }

    public Transport getTransport(){
        return  this.transport;
    }

    public CommonClasses.ApartmentDescription.View getView(){
        return  this.view;
    }
    public Furnish getFurnish(){
        return  this.furnish;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setFurnish(Furnish furnish) {
        this.furnish = furnish;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfRooms(long numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public void setView(CommonClasses.ApartmentDescription.View view) {
        this.view = view;
    }

//    @Override
//    public int compareTo(CommonClasses.Flat o) {
//
//        if((getNumberOfRooms() > Integer.MAX_VALUE) | (o.getNumberOfRooms() > Integer.MAX_VALUE)){
//            return (int)(getNumberOfRooms() - Integer.MAX_VALUE) - (int)(o.getNumberOfRooms() - Integer.MAX_VALUE);
//
//        }
//        else {
//            return (int) getNumberOfRooms() - (int) o.getNumberOfRooms();
//        }
//    }

//    @Override
//    public int compareTo(CommonClasses.Flat o) {
//
//        if((getNumberOfRooms() > Integer.MAX_VALUE) | (o.getNumberOfRooms() > Integer.MAX_VALUE)){
//            return (int)(o.getNumberOfRooms() - Integer.MAX_VALUE) - (int)(getNumberOfRooms() - Integer.MAX_VALUE);
//
//        }
//        else {
//            return (int) o.getNumberOfRooms() - (int) getNumberOfRooms();
//        }
//    }

    @Override
    public int compareTo(Flat flat){
        if((ComparisonOfAttractiveness.compare(this) > Integer.MAX_VALUE) | (ComparisonOfAttractiveness.compare(flat) > Integer.MAX_VALUE)){
            return (int)(ComparisonOfAttractiveness.compare(flat) - Integer.MAX_VALUE) - (int)(ComparisonOfAttractiveness.compare(this) - Integer.MAX_VALUE);

        }
        else {
            return (int) ComparisonOfAttractiveness.compare(flat) - (int) ComparisonOfAttractiveness.compare(this);
        }
    }
}
