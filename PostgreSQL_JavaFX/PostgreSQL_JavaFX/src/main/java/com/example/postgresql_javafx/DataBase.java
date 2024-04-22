package com.example.postgresql_javafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
    private static final String url = "jdbc:postgresql://localhost:5432/middleJava";
    private static final String user = "postgres";
    private static final String password = "1111";
    public static final Connection connection;
    static {
        try {
            connection = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
