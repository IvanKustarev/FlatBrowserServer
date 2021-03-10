package CommonClasses;

import java.io.BufferedReader;
import java.io.Serializable;
import java.util.Stack;

public enum CommandsData implements Serializable {
    HELP {
        @Override
        public String toString(){
            return "help";
        }

        {
            comandWithElementParameter = false;
        }
    },
    INFO{
        @Override
        public String toString(){
            return "info";
        }

        {
            comandWithElementParameter = false;
        }
    },
    SHOW{
        @Override
        public String toString(){
            return "show";
        }

        {
            comandWithElementParameter = false;
        }
    },
    ADD{
        @Override
        public String toString(){
            return "add";
        }

        {
            comandWithElementParameter = true;
        }
    },
    UPDATE{
        @Override
        public String toString(){
            return "update";
        }

        {
            comandWithElementParameter = true;
        }
    },
    REMOVEBYID{
        @Override
        public String toString(){
            return "remove_by_id";
        }

        {
            comandWithElementParameter = false;
        }
    },
    CLEAR{
        @Override
        public String toString() {
            return "clear";
        }

        {
            comandWithElementParameter = false;
        }
    },
    EXECUTESCRIPT{
        @Override
        public String toString(){
            return "execute_script";
        }

        {
            comandWithElementParameter = false;
        }
    },
    EXIT{
        @Override
        public String toString(){
            return "exit";
        }

        {
            comandWithElementParameter = null;
        }
    },
    REMOVEHEAD{
        @Override
        public String toString(){
            return "remove_head";
        }

        {
            comandWithElementParameter = false;
        }
    },
    ADDIFMIN{
        @Override
        public String toString(){
            return "add_if_min";
        }

        {
            comandWithElementParameter = true;
        }
    },
    REMOVELOWER{
        @Override
        public String toString(){
            return "remove_lower";
        }

        {
            comandWithElementParameter = true;
        }
    },
    SUMOFNUMBEROFROOMS{
        @Override
        public String toString(){
            return "sum_of_number_of_rooms";
        }

        {
            comandWithElementParameter = false;
        }
    },
    FILTERLESSTHANTRANSPORT{
        @Override
        public String toString(){
            return "filter_less_than_transport";
        }

        {
            comandWithElementParameter = false;
        }
    },
    PRINTFIELDASCENDINGNUMBEROFROOMS{

        {
            comandWithElementParameter = false;
        }

        @Override
        public String toString(){
            return "print_field_ascending_number_of_rooms";
        }
    };

    //    protected Boolean parameteringCommand = null;
    protected Boolean comandWithElementParameter = null;
//    protected Boolean neededCollectionCommand = null;

    public Boolean isComandWithElementParameter() {
        return comandWithElementParameter;
    }

//    public Boolean getNeededCollectionCommand() {
//        return neededCollectionCommand;
//    }
//
//    public Boolean getParameteringCommand() {
//        return parameteringCommand;
//    }

    public void setComandWithElementParameter(Boolean comandWithElementParameter) {
        this.comandWithElementParameter = comandWithElementParameter;
    }

    //    public void setNeededCollectionCommand(Boolean neededCollectionCommand) {
//        this.neededCollectionCommand = neededCollectionCommand;
//    }
//
//    public void setParameteringCommand(Boolean parameteringCommand) {
//        this.parameteringCommand = parameteringCommand;
//    }
    //L6User.User or Script
    private Creator creator;

    public void setCreator(Creator creator){
        this.creator = creator;
    }

    public Creator getCreator() {
        return creator;
    }

    private String parameter = "";

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }

    private Flat flat = null;

    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    public Flat getFlat() {
        return flat;
    }


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
}
