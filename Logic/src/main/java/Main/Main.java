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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws URISyntaxException, CloneNotSupportedException, SQLException, IOException {

        HousesRepo housesRepo = new HousesRepo(HouseLoadingManager.LoadHouses());

        LinguisticQuantifierRepo quantifierRepo = new LinguisticQuantifierRepo();
        LinguisticVariableRepo linguisticVariableRepo = new LinguisticVariableRepo();

        QuantifiersLoadingManager.loadLinguisticQuantifiers(quantifierRepo);
        LinguisticVariablesLoadingManager.loadLinguisticQuantifiers(linguisticVariableRepo);

        System.out.println(quantifierRepo);
        System.out.println(linguisticVariableRepo);

        ClassicSet set1 = new ClassicSet(housesRepo.getValuesOfAttribute(AttributeType.livingArea), null, false);
        ClassicSet set2 = new ClassicSet(housesRepo.getValuesOfAttribute(AttributeType.kitchenArea), null, false);

        linguisticVariableRepo.getVariable(AttributeType.livingArea).setCurrentLabel("niewielkie");
        linguisticVariableRepo.getVariable(AttributeType.kitchenArea).setCurrentLabel("duże");
        Summary summary = new Summary(linguisticVariableRepo.getVariable(AttributeType.kitchenArea), quantifierRepo.getAll(),
                Arrays.asList(linguisticVariableRepo.getVariable(AttributeType.livingArea)), set1, set2, 2);

        List<Double> f1 = summary.getF_1();

        for (var v:f1
             ) {


            System.out.println(v);
        }

        System.out.println(quantifierRepo);
    }
}
