package Main;

import Managers.HouseLoadingManager;
import Managers.LinguisticVariablesLoadingManager;
import Managers.QuantifiersLoadingManager;
import Repos.HousesRepo;
import Repos.LinguisticQuantifierRepo;
import Repos.LinguisticVariableRepo;
import SetsModel.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws URISyntaxException, CloneNotSupportedException, SQLException, IOException {

        HousesRepo housesRepo = new HousesRepo(HouseLoadingManager.LoadHouses());

        LinguisticQuantifierRepo quantifierRepo = new LinguisticQuantifierRepo();
        LinguisticVariableRepo linguisticVariableRepo = new LinguisticVariableRepo();

        QuantifiersLoadingManager.loadLinguisticQuantifiers(quantifierRepo);
        LinguisticVariablesLoadingManager.loadLinguisticQuantifiers(linguisticVariableRepo);

        System.out.println(quantifierRepo);
        System.out.println(linguisticVariableRepo);


    }
}
