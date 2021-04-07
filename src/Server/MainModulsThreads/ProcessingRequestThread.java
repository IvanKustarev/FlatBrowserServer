package Server.MainModulsThreads;

import Server.DataPacket;
import Server.WorkWithUser;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class ProcessingRequestThread extends RecursiveAction {

    ConcurrentLinkedQueue<DataPacket> answersWaitingSending = null;
    DataPacket command = null;
    WorkWithUser workWithUser = null;

    public ProcessingRequestThread(ConcurrentLinkedQueue<DataPacket> answersWaitingSending, DataPacket command, WorkWithUser workWithUser){
        this.answersWaitingSending = answersWaitingSending;
        this.command = command;
        this.workWithUser = workWithUser;
    }

    @Override
    protected void compute() {
        workWithUser.startWorkWithUser(command, answersWaitingSending);
    }
}
