package Main;

import Managers.LoadingManager;
import Repos.HousesRepo;
import SetsModel.ClassicSet;
import SetsModel.Range;
import SetsModel.Space;

import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws URISyntaxException, CloneNotSupportedException, SQLException {


        /*
        HousesRepo repo = new HousesRepo(
                LoadingManager.LoadHouses()
        );

        for (var house : repo.getHouses()
             ) {

            System.out.println(house.toString());
        }

         */



        ClassicSet set1 = new ClassicSet(Arrays.asList(4.0, 7.0, 5.0, 8.89), new Space(Space.SpaceType.dense,
                new Range(0.0, 9.0)), false);

        ClassicSet set2 = new ClassicSet(Arrays.asList(4.0, 8.69, 3.85, 8.89), new Space(Space.SpaceType.dense,
                new Range(0.0, 9.0)), true);

        ClassicSet set12 = set1.product(set2);



        System.out.println(set1);
        System.out.println(set2);
        System.out.println(set12);
    }
}
