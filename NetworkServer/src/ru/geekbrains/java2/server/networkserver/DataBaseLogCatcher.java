package ru.geekbrains.java2.server.networkserver;

import javax.crypto.spec.PSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DataBaseLogCatcher {
    protected static Connection connection;
    protected static Statement statement;
    protected Date date = new Date();

    public DataBaseLogCatcher() throws SQLException, ClassNotFoundException {
        connect();
        createTable();
    }

    private void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS logData ( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "Information TEXT NOT NULL," +
                "Date TEXT NOT NULL)";
        statement.executeUpdate(sql);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:NetworkServer/LogData.db");
        statement = connection.createStatement();
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String insertData) throws SQLException {
        if (connection != null){
            String d = date.toString();
            String sql = "INSERT INTO logData ('Information', 'Date') VALUES (" + insertData + "'" + d + "')";
            statement.executeUpdate(sql);
        }else{
            System.err.println("Connection to dataBase is not exist!");
        }
    }

}
