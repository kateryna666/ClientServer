package com.company;

import java.util.Arrays;

public class Command {
    private int commandID;
    private byte[] commandBody;

    public Command(){}
    public Command(int commandID, byte[] commandBody){
        this.commandID = commandID;
        this.commandBody = commandBody;
    }

    public int getCommandID() {
        return commandID;
    }

    public void setCommandID(int commandID) {
        this.commandID = commandID;
    }

    public byte[] getCommandBody() {
        return commandBody;
    }

    public void setCommandBody(byte[] commandBody) {
        this.commandBody = commandBody;
    }

    @Override
    public String toString() {
        return "Command{" +
                "commandID=" + commandID +
                ", commandBody=" + Arrays.toString(commandBody) +
                '}';
    }
}
