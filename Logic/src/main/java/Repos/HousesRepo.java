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

    private List<Double> getValuesOfAttribute(AttributeType attributeType, List<House> houses) {

        if (houses == null)
            houses = this.houses;

        if (attributeType.equals(AttributeType.price))
            return houses.stream().map(x -> x.getPrice()).collect(Collectors.toList());

        if (attributeType.equals(AttributeType.livingArea))
            return houses.stream().map(x -> x.getLivingArea()).collect(Collectors.toList());

        if (attributeType.equals(AttributeType.lotSize))
            return houses.stream().map(x -> x.getLotSize()).collect(Collectors.toList());

        if (attributeType.equals(AttributeType.kitchenArea))
            return houses.stream().map(x -> x.getKitchenArea()).collect(Collectors.toList());

        if (attributeType.equals(AttributeType.bedroomArea))
            return houses.stream().map(x -> x.getBedroomArea()).collect(Collectors.toList());

        if (attributeType.equals(AttributeType.livingRoomArea))
            return houses.stream().map(x -> x.getLivingRoomArea()).collect(Collectors.toList());

        if (attributeType.equals(AttributeType.diningRoomArea))
            return houses.stream().map(x -> x.getDiningRoomArea()).collect(Collectors.toList());

        if (attributeType.equals(AttributeType.elementarySchoolDistance))
            return houses.stream().map(x -> x.getElementarySchoolDistance()).collect(Collectors.toList());

        if (attributeType.equals(AttributeType.middleSchoolDistance))
            return houses.stream().map(x -> x.getMiddleSchoolDistance()).collect(Collectors.toList());

        if (attributeType.equals(AttributeType.highSchoolDistance))
            return houses.stream().map(x -> x.getHighSchoolDistance()).collect(Collectors.toList());

        return null;
    }

    public List<Double> getValuesOfAttribute(AttributeType attributeType, String type) {

        return getValuesOfAttribute(attributeType, houses.stream().filter(x -> x.getHouseType().equals(type)).collect(Collectors.toList()));
    }

    public List<Double> getValuesOfAttribute(AttributeType attributeType) {

        return getValuesOfAttribute(attributeType, (List<House>) null);
    }
}
