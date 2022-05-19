package Repos;

import DataModel.House;
import SetsModel.AttributeType;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class HousesRepo {

    @Getter private List<House> houses;

    public HousesRepo(List<House> houses) {
        this.houses = houses;
    }

    public List<Double> getValuesOfAttribute(AttributeType attributeType) {

        if (attributeType.equals(AttributeType.livingArea))
            return houses.stream().map(x -> x.getLivingArea()).collect(Collectors.toList());

        if (attributeType.equals(AttributeType.kitchenArea))
            return houses.stream().map(x -> x.getKitchenArea()).collect(Collectors.toList());

        //TODO: other cases

        return null;
    }
}
