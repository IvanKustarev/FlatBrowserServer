package CommonClasses;

import java.io.BufferedReader;
import java.io.Serializable;
import java.util.Stack;

public class DataBlock implements Serializable {
    static final long serialVersionUID = 1;

    private User user;

    private CommandsData commandsData = null;

    public void setCommandsData(CommandsData commandsData) {
        this.commandsData = commandsData;
    }

    public CommandsData getCommandsData() {
        return commandsData;
    }

    //    commandWithElementParameter ======================================================
    protected Boolean commandWithElementParameter = null;
    public Boolean isCommandWithElementParameter() {
        return commandWithElementParameter;
    }
    public void setCommandWithElementParameter (Boolean commandWithElementParameter){
        this.commandWithElementParameter = commandWithElementParameter;
    }
//    ===============================================================================


    //    Creator: User or Script ===========================
    private Creator creator;
    public void setCreator(Creator creator){
        this.creator = creator;
    }
    public Creator getCreator() {
        return creator;
    }
//    ==================================================


    //    Parameter ==========================================
    public String parameter = "";
    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
    public String getParameter() {
        return parameter;
    }
//    ====================================================


    //    Flat Element ========================================
    private Flat flat = null;

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public Flat getFlat() {
        return flat;
    }
//    ====================================================


    private BufferedReader bufferedReader = null;

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }



    //содержит null пока не начнётся скрипт
    Stack<String> openingFiles = null;

    public void addOpeningFile(String openingFile) {
        this.openingFiles.push(openingFile);
    }

    public Stack<String> getOpeningFiles() {
        return openingFiles;
    }

    public void setOpeningFiles(Stack<String> openingFiles) {
        this.openingFiles = openingFiles;
    }


    private boolean commandEnded = false;

    public boolean isCommandEnded() {
        return commandEnded;
    }

    public void setCommandEnded(boolean commandEnded) {
        this.commandEnded = commandEnded;
    }




    private String phrase = null;

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }




    public boolean isServerNeedStringParameter = false;
    public boolean isServerNeedElementParameter = false;
    public boolean isUserNeedToShowFlatArr = false;

    public boolean isServerNeedElementParameter() {
        return isServerNeedElementParameter;
    }

    public boolean isServerNeedStringParameter() {
        return isServerNeedStringParameter;
    }

    public boolean isUserNeedToShowFlatArr() {
        return isUserNeedToShowFlatArr;
    }

    public void setServerNeedElementParameter(boolean serverNeedElementParameter) {
        isServerNeedElementParameter = serverNeedElementParameter;
    }

    public void setServerNeedStringParameter(boolean serverNeedStringParameter) {
        isServerNeedStringParameter = serverNeedStringParameter;
    }

    public void setUserNeedToShowFlatArr(boolean userNeedToShowFlatArr) {
        isUserNeedToShowFlatArr = userNeedToShowFlatArr;
    }




    public Flat[] flats = null;

    public void setFlats(Flat[] flats) {
        this.flats = flats;
    }

    public Flat[] getFlats() {
        return flats;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
