package Main;

import Managers.LoadingManager;
import Repos.HousesRepo;
import SetsModel.*;

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


        /*
        ClassicSet set1 = new ClassicSet(Arrays.asList(4.0, 7.0, 5.0, 8.89), new Space(Space.SpaceType.dense,
                new Range(0.0, 9.0)), false);

        ClassicSet set2 = new ClassicSet(Arrays.asList(4.0, 8.69, 3.85, 8.89), new Space(Space.SpaceType.dense,
                new Range(0.0, 9.0)), true);

        ClassicSet set12 = set1.product(set2);



        System.out.println(set1);
        System.out.println(set2);
        System.out.println(set12);

        */

        /*

        Function function1 = new Function(FuzzyOperationsType.None, new TriangularFunction(14.0, 20.0, 25.5),
                null);

        Function function2 = new Function(FuzzyOperationsType.None, new TriangularFunction(14.0, 20.0, 30.0),
                null);

        Function function3 = new Function(FuzzyOperationsType.None, new TriangularFunction(50.0, 78.0, 80.0),
                null);

        FuzzySet f1 = new FuzzySet(new ClassicSet(Arrays.asList(5.0, 8.0, 20.0), new Space(Space.SpaceType.dense,
                new Range(0.0, 100.0)), false), function1, false);

        FuzzySet f2 = new FuzzySet(new ClassicSet(Arrays.asList(26.0, 22.8, 78.0), new Space(Space.SpaceType.dense,
                new Range(0.0, 100.0)), false), function2, false);

        FuzzySet f3 = f1.sum(f2);

        FuzzySet f4 = new FuzzySet(new ClassicSet(Arrays.asList(14.1, 22.8, 78.0), new Space(Space.SpaceType.dense,
                new Range(0.0, 100.0)), false), function3, false);

        FuzzySet f5 = f3.sum(f4);

        System.out.println(f1);
        System.out.println(f2);
        System.out.println(f3);
        System.out.println(f4);
        System.out.println(f5);


         */

        Function function1 = new Function(FuzzyOperationsType.None, new TriangularFunction(14.0, 20.0, 25.5),
                null);

        Function function2 = new Function(FuzzyOperationsType.None, new TrapezoidalFunction(13.0, 20.0, 30.0, 35.0),
                null);

        FuzzySet f1 = new FuzzySet(new ClassicSet(Arrays.asList(5.0, 8.0, 20.0), new Space(Space.SpaceType.dense,
                new Range(0.0, 100.0)), false), function1, false);

        FuzzySet f2 = new FuzzySet(new ClassicSet(Arrays.asList(26.0, 22.8, 78.0), new Space(Space.SpaceType.dense,
                new Range(0.0, 100.0)), false), function2, false);

        FuzzySet f12 = f1.sum(f2);

        Function function3 = new Function(FuzzyOperationsType.None, new TrapezoidalFunction(13.0, 20.0, 22.0, 25.0),
                null);

        FuzzySet f3 = new FuzzySet(new ClassicSet(Arrays.asList(26.0, 22.8, 78.0, 5.0), new Space(Space.SpaceType.dense,
                new Range(0.0, 100.0)), false), function3, false);

        FuzzySet f123 = f3.product(f12);



        System.out.println(f123);
    }
}
