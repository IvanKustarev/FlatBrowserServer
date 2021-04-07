package Server.DBWork;


//import Server.org.postgresql.ds.common.*;

//import org.postgresql.ds.PGSimpleDataSource;

import java.sql.*;

public class Connector {
    private Connection connection;
//    private Statement statement;

    public Connector(){
        connect();
    }

    private Connection connect(){





//        PGSimpleDataSource ds = new PGSimpleDataSource();
//        ds.setServerName("localhost");
//        ds.setDatabaseName("studs");
//        ds.setUser("s309681");
//        ds.setPassword("yvr557");
//        try {
//            Connection connection = ds.getConnection();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://pg:5432/studs", "s309681", "yvr557");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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
