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

import java.io.*;
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

        summaryBuilder.withConnector(Connector.and).withSummaryType(summaryType).withMultiForm(multiType)
                .withQuantifier(linguisticQuantifierRepo.getAll());

        LinguisticVariable qualifier1 = null;
        LinguisticVariable qualifier2 = null;

        if (qualifierIndexes.get(0) != -1) {

            qualifier1 = linguisticVariableRepo.getVariable(qualifierIndexes.get(0));

        }
        if (qualifierIndexes.get(1) != -1) {

            qualifier2 = linguisticVariableRepo.getVariable(qualifierIndexes.get(1));
        }

        List<LinguisticQuantifier> quantifiers = (summaryType == SummaryTypes.single && multiType == 1) ?
                linguisticQuantifierRepo.getAll() : linguisticQuantifierRepo.getSelected(LinguisticQuantifiersTypes.relative);

        switch(summaryType) {

            case single -> {

                List<ClassicSet> classicSetsSummarizers = getSummarizersClassicSets(summarizersIndexes);

                List<LinguisticVariable> variables = getSummarizersLinguisticVariables(summarizersIndexes);

                for(int i = 0; i < summarizersIndexes.size(); i++) {

                    if (i < variables.size()) {
                        variables.get(i).setClassicSet(classicSetsSummarizers.get(i));
                    }
                }


                switch(multiType){

                    case 1 -> {

                        for (int i = 0; i < variables.get(0).getAllFuzzySets().size(); i++) {

                            variables.get(0).clearCurrentLabels();
                            variables.get(0).addCurrentLabel(i);

                            if (variables.size() > 1)
                            for (int j = 0; j < variables.get(1).getAllFuzzySets().size(); j++) {

                                variables.get(1).clearCurrentLabels();
                                variables.get(1).addCurrentLabel(j);


                                SummaryMaker summary = summaryBuilder.withSummarizers(variables).withQuantifier(quantifiers).build();

                                summaryRepo.addAll(summary.getStringSummariesWithAverageT(weights));
                            }

                            if (variables.size() == 1) {
                                SummaryMaker summary = summaryBuilder.withSummarizers(Arrays.asList(variables.get(0))).withQuantifier(quantifiers).build();

                                summaryRepo.addAll(summary.getStringSummariesWithAverageT(weights));
                            }

                        }
                    }
                    case 2 -> {

                        qualifier1.setClassicSet(new ClassicSet(housesRepo.getValuesOfAttribute(qualifier1.getAttributeType()),
                                qualifier1.getSpace(), false));

                        if (qualifier2 != null) {
                            qualifier2.setClassicSet(new ClassicSet(housesRepo.getValuesOfAttribute(qualifier2.getAttributeType()),
                                    qualifier2.getSpace(), false));
                        }



                        for (int i = 0; i < variables.get(0).getAllFuzzySets().size(); i++) {

                            variables.get(0).clearCurrentLabels();
                            variables.get(0).addCurrentLabel(i);

                            for (int k = 0; k < qualifier1.getAllFuzzySets().size(); k++) {

                                qualifier1.clearCurrentLabels();
                                qualifier1.addCurrentLabel(k);

                                if (qualifier2 != null)
                                for (int j = 0; j < qualifier2.getAllFuzzySets().size(); j++) {

                                    qualifier2.clearCurrentLabels();
                                    qualifier2.addCurrentLabel(j);

                                    if (variables.size() > 1)
                                    for (int h = 0; h < variables.get(1).getAllFuzzySets().size(); h++) {

                                        variables.get(1).clearCurrentLabels();
                                        variables.get(1).addCurrentLabel(h);


                                        SummaryMaker summary = summaryBuilder.withSummarizers(variables).withQuantifier(quantifiers)
                                                .withQualifiers(Arrays.asList(qualifier1, qualifier2)).build();

                                        summaryRepo.addAll(summary.getStringSummariesWithAverageT(weights));
                                    }

                                    if (variables.size() == 1) {
                                        SummaryMaker summary = summaryBuilder.withSummarizers(Arrays.asList(variables.get(0))).
                                                withQuantifier(quantifiers)
                                                .withQualifiers(Arrays.asList(qualifier1, qualifier2)).build();

                                        summaryRepo.addAll(summary.getStringSummariesWithAverageT(weights));
                                    }
                                }

                                if (variables.size() > 1 && qualifier2 == null) {
                                    for (int j = 0; j < variables.get(1).getAllFuzzySets().size(); j++) {

                                        variables.get(1).clearCurrentLabels();
                                        variables.get(1).addCurrentLabel(j);


                                        SummaryMaker summary = summaryBuilder.withSummarizers(variables).withQuantifier(quantifiers)
                                                .withQualifiers(Arrays.asList(qualifier1)).build();

                                        summaryRepo.addAll(summary.getStringSummariesWithAverageT(weights));
                                    }

                                    if (variables.size() == 1) {
                                        SummaryMaker summary = summaryBuilder.withSummarizers(Arrays.asList(variables.get(0))).
                                                withQuantifier(quantifiers)
                                                .withQualifiers(Arrays.asList(qualifier1)).build();

                                        summaryRepo.addAll(summary.getStringSummariesWithAverageT(weights));
                                    }
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

