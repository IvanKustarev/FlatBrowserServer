package CommonClasses;

import java.io.Serializable;

public class User implements Serializable {
//    Подразумевается что пользватель уже зарегестрирован,
//    если нет (и пользователь получен в результате регистрации),
//    то необходимо перевести флаг в true
    private boolean newUser = false;

    private String login = "";
    private String password = "";

    public User(boolean newUser, String login, String password){
        this.login = login;
        this.password = password;
        this.newUser = newUser;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNewUser() {
        return newUser;
    }
}
