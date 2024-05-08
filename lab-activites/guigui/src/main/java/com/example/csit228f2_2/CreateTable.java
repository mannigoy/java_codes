package com.example.csit228f2_2;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        try(Connection c = MySQLConnection.getConnection();
            Statement statement = c.createStatement()){

            String createTableQuery = "CREATE TABLE IF NOT EXISTS user (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "password VARCHAR(50) NOT NULL, " +
                    "username VARCHAR(50) NOT NULL )";

            statement.execute(createTableQuery);
            System.out.println("Table created successfully!");

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}