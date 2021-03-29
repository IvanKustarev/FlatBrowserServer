//package L6Server.Commands;
//
//import L5.FlatCollection;
//
//public class SaveCommand implements Command {
//
//    private FlatCollection flatCollection;
//    private String fileAddress;
//
//    public SaveCommand(FlatCollection flatCollection, String fileAddress){
//        this.flatCollection = flatCollection;
//        this.fileAddress = fileAddress;
//    }
//
//    @Override
//    public String toString(){
//        return "save : сохранить коллекцию в файл";
//    }
//
//    @Override
//    public void execute(CommandsData command) {
//        flatCollection.save(flatCollection, fileAddress);
//    }
//}
