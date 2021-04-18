package CommonClasses.ApartmentDescription;

import java.io.Serializable;

public class House implements Attractive , Serializable {
    private String name; //Поле не может быть null
    private long year; //Значение поля должно быть больше 0
    private long numberOfFloors; //Максимальное значение поля: 86, Значение поля должно быть больше 0
    private int numberOfFlatsOnFloor; //Значение поля должно быть больше 0
    private long numberOfLifts; //Значение поля должно быть больше 0

    public String getFields(){
        String str = new String();
        str += String.format("%-30s %s \n", "   house name", name);
        str += String.format("%-30s %s \n", "   house year", year);
        str += String.format("%-30s %s \n", "   house numberOfFloors", numberOfFloors);
        str += String.format("%-30s %s \n", "   house numberOfFlatsOnFloor", numberOfFlatsOnFloor);
        str += String.format("%-30s %s \n", "   house numberOfLifts", numberOfLifts);
        return str;
    }

    @Override
    public int levelAttractive() {
        int goodnessNumberOfFlatsOnFloor = 0;
        if(numberOfFlatsOnFloor < 5){
            goodnessNumberOfFlatsOnFloor = 1;
        }
        if(numberOfFlatsOnFloor < 3){
            goodnessNumberOfFlatsOnFloor = 2;
        }

        return (int) (year/1000 + (86-numberOfFlatsOnFloor)/15 + goodnessNumberOfFlatsOnFloor + numberOfLifts);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfFlatsOnFloor(int numberOfFlatsOnFloor) {
        this.numberOfFlatsOnFloor = numberOfFlatsOnFloor;
    }

    public void setNumberOfFloors(long numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public void setNumberOfLifts(long numberOfLifts) {
        this.numberOfLifts = numberOfLifts;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfFlatsOnFloor() {
        return numberOfFlatsOnFloor;
    }

    public long getNumberOfFloors() {
        return numberOfFloors;
    }

    public long getNumberOfLifts() {
        return numberOfLifts;
    }

    public long getYear() {
        return year;
    }

}