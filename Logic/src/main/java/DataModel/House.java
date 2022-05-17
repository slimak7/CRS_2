package DataModel;

import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class House {

    @Getter private Integer number;
    @Getter private Double
    price,
    livingArea,
    lotSize,
    kitchenArea,
    bedroomArea,
    livingRoomArea,
    diningRoomArea,
    elementarySchoolDistance,
    middleSchoolDistance,
    highSchoolDistance;
    @Getter String city;

    public House(Integer number, Double values[]) {
        this.number = number;

        price = values[0];
        livingArea = values[1];
        lotSize = values[2];
        kitchenArea = values[3];
        bedroomArea = values[4];
        livingRoomArea = values[5];
        diningRoomArea = values[6];
        elementarySchoolDistance = values[7];
        middleSchoolDistance = values[8];
        highSchoolDistance = values[9];
    }
}
