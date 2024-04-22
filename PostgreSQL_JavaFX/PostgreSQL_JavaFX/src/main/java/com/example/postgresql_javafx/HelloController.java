package com.example.postgresql_javafx;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.*;

public class HelloController {

    @FXML
    public TextField email;
    @FXML
    public TextField passwords;
    @FXML
    public TextField key;


    @FXML
    protected void sendData() throws Exception {

        String sqlInsert = "INSERT INTO middlejava(name, phone) " +
                "VALUES (" +"'"+ email.getText()+"'"+ "," +"'" + passwords.getText()+"'" + ")";
        PreparedStatement preparedStatement = DataBase.connection.prepareStatement(sqlInsert);
        preparedStatement.executeUpdate();

    }
    @FXML
    public void getAllData() throws Exception{

        String sqlInsert = "SELECT * FROM middleJava";
        Statement statement = DataBase.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlInsert);
        while (resultSet.next()){
            for (int i = 1; i <4 ; i++) {
                System.out.print(resultSet.getString(i)+" ");
            }
            System.out.println("-------------");

        }

    }
    @FXML
    public void getSorted() throws Exception{
        String keys = key.getText();
        String sql = "SELECT * FROM middlejava ORDER BY "+keys+" DESC";
        Statement statement = DataBase.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            System.out.println("------------------------");
            for (int i = 1; i <4 ; i++) {
                System.out.print(resultSet.getString(i)+" | ");
            }
            System.out.println();
        }

    }
}
