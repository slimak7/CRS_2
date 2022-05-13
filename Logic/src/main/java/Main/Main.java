package Main;

import Managers.LoadingManager;
import Repos.HousesRepo;

import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) throws URISyntaxException {

        HousesRepo repo = new HousesRepo(
                LoadingManager.LoadHouses()
        );

        for (var house : repo.getHouses()
             ) {

            System.out.println(house.toString());
        }
    }
}
