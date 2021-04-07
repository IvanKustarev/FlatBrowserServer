package Server.DBWork;

import CommonClasses.ApartmentDescription.*;
import CommonClasses.Flat;

import java.util.Date;

public class TransformerObjectSQL {
    public static String transformFlatObjToSQL(Flat flat){

        String sql  = "INSERT INTO FLATS " +
                    "(USERNAME,ID,NAME,X_COORDINATE,Y_COORDINATE," +
                    "CREATION_DATE,AREA,NUMBER_OF_ROOMS,FURNISH," +
                    "VIEW,TRANSPORT,HOUSE_NAME,HOUSE_YEAR,HOUSE_NUMBEROFFLOORS," +
                    "HOUSE_NUMBEROFFLATSONFLOOR,HOUSE_NUMBEROFLIFTS)" +" VALUES ('" + flat.getUserName() +
                "', " + flat.getId() + ",'" + flat.getName() +
                "', " + flat.getCoordinates().getX() +
                ", " + flat.getCoordinates().getY() +
                ", " + flat.getCreationDate().getTime() +
                ", " + flat.getArea() + ", " + flat.getNumberOfRooms() +
                ", '" + flat.getFurnish().name() + "'";

        if(flat.getView() == null){
            sql += ", NULL";
        }
        else {
            sql += ", '" + flat.getView().name() + "'";
        }
        if(flat.getTransport() == null){
            sql += ", NULL";
        }
        else {
            sql += ", '" + flat.getTransport().name() + "'";
        }
        if(flat.getHouse() == null){
            sql += ", NULL, NULL, NULL, NULL, NUll";
        }
        else {
            sql += ", '" + flat.getHouse().getName() + "', " + flat.getHouse().getYear() + ", " + flat.getHouse().getNumberOfFloors() +
            ", " + flat.getHouse().getNumberOfFlatsOnFloor() + ", " + flat.getHouse().getNumberOfLifts();
        }
        sql += ")";

//        char[] chars = sql.toCharArray();
//        for(int i = 260; i<270;i++){
//            System.out.println(chars[i]);
//        }
//        System.out.println(sql);


//        if(flat.getHouse() == null){
//            sql = "INSERT INTO FLATS " +
//                    "(USERNAME,ID,NAME,X_COORDINATE,Y_COORDINATE," +
//                    "CREATION_DATE,AREA,NUMBER_OF_ROOMS,FURNISH," +
//                    "VIEW,TRANSPORT,HOUSE_NAME,HOUSE_YEAR,HOUSE_NUMBEROFFLOORS," +
//                    "HOUSE_NUMBEROFFLATSONFLOOR,HOUSE_NUMBEROFLIFTS)" +
//                    " VALUES ('" + flat.getUserName() +
//                    "', " + flat.getId() + ",'" + flat.getName() +
//                    "', " + flat.getCoordinates().getX() +
//                    ", " + flat.getCoordinates().getY() +
//                    ", " + flat.getCreationDate().getTime() +
//                    ", " + flat.getArea() + ", " + flat.getNumberOfRooms() +
//                    ", '" + flat.getFurnish().name() +
//                    "', '" + view + "', '" + transport  +
//                    "'," + "'',,,,)";
//        }
//        else {
//            sql = "INSERT INTO FLATS " +
//                    "(USERNAME,ID,NAME,X_COORDINATE,Y_COORDINATE," +
//                    "CREATION_DATE,AREA,NUMBER_OF_ROOMS,FURNISH," +
//                    "VIEW,TRANSPORT,HOUSE_NAME,HOUSE_YEAR,HOUSE_NUMBEROFFLOORS," +
//                    "HOUSE_NUMBEROFFLATSONFLOOR,HOUSE_NUMBEROFLIFTS)" +
//                    " VALUES ('" + flat.getUserName() +
//                    "', " + flat.getId() + ",'" + flat.getName() +
//                    "', " + flat.getCoordinates().getX() +
//                    ", " + flat.getCoordinates().getY() +
//                    ", " + flat.getCreationDate().getTime() +
//                    ", " + flat.getArea() + ", " + flat.getNumberOfRooms() +
//                    ", '" + flat.getFurnish().name() +
//                    "', '" + view + "', '" + transport  +
//                    "', '" + flat.getHouse().getName() + "', " + flat.getHouse().getYear() +", " + flat.getHouse().getNumberOfFloors() +
//                    ", " + flat.getHouse().getNumberOfFlatsOnFloor() +
//                    ", " + flat.getHouse().getNumberOfLifts() + ")";
//        }
        return sql;
    }

    public static Flat transformerSQLtoFlatObject(String sqlFields){

        String[] parameters  = sqlFields.split("\n");
        Flat flat = new Flat();

//        for(int i = 0; i< parameters.length;i++){
//            if(parameters[i].equals("null")){
//                System.out.println(parameters[i]);
//            }
//        }

        flat.setUserName(parameters[0]);
        flat.setId(Long.valueOf(parameters[1]));
        flat.setName(parameters[2]);

        Coordinates coordinates = new Coordinates();
        coordinates.setX(Double.valueOf(parameters[3]));
        coordinates.setY(Integer.valueOf(parameters[4]));
        flat.setCoordinates(coordinates);
        flat.setCreationDate(new Date(Long.valueOf(parameters[5])));
        flat.setArea(Long.valueOf(parameters[6]));
        flat.setNumberOfRooms(Long.valueOf(parameters[7]));
        flat.setFurnish(Furnish.valueOf(parameters[8]));
        if(parameters[9].equals("null")){
            flat.setView(null);
        }
        else {
            flat.setView(View.valueOf(parameters[9]));
        }
        if(parameters[10].equals("null")){
            flat.setTransport(null);
        }
        else {
            flat.setTransport(Transport.valueOf(parameters[10]));
        }
        if(parameters[11].equals("null")){
            flat.setHouse(null);
        }
        else {
            House house = new House();
            house.setName(parameters[11]);
            house.setNumberOfFloors(Long.valueOf(parameters[12]));
            house.setNumberOfFlatsOnFloor(Integer.valueOf(parameters[13]));
            house.setNumberOfLifts(Long.valueOf(parameters[14]));
            flat.setHouse(house);
        }

        return flat;
    }
}
