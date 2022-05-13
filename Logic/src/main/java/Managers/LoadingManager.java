package Managers;

import DataModel.House;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class LoadingManager {

    private static final String FILE_NAME = "PortlandHousingPrices.csv";

    public static List<House> LoadHouses() throws URISyntaxException {

        List<House> houses = new ArrayList<>();

        File file = new File(LoadingManager.class.getClassLoader().getResource(FILE_NAME).toURI());

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                int number = Integer.parseInt(parts[0]);

                Double values[] = new Double[10];

                for(int i = 0; i < 10; i++){

                    values[i] = Double.parseDouble(parts[i+1]);
                }

                houses.add(new House(number, values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return houses;
    }
}
