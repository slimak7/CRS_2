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

import org.paukov.combinatorics3.Generator;


import java.io.*;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;


public class Application extends javafx.application.Application {

    public static Application instance;

    private HousesRepo housesRepo;
    private LinguisticQuantifierRepo linguisticQuantifierRepo;
    private LinguisticVariableRepo linguisticVariableRepo;

    private SummaryRepo summaryRepo;

    private Double truthDegree;

    private List<Double> weights;

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

        weights = new ArrayList<>();

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


    public void generateAllSummaries(SummaryTypes summaryType, Integer multiType, List<Integer> qualifierIndexes, List<Integer> summarizersIndexes, Double truthBorder) {

        truthDegree = truthBorder;

        weights = Controller.instance.getWeights();

        summaryRepo.clearAll();

        SummaryMakerBuilder summaryBuilder = SummaryMakerBuilder.aSummaryMaker();

        summaryBuilder.withConnector(Connector.and).withSummaryType(summaryType).withMultiForm(multiType);


        switch(summaryType) {

            case single -> {

                List<ClassicSet> classicSetsSummarizers = getSummarizersClassicSets(summarizersIndexes);

                List<LinguisticVariable> variables = getSummarizersLinguisticVariables(summarizersIndexes);

                for(int i = 0; i < summarizersIndexes.size(); i++) {


                     variables.get(i).setClassicSet(classicSetsSummarizers.get(i));

                }

                var optionsSummarizers = generateAllSummariesOptions(summarizersIndexes.size());

                switch(multiType){

                    case 1 -> {

                        for(var option:optionsSummarizers) {

                            boolean ok = true;

                            for (int i = 0; i < optionsSummarizers.size(); i++) {

                                if (i < variables.size()) {
                                    if (option.get(i) < variables.get(i).getLabels().size()) {
                                        variables.get(i).clearCurrentLabels();
                                        variables.get(i).addCurrentLabel(option.get(i));
                                    } else {

                                        ok = false;
                                        break;
                                    }
                                }
                                else {

                                    break;
                                }

                            }

                            if (ok) {
                                SummaryMaker sum = summaryBuilder.withSummarizers(variables).
                                        withQuantifier(linguisticQuantifierRepo.getAll()).build();

                                summaryRepo.addAll(sum.getStringSummariesWithAverageT(weights));
                            }
                        }

                    }
                    case 2 -> {

                        var optionsQualifiers = generateAllSummariesOptions(qualifierIndexes.size());

                        List<LinguisticVariable> qualifiers = getSummarizersLinguisticVariables(qualifierIndexes);

                        List<ClassicSet> classicSetsQualifiers = getSummarizersClassicSets(qualifierIndexes);


                        for(int i = 0; i < qualifierIndexes.size(); i++) {


                            qualifiers.get(i).setClassicSet(classicSetsQualifiers.get(i));

                        }

                        for(var option:optionsSummarizers) {
                            boolean ok = true;

                            for (int i = 0; i < optionsSummarizers.size(); i++) {

                                if (i < variables.size()) {
                                    if (option.get(i) < variables.get(i).getLabels().size()) {
                                        variables.get(i).clearCurrentLabels();
                                        variables.get(i).addCurrentLabel(option.get(i));

                                    } else {
                                        ok = false;
                                        break;
                                    }
                                }
                                else {
                                    break;
                                }

                            }

                            for(var optionQ:optionsQualifiers) {


                                for (int j = 0; j < optionsQualifiers.size(); j++) {

                                    if (j < qualifiers.size()) {
                                        if (optionQ.get(j) < qualifiers.get(j).getLabels().size()) {

                                            qualifiers.get(j).clearCurrentLabels();
                                            qualifiers.get(j).addCurrentLabel(optionQ.get(j));


                                        } else {
                                            ok = false;
                                            break;
                                        }
                                    }
                                    else {
                                        break;
                                    }

                                }

                                if (ok) {
                                    SummaryMaker sum = summaryBuilder.withSummarizers(variables).
                                            withQuantifier(linguisticQuantifierRepo.getSelected(LinguisticQuantifiersTypes.relative)).
                                            withQualifiers(qualifiers).build();

                                    summaryRepo.addAll(sum.getStringSummariesWithAverageT(weights));
                                }

                            }

                        }
                    }
                }
            }
            case multi -> {

            }
        }

        summaryRepo.setDegreeOfTruth(truthBorder);
        summaryRepo.setMeasureToSort(Controller.instance.getMeasureIndexToSort());
        Controller.instance.showSummary(summaryRepo.getAllToString());

    }

    private List<List<Integer>> generateAllSummariesOptions(Integer r) {

        List<List<Integer>> options = new ArrayList<>();

        Generator.combination(0, 1, 2, 3).multi(r).stream().forEach(combination -> Generator.permutation(combination)
                .simple().stream().forEach(x-> options.add(x)));

        return options;

    }

    public void saveSummaries(String path) throws FileNotFoundException, UnsupportedEncodingException {

        String text = summaryRepo.getAllToStringWithMeasures();

        PrintWriter writer = new PrintWriter(path + ".txt", "UTF-8");

        writer.write(text);
        writer.close();
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

