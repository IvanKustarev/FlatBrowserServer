package Server.BDWork;


//import Server.org.postgresql.ds.common.*;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.ConnectionPoolDataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    Connection connection;

    public void connect(){





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
            connection = DriverManager.getConnection("localhost", "s309681", "yvr557");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
