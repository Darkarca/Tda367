package com.CEYMChatLib;

import java.io.Serializable;
/**
 * This class is used to send commands to the server from a client.
 * A COMMAND could for example request a
 * "login", a "logout" or a "addToFriends" request.
 */

public class Command implements Serializable {

    private final CommandName commandName;
    private final String commandData;

    public Command(CommandName commandName, String commandData) {
        this.commandName = commandName;
        this.commandData = commandData;
    }

    /** Getters and setters **/
    public CommandName getCommandName() {
        return commandName;
    }

    public String getCommandData() {
        return commandData;
    }




}

