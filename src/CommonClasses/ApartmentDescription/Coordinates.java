package CommonClasses.ApartmentDescription;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private double x; //Значение поля должно быть больше -587
    private Integer y; //Максимальное значение поля: 77, Поле не может быть null

    public void show(){

        System.out.printf("%-30s %s \n", "   coordinate x", x);
        System.out.printf("%-30s %s \n", "   coordinate y", y);

    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }
}