package CommonClasses;

import java.io.BufferedReader;
import java.io.Serializable;
import java.util.Stack;

public enum CommandsData implements Serializable {
    CHECKUSER{
        @Override
        public String toString(){
            return "check_user";
        }
        {
            commandWithElementParameter = false;
        }
    },
    HELP {
        @Override
        public String toString(){
            return "help";
        }

        {
            commandWithElementParameter = false;
        }
    },
    INFO{
        @Override
        public String toString(){
            return "info";
        }

        {
            commandWithElementParameter = false;
        }
    },
    SHOW{
        @Override
        public String toString(){
            return "show";
        }

        {
            commandWithElementParameter = false;
        }
    },
    ADD{
        @Override
        public String toString(){
            return "add";
        }

        {

            commandWithElementParameter = true;
        }
    },
    UPDATE{
        @Override
        public String toString(){
            return "update";
        }

        {
            commandWithElementParameter = false;
        }
    },
    REMOVEBYID{
        @Override
        public String toString(){
            return "remove_by_id";
        }

        {
            commandWithElementParameter = false;
        }
    },
    CLEAR{
        @Override
        public String toString() {
            return "clear";
        }

        {
            commandWithElementParameter = false;
        }
    },
    EXECUTESCRIPT{
        @Override
        public String toString(){
            return "execute_script";
        }

        {
            commandWithElementParameter = false;
        }
    },
    EXIT{
        @Override
        public String toString(){
            return "exit";
        }

        {
            commandWithElementParameter = null;
        }
    },
    REMOVEHEAD{
        @Override
        public String toString(){
            return "remove_head";
        }

        {
            commandWithElementParameter = false;
        }
    },
    ADDIFMIN{
        @Override
        public String toString(){
            return "add_if_min";
        }

        {
            commandWithElementParameter = true;
        }
    },
    REMOVELOWER{
        @Override
        public String toString(){
            return "remove_lower";
        }

        {
            commandWithElementParameter = true;
        }
    },
    SUMOFNUMBEROFROOMS{
        @Override
        public String toString(){
            return "sum_of_number_of_rooms";
        }

        {
            commandWithElementParameter = false;
        }
    },
    FILTERLESSTHANTRANSPORT{
        @Override
        public String toString(){
            return "filter_less_than_transport";
        }

        {
            commandWithElementParameter = false;
        }
    },
    PRINTFIELDASCENDINGNUMBEROFROOMS{

        {
            commandWithElementParameter = false;
        }

        @Override
        public String toString(){
            return "print_field_ascending_number_of_rooms";
        }
    };

    static final long serialVersionUID = 5;

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




//    private DataBlock dataBlock = null;
//
//    public DataBlock getDataBlock() {
//        return dataBlock;
//    }
//
//    public void setDataBlock(DataBlock dataBlock) {
//        this.dataBlock = dataBlock;
//    }




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
}
