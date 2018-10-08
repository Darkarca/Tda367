package com.CEYMChat;

import java.io.Serializable;

/**
 * This class is used to send commands to the server from a client.
 * A Command could for example request a "login", a "logout" or a "addToFriends" request.
 */

public class Command implements Serializable {

    private String commandName;
    private String commandData;

    public Command(String commandName, String commandData) {
        this.commandName = commandName;
        this.commandData = commandData;
    }


    public String getCommandName() {
        return commandName;
    }

    public String getCommandData() {
        return commandData;
    }




}

