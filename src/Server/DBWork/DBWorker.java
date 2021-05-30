package Server.DBWork;

import CommonClasses.Flat;
import CommonClasses.User;
import Server.FlatCollectionWorkers.FlatCollection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

public class DBWorker implements  DBWorking{

    final String PEPPER = "3hju.65()Ukwkjd";

    FlatCollection flatCollection = null;
    Connection connection;

    public DBWorker(FlatCollection flatCollection, Connection connection){
        this.flatCollection = flatCollection;

        try {
            this.connection = connection;
        }catch (Exception e){}
    }

    @Override
    public boolean pushNewFlat(Flat flat) {
        try {
            String sql = TransformerObjectSQL.transformFlatObjToSQL(flat);
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.commit();
//            System.out.println("pushNewFlat");
            return true;
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    @Override
    public AnswerDBWorkerCommands pushNewUser(User user) {
        String answerString;
        if(!checkUserByName(user.getLogin())){
            String sql;
            if(user.getPassword() != null){
                String salt = generateString(new Random(), 10);
                sql = "INSERT INTO USERS " +
                        "(LOGIN, PASSWORD, SALT) VALUES ('" + user.getLogin() + "', '" + gettingPasswordHash(user.getPassword(), salt) + "', '" + salt + "');";
            }
            else {
                sql = "INSERT INTO USERS " +
                        "(LOGIN) VALUES ('" + user.getLogin() + "');";
            }

            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                statement.close();
                connection.commit();
                answerString = "Пользователь успешно добавлен";
//                System.out.println(answerString);
//                return true;
                return new AnswerDBWorkerCommands(true, answerString);
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
            answerString = "Ошибка во время добавления пользователя";
//            System.out.println(answerString);
//            return false;
            return new AnswerDBWorkerCommands(false, answerString);
        }
        else {
            answerString = "Пользователь с таким логином уже существует.";
//            System.out.println(answerString);
//            return false;
            return new AnswerDBWorkerCommands(false, answerString);
        }
    }

    private String gettingPasswordHash(String password, String salt){
        byte[] passHash = new byte[0];
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            passHash = md5.digest((PEPPER + password + salt).getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String str = bytesToStringUTFCustom(passHash);
        return str;
    }
    private boolean checkUserByName(String name){
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM USERS where LOGIN='" + name + "';");
            while (rs.next()) {
                rs.close();
                statement.close();
                return true;
            }
            rs.close();
            statement.close();
            return false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }

    @Override
    public boolean load(){
        try {
            Statement statement;
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM FLATS");
            flatCollection.clearMemoryCollection(); //очищаем то, что сейчас находится в коллекции
            while (rs.next()){
                String sql = rs.getString("username") + "\n" +
                rs.getString("id") + "\n" +
                rs.getString("name") + "\n" +
                rs.getString("x_coordinate") + "\n" +
                rs.getString("y_coordinate") + "\n" +
                rs.getString("creation_date") + "\n" +
                rs.getString("area") + "\n" +
                rs.getString("number_of_rooms") + "\n" +
                rs.getString("furnish") + "\n" +
                rs.getString("view") + "\n" +
                rs.getString("transport") + "\n" +
                rs.getString("house_name") + "\n" +
                rs.getString("house_numberoffloors") + "\n" +
                rs.getString("house_numberofflatsonfloor") + "\n" +
                rs.getString("house_numberoflifts");

                flatCollection.add(TransformerObjectSQL.transformerSQLtoFlatObject(sql));
            }
            rs.close();
            statement.close();
            connection.commit();

//            System.out.println("load");
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  false;
    }

    @Override
    public AnswerDBWorkerCommands deleteFlatByID(long id, User user) {



//        try {
//            Statement statement = null;
//            statement = connection.createStatement();
//            ResultSet rs = statement.executeQuery("SELECT * FROM FLATS where USERNAME='" + user.getLogin() + "' AND ID=" + id + ";");
//
//            while (rs.next()) {
//                rs.close();
//                statement.close();
//                return new AnswerBDWorkerCommands(true, "Удалил");
//            }
//            rs.close();
//            statement.close();
//            return new AnswerBDWorkerCommands(false, "Нечего удалять");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return new AnswerBDWorkerCommands(false, "kkk");


        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM FLATS where USERNAME='" + user.getLogin() + "';");
            if(resultSet.next()){
            }
            else {
                AnswerDBWorkerCommands answerDBWorkerCommands = new AnswerDBWorkerCommands(false, "Нет подходящего объекта,создателем которого является данный пользователь!");
                return answerDBWorkerCommands;
            }
            resultSet.close();
            statement.close();
            connection.commit();
        }catch (Exception e){
            e.printStackTrace();
            AnswerDBWorkerCommands answerDBWorkerCommands = new AnswerDBWorkerCommands(false, "Ошибка во время выбора объекта для удаления!");
            return answerDBWorkerCommands;
        }
//      return new AnswerBDWorkerCommands(false, "kkk");
//
//
//
//
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from FLATS where ID=" + id + " AND USERNAME='" + user.getLogin() + "';";
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
            return new AnswerDBWorkerCommands(true, "Удаление прошло успешно.");
        } catch (Exception throwables) {
//            throwables.printStackTrace();
            return new AnswerDBWorkerCommands(false, "Проблема при удалении существующего и принадлежащего данному пользователю объекта!");
        }
    }

    @Override
    public AnswerDBWorkerCommands clearFlats(User user) {

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM FLATS where USERNAME='" + user.getLogin() + "';");
            if(resultSet.next()){
            }
            else {
                AnswerDBWorkerCommands answerDBWorkerCommands = new AnswerDBWorkerCommands(false, "Нет подходящих объектов,создателем которых является данный пользователь!");
                return answerDBWorkerCommands;
            }
            resultSet.close();
            statement.close();
            connection.commit();
        }catch (Exception e){
            e.printStackTrace();
            AnswerDBWorkerCommands answerDBWorkerCommands = new AnswerDBWorkerCommands(false, "Ошибка во время выбора объектов для удаления!");
            return answerDBWorkerCommands;
        }

        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from FLATS where USERNAME='" + user.getLogin() + "';";
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
            return new AnswerDBWorkerCommands(true, "Очистка элементов, принадлежащих " + user.getLogin() + " прошла успешно.");
        } catch (Exception throwables) {
            return new AnswerDBWorkerCommands(false, "Проблема при удалении существующего и принадлежащего данному пользователю объекта!");
        }
    }

