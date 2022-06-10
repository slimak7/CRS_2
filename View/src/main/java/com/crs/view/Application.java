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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.paukov.combinatorics3.Generator;


import java.io.*;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Stream;


public class Application extends javafx.application.Application {

    public static Application instance;

    private HousesRepo housesRepo;
    private LinguisticQuantifierRepo linguisticQuantifierRepo;
    private LinguisticVariableRepo linguisticVariableRepo;

    private SummaryRepo summaryRepo;

    private Double truthDegree;

    private List<Double> weights;

    private Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException, URISyntaxException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("startView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("KSR");
        stage.setScene(scene);


        stage.show();

        mainStage = stage;

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


    public void generateAllSummaries(SummaryTypes summaryType, Integer multiType, List<Integer> qualifierIndexes, List<Integer> summarizersIndexes, Double truthBorder, List<String> houseTypes) throws CloneNotSupportedException {

        truthDegree = truthBorder;

        weights = Controller.instance.getWeights();

        summaryRepo.clearAll();

        SummaryMakerBuilder summaryBuilder = SummaryMakerBuilder.aSummaryMaker();

        summaryBuilder.withConnector(Connector.and).withSummaryType(summaryType).withMultiForm(multiType);


        switch(summaryType) {

            case single -> {

                List<List<Integer>> summarizersSubsets = subsets(summarizersIndexes);

                for(List<Integer> summarizersSubset : summarizersSubsets)
                {
                    if(summarizersSubset.isEmpty() || summarizersSubset.size()>2)
                    {
                        continue;
                    }

                    List<ClassicSet> classicSetsSummarizers = getSummarizersClassicSets(summarizersSubset);

                    List<LinguisticVariable> variables = getSummarizersLinguisticVariables(summarizersSubset);

                    for(int i = 0; i < summarizersSubset.size(); i++) {


                         variables.get(i).setClassicSet(classicSetsSummarizers.get(i));

                    }

                    var optionsSummarizers = generateAllSummariesOptions(summarizersSubset.size());

                    switch(multiType) {

                        case 1 -> {

                            for (var option : optionsSummarizers) {

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
                                    } else {

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

                            List<List<Integer>> qualifierSubsets = subsets(qualifierIndexes);

                            for(List<Integer> qualifierSubset : qualifierSubsets) {
                                if (qualifierSubset.isEmpty() || qualifierSubset.size() > 2) {
                                    continue;
                                }
                                Boolean containFlag = false;
                                for (int idx:qualifierSubset) {
                                    if(summarizersSubset.contains(idx))
                                    {
                                        containFlag = true;
                                    }
                                }
                                if (containFlag)
                                {
                                    continue;
                                }

                                var optionsQualifiers = generateAllSummariesOptions(qualifierSubset.size());

                                List<LinguisticVariable> qualifiers = getSummarizersLinguisticVariables(qualifierSubset);

                                List<ClassicSet> classicSetsQualifiers = getSummarizersClassicSets(qualifierSubset);


                                for (int i = 0; i < qualifierSubset.size(); i++) {


                                    qualifiers.get(i).setClassicSet(classicSetsQualifiers.get(i));

                                }

                                for (var option : optionsSummarizers) {
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
                                        } else {
                                            break;
                                        }

                                    }

                                    for (var optionQ : optionsQualifiers) {


                                        for (int j = 0; j < optionsQualifiers.size(); j++) {

                                            if (j < qualifiers.size()) {
                                                if (optionQ.get(j) < qualifiers.get(j).getLabels().size()) {

                                                    qualifiers.get(j).clearCurrentLabels();
                                                    qualifiers.get(j).addCurrentLabel(optionQ.get(j));


                                                } else {
                                                    ok = false;
                                                    break;
                                                }
                                            } else {
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
                }
            }
            case multi -> {

                List<List<Integer>> summarizersSubsets = subsets(summarizersIndexes);

                for(List<Integer> summarizersSubset : summarizersSubsets)
                {
                    if(summarizersSubset.isEmpty() || summarizersSubset.size()>2)
                    {
                        continue;
                    }

                    List<ClassicSet> classicSets1Summarizers = getSummarizersClassicSets(summarizersSubset, houseTypes.get(0));
                    List<ClassicSet> classicSets2Summarizers = getSummarizersClassicSets(summarizersSubset, houseTypes.get(1));

                    List<LinguisticVariable> variables = getSummarizersLinguisticVariables(summarizersSubset);
                    List<LinguisticVariable> variablesCopy = new ArrayList<>();

                    for(var v:variables) {

                        variablesCopy.add((LinguisticVariable) v.clone());
                    }

                    variables = Stream.concat(variables.stream(), variablesCopy.stream()).toList();

                    for(int i = 0; i < summarizersSubset.size(); i++) {

                        variables.get(i).setClassicSet(classicSets1Summarizers.get(i));
                        variables.get(i + summarizersSubset.size()).setClassicSet(classicSets2Summarizers.get(i));
                    }

                    var optionsSummarizers = generateAllSummariesOptions(summarizersSubset.size());

                    switch(multiType) {

                        case 1 -> {

                            for (var option : optionsSummarizers) {

                                boolean ok = true;

                                for (int i = 0; i < optionsSummarizers.size(); i++) {

                                    if (i < option.size()) {
                                        if (option.get(i) < variables.get(i).getLabels().size()) {
                                            variables.get(i).clearCurrentLabels();
                                            variables.get(i + option.size()).clearCurrentLabels();

                                            variables.get(i).addCurrentLabel(option.get(i));
                                            variables.get(i + option.size()).addCurrentLabel((option.get(i)));
                                        } else {

                                            ok = false;
                                            break;
                                        }
                                    } else {

                                        break;
                                    }

                                }

                                if (ok) {
                                    SummaryMaker sum = summaryBuilder.withSummarizers(variables).
                                            withQuantifier(linguisticQuantifierRepo.getSelected(LinguisticQuantifiersTypes.relative)).
                                            withHouseTypes(houseTypes).build();


                                    summaryRepo.addAll(sum.getStringSummariesWithAverageT(weights));
                                }
                            }

                        }
                        case 2 -> {

                            List<List<Integer>> qualifierSubsets = subsets(qualifierIndexes);

                            for(List<Integer> qualifierSubset : qualifierSubsets) {
                                if (qualifierSubset.isEmpty() || qualifierSubset.size() > 1) {
                                    continue;
                                }

                                Boolean containFlag = false;
                                for (int idx:qualifierSubset) {
                                    if(summarizersSubset.contains(idx))
                                    {
                                        containFlag = true;
                                    }
                                }
                                if (containFlag)
                                {
                                    continue;
                                }

                                var optionsQualifiers = generateAllSummariesOptions(qualifierSubset.size());

                                List<LinguisticVariable> qualifiers = getSummarizersLinguisticVariables(qualifierSubset);

                                List<ClassicSet> classicSetsQualifiers = getSummarizersClassicSets(qualifierSubset, houseTypes.get(1));


                                for (int i = 0; i < qualifierSubset.size(); i++) {


                                    qualifiers.get(i).setClassicSet(classicSetsQualifiers.get(i));

                                }

                                for (var option : optionsSummarizers) {
                                    boolean ok = true;

                                    for (int i = 0; i < optionsSummarizers.size(); i++) {

                                        if (i < option.size()) {
                                            if (option.get(i) < variables.get(i).getLabels().size()) {
                                                variables.get(i).clearCurrentLabels();
                                                variables.get(i + option.size()).clearCurrentLabels();

                                                variables.get(i).addCurrentLabel(option.get(i));
                                                variables.get(i + option.size()).addCurrentLabel((option.get(i)));

                                            } else {
                                                ok = false;
                                                break;
                                            }
                                        } else {
                                            break;
                                        }

                                    }

                                    for (var optionQ : optionsQualifiers) {


                                        for (int j = 0; j < optionsQualifiers.size(); j++) {

                                            if (j < qualifiers.size()) {
                                                if (optionQ.get(j) < qualifiers.get(j).getLabels().size()) {

                                                    qualifiers.get(j).clearCurrentLabels();
                                                    qualifiers.get(j).addCurrentLabel(optionQ.get(j));


                                                } else {
                                                    ok = false;
                                                    break;
                                                }
                                            } else {
                                                break;
                                            }

                                        }

                                        if (ok) {
                                            SummaryMaker sum = summaryBuilder.withSummarizers(variables).
                                                    withQuantifier(linguisticQuantifierRepo.getSelected(LinguisticQuantifiersTypes.relative)).
                                                    withQualifiers(qualifiers).
                                                    withHouseTypes(houseTypes).build();

                                            summaryRepo.addAll(sum.getStringSummariesWithAverageT(weights));
                                        }

                                    }

                                }
                            }
                        }
                        case 3 -> {
                            List<List<Integer>> qualifierSubsets = subsets(qualifierIndexes);

                            for(List<Integer> qualifierSubset : qualifierSubsets) {
                                if (qualifierSubset.isEmpty() || qualifierSubset.size() > 1) {
                                    continue;
                                }

                                Boolean containFlag = false;
                                for (int idx:qualifierSubset) {
                                    if(summarizersSubset.contains(idx))
                                    {
                                        containFlag = true;
                                    }
                                }
                                if (containFlag)
                                {
                                    continue;
                                }

                                var optionsQualifiers = generateAllSummariesOptions(qualifierSubset.size());

                                List<LinguisticVariable> qualifiers = getSummarizersLinguisticVariables(qualifierSubset);

                                List<ClassicSet> classicSetsQualifiers = getSummarizersClassicSets(qualifierSubset, houseTypes.get(0));


                                for (int i = 0; i < qualifierSubset.size(); i++) {


                                    qualifiers.get(i).setClassicSet(classicSetsQualifiers.get(i));

                                }

                                for (var option : optionsSummarizers) {
                                    boolean ok = true;

                                    for (int i = 0; i < optionsSummarizers.size(); i++) {

                                        if (i < option.size()) {
                                            if (option.get(i) < variables.get(i).getLabels().size()) {
                                                variables.get(i).clearCurrentLabels();
                                                variables.get(i + option.size()).clearCurrentLabels();

                                                variables.get(i).addCurrentLabel(option.get(i));
                                                variables.get(i + option.size()).addCurrentLabel((option.get(i)));

                                            } else {
                                                ok = false;
                                                break;
                                            }
                                        } else {
                                            break;
                                        }

                                    }

                                    for (var optionQ : optionsQualifiers) {


                                        for (int j = 0; j < optionsQualifiers.size(); j++) {

                                            if (j < qualifiers.size()) {
                                                if (optionQ.get(j) < qualifiers.get(j).getLabels().size()) {

                                                    qualifiers.get(j).clearCurrentLabels();
                                                    qualifiers.get(j).addCurrentLabel(optionQ.get(j));


                                                } else {
                                                    ok = false;
                                                    break;
                                                }
                                            } else {
                                                break;
                                            }

                                        }

                                        if (ok) {
                                            SummaryMaker sum = summaryBuilder.withSummarizers(variables).
                                                    withQuantifier(linguisticQuantifierRepo.getSelected(LinguisticQuantifiersTypes.relative)).
                                                    withQualifiers(qualifiers).
                                                    withHouseTypes(houseTypes).build();

                                            summaryRepo.addAll(sum.getStringSummariesWithAverageT(weights));
                                        }

                                    }

                                }
                            }
                        }

                        case 4 -> {

                            for (var option : optionsSummarizers) {

                                boolean ok = true;

                                for (int i = 0; i < optionsSummarizers.size(); i++) {

                                    if (i < option.size()) {
                                        if (option.get(i) < variables.get(i).getLabels().size()) {
                                            variables.get(i).clearCurrentLabels();
                                            variables.get(i + option.size()).clearCurrentLabels();

                                            variables.get(i).addCurrentLabel(option.get(i));
                                            variables.get(i + option.size()).addCurrentLabel((option.get(i)));
                                        } else {

                                            ok = false;
                                            break;
                                        }
                                    } else {

                                        break;
                                    }

                                }

                                if (ok) {
                                    SummaryMaker sum = summaryBuilder.withSummarizers(variables).withHouseTypes(houseTypes).build();


                                    summaryRepo.addAll(sum.getStringSummariesWithAverageT(weights));
                                }
                            }

                        }
                    }
                }
            }
        }

        summaryRepo.setDegreeOfTruth(truthBorder);

        int index = Controller.instance.getMeasureIndexToSort();

        if (summaryType.equals(SummaryTypes.multi))
            index = 0;

        summaryRepo.setMeasureToSort(index);
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

    private List<ClassicSet> getSummarizersClassicSets(List<Integer> summarizersIndexes, String houseType) {

        List<ClassicSet> sets = new ArrayList<>();

        for (var i:summarizersIndexes
        ) {

            if (i != -1) {
                LinguisticVariable linguisticVariable = linguisticVariableRepo.getVariable(i);

                sets.add(new ClassicSet(housesRepo.getValuesOfAttribute(linguisticVariable.getAttributeType(), houseType),
                        linguisticVariable.getSpace(), false));
            }
        }

        return sets;
    }

    public List<List<Integer>> subsets(List<Integer> nums) {
        List<List<Integer>> list = new ArrayList<>();
        subsetsHelper(list, new ArrayList<>(), nums, 0);
        return list;
    }

    private void subsetsHelper(List<List<Integer>> list , List<Integer> resultList, List<Integer> nums, int start){
        list.add(new ArrayList<>(resultList));
        for(int i = start; i < nums.size(); i++){
            // add element
            resultList.add(nums.get(i));
            // Explore
            subsetsHelper(list, resultList, nums, i + 1);
            // remove
            resultList.remove(resultList.size() - 1);
        }
    }

    public void ShowOptionsWindow() throws IOException {

        Stage dialog = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("createThingsView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        dialog.setTitle("Twórz własne sumaryzatory, kwalifikatory oraz kwantyfikatory!");
        dialog.setScene(scene);

        dialog.initOwner(mainStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();


    }

    public LinguisticQuantifierRepo getLinguisticQuantifierRepo() {
        return linguisticQuantifierRepo;
    }

    public LinguisticVariableRepo getLinguisticVariableRepo() {
        return linguisticVariableRepo;
    }

    public void update() {

        Controller.instance.reset();
    }
}

