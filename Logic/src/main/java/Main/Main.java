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

        ClassicSet set1 = new ClassicSet(housesRepo.getValuesOfAttribute(AttributeType.livingArea, "SINGLE_FAMILY"), null, false);
        ClassicSet set2 = new ClassicSet(housesRepo.getValuesOfAttribute(AttributeType.livingArea, "CONDO"), null, false);
        ClassicSet set3 = new ClassicSet(housesRepo.getValuesOfAttribute(AttributeType.price), null, false);

        linguisticVariableRepo.getVariable(AttributeType.price).setCurrentLabel("atrakcyjna");
        linguisticVariableRepo.getVariable(AttributeType.livingArea).setCurrentLabel("duże");
        Summary summary = new Summary(linguisticVariableRepo.getVariable(AttributeType.price), quantifierRepo.getAll(),
                Arrays.asList(linguisticVariableRepo.getVariable(AttributeType.livingArea)), set1, set2, set3, 3,
                SummaryTypes.multi);



        System.out.println(summary.getT_1Values());
    }
}
