package SetsModel;

public enum AttributeType {
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


    @Override
    public String toString() {

        switch (this) {

            case price -> {

                return "cena";
            }
            case livingArea -> {

                return "powierzchnia mieszkalna";
            }
            case lotSize -> {

                return "działka";
            }
            case kitchenArea -> {

                return "kuchnia";
            }
            case bedroomArea -> {

                return "sypialnia";
            }
            case livingRoomArea -> {

                return "pokój dzienny";
            }
            case diningRoomArea -> {

                return "jadalnia";
            }
            case elementarySchoolDistance -> {

                return "odległość do szkoły podstawowej";
            }
            case middleSchoolDistance -> {

                return "odległość do gimnazjum";
            }
            case highSchoolDistance -> {

                return "odległość do liceum";
            }
            default -> {
                return null;
            }
        }

    }
}
