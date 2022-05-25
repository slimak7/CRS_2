package com.crs.view;

import Managers.HouseLoadingManager;
import Managers.LinguisticVariablesLoadingManager;
import Managers.QuantifiersLoadingManager;
import Repos.HousesRepo;
import Repos.LinguisticQuantifierRepo;
import Repos.LinguisticVariableRepo;
import Repos.SummaryRepo;
import SetsModel.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.commons.math3.util.CombinatoricsUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static SetsModel.FuzzyOperationsType.Product;

public class Application extends javafx.application.Application {

    public static Application instance;

    private HousesRepo housesRepo;
    private LinguisticQuantifierRepo linguisticQuantifierRepo;
    private LinguisticVariableRepo linguisticVariableRepo;

    private SummaryRepo summaryRepo;

    private Double truthBorder;

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("startView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("KSR");
        stage.setScene(scene);


        stage.show();

        initialize();
    }

    private void initialize() throws URISyntaxException, SQLException, IOException {

        instance = this;

        housesRepo = new HousesRepo(
                HouseLoadingManager.LoadHouses()
        );

        linguisticQuantifierRepo = new LinguisticQuantifierRepo();
        linguisticVariableRepo = new LinguisticVariableRepo();

        QuantifiersLoadingManager.loadLinguisticQuantifiers(linguisticQuantifierRepo);
        LinguisticVariablesLoadingManager.loadLinguisticQuantifiers(linguisticVariableRepo);

        summaryRepo = new SummaryRepo();

        Controller.instance.setObjects(housesRepo, linguisticVariableRepo, linguisticQuantifierRepo);
    }


    public void generateAllSummaries(SummaryTypes summaryType, Integer multiType, Integer qualifierIndex, List<Integer> summarizersIndexes, Double truthBorder) {

        summaryRepo.clearAll();

        SummaryBuilder summaryBuilder = SummaryBuilder.aSummary();

        summaryBuilder.withConnector(Connector.and).withSummaryType(summaryType).withMultiForm(multiType)
                .withQuantifier(linguisticQuantifierRepo.getAll());

        LinguisticVariable qualifier = null;

        if (qualifierIndex != -1) {
            qualifier = linguisticVariableRepo.getVariable(qualifierIndex);

        }

        switch(summaryType) {

            case single -> {

                List<ClassicSet> classicSetsSummarizers = getSummarizersClassicSets(summarizersIndexes);

                List<LinguisticVariable> variables = getSummarizersLinguisticVariables(summarizersIndexes);

                for(int i = 0; i < summarizersIndexes.size(); i++) {

                    if (i < variables.size()) {
                        variables.get(i).setClassicSet(classicSetsSummarizers.get(i));
                    }
                }

                Integer s1 = variables.get(0).getLabels().size();
                Integer s2 = 0;

                if (variables.size() > 1)
                    s2 = variables.get(1).getLabels().size();

                switch(multiType){

                    case 1 -> {

                        for (int i = 0; i < s1; i++) {

                            variables.get(0).clearCurrentLabels();
                            variables.get(0).addCurrentLabel(i);

                            for (int j = 0; j < s2; j++) {

                                variables.get(1).clearCurrentLabels();
                                variables.get(1).addCurrentLabel(j);


                                SummaryMaker summary = summaryBuilder.withSummarizers(variables).withQuantifier(linguisticQuantifierRepo.getAll()).build();

                                summaryRepo.addAll(summary.getStringSummariesWithAverageT());
                            }

                            SummaryMaker summary = summaryBuilder.withSummarizers(Arrays.asList(variables.get(0))).withQuantifier(linguisticQuantifierRepo.getAll()).build();

                            summaryRepo.addAll(summary.getStringSummariesWithAverageT());

                        }
                    }
                    case 2 -> {

                        qualifier.setClassicSet(new ClassicSet(housesRepo.getValuesOfAttribute(qualifier.getAttributeType()),
                                qualifier.getSpace(), false));

                        for (int i = 0; i < s1; i++) {

                            variables.get(0).clearCurrentLabels();
                            variables.get(0).addCurrentLabel(i);

                            for (int j = 0; j < s2; j++) {


                                Integer k;
                                for (k = 0; k < qualifier.getAllLabels().size(); k++) {

                                    qualifier.clearCurrentLabels();
                                    qualifier.addCurrentLabel(k);

                                    variables.get(1).clearCurrentLabels();
                                    variables.get(1).addCurrentLabel(j);


                                    SummaryMaker summary = summaryBuilder.withSummarizers(variables).withQuantifier(linguisticQuantifierRepo.getAll()).
                                            withQualifier(qualifier).build();

                                    summaryRepo.addAll(summary.getStringSummariesWithAverageT());
                                }

                            }
                        }
                    }
                }
            }
        }

        Controller.instance.showSummary(summaryRepo.getAllToString());

    }

    public static void main(String[] args) {
        launch();
    }



    private List<LinguisticVariable> getSummarizersLinguisticVariables(List<Integer> summarizersIndexes) {

        List<LinguisticVariable> variables = new ArrayList<>();

        for (var i:summarizersIndexes
        ) {

            if (i != -1) {
                variables.add(linguisticVariableRepo.getVariable(i));
            }
        }

        return variables;
    }

    private List<ClassicSet> getSummarizersClassicSets(List<Integer> summarizersIndexes) {

        List<ClassicSet> sets = new ArrayList<>();

        for (var i:summarizersIndexes
             ) {

            if (i != -1) {
                LinguisticVariable linguisticVariable = linguisticVariableRepo.getVariable(i);

                sets.add(new ClassicSet(housesRepo.getValuesOfAttribute(linguisticVariable.getAttributeType()),
                        linguisticVariable.getSpace(), false));
            }
        }

        return sets;
    }
}

