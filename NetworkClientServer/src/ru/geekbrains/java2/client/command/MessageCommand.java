package ru.geekbrains.java2.client.command;

import java.io.Serializable;

public class MessageCommand implements Serializable {

    private final String username;
    private final String message;


    public MessageCommand(String username, String message){
        this.message = message;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }
}
