package CommonClasses;



import CommonClasses.ApartmentDescription.*;

import java.io.Serializable;
import java.lang.instrument.Instrumentation;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class Flat implements Serializable, Comparable<Flat>{

    static final long serialVersionUID = 2;

    private String userName;

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

    public String show(Locale locale){

//        DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale).format(creationDate);

        String str = new String();

        str += String.format("%-30s %s \n", "userName", userName);
        str += String.format("%-30s %s \n", "id", id);
        str += String.format("%-30s %s \n", "name", name);
        str += String.format("coordinates:");
        str += coordinates.show();
        str += String.format("%-30s %s \n", "creationDate", DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale).format(creationDate));
        str += String.format("%-30s %s \n", "area", area);
        str += String.format("%-30s %s \n", "numberOfRooms", numberOfRooms);
        str += String.format("%-30s %s \n", "furnish", furnish.name());

        try {
            str += String.format("%-30s %s \n", "view", view.name());
        }catch (Exception e){
            str += String.format("view - пустое поле");
        }
        try {
            str += String.format("%-30s %s \n", "transport", transport.name());
        }catch (Exception e){
            str += String.format("transport - пустое поле");
        }
        try {
            str += String.format("house:");
            str += house.getFields();
        }catch (Exception e){
            str += String.format("house - пустое поле");
        }

        return str;
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


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