    @Override
    public AnswerDBWorkerCommands checkUser(User user) {
        String answer;
        if(!checkUserByName(user.getLogin())){
            answer = "Пользователя с таким логином не существует!";
//            System.out.println(answer);
//            return false;
            return new AnswerDBWorkerCommands(false, answer);
        }

        if(user.getPassword() != null){
            try {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM USERS where LOGIN='" + user.getLogin() + "';");
                rs.next();
                String userPassFromDB = rs.getString("password");
                if(userPassFromDB.equals(gettingPasswordHash(user.getPassword(), gettingUserSalt(user.getLogin())))){
                    rs.close();
                    statement.close();
                    answer = "Пользователь успешно найден!";
//                    System.out.println(answer);
//                    return true;
                    return new AnswerDBWorkerCommands(true, answer);
                }
                rs.close();
                statement.close();
                answer = "Неверный пароль!";
//                System.out.println(answer);
//                return false;
                return new AnswerDBWorkerCommands(false, answer);
            }catch (Exception e){
                e.printStackTrace();
            }
            answer = "Ошибка при поиске пользователя!";
//            System.out.println(answer);
//            return  false;
            return new AnswerDBWorkerCommands(false, answer);
        }
        else {
            try {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM USERS where LOGIN='" + user.getLogin() + "';");
                if(rs.getString("password").equals("null")){
                    rs.close();
                    statement.close();
                    answer = "Пользователь найден.";
                    return new AnswerDBWorkerCommands(true, answer);
                }
                rs.close();
                statement.close();
                answer = "У пользователя есть пароль!";
//                System.out.println(answer);
//                return false;
                return new AnswerDBWorkerCommands(false, answer);
            }catch (Exception e){
                e.printStackTrace();
            }
            answer = "Ошибка при поиске пользователя.";
//            System.out.println(answer);
//            return  false;
            return new AnswerDBWorkerCommands(false, answer);
        }

    }


    public Connection getConnection() {
        return connection;
    }

    public static String generateString(Random rng,  int length)
    {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()|?";
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    public static String bytesToStringUTFCustom(byte[] bytes) {

        char[] buffer = new char[bytes.length >> 1];

        for(int i = 0; i < buffer.length; i++) {

            int bpos = i << 1;

            char c = (char)(((bytes[bpos]&0x00FF)<<8) + (bytes[bpos+1]&0x00FF));

            buffer[i] = c;

        }

        return new String(buffer);
    }

    private String gettingUserSalt(String name){
        String salt = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM USERS where LOGIN='" + name + "';");
            rs.next();
            salt = rs.getString("salt");
            rs.close();
            statement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return salt;
    }
}
