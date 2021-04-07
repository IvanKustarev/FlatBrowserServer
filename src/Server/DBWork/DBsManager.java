package Server.DBWork;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBsManager {

    public void createNewFlatTable(Connection connection){
        Statement statement = null;
        String sql;
        try {
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            statement = connection.createStatement();
            sql = "CREATE TABLE FLATS" +
                    " (USERNAME TEXT NOT NULL," +
//                    "ID uniqueidentifier NOT NULL " +
                    "ID BIGINT NOT NULL," +
//                    "DEFAULT newid()," +
                    "NAME TEXT NOT NULL," +
                    "X_COORDINATE DOUBLE PRECISION NOT NULL," +
                    "Y_COORDINATE INT NOT NULL," +
                    "CREATION_DATE BIGINT NOT NULL," +
                    "AREA BIGINT NOT NULL," +
                    "NUMBER_OF_ROOMS BIGINT NOT NULL," +
                    "FURNISH TEXT NOT NULL," +
                    "VIEW TEXT," +
                    "TRANSPORT TEXT," +
                    "HOUSE_NAME TEXT," +
                    "HOUSE_YEAR BIGINT," +
                    "HOUSE_NUMBEROFFLOORS BIGINT," +
                    "HOUSE_NUMBEROFFLATSONFLOOR INT," +
                    "HOUSE_NUMBEROFLIFTS BIGINT)";

            statement.executeUpdate(sql);
            statement.close();
            connection.commit();
            System.out.println("Создал таблицу квртир");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteTable(Connection connection, String tableName){
        try {
//            System.out.println("ttt");
            String sql = "DROP TABLE " + tableName;
            Statement statement = connection.createStatement();
//            System.out.println("kkk");
            statement.executeUpdate(sql);
//            System.out.println("ppp");
            statement.close();
            connection.commit();
            System.out.println("Удалил таблицу " + tableName);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void createNewUsersTable(Connection connection){
        Statement statement = null;
        String sql;
        try {
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            statement = connection.createStatement();
            sql = "CREATE TABLE USERS" +
                    " (LOGIN TEXT NOT NULL," +
                    "PASSWORD TEXT, SALT TEXT)";
            statement.executeUpdate(sql);
            statement.close();
            connection.commit();
            System.out.println("Создал таблицу пользователей");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
