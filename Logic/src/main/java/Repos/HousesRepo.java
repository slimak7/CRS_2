package Repos;

import DataModel.House;
import lombok.Getter;

import java.util.List;

public class HousesRepo {

    @Getter private List<House> houses;

    public HousesRepo(List<House> houses) {
        this.houses = houses;
    }
}
