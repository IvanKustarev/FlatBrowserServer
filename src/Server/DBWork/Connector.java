package Server.DBWork;

import org.postgresql.util.PSQLException;

import java.sql.*;

public class Connector {
    private Connection connection;
//    private Statement statement;

    public Connector() throws SQLException{
        connect();
    }

    private Connection connect() throws SQLException {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }


            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", "s309681", "yvr557");


        try {
            connection.setAutoCommit(false);
        }catch (Exception e){
            e.printStackTrace();
        }

        return connection;

    }

    public Connection getConnection() {
        return connection;
    }
}
