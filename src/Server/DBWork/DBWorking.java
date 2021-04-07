package Server.DBWork;

import CommonClasses.Flat;
import CommonClasses.User;

public interface DBWorking {
    boolean pushNewFlat(Flat flat);
    AnswerDBWorkerCommands pushNewUser(User user);
    boolean load();
    AnswerDBWorkerCommands deleteFlatByID(long id, User user);
    AnswerDBWorkerCommands clearFlats(User user);
    AnswerDBWorkerCommands checkUser(User user);
}
