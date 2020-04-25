package ru.geekbrains.java2.client.command;

import java.io.Serializable;
import java.util.List;

public class UpdateUserListCommand implements Serializable {

    private final List<String> users;

    public UpdateUserListCommand(List<String> users){
        this.users = users;
    }

    public List<String> getUsers() {
        return users;
    }
}
