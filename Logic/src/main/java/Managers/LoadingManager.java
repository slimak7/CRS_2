package Managers;

import DataModel.House;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoadingManager {

    private static final String DB_NAME = "jdbc:sqlite:HousesDB";

    public static List<House> LoadHouses() throws URISyntaxException, SQLException {

        List<House> houses = new ArrayList<>();

        Connection connection = DriverManager.getConnection(DB_NAME);

        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        ResultSet resultSet = statement.executeQuery("select * from main.houses");

        while (resultSet.next()){

            Integer id = resultSet.getInt(1);

            Double values[] = new Double[10];

            for(int i = 0; i < 10; i++) {
                values[i] = resultSet.getDouble(i + 2);
            }

            houses.add(new House(id, values));

        }

        return houses;
    }
}
