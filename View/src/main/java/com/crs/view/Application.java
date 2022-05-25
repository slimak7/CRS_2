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
import org.paukov.combinatorics3.Generator;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

                List<List<Integer>> combinationSum1 = generateAllLabelsCombinations(variables.get(0).getAllLabels().size(), 2);
                List<List<Integer>> combinationSum2 = new ArrayList<>();

                if (variables.size() > 1)
                    combinationSum2 = generateAllLabelsCombinations(variables.get(1).getAllLabels().size(), 2);

                switch(multiType){

                    case 1 -> {

                        for (int i = 0; i < combinationSum1.size(); i++) {

                            variables.get(0).clearCurrentLabels();
                            variables.get(0).addCurrentLabel(combinationSum1.get(i));

                            for (int j = 0; j < combinationSum2.size(); j++) {

                                variables.get(1).clearCurrentLabels();
                                variables.get(1).addCurrentLabel(combinationSum2.get(j));


                                SummaryMaker summary = summaryBuilder.withSummarizers(variables).withQuantifier(linguisticQuantifierRepo.getAll()).build();

                                summaryRepo.addAll(summary.getStringSummariesWithAverageT());
                            }

                            if (variables.size() == 1) {
                                SummaryMaker summary = summaryBuilder.withSummarizers(Arrays.asList(variables.get(0))).withQuantifier(linguisticQuantifierRepo.getAll()).build();

                                summaryRepo.addAll(summary.getStringSummariesWithAverageT());
                            }

                        }
                    }
                    case 2 -> {


                        qualifier.setClassicSet(new ClassicSet(housesRepo.getValuesOfAttribute(qualifier.getAttributeType()),
                                qualifier.getSpace(), false));

                        List<List<Integer>> combinationQ = generateAllLabelsCombinations(qualifier.getAllLabels().size(), 2);

                        for (int i = 0; i < combinationSum1.size(); i++) {

                            variables.get(0).clearCurrentLabels();
                            variables.get(0).addCurrentLabel(combinationSum1.get(i));

                            for (int k = 0; k < combinationQ.size(); k++) {

                                qualifier.clearCurrentLabels();
                                qualifier.addCurrentLabel(combinationQ.get(k));

                                for (int j = 0; j < combinationSum2.size(); j++) {

                                    variables.get(1).clearCurrentLabels();
                                    variables.get(1).addCurrentLabel(combinationSum2.get(j));


                                    SummaryMaker summary = summaryBuilder.withSummarizers(variables).withQuantifier(linguisticQuantifierRepo.getAll())
                                            .withQualifier(qualifier).build();

                                    summaryRepo.addAll(summary.getStringSummariesWithAverageT());
                                }

                                if (variables.size() == 1) {
                                    SummaryMaker summary = summaryBuilder.withSummarizers(Arrays.asList(variables.get(0))).
                                            withQuantifier(linguisticQuantifierRepo.getAll())
                                            .withQualifier(qualifier).build();

                                    summaryRepo.addAll(summary.getStringSummariesWithAverageT());
                                }
                            }

                        }

                    }
                }
            }
            case multi -> {

            }
        }

        Controller.instance.showSummary(summaryRepo.getAllToString());

    }

    public static void main(String[] args) {
        launch();
    }

    private List<List<Integer>> generateAllLabelsCombinations(Integer n, Integer r) {

        List<Integer> values = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            values.add(i);
        }

        List<List<Integer>> combinationR = Generator.combination(values).simple(r).stream().toList();

        List<List<Integer>> combinationOne = Generator.combination(values).simple(1).stream().toList();

        return Stream.concat(combinationR.stream(), combinationOne.stream()).collect(Collectors.toList());
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

