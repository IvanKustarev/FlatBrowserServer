package L6Server.Commands;

import CommonClasses.*;
import L6Server.TransferCenter;

public interface Command {
    void execute(CommandsData command, TransferCenter transferCenter, CommandsData commandsData);
}
